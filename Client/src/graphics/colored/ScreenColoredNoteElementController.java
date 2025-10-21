package graphics.colored;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import javax.imageio.stream.ImageOutputStream;
import java.util.Map;


public class ScreenColoredNoteElementController implements PageController {

    @FXML
    Label noteLabel;

    @Override
    public void setParams(Map<String, String> params) {
        noteLabel.setText(params.get("label"));
    }

    @Override
    public void appendSecondaryPage(int id, Node secondaryPage) {

    }

    @FXML
    public void onNoteClick(MouseEvent mouseEvent) {

    }
}
