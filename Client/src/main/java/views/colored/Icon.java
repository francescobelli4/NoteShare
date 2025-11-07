package views.colored;

public enum Icon {

    APPICON("/colored/pages/styles/icons/app_icon.png"),
    ERROR("/colored/pages/styles/icons/error.png"),
    SUCCESS("/colored/pages/styles/icons/success.png");

    private String path;

    Icon(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
