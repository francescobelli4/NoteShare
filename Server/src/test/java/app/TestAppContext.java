package app;

import daos.message.NPMessageDAO;
import daos.user.NonPersistentUserDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestAppContext {

    @BeforeEach
    void setUp() {
        AppContext.reset();
    }

    @AfterEach
    void tearDown() {
        AppContext.reset();
    }

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

    @Test
    void getOptions_Null() {
        assertNull(AppContext.getInstance().getOptions());
    }

    @Test
    void getOptions_NotNull() {
        String[] args = {"demo"};

        AppContext instance = AppContext.getInstance();

        instance.setOptions(ArgsParser.parseArgs(args));

        assertNotNull(instance.getOptions());
    }

    @Test
    void setOptions_WrongNumberOfArgs() {
        String[] args = {"demo", "en"};
        AppContext instance = AppContext.getInstance();
        assertThrows(IllegalArgumentException.class, () -> instance.setOptions(ArgsParser.parseArgs(args)));
    }

    @Test
    void setOptions_WrongArg() {
        String[] args = {"foooooooo :D"};
        AppContext instance = AppContext.getInstance();
        assertThrows(IllegalArgumentException.class, () -> instance.setOptions(ArgsParser.parseArgs(args)));
    }

    @Test
    void setOptions_Successful() {
        String[] args = {"demo"};
        AppContext instance = AppContext.getInstance();

        instance.setOptions(ArgsParser.parseArgs(args));

        assertInstanceOf(NonPersistentUserDAO.class, instance.getUserDAO());
        assertInstanceOf(NPMessageDAO.class, instance.getMessageDAO());
    }

    @Test
    void getUserDAO_Null() {
        assertNull(AppContext.getInstance().getUserDAO());
    }

    @Test
    void getMessageDAO_Null() {
        assertNull(AppContext.getInstance().getMessageDAO());
    }
}