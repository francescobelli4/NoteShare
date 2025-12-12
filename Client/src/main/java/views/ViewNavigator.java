package views;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
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

    private Stage primaryStage;
    private Scene primaryScene;
    private StackPane mainLayout;

    public void setupView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.mainLayout = new StackPane();
        this.primaryScene = new Scene(mainLayout, 1920, 1080);
        this.primaryStage.setScene(this.primaryScene);
        this.primaryStage.show();
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

    public StackPane getMainLayout() {
        return mainLayout;
    }
}
