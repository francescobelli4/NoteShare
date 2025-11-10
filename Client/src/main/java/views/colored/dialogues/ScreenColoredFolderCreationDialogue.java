package views.colored.dialogues;

import app.mvc.BoundaryManager;
import app.mvc.models.UserModel;
import views.GraphicsController;
import views.colored.Page;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import locales.Locales;
import utils.Utils;

/**
 * Class that represents the folder creation dialogue.
 *
 * This dialogue allows user to create a new FolderElement
 */
public class ScreenColoredFolderCreationDialogue extends ScreenColoredDialogue {

    /**
     * FXML Elements
     */
    @FXML
    Label createFolderLabel;
    @FXML
    TextField folderNameTextField;
    @FXML
    Button createFolderButton;
    @FXML
    Button closeButton;

    /**
     * Constructor with parent controller
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page
     */
    public ScreenColoredFolderCreationDialogue() {
        super(Page.FOLDER_CREATION_DIALOGUE);

        this.loader.setController(this);
        this.root = GraphicsController.getInstance().loadFXMLLoader(loader);
    }

    /**
     * In the initialize function, I set all the labels and the button click events. I also set the text
     * formatter for the text field
     */
    @FXML
    public void initialize() {
        createFolderLabel.setText(Locales.get("create_folder"));
        createFolderButton.setText(Locales.get("create_folder"));
        folderNameTextField.setPromptText(Locales.get("name"));

        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            int len = change.getControlNewText().length();
            return len < BoundaryManager.getInstance().getManageFolderBoundary().getMaxFolderNameLength() && Utils.isAlphanumeric(change.getText()) ? change : null;
        });

        folderNameTextField.setTextFormatter(textFormatter);

        createFolderButton.setOnAction(_ -> onCreateFolderButtonClick());
        closeButton.setOnAction(_ -> onCloseButtonClick());
    }

    /**
     * This function handles the close button click. It closes the dialogue
     */
    @FXML
    public void onCloseButtonClick() {
        close();
    }

    /**
     * This function actually creates a new FolderElement
     */
    @FXML
    public void onCreateFolderButtonClick() {

        BoundaryManager.getInstance().getManageFolderBoundary().saveFolder(folderNameTextField.getText(), UserModel.getInstance().getActiveFolder());
        close();
    }
}
