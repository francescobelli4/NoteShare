package views.colored;

import views.GraphicsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 *  Abstract class that represents a generic page controller.
 *
 *  Every controller should have:
 *  -   The page loader
 *  -   The page's root node
 *  -   The parent's controller to communicate easily
 *
 *  Every controller should allow:
 *  -   The page creation (I'm using two non-default constructors to generate the FXMLLoaders):
 *      -   PageController(Page page) is used for main pages, that do not have a parent
 *      -   PageController(Page page, PageController parentController) is used for every page that needs a
 *          parent to be displayed
 *  -   The page closing
 *  -   The closing of a child
 *
 *  Every PageController final subclass should add references to their children controllers and
 *  containers (VBox)
 */
public abstract class PageController {

    /** This loader is needed to access the page's controller */
    protected FXMLLoader loader;
    /** The root node of the page */
    protected Node root;
    /** The controller of the page's parent (in case the page has a parent...) */
    protected PageController parentController;

    /**
     * Base constructor
     *
     * It uses the GraphicsController to generate the FXMLLoader.
     * PageController final subclasses (The actual page controllers) should actually load the page
     * by themselves because every controller should set their instance as the loader's controller.
     * @param page the Page that should be loaded
     */
    protected PageController(Page page) {
        this.loader = GraphicsController.getInstance().generateFXMLLoader(page);
    }

    /**
     * Constructor with parent
     *
     * It uses the GraphicsController to generate the FXMLLoader.
     * PageController final subclasses (The actual page controllers) should actually load the page
     * by themselves because every controller should set their instance as the loader's controller.
     * This also sets the parentController.
     * @param page the Page that should be loaded
     * @param parentController the controller of the parent page
     */
    protected PageController(Page page, PageController parentController) {
        this.loader = GraphicsController.getInstance().generateFXMLLoader(page);
        this.parentController = parentController;
    }

    /**
     * GETTERS
     */

    public PageController getParentController() {
        return parentController;
    }

    public FXMLLoader getLoader() {
        return this.loader;
    }

    public Node getRoot() {
        return root;
    }

    /**
     * This function should close the page. PageController subclasses should implement this as every
     * page could use a different way for closing.
     */
    public abstract void close();

    /**
     * This function should close a child. As every child could use a different way for closing, I
     * decided to add the controller of the child.
     * @param childController the controller of the child page
     */
    public void closeChild(PageController childController) {
        if (childController == null) return;
        childController.close();
    }

}
