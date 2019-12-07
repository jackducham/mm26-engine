package mech.mania.engine.logging;

public class GameLogger {
    public enum LogLevel {
        INFO,
        ERROR,
        DEBUG
    }

    public static void log(LogLevel level, String msg) {
        // TODO: add colors to print statements
        switch (level) {
            case INFO:

                break;
            case ERROR:

                break;
            case DEBUG:

                break;
        }

        System.out.println(msg);
    }
}
