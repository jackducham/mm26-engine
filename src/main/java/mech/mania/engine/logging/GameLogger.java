package mech.mania.engine.logging;

public class GameLogger {
    public enum LogLevel {
        ERROR(2),
        INFO(1),
        DEBUG(0);

        // each enum should have a priority to compare against
        private int priority;
        LogLevel(int priority) {
            this.priority = priority;
        }
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

    private static LogLevel printLevel = LogLevel.DEBUG;

    /**
     * Print using a specific level and a label.
     * @param level a LogLevel that specifies which level to print at
     * @param label a label to use, specifying where the message is coming from
     * @param msg the actual message
     */
    public static void log(LogLevel level, String label, String msg) {

        if (level.priority > printLevel.priority) {
            // don't print if priority is too low
            return;
        }

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

    /**
     * Set the minimum print level. By default it is `LogLevel.DEBUG`.
     * Priority goes: `ERROR` > `INFO` > `DEBUG`.
     * @param level LogLevel to set the minimum to
     */
    public static void setPrintLevel(LogLevel level) {
        printLevel = level;
    }
}
