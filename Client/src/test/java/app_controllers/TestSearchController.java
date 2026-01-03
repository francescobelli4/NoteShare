package app_controllers;

import app.AppContext;
import app.ArgsParser;
import models.user.UserModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestSearchController {

    @Test
    void updateQuery() {
        String[] args = {"demo", "en", "colored"};

        AppContext instance = AppContext.getInstance();
        instance.setOptions(ArgsParser.parseArgs(args));
        instance.setCurrentUser(new UserModel());

        SearchController.updateQuery("test");

        assertEquals("test", instance.getCurrentUser().getQuery());
    }
}