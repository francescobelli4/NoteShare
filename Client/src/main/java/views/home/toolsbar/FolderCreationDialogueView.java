package views.home.toolsbar;

import graphics_controllers.GraphicsController;
import graphics_controllers.home.toolsbar.FolderCreationDialogueViewController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.StackPane;
import locales.Locales;
import utils.Utils;
import views.Page;
import views.View;
import views.ViewNavigator;

public class FolderCreationDialogueView implements View {

    @FXML
    private StackPane root;
    @FXML
    private Button closeButton;
    @FXML
    private Label createFolderLabel;
    @FXML
    private TextField folderNameTextField;
    @FXML
    private Button createFolderButton;

    private static final Page page = Page.FOLDER_CREATION_DIALOGUE;
    private final GraphicsController<FolderCreationDialogueView> graphicsController;

    public FolderCreationDialogueView() {
        graphicsController = new FolderCreationDialogueViewController(this);
        init();
    }

    @Override
    public void init() {

        Utils.scaleFonts(root);

        createFolderLabel.setText(Locales.get("create_folder"));
        folderNameTextField.setTextFormatter(getTextFormatter(15, true));
        folderNameTextField.setPromptText(Locales.get("folder_name"));
        createFolderButton.setText(Locales.get("create_folder"));
    }

    @Override
    public void close() {
        ((StackPane) ViewNavigator.getActiveView().getRoot()).getChildren().remove(root);
    }

    private TextFormatter<?> getTextFormatter(int maxLength, boolean onlyAlphanumeric) {
        return new TextFormatter<>(change -> {
            int len = change.getControlNewText().length();

            if (onlyAlphanumeric) {
                return len < maxLength && Utils.isAlphanumeric(change.getText()) ? change : null;
            } else {
                return len < maxLength ? change : null;
            }
        });
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public Page getPage() {
        return page;
    }

    public Button getCloseButton() {
        return closeButton;
    }

    public Label getCreateFolderLabel() {
        return createFolderLabel;
    }

    public TextField getFolderNameTextField() {
        return folderNameTextField;
    }

    public Button getCreateFolderButton() {
        return createFolderButton;
    }

    @Override
    public GraphicsController<FolderCreationDialogueView> getGraphicsController() {
        return graphicsController;
    }
}
