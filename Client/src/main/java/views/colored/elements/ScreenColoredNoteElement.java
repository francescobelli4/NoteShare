package views.colored.elements;

import app.App;
import app.mvc.models.NoteModel;
import views.GraphicsController;
import views.colored.Page;
import views.colored.PageController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;


/**
 * Class that represents the folder element.
 *
 * This is used to open notes.
 */
public class ScreenColoredNoteElement extends ScreenColoredElement{

    /** The name of the note */
    @FXML
    Label noteLabel;
    @FXML
    VBox noteElement;

    /** A reference to the note represented by this element */
    NoteModel thisNote;

    /**
     * Constructor with parent controller and note reference
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page and the
     * reference to the NoteEntity
     *
     * @param parentController the parent's controller
     * @param note a reference to the represented note
     */
    public ScreenColoredNoteElement(PageController parentController, NoteModel note) {
        super(Page.NOTE_ELEMENT, parentController);

        this.thisNote = note;

        this.loader.setController(this);
        this.root = App.getGraphicsController().loadFXMLLoader(loader);
    }

    /**
     * The initialize function sets the note label
     */
    @FXML
    public void initialize() {
        noteLabel.setText(thisNote.getName());
        noteElement.setOnMouseClicked(this::onNoteElementClick);
    }

    public void onNoteElementClick(MouseEvent mouseEvent) {

        if (!mouseEvent.getButton().equals(MouseButton.PRIMARY) || mouseEvent.getClickCount() != 2) return;

        openNote();
    }

    private void openNote() {
        //TODO
    }
}

