package views;

public enum Icon {

    APPICON("/colored/styles/icons/app_icon.png"),
    ERROR("/colored/styles/icons/error.png"),
    SUCCESS("/colored/styles/icons/success.png");

    private String path;

    Icon(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
