package views.home.leftbar;

import graphics_controllers.GraphicsController;
import graphics_controllers.home.leftbar.LeftBarUserDataViewController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import locales.Locales;
import utils.Utils;
import views.Page;
import views.View;

public class LeftBarUserDataView implements View {

    @FXML
    private VBox root;
    @FXML
    private VBox userData;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label coinsLabel;


    @FXML
    private VBox accessForm;
    @FXML
    private Button accessButton;

    private static final Page page = Page.LEFT_BAR_USER_DATA;
    private final GraphicsController<LeftBarUserDataView> graphicsController;

    public LeftBarUserDataView() {
        graphicsController = new LeftBarUserDataViewController(this);
        init();
    }

    @Override
    public void init() {

        Utils.scaleFonts(root);
        accessButton.setText(Locales.get("access"));
    }

    @Override
    public void update() {
        //Not needed...
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
    public GraphicsController<LeftBarUserDataView> getGraphicsController() {
        return graphicsController;
    }

    public VBox getUserData() {
        return userData;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public Label getCoinsLabel() {
        return coinsLabel;
    }

    public VBox getAccessForm() {
        return accessForm;
    }

    public Button getAccessButton() {
        return accessButton;
    }
}
