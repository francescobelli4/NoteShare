package app_controllers;

import app.App;
import app.AppContext;
import models.note.NoteModel;
import utils.PDFWrapper;

import java.io.File;

public class NotesController {

    private NotesController() {}

    public static void createNote(String name, File pdf) {
        NoteModel note = new NoteModel(name, new PDFWrapper(pdf));
        AppContext.getInstance().getNoteDAO().save(note, AppContext.getInstance().getCurrentUser().getActiveFolder());
    }
}