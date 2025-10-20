package graphics.colored;

import graphics.GraphicsController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import user.User;

import java.util.Map;
import java.util.Objects;

public class ScreenColoredHomePageController implements PageController{

    @FXML
    StackPane root;

    @FXML
    VBox leftBarSlot;

    @FXML
    VBox toolsSlot;

    @FXML
    FlowPane elements_container;


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
                elements_container.getChildren().add(secondaryPage);
                break;
            case 2:
                toolsSlot.getChildren().add(secondaryPage);
                break;
            case 3:
                root.getChildren().add(secondaryPage);
                break;
        }

    }

    @FXML
    public void initialize() {

        String userType = User.getInstance().getUserDTO().getUserType();

        if (Objects.equals(userType, "student")) {
            GraphicsController.displaySecondaryPage(Pages.STUDENT_HOME_PAGE_LEFT_BAR, 0, null);
            GraphicsController.displaySecondaryPage(Pages.STUDENT_HOME_PAGE_TOOLS_BAR, 2, null);
        } else if (Objects.equals(userType, "teacher")) {
            GraphicsController.displaySecondaryPage(Pages.TEACHER_HOME_PAGE_LEFT_BAR, 0, null);
        } else if (Objects.equals(userType, "administrator")) {
            GraphicsController.displaySecondaryPage(Pages.ADMINISTRATOR_HOME_PAGE_LEFT_BAR, 0, null);
        }
    }
}
