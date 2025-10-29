package graphics.colored.controllers.forms;

import graphics.GraphicsController;
import graphics.colored.Page;
import graphics.colored.controllers.PageController;
import graphics.colored.controllers.dialogues.ScreenColoredFolderCreationDialogue;
import graphics.colored.controllers.dialogues.ScreenColoredNoteCreationDialogue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Class that represents the left bar for the student user type.
 */
public class ScreenColoredStudentToolsBar extends ScreenColoredForm {

    /**
     * FXML elements
     */
    @FXML
    Label pathLabel;
    @FXML
    Button folder_add_button;
    @FXML
    Button note_add_button;

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
    }

    /**
     * The initialize function sets the on click events for the buttons
     */
    @FXML
    public void initialize() {
        folder_add_button.setOnAction(_ -> onFolderAddButtonClick());
        note_add_button.setOnAction(_ -> onNoteAddButtonClick());
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

    }

    /**
     * This function should display the tools bar
     * @param container the container that contains this page
     */
    @Override
    public void display(VBox container) {
        super.display(container);
    }
}
