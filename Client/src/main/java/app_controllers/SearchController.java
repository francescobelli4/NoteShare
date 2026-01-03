package app_controllers;

import app.AppContext;
import models.user.UserModel;

public class SearchController {

    private SearchController() {}

    public static void updateQuery(String query) {
        UserModel user = AppContext.getInstance().getCurrentUser();
        user.setSearchQuery(query);
    }
}
