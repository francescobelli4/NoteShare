package persistency.persistent.daos;

import app.mvc.models.FolderModel;
import app.mvc.models.NoteModel;
import persistency.shared.daos.NoteDAO;

public class PNoteDAO implements NoteDAO {

    @Override
    public void save(NoteModel note, FolderModel folder) {

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
