package dto_factory;

import communication.user.*;

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
}
