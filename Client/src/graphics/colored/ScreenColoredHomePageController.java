package graphics.colored;

import graphics.GraphicsController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import locales.Locales;
import user.User;

import java.util.Map;
import java.util.Objects;

public class ScreenColoredHomePageController implements PageController{

    @FXML
    VBox leftBarSlot;

    @FXML
    VBox rightSideSlot;


    @Override
    public void setParams(Map<String, String> params) {

    }

    @Override
    public void appendSecondaryPage(int id, Node secondaryPage) {

        switch (id) {
            case 0:
                leftBarSlot.getChildren().add(secondaryPage);
                break;
            case 1:
                rightSideSlot.getChildren().add(secondaryPage);
                break;

        }

    }

    @FXML
    public void initialize() {

        if (Objects.equals(User.getInstance().getUserDTO().getUserType(), "student")) {
            GraphicsController.displaySecondaryPage(Pages.STUDENT_HOME_PAGE_FORM, 0, null);
        } else if (Objects.equals(User.getInstance().getUserDTO().getUserType(), "teacher")) {

        } else if (Objects.equals(User.getInstance().getUserDTO().getUserType(), "administrator")) {

        }
    }
}
