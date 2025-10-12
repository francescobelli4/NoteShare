import user.User;

public class Main {


    public static void main() {

        User user = User.getInstance();

        user.connect("localhost", 12345);

        // Now start the Graphics Controller!
    }
}