package app;

import app.mvc.Boundary;
import app.mvc.BoundaryManager;
import app.mvc.login.LoginResult;
import app.mvc.register.RegisterResult;
import communication.Transferable;
import communication.events.NewMessageEvent;
import communication.requests.LoginRequest;
import communication.requests.RegisterRequest;
import communication.requests.TokenLoginRequest;
import communication.responses.ErrorResponse;
import communication.responses.LoginSuccessResponse;
import communication.responses.RegisterSuccessResponse;
import communication.responses.TokenLoginSuccessResponse;
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
    private final BlockingQueue<Transferable> bq = new LinkedBlockingQueue<>();

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
                enqueueMessage(new TokenLoginRequest(new String(Files.readAllBytes(path))));
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
        LoginRequest lm = new LoginRequest(username, password);
        enqueueMessage(lm);
    }

    /**
     * This function should send a RegisterMessage to the server
     * @param username
     * @param password
     * @param userType
     */
    public void register(String username, String password, String userType) {
        RegisterRequest rm = new RegisterRequest(username, password, userType);
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

                    Transferable parsedTransferable = Transferable.fromJson(input);

                    if (parsedTransferable instanceof RegisterSuccessResponse rsm) {
                        handleRegisterSuccessMessage(rsm);
                    } else if (parsedTransferable instanceof LoginSuccessResponse lsm) {
                        handleLoginSuccessMessage(lsm);
                    } else if (parsedTransferable instanceof TokenLoginSuccessResponse tlsm) {
                        handleTokenLoginSuccessMessage(tlsm);
                    } else if (parsedTransferable instanceof NewMessageEvent nme) {
                        handleNewMessageEvent(nme);
                    } else if (parsedTransferable instanceof ErrorResponse err) {

                        switch (err.error_code) {
                            case 0:
                                BoundaryManager.getInstance().getRegisterBoundary().onRegisterFailed(RegisterResult.USERNAME_ALREADY_IN_USE);
                                break;
                            case 1:
                                BoundaryManager.getInstance().getLoginBoundary().onLoginFailed(LoginResult.USER_NOT_EXISTS);
                                break;
                            case 2:
                                BoundaryManager.getInstance().getLoginBoundary().onLoginFailed(LoginResult.WRONG_PASSWORD);
                                break;
                            default:
                                break;
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

                    Transferable sendingTransferable = bq.take();
                    String json = sendingTransferable.toJson();

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
     * @param sendingTransferable a Message subclass
     */
    private void enqueueMessage(Transferable sendingTransferable) {
        bq.add(sendingTransferable);
    }

    /**
     * This function handles the RegisterSuccessMessage. It also saves the access token sent by the server
     * @param rsm server response
     */
    private void handleRegisterSuccessMessage(RegisterSuccessResponse rsm) {
        BoundaryManager.getInstance().getRegisterBoundary().onRegisterSuccess(rsm.userDTO, rsm.token);
    }

    /**
     * This function handles the LoginSuccessMessage. It also saves the access token sent by the server
     * @param lsm server response
     */
    private void handleLoginSuccessMessage(LoginSuccessResponse lsm) {
        BoundaryManager.getInstance().getLoginBoundary().onLoginSuccess(lsm.userDTO, lsm.token);
        BoundaryManager.getInstance().getViewMessagesBoundary().messagesListArrived(lsm.messages);
    }

    /**
     * This function handles the TokenLoginSuccessMessage.
     * It allows to log-in without writing credentials
     * @param tlsm server response
     */
    private void handleTokenLoginSuccessMessage(TokenLoginSuccessResponse tlsm) {
        BoundaryManager.getInstance().getLoginBoundary().onLoginSuccess(tlsm.userDTO, tlsm.token);
        BoundaryManager.getInstance().getViewMessagesBoundary().messagesListArrived(tlsm.messages);
    }

    private void handleNewMessageEvent(NewMessageEvent nme) {
        BoundaryManager.getInstance().getViewMessagesBoundary().messageArrived(nme.message);
    }
}


