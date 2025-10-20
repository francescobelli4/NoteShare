package graphics.colored;

import graphics.GraphicsController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import user.User;

import java.awt.*;
import java.util.Map;
import java.util.Objects;

public class ScreenColoredStudentHomePageToolsBarController implements PageController{



    @Override
    public void setParams(Map<String, String> params) {

    }

    @Override
    public void appendSecondaryPage(int id, Node secondaryPage) {

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

    }
}
