package views;

import graphics_controllers.GraphicsController;
import javafx.scene.Parent;

public interface View {

    void update();
    void init();
    Parent getRoot();
    Page getPage();
    // SonarCloud finds a smell here for the generic type, but actually this is needed
    // for the architecture.
    @SuppressWarnings("java:S1452")
    GraphicsController<? extends View> getGraphicsController();
}
