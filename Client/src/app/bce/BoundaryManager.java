package app.bce;

import app.bce.login.LoginBoundary;
import app.bce.login.LoginController;
import app.bce.navigation.NavigationBoundary;
import app.bce.navigation.NavigationController;
import app.bce.register.RegisterBoundary;
import app.bce.register.RegisterController;

/**
 * Singleton
 */
public class BoundaryManager {

    private static BoundaryManager instance;
    private BoundaryManager() { }
    public static BoundaryManager getInstance() {
        if (instance == null) {
            instance = new BoundaryManager();
        }
        return instance;
    }

    private LoginBoundary loginBoundary;
    private RegisterBoundary registerBoundary;
    private NavigationBoundary navigationBoundary;

    public LoginBoundary getLoginBoundary() {
        return loginBoundary;
    }

    public NavigationBoundary getNavigationBoundary() {
        return navigationBoundary;
    }

    public RegisterBoundary getRegisterBoundary() {
        System.out.println("GETTTTTTT");
        return registerBoundary;
    }

    public void initializeLoginBoundary() {
        LoginController loginController = new LoginController();
        loginBoundary = new LoginBoundary(loginController);
    }

    public void initializeRegisterBoundary() {
        System.out.println("SETTTTTTTTT");
        RegisterController registerController = new RegisterController();
        registerBoundary = new RegisterBoundary(registerController);
    }

    public void initializeNavigationBoundary() {
        NavigationController navigationController = new NavigationController();
        navigationBoundary = new NavigationBoundary(navigationController);
    }

    public void destroyLoginBoundary() {
        loginBoundary.destroy();
        loginBoundary = null;
    }

    public void destroyRegisterBoundary() {
        registerBoundary.destroy();
        registerBoundary = null;
    }

    public void destroyNavigationBoundary() {
        navigationBoundary.destroy();
        navigationBoundary = null;
    }
}
