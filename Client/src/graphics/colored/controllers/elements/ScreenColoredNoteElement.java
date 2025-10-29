package graphics.colored.controllers.elements;

import graphics.GraphicsController;
import graphics.colored.Page;
import graphics.colored.controllers.PageController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import persistency.shared.entities.NoteEntity;

/**
 * Class that represents the folder element.
 *
 * This is used to open notes.
 */
public class ScreenColoredNoteElement extends ScreenColoredElement{

    /** The name of the note */
    @FXML
    Label noteLabel;

    /** A reference to the note represented by this element */
    NoteEntity thisNote;

    /**
     * Constructor with parent controller and note reference
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page and the
     * reference to the NoteEntity
     *
     * @param parentController the parent's controller
     * @param note a reference to the represented note
     */
    public ScreenColoredNoteElement(PageController parentController, NoteEntity note) {
        super(Page.NOTE_ELEMENT, parentController);

        this.thisNote = note;

        this.loader.setController(this);
        this.root = GraphicsController.getInstance().loadFXMLLoader(loader);
    }

    /**
     * The initialize function sets the note label
     */
    @FXML
    public void initialize() {
        noteLabel.setText(thisNote.getName());
    }
}
