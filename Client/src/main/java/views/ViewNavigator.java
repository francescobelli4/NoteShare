package views;

import javafx.geometry.Rectangle2D;
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
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        return val * screenBounds.getHeight()/1080.0f;
    }
}
