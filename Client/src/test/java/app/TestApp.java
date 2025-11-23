package app;

import exceptions.ArgsException;
import locales.Locales;
import org.junit.Assert;
import org.junit.Test;
import utils.Utils;

import java.io.File;


public class TestApp {
    @Test
    public void parseArgsSuccessFourArgs() {
        String[] args = {"demo", "en", "colored", "/test/path"};

        App.parseArgs(args);

        Assert.assertEquals(App.Options.AppMode.DEMO, App.Options.getAppMode());
        Assert.assertEquals(App.Options.Lang.EN, App.Options.getLanguage());
        Assert.assertEquals(App.Options.UiType.COLORED, App.Options.getUiType());
        Assert.assertEquals("/test/path", App.Options.getRootFolderPath());
    }

    @Test
    public void parseArgsSuccessThreeArgs() {
        App.Options.setRootFolderPath(Utils.getOSLocalPath());
        String[] args = {"demo", "en", "colored"};

        App.parseArgs(args);

        Assert.assertEquals(App.Options.AppMode.DEMO, App.Options.getAppMode());
        Assert.assertEquals(App.Options.Lang.EN, App.Options.getLanguage());
        Assert.assertEquals(App.Options.UiType.COLORED, App.Options.getUiType());
        Assert.assertEquals(Utils.getOSLocalPath(), App.Options.getRootFolderPath());
    }

    @Test
    public void parseArgsFailureTooFewArgs() {

        String[] args = {"demo", "en"};

        Assert.assertThrows(ArgsException.class, () -> App.parseArgs(args));
    }

    @Test
    public void parseArgsFailureInvalidLang() {
        String[] args = {"desffmo", "fr", "dadasa"};

        Assert.assertThrows(ArgsException.class, () -> App.parseArgs(args));
    }

    /*@Test
    public void testInitializeApp() {
        String[] args = {"demo", "en", "colored"};
        App.initializeApp(args);

        Assert.assertEquals(App.Options.AppMode.DEMO, App.Options.getAppMode());
        Assert.assertEquals(App.Options.Lang.EN, App.Options.getLanguage());
        Assert.assertEquals(App.Options.UiType.COLORED, App.Options.getUiType());
        Assert.assertEquals(Utils.getOSLocalPath(), App.Options.getRootFolderPath());


    }*/
}
