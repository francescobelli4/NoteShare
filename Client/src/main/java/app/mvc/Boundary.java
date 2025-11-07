package app.mvc;

public abstract class Boundary {

    protected Controller controller;

    public Boundary(Controller controller)  {
        this.controller = controller;
        this.controller.setBoundary(this);
        initialize();
    }

    protected abstract void initialize();
    public abstract void destroy();
    protected abstract Controller getController();
}
