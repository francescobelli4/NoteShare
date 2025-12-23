package app;

import daos.folder.NPFolderDAO;
import daos.folder.PFolderDAO;
import daos.note.NPNoteDAO;
import daos.note.PNoteDAO;
import locales.Locales;
import models.user.StudentUserModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TestAppContext {

    @BeforeEach
    void setUp() {
        AppContext.reset();
    }

    @AfterEach
    void tearDown() {
        AppContext.reset();
    }

    /**
     * Testing if an instance is correctly delivered and also the difference
     * between two instances when the first one is reset.
     * Also testing that, without a reset, two instances are the same.
     */
    @Test
    void getInstance() {

        AppContext instance = AppContext.getInstance();
        assertNotNull(instance);

        AppContext.reset();

        AppContext newInstance = AppContext.getInstance();
        assertNotNull(newInstance);
        assertNotEquals(instance, newInstance);

        AppContext newInstance2 = AppContext.getInstance();
        assertEquals(newInstance, newInstance2);
    }

    /**
     * Options should be null by default
     */
    @Test
    void getOptions_Null() {
        assertNull(AppContext.getInstance().getOptions());
    }

    /**
     * Testing if not null options are correctly delivered
     */
    @Test
    void getOptions_NotNull() {
        String[] args = {"demo", "en", "colored"};

        AppContext instance = AppContext.getInstance();

        instance.setOptions(ArgsParser.parseArgs(args));

        assertNotNull(instance.getOptions());
    }

    /**
     * Testing if context is correctly set.
     */
    @Test
    void setOptions_SuccessfulDEMOEN() {

        String[] args = {"demo", "en", "colored"};

        AppContext instance = AppContext.getInstance();

        instance.setOptions(ArgsParser.parseArgs(args));
        assertNotNull(instance.getOptions());

        assertInstanceOf(NPFolderDAO.class, instance.getFolderDAO());
        assertInstanceOf(NPNoteDAO.class, instance.getNoteDAO());

        assertEquals("Register", Locales.get("register"));
    }

    /**
     * Testing if context is correctly set.
     */
    @Test
    void setOptions_SuccessfulRELEASEIT() {

        String[] args = {"release", "it", "colored"};

        AppContext instance = AppContext.getInstance();

        instance.setOptions(ArgsParser.parseArgs(args));
        assertNotNull(instance.getOptions());

        assertInstanceOf(PFolderDAO.class, instance.getFolderDAO());
        assertInstanceOf(PNoteDAO.class, instance.getNoteDAO());

        assertEquals("Registrati", Locales.get("register"));
    }

    @Test
    void getCurrentUser_Null() {
        assertNull(AppContext.getInstance().getCurrentUser());
    }

    @Test
    void getCurrentUser_NotNull() {

        AppContext instance = AppContext.getInstance();
        StudentUserModel mockedUser = mock(StudentUserModel.class);
        instance.setCurrentUser(mockedUser);
        assertNotNull(instance.getCurrentUser());
    }

    @Test
    void setCurrentUser() {

        AppContext instance = AppContext.getInstance();
        StudentUserModel mockedUser = mock(StudentUserModel.class);
        instance.setCurrentUser(mockedUser);
        assertNotNull(instance.getCurrentUser());
    }

    @Test
    void getFolderDAO_Null() {
        assertNull(AppContext.getInstance().getFolderDAO());
    }


    @Test
    void getFolderDAO_NotNull() {
        String[] args = {"demo", "en", "colored"};

        AppContext instance = AppContext.getInstance();

        instance.setOptions(ArgsParser.parseArgs(args));
        assertNotNull(instance.getFolderDAO());
    }

    @Test
    void getNoteDAO_Null() {
        assertNull(AppContext.getInstance().getNoteDAO());
    }


    @Test
    void getNoteDAO_NotNull() {
        String[] args = {"demo", "en", "colored"};

        AppContext instance = AppContext.getInstance();

        instance.setOptions(ArgsParser.parseArgs(args));
        assertNotNull(instance.getNoteDAO());
    }
}