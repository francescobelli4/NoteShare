package graphics.colored;

import graphics.GraphicsController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import java.util.Map;

/**
 * This controller manages the AccessPage.
 */
public class ScreenColoredAccessController implements PageController{

    @FXML
    VBox secondaryPageSlot;

    /**
     * This page needs no params to be started
     */
    @Override
    public void setParams(Map<String, String> params) {}

    @Override
    public void appendSecondaryPage(int id, Node secondaryPage) {
        secondaryPageSlot.getChildren().clear();
        secondaryPageSlot.getChildren().add(secondaryPage);
    }

    @FXML
    public void initialize() {
        GraphicsController.displaySecondaryPage(Pages.ACCESS_FORM, 0, null);
    }
}
