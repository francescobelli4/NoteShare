package app.mvc;

/**
 * Abstract class representing a boundary. A boundary class abstracts the user interactions
 * with the system to complete a use case: in this way, the app can use multiple UIs without
 * having to duplicate code for each UI.
 *
 * Actually, in the MVC pattern, a boundary is represented by a View. I chose to separate
 * the view (which manages the UI elements and calls boundary functions) from this boundary.
 * In this way, the UI-code never gets in touch with the actual logic of each user interaction.
 *
 * Every boundary should have a Listener interface: every Listener's method is an event that
 * can be triggered by the use case's controller and it's implemented by each class that
 * wants to register as a listener.
 *
 * A boundary should not implement application logic: it should only be a way to make the
 * user communicate with the use case's controller.
 */
public abstract class Boundary {

    /**
     * This is the controller associated with the boundary. In every Boundary subclass,
     * getController method should return the correct type of controller.
     */
    protected Controller controller;

    /**
     * Base constructor
     *
     * When a boundary is instantiated, it should set its own controller and the controller
     * should be notified that his boundary is up, so they are connected.
     * @param controller the right controller for the boundary
     */
    protected Boundary(Controller controller)  {
        this.controller = controller;
        this.controller.setBoundary(this);
    }

    /**
     * Every boundary subclass should implement a proper way to destroy his controller and
     * himself.
     * A boundary can be destroyed when it's not needed anymore (ex. login, register)
     */
    public abstract void destroy();

    /**
     * This function should return the right type of controller.
     * @return Controller subclass
     */
    protected abstract Controller getController();
}
