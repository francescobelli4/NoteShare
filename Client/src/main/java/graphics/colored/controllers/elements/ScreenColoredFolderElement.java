package graphics.colored.controllers.elements;

import app.mvc.BoundaryManager;
import graphics.GraphicsController;
import graphics.colored.Page;
import graphics.colored.controllers.PageController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import app.mvc.models.FolderModel;

/**
 * Class that represents the folder element.
 *
 * This is used to change the user's active directory.
 */
public class ScreenColoredFolderElement extends ScreenColoredElement{

    /** The name of the folder */
    @FXML
    Label folderLabel;
    @FXML
    VBox folderElement;

    /** A reference to the folder represented by this element */
    FolderModel thisFolder;

    /**
     * Constructor with parent controller and folder reference
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page and the
     * reference to the Folder
     *
     * @param parentController the parent's controller
     * @param folderModel a reference to the represented folder
     */
    public ScreenColoredFolderElement(PageController parentController, FolderModel folderModel) {
        super(Page.FOLDER_ELEMENT, parentController);

        this.thisFolder = folderModel;

        this.loader.setController(this);
        this.root = GraphicsController.getInstance().loadFXMLLoader(loader);
    }

    /**
     * The initialize function sets the note's name
     */
    @FXML
    public void initialize() {
        folderLabel.setText(thisFolder.getName());
        folderElement.setOnMouseClicked(this::onFolderElementClick);
    }

    /**
     * This function should change the actual active folder to this one
     */
    public void onFolderElementClick(MouseEvent mouseEvent) {

        if (!mouseEvent.getButton().equals(MouseButton.PRIMARY) || mouseEvent.getClickCount() != 2) return;

        BoundaryManager.getInstance().getNavigationBoundary().goToFolder(thisFolder);
    }
}
