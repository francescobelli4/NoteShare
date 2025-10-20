package locales;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: EN and IT
 */
public class Locales {

    private static final Map<String, String> locales = new HashMap<>();

    public static void initializeLocales() {
        locales.put("register", "Register");
        locales.put("login", "Login");
        locales.put("username", "Username");
        locales.put("password", "Password");
        locales.put("register_page_username_field_prompt", "Min %d Max %d chars");
        locales.put("register_page_password_field_prompt", "Min %d Max %d chars");
        locales.put("student", "Student");
        locales.put("teacher", "Teacher");
        locales.put("error", "Error");
        locales.put("error_username_too_short", "This username is too short.");
        locales.put("error_username_too_long", "This username is too long.");
        locales.put("error_password_too_short", "This password is too short.");
        locales.put("error_password_too_long", "This password is too long.");
        locales.put("error_user_type_not_selected", "No user type was selected");
        locales.put("error_username_already_in_use", "This username is already in use.");
        locales.put("error_user_does_not_exist", "This user does not exist.");
        locales.put("error_wrong_password", "You entered a wrong password");
        locales.put("success", "Success");
        locales.put("success_register", "You successfully registered!");
        locales.put("your_notes", "Your Notes");
        locales.put("browse_notes", "Browse Notes");
        locales.put("shared_notes", "Shared Notes");
        locales.put("reported_notes", "Reported Notes");
        locales.put("folder_created", "Folder was created");
        locales.put("folder_name_too_short", "Folder name is too short");
        locales.put("folder_already_exists", "This folder already exists");
        locales.put("create_folder", "Create Folder");
        locales.put("name", "Name");
    }

    public static String get(String key) {
        return locales.get(key);
    }
}
