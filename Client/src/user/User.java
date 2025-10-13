package user;

import dtos.UserDTO;
import graphics.GraphicsController;
import graphics.colored.Icons;
import graphics.colored.Pages;
import javafx.application.Platform;
import locales.Locales;
import messages.Message;
import messages.responses.ErrorMessage;
import messages.responses.RegisterSuccessMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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
    public static UserDTO userDTO;

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
                        System.out.println("SUCCESSSSSSS");
                        Platform.runLater(() -> {

                            userDTO = rsm.userDTO;

                            System.out.println("ASDSADASDSA");
                            GraphicsController.displayMainPage(Pages.STUDENT_HOME);
                            //GraphicsController.displayNotification(Locales.get("success"), Locales.get("success_register"), Icons.SUCCESS);
                        });
                    } else if (parsedMessage instanceof ErrorMessage err) {
                        Platform.runLater(() -> {
                            GraphicsController.displayNotification(Locales.get("error"), Locales.get("error_username_already_in_use"), Icons.ERROR);
                        });
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
    public void enqueueMessage(Message sendingMessage) {
        bq.add(sendingMessage);
    }
}


