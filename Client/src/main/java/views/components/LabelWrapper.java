package views.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class LabelWrapper extends Label {

    public LabelWrapper(String label, float size) {
        setText(label);
        setFont(Font.font("Cantarell Regular", size));
    }

    public LabelWrapper(String label, float size, Insets insets) {
        this(label, size);
        VBox.setMargin(this, insets);
    }
}
