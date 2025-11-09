package views.colored.elements;

import app.mvc.models.MessageModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import locales.Locales;
import views.GraphicsController;
import views.colored.Page;
import views.colored.PageController;

import java.util.Objects;

/**
 * This class represents a Message element. Every message element is displayed in the MessageContainer.
 * A Message Element should only display the message data.
 */
public class ScreenColoredMessageElement extends ScreenColoredElement{

    @FXML
    ImageView icon;
    @FXML
    Label title;
    @FXML
    Label description;
    @FXML
    Label date;

    /**
     * The message associated with this MessageElement
     */
    MessageModel message;

    /**
     * Constructor with parent controller and message reference
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page and the
     * reference to the MessageModel
     *
     * @param parentController the parent's controller
     * @param message a reference to the represented folder
     */
    public ScreenColoredMessageElement(PageController parentController, MessageModel message) {
        super(Page.MESSAGE_ELEMENT, parentController);

        this.message = message;

        this.loader.setController(this);
        this.root = GraphicsController.getInstance().loadFXMLLoader(loader);
    }

    /**
     * This function should set all the correct labels
     */
    @FXML
    public void initialize() {
        title.setText(Locales.get(message.getTitle()));
        description.setText(Locales.get(message.getDescription()));
        date.setText(message.getDate());
        icon.setImage(new Image(Objects.requireNonNull(getClass().getResource(message.getIcon().getPath())).toExternalForm()));
    }

    /**
     * This function should display this FolderElement
     * @param container the container that contains this element
     */
    @Override
    public void display(FlowPane container) {
        super.display(container);
    }
}
