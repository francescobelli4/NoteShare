package daos.note;

import models.folder.FolderModel;
import models.note.NoteModel;

public class NPNoteDAO implements NoteDAO {
    @Override
    public void save(NoteModel note, FolderModel parentFolder) {
        parentFolder.addNote(note);
    }

    @Override
    public void delete(NoteModel note) {
        note.getParentFolder().deleteNote(note);
    }

    @Override
    public NoteModel searchByPath(String path, FolderModel folder) {
        return folder.searchNote(path);
    }
}
