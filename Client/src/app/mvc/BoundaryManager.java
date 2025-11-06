package app.mvc;

import app.mvc.login.LoginBoundary;
import app.mvc.login.LoginController;
import app.mvc.managefolder.ManageFolderBoundary;
import app.mvc.managefolder.ManageFolderController;
import app.mvc.managenote.ManageNoteBoundary;
import app.mvc.managenote.ManageNoteController;
import app.mvc.navigation.NavigationBoundary;
import app.mvc.navigation.NavigationController;
import app.mvc.register.RegisterBoundary;
import app.mvc.register.RegisterController;

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
    private ManageFolderBoundary manageFolderBoundary;
    private ManageNoteBoundary manageNoteBoundary;

    public LoginBoundary getLoginBoundary() {
        return loginBoundary;
    }

    public NavigationBoundary getNavigationBoundary() {
        return navigationBoundary;
    }

    public RegisterBoundary getRegisterBoundary() {
        return registerBoundary;
    }

    public ManageFolderBoundary getManageFolderBoundary() {
        return manageFolderBoundary;
    }

    public ManageNoteBoundary getManageNoteBoundary() {
        return manageNoteBoundary;
    }

    public void initializeLoginBoundary() {
        LoginController loginController = new LoginController();
        loginBoundary = new LoginBoundary(loginController);
    }

    public void initializeRegisterBoundary() {
        RegisterController registerController = new RegisterController();
        registerBoundary = new RegisterBoundary(registerController);
    }

    public void initializeNavigationBoundary() {
        NavigationController navigationController = new NavigationController();
        navigationBoundary = new NavigationBoundary(navigationController);
    }

    public void initializeManageFolderBoundary() {
        ManageFolderController manageFolderController = new ManageFolderController();
        manageFolderBoundary = new ManageFolderBoundary(manageFolderController);
    }

    public void initializeManageNoteBoundary() {
        ManageNoteController manageNoteController = new ManageNoteController();
        manageNoteBoundary = new ManageNoteBoundary(manageNoteController);
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

    public void destroyManageFolderBoundary() {
        manageFolderBoundary.destroy();
        manageFolderBoundary = null;
    }

    public void destroyManageNoteBoundary() {
        manageNoteBoundary.destroy();
        manageNoteBoundary = null;
    }
}
