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

public class ScreenColoredMessageElement extends ScreenColoredElement{

    @FXML
    ImageView icon;
    @FXML
    Label title;
    @FXML
    Label description;
    @FXML
    Label date;

    MessageModel message;

    public ScreenColoredMessageElement(PageController parentController, MessageModel message) {
        super(Page.MESSAGE_ELEMENT, parentController);

        this.message = message;

        this.loader.setController(this);
        this.root = GraphicsController.getInstance().loadFXMLLoader(loader);
    }

    @FXML
    public void initialize() {

        title.setText(Locales.get(message.getTitle()));
        description.setText(Locales.get(message.getDescription()));
        date.setText(message.getDate());
        icon.setImage(new Image(Objects.requireNonNull(getClass().getResource(message.getIcon().getPath())).toExternalForm()));
    }

    @Override
    public void display(FlowPane container) {
        super.display(container);
    }
}
