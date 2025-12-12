package views.components;

import javafx.geometry.Insets;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import utils.Utils;

public class PasswordFieldWrapper extends PasswordField {

    public PasswordFieldWrapper(String placeHolder, double size) {

        setPromptText(placeHolder);
        setFont(Font.font("Cantarell Regular", size));
        setFocusTraversable(false);

        setTextFormatter(new TextFormatter<>(change -> {
            int len = change.getControlNewText().length();
            return len < Utils.getMaxPasswordLength() && Utils.isAlphanumeric(change.getText()) ? change : null;
        }));
    }

    public PasswordFieldWrapper(String placeHolder, double size, Insets insets) {
        this(placeHolder, size);
        VBox.setMargin(this, insets);
    }
}