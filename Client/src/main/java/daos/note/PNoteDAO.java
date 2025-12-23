package daos.note;


import models.folder.FolderModel;
import models.note.NoteModel;

public class PNoteDAO implements NoteDAO {

    @Override
    public void save(NoteModel note, FolderModel folder) {
        //TODO
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
