package daos.note;
import models.folder.FolderModel;
import models.note.NoteModel;

public interface NoteDAO {
    void save(NoteModel note, FolderModel parentFolder);
    NoteModel searchByPath(String path);
    NoteModel searchByPath(String path, FolderModel parentFolder);
}
