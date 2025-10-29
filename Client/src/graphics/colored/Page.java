package graphics.colored;

public enum Page {
    ACCESS("colored/pages/main_pages/AccessPage.fxml"),
    NOTIFICATION("colored/pages/notifications/Notification.fxml"),
    ACCESS_FORM("colored/pages/forms/AccessForm.fxml"),
    REGISTER_FORM("colored/pages/forms/RegisterForm.fxml"),
    LOGIN_FORM("colored/pages/forms/LoginForm.fxml"),
    HOME_PAGE("colored/pages/main_pages/HomePage.fxml"),
    STUDENT_HOME_PAGE_LEFT_BAR("colored/pages/forms/StudentHomePageLeftBar.fxml"),
    TEACHER_HOME_PAGE_LEFT_BAR("colored/pages/forms/AdministratorHomePageLeftBar.fxml"),
    ADMINISTRATOR_HOME_PAGE_LEFT_BAR("colored/pages/forms/TeacherHomePageLeftBar.fxml"),
    STUDENT_HOME_PAGE_TOOLS_BAR("colored/pages/forms/StudentHomePageToolsBar.fxml"),
    FOLDER_CREATION_DIALOGUE("colored/pages/dialogues/FolderCreationDialogue.fxml"),
    NOTE_CREATION_DIALOGUE("colored/pages/dialogues/NoteCreationDialogue.fxml"),
    FOLDERS_CONTAINER("colored/pages/forms/FoldersContainerForm.fxml"),
    FOLDER_ELEMENT("colored/pages/elements/FolderElement.fxml"),
    NOTE_ELEMENT("colored/pages/elements/NoteElement.fxml");


    private String path;

    Page(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
