package models.user;

import app.AppContext;
import communication.dtos.user.UserType;
import daos.folder.NPFolderDAO;
import models.folder.FolderModel;
import models.messages.MessageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestUserModel {

    private static UserModel user;

    @BeforeEach
    void setupUser() {

        AppContext mockedAppContext = mock(AppContext.class);
        when(mockedAppContext.getFolderDAO()).thenReturn(new NPFolderDAO());

        try (MockedStatic<AppContext> staticAppContext = mockStatic(AppContext.class)) {

            staticAppContext.when(AppContext::getInstance).thenReturn(mockedAppContext);
            user = new UserModel("TMO", UserType.STUDENT);
        }
    }

    @Test
    void addUserLoginListener() {
        UserModel.LoginListener listener = mock(UserModel.LoginListener.class);
        user.addUserLoginListener(listener);
        assertTrue(user.getLoginListeners().contains(listener));
    }

    @Test
    void removeUserLoginListener() {
        UserModel.LoginListener listener = mock(UserModel.LoginListener.class);
        user.addUserLoginListener(listener);
        user.removeUserLoginListener(listener);
        assertFalse(user.getLoginListeners().contains(listener));
    }

    @Test
    void addUserMessageListener() {
        UserModel.MessageListener listener = mock(UserModel.MessageListener.class);
        user.addUserMessageListener(listener);
        assertTrue(user.getMessageListeners().contains(listener));
    }

    @Test
    void removeUserMessageListener() {
        UserModel.MessageListener listener = mock(UserModel.MessageListener.class);
        user.addUserMessageListener(listener);
        user.removeUserMessageListener(listener);
        assertFalse(user.getMessageListeners().contains(listener));
    }

    @Test
    void addUserActiveFolderListener() {
        UserModel.ActiveFolderListener listener = mock(UserModel.ActiveFolderListener.class);
        user.addUserActiveFolderListener(listener);
        assertTrue(user.getActiveFolderListeners().contains(listener));
    }

    @Test
    void removeUserActiveFolderListener() {
        UserModel.ActiveFolderListener listener = mock(UserModel.ActiveFolderListener.class);
        user.addUserActiveFolderListener(listener);
        user.removeUserActiveFolderListener(listener);
        assertFalse(user.getActiveFolderListeners().contains(listener));
    }

    @Test
    void getUserType() {
        assertEquals(UserType.STUDENT, user.getRole().getUserType());
    }

    @Test
    void getUsername() {
        assertEquals("TMO", user.getUsername());
    }

    @Test
    void setUsername() {
        user.setUsername(":D");
        assertEquals(":D", user.getUsername());
    }

    @Test
    void isLoggedIn() {
        assertFalse(user.isLoggedIn());
    }

    @Test
    void setLoggedIn() {

        UserModel.LoginListener listener = mock(UserModel.LoginListener.class);
        user.addUserLoginListener(listener);

        AppContext mockedAppContext = mock(AppContext.class);
        when(mockedAppContext.getFolderDAO()).thenReturn(new NPFolderDAO());

        try (MockedStatic<AppContext> staticAppContext = mockStatic(AppContext.class)) {

            staticAppContext.when(AppContext::getInstance).thenReturn(mockedAppContext);
            user.setLoggedIn(true);
            assertTrue(user.isLoggedIn());

            for (UserModel.LoginListener l : user.getLoginListeners()) {
                verify(l, times(1)).onLoggedIn();
            }
        }
    }

    @Test
    void addMessage() {
        MessageModel mockedMessage = mock(MessageModel.class);
        UserModel.MessageListener listener = mock(UserModel.MessageListener.class);

        user.addUserMessageListener(listener);
        user.addMessage(mockedMessage);

        assertTrue(user.getMessages().contains(mockedMessage));

        for (UserModel.MessageListener l : user.getMessageListeners()) {
            verify(l, times(1)).onMessageAdded(mockedMessage);
        }
    }

    @Test
    void setMessages() {
        List<MessageModel> messages = new ArrayList<>();
        MessageModel mockedMessage = mock(MessageModel.class);
        UserModel.MessageListener listener = mock(UserModel.MessageListener.class);

        messages.add(mockedMessage);
        user.addUserMessageListener(listener);
        user.setMessages(messages);

        assertTrue(user.getMessages().contains(mockedMessage));

        for (UserModel.MessageListener l : user.getMessageListeners()) {
            verify(l, times(1)).onMessagesSet(messages);
        }
    }

    @Test
    void getActiveFolder() {
        assertNotNull(user.getActiveFolder());
        assertEquals("TMO", user.getActiveFolder().getName());
    }

    @Test
    void setActiveFolder() {
        FolderModel mockedFolder = mock(FolderModel.class);

        UserModel.ActiveFolderListener listener = mock(UserModel.ActiveFolderListener.class);
        user.addUserActiveFolderListener(listener);

        user.setActiveFolder(mockedFolder);

        assertEquals(mockedFolder, user.getActiveFolder());

        for (UserModel.ActiveFolderListener l : user.getActiveFolderListeners()) {
            verify(l, times(1)).activeFolderSet(mockedFolder);
        }
    }

    @Test
    void getRootFolder() {
        assertNotNull(user.getRootFolder());
        assertEquals("NoteShare", user.getRootFolder().getName());
    }

    @Test
    void setRootFolder() {
        FolderModel mockedFolder = mock(FolderModel.class);
        user.setRootFolder(mockedFolder);
        assertEquals(mockedFolder, user.getRootFolder());
    }

    @Test
    void setCopiedElement() {
        FolderModel f = new FolderModel(":D");
        user.setCopiedElement(f);
        assertEquals(f, user.getCopiedElement());
    }


}