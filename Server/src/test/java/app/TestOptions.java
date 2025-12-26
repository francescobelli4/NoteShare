package app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestOptions {

    private Options options;

    @BeforeEach
    void setOptions() {
        options = new Options();
    }

    @Test
    void getAppMode() {
        assertEquals(Options.AppMode.DEMO, options.getAppMode());
    }

    @Test
    void setAppMode() {
        options.setAppMode(Options.AppMode.RELEASE);
        assertEquals(Options.AppMode.RELEASE, options.getAppMode());
    }

    @Test
    void appMode_FromIdentifier_Valid() {
        assertEquals(Options.AppMode.DEMO, Options.AppMode.fromIdentifier("demo"));
    }

    @Test
    void appMode_FromIdentifier_NotValid() {
        assertThrows(IllegalArgumentException.class, () -> Options.AppMode.fromIdentifier("demoooo"));
    }
}