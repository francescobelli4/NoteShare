package mappers;

import app.AppContext;
import communication.dtos.user.UserAdminDTO;
import communication.dtos.user.UserStudentDTO;
import communication.dtos.user.UserTeacherDTO;
import communication.dtos.user.UserType;
import daos.folder.NPFolderDAO;
import models.user.AdminUserModel;
import models.user.StudentUserModel;
import models.user.TeacherUserModel;
import models.user.UserModel;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
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
        when(user.getUserType()).thenReturn(UserType.TEACHER);

        return user;
    }

    private UserAdminDTO genMockedAdminStudentDTO() {
        UserAdminDTO user = mock(UserAdminDTO.class);
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

            assertInstanceOf(StudentUserModel.class, user);
            assertEquals("TMO", user.getUsername());
            assertEquals(UserType.STUDENT, user.getUserType());
            assertEquals(100, ((StudentUserModel)user).getCoins());
        }
    }

    @Test
    void toModel_Teacher() {

        AppContext mockedAppContext = mock(AppContext.class);

        when(mockedAppContext.getFolderDAO()).thenReturn(new NPFolderDAO());

        try (MockedStatic<AppContext> staticAppContext = mockStatic(AppContext.class)) {

            staticAppContext.when(AppContext::getInstance).thenReturn(mockedAppContext);
            UserModel user = UserMapper.toModel(genMockedTeacherStudentDTO());

            assertInstanceOf(TeacherUserModel.class, user);
            assertEquals("guest", user.getUsername());
            assertEquals(UserType.TEACHER, user.getUserType());
        }
    }

    @Test
    void toModel_Admin() {

        AppContext mockedAppContext = mock(AppContext.class);
        when(mockedAppContext.getFolderDAO()).thenReturn(new NPFolderDAO());

        try (MockedStatic<AppContext> staticAppContext = mockStatic(AppContext.class)) {

            staticAppContext.when(AppContext::getInstance).thenReturn(mockedAppContext);
            UserModel user = UserMapper.toModel(genMockedAdminStudentDTO());

            assertInstanceOf(AdminUserModel.class, user);
            assertEquals("guest", user.getUsername());
            assertEquals(UserType.ADMINISTRATOR, user.getUserType());
        }
    }

    @Test
    void populateModel() {

        AppContext mockedAppContext = mock(AppContext.class);
        UserModel mockedUser = mock(UserModel.class);

        when(mockedUser.getUsername()).thenReturn("TMO");
        when(mockedUser.getUserType()).thenReturn(UserType.STUDENT);

        when(mockedAppContext.getFolderDAO()).thenReturn(new NPFolderDAO());

        try (MockedStatic<AppContext> staticAppContext = mockStatic(AppContext.class)) {

            staticAppContext.when(AppContext::getInstance).thenReturn(mockedAppContext);

            UserModel newModel = UserMapper.populateModel(mockedUser, genMockedUserStudentDTO());

            assertInstanceOf(StudentUserModel.class, newModel);
            assertEquals("TMO", newModel.getUsername());
            assertEquals(UserType.STUDENT, newModel.getUserType());
            assertEquals(100, ((StudentUserModel)newModel).getCoins());
        }
    }
}