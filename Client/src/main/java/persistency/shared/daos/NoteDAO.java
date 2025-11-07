package persistency.shared.daos;

import app.mvc.models.FolderModel;
import app.mvc.models.NoteModel;

public interface NoteDAO {
    void save(NoteModel note, FolderModel parentFolder);
    NoteModel searchByPath(String path);
    NoteModel searchByPath(String path, FolderModel parentFolder);
}
