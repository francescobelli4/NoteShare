package views.home.leftbar;

import graphics_controllers.GraphicsController;
import graphics_controllers.home.leftbar.LeftBarMenuOptionsViewController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import locales.Locales;
import views.Page;
import views.View;

public class LeftBarMenuOptionsView implements View {

    @FXML
    private VBox barContainer;
    @FXML
    private Button yourNotesButton;
    @FXML
    private Button browseNotesButton;
    @FXML
    private Button sharedNotesButton;

    private final Page page = Page.LEFT_BAR_MENU_OPTIONS;
    private final GraphicsController<LeftBarMenuOptionsView> graphicsController;

    public LeftBarMenuOptionsView() {
        graphicsController = new LeftBarMenuOptionsViewController(this);
        init();
    }

    @Override
    public void init() {
        yourNotesButton.setText(Locales.get("your_notes"));
        browseNotesButton.setText(Locales.get("browse_notes"));
        sharedNotesButton.setText(Locales.get("shared_notes"));
    }

    @Override
    public void update() {
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
    public GraphicsController<LeftBarMenuOptionsView> getGraphicsController() {
        return graphicsController;
    }

    public Button getYourNotesButton() {
        return yourNotesButton;
    }

    public Button getBrowseNotesButton() {
        return browseNotesButton;
    }

    public Button getSharedNotesButton() {
        return sharedNotesButton;
    }
}
