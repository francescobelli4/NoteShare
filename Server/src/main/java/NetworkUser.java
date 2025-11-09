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
        try {
            inputThread.interrupt();
            outputThread.interrupt();

            client.close();
        } catch (IOException _) {
            //TODO
        }

        System.out.println("Connessione chiusa con " + client.getInetAddress());

        Thread.currentThread().interrupt();
    }

    /**
     * When a NetworkUser thread is launched, it should start the input and the
     * output thread also
     */
    @Override
    public void run() {

        System.out.println("User Thread Started");

        inputThread = new Thread(() -> {

            try {
                DataInputStream in = new DataInputStream(client.getInputStream());
                while (connected) {
                    handleRequest(in);
                }
            } catch (IOException _) {
                closeConnection();
            }
        });
        inputThread.start();


        outputThread = new Thread(() -> {

            try {
                DataOutputStream out = new DataOutputStream(client.getOutputStream());

                while (connected) {

                    try {
                        Transferable msg = bq.take();

                        sendMessage(msg, out);
                    } catch (InterruptedException _) {
                        closeConnection();
                    }
                }
            } catch (IOException _) {
                closeConnection();
            }
        });

        outputThread.start();
    }

    /**
     * This function should read messages from the client (as a JSON string). Every
     * message has to be parsed to get a Message instance, that can be used to find
     * which Message subclass was sent by using instanceof.
     * @param in the input stream
     */
    private void handleRequest(DataInputStream in) throws IOException {
        try {
            String data = in.readUTF();
            System.out.println("Received JSON: " + data);

            Transferable msg = Transferable.fromJson(data);

            if (msg instanceof RegisterRequest rm) {
                handleRegisterRequest(rm);
            } else if (msg instanceof LoginRequest lm) {
                handleLoginRequest(lm);
            } else if (msg instanceof TokenLoginRequest tlm) {
                handleTokenLoginRequest(tlm);
            }
        } catch (IOException _) {
            closeConnection();
        }
    }

    /**
     * This function has to send the client a Message subclass instance, that is serialized
     * and sent to the client as a JSON string.
     * @param sendingTransferable a Messsage subclass
     * @param out the output stream
     */
    private void sendMessage(Transferable sendingTransferable, DataOutputStream out) {

        try {
            out.writeUTF(sendingTransferable.toJson());
            System.out.println("Sent: " + sendingTransferable.toJson());
        } catch (IOException _) {
            //TODO
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

        if (userEntity != null) {
            bq.add(new TokenLoginSuccessResponse(UserMapper.toDTO(userEntity), MessageMapper.toDTOList(Server.getMessageDAO().get(userEntity.getUsername())), userEntity.getToken()));
            this.userEntity = user;
        }
    }
}
