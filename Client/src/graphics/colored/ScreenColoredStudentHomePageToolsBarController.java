package graphics.colored;

import graphics.GraphicsController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import java.util.Map;

public class ScreenColoredStudentHomePageToolsBarController implements PageController{

    @FXML
    Label pathLabel;

    @Override
    public void setParams(Map<String, String> params) {

    }

    @Override
    public void appendSecondaryPage(int id, Node secondaryPage) {

    }

    public void updatePath(String path) {
        pathLabel.setText(path);
    }

    @FXML
    public void initialize() {

    }


    @FXML
    public void onFolderAddButtonClick() {
        GraphicsController.displaySecondaryPage(Pages.FOLDER_CREATION_FORM, 3, null);
    }

    @FXML
    public void onFileAddButtonClick() {
        GraphicsController.displaySecondaryPage(Pages.NOTES_CREATION_FORM, 3, null);
    }
}
