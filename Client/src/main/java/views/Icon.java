package views;

public enum Icon {

    APPICON("/colored/styles/icons/app_icon.png"),
    ERROR("/colored/styles/icons/error.png"),
    SUCCESS("/colored/styles/icons/success.png"),
    BACK("/colored/styles/icons/back-button.png"),
    COIN("/colored/styles/icons/coin.png"),
    FILE("/colored/styles/icons/file.png"),
    FILE_PLUS("/colored/styles/icons/file_plus.png"),
    FOLDER("/colored/styles/icons/folder.png"),
    FOLDER_PLUS("/colored/styles/icons/folder_plus.png"),
    WEB("/colored/styles/icons/globe_with_meridians.png"),
    LEFT_ARROW("/colored/styles/icons/left-arrow.png"),
    TEACHER("/colored/styles/icons/female-teacher.png"),
    PLUS("/colored/styles/icons/plus_button.png"),
    NOTE_PAD("/colored/styles/icons/spiral_note_pad.png"),
    USER("/colored/styles/icons/user_image.png"),
    NOTIFICATION("/colored/styles/icons/message.png"),
    X("/colored/styles/icons/x-button.png");

    private String path;

    Icon(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
