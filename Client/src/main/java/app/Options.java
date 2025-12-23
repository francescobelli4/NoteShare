package app;

import exceptions.ArgsException;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Options {

    private AppMode appMode = AppMode.DEMO;
    private Lang language = Lang.EN;
    private UiType uiType = UiType.COLORED;
    private final String rootFolderPath = Utils.getOSLocalPath();

    public AppMode getAppMode() {
        return appMode;
    }

    public void setAppMode(AppMode appMode) {
        this.appMode = appMode;
    }

    public Lang getLanguage() {
        return language;
    }

    public void setLanguage(Lang language) {
        this.language = language;
    }

    public UiType getUiType() {
        return uiType;
    }

    public void setUiType(UiType uiType) {
        this.uiType = uiType;
    }

    public String getRootFolderPath() {
        return rootFolderPath;
    }

    public enum AppMode {
        DEMO("demo"),
        RELEASE("release");

        private final String identifier;

        AppMode(String identifier) {
            this.identifier = identifier;
        }

        public static AppMode fromIdentifier(String id) throws ArgsException {
            List<String> legalIdentifiers = new ArrayList<>();

            for (AppMode appMode : values()) {
                legalIdentifiers.add(appMode.identifier);
                if (appMode.identifier.equalsIgnoreCase(id)) {
                    return appMode;
                }
            }
            throw new ArgsException("Invalid app mode: " + id + ". Use " + legalIdentifiers + " instead.");
        }
    }

    public enum Lang {
        IT("it"),
        EN("en");

        private final String identifier;

        Lang(String identifier) {
            this.identifier = identifier;
        }

        public static Lang fromIdentifier(String id) throws ArgsException {
            List<String> legalIdentifiers = new ArrayList<>();

            for (Lang lang : values()) {
                legalIdentifiers.add(lang.identifier);
                if (lang.identifier.equalsIgnoreCase(id)) {
                    return lang;
                }
            }
            throw new ArgsException("Invalid lang: " + id + ". Use " + legalIdentifiers + " instead.");
        }
    }

    public enum UiType {
        COLORED("colored"),
        OLD("old");

        private final String identifier;

        UiType(String identifier) {
            this.identifier = identifier;
        }

        public static UiType fromIdentifier(String id) throws ArgsException {
            List<String> legalIdentifiers = new ArrayList<>();

            for (UiType uiType : values()) {
                legalIdentifiers.add(uiType.identifier);
                if (uiType.identifier.equalsIgnoreCase(id)) {
                    return uiType;
                }
            }
            throw new ArgsException("Invalid ui type: " + id + ". Use " + legalIdentifiers + " instead.");
        }
    }
}
