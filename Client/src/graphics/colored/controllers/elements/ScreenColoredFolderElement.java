package graphics.colored.controllers.elements;

import graphics.GraphicsController;
import graphics.colored.Page;
import graphics.colored.controllers.PageController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import persistency.shared.Folder;

/**
 * Class that represents the folder element.
 *
 * This is used to change the user's active directory.
 */
public class ScreenColoredFolderElement extends ScreenColoredElement{

    /** The name of the folder */
    @FXML
    Label folderLabel;

    /** A reference to the folder represented by this element */
    Folder thisFolder;

    /**
     * Constructor with parent controller and folder reference
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page and the
     * reference to the Folder
     *
     * @param parentController the parent's controller
     * @param folder a reference to the represented folder
     */
    public ScreenColoredFolderElement(PageController parentController, Folder folder) {
        super(Page.FOLDER_ELEMENT, parentController);

        this.thisFolder = folder;

        this.loader.setController(this);
        this.root = GraphicsController.getInstance().loadFXMLLoader(loader);
    }

    /**
     * The initialize function sets the note's name
     */
    @FXML
    public void initialize() {
        folderLabel.setText(thisFolder.getName());
    }
}
