package graphics.colored;

import javafx.scene.Node;

import java.util.Map;

/**
 * I use this interface to set params (if needed) for a generic Controller.
 * For example, I need this in notifications...
 */
public interface PageController {

    void setParams(Map<String, String> params);

    void appendSecondaryPage(Node secondaryPage);
}
