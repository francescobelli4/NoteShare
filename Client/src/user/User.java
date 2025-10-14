package user;

import dtos.UserDTO;
import graphics.GraphicsController;
import graphics.colored.Icons;
import graphics.colored.Pages;
import javafx.application.Platform;
import locales.Locales;
import messages.Message;
import messages.requests.LoginMessage;
import messages.requests.RegisterMessage;
import messages.requests.TokenLoginMessage;
import messages.responses.ErrorMessage;
import messages.responses.LoginSuccessMessage;
import messages.responses.RegisterSuccessMessage;
import messages.responses.TokenLoginSuccessMessage;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * User should be a singleton class!
 * This class manages the client <--> server communication and the user's data
 * using a UserDTO
 */
public class User {

    /**
     * This does not need to be private, every important check will be done by
     * the server anyway
     */
    private UserDTO userDTO;

    /**
     * Singleton
     */
    private static User instance;
    private User() {}
    public static User getInstance() {

        if (instance == null) {
            instance = new User();
        }

        return instance;
    }

    /**
     * This socket will be used to communicate with the server
     */
    private Socket server;
    /**
     * The best way to implement the communication with the server, is to create two threads:
     * one for receiving and one for sending data to the server.
     * The main thread will be occupied by the UI and I don't want blocking functions to ruin
     * the user's experience by blocking the UI.
     *
     * The communication is based on messages (Message in Shared module), which are
     * serialized using Gson library.
     * So, actually, data travels in JSON format and then the json string is parsed
     * to match the right Message child class
     */
    private Thread inputThread;
    private Thread outputThread;
    /**
     * I use this BlockingQueue to "notify" the outputThread that a Message needs to be sent out
     * to the server.
     * This is the correct way to block outputThread until a message actually needs to be sent.
     * bq.take() will do the job :D
     */
    private final BlockingQueue<Message> bq = new LinkedBlockingQueue<>();

    public UserDTO getUserDTO() {
        return userDTO;
    }

    /**
     * This function connects the socket to the server
     * @param host server's host string
     * @param port server's accepting port
     */
    public void connect(String host, int port) {

        try {
            server = new Socket(host, port);

            System.out.println("Connected! :D");

            startInputThread();
            startOutputThread();

            /**
             * After this function, if userDTO is set, it means that the token login was successful,
             * otherwise it wasn't...
             */
            tokenLogin();

        } catch (IOException e) {
            // TODO
            e.printStackTrace();
        }
    }

    private void tokenLogin() {

        Path path = Path.of("token.txt");

        if (path.toFile().exists()) {
            try {
                enqueueMessage(new TokenLoginMessage(new String(Files.readAllBytes(path))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This function should send a LoginMessage to the server
     * @param username
     * @param password
     */
    public void login(String username, String password) {
        LoginMessage lm = new LoginMessage(username, password);
        enqueueMessage(lm);
    }

    /**
     * This function should send a RegisterMessage to the server
     * @param username
     * @param password
     * @param userType
     */
    public void register(String username, String password, String userType) {
        RegisterMessage rm = new RegisterMessage(username, password, userType);
        enqueueMessage(rm);
    }

    /**
     * This function should save the access token in a file
     * @param token
     */
    private void saveUserToken(String token) {

        File tokenFile = new File("token.txt");

        try {
            tokenFile.createNewFile();

            FileWriter fileWriter = new FileWriter("token.txt");
            fileWriter.write(token);
            fileWriter.close();
        } catch (IOException e) {
            // TODO
            e.printStackTrace();
        }
    }


    /**
     * This function starts the thread that will be used to read data from server.
     * It's possible to find which type of Message I received by using instanceof
     */
    private void startInputThread() {

        inputThread = new Thread(() -> {

            try {
                DataInputStream reader = new DataInputStream(server.getInputStream());

                while (server.isConnected()) {

                    String input = reader.readUTF();

                    System.out.println("INPUT: " + input);

                    Message parsedMessage = Message.fromJson(input);

                    if (parsedMessage instanceof RegisterSuccessMessage rsm) {
                        handleRegisterSuccessMessage(rsm);
                    } else if (parsedMessage instanceof LoginSuccessMessage lsm) {
                        handleLoginSuccessMessage(lsm);
                    } else if (parsedMessage instanceof TokenLoginSuccessMessage tlsm) {
                        handleTokenLoginSuccessMessage(tlsm);
                    } else if (parsedMessage instanceof ErrorMessage err) {

                        switch (err.error_code) {
                            case 0:
                                Platform.runLater(() -> {
                                    GraphicsController.displayNotification(Locales.get("error"), Locales.get("error_username_already_in_use"), Icons.ERROR);
                                });
                                break;
                            case 1:
                                Platform.runLater(() -> {
                                    GraphicsController.displayNotification(Locales.get("error"), Locales.get("error_user_does_not_exist"), Icons.ERROR);
                                });
                                break;
                            case 2:
                                Platform.runLater(() -> {
                                    GraphicsController.displayNotification(Locales.get("error"), Locales.get("error_wrong_password"), Icons.ERROR);
                                });
                        }

                    }
                }
            } catch (IOException e) {
                //TODO
                e.printStackTrace();
            }
        });

        inputThread.start();
    }

    /**
     * This function starts the thread that will be used to send data to the server.
     */
    private void startOutputThread() {

        outputThread = new Thread(() -> {

            try {
                DataOutputStream sender = new DataOutputStream(server.getOutputStream());

                while (server.isConnected()) {

                    Message sendingMessage = bq.take();
                    String json = sendingMessage.toJson();

                    System.out.println("OUTPUT: " + json);

                    sender.writeUTF(json);
                }

            } catch (IOException | InterruptedException e) {
                //TODO
                e.printStackTrace();
            }
        });

        outputThread.start();
    }

    /**
     * This function adds a new message to the bq BlockingQueue.
     * This queue is actually a FIFO.
     * @param sendingMessage a Message subclass
     */
    private void enqueueMessage(Message sendingMessage) {
        bq.add(sendingMessage);
    }

    /**
     * This function handles the RegisterSuccessMessage. It also saves the access token sent by the server
     * @param rsm server response
     */
    private void handleRegisterSuccessMessage(RegisterSuccessMessage rsm) {

        userDTO = rsm.userDTO;

        saveUserToken(rsm.token);

        Platform.runLater(() -> {
            GraphicsController.displayMainPage(Pages.HOME_PAGE);
        });
    }

    /**
     * This function handles the LoginSuccessMessage. It also saves the access token sent by the server
     * @param lsm server response
     */
    private void handleLoginSuccessMessage(LoginSuccessMessage lsm) {

        userDTO = lsm.userDTO;

        saveUserToken(lsm.token);

        Platform.runLater(() -> {
            GraphicsController.displayMainPage(Pages.HOME_PAGE);
        });
    }

    /**
     * This function handles the TokenLoginSuccessMessage.
     * When the UI starts, if userDTO is null, it starts the AccessPage, otherwise it
     * logs in the user, and it shows him the home page directly.
     * @param tlsm server response
     */
    private void handleTokenLoginSuccessMessage(TokenLoginSuccessMessage tlsm) {
        userDTO = tlsm.userDTO;
    }
}


