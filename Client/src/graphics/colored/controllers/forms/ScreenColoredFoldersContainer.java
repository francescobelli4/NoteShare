package graphics.colored.controllers.forms;

import app.User;
import graphics.GraphicsController;
import graphics.colored.Page;
import graphics.colored.controllers.PageController;
import graphics.colored.controllers.elements.ScreenColoredFolderElement;
import graphics.colored.controllers.elements.ScreenColoredNoteElement;
import graphics.colored.controllers.main_pages.ScreenColoredHomePage;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import persistency.shared.Folder;
import persistency.shared.entities.NoteEntity;

/**
 * Class that represents the folders container
 */
public class ScreenColoredFoldersContainer extends ScreenColoredForm {

    @FXML
    FlowPane foldersContainer;

    /**
     * Constructor with parent controller
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page
     * @param parentController the controller of the parent page
     */
    public ScreenColoredFoldersContainer(PageController parentController) {
        super(Page.FOLDERS_CONTAINER, parentController);

        this.loader.setController(this);
        this.root = GraphicsController.getInstance().loadFXMLLoader(loader);
    }

    /**
     * This function should clear the foldersContainer and then display a new folder's content
     * @param folder the folder that should be displayed
     */
    public void displayFolder(Folder folder) {

        clear();

        ScreenColoredHomePage homePageController = (ScreenColoredHomePage)this.parentController;
        ScreenColoredStudentToolsBar toolsBar = homePageController.getStudentToolsBarController();
        toolsBar.setPathLabel(folder.getPath());

        for (Folder f : folder.getSubFolders()) {
            addFolder(f);
        }

        for (NoteEntity n : folder.getNotes()) {
            addNote(n);
        }
    }

    /**
     * This function should clear the foldersContainer
     */
    private void clear() {
        foldersContainer.getChildren().clear();
    }

    /**
     * This function should display a folder
     * @param folder the folder that should be displayed
     */
    private void addFolder(Folder folder) {
        ScreenColoredFolderElement folderElement = new ScreenColoredFolderElement(this, folder);
        folderElement.display(foldersContainer);
    }

    /**
     * This function should display a note
     * @param note the note that should be displayed
     */
    private void addNote(NoteEntity note) {
        ScreenColoredNoteElement noteElement = new ScreenColoredNoteElement(this, note);
        noteElement.display(foldersContainer);
    }

    /**
     * The initialize function sets the on click events for the buttons
     */
    @FXML
    public void initialize() {
        displayFolder(User.getInstance().getRootFolder());
    }

    /**
     * This functions should close this container, never needed actually...
     */
    @Override
    public void close() { }

    /**
     * This function should display the folders container
     * @param container the container that contains this page
     */
    @Override
    public void display(VBox container) {
        super.display(container);
    }
}
