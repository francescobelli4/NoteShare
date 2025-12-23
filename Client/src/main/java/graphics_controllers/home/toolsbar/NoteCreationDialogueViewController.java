package graphics_controllers.home.toolsbar;

import app.AppContext;
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

        String title = Locales.get("error");
        Icon icon = Icon.ERROR;

        if (getView().getNoteNameTextField().getText().isEmpty()) {
            ViewNavigator.displayNotification(title, Locales.get("note_name_too_short"), icon);
            return;
        }

        if (getView().getNoteNameTextField().getText().length() > 15) {
            ViewNavigator.displayNotification(title, Locales.get("note_name_too_long"), icon);
            return;
        }

        if (AppContext.getInstance().getCurrentUser().getActiveFolder().searchNote(getView().getNoteNameTextField().getText()) != null) {
            ViewNavigator.displayNotification(title, Locales.get("note_already_exists"), icon);
            return;
        }

        if (selectedPDF == null) {
            ViewNavigator.displayNotification(title, Locales.get("note_pdf_not_set"), icon);
            return;
        }

        NotesController.createNote(getView().getNoteNameTextField().getText(), selectedPDF);
        getView().close();
    }
}
