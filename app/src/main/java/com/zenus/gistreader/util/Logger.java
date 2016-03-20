package com.zenus.gistreader.util;

import android.util.Log;

public class Logger {
    /**
     * write messages to log, may control it from settings
     */
    public static boolean DEBUG = true;


    /**
     * log a simple message
     *
     * @param tag
     * @param message
     */
    public static void log(String tag, String message) {
        if (DEBUG)
            Log.i(tag, message);
    }

    /**
     * log a throwable
     *
     * @param tag
     * @param message
     * @param e
     */
    public static void log(String tag, String message, Throwable e) {
        if (DEBUG)
            Log.i(tag, message, e);
    }
}
