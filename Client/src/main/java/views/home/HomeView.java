package views.home;

import graphics_controllers.GraphicsController;
import graphics_controllers.home.HomeViewController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utils.Utils;
import views.Page;
import views.View;

public class HomeView implements View {

    @FXML
    private StackPane root;
    @FXML
    private Button messagesButton;
    @FXML
    private TextField searchBar;
    @FXML
    private VBox leftBarSlot;
    @FXML
    private VBox toolsSlot;
    @FXML
    private VBox foldersContainer;


    private static final Page page = Page.HOME_PAGE;
    private final GraphicsController<HomeView> graphicsController;

    public HomeView() {
        graphicsController = new HomeViewController(this);
        init();
    }

    public void appendLeftBar(Node leftBar) {
        leftBarSlot.getChildren().clear();
        leftBarSlot.getChildren().add(leftBar);
    }

    public void appendToolsBar(Node toolsBar) {
        toolsSlot.getChildren().clear();
        toolsSlot.getChildren().add(toolsBar);
    }

    public void appendMessagesContainer(Node messagesContainer) {
        root.getChildren().add(messagesContainer);
    }

    @Override
    public void init() {
        Utils.scaleFonts(root);
    }

    @Override
    public void close() {
        //Nothing to do...
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
    public GraphicsController<HomeView> getGraphicsController() {
        return graphicsController;
    }

    public Button getMessagesButton() {
        return messagesButton;
    }

    public TextField getSearchBar() {
        return searchBar;
    }

    public VBox getLeftBarSlot() {
        return leftBarSlot;
    }

    public VBox getFoldersContainer() {
        return foldersContainer;
    }

    public VBox getToolsSlot() {
        return toolsSlot;
    }
}
