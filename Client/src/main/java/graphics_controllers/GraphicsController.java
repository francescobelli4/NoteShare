package graphics_controllers;

import javafx.fxml.FXMLLoader;
import views.Page;
import views.View;

import java.io.IOException;
import java.util.logging.Logger;

public abstract class GraphicsController <V extends View> {

    private static final Logger LOGGER = Logger.getLogger("GraphicsController");

    private final V view;

    protected GraphicsController(V view) {
        this.view = view;

        loadFXML(view.getPage());

        loaded();
    }

    private void loadFXML(Page page) {

       FXMLLoader loader = new FXMLLoader(GraphicsController.class.getResource(page.getPath()));
       loader.setController(view);

       try {
           loader.load();
       } catch (IOException _) {
           LOGGER.severe(String.format("Failed loading page %s", page.getPath()));
       }
    }

    protected V getView() {
        return view;
    }

    public abstract void loaded();
}
