package graphics.colored.controllers.dialogues;

import app.App;
import app.User;
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
import persistency.shared.entities.NoteEntity;
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

    /** TextField limits */
    private int maxNoteNameLength = 25;

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
        this.root = GraphicsController.getInstance().loadFXMLLoader(loader);
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

        noteNameTextField.setTextFormatter(new TextFormatter<String>(change -> {
            int len = change.getControlNewText().length();
            return len < maxNoteNameLength && Utils.isAlphanumeric(change.getText()) ? change : null;
        }));

        createNoteButton.setOnAction(actionEvent -> onCreateNoteButtonClick());
        closeButton.setOnAction(actionEvent -> onCloseButtonClick());
        choosePDFButton.setOnAction(actionEvent -> onChoosePDFButtonClick());
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

        if (noteNameTextField.getText().isEmpty()) {
            ScreenColoredGenericNotification notification = new ScreenColoredGenericNotification(Locales.get("error"), Locales.get("note_name_too_short"), Icon.ERROR);
            notification.display();
            return;
        }

        if (App.getNoteDAO().getNote(User.getInstance().getActiveFolder(), noteNameTextField.getText()) != null) {
            ScreenColoredGenericNotification notification = new ScreenColoredGenericNotification(Locales.get("error"), Locales.get("note_already_exists"), Icon.ERROR);
            notification.display();
            return;
        }

        if (pdf == null) {
            ScreenColoredGenericNotification notification = new ScreenColoredGenericNotification(Locales.get("error"), Locales.get("note_pdf_not_set"), Icon.ERROR);
            notification.display();
            return;
        }

        ScreenColoredGenericNotification notification = new ScreenColoredGenericNotification(Locales.get("success"), Locales.get("note_created"), Icon.SUCCESS);
        notification.display();

        NoteEntity note = App.getNoteDAO().createNote(noteNameTextField.getText(), pdf.getAbsolutePath());
        App.getNoteDAO().saveNote(User.getInstance().getActiveFolder(), note);

        ScreenColoredHomePage homePageController = (ScreenColoredHomePage)GraphicsController.getInstance().getMainPage();
        ScreenColoredFoldersContainer foldersContainer = homePageController.getFoldersContainerController();
        foldersContainer.displayFolder(User.getInstance().getActiveFolder());

        close();
    }

    /**
     * This function opens the file chooser to select the pdf associated with the note
     */
    @FXML
    public void onChoosePDFButtonClick() {
        pdf = GraphicsController.getInstance().openFileChooser();

        if (pdf == null) {
            return;
        }

        choosePDFButton.getStyleClass().add("selected");
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
