package app_controllers;

import app.AppContext;
import app.ArgsParser;
import communication.SocketMessage;
import communication.SocketMessageFactory;
import communication.dtos.responses.login.LoginFailureReason;
import communication.dtos.user.UserDTO;
import communication.dtos.user.UserStudentDTO;
import communication.dtos.user.UserType;
import exceptions.LoginFailureException;
import mappers.UserMapper;
import models.user.StudentUserModel;
import models.user.UserModel;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import services.ServerCommunicationService;
import utils.Hashing;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TestLoginController {

    @Test
    void login_Fail() throws IOException, ExecutionException, InterruptedException {
        assertEquals(LoginFailureReason.USERNAME_TOO_SHORT, assertThrows(LoginFailureException.class, () ->LoginController.login("TMO", "TMOoooo")).getLoginFailureReason());
        assertEquals(LoginFailureReason.USERNAME_TOO_LONG, assertThrows(LoginFailureException.class, () ->LoginController.login("TMOoooooooooooooooooooooooooooooooo", "TMOoooo")).getLoginFailureReason());
        assertEquals(LoginFailureReason.PASSWORD_TOO_SHORT, assertThrows(LoginFailureException.class, () ->LoginController.login("TMOOOOOO", "TMO")).getLoginFailureReason());
        assertEquals(LoginFailureReason.PASSWORD_TOO_LONG, assertThrows(LoginFailureException.class, () ->LoginController.login("TMOOOOOO", "TMOoooooooooooooooooooooooooooooooooooooooooo")).getLoginFailureReason());

        ServerCommunicationService mockedServerComm = mock(ServerCommunicationService.class);
        when(mockedServerComm.sendSync(any())).thenReturn(SocketMessageFactory.createLoginFailureResponse(LoginFailureReason.WRONG_PASSWORD, "abc"));

        try (MockedStatic<ServerCommunicationService> staticServerComm = mockStatic(ServerCommunicationService.class)) {

            staticServerComm.when(ServerCommunicationService::getInstance).thenReturn(mockedServerComm);

            assertEquals(LoginFailureReason.WRONG_PASSWORD, assertThrows(LoginFailureException.class, () ->LoginController.login("TMOOOOOO", "TMOOOOOO")).getLoginFailureReason());

            when(mockedServerComm.sendSync(any())).thenReturn(SocketMessageFactory.createLoginFailureResponse(LoginFailureReason.WRONG_USERNAME, "abc"));

            assertEquals(LoginFailureReason.WRONG_USERNAME, assertThrows(LoginFailureException.class, () ->LoginController.login("TMOOOOOO", "TMOOOOOO")).getLoginFailureReason());
        }
    }

    @Test
    void login_Successful() throws IOException, ExecutionException, InterruptedException {

        String[] args = {"demo", "en", "colored"};

        AppContext.getInstance().setOptions(ArgsParser.parseArgs(args));

        AppContext.getInstance().setCurrentUser(new StudentUserModel("TMOOOOO", UserType.STUDENT));

        ServerCommunicationService mockedServerComm = mock(ServerCommunicationService.class);
        UserStudentDTO mockDTO = new UserStudentDTO();
        mockDTO.setUsername("TMOOOOO");
        mockDTO.setUserType(UserType.STUDENT);
        mockDTO.setCoins(100);

        when(mockedServerComm.sendSync(any())).thenReturn(SocketMessageFactory.createAccessSuccessResponse(mockDTO, "abc", "123"));

        try (
                MockedStatic<ServerCommunicationService> staticServerComm = mockStatic(ServerCommunicationService.class);
                MockedStatic<UserMapper> staticUserMapper = mockStatic(UserMapper.class);
                MockedStatic<Utils> staticUtils = mockStatic(Utils.class);
                MockedStatic<Hashing> staticHashing = mockStatic(Hashing.class)
        ) {

            staticServerComm.when(ServerCommunicationService::getInstance).thenReturn(mockedServerComm);

            StudentUserModel finalUser = new StudentUserModel("TMOOOOO", UserType.STUDENT);
            staticUserMapper.when(() -> UserMapper.populateModel(any(UserModel.class), any(UserDTO.class))).thenReturn(finalUser);

            staticUtils.when(Utils::getMinUsernameLength).thenReturn(5);
            staticUtils.when(Utils::getMaxUsernameLength).thenReturn(20);
            staticUtils.when(Utils::getMinPasswordLength).thenReturn(5);
            staticUtils.when(Utils::getMaxPasswordLength).thenReturn(20);

            staticUtils.when(() -> Utils.saveAccessToken(anyString())).thenAnswer(i -> null);
            staticHashing.when(() -> Hashing.hashString(anyString())).thenReturn("hashedPass");

            LoginController.login("TMOOOOO", "TMOOOOO");

            assertEquals("TMOOOOO", AppContext.getInstance().getCurrentUser().getUsername());
            assertTrue(AppContext.getInstance().getCurrentUser().isLoggedIn());
        }
    }

    @Test
    void loginUsingToken_NoTokenFile() {
        AppContext.reset();

        String[] args = {"demo", "en", "colored"};
        AppContext.getInstance().setOptions(ArgsParser.parseArgs(args));

        AppContext.getInstance().setCurrentUser(new StudentUserModel("TMOOOOO", UserType.STUDENT));

        try (MockedStatic<Utils> staticUtils = mockStatic(Utils.class)) {

            staticUtils.when(Utils::getOSLocalPath).thenReturn("testPath/");

            staticUtils.when(() -> Utils.findFile(anyString())).thenReturn(null);
            LoginController.loginUsingToken();
            assertFalse(AppContext.getInstance().getCurrentUser().isLoggedIn());
        }
    }

    @Test
    void loginUsingToken_Success() throws IOException, ExecutionException, InterruptedException {
        AppContext.reset();

        String[] args = {"demo", "en", "colored"};
        AppContext.getInstance().setOptions(ArgsParser.parseArgs(args));
        AppContext.getInstance().setCurrentUser(new StudentUserModel("TMOOOOO", UserType.STUDENT));

        ServerCommunicationService mockService = mock(ServerCommunicationService.class);

        UserStudentDTO userDTO = new UserStudentDTO();
        userDTO.setUsername("TMOOOOOO");
        SocketMessage successResponse = SocketMessageFactory.createAccessSuccessResponse(userDTO, "abc", "123");

        when(mockService.sendSync(any())).thenReturn(successResponse);

        try (
                MockedStatic<Utils> staticUtils = mockStatic(Utils.class);
                MockedStatic<ServerCommunicationService> staticServer = mockStatic(ServerCommunicationService.class);
                MockedStatic<UserMapper> staticMapper = mockStatic(UserMapper.class)
        ) {

            staticServer.when(ServerCommunicationService::getInstance).thenReturn(mockService);
            staticUtils.when(Utils::getOSLocalPath).thenReturn("testPath/");

            File mockFile = mock(File.class);
            staticUtils.when(() -> Utils.findFile(anyString())).thenReturn(mockFile);
            staticUtils.when(() -> Utils.readFile(mockFile)).thenReturn("123");

            StudentUserModel loggedUser = new StudentUserModel("TMOOOOOO", UserType.STUDENT);
            staticMapper.when(() -> UserMapper.populateModel(any(UserModel.class), any())).thenReturn(loggedUser);

            LoginController.loginUsingToken();
            assertEquals("TMOOOOOO", AppContext.getInstance().getCurrentUser().getUsername());
            assertTrue(AppContext.getInstance().getCurrentUser().isLoggedIn());
        }
    }
}