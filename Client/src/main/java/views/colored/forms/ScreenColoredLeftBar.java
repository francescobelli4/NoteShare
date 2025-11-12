package views.colored.forms;

import app.App;
import app.mvc.models.UserModel;
import views.colored.Page;
import views.colored.PageController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import locales.Locales;
import views.colored.main_pages.ScreenColoredAccessPage;
import views.colored.main_pages.ScreenColoredHomePage;
import views.colored.main_pages.ScreenColoredMainPage;

/**
 * Class that represents the left bar for the student user type.
 */
public class ScreenColoredLeftBar extends ScreenColoredForm {

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
    @FXML
    VBox accessForm;
    @FXML
    VBox userData;
    @FXML
    Button accessButton;

    /**
     * Constructor with parent controller
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page
     * @param parentController the controller of the parent page
     */
    public ScreenColoredLeftBar(PageController parentController) {
        super(Page.STUDENT_HOME_PAGE_LEFT_BAR, parentController);

        this.loader.setController(this);
        this.root = App.getGraphicsController().loadFXMLLoader(loader);
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

        accessButton.setOnAction(_ -> displayAccessPage());

        accessForm.setManaged(!UserModel.getInstance().isLoggedIn());
        userData.setManaged(UserModel.getInstance().isLoggedIn());
        accessForm.setVisible(!UserModel.getInstance().isLoggedIn());
        userData.setVisible(UserModel.getInstance().isLoggedIn());
    }

    public void displayAccessPage() {
        ScreenColoredMainPage screenColoredAccessPage = new ScreenColoredAccessPage();
        screenColoredAccessPage.display();
    }

    /**
     * This function should close this page. It only needs to clear the parent's child slot
     */
    @Override
    public void close() {
        this.container.getChildren().clear();
    }
}
