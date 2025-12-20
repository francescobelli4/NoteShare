package views.home.toolsbar;

import app.App;
import communication.dtos.user.UserType;
import graphics_controllers.GraphicsController;
import graphics_controllers.home.toolsbar.ToolsBarViewController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.folder.FolderModel;
import models.user.StudentUserModel;
import views.Page;
import views.View;

public class ToolsBarView implements View, StudentUserModel.Listener {

    @FXML
    private VBox root;
    @FXML
    private Button backButton;
    @FXML
    private Button folderAddButton;
    @FXML
    private Button noteAddButton;
    @FXML
    private Label pathLabel;

    private static final Page page = Page.TOOLS_BAR;
    private final GraphicsController<ToolsBarView> graphicsController;

    public ToolsBarView() {
        graphicsController = new ToolsBarViewController(this);
        init();
    }

    @Override
    public void init() {

        if (App.getUser().getUserType() == UserType.STUDENT) {
            StudentUserModel studentUserModel = App.getUserAs(StudentUserModel.class);
            setPathLabelText(studentUserModel.getActiveFolder().getPath());
            studentUserModel.addStudentListener(this);
        }
    }

    @Override
    public void update() {
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

    @Override
    public GraphicsController<ToolsBarView> getGraphicsController() {
        return graphicsController;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Button getFolderAddButton() {
        return folderAddButton;
    }

    public Button getNoteAddButton() {
        return noteAddButton;
    }

    public Label getPathLabel() {
        return pathLabel;
    }

    public void setPathLabelText(String path) {
        pathLabel.setText(path+"/");
    }

    @Override
    public void activeFolderSet(FolderModel folder) {
        setPathLabelText(folder.getPath());
    }
}
