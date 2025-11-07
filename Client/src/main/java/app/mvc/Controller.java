package app.mvc;

public abstract class Controller
{
    protected Boundary boundary;

    public void setBoundary(Boundary boundary) {
        this.boundary = boundary;
    }
    protected abstract Boundary getBoundary();
}