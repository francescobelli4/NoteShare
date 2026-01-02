package views;

public enum Page {
    ACCESS("/colored/pages/main_pages/AccessPage.fxml"),
    NOTIFICATION("/colored/pages/notifications/Notification.fxml"),
    ACCESS_FORM("/colored/pages/forms/AccessForm.fxml"),
    REGISTER_FORM("/colored/pages/forms/RegisterForm.fxml"),
    LOGIN_FORM("/colored/pages/forms/LoginForm.fxml"),
    HOME_PAGE("/colored/pages/main_pages/HomePage.fxml"),
    VIEW_NOTE_PAGE("/colored/pages/main_pages/ViewNotePage.fxml"),
    LEFT_BAR("/colored/pages/forms/LeftBar.fxml"),
    LEFT_BAR_MENU_OPTIONS("/colored/pages/forms/MenuOptions.fxml"),
    LEFT_BAR_USER_DATA("/colored/pages/forms/UserData.fxml"),
    TOOLS_BAR("/colored/pages/forms/ToolsBar.fxml"),
    MESSAGES_CONTAINER("/colored/pages/forms/MessagesContainerForm.fxml"),
    FOLDER_CREATION_DIALOGUE("/colored/pages/dialogues/FolderCreationDialogue.fxml"),
    NOTE_CREATION_DIALOGUE("/colored/pages/dialogues/NoteCreationDialogue.fxml"),
    FOLDERS_CONTAINER("/colored/pages/forms/FoldersContainerForm.fxml"),
    FOLDER_ELEMENT("/colored/pages/elements/FolderElement.fxml"),
    NOTE_ELEMENT("/colored/pages/elements/NoteElement.fxml"),
    MESSAGE_ELEMENT("/colored/pages/elements/MessageElement.fxml"),
    FOLDERS_CONTAINER_ELEMENT_OPTIONS_FORM("/colored/pages/forms/FoldersContainerElementOptionsForm.fxml");

    private String path;

    Page(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
