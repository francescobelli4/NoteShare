package graphics.colored.controllers.main_pages;

import graphics.GraphicsController;
import graphics.colored.Page;
import graphics.colored.controllers.PageController;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *  Abstract class that represents a main page.
 *  A main page does not have a parent and it becomes the root of a new scene.
 *
 *  Every ScreenColoredMainPage should allow:
 *  -   The creation of the page using the ScreenColoredMainPage(Page page) constructor
 *  -   The displaying of the page
 */
public abstract class ScreenColoredMainPage extends PageController {

    /**
     * Base constructor
     *
     * This constructor only calls PageController base constructor, other operations are not needed
     *
     * @param page the page that should be loaded
     */
    public ScreenColoredMainPage(Page page) {
        super(page);
    }

    /**
     * This function should actually display the page. A main page becomes the new root of a new scene.
     */
    public void display() {
        GraphicsController.getInstance().getWindow().setScene(new Scene((Parent) this.root));
        GraphicsController.getInstance().getWindow().show();
        GraphicsController.getInstance().setMainPage(this);
    }

    /**
     * This function should close the main page. No closing method is needed here.
     */
    @Override
    public void close() {

    }
}
