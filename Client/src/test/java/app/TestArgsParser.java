package app;

import exceptions.ArgsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestArgsParser {

    @Test
    void parseArgs_Successful() {
        String[] args = {"demo", "en", "colored"};

        Options options = ArgsParser.parseArgs(args);

        assertNotNull(options);
        assertEquals(Options.AppMode.DEMO, options.getAppMode());
        assertEquals(Options.Lang.EN, options.getLanguage());
        assertEquals(Options.UiType.COLORED, options.getUiType());
    }

    @Test
    void parseArgs_ArgsException() {
        String[] args = {"demo", "en"};
        assertThrows(ArgsException.class, () -> ArgsParser.parseArgs(args));
    }

    @Test
    void parseArgs_IllegalArgumentException() {
        String[] args = {"demooooo :D", "en", "colored"};
        assertThrows(ArgsException.class, () -> ArgsParser.parseArgs(args));
    }
}