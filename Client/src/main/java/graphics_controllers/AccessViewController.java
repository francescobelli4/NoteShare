package graphics_controllers;

import views.*;

public class AccessViewController extends GraphicsController<AccessView> implements AccessFormViewController.Listener, RegisterFormViewController.Listener {

    public AccessViewController(View view) {
        super(view);
    }

    @Override
    public void setup() {
        loadDefaultContent();
    }

    private void loadDefaultContent() {
        AccessFormView accessFormView = ViewFactory.getInstance().createAccessFormView();
        AccessFormViewController accessFormViewController = (AccessFormViewController) accessFormView.getGraphicsController();
        accessFormViewController.setListener(this);
        getView().setContent(accessFormView.getRoot());
    }

    private void loadRegisterContent() {
        RegisterFormView registerFormView = ViewFactory.getInstance().createRegisterFormView();
        RegisterFormViewController registerFormViewController = (RegisterFormViewController) registerFormView.getGraphicsController();
        registerFormViewController.setListener(this);
        getView().setContent(registerFormView.getRoot());
    }

    private void loadLoginContent() {

    }

    @Override
    public void onRegisterButtonClicked() {
        loadRegisterContent();
    }

    @Override
    public void onLoginButtonClicked() {
        loadLoginContent();
    }

    @Override
    public void backButtonClicked() {
        loadDefaultContent();
    }
}
