package app;

import exceptions.ArgsException;

public class ArgsParser {

    private ArgsParser() {}

    public static Options parseArgs(String[] args) throws ArgsException, IllegalArgumentException {

        Options options = new Options();
        if (args.length < 3) {
            throw new ArgsException("Illegal number of args: appMode, language, uiType");
        }

        options.setAppMode(Options.AppMode.fromIdentifier(args[0]));
        options.setLanguage(Options.Lang.fromIdentifier(args[1]));
        options.setUiType(Options.UiType.fromIdentifier(args[2]));

        return options;
    }
}
