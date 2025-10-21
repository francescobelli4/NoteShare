package graphics.colored;

import graphics.GraphicsController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.StackPane;
import locales.Locales;
import main.Main;
import memory.shared.entities.NoteEntity;
import user.User;
import utils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ScreenColoredNoteCreationFormController implements PageController{
    @FXML
    StackPane base;

    @FXML
    TextField noteNameTextField;

    @FXML
    Label createNoteLabel;

    @FXML
    Button createNoteButton;

    @FXML
    Button choosePDFButton;

    File pdf;

    private int maxNoteNameLength = 25;

    @Override
    public void setParams(Map<String, String> params) {

    }

    @Override
    public void appendSecondaryPage(int id, Node secondaryPage) {

    }

    private void closeForm() {
        StackPane parent = (StackPane) base.getParent();
        parent.getChildren().remove(base);
    }

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

    }

    @FXML
    public void onCloseButtonClick() {
        closeForm();
    }

    @FXML
    public void onCreateNoteButtonClick() {

        User user = User.getInstance();

        if (noteNameTextField.getText().isEmpty()) {
            GraphicsController.displayNotification(Locales.get("error"), Locales.get("note_name_too_short"), Icons.ERROR);
            return;
        }

        if (Main.noteDAO.getNote(user.getActiveFolder(), noteNameTextField.getText()) != null) {
            GraphicsController.displayNotification(Locales.get("error"), Locales.get("note_already_exists"), Icons.ERROR);
            return;
        }

        if (pdf == null) {
            GraphicsController.displayNotification(Locales.get("error"), Locales.get("note_pdf_not_set"), Icons.ERROR);
            return;
        }

        GraphicsController.displayNotification(Locales.get("success"), Locales.get("note_created"), Icons.SUCCESS);

        NoteEntity note = Main.noteDAO.createNote(noteNameTextField.getText(), pdf.getAbsolutePath());
        Main.noteDAO.saveNote(user.getActiveFolder(), note);

        Map<String, String> params = new HashMap<>();
        params.put("label", noteNameTextField.getText());

        GraphicsController.displaySecondaryPage(Pages.NOTE_ELEMENT, 1, params);

        closeForm();
    }

    @FXML
    public void onChoosePDFButtonClick() {
        pdf = GraphicsController.openFileChooser();

        if (pdf == null) {
            return;
        }

        choosePDFButton.getStyleClass().add("selected");
    }
}
