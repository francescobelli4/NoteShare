package memory.shared.daos;

import memory.shared.entities.NoteEntity;

public interface NoteDAO {

    void saveNote(NoteEntity noteEntity);

    NoteEntity getNote(String path);
}
