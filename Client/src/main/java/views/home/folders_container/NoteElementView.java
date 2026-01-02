package views.home.folders_container;

import graphics_controllers.GraphicsController;
import graphics_controllers.home.folders_container.NoteElementViewController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
    @FXML
    private Button optionsButton;

    private static final Page page = Page.NOTE_ELEMENT;
    private final GraphicsController<NoteElementView> graphicsController;

    public NoteElementView(NoteModel note) {
        graphicsController = new NoteElementViewController(this, note);
        init();
    }

    @Override
    public void init() {
        Utils.scaleFonts(root);
    }

    @Override
    public void close() {
        //Nothing to do...
    }

    public Button getOptionsButton() {
        return optionsButton;
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public Page getPage() {
        return page;
    }

    public Label getNoteLabel() {
        return noteLabel;
    }

    @Override
    public GraphicsController<NoteElementView> getGraphicsController() {
        return graphicsController;
    }
}
