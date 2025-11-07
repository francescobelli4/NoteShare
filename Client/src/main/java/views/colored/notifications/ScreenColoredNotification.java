package views.colored.notifications;

import views.GraphicsController;
import views.colored.Page;
import views.colored.PageController;
import javafx.scene.layout.StackPane;

/**
 *  Abstract class that represents a notification.
 *
 *  Every ScreenColoredNotification should just provide a method for displaying the notification
 *
 *  Every ScreenColoredNotification subclass should manage its lifetime and animations.
 */
public abstract class ScreenColoredNotification extends PageController {

    /**
     * Base constructor
     *
     * This constructor only calls PageController base constructor, other operations are not needed
     *
     * @param page the page that should be loaded
     */
    public ScreenColoredNotification(Page page) {
        super(page);
    }

    /**
     * This function should display the notification. A notifications should be appended to the
     * main page StackPane.
     */
    public void display() {
        ((StackPane)GraphicsController.getInstance().getRoot()).getChildren().add(this.getRoot());
    }

    /**
     * This function should close the notification. Not needed...
     */
    @Override
    public void close() {

    }
}
