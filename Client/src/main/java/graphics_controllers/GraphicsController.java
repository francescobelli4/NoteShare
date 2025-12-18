package graphics_controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import views.Page;
import views.View;

import java.io.IOException;

public abstract class GraphicsController <V extends View> {

    private final V view;

    protected GraphicsController(V view) {
        this.view = view;

        Node root = loadFXML(view.getPage());

        loaded();
    }

    private Node loadFXML(Page page) {

       FXMLLoader loader = new FXMLLoader(GraphicsController.class.getResource(page.getPath()));

       loader.setController(view);

       try {
           return loader.load();
       } catch (IOException e) {
           e.printStackTrace();
       }

       return null;
    }

    protected V getView() {
        return view;
    }

    public abstract void loaded();
}
