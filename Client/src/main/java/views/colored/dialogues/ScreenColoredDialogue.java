package views.colored.dialogues;

import app.App;
import views.GraphicsController;
import views.colored.Page;
import views.colored.PageController;
import javafx.scene.layout.StackPane;

/**
 * Abstract class representing a Dialog page
 */
public abstract class ScreenColoredDialogue extends PageController {

    /**
     * Base constructor
     *
     * This constructor only calls PageController base constructor, other operations are not needed
     *
     * @param page the page that should be loaded
     */
    protected ScreenColoredDialogue(Page page) {
        super(page);
    }

    /**
     * This function should display the Dialog page by adding it to the main page's StackPane
     */
    public void display() {
        ((StackPane) App.getGraphicsController().getRoot()).getChildren().add(this.root);
    }

    /**
     * This function should close the Dialog by removing it to the main page's StackPane's children
     */
    @Override
    public void close() {
        ((StackPane) App.getGraphicsController().getRoot()).getChildren().remove(this.root);
    }
}
