package views.access;

import app.App;
import graphics_controllers.GraphicsController;
import graphics_controllers.access.AccessViewController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.UserModel;
import views.Page;
import views.View;
import views.ViewNavigator;

public class AccessView implements View, UserModel.Listener {

    @FXML
    private StackPane root;
    @FXML
    private VBox secondaryPageSlot;

    private final Page page = Page.ACCESS;
    private final GraphicsController<AccessView> graphicsController;

    public AccessView() {
        graphicsController = new AccessViewController(this);
        init();
    }

    @Override
    public void init() {
        App.getUser().addListener(this);
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void update() {
        //Not needed...
    }

    public GraphicsController<AccessView> getGraphicsController() {
        return graphicsController;
    }

    public void loadForm(Node node) {
        secondaryPageSlot.getChildren().clear();
        secondaryPageSlot.getChildren().add(node);
    }

    @Override
    public void onLoggedIn() {
        ViewNavigator.displayHomeView();
    }
}
