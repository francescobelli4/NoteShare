package views.colored.forms;

import app.mvc.models.UserModel;
import views.GraphicsController;
import views.colored.Page;
import views.colored.PageController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import locales.Locales;

/**
 * Class that represents the left bar for the student user type.
 */
public class ScreenColoredStudentLeftBar extends ScreenColoredForm {

    /**
     * FXML elements
     */
    @FXML
    Button yourNotesButton;
    @FXML
    Button browseNotesButton;
    @FXML
    Button sharedNotesButton;
    @FXML
    Label usernameLabel;
    @FXML
    Label coinsLabel;
    @FXML
    VBox barContainer;

    /**
     * Constructor with parent controller
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page
     * @param parentController the controller of the parent page
     */
    public ScreenColoredStudentLeftBar(PageController parentController) {
        super(Page.STUDENT_HOME_PAGE_LEFT_BAR, parentController);

        this.loader.setController(this);
        this.root = GraphicsController.getInstance().loadFXMLLoader(loader);
    }

    /**
     * In the initialize function, I set all the labels.
     */
    @FXML
    public void initialize() {
        VBox.setVgrow(barContainer, Priority.ALWAYS);

        yourNotesButton.setText(Locales.get("your_notes"));
        browseNotesButton.setText(Locales.get("browse_notes"));
        sharedNotesButton.setText(Locales.get("shared_notes"));

        usernameLabel.setText(UserModel.getInstance().getUsername());
        coinsLabel.setText(UserModel.getInstance().getCoins() + "");
    }

    /**
     * This function should close this page. It only needs to clear the parent's child slot
     */
    @Override
    public void close() {
        this.container.getChildren().clear();
    }
}
