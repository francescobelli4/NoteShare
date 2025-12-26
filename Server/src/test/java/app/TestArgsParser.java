package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestArgsParser {

    @Test
    void parseArgs_Successful() {
        String[] args = {"demo"};

        Options options = ArgsParser.parseArgs(args);

        assertNotNull(options);
        assertEquals(Options.AppMode.DEMO, options.getAppMode());
    }

    @Test
    void parseArgs_WrongNumberOfArgs() {
        String[] args = {"demo", "en"};
        assertThrows(IllegalArgumentException.class, () -> ArgsParser.parseArgs(args));
    }

    @Test
    void parseArgs_IllegalArgumentException() {
        String[] args = {"demooooo :D"};
        assertThrows(IllegalArgumentException.class, () -> ArgsParser.parseArgs(args));
    }
}