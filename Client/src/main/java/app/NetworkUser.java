package app;

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
import java.util.logging.Logger;


/**
 * User should be a singleton class!
 * This class manages the client <--> server communication and the user's data
 * using a UserDTO.
 * 
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
public class NetworkUser {

    private final Logger logger = Logger.getLogger(getClass().getName());

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

            logger.info("Connected to the server!");

            startInputThread();
            startOutputThread();
        } catch (IOException e) {
            logger.severe("Failed connecting to the server!");
            // TODO tenta la riconnessione con notifica anche
        }
    }

    public void tokenLogin() {

        Path path = Path.of(PathUtils.getOSLocalPath() + "token.txt");

        if (path.toFile().exists()) {
            try {
                enqueueTransferable(new TokenLoginRequest(new String(Files.readAllBytes(path))));
            } catch (IOException _) {
                // Nothing to do...
            }
        }
    }

    /**
     * This function should send a LoginMessage to the server
     * @param username the selected username
     * @param password the selected password
     */
    public void login(String username, String password) {
        LoginRequest lm = new LoginRequest(username, password);
        enqueueTransferable(lm);
    }

    /**
     * This function should send a RegisterMessage to the server
     * @param username the selected username
     * @param password the selected password
     * @param userType the selected userType
     */
    public void register(String username, String password, String userType) {
        RegisterRequest rm = new RegisterRequest(username, password, userType);
        enqueueTransferable(rm);
    }

    /**
     * This function starts the thread that will be used to read data from server.
     * It's possible to find which type of Message I received by using instanceof
     */
    private void startInputThread() {

        Thread inputThread = new Thread(() -> {

            DataInputStream reader = null;

            try {
                reader = new DataInputStream(server.getInputStream());

                while (server.isConnected()) {

                    String input = reader.readUTF();

                    Transferable parsedTransferable = Transferable.fromJson(input);

                    handleInputTransferable(parsedTransferable);
                }
            } catch (IOException e) {
                logger.severe("Failed reading from server!");

                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException _) {
                    System.exit(-1);
                }

                Thread.currentThread().interrupt();
            }
        });

        inputThread.start();
    }


    private void handleInputTransferable(Transferable transferable) {
        if (transferable instanceof RegisterSuccessResponse rsm) {
            handleRegisterSuccessResponse(rsm);
        } else if (transferable instanceof LoginSuccessResponse lsm) {
            handleLoginSuccessResponse(lsm);
        } else if (transferable instanceof TokenLoginSuccessResponse tlsm) {
            handleTokenLoginSuccessResponse(tlsm);
        } else if (transferable instanceof NewMessageEvent nme) {
            handleNewMessageEvent(nme);
        } else if (transferable instanceof ErrorResponse err) {

            switch (err.getErrorCode()) {
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

    /**
     * This function starts the thread that will be used to send data to the server.
     */
    private void startOutputThread() {

        Thread outputThread = new Thread(() -> {

            DataOutputStream sender = null;

            try {
                sender = new DataOutputStream(server.getOutputStream());

                while (server.isConnected()) {

                    Transferable sendingTransferable = bq.take();
                    String json = sendingTransferable.toJson();

                    sender.writeUTF(json);
                }

            } catch (IOException | InterruptedException e) {
                logger.severe("Failed sending data to server");

                try {
                    if (sender != null)
                        sender.close();
                } catch (IOException _) {
                    System.exit(-1);
                }

                Thread.currentThread().interrupt();
            }
        });

        outputThread.start();
    }

    /**
     * This function adds a new transferable to the bq BlockingQueue.
     * This queue is actually a FIFO.
     * @param sendingTransferable a Transferable subclass
     */
    private void enqueueTransferable(Transferable sendingTransferable) {
        bq.add(sendingTransferable);
    }

    /**
     * This function handles the RegisterSuccessResponse. It also saves the access token sent by the server
     * @param rsm server response
     */
    private void handleRegisterSuccessResponse(RegisterSuccessResponse rsm) {
        BoundaryManager.getInstance().getRegisterBoundary().onRegisterSuccess(rsm.getUserDTO(), rsm.getToken());
    }

    /**
     * This function handles the LoginSuccessResponse. It also saves the access token sent by the server
     * @param lsm server response
     */
    private void handleLoginSuccessResponse(LoginSuccessResponse lsm) {
        BoundaryManager.getInstance().getLoginBoundary().onLoginSuccess(lsm.getUserDTO(), lsm.getToken());
        BoundaryManager.getInstance().getViewMessagesBoundary().messagesListArrived(lsm.getMessages());
    }

    /**
     * This function handles the TokenLoginSuccessResponse.
     * It allows to log-in without writing credentials
     * @param tlsm server response
     */
    private void handleTokenLoginSuccessResponse(TokenLoginSuccessResponse tlsm) {
        BoundaryManager.getInstance().getLoginBoundary().onLoginSuccess(tlsm.getUserDTO(), tlsm.getToken());
        BoundaryManager.getInstance().getViewMessagesBoundary().messagesListArrived(tlsm.getMessages());
    }

    /**
     * This function handles the NewMessageEvent.
     * It allows to update UserModel's list of messages
     * @param nme the event
     */
    private void handleNewMessageEvent(NewMessageEvent nme) {
        BoundaryManager.getInstance().getViewMessagesBoundary().messageArrived(nme.getMessage());
    }
}


