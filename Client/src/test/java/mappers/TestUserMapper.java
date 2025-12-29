package mappers;

import app.AppContext;
import communication.dtos.user.UserAdminDTO;
import communication.dtos.user.UserStudentDTO;
import communication.dtos.user.UserTeacherDTO;
import communication.dtos.user.UserType;
import daos.folder.NPFolderDAO;
import models.user.UserModel;
import models.user.roles.AdminRole;
import models.user.roles.StudentRole;
import models.user.roles.TeacherRole;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

class TestUserMapper {

    private UserStudentDTO genMockedUserStudentDTO() {
        UserStudentDTO user = mock(UserStudentDTO.class);
        when(user.getUsername()).thenReturn("TMO");
        when(user.getUserType()).thenReturn(UserType.STUDENT);
        when(user.getCoins()).thenReturn(100);

        return user;
    }

    private UserTeacherDTO genMockedTeacherStudentDTO() {
        UserTeacherDTO user = mock(UserTeacherDTO.class);
        when(user.getUsername()).thenReturn("guest");
        when(user.getUserType()).thenReturn(UserType.TEACHER);

        return user;
    }

    private UserAdminDTO genMockedAdminStudentDTO() {
        UserAdminDTO user = mock(UserAdminDTO.class);
        when(user.getUsername()).thenReturn("guest");
        when(user.getUserType()).thenReturn(UserType.ADMINISTRATOR);

        return user;
    }

    @Test
    void toModel_Student() {

        AppContext mockedAppContext = mock(AppContext.class);

        when(mockedAppContext.getFolderDAO()).thenReturn(new NPFolderDAO());

        try (MockedStatic<AppContext> staticAppContext = mockStatic(AppContext.class)) {

            staticAppContext.when(AppContext::getInstance).thenReturn(mockedAppContext);
            UserModel user = UserMapper.toModel(genMockedUserStudentDTO());

            assertInstanceOf(StudentRole.class, user.getRole());
            assertEquals("TMO", user.getUsername());
            assertEquals(UserType.STUDENT, user.getRole().getUserType());
            assertEquals(100, ((StudentRole)user.getRole()).getCoins());
        }
    }

    @Test
    void toModel_Teacher() {

        AppContext mockedAppContext = mock(AppContext.class);

        when(mockedAppContext.getFolderDAO()).thenReturn(new NPFolderDAO());

        try (MockedStatic<AppContext> staticAppContext = mockStatic(AppContext.class)) {

            staticAppContext.when(AppContext::getInstance).thenReturn(mockedAppContext);
            UserModel user = UserMapper.toModel(genMockedTeacherStudentDTO());

            assertInstanceOf(TeacherRole.class, user.getRole());
            assertEquals("guest", user.getUsername());
            assertEquals(UserType.TEACHER, user.getRole().getUserType());
        }
    }

    @Test
    void toModel_Admin() {

        AppContext mockedAppContext = mock(AppContext.class);
        when(mockedAppContext.getFolderDAO()).thenReturn(new NPFolderDAO());

        try (MockedStatic<AppContext> staticAppContext = mockStatic(AppContext.class)) {

            staticAppContext.when(AppContext::getInstance).thenReturn(mockedAppContext);
            UserModel user = UserMapper.toModel(genMockedAdminStudentDTO());

            assertInstanceOf(AdminRole.class, user.getRole());
            assertEquals("guest", user.getUsername());
            assertEquals(UserType.ADMINISTRATOR, user.getRole().getUserType());
        }
    }

    @Test
    void populateModel() {

        AppContext mockedAppContext = mock(AppContext.class);
        when(mockedAppContext.getFolderDAO()).thenReturn(new NPFolderDAO());

        try (MockedStatic<AppContext> staticAppContext = mockStatic(AppContext.class)) {

            staticAppContext.when(AppContext::getInstance).thenReturn(mockedAppContext);

            UserModel user = new UserModel();
            UserMapper.populateModel(user, genMockedUserStudentDTO());

            assertInstanceOf(StudentRole.class, user.getRole());
            assertEquals("TMO", user.getUsername());
            assertEquals(UserType.STUDENT, user.getRole().getUserType());
            assertEquals(100, ((StudentRole)user.getRole()).getCoins());
        }
    }
}