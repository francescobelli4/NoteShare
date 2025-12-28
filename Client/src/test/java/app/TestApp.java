package app;

import models.user.StudentUserModel;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import services.ServerCommunicationService;
import utils.PDFToImage;
import utils.Utils;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TestApp {

    /**
     * Testing successful case. Unsuccessful cases will be tested in other test classes.
     * Actually this test should only check if the right operations are launched and if
     * the user directories are created.
     * This test should not "see if the connection with the server is ok" or if "the options
     * are properly set". These checks are done in other tests in the right classes!
     */
    @Test
    void initializeApp_Successful() {

        String[] args = {"demo", "en", "colored"};

        ServerCommunicationService mockedServer = mock(ServerCommunicationService.class);

        try (
                MockedStatic<ServerCommunicationService> serverStatic = mockStatic(ServerCommunicationService.class);
                MockedStatic<Launcher> launcherStatic = mockStatic(Launcher.class);
                MockedStatic<Utils> utilsStatic = mockStatic(Utils.class);
                MockedStatic<PDFToImage> pdfToImageStatic = mockStatic(PDFToImage.class);
        ) {

            serverStatic.when(ServerCommunicationService::getInstance).thenReturn(mockedServer);
            pdfToImageStatic.when(PDFToImage::initialize).thenAnswer(_ -> null);

            utilsStatic.when(Utils::getOSLocalPath).thenReturn("/tmp/test");

            App.initializeApp(args);

            assertNotNull(AppContext.getInstance().getOptions());
            launcherStatic.verify(Launcher::launchApp);
            // I don't want folders to be created because of tests: this is a mocked folder creation!
            // I just want to see if the right function is called
            utilsStatic.verify(() -> Utils.createDir(anyString()));
            pdfToImageStatic.verify(PDFToImage::initialize);
            assertNotNull(AppContext.getInstance().getCurrentUser());
            assertInstanceOf(StudentUserModel.class, AppContext.getInstance().getCurrentUser());
        }
    }
}