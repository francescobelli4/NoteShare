package views.colored.forms;

import app.App;
import app.mvc.BoundaryManager;
import app.mvc.managefolder.ManageFolderBoundary;
import app.mvc.managefolder.ManageFolderResult;
import app.mvc.managenote.ManageNoteBoundary;
import app.mvc.managenote.ManageNoteResult;
import app.mvc.models.FolderModel;
import app.mvc.models.NoteModel;
import app.mvc.models.UserModel;
import app.mvc.navigation.NavigationBoundary;
import views.colored.Icon;
import views.colored.Page;
import views.colored.PageController;
import views.colored.elements.ScreenColoredFolderElement;
import views.colored.elements.ScreenColoredNoteElement;
import views.colored.notifications.ScreenColoredGenericNotification;
import views.colored.notifications.ScreenColoredNotification;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import locales.Locales;

/**
 * Class that represents the folders container
 */
public class ScreenColoredFoldersContainer extends ScreenColoredForm implements NavigationBoundary.Listener, ManageFolderBoundary.Listener, ManageNoteBoundary.Listener {

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
        this.root = App.getGraphicsController().loadFXMLLoader(loader);

        BoundaryManager.getInstance().initializeNavigationBoundary();
        BoundaryManager.getInstance().initializeManageFolderBoundary();
        BoundaryManager.getInstance().initializeManageNoteBoundary();

        BoundaryManager.getInstance().getNavigationBoundary().addListener(this);
        BoundaryManager.getInstance().getManageFolderBoundary().addListener(this);
        BoundaryManager.getInstance().getManageNoteBoundary().addListener(this);
    }

    /**
     * This function should clear the foldersContainer and then display a new folder's content
     * @param folderModel the folder that should be displayed
     */
    public void displayFolder(FolderModel folderModel) {

        clear();

        for (FolderModel f : folderModel.getSubFolders()) {
            addFolder(f);
        }

        for (NoteModel n : folderModel.getNotes()) {
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
     * @param folderModel the folder that should be displayed
     */
    private void addFolder(FolderModel folderModel) {
        ScreenColoredFolderElement folderElement = new ScreenColoredFolderElement(this, folderModel);
        folderElement.display(foldersContainer);
    }

    /**
     * This function should display a note
     * @param note the note that should be displayed
     */
    private void addNote(NoteModel note) {
        ScreenColoredNoteElement noteElement = new ScreenColoredNoteElement(this, note);
        noteElement.display(foldersContainer);
    }

    /**
     * The initialize function sets the on click events for the buttons
     */
    @FXML
    public void initialize() {
        displayFolder(UserModel.getInstance().getRootFolder());
    }

    /**
     * This functions should close this container, never needed actually...
     */
    @Override
    public void close() {
        BoundaryManager.getInstance().destroyNavigationBoundary();
        BoundaryManager.getInstance().destroyManageFolderBoundary();
        BoundaryManager.getInstance().destroyManageNoteBoundary();
    }

    @Override
    public void onActiveFolderUpdated() {
        displayFolder(UserModel.getInstance().getActiveFolder());
    }

    @Override
    public void onFolderSaved(FolderModel folder) {
        displayFolder(folder.getParentFolder());

        ScreenColoredNotification notification = new ScreenColoredGenericNotification(Locales.get("success"), Locales.get("folder_created"), Icon.SUCCESS);
        notification.display();
    }

    @Override
    public void onFolderDeleted() {
        displayFolder(UserModel.getInstance().getActiveFolder());
    }

    @Override
    public void onFolderMoved() {
        //TODO
    }

    @Override
    public void onFolderCreationFailed(ManageFolderResult manageFolderResult) {

        String title = Locales.get("error");
        Icon icon = Icon.ERROR;

        String description = switch (manageFolderResult) {
            case ManageFolderResult.FOLDER_NAME_NOT_SPECIFIED ->
                    Locales.get("folder_name_too_short");
            case ManageFolderResult.FOLDER_ALREADY_EXISTS ->
                    Locales.get("folder_already_exists");
            case ManageFolderResult.FOLDER_NAME_TOO_LONG ->
                    Locales.get("folder_name_too_long");
        };

        ScreenColoredGenericNotification notification = new ScreenColoredGenericNotification(title, description, icon);

        Platform.runLater(notification::display);
    }

    @Override
    public void onNoteSaved(NoteModel note) {
        displayFolder(note.getParentFolder());

        ScreenColoredNotification notification = new ScreenColoredGenericNotification(Locales.get("success"), Locales.get("note_created"), Icon.SUCCESS);
        notification.display();
    }

    @Override
    public void onNoteDeleted() {
        displayFolder(UserModel.getInstance().getActiveFolder());
    }

    @Override
    public void onNoteMoved() {
        //TODO
    }

    @Override
    public void onNoteCreationFailed(ManageNoteResult manageNoteResult) {

        String title = Locales.get("error");
        Icon icon = Icon.ERROR;

        String description = switch (manageNoteResult) {
            case ManageNoteResult.NOTE_NAME_NOT_SPECIFIED ->
                    Locales.get("note_name_too_short");
            case ManageNoteResult.NOTE_ALREADY_EXISTS ->
                    Locales.get("note_already_exists");
            case ManageNoteResult.NOTE_NAME_TOO_LONG ->
                    Locales.get("note_name_too_long");
        };

        ScreenColoredGenericNotification notification = new ScreenColoredGenericNotification(title, description, icon);
        Platform.runLater(notification::display);
    }
}
