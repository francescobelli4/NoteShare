package graphics_controllers.home.toolsbar;

import app_controllers.NotesController;
import exceptions.DuplicateNoteException;
import exceptions.InvalidNoteNameException;
import exceptions.InvalidNotePDFException;
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

        try {
            NotesController.createNote(getView().getNoteNameTextField().getText(), selectedPDF);
            getView().close();
        } catch (DuplicateNoteException _) {
            ViewNavigator.displayNotification(title, Locales.get("note_already_exists"), icon);
        } catch (InvalidNoteNameException e) {
            ViewNavigator.displayNotification(title, Locales.get(e.getMessage()), icon);
        } catch (InvalidNotePDFException _) {
            ViewNavigator.displayNotification(title, Locales.get("note_pdf_not_set"), icon);
        }
    }
}
