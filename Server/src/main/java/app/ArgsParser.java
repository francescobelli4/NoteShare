package app;

public class ArgsParser {

    private ArgsParser() {}

    public static Options parseArgs(String[] args) throws IllegalArgumentException {

        Options options = new Options();
        if (args.length != 1) {
            throw new IllegalArgumentException("Illegal number of args: appMode");
        }

        options.setAppMode(Options.AppMode.fromIdentifier(args[0]));

        return options;
    }
}
