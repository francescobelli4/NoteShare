package graphics.colored;

import graphics.GraphicsController;
import graphics.colored.controllers.main_pages.ScreenColoredAccessPage;
import graphics.colored.controllers.main_pages.ScreenColoredMainPage;


public class ColoredGraphicsController extends GraphicsController {


    @Override
    protected void startMainPage() {
        ScreenColoredMainPage homePage = new ScreenColoredAccessPage();
        homePage.display();
    }
}
