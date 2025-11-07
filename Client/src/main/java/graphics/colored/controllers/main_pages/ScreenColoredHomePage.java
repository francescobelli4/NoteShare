package graphics.colored.controllers.main_pages;

import app.mvc.models.UserModel;
import graphics.GraphicsController;
import graphics.colored.Page;
import graphics.colored.controllers.forms.*;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * Class that represents the home page.
 *
 * A home page is a main page and it's elements depend on the user type (student, teacher, administrator).
 *
 * A home page has 3 slots for children pages:
 * -    leftBarSlot:        ScreenColored[Student/Teacher/Administrator]LeftBar
 * -    toolsSlot:          ScreenColored[Student/Teacher/Administrator]ToolsBar
 * -    foldersContainer:   TODO
 *
 */
public class ScreenColoredHomePage extends ScreenColoredMainPage {

    /** The slot for left bar secondary page */
    @FXML
    VBox leftBarSlot;
    /** The slot for tools bar secondary page */
    @FXML
    VBox toolsSlot;
    /** The slot for folders container */
    @FXML
    VBox foldersContainer;

    /** The controller of the active left bar */
    ScreenColoredStudentLeftBar studentLeftBarController;

    /** The controller of the active tools bar */
    ScreenColoredStudentToolsBar studentToolsBarController;

    /** The controller of the folders container */
    ScreenColoredFoldersContainer foldersContainerController;

    /**
     * Base constructor
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page
     */
    public ScreenColoredHomePage() {
        super(Page.HOME_PAGE);

        this.loader.setController(this);
        this.root = GraphicsController.getInstance().loadFXMLLoader(loader);
    }

    /**
     * This function should display this page. When the page is displayed, it should automatically
     * append both the tools bar and the left bar.
     */
    @Override
    public void display() {
        super.display();

        appendToolsBar();
        appendLeftBar();
        appendFoldersContainer();
    }

    /**
     * This function should append the tools bar to the toolsSlot
     */
    private void appendToolsBar() {

        switch (UserModel.getInstance().getUserType()) {
            case "student":
                studentToolsBarController = new ScreenColoredStudentToolsBar(this);
                studentToolsBarController.display(toolsSlot);
                break;
            case "teacher":
                break;
            case "administrator":
                break;

        }
    }

    /**
     * This function should append the left bar to the leftBarSlot
     */
    private void appendLeftBar() {
        switch (UserModel.getInstance().getUserType()) {
            case "student":
                studentLeftBarController = new ScreenColoredStudentLeftBar(this);
                studentLeftBarController.display(leftBarSlot);
                break;
            case "teacher":
                break;
            case "administrator":
                break;

        }
    }

    private void appendFoldersContainer() {
        foldersContainerController = new ScreenColoredFoldersContainer(this);
        foldersContainerController.display(foldersContainer);
    }

    public ScreenColoredStudentToolsBar getStudentToolsBarController() {
        return studentToolsBarController;
    }

    public ScreenColoredFoldersContainer getFoldersContainerController() {
        return foldersContainerController;
    }
}
