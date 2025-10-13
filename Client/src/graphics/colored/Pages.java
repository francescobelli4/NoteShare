package graphics.colored;

public enum Pages {
    ACCESS("colored/pages/main_pages/AccessPage.fxml"),
    NOTIFICATION("colored/pages/secondary_pages/Notification.fxml"),
    ACCESS_FORM("colored/pages/secondary_pages/AccessForm.fxml"),
    REGISTER_FORM("colored/pages/secondary_pages/RegisterForm.fxml"),
    LOGIN_FORM("colored/pages/secondary_pages/LoginForm.fxml"),
    STUDENT_HOME("colored/pages/main_pages/StudentHomePage.fxml");

    private String path;

    Pages(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
