package graphics_controllers.notification;

import graphics_controllers.GraphicsController;
import views.notification.NotificationView;

public class NotificationViewController extends GraphicsController<NotificationView> {

    public NotificationViewController(NotificationView view) {
        super(view);
    }

    @Override
    public void loaded() {
        //Nothing to do...
    }

}
