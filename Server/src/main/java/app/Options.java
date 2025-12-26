package app;

import java.util.ArrayList;
import java.util.List;

public class Options {

    private AppMode appMode = AppMode.DEMO;

    public AppMode getAppMode() {
        return appMode;
    }

    public void setAppMode(AppMode appMode) {
        this.appMode = appMode;
    }

    public enum AppMode {
        DEMO("demo"),
        RELEASE("release");

        private final String identifier;

        AppMode(String identifier) {
            this.identifier = identifier;
        }

        public static AppMode fromIdentifier(String id) throws IllegalArgumentException {
            List<String> legalIdentifiers = new ArrayList<>();

            for (AppMode appMode : values()) {
                legalIdentifiers.add(appMode.identifier);
                if (appMode.identifier.equalsIgnoreCase(id)) {
                    return appMode;
                }
            }
            throw new IllegalArgumentException("Invalid app mode: " + id + ". Use " + legalIdentifiers + " instead.");
        }
    }
}
