package mech.mania.engine.logging;

public class GameLogger {
    public enum LogLevel {
        INFO,
        ERROR,
        DEBUG
    }

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    public static void log(LogLevel level, String label, String msg) {
        switch (level) {
            case INFO:
                System.out.print(ANSI_WHITE);
                break;
            case ERROR:
                System.out.print(ANSI_RED);
                break;
            case DEBUG:
                System.out.print(ANSI_GREEN);
                break;
        }
        System.out.print("[" + label + "] " + msg);
        System.out.println(ANSI_RESET);
    }
}
