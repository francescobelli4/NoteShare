package locales;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TestLocales {

    @BeforeEach
    @AfterEach
    void reset() {
        Locales.reset();
    }

    @Test
    void labels_not_initialized() {
        assertNull(Locales.get("register"));
    }

    @Test
    void initializeENLocales() {
        Locales.initializeENLocales();
        assertEquals("Register", Locales.get("register"));
    }

    @Test
    void initializeITLocales() {
        Locales.initializeITLocales();
        assertEquals("Registrati", Locales.get("register"));
    }

    @Test
    void get_Null() {
        Locales.initializeENLocales();
        assertNull(Locales.get("ITopiNonAvevanoNipoti"));
    }

    @Test
    void switchLanguage() {

        Locales.initializeITLocales();
        assertEquals("Registrati", Locales.get("register"));

        Locales.initializeENLocales();
        assertEquals("Register", Locales.get("register"));
    }

    @Test
    void sameLocales() {
        Locales.initializeENLocales();
        Set<String> enKeys = Set.copyOf(Locales.getLocalesLabels().keySet());

        Locales.reset();
        Locales.initializeITLocales();

        for (String key : enKeys) {
            assertTrue(Locales.getLocalesLabels().containsKey(key));
        }
    }
}