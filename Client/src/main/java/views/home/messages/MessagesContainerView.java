package views.home.messages;

import app.App;
import graphics_controllers.GraphicsController;
import graphics_controllers.home.messages.MessagesContainerViewController;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.messages.MessageModel;
import models.user.UserModel;
import views.Page;
import views.View;
import views.ViewFactory;

import java.util.List;

public class MessagesContainerView implements View, UserModel.MessageListener {

    @FXML
    private AnchorPane root;
    @FXML
    private ScrollPane box;
    @FXML
    private FlowPane messagesContainer;

    private final double x;
    private final double y;

    private static final Page page = Page.MESSAGES_CONTAINER;
    private final GraphicsController<MessagesContainerView> graphicsController;

    public MessagesContainerView(double x, double y) {
        this.x = x;
        this.y = y;

        graphicsController = new MessagesContainerViewController(this);
        init();
    }

    @Override
    public void init() {
        AnchorPane.setTopAnchor(box, y);
        AnchorPane.setRightAnchor(box, x);

        for (MessageModel message : App.getUser().getMessages()) {
            appendMessage(ViewFactory.getInstance().createMessageView(message.getTitle(), message.getDescription(), message.getDate(), message.getIcon()).getRoot());
        }
    }

    @Override
    public void update() {
        //Not needed...
    }

    public void appendMessage(Node message) {
        messagesContainer.getChildren().add(message);
    }

    @Override
    public void onMessagesSet(List<MessageModel> messages) {

    }

    @Override
    public void onMessageAdded(MessageModel message) {
        appendMessage(ViewFactory.getInstance().createMessageView(message.getTitle(), message.getDescription(), message.getDate(), message.getIcon()).getRoot());
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
    public GraphicsController<MessagesContainerView> getGraphicsController() {
        return graphicsController;
    }
}
