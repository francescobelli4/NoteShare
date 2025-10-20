package memory.nonpersistent.daos;

import memory.shared.entities.NoteEntity;
import memory.shared.daos.NoteDAO;

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
    public void saveNote(NoteEntity noteEntity) {

    }

    @Override
    public NoteEntity getNote(String path) {
        return null;
    }
}
