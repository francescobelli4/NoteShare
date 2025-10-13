import memory.shared.user.UserEntity;
import memory.shared.user.UserMapper;
import messages.Message;
import messages.requests.LoginMessage;
import messages.requests.RegisterMessage;
import messages.requests.TokenLoginMessage;
import messages.responses.ErrorMessage;
import messages.responses.LoginSuccessMessage;
import messages.responses.RegisterSuccessMessage;
import messages.responses.TokenLoginSuccessMessage;
import utils.Auth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
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

    private final BlockingQueue<Message> bq = new LinkedBlockingQueue<>();

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
        } catch (IOException _) {}

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
            } catch (IOException e) {
                e.printStackTrace();
                closeConnection();
            }
        });
        inputThread.start();


        outputThread = new Thread(() -> {

            try {
                DataOutputStream out = new DataOutputStream(client.getOutputStream());

                while (connected) {

                    try {
                        Message msg = bq.take();

                        sendMessage(msg, out);
                    } catch (InterruptedException e) {
                        //TODO
                        closeConnection();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
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

            Message msg = Message.fromJson(data);

            if (msg instanceof RegisterMessage rm) {
                handleRegisterRequest(rm);
            } else if (msg instanceof LoginMessage lm) {
                handleLoginRequest(lm);
            } else if (msg instanceof TokenLoginMessage tlm) {
                handleTokenLoginRequest(tlm);
            }
        } catch (EOFException e) {
            //TODO
            closeConnection();
        } catch (IOException e) {
            closeConnection();
        }
    }

    /**
     * This function has to send the client a Message subclass instance, that is serialized
     * and sent to the client as a JSON string.
     * @param sendingMessage a Messsage subclass
     * @param out the output stream
     */
    private void sendMessage(Message sendingMessage, DataOutputStream out) {

        try {
            out.writeUTF(sendingMessage.toJson());
            System.out.println("Sent: " + sendingMessage.toJson());
        } catch (IOException e) {
            //TODO
            closeConnection();
        }
    }

    /**
     * This function should notify the user if his username is available. In this case, it sends
     * a RegisterSuccessMessage, otherwise it sends an ErrorMessage
     * @param rm the request the user sent
     */
    private void handleRegisterRequest(RegisterMessage rm) {

        userEntity.setUsername(rm.username);
        userEntity.setPassword(rm.password);
        userEntity.setUserType(rm.userType);
        userEntity.setToken(Auth.generateAccessToken());
        userEntity.setCoins(100);

        if (Server.userDAO.saveUser(userEntity)) {
            bq.add(new RegisterSuccessMessage(UserMapper.toDTO(userEntity), userEntity.getToken()));
        } else {
            bq.add(new ErrorMessage(0));
        }
    }

    /**
     * This function should check if login was successful
     * @param lm request
     */
    private void handleLoginRequest(LoginMessage lm) {

        UserEntity userEntity = Server.userDAO.findUserByUsername(lm.username);
        if (userEntity == null) {
            // LOGIN FAILED (user not found)
            bq.add(new ErrorMessage(1));
            return;
        }

        if (Objects.equals(userEntity.getPassword(), lm.password)) {
            bq.add(new LoginSuccessMessage(UserMapper.toDTO(userEntity), userEntity.getToken()));
        } else {
            // LOGIN FAILED (wrong password)
            bq.add(new ErrorMessage(2));
        }
    }

    /**
     * This function should check the user token that the user sent to auto-authenticate.
     * For simplicity, this token is only changed when the user logs in without using the token
     * or when the user registers.
     * @param tlm request
     */
    private void handleTokenLoginRequest(TokenLoginMessage tlm) {

        UserEntity userEntity = Server.userDAO.findUserByToken(tlm.token);

        if (userEntity != null) {
            bq.add(new TokenLoginSuccessMessage(UserMapper.toDTO(userEntity), userEntity.getToken()));
        }
    }
}
