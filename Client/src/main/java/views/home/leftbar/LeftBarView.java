package views.home.leftbar;

import graphics_controllers.GraphicsController;
import graphics_controllers.home.leftbar.LeftBarViewController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import utils.Utils;
import views.Page;
import views.View;
import views.ViewFactory;

public class LeftBarView implements View {

    @FXML
    private VBox barContainer;
    @FXML
    private VBox menuOptionsContainer;
    @FXML
    private VBox bottomContainer;

    private static final Page page = Page.LEFT_BAR;
    private final GraphicsController<LeftBarView> graphicsController;

    public LeftBarView() {
        graphicsController = new LeftBarViewController(this);
        init();
    }

    public void appendMenuOptions() {
        LeftBarMenuOptionsView menuOptionsView = ViewFactory.getInstance().createLeftBarMenuOptionsView();
        menuOptionsContainer.getChildren().clear();
        menuOptionsContainer.getChildren().add(menuOptionsView.getRoot());
    }

    public void appendUserData() {
        LeftBarUserDataView leftBarUserDataView = ViewFactory.getInstance().createLeftBarUserDataView();
        bottomContainer.getChildren().clear();
        bottomContainer.getChildren().add(leftBarUserDataView.getRoot());
    }

    @Override
    public void init() {
        Utils.scaleFonts(barContainer);

        VBox.setVgrow(barContainer, Priority.ALWAYS);
    }

    @Override
    public void close() {
        //Nothing to do...
    }

    @Override
    public Parent getRoot() {
        return barContainer;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public GraphicsController<LeftBarView> getGraphicsController() {
        return graphicsController;
    }
}
