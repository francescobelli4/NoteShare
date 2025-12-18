package views;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ViewNavigator {

    private ViewNavigator() {}

    private static Stage stage;
    private static View activeView;

    private static double scaleFactor = 1.0f;

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
        scaleFactor = stage.getHeight()/1080.0f;
    }

    public static void displayHomeView() {

        activeView = ViewFactory.getInstance().createHomeView();

        Scene primaryScene = new Scene(activeView.getRoot());
        stage.setScene(primaryScene);
    }

    public static void displayAccessView() {

        activeView = ViewFactory.getInstance().createAccessView();

        Scene primaryScene = new Scene(activeView.getRoot());
        stage.setScene(primaryScene);
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
        return val * scaleFactor;
    }
}
