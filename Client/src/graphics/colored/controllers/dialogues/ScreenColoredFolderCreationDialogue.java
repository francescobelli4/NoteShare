package graphics.colored.controllers.dialogues;

import app.App;
import app.mvc.BoundaryManager;
import app.mvc.models.UserModel;
import graphics.GraphicsController;
import graphics.colored.Icon;
import graphics.colored.Page;
import graphics.colored.controllers.forms.ScreenColoredFoldersContainer;
import graphics.colored.controllers.main_pages.ScreenColoredHomePage;
import graphics.colored.controllers.notifications.ScreenColoredGenericNotification;
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

    /** TextField limits */
    private int maxFolderNameLength = 25;

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

        folderNameTextField.setTextFormatter(new TextFormatter<String>(change -> {
            int len = change.getControlNewText().length();
            return len < maxFolderNameLength && Utils.isAlphanumeric(change.getText()) ? change : null;
        }));

        createFolderButton.setOnAction(e -> onCreateFolderButtonClick());
        closeButton.setOnAction(e -> onCloseButtonClick());
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

        /*if (folderNameTextField.getText().isEmpty()) {
            ScreenColoredGenericNotification notification = new ScreenColoredGenericNotification(Locales.get("error"), Locales.get("folder_name_too_short"), Icon.ERROR);
            notification.display();
            return;
        }

        if (UserModel.getInstance().getActiveFolder().searchSubFolder(folderNameTextField.getText()) != null) {
            ScreenColoredGenericNotification notification = new ScreenColoredGenericNotification(Locales.get("error"), Locales.get("folder_already_exists"), Icon.ERROR);
            notification.display();
            return;
        }

        UserModel.getInstance().getActiveFolder().addSubFolder(folderNameTextField.getText());

        ScreenColoredHomePage homePageController = (ScreenColoredHomePage)GraphicsController.getInstance().getMainPage();
        ScreenColoredFoldersContainer foldersContainer = homePageController.getFoldersContainerController();
        foldersContainer.displayFolder(UserModel.getInstance().getActiveFolder());

        ScreenColoredGenericNotification notification = new ScreenColoredGenericNotification(Locales.get("success"), Locales.get("folder_created"), Icon.SUCCESS);
        notification.display();*/

        close();
    }

    /**
     * This function should close this page. It only needs to clear the parent's child slot
     */
    @Override
    public void close() {
        super.close();
    }

    /**
     * This function should display the dialogue
     */
    @Override
    public void display() {
        super.display();
    }
}
