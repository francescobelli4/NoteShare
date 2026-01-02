package app_controllers;

import app.AppContext;
import exceptions.DuplicateNoteException;
import exceptions.InvalidNoteNameException;
import exceptions.InvalidNotePDFException;
import models.note.NoteModel;
import utils.PDFWrapper;

import java.io.File;

public class NotesController {

    private NotesController() {}

    public static void createNote(String name, File pdf) throws InvalidNoteNameException, InvalidNotePDFException, DuplicateNoteException {

        if (name.isEmpty()) {
            throw new InvalidNoteNameException("note_name_too_short");
        }

        if (name.length() > 15) {
            throw new InvalidNoteNameException("note_name_too_long");
        }

        if (AppContext.getInstance().getCurrentUser().getActiveFolder().searchNote(name) != null) {
            throw new DuplicateNoteException();
        }

        if (pdf == null) {
            throw new InvalidNotePDFException();
        }

        NoteModel note = new NoteModel(name, new PDFWrapper(pdf));
        AppContext.getInstance().getNoteDAO().save(note, AppContext.getInstance().getCurrentUser().getActiveFolder());
    }

    public static void copyNote(NoteModel note) {
        AppContext.getInstance().getCurrentUser().setCopiedElement(note.copy());
    }

    public static void deleteNote(NoteModel note) {
        AppContext.getInstance().getNoteDAO().delete(note);
    }
}