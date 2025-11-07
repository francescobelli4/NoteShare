package app.mvc;

/**
 * Abstract class representing a Controller. In the MVC pattern, a controller should contain
 * all the logic needed to complete a use case. A Controller should trigger Listener events
 * on a boundary to be able to update the UI.
 */
public abstract class Controller
{
    /**
     * The boundary relative to this controller
     */
    protected Boundary boundary;

    /**
     * Whenever a boundary is created, it can be linked to this controller using this
     * function
     * @param boundary the boundary related to this controller
     */
    public void setBoundary(Boundary boundary) {
        this.boundary = boundary;
    }

    /**
     * This function should return the correct Boundary subclass for this controller
     * @return a boundary subclass
     */
    protected abstract Boundary getBoundary();
}