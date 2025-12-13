package views.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ButtonWrapper extends Button {

    public ButtonWrapper(String text, double textSize, Insets insets) {
        this(text, textSize);
        VBox.setMargin(this, insets);
    }

    public ButtonWrapper(String text, double textSize) {
        setText(text);
        setFont(Font.font("Cantarell Regular", textSize));
    }

    public ButtonWrapper(ImageView imageView) {
        setGraphic(imageView);
    }

    public ButtonWrapper(ImageView imageView, Insets insets) {
        this(imageView);
        VBox.setMargin(this, insets);
    }
}
