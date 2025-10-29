package graphics.colored.controllers.dialogues;

import graphics.GraphicsController;
import graphics.colored.Page;
import graphics.colored.controllers.PageController;
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
    public ScreenColoredDialogue(Page page) {
        super(page);
    }

    /**
     * This function should display the Dialog page by adding it to the main page's StackPane
     */
    public void display() {
        ((StackPane) GraphicsController.getInstance().getRoot()).getChildren().add(this.root);
    }

    /**
     * This function should close the Dialog by removing it to the main page's StackPane's children
     */
    @Override
    public void close() {
        ((StackPane)GraphicsController.getInstance().getRoot()).getChildren().remove(this.root);
    }
}
