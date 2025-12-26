package models.user;

import app.AppContext;
import communication.dtos.user.UserType;
import daos.folder.NPFolderDAO;
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
            StudentUserModel user = new StudentUserModel("TMO", UserType.STUDENT);
            assertEquals(0, user.getCoins());
        }
    }

    @Test
    void setCoins() {
        AppContext mockedAppContext = mock(AppContext.class);
        when(mockedAppContext.getFolderDAO()).thenReturn(new NPFolderDAO());

        try (MockedStatic<AppContext> staticAppContext = mockStatic(AppContext.class)) {

            staticAppContext.when(AppContext::getInstance).thenReturn(mockedAppContext);
            StudentUserModel user = new StudentUserModel("TMO", UserType.STUDENT);
            user.setCoins(100);
            assertEquals(100, user.getCoins());
        }
    }
}