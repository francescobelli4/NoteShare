package views.colored.forms;

import views.colored.Page;
import views.colored.PageController;
import javafx.scene.layout.VBox;

/**
 * Abstract class representing a form
 *
 * A form is a secondary page and always has a parent page.
 * Every form page is appended in a VBox container.
 *
 */
public abstract class ScreenColoredForm extends PageController {

    /** The VBox container that contains this page */
    VBox container;

    /**
     * Constructor with parent
     *
     * This constructor only calls PageController constructor with parent, other operations are not needed
     *
     * @param page the page that should be displayed
     * @param parentController the controller of the parent page
     */
    public ScreenColoredForm(Page page, PageController parentController) {
        super(page, parentController);
    }

    /**
     * This function should display this page in the VBox container
     * @param container the container that contains this page
     */
    public void display(VBox container) {
        this.container = container;
        container.getChildren().add(this.root);
    }
}
