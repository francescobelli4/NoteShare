package app_controllers;

import app.App;
import models.note.NoteModel;
import utils.PDFWrapper;

import java.io.File;

public class NotesController {

    private NotesController() {}

    public static void createNote(String name, File pdf) {
        NoteModel note = new NoteModel(name, new PDFWrapper(pdf));
        App.getNoteDAO().save(note, App.getUser().getActiveFolder());
    }
}