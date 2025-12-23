package app;

import exceptions.ArgsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Utils;

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
    void getLanguage() {
        assertEquals(Options.Lang.EN, options.getLanguage());
    }

    @Test
    void setLanguage() {
        options.setLanguage(Options.Lang.IT);
        assertEquals(Options.Lang.IT, options.getLanguage());
    }

    @Test
    void getUiType() {
        assertEquals(Options.UiType.COLORED, options.getUiType());
    }

    @Test
    void setUiType() {
        options.setUiType(Options.UiType.OLD);
        assertEquals(Options.UiType.OLD, options.getUiType());
    }

    @Test
    void getRootFolderPath() {
        assertEquals(Utils.getOSLocalPath(), options.getRootFolderPath());
    }

    @Test
    void appMode_FromIdentifier_Valid() {
        assertEquals(Options.AppMode.DEMO, Options.AppMode.fromIdentifier("demo"));
    }

    @Test
    void appMode_FromIdentifier_NotValid() {
        assertThrows(ArgsException.class, () -> Options.AppMode.fromIdentifier("demoooo"));
    }

    @Test
    void lang_FromIdentifier_Valid() {
        assertEquals(Options.Lang.EN, Options.Lang.fromIdentifier("en"));
    }

    @Test
    void lang_FromIdentifier_NotValid() {
        assertThrows(ArgsException.class, () -> Options.Lang.fromIdentifier("ennnnnn"));
    }

    @Test
    void uiType_FromIdentifier_Valid() {
        assertEquals(Options.UiType.COLORED, Options.UiType.fromIdentifier("colored"));
    }

    @Test
    void uiType_FromIdentifier_NotValid() {
        assertThrows(ArgsException.class, () -> Options.UiType.fromIdentifier("coloreddd"));
    }
}