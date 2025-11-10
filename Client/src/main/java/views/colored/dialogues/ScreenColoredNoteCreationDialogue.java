package views.colored.dialogues;

import app.App;
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

import java.io.File;

/**
 * Class that represents the note creation dialogue.
 *
 * This dialogue allows user to create a new NoteElement
 */
public class ScreenColoredNoteCreationDialogue extends ScreenColoredDialogue {

    /**
     * FXML Elements
     */
    @FXML
    Label createNoteLabel;
    @FXML
    TextField noteNameTextField;
    @FXML
    Button choosePDFButton;
    @FXML
    Button createNoteButton;
    @FXML
    Button closeButton;


    /** Selected PDF */
    File pdf;

    /**
     * Constructor with parent controller
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page
     */
    public ScreenColoredNoteCreationDialogue() {
        super(Page.NOTE_CREATION_DIALOGUE);

        this.loader.setController(this);
        this.root = App.getGraphicsController().loadFXMLLoader(loader);
    }

    /**
     * In the initialize function, I set all the labels and the button click events. I also set the text
     * formatter for the text field
     */
    @FXML
    public void initialize() {
        createNoteLabel.setText(Locales.get("create_note"));
        createNoteButton.setText(Locales.get("create_note"));
        noteNameTextField.setPromptText(Locales.get("name"));
        choosePDFButton.setText(Locales.get("choose_pdf"));

        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            int len = change.getControlNewText().length();
            return len < BoundaryManager.getInstance().getManageNoteBoundary().getMaxNoteNameLength() && Utils.isAlphanumeric(change.getText()) ? change : null;
        });

        noteNameTextField.setTextFormatter(textFormatter);

        createNoteButton.setOnAction(e -> onCreateNoteButtonClick());
        closeButton.setOnAction(e -> onCloseButtonClick());
        choosePDFButton.setOnAction(e -> onChoosePDFButtonClick());
    }

    /**
     * This function handles the close button click. It closes the dialogue.
     */
    @FXML
    public void onCloseButtonClick() {
        close();
    }

    /**
     * This function actually creates a new NoteElement
     */
    @FXML
    public void onCreateNoteButtonClick() {

        BoundaryManager.getInstance().getManageNoteBoundary().saveNote(noteNameTextField.getText(), pdf, UserModel.getInstance().getActiveFolder());
        close();
    }

    /**
     * This function opens the file chooser to select the pdf associated with the note
     */
    @FXML
    public void onChoosePDFButtonClick() {
        pdf = App.getGraphicsController().openFileChooser();

        if (pdf == null) {
            return;
        }

        choosePDFButton.getStyleClass().add("selected");
    }
}
