package utils;

public enum MessageType {
    NOTE_SOLD("note_sold"),
    NOTE_BOUGHT("note_bought"),
    INFO("info");

    private final String type;

    MessageType(String type) {
        this.type = type;
    }

    public String getPath() {
        return this.type;
    }

    public static MessageType fromString(String type) {
        return switch (type) {
            case "note_sold" -> MessageType.NOTE_SOLD;
            case "note_bought" -> MessageType.NOTE_BOUGHT;
            case "info" -> MessageType.INFO;
            default -> null;
        };
    }
}
