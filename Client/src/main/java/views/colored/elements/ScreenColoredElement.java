package views.colored.elements;

import views.colored.Page;
import views.colored.PageController;
import javafx.scene.layout.FlowPane;

/**
 * Abstract class representing an Element
 *
 * Every Element (Folder or Note) should be linked to its parent's FlowPane
 */
public abstract class ScreenColoredElement extends PageController {

    /** Where the Element should be displayed */
    FlowPane container;

    /**
     * Constructor with parent
     *
     * This constructor only calls PageController constructor with parent, other operations are not needed
     *
     * @param page the page that should be displayed
     * @param parentController the controller of the parent page
     */
    public ScreenColoredElement(Page page, PageController parentController) {
        super(page, parentController);
    }

    /**
     * This function should display the element by adding it to the parent's FlowPane
     * @param container the container that contains this element
     */
    public void display(FlowPane container) {
        this.container = container;
        container.getChildren().add(this.root);
    }

    /**
     * This function should close the element by removing it from the parent's FlowPane
     */
    @Override
    public void close() {
        this.container.getChildren().remove(this.root);
    }
}
