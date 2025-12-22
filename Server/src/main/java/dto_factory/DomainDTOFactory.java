package dto_factory;

import communication.dtos.message.MessageDTO;
import communication.dtos.message.MessageType;
import communication.dtos.user.*;

public class DomainDTOFactory {

    private DomainDTOFactory() {}

    public static UserDTO createUserDTO(String username, UserType userType) {
        return switch (userType) {
            case STUDENT -> {
                UserStudentDTO userStudentDTO = new UserStudentDTO();
                userStudentDTO.setUsername(username);
                userStudentDTO.setUserType(userType);
                userStudentDTO.setCoins(0);

                yield userStudentDTO;
            }
            case TEACHER -> new UserTeacherDTO();
            case ADMINISTRATOR -> new UserAdminDTO();
        };
    }

    public static MessageDTO createMessageDTO(String title, String date, String description, MessageType type) {

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTitle(title);
        messageDTO.setDate(date);
        messageDTO.setDescription(description);
        messageDTO.setType(type);

        return messageDTO;
    }
}
