package views.colored;

import views.GraphicsController;
import views.colored.main_pages.ScreenColoredAccessPage;
import views.colored.main_pages.ScreenColoredMainPage;


public class ColoredGraphicsController extends GraphicsController {


    @Override
    protected void startMainPage() {
        ScreenColoredMainPage homePage = new ScreenColoredAccessPage();
        homePage.display();
    }
}
