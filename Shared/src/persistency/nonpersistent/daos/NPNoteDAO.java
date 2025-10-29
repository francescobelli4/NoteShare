package persistency.nonpersistent.daos;

import persistency.shared.Folder;
import persistency.shared.entities.NoteEntity;
import persistency.shared.daos.NoteDAO;

import java.util.Objects;

public class NPNoteDAO implements NoteDAO {

    private static NPNoteDAO instance;
    private NPNoteDAO(){}
    public static NPNoteDAO getInstance() {
        if (instance == null) {
            instance = new NPNoteDAO();
        }

        return instance;
    }


    @Override
    public NoteEntity createNote(String name, String fileRelativePath) {
        return new NoteEntity(name, fileRelativePath);
    }

    @Override
    public NoteEntity getNote(Folder parentFolder, String name) {

        for (NoteEntity note : parentFolder.getNotes()) {
            if (Objects.equals(note.getName(), name)) {
                return note;
            }
        }

        return null;
    }

    @Override
    public void saveNote(Folder parentFolder, NoteEntity note) {
        parentFolder.addNote(note);
    }
}
