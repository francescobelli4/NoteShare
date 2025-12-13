package views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ViewNavigator {

    private static ViewNavigator instance;
    public static ViewNavigator getInstance() {
        if (instance == null) {
            instance = new ViewNavigator();
        }
        return instance;
    }
    private ViewNavigator() {}

    private StackPane mainLayout;

    private static double scaleFactor = 1.0f;

    public void setupView(Stage primaryStage) {
        this.mainLayout = new StackPane();
        Scene primaryScene = new Scene(mainLayout);
        primaryStage.setScene(primaryScene);

        primaryStage.setTitle("NoteShare");
        primaryStage.setResizable(false);
        primaryStage.setMaximized(true);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setMinHeight(screenBounds.getHeight());
        primaryStage.setMinWidth(screenBounds.getWidth());

        scaleFactor = screenBounds.getHeight()/1080.0f;

        primaryStage.show();
    }

    public static double scaleValue(double val) {
        return val * scaleFactor;
    }

    public void displayAccessView() {
        AccessView accessView = ViewFactory.getInstance().createAccessView();
        mainLayout.getChildren().clear();
        mainLayout.getChildren().add(accessView.getRoot());
    }

    public void displayNotification(String title, String description, Icon icon) {
        NotificationView notificationView = ViewFactory.getInstance().createNotificationView(title, description, icon);
        mainLayout.getChildren().add(notificationView.getRoot());
    }

    public void displayHomeView() {
        HomeView homeView = ViewFactory.getInstance().createHomeView();
        mainLayout.getChildren().clear();
        mainLayout.getChildren().add(homeView.getRoot());
    }

    public StackPane getMainLayout() {
        return mainLayout;
    }
}
