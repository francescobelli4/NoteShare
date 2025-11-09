package persistency.nonpersistent.daos;

import app.mvc.models.FolderModel;
import app.mvc.models.NoteModel;
import persistency.shared.daos.NoteDAO;

public class NPNoteDAO implements NoteDAO {
    @Override
    public void save(NoteModel note, FolderModel parentFolder) {
        parentFolder.addNote(note);
    }

    @Override
    public NoteModel searchByPath(String path) {
        return null;
    }

    @Override
    public NoteModel searchByPath(String path, FolderModel folder) {
        return null;
    }
}
