package graphics.colored;

public enum Pages {
    ACCESS("colored/pages/main_pages/AccessPage.fxml"),
    NOTIFICATION("colored/pages/Notification.fxml");


    private String path;

    Pages(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
