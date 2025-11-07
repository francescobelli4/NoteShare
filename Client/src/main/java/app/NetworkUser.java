package app;

import app.mvc.BoundaryManager;
import app.mvc.login.LoginResult;
import app.mvc.register.RegisterResult;
import messages.Message;
import messages.requests.LoginMessage;
import messages.requests.RegisterMessage;
import messages.requests.TokenLoginMessage;
import messages.responses.ErrorMessage;
import messages.responses.LoginSuccessMessage;
import messages.responses.RegisterSuccessMessage;
import messages.responses.TokenLoginSuccessMessage;
import utils.PathUtils;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * User should be a singleton class!
 * This class manages the client <--> server communication and the user's data
 * using a UserDTO
 */
public class NetworkUser {

    /**
     * Singleton
     */
    private static NetworkUser instance;
    private NetworkUser() {}
    public static NetworkUser getInstance() {

        if (instance == null) {
            instance = new NetworkUser();
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
        } catch (IOException e) {
            // TODO
            e.printStackTrace();
        }
    }

    public void tokenLogin() {

        Path path = Path.of(PathUtils.getOSLocalPath() + "token.txt");

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
                                BoundaryManager.getInstance().getRegisterBoundary().onRegisterFailed(RegisterResult.USERNAME_ALREADY_IN_USE);
                                break;
                            case 1:
                                BoundaryManager.getInstance().getLoginBoundary().onLoginFailed(LoginResult.USER_NOT_EXISTS);
                                break;
                            case 2:
                                BoundaryManager.getInstance().getLoginBoundary().onLoginFailed(LoginResult.WRONG_PASSWORD);
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

            DataOutputStream sender = null;

            try {
                sender = new DataOutputStream(server.getOutputStream());

                while (server.isConnected()) {

                    Message sendingMessage = bq.take();
                    String json = sendingMessage.toJson();

                    System.out.println("OUTPUT: " + json);

                    sender.writeUTF(json);
                }

            } catch (IOException | InterruptedException e) {
                //TODO
                e.printStackTrace();
                Thread.currentThread().interrupt();

                try {
                    if (sender != null)
                        sender.close();
                } catch (IOException ex) {
                    System.exit(-1);
                }
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
        BoundaryManager.getInstance().getRegisterBoundary().onRegisterSuccess(rsm.userDTO, rsm.token);
    }

    /**
     * This function handles the LoginSuccessMessage. It also saves the access token sent by the server
     * @param lsm server response
     */
    private void handleLoginSuccessMessage(LoginSuccessMessage lsm) {
        BoundaryManager.getInstance().getLoginBoundary().onLoginSuccess(lsm.userDTO, lsm.token);
    }

    /**
     * This function handles the TokenLoginSuccessMessage.
     * It allows to log-in without writing credentials
     * @param tlsm server response
     */
    private void handleTokenLoginSuccessMessage(TokenLoginSuccessMessage tlsm) {
        BoundaryManager.getInstance().getLoginBoundary().onLoginSuccess(tlsm.userDTO, tlsm.token);
    }
}


