package views.home.toolsbar;

import graphics_controllers.GraphicsController;
import graphics_controllers.home.toolsbar.NoteCreationDialogueViewController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.StackPane;
import locales.Locales;
import utils.Utils;
import views.Page;
import views.View;
import views.ViewNavigator;

public class NoteCreationDialogueView implements View {

    @FXML
    private StackPane root;
    @FXML
    private Button closeButton;
    @FXML
    private Label createNoteLabel;
    @FXML
    private TextField noteNameTextField;
    @FXML
    private Button choosePDFButton;
    @FXML
    private Button createNoteButton;

    private static final Page page = Page.NOTE_CREATION_DIALOGUE;
    private final GraphicsController<NoteCreationDialogueView> graphicsController;

    public NoteCreationDialogueView() {
        graphicsController = new NoteCreationDialogueViewController(this);
        init();
    }

    @Override
    public void init() {

        Utils.scaleFonts(root);

        createNoteLabel.setText(Locales.get("create_note"));
        noteNameTextField.setTextFormatter(getTextFormatter(15, true));
        noteNameTextField.setPromptText(Locales.get("note_name"));
        createNoteButton.setText(Locales.get("create_note"));
    }

    @Override
    public void close() {
        ((StackPane) ViewNavigator.getActiveView().getRoot()).getChildren().remove(root);
    }

    private TextFormatter<?> getTextFormatter(int maxLength, boolean onlyAlphanumeric) {
        return new TextFormatter<>(change -> {
            int len = change.getControlNewText().length();

            if (onlyAlphanumeric) {
                return len < maxLength && Utils.isAlphanumeric(change.getText()) ? change : null;
            } else {
                return len < maxLength ? change : null;
            }
        });
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public Page getPage() {
        return page;
    }

    public Button getCloseButton() {
        return closeButton;
    }

    public Label getCreateNoteLabel() {
        return createNoteLabel;
    }

    public Button getChoosePDFButton() {
        return choosePDFButton;
    }

    public Button getCreateNoteButton() {
        return createNoteButton;
    }

    public TextField getNoteNameTextField() {
        return noteNameTextField;
    }

    @Override
    public GraphicsController<NoteCreationDialogueView> getGraphicsController() {
        return graphicsController;
    }
}
