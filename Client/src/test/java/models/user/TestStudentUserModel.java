package models.user;

import app.AppContext;
import communication.dtos.user.UserType;
import daos.folder.NPFolderDAO;
import models.user.roles.StudentRole;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TestStudentUserModel {

    @Test
    void getCoins() {

        AppContext mockedAppContext = mock(AppContext.class);
        when(mockedAppContext.getFolderDAO()).thenReturn(new NPFolderDAO());

        try (MockedStatic<AppContext> staticAppContext = mockStatic(AppContext.class)) {

            staticAppContext.when(AppContext::getInstance).thenReturn(mockedAppContext);
            UserModel user = new UserModel("TMO", UserType.STUDENT);
            assertEquals(0, (((StudentRole)user.getRole()).getCoins()));
        }
    }

    @Test
    void setCoins() {
        AppContext mockedAppContext = mock(AppContext.class);
        when(mockedAppContext.getFolderDAO()).thenReturn(new NPFolderDAO());

        try (MockedStatic<AppContext> staticAppContext = mockStatic(AppContext.class)) {

            staticAppContext.when(AppContext::getInstance).thenReturn(mockedAppContext);
            UserModel user = new UserModel("TMO", UserType.STUDENT);
            user.as(StudentRole.class).ifPresent(student -> student.setCoins(100));
            assertEquals(100, (((StudentRole)user.getRole()).getCoins()));
        }
    }
}