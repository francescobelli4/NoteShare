package locales;

import app.App;

import java.util.HashMap;
import java.util.Map;

public class Locales {

    private Locales() {}

    private static final Map<String, String> localesLabels = new HashMap<>();

    public static void initializeLocales() {

        if (App.Options.getLanguage() == App.Options.Lang.EN) {

            localesLabels.put("register", "Register");
            localesLabels.put("login", "Login");
            localesLabels.put("username", "Username");
            localesLabels.put("password", "Password");
            localesLabels.put("register_page_username_field_prompt", "Min %d Max %d chars");
            localesLabels.put("register_page_password_field_prompt", "Min %d Max %d chars");
            localesLabels.put("student", "Student");
            localesLabels.put("teacher", "Teacher");
            localesLabels.put("error", "Error");
            localesLabels.put("error_username_too_short", "This username is too short.");
            localesLabels.put("error_username_too_long", "This username is too long.");
            localesLabels.put("error_password_too_short", "This password is too short.");
            localesLabels.put("error_password_too_long", "This password is too long.");
            localesLabels.put("error_user_type_not_selected", "No user type was selected");
            localesLabels.put("error_username_already_in_use", "This username is already in use.");
            localesLabels.put("error_user_does_not_exist", "This user does not exist.");
            localesLabels.put("error_wrong_password", "You entered a wrong password");
            localesLabels.put("success", "Success");
            localesLabels.put("success_register", "You successfully registered!");
            localesLabels.put("your_notes", "Your Notes");
            localesLabels.put("browse_notes", "Browse Notes");
            localesLabels.put("shared_notes", "Shared Notes");
            localesLabels.put("reported_notes", "Reported Notes");
            localesLabels.put("folder_created", "Folder was created");
            localesLabels.put("folder_name_too_short", "Folder name is too short");
            localesLabels.put("folder_already_exists", "This folder already exists");
            localesLabels.put("create_folder", "Create Folder");
            localesLabels.put("note_created", "Note was created");
            localesLabels.put("note_name_too_short", "Note name is too short");
            localesLabels.put("note_already_exists", "This note already exists");
            localesLabels.put("create_note", "Create Note");
            localesLabels.put("choose_pdf", "Choose PDF");
            localesLabels.put("note_pdf_not_set", "You did not choose a PDF file");
            localesLabels.put("name", "Name");
            localesLabels.put("info", "Info");
            localesLabels.put("register_welcome", "Welcome to NoteShare!");
        } else {
            localesLabels.put("register", "Register");
            localesLabels.put("login", "Login");
            localesLabels.put("username", "Username");
            localesLabels.put("password", "Password");
            localesLabels.put("register_page_username_field_prompt", "Min %d Max %d chars");
            localesLabels.put("register_page_password_field_prompt", "Min %d Max %d chars");
            localesLabels.put("student", "Student");
            localesLabels.put("teacher", "Teacher");
            localesLabels.put("error", "Error");
            localesLabels.put("error_username_too_short", "This username is too short.");
            localesLabels.put("error_username_too_long", "This username is too long.");
            localesLabels.put("error_password_too_short", "This password is too short.");
            localesLabels.put("error_password_too_long", "This password is too long.");
            localesLabels.put("error_user_type_not_selected", "No user type was selected");
            localesLabels.put("error_username_already_in_use", "This username is already in use.");
            localesLabels.put("error_user_does_not_exist", "This user does not exist.");
            localesLabels.put("error_wrong_password", "You entered a wrong password");
            localesLabels.put("success", "Success");
            localesLabels.put("success_register", "You successfully registered!");
            localesLabels.put("your_notes", "Your Notes");
            localesLabels.put("browse_notes", "Browse Notes");
            localesLabels.put("shared_notes", "Shared Notes");
            localesLabels.put("reported_notes", "Reported Notes");
            localesLabels.put("folder_created", "Folder was created");
            localesLabels.put("folder_name_too_short", "Folder name is too short");
            localesLabels.put("folder_already_exists", "This folder already exists");
            localesLabels.put("create_folder", "Create Folder");
            localesLabels.put("note_created", "Note was created");
            localesLabels.put("note_name_too_short", "Note name is too short");
            localesLabels.put("note_already_exists", "This note already exists");
            localesLabels.put("create_note", "Create Note");
            localesLabels.put("choose_pdf", "Choose PDF");
            localesLabels.put("note_pdf_not_set", "You did not choose a PDF file");
            localesLabels.put("name", "Name");
            localesLabels.put("info", "Info");
            localesLabels.put("register_welcome", "Welcome to NoteShare!");
        }
    }

    public static String get(String key) {
        return localesLabels.get(key);
    }
}
