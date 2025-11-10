package views.colored.forms;

import app.App;
import app.mvc.BoundaryManager;
import app.mvc.models.MessageModel;
import app.mvc.models.UserModel;
import app.mvc.viewmessages.ViewMessagesBoundary;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import views.GraphicsController;
import views.colored.Page;
import views.colored.PageController;
import views.colored.elements.ScreenColoredMessageElement;

/**
 * Class that represents the folders container
 */
public class ScreenColoredMessagesContainer extends ScreenColoredForm implements ViewMessagesBoundary.Listener {

    /**
     * This FlowPane contains the list of messages
     */
    @FXML
    FlowPane messagesContainer;
    @FXML
    ScrollPane box;

    /**
     * Constructor with parent controller
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page
     * @param parentController the controller of the parent page
     */
    public ScreenColoredMessagesContainer(PageController parentController) {

        super(Page.MESSAGES_CONTAINER, parentController);

        this.loader.setController(this);
        this.root = App.getGraphicsController().loadFXMLLoader(loader);

        BoundaryManager.getInstance().getViewMessagesBoundary().addListener(this);

        this.root.setOnMouseClicked(e -> close());
    }

    /**
     * This function should display the list of messages. It creates a MessageElement for
     * each MessageModel
     */
    public void displayMessages() {

        messagesContainer.getChildren().clear();

        for (MessageModel message : UserModel.getInstance().getMessages()) {
            ScreenColoredMessageElement messageElement = new ScreenColoredMessageElement(this, message);
            messageElement.display(messagesContainer);
        }
    }

    /**
     * The initialize function adds the MessageElements
     */
    @FXML
    public void initialize() {
        displayMessages();
    }


    /**
     * This function should close this element and remove it from the listeners list
     */
    @Override
    public void close() {
        BoundaryManager.getInstance().getViewMessagesBoundary().removeListener(this);
        ((StackPane)this.root.getParent()).getChildren().remove(this.root);
    }

    /**
     * This function should display the folders container
     */
    public void display(double x, double y) {
        super.display();

        AnchorPane.setTopAnchor(box, y);
        AnchorPane.setRightAnchor(box, x);
    }

    /**
     * This function should update the list of messages if a new message is received while
     * this element exists
     * @param message the new message (a don't care condition at this moment...)
     */
    @Override
    public void onMessageArrived(MessageModel message) {
        displayMessages();
    }
}
