package views.home.folders_container;

import graphics_controllers.GraphicsController;
import graphics_controllers.home.folders_container.NoteElementViewController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.note.NoteModel;
import utils.Utils;
import views.Page;
import views.View;

public class NoteElementView implements View {

    @FXML
    private VBox root;
    @FXML
    private Label noteLabel;

    private final NoteModel note;
    private static final Page page = Page.NOTE_ELEMENT;
    private final GraphicsController<NoteElementView> graphicsController;

    public NoteElementView(NoteModel note) {
        this.note = note;

        graphicsController = new NoteElementViewController(this);
        init();
    }

    @Override
    public void init() {
        Utils.scaleFonts(root);

        noteLabel.setText(note.getName());
    }

    @Override
    public void close() {
        //Nothing to do...
    }

    public NoteModel getNote() {
        return note;
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public GraphicsController<NoteElementView> getGraphicsController() {
        return graphicsController;
    }
}
