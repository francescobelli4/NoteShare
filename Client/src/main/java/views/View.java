package views;

import graphics_controllers.GraphicsController;
import javafx.scene.Parent;

public interface View {

    void update();
    void init();
    Parent getRoot();
    Page getPage();
    GraphicsController<? extends View> getGraphicsController();
}
