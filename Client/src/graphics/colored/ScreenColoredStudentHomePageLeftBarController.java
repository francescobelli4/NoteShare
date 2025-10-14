package graphics.colored;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import locales.Locales;
import user.User;

import java.util.Map;

public class ScreenColoredStudentHomePageLeftBarController implements PageController{

    @FXML
    Button your_notes_button;

    @FXML
    Button browse_notes_button;

    @FXML
    Button shared_notes_button;

    @FXML
    Label username_label;

    @FXML
    Label coins_label;

    @Override
    public void setParams(Map<String, String> params) {

    }

    @Override
    public void appendSecondaryPage(int id, Node secondaryPage) {

    }

    @FXML
    public void initialize() {

        your_notes_button.setText(Locales.get("your_notes"));
        browse_notes_button.setText(Locales.get("browse_notes"));
        shared_notes_button.setText(Locales.get("shared_notes"));

        username_label.setText(User.getInstance().getUserDTO().getUsername());
        coins_label.setText(User.getInstance().getUserDTO().getCoins() + "");
    }
}
