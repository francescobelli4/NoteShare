package views.components;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class GridPaneWrapper extends GridPane {

    public GridPaneWrapper(float[] percentWidths, float[] percentHeights) {

        for (float width : percentWidths) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.SOMETIMES);
            col.setPercentWidth(width);
            getColumnConstraints().add(col);
        }

        for (float height : percentHeights) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.SOMETIMES);
            row.setPercentHeight(height);
            getRowConstraints().add(row);
        }
    }
}
