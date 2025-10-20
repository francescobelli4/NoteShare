package graphics.colored;

import graphics.GraphicsController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.StackPane;
import locales.Locales;
import user.User;
import utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class ScreenColoredFolderCreationFormController implements PageController{
    @FXML
    StackPane base;

    @FXML
    TextField folderNameTextField;

    @FXML
    Label createFolderLabel;

    @FXML
    Button createFolderButton;

    private int maxFolderNameLength = 25;

    @Override
    public void setParams(Map<String, String> params) {

    }

    @Override
    public void appendSecondaryPage(int id, Node secondaryPage) {

    }

    private void closeForm() {
        StackPane parent = (StackPane) base.getParent();
        parent.getChildren().remove(base);
    }

    @FXML
    public void initialize() {
        createFolderLabel.setText(Locales.get("create_folder"));
        createFolderButton.setText(Locales.get("create_folder"));
        folderNameTextField.setPromptText(Locales.get("name"));

        folderNameTextField.setTextFormatter(new TextFormatter<String>(change -> {
            int len = change.getControlNewText().length();
            return len < maxFolderNameLength && Utils.isAlphanumeric(change.getText()) ? change : null;
        }));

    }

    @FXML
    public void onCloseButtonClick() {
        closeForm();
    }

    @FXML
    public void onCreateFolderButtonClick() {

        User user = User.getInstance();

        if (folderNameTextField.getText().isEmpty()) {
            GraphicsController.displayNotification(Locales.get("error"), Locales.get("folder_name_too_short"), Icons.ERROR);
            return;
        }

        if (user.getActiveFolder().searchSubFolder(folderNameTextField.getText()) != null) {
            GraphicsController.displayNotification(Locales.get("error"), Locales.get("folder_already_exists"), Icons.ERROR);
            return;
        }

        GraphicsController.displayNotification(Locales.get("success"), Locales.get("folder_created"), Icons.SUCCESS);
        user.getActiveFolder().addSubFolder(folderNameTextField.getText());

        Map<String, String> params = new HashMap<>();
        params.put("label", folderNameTextField.getText());

        GraphicsController.displaySecondaryPage(Pages.FOLDER_ELEMENT, 1, params);
        closeForm();
    }
}
