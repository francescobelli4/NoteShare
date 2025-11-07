package graphics.colored.controllers.forms;

import app.mvc.models.UserModel;
import graphics.GraphicsController;
import graphics.colored.Page;
import graphics.colored.controllers.PageController;
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
    Button your_notes_button;
    @FXML
    Button browse_notes_button;
    @FXML
    Button shared_notes_button;
    @FXML
    Label username_label;
    @FXML
    Label coins_label;
    @FXML
    VBox container;

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
        VBox.setVgrow(container, Priority.ALWAYS);

        your_notes_button.setText(Locales.get("your_notes"));
        browse_notes_button.setText(Locales.get("browse_notes"));
        shared_notes_button.setText(Locales.get("shared_notes"));

        username_label.setText(UserModel.getInstance().getUsername());
        coins_label.setText(UserModel.getInstance().getCoins() + "");
    }

    /**
     * This function should close this page. It only needs to clear the parent's child slot
     */
    @Override
    public void close() {
        this.container.getChildren().clear();
    }

    /**
     * This function should actually show this page.
     * @param container the container that contains this page
     */
    @Override
    public void display(VBox container) {
        super.display(container);
    }
}
