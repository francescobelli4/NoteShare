package locales;

import app.App;

import java.util.HashMap;
import java.util.Map;

public class Locales {

    private Locales() {}

    private static final Map<String, String> localesLabels = new HashMap<>();

    private static void initializeENLocales() {
        localesLabels.put("note_share", "Note Share");
        localesLabels.put("register", "Register");
        localesLabels.put("login", "Login");
        localesLabels.put("username", "Username");
        localesLabels.put("password", "Password");
        localesLabels.put("register_page_username_field_prompt", "Min %d Max %d chars");
        localesLabels.put("register_page_password_field_prompt", "Min %d Max %d chars");
        localesLabels.put("student", "Student");
        localesLabels.put("teacher", "Teacher");
        localesLabels.put("communication_failed", "Error communicating with server");
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
        localesLabels.put("access", "Access");
        localesLabels.put("search_for_a_note", "Search for a note...");
    }

    private static void initializeITLocales() {
        localesLabels.put("note_share", "Note Share");
        localesLabels.put("register", "Registrati");
        localesLabels.put("login", "Accedi");
        localesLabels.put("username", "Nome utente");
        localesLabels.put("password", "Password");
        localesLabels.put("register_page_username_field_prompt", "Minimo %d Massimo %d caratteri");
        localesLabels.put("register_page_password_field_prompt", "Minimo %d Massimo %d caratteri");
        localesLabels.put("student", "Studente");
        localesLabels.put("teacher", "Insegnante");
        localesLabels.put("error", "Errore");
        localesLabels.put("error_username_too_short", "Questo nome utente è troppo corto");
        localesLabels.put("error_username_too_long", "Questo nome utente è troppo lungo.");
        localesLabels.put("error_password_too_short", "Questa password è troppo corta.");
        localesLabels.put("error_password_too_long", "Questa password è troppo lunga.");
        localesLabels.put("error_user_type_not_selected", "Nessun tipo di utente è stato selezionato");
        localesLabels.put("error_username_already_in_use", "Questo nome utente è già in uso.");
        localesLabels.put("error_user_does_not_exist", "Questo utente non esiste.");
        localesLabels.put("error_wrong_password", "Hai inserito una password errata");
        localesLabels.put("success", "Successo");
        localesLabels.put("success_register", "Registrazione effettuata con successo!");
        localesLabels.put("your_notes", "Le tue Note");
        localesLabels.put("browse_notes", "Sfoglia Note");
        localesLabels.put("shared_notes", "Note Condivise");
        localesLabels.put("reported_notes", "Note Segnalate");
        localesLabels.put("folder_created", "Cartella creata");
        localesLabels.put("folder_name_too_short", "Il nome della cartella è troppo corto");
        localesLabels.put("folder_already_exists", "Questa cartella esiste già");
        localesLabels.put("create_folder", "Crea Cartella");
        localesLabels.put("note_created", "Nota creata");
        localesLabels.put("note_name_too_short", "Il nome della nota è troppo corto");
        localesLabels.put("note_already_exists", "Questa nota esiste già");
        localesLabels.put("create_note", "Crea Nota");
        localesLabels.put("choose_pdf", "Scegli PDF");
        localesLabels.put("note_pdf_not_set", "Non hai scelto un file PDF");
        localesLabels.put("name", "Nome");
        localesLabels.put("info", "Info");
        localesLabels.put("register_welcome", "Benvenuto su NoteShare!");
    }

    public static void initializeLocales() {

        if (App.Options.getLanguage() == App.Options.Lang.EN) {

            initializeENLocales();
        } else {
            initializeITLocales();
        }
    }

    public static String get(String key) {
        return localesLabels.get(key);
    }
}
