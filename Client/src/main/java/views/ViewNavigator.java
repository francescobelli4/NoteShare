package views;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ViewNavigator {

    private ViewNavigator() {}

    private static Stage stage;
    private static Scene scene;
    private static View activeView;

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;

        scene = new Scene(new StackPane());
        stage.setScene(scene);
    }

    public static void displayHomeView() {
        activeView = ViewFactory.getInstance().createHomeView();
        scene.setRoot(activeView.getRoot());
    }

    public static void displayAccessView() {
        activeView = ViewFactory.getInstance().createAccessView();
        scene.setRoot(activeView.getRoot());
    }

    public static void displayNotification(String title, String description, Icon icon) {
        ((StackPane)activeView.getRoot()).getChildren().add(ViewFactory.getInstance().createNotificationView(title, description, icon).getRoot());
    }

    public static Stage getStage() {
        return stage;
    }

    public static View getActiveView() {
        return activeView;
    }

    public static double scaleValue(double val) {

        Screen screen = Screen.getPrimary();

        // Windows usually has 1.25 scale
        double scale = screen.getOutputScaleY();
        // Converting screen bounds height to real pixel height
        double phys = screen.getBounds().getHeight() * scale;
        // I built the UI using a 1920x1200 res
        return val * (phys/1200);
    }
}
