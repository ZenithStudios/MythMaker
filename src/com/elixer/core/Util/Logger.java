package com.elixer.core.Util;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by aweso on 7/20/2017.
 */
public class Logger {

    public static enum Levels {
        INFO("[INFO]", System.out),
        CAUTION("[WARN]", System.out),
        ERROR("[ERR]", System.err),
        ERROREND("[FATAL]", System.err);

        private String levelText;
        private PrintStream stream;

        Levels(String levelText, PrintStream stream) {
            this.levelText = levelText;
            this.stream = stream;
        }

        public String getLevelText() {
            return levelText;
        }

        public PrintStream getStream() {
            return stream;
        }
    }

    public static void println(Object message, Levels level) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        level.getStream().println(level.getLevelText() + " " + sdf.format(cal.getTime()) + " | " + message);

        if(level == Levels.ERROREND)
            System.exit(1);
    }

    public static void println(Object message) {
        println(message, Levels.INFO);
    }

}
