package views.components;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import utils.Utils;

public class TextFieldWrapper extends TextField {

    public TextFieldWrapper(String placeHolder, double size) {

        setPromptText(placeHolder);
        setFont(Font.font("Cantarell Regular", size));
        setFocusTraversable(false);

        setTextFormatter(new TextFormatter<>(change -> {
            int len = change.getControlNewText().length();
            return len < Utils.getMaxUsernameLength() && Utils.isAlphanumeric(change.getText()) ? change : null;
        }));
    }

    public TextFieldWrapper(String placeHolder, double size, Insets insets) {
        this(placeHolder, size);
        VBox.setMargin(this, insets);
    }
}