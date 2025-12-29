package app_controllers;

import app.AppContext;
import app.ArgsParser;
import communication.SocketMessageFactory;
import communication.dtos.responses.login.RegisterFailureReason;
import communication.dtos.user.UserStudentDTO;
import communication.dtos.user.UserType;
import exceptions.RegisterFailureException;
import mappers.UserMapper;
import models.user.UserModel;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import services.ServerCommunicationService;
import utils.Hashing;
import utils.Utils;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestRegisterController {

    @Test
    void register_Failure() throws IOException, ExecutionException, InterruptedException {

        assertEquals(RegisterFailureReason.USERNAME_TOO_SHORT, assertThrows(RegisterFailureException.class, () ->RegisterController.register("TMO", "TMOoooo", UserType.STUDENT)).getRegisterFailureReason());
        assertEquals(RegisterFailureReason.USERNAME_TOO_LONG, assertThrows(RegisterFailureException.class, () ->RegisterController.register("TMOoooooooooooooooooooooooooooooooo", "TMOoooo", UserType.STUDENT)).getRegisterFailureReason());
        assertEquals(RegisterFailureReason.PASSWORD_TOO_SHORT, assertThrows(RegisterFailureException.class, () ->RegisterController.register("TMOOOOOO", "TMO", UserType.STUDENT)).getRegisterFailureReason());
        assertEquals(RegisterFailureReason.PASSWORD_TOO_LONG, assertThrows(RegisterFailureException.class, () ->RegisterController.register("TMOOOOOO", "TMOoooooooooooooooooooooooooooooooooooooooooo", UserType.STUDENT)).getRegisterFailureReason());

        ServerCommunicationService mockedServerComm = mock(ServerCommunicationService.class);
        when(mockedServerComm.sendSync(any())).thenReturn(SocketMessageFactory.createRegisterFailureResponse(RegisterFailureReason.USERNAME_ALREADY_TAKEN, "abc"));

        try (MockedStatic<ServerCommunicationService> staticServerComm = mockStatic(ServerCommunicationService.class)) {

            staticServerComm.when(ServerCommunicationService::getInstance).thenReturn(mockedServerComm);

            assertEquals(RegisterFailureReason.USERNAME_ALREADY_TAKEN, assertThrows(RegisterFailureException.class, () ->RegisterController.register("TMOOOOOO", "TMOOOOOO", UserType.STUDENT)).getRegisterFailureReason());
        }
    }

    @Test
    void register_Successful() throws IOException, ExecutionException, InterruptedException {

        String[] args = {"demo", "en", "colored"};

        AppContext.getInstance().setOptions(ArgsParser.parseArgs(args));

        AppContext.getInstance().setCurrentUser(new UserModel("TMOOOOO", UserType.STUDENT));

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

            UserMapper.populateModel(AppContext.getInstance().getCurrentUser(), mockDTO);

            staticUtils.when(Utils::getMinUsernameLength).thenReturn(5);
            staticUtils.when(Utils::getMaxUsernameLength).thenReturn(20);
            staticUtils.when(Utils::getMinPasswordLength).thenReturn(5);
            staticUtils.when(Utils::getMaxPasswordLength).thenReturn(20);

            staticUtils.when(() -> Utils.saveAccessToken(anyString())).thenAnswer(i -> null);
            staticHashing.when(() -> Hashing.hashString(anyString())).thenReturn("hashedPass");

            RegisterController.register("TMOOOOO", "TMOOOOO", UserType.STUDENT);

            assertEquals("TMOOOOO", AppContext.getInstance().getCurrentUser().getUsername());
            assertTrue(AppContext.getInstance().getCurrentUser().isLoggedIn());
        }
    }
}