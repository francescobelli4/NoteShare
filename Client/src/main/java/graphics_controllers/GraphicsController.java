package graphics_controllers;

import views.View;

public abstract class GraphicsController <V extends View> {

    View view;

    protected GraphicsController(View view) {
        this.view = view;
    }

    public V getView() {
        return (V) view;
    }

    public abstract void setup();
}
