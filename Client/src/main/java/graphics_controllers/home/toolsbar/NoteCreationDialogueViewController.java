package graphics_controllers.home.toolsbar;

import app.App;
import app_controllers.NotesController;
import graphics_controllers.GraphicsController;
import locales.Locales;
import utils.Utils;
import views.Icon;
import views.ViewNavigator;
import views.home.toolsbar.NoteCreationDialogueView;

import java.io.File;

public class NoteCreationDialogueViewController extends GraphicsController<NoteCreationDialogueView> {

    private File selectedPDF;

    public NoteCreationDialogueViewController(NoteCreationDialogueView view) {
        super(view);
    }

    @Override
    public void loaded() {
        getView().getCloseButton().setOnMouseClicked(_ -> getView().close());
        getView().getCreateNoteButton().setOnMouseClicked(_ -> createNoteButtonClicked());
        getView().getChoosePDFButton().setOnMouseClicked(_ -> choosePDFButtonClicked());
    }

    private void choosePDFButtonClicked() {
        selectedPDF = Utils.openFileChooser(ViewNavigator.getStage());

        if (selectedPDF == null)
            return;

        getView().getChoosePDFButton().getStyleClass().add("selected");
    }

    private void createNoteButtonClicked() {

        if (getView().getNoteNameTextField().getText().isEmpty()) {
            ViewNavigator.displayNotification(Locales.get("error"), Locales.get("note_name_too_short"), Icon.ERROR);
            return;
        }

        if (getView().getNoteNameTextField().getText().length() > 15) {
            ViewNavigator.displayNotification(Locales.get("error"), Locales.get("note_name_too_long"), Icon.ERROR);
            return;
        }

        if (App.getUser().getActiveFolder().searchNote(getView().getNoteNameTextField().getText()) != null) {
            ViewNavigator.displayNotification(Locales.get("error"), Locales.get("note_already_exists"), Icon.ERROR);
            return;
        }

        if (selectedPDF == null) {
            ViewNavigator.displayNotification(Locales.get("error"), Locales.get("note_pdf_not_set"), Icon.ERROR);
            return;
        }

        NotesController.createNote(getView().getNoteNameTextField().getText(), selectedPDF);
        getView().close();
    }
}
