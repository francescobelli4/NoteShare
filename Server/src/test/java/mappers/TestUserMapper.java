package mappers;

import communication.dtos.user.*;
import entities.user.UserAdminEntity;
import entities.user.UserEntity;
import entities.user.UserStudentEntity;
import entities.user.UserTeacherEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TestUserMapper {

    @Test
    void toDTO_Student() {

        UserStudentEntity user = new UserStudentEntity();
        user.setUserType(UserType.STUDENT);
        user.setUsername("Username");
        user.setToken("Token");
        user.setCoins(100);
        user.setPassword("Password");

        UserDTO userDTO = UserMapper.toDTO(user);

        assertInstanceOf(UserStudentDTO.class, userDTO);
        assertEquals("Username", userDTO.getUsername());
        assertEquals(100, ((UserStudentDTO)userDTO).getCoins());
        assertEquals(UserType.STUDENT, userDTO.getUserType());
    }

    @Test
    void toDTO_Teacher() {

        UserTeacherEntity user = new UserTeacherEntity();
        user.setUserType(UserType.TEACHER);
        user.setUsername("Username");
        user.setToken("Token");
        user.setPassword("Password");

        UserDTO userDTO = UserMapper.toDTO(user);

        assertInstanceOf(UserTeacherDTO.class, userDTO);
        assertEquals("Username", userDTO.getUsername());
        assertEquals(UserType.TEACHER, userDTO.getUserType());
    }

    @Test
    void toDTO_Admin() {
        UserAdminEntity user = new UserAdminEntity();
        user.setUserType(UserType.ADMINISTRATOR);
        user.setUsername("Username");
        user.setToken("Token");
        user.setPassword("Password");

        UserDTO userDTO = UserMapper.toDTO(user);

        assertInstanceOf(UserAdminDTO.class, userDTO);
        assertEquals("Username", userDTO.getUsername());
        assertEquals(UserType.ADMINISTRATOR, userDTO.getUserType());
    }

    @Test
    void toEntity_Student() {

        UserStudentDTO user = new UserStudentDTO();
        user.setUserType(UserType.STUDENT);
        user.setUsername("Username");
        user.setCoins(100);

        UserEntity userEntity = UserMapper.toEntity(user, "Password");

        assertInstanceOf(UserStudentEntity.class, userEntity);
        assertEquals("Username", userEntity.getUsername());
        assertEquals(100, ((UserStudentEntity)userEntity).getCoins());
        assertEquals("Password", userEntity.getPassword());
        assertEquals(UserType.STUDENT, userEntity.getUserType());
    }

    @Test
    void toEntity_Teacher() {

        UserTeacherDTO user = new UserTeacherDTO();
        user.setUserType(UserType.TEACHER);
        user.setUsername("Username");

        UserEntity userEntity = UserMapper.toEntity(user, "Password");

        assertInstanceOf(UserTeacherEntity.class, userEntity);
        assertEquals("Username", userEntity.getUsername());
        assertEquals("Password", userEntity.getPassword());
        assertEquals(UserType.TEACHER, userEntity.getUserType());
    }

    @Test
    void toEntity_Admin() {
        UserAdminDTO user = new UserAdminDTO();
        user.setUserType(UserType.ADMINISTRATOR);
        user.setUsername("Username");

        UserEntity userEntity = UserMapper.toEntity(user, "Password");

        assertInstanceOf(UserAdminEntity.class, userEntity);
        assertEquals("Username", userEntity.getUsername());
        assertEquals("Password", userEntity.getPassword());
        assertEquals(UserType.ADMINISTRATOR, userEntity.getUserType());
    }
}