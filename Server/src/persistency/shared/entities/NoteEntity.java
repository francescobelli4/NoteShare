package persistency.shared.entities;

public class NoteEntity {
    private String name;
    private String filePath;

    public NoteEntity(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public String getFilePath() {
        return filePath;
    }
}
