package views.colored.main_pages;

import app.App;
import app.mvc.BoundaryManager;
import app.mvc.models.MessageModel;
import app.mvc.models.UserModel;
import app.mvc.viewmessages.ViewMessagesBoundary;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import locales.Locales;
import views.GraphicsController;
import views.colored.Page;
import views.colored.forms.*;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import views.colored.notifications.ScreenColoredGenericNotification;

/**
 * Class that represents the home page.
 *
 * A home page is a main page and it's elements depend on the user type (student, teacher, administrator).
 *
 * A home page has 3 slots for children pages:
 * -    leftBarSlot:        ScreenColored[Student/Teacher/Administrator]LeftBar
 * -    toolsSlot:          ScreenColored[Student/Teacher/Administrator]ToolsBar
 * -    foldersContainer:   ScreenColoredFoldersContainer
 *
 * A home page also supports a ScreenColoredMessagesContainer.
 */
public class ScreenColoredHomePage extends ScreenColoredMainPage implements ViewMessagesBoundary.Listener {

    /** The slot for left bar secondary page */
    @FXML
    VBox leftBarSlot;
    /** The slot for tools bar secondary page */
    @FXML
    VBox toolsSlot;
    /** The slot for folders container */
    @FXML
    VBox foldersContainer;
    /** The button used to open the messages form */
    @FXML
    Button messagesButton;

    /** The controller of the active left bar */
    ScreenColoredStudentLeftBar studentLeftBarController;

    /** The controller of the active tools bar */
    ScreenColoredStudentToolsBar studentToolsBarController;

    /** The controller of the folders container */
    ScreenColoredFoldersContainer foldersContainerController;

    /** The controller of the messages container */
    ScreenColoredMessagesContainer messagesContainerController;

    /**
     * Base constructor
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page
     */
    public ScreenColoredHomePage() {
        super(Page.HOME_PAGE);

        BoundaryManager.getInstance().getViewMessagesBoundary().addListener(this);

        this.loader.setController(this);
        this.root = App.getGraphicsController().loadFXMLLoader(loader);
    }

    /**
     * This function should display this page. When the page is displayed, it should automatically
     * append both the tools bar and the left bar.
     */
    @Override
    public void display() {
        super.display();

        appendFoldersContainer();
        appendToolsBar();
        appendLeftBar();

        messagesButton.setOnMouseClicked(e -> openMessagesForm());
    }

    /**
     * This function should append the tools bar to the toolsSlot
     */
    private void appendToolsBar() {

        switch (UserModel.getInstance().getUserType()) {
            case "student":
                studentToolsBarController = new ScreenColoredStudentToolsBar(this);
                studentToolsBarController.display(toolsSlot);
                break;
            case "teacher":
                break;
            case "administrator":
                break;
            default:
                break;

        }
    }

    /**
     * This function should append the left bar to the leftBarSlot
     */
    private void appendLeftBar() {
        switch (UserModel.getInstance().getUserType()) {
            case "student":
                studentLeftBarController = new ScreenColoredStudentLeftBar(this);
                studentLeftBarController.display(leftBarSlot);
                break;
            case "teacher":
                break;
            case "administrator":
                break;
            default:
                break;

        }
    }

    private void appendFoldersContainer() {
        foldersContainerController = new ScreenColoredFoldersContainer(this);
        foldersContainerController.display(foldersContainer);
    }

    private void openMessagesForm() {
        Bounds boundsInScene = messagesButton.localToScene(messagesButton.getBoundsInLocal());

        messagesContainerController = new ScreenColoredMessagesContainer(this);
        messagesContainerController.display(App.getGraphicsController().getWindow().getWidth() - boundsInScene.getMinX(), boundsInScene.getMinY() + messagesButton.getHeight());
    }

    @Override
    public void onMessageArrived(MessageModel message) {
        ScreenColoredGenericNotification notification = new ScreenColoredGenericNotification(Locales.get(message.getTitle()), Locales.get(message.getDescription()), message.getIcon());
        Platform.runLater(notification::display);
    }
}
