package memory.shared.daos;

import memory.shared.Folder;
import memory.shared.entities.NoteEntity;

public interface NoteDAO {

    NoteEntity createNote(String name, String fileRelativePath);

    NoteEntity getNote(Folder parentFolder, String name);

    void saveNote(Folder parentFolder, NoteEntity note);
}
