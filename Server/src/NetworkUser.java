import memory.shared.user.UserEntity;
import memory.shared.user.UserMapper;
import messages.Message;
import messages.requests.RegisterMessage;
import messages.responses.ErrorMessage;
import messages.responses.RegisterSuccessMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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

    /**
     *  UserEntity is a class that represent all the data associated to a user.
     */
    private UserEntity userEntity;

    private Thread inputThread;
    private Thread outputThread;
    private final BlockingQueue<Message> bq = new LinkedBlockingQueue<>();

    public NetworkUser (Socket client) {
        this.client = client;
        this.userEntity = new UserEntity();
    }

    /**
     * When a NetworkUser thread is launched, it should start the input and the
     * output thread also
     */
    @Override
    public void run() {

        System.out.println("User Thread Started");

        inputThread = new Thread(() -> {

            DataInputStream in = null;
            try {
                in = new DataInputStream(client.getInputStream());
            } catch (IOException e) {
                //TODO
                e.printStackTrace();
            }

            while (true) {
                handleRequest(in);
            }
        });
        inputThread.start();

        outputThread = new Thread(() -> {

            DataOutputStream out = null;
            try {
                out = new DataOutputStream(client.getOutputStream());
            } catch (IOException e) {
                //TODO
                e.printStackTrace();
            }

            while (true) {

                try {
                    Message msg = bq.take();

                    sendMessage(msg, out);
                } catch (InterruptedException e) {
                    //TODO
                    e.printStackTrace();
                }
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
    private void handleRequest(DataInputStream in) {
        try {
            String data = in.readUTF();
            System.out.println("Received JSON: " + data);

            Message msg = Message.fromJson(data);

            if (msg instanceof RegisterMessage rr) {
                handleRegisterRequest(rr);
            }
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    /**
     * This function should notify the user if his username is available. In this case, it sends
     * a RegisterSuccessMessage, otherwise it sends an ErrorMessage
     * @param rr the request the user sent
     */
    private void handleRegisterRequest(RegisterMessage rr) {

        userEntity.setUsername(rr.username);
        userEntity.setPassword(rr.password);
        userEntity.setUserType(rr.userType);
        userEntity.setCoins(100);

        if (Server.userDAO.saveUser(userEntity)) {
            // Rispondo ok!
            bq.add(new RegisterSuccessMessage(UserMapper.toDTO(userEntity)));
        } else {
            // Rispondo che c'è stato un problema!
            bq.add(new ErrorMessage(0));
        }
    }
}
