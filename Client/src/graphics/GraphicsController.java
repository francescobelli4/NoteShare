package graphics;

import graphics.colored.Page;
import graphics.colored.controllers.main_pages.ScreenColoredMainPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public abstract class GraphicsController extends Application {

    private static GraphicsController instance;
    protected Stage window;
    protected ScreenColoredMainPage mainPage;

    public static GraphicsController getInstance() {
        return instance;
    }

    public Stage getWindow() {
        return window;
    }

    public Parent getRoot() {
        return window.getScene().getRoot();
    }

    public ScreenColoredMainPage getMainPage() {
        return mainPage;
    }

    public void setMainPage(ScreenColoredMainPage mainPage) {
        this.mainPage = mainPage;
    }

    public FXMLLoader generateFXMLLoader(Page page) {
        return new FXMLLoader(GraphicsController.class.getResource(page.getPath()));
    }

    public Node loadFXMLLoader(FXMLLoader loader) {
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public File openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Apri file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.PDF", "*.pdf"));
        return fileChooser.showOpenDialog(window);
    }

    @Override
    public void start(Stage stage) {
        instance = this;

        this.window = stage;
        this.window.setTitle("NoteShare");

        startMainPage();
    }

    protected abstract void startMainPage();
}
