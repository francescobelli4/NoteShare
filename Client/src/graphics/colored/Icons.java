package graphics.colored;

public enum Icons {

    APPICON("src/graphics/colored/pages/styles/icons/app_icon.png"),
    ERROR("src/graphics/colored/pages/styles/icons/error.png"),
    SUCCESS("src/graphics/colored/pages/styles/icons/success.png");

    private String path;

    Icons(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
