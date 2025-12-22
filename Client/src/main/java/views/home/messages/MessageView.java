package views.home.messages;

import graphics_controllers.GraphicsController;
import graphics_controllers.home.messages.MessageViewController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import locales.Locales;
import utils.Utils;
import views.Icon;
import views.Page;
import views.View;

import java.util.Objects;

public class MessageView implements View  {

    private final String titleLabel;
    private final String descriptionLabel;
    private final String dateLabel;
    private final Icon icon;

    @FXML
    private HBox root;
    @FXML
    private ImageView iconImage;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label date;

    private static final Page page = Page.MESSAGE_ELEMENT;
    private final GraphicsController<MessageView> graphicsController;

    public MessageView(String titleLabel, String descriptionLabel, String dateLabel, Icon icon) {

        this.titleLabel = titleLabel;
        this.descriptionLabel = descriptionLabel;
        this.dateLabel = dateLabel;
        this.icon = icon;

        graphicsController = new MessageViewController(this);
        init();
    }

    @Override
    public void init() {
        Utils.scaleFonts(root);

        title.setText(Locales.get(titleLabel));
        description.setText(Locales.get(descriptionLabel));
        date.setText(dateLabel);
        iconImage.setImage(new Image(Objects.requireNonNull(getClass().getResource(icon.getPath())).toExternalForm()));
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
    public GraphicsController<MessageView> getGraphicsController() {
        return graphicsController;
    }
}
