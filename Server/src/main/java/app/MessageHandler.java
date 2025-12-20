package app;

import communication.SocketMessage;
import communication.SocketMessageFactory;
import communication.SocketMessageType;
import communication.dtos.message.MessageDTO;
import communication.dtos.message.MessageType;
import communication.dtos.requests.login.LoginRequestDTO;
import communication.dtos.requests.login.LoginUsingTokenRequestDTO;
import communication.dtos.requests.register.RegisterRequestDTO;
import communication.dtos.responses.login.LoginFailureReason;
import communication.dtos.responses.login.RegisterFailureReason;
import communication.dtos.user.UserDTO;
import dto_factory.DomainDTOFactory;
import entities.message.MessageEntity;
import entities.user.UserEntity;
import mappers.MessageMapper;
import mappers.UserMapper;
import utils.Hashing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageHandler {

    private static final Logger LOGGER = Logger.getLogger("MessageHandler");

    private static final int CORES_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * Many operations are I/O-bound, so it's reasonable to use CORES_COUNT*2 threads!
     */
    private static final ExecutorService executorService = Executors.newFixedThreadPool(CORES_COUNT * 2);

    private static MessageHandler instance;
    private MessageHandler() {}
    public static MessageHandler getInstance() {
        if (instance == null) {
            instance = new MessageHandler();
        }
        return instance;
    }

    /**
     * This function should handle the data received from a client. It should handle it by
     * checking the message category first.
     *
     * REQUESTS:      a request message requires an answer from the server to the client
     *
     * NOTIFICATIONS: a notification message does not require an answer from the server
     *
     * (Actually it's just for clarity, the server does not need to distinguish messages
     * with their category... The handling process is the same)
     * @param json the received data from the client
     * @param networkUser the user connection manager instance
     */
    public void handleMessage(String json, NetworkUser networkUser) {

        executorService.submit(() -> {
            SocketMessage message = SocketMessage.fromJson(json);
            SocketMessageType messageType = message.getSocketMessageType();

            if (messageType.getCategory() == SocketMessageType.SocketMessageCategory.REQUEST) {
                handleRequest(message, networkUser);
            } else if (messageType.getCategory() == SocketMessageType.SocketMessageCategory.NOTIFICATION) {
                handleNotification(message, networkUser);
            }
        });
    }

    /**
     * This function should handle a request message.
     * @param message the parsed message
     * @param networkUser the user connection manager instance
     */
    private void handleRequest(SocketMessage message, NetworkUser networkUser) {

        if (LOGGER.isLoggable(Level.INFO))
            LOGGER.info("Received Request " + message.getSocketMessageType() + " from client (" + networkUser.getAddress() + ").");

        executorService.submit(() -> {

            switch (message.getSocketMessageType()) {

                case LOGIN_USING_TOKEN_REQUEST -> handleLoginUsingTokenRequest(message, networkUser);
                case REGISTER_REQUEST -> handleRegisterRequest(message, networkUser);
                case LOGIN_REQUEST -> handleLoginRequest(message, networkUser);

                default -> LOGGER.warning("Received an invalid request from client (" + networkUser.getAddress() + ").");
            }
        });
    }

    /**
     * This function should handle a token login request.
     *
     * @param message the parsed message
     * @param networkUser the user connection manager instance
     */
    private void handleLoginUsingTokenRequest(SocketMessage message, NetworkUser networkUser) {

        LoginUsingTokenRequestDTO payload = (LoginUsingTokenRequestDTO) message.getPayload();

        UserEntity userEntity = Server.getUserDAO().findUserByToken(payload.getToken());

        if (userEntity != null) {

            networkUser.write(SocketMessageFactory.createAccessSuccessResponse(UserMapper.toDTO(userEntity), message.getSocketMessageID(), userEntity.getToken()));

            List<MessageDTO> userMessages = MessageMapper.toDTOList(Server.getMessageDAO().get(userEntity.getUsername()));
            networkUser.write(SocketMessageFactory.createMessagesSetNotification(userMessages));
        } else {
            networkUser.write(SocketMessageFactory.createLoginFailureResponse(LoginFailureReason.WRONG_TOKEN, message.getSocketMessageID()));
        }
    }

    /**
     * This function should handle a login request.
     *
     * @param message the parsed message
     * @param networkUser the user connection manager instance
     */
    private void handleLoginRequest(SocketMessage message, NetworkUser networkUser) {
        LoginRequestDTO payload = (LoginRequestDTO) message.getPayload();

        UserEntity userEntity = Server.getUserDAO().findUserByUsername(payload.getUsername());

        if (userEntity != null) {

            if (!Hashing.verifyHash(payload.getPassword(), userEntity.getPassword())) {
                networkUser.write(SocketMessageFactory.createLoginFailureResponse(LoginFailureReason.WRONG_PASSWORD, message.getSocketMessageID()));
                return;
            }

            networkUser.write(SocketMessageFactory.createAccessSuccessResponse(UserMapper.toDTO(userEntity), message.getSocketMessageID(), userEntity.getToken()));

            List<MessageDTO> userMessages = MessageMapper.toDTOList(Server.getMessageDAO().get(userEntity.getUsername()));
            networkUser.write(SocketMessageFactory.createMessagesSetNotification(userMessages));
        } else {
            networkUser.write(SocketMessageFactory.createLoginFailureResponse(LoginFailureReason.WRONG_USERNAME, message.getSocketMessageID()));
        }
    }

    /**
     * This function should handle a register request.
     *
     * @param message the parsed message
     * @param networkUser the user connection manager instance
     */
    private void handleRegisterRequest(SocketMessage message, NetworkUser networkUser) {
        if (LOGGER.isLoggable(Level.INFO))
            LOGGER.info(String.format("RECEIVED %s", message.toJson()));

        RegisterRequestDTO payload = (RegisterRequestDTO) message.getPayload();

        UserEntity userEntity = Server.getUserDAO().findUserByUsername(payload.getUsername());

        if (userEntity == null) {
            UserDTO userDTO = DomainDTOFactory.createUserDTO(payload.getUsername(), payload.getUserType());
            UserEntity user = UserMapper.toEntity(userDTO, payload.getPassword());
            networkUser.write(SocketMessageFactory.createAccessSuccessResponse(UserMapper.toDTO(user), message.getSocketMessageID(), user.getToken()));
            Server.getUserDAO().saveUser(user);

            MessageDTO messageDTO = DomainDTOFactory.createMessageDTO(
                    "info",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                    "register_welcome",
                    MessageType.INFO
            );

            networkUser.write(SocketMessageFactory.createMessageAddNotification(messageDTO));
            Server.getMessageDAO().save(MessageMapper.toEntity(messageDTO, user.getUsername()));
        } else {
            networkUser.write(SocketMessageFactory.createRegisterFailureResponse(RegisterFailureReason.USERNAME_ALREADY_TAKEN, message.getSocketMessageID()));
        }
    }

    /**
     * This function should handle a notification message.
     *
     * @param message the parsed message
     * @param networkUser the user connection manager instance
     */
    private void handleNotification(SocketMessage message, NetworkUser networkUser) {
        //TODO
    }
}
