import graphics.GraphicsController;
import locales.Locales;
import user.User;

public class Main {


    public static void main() {

        User user = User.getInstance();

        user.connect("localhost", 12345);

        Locales.initializeLocales();

        // Now start the Graphics Controller!
        GraphicsController.setup();
    }
}