package graphics.colored;

public enum Pages {
    ACCESS("colored/pages/main_pages/AccessPage.fxml"),
    NOTIFICATION("colored/pages/secondary_pages/Notification.fxml"),
    ACCESS_FORM("colored/pages/secondary_pages/AccessForm.fxml"),
    REGISTER_FORM("colored/pages/secondary_pages/RegisterForm.fxml"),
    LOGIN_FORM("colored/pages/secondary_pages/LoginForm.fxml"),
    HOME_PAGE("colored/pages/main_pages/HomePage.fxml"),
    STUDENT_HOME_PAGE_FORM("colored/pages/secondary_pages/StudentHomePageLeftBar.fxml"),
    TEACHER_HOME_PAGE_FORM("colored/pages/secondary_pages/AdministratorHomePageLeftBar.fxml"),
    ADMINISTRATOR_HOME_PAGE_FORM("colored/pages/secondary_pages/TeacherHomePageLeftBar.fxml");

    private String path;

    Pages(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
