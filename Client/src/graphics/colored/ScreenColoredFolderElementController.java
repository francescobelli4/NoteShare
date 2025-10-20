package graphics.colored;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

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
    }
}
