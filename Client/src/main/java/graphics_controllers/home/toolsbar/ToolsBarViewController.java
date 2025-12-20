package graphics_controllers.home.toolsbar;

import graphics_controllers.GraphicsController;
import views.home.toolsbar.ToolsBarView;

public class ToolsBarViewController extends GraphicsController<ToolsBarView> {

    public ToolsBarViewController(ToolsBarView view) {
        super(view);
    }

    @Override
    public void loaded() {
        getView().getBackButton().setOnMouseClicked(_ -> backButtonClicked());
        getView().getFolderAddButton().setOnMouseClicked(_ -> folderAddButtonClicked());
        getView().getNoteAddButton().setOnMouseClicked(_ -> noteAddButtonClicked());
    }

    private void backButtonClicked() {

    }

    private void folderAddButtonClicked() {

    }

    private void noteAddButtonClicked() {

    }
}
