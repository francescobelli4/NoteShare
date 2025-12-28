package views.viewnote;

import graphics_controllers.GraphicsController;
import graphics_controllers.viewnote.ViewNoteViewController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.note.NoteModel;
import utils.Utils;
import views.Page;
import views.View;

public class ViewNoteView implements View {

    @FXML
    private StackPane root;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox imagesContainer;


    private static final Page page = Page.VIEW_NOTE_PAGE;
    private final GraphicsController<ViewNoteView> graphicsController;

    public ViewNoteView(NoteModel note) {
        graphicsController = new ViewNoteViewController(this, note);
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

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public Page getPage() {
        return page;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public VBox getImagesContainer() {
        return imagesContainer;
    }

    @Override
    public GraphicsController<ViewNoteView> getGraphicsController() {
        return graphicsController;
    }
}
