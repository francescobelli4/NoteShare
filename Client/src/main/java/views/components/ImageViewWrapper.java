package views.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;

public class ImageViewWrapper extends ImageView {

    public ImageViewWrapper(String imagePath, double width, double height) {
        super(loadImage(imagePath));

        this.setFitWidth(width);
        this.setFitHeight(height);
        this.setPreserveRatio(true);
        this.setPickOnBounds(true);
    }

    private static Image loadImage(String path) {
        try {

            InputStream is = ImageViewWrapper.class.getResourceAsStream(path);

            if (is != null) {
                return new Image(is);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}