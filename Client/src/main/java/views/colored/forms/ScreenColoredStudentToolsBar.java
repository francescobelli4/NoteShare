package views.colored.forms;

import app.mvc.BoundaryManager;
import app.mvc.models.UserModel;
import app.mvc.navigation.NavigationBoundary;
import views.GraphicsController;
import views.colored.Page;
import views.colored.PageController;
import views.colored.dialogues.ScreenColoredFolderCreationDialogue;
import views.colored.dialogues.ScreenColoredNoteCreationDialogue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Class that represents the left bar for the student user type.
 */
public class ScreenColoredStudentToolsBar extends ScreenColoredForm implements NavigationBoundary.Listener {

    /**
     * FXML elements
     */
    @FXML
    Label pathLabel;
    @FXML
    Button folderAddButton;
    @FXML
    Button noteAddButton;
    @FXML
    Button backButton;

    /**
     * Constructor with parent controller
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page
     * @param parentController the controller of the parent page
     */
    public ScreenColoredStudentToolsBar(PageController parentController) {
        super(Page.STUDENT_HOME_PAGE_TOOLS_BAR, parentController);

        this.loader.setController(this);
        this.root = GraphicsController.getInstance().loadFXMLLoader(loader);

        BoundaryManager.getInstance().getNavigationBoundary().addListener(this);
    }

    /**
     * The initialize function sets the on click events for the buttons
     */
    @FXML
    public void initialize() {
        folderAddButton.setOnAction(e -> onFolderAddButtonClick());
        noteAddButton.setOnAction(e -> onNoteAddButtonClick());
        backButton.setOnAction(e -> onBackButtonClick());
        setPathLabel(UserModel.getInstance().getRootFolder().getPath());
    }

    /**
     * This function should display a FolderCreationDialogue
     */
    public void onFolderAddButtonClick() {
        ScreenColoredFolderCreationDialogue screenColoredFolderCreationDialogue = new ScreenColoredFolderCreationDialogue();
        screenColoredFolderCreationDialogue.display();
    }

    /**
     * This function should display a NoteCreationDialogue
     */
    public void onNoteAddButtonClick() {
        ScreenColoredNoteCreationDialogue screenColoredNoteCreationDialogue = new ScreenColoredNoteCreationDialogue();
        screenColoredNoteCreationDialogue.display();
    }

    /**
     * This function should set the active folder to the actual active folder's parent
     */
    public void onBackButtonClick() {
        if (UserModel.getInstance().getActiveFolder() == UserModel.getInstance().getRootFolder()) return;
        BoundaryManager.getInstance().getNavigationBoundary().goBack();
    }

    /**
     * This function should update the pathLabel whenever the user changes the active folder
     * @param path the path of the new active folder
     */
    public void setPathLabel(String path) {
        pathLabel.setText(path + "/");
    }

    /**
     * This function should close the tools bar, actually this is never needed...
     */
    @Override
    public void close() {
        //Nothing to do (never closed)
    }

    @Override
    public void onActiveFolderUpdated() {
        setPathLabel(UserModel.getInstance().getActiveFolder().getPath());
    }
}
