package graphics_controllers;

import views.AccessFormView;
import views.View;

public class AccessFormViewController extends GraphicsController<AccessFormView> {

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public AccessFormViewController(View view) {
        super(view);
    }

    @Override
    public void setup() {
        getView().registerButton.setOnMouseClicked(_ -> registerButtonClicked());
        getView().loginButton.setOnMouseClicked(_ -> loginButtonClicked());
    }

    private void registerButtonClicked() {
        listener.onRegisterButtonClicked();
    }

    private void loginButtonClicked() {
        listener.onLoginButtonClicked();
    }

    public interface Listener {

        void onRegisterButtonClicked();
        void onLoginButtonClicked();
    }
}
