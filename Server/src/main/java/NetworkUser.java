import communication.Transferable;
import communication.events.NewMessageEvent;
import communication.requests.LoginRequest;
import communication.requests.RegisterRequest;
import communication.requests.TokenLoginRequest;
import communication.responses.ErrorResponse;
import communication.responses.LoginSuccessResponse;
import communication.responses.RegisterSuccessResponse;
import communication.responses.TokenLoginSuccessResponse;
import persistency.shared.entities.MessageEntity;
import persistency.shared.entities.UserEntity;
import persistency.shared.mappers.MessageMapper;
import persistency.shared.mappers.UserMapper;
import utils.Auth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

/**
 * This class should manage the communication with the user by handling messages.
 * Every user should have an inputThread, an outputThread and a BlockingQueue to properly
 * manage the communication.
 *
 * Every NetworkUser is associated with his UserEntity, that is mapped to UserDTO when it
 * has to be sent to the client.
 * A UserEntity is a class that represent all the data associated to a user.
 */
public class NetworkUser implements Runnable {

    private final Logger logger = Logger.getLogger(getClass().getName());

    private final Socket client;
    private boolean connected = true;

    /**
     *  UserEntity is a class that represent all the data associated to a user.
     */
    private UserEntity userEntity;

    private Thread outputThread;
    private Thread inputThread;

    private final BlockingQueue<Transferable> bq = new LinkedBlockingQueue<>();

    public NetworkUser (Socket client) {
        this.client = client;
        this.userEntity = new UserEntity();
    }

    private void closeConnection() {

        if (!connected) return;

        connected = false;

        inputThread.interrupt();
        outputThread.interrupt();

        try {
            client.close();
        } catch (IOException _) {
            logger.severe("Failed closing client " + client.getInetAddress() + " socket.");
        }

        logger.info("Closed connection with client: " + client.getInetAddress());

        Thread.currentThread().interrupt();
    }

    /**
     * When a NetworkUser thread is launched, it should start the input and the
     * output thread also
     */
    @Override
    public void run() {

        logger.info("User thread started for client " + client.getInetAddress());

        inputThread = new Thread(() -> {

            try {
                DataInputStream in = new DataInputStream(client.getInputStream());
                while (connected) {
                    handleRequest(in);
                }
            } catch (IOException _) {
                logger.severe("Broken DataInputStream for client " + client.getInetAddress());
                closeConnection();
            }
        });
        inputThread.start();


        outputThread = new Thread(() -> {

            try {
                DataOutputStream out = new DataOutputStream(client.getOutputStream());

                while (connected) {
                    handleSendMessage(out);
                }
            } catch (IOException _) {
                logger.severe("Broken DataOutputStream for client " + client.getInetAddress());
                closeConnection();
            }
        });

        outputThread.start();
    }

    private void handleSendMessage(DataOutputStream out) {
        Transferable msg;

        try {
            msg = bq.take();
        } catch (InterruptedException _) {
            logger.severe("bq.take was interrupted. (Should probably deleted this log)");
            closeConnection();
            return;
        }

        sendMessage(msg, out);
    }

    /**
     * This function should read messages from the client (as a JSON string). Every
     * message has to be parsed to get a Message instance, that can be used to find
     * which Message subclass was sent by using instanceof.
     * @param in the input stream
     */
    private void handleRequest(DataInputStream in) throws IOException {

        String data;

        try {
            data = in.readUTF();
        } catch (IOException _) {
            logger.severe("Failed reading data from client " + client.getInetAddress());
            closeConnection();
            return;
        }

        Transferable msg = Transferable.fromJson(data);

        if (msg instanceof RegisterRequest rm) {
            handleRegisterRequest(rm);
        } else if (msg instanceof LoginRequest lm) {
            handleLoginRequest(lm);
        } else if (msg instanceof TokenLoginRequest tlm) {
            handleTokenLoginRequest(tlm);
        }
    }

    /**
     * This function has to send to the client a Message subclass instance, that is serialized
     * and sent to the client as a JSON string.
     * @param sendingTransferable a Messsage subclass
     * @param out the output stream
     */
    private void sendMessage(Transferable sendingTransferable, DataOutputStream out) {

        try {
            out.writeUTF(sendingTransferable.toJson());
        } catch (IOException _) {
            logger.severe("Failed writing data to the client " + client.getInetAddress());
            closeConnection();
        }
    }

    /**
     * This function should notify the user if his username is available. In this case, it sends
     * a RegisterSuccessMessage, otherwise it sends an ErrorMessage
     * @param rm the request the user sent
     */
    private void handleRegisterRequest(RegisterRequest rm) {

        userEntity.setUsername(rm.getUsername());
        userEntity.setPassword(rm.getPassword());
        userEntity.setUserType(rm.getUserType());
        userEntity.setToken(Auth.generateAccessToken());
        userEntity.setCoins(100);

        if (Server.getUserDAO().saveUser(userEntity)) {
            bq.add(new RegisterSuccessResponse(UserMapper.toDTO(userEntity), userEntity.getToken()));

            MessageEntity mess = new MessageEntity();
            mess.setTitle("info");
            mess.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            mess.setDescription("register_welcome");
            mess.setType("info");
            mess.setUsername(rm.getUsername());
            Server.getMessageDAO().save(mess);

            bq.add(new NewMessageEvent(MessageMapper.toDTO(mess)));
        } else {
            bq.add(new ErrorResponse(0));
        }
    }

    /**
     * This function should check if login was successful
     * @param lm request
     */
    private void handleLoginRequest(LoginRequest lm) {

        UserEntity user = Server.getUserDAO().findUserByUsername(lm.getUsername());

        if (user == null) {
            // LOGIN FAILED (user not found)
            bq.add(new ErrorResponse(1));
            return;
        }

        if (Objects.equals(user.getPassword(), lm.getPassword())) {
            bq.add(new LoginSuccessResponse(UserMapper.toDTO(user), MessageMapper.toDTOList(Server.getMessageDAO().get(lm.getUsername())), user.getToken()));
            this.userEntity = user;
        } else {
            // LOGIN FAILED (wrong password)
            bq.add(new ErrorResponse(2));
        }
    }

    /**
     * This function should check the user token that the user sent to auto-authenticate.
     * For simplicity, this token is only changed when the user logs in without using the token
     * or when the user registers.
     * @param tlm request
     */
    private void handleTokenLoginRequest(TokenLoginRequest tlm) {

        UserEntity user = Server.getUserDAO().findUserByToken(tlm.getToken());

        if (user != null) {
            bq.add(new TokenLoginSuccessResponse(UserMapper.toDTO(user), MessageMapper.toDTOList(Server.getMessageDAO().get(user.getUsername())), user.getToken()));
            this.userEntity = user;
        }
    }
}
