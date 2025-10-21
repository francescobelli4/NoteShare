package graphics.colored;

import graphics.GraphicsController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import user.User;

import java.util.Map;


public class ScreenColoredFolderElementController implements PageController{

    @FXML
    Label folderLabel;

    @Override
    public void setParams(Map<String, String> params) {
        folderLabel.setText(params.get("label"));
    }

    @Override
    public void appendSecondaryPage(int id, Node secondaryPage) {

    }

    @FXML
    public void onFolderClick() {
        System.out.println("Folder clicked!");
        ScreenColoredHomePageController homePageController = GraphicsController.getMainPage().getController();
        //homePageController.updatePathLabel(User.getInstance().getRootFolder().searchSubFolder());
    }
}
