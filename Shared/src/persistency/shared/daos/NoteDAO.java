package persistency.shared.daos;

import persistency.shared.Folder;
import persistency.shared.entities.NoteEntity;

public interface NoteDAO {

    NoteEntity createNote(String name, String fileRelativePath);

    NoteEntity getNote(Folder parentFolder, String name);

    void saveNote(Folder parentFolder, NoteEntity note);
}
