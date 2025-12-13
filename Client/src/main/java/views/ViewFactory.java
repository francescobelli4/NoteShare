package views;

public class ViewFactory {

    private static ViewFactory instance;
    public static ViewFactory getInstance() {
        if (instance == null) {
            instance = new ViewFactory();
        }
        return instance;
    }
    private ViewFactory () {}

    public AccessView createAccessView() {
        return new AccessView();
    }

    public AccessFormView createAccessFormView() {
        return new AccessFormView();
    }

    public RegisterFormView createRegisterFormView() {
        return new RegisterFormView();
    }

    public LoginFormView createLoginFormView() {
        return new LoginFormView();
    }

    public HomeView createHomeView() {
        return new HomeView();
    }

    public NotificationView createNotificationView(String title, String description, Icon icon) {
        return new NotificationView(title, description, icon);
    }
}