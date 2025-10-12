package graphics.colored;

public enum Icons {

    APPICON("/graphics/colored/pages/styles/icons/app_icon.png"),
    ERROR("/graphics/colored/pages/styles/icons/error.png"),
    SUCCESS("/graphics/colored/pages/styles/icons/success.png");

    private String path;

    Icons(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
