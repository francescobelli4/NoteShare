package locales;

import app.App;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class TestLocales {

    @Test
    public void testInitialization() {

        try (MockedStatic<App.Options> mockedOptions = Mockito.mockStatic(App.Options.class)) {
            mockedOptions.when(App.Options::getLanguage).thenReturn(App.Options.Lang.EN);
            Locales.initializeLocales();
            assertNotNull(Locales.get("login"));
        }
    }

    @Test
    public void testLocalesEN() {

        try (MockedStatic<App.Options> mockedOptions = Mockito.mockStatic(App.Options.class)) {
            mockedOptions.when(App.Options::getLanguage).thenReturn(App.Options.Lang.EN);
            Locales.initializeLocales();
            assertEquals("Login", Locales.get("login"));
        }
    }

    @Test
    public void testLocalesIT() {
        try (MockedStatic<App.Options> mockedOptions = Mockito.mockStatic(App.Options.class)) {
            mockedOptions.when(App.Options::getLanguage).thenReturn(App.Options.Lang.IT);
            Locales.initializeLocales();
            assertEquals("Accedi", Locales.get("login"));
        }
    }

    @Test
    public void testLocalesNotFound() {
        try (MockedStatic<App.Options> mockedOptions = Mockito.mockStatic(App.Options.class)) {
            mockedOptions.when(App.Options::getLanguage).thenReturn(App.Options.Lang.EN);
            Locales.initializeLocales();
            assertNull(Locales.get("abcdef"));
        }
    }
}
