package com.skocken.testing;

import com.skocken.tictactoeia.BuildConfig;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JULog {

    private static final String CLASS_NAME = JULog.class.getName();

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS",
            Locale.US);

    private static final boolean LONG_VERSION = false;

    private static boolean sMute = true;

    private static List<Element> sElementList = new ArrayList<>();

    public static void setMute(boolean enable) {
        sMute = enable;
    }

    public static void clearLastElements() {
        sElementList.clear();
    }

    public static List<Element> getLastElements(int nLast) {
        int size = sElementList.size();
        if (size == 0) {
            return null;
        }
        int begin = Math.max(0, size - nLast);
        return sElementList.subList(begin, size);
    }

    public static Element getLastElement() {
        if (sElementList.isEmpty()) {
            return null;
        }
        return sElementList.get(sElementList.size() - 1);
    }

    public static void logMethodName() {
        StackTraceElement firstStackTraceElement = getFirstStackTraceElement();
        String msg = String.format("At %s (#%d)",
                firstStackTraceElement.getMethodName(),
                firstStackTraceElement.getLineNumber());
        println(0, Log.DEBUG, firstStackTraceElement.getFileName(), msg);
    }

    public static int println(int bufID,
            int priority, String tag, String msg) {
        sElementList.add(new Element(bufID, priority, tag, msg));
        if (sMute) {
            return 0;
        }

        StringBuffer sb = new StringBuffer();
        if (LONG_VERSION) {
            sb.append(DATE_FORMAT.format(new Date()));
            sb.append("    JUnit/");
            sb.append(BuildConfig.APPLICATION_ID);
            sb.append(" ");
        }

        switch (priority) {
            case Log.VERBOSE:
                sb.append("V/");
                break;
            case Log.DEBUG:
                sb.append("D/");
                break;
            case Log.INFO:
                sb.append("I/");
                break;
            case Log.WARN:
                sb.append("W/");
                break;
            case Log.ERROR:
                sb.append("E/");
                break;
            case Log.ASSERT:
                sb.append("A/");
                break;
            default:
                sb.append("?/");
        }
        sb.append(tag);
        sb.append("﹕ ");
        sb.append(msg);
        System.out.println(sb.toString());
        return 0;
    }

    private static StackTraceElement getFirstStackTraceElement() {
        StackTraceElement firstStackTraceElement = null;
        StackTraceElement[] stacks = (new Throwable()).getStackTrace();
        if (stacks != null) {
            // fetch the stack trace to get the method name which call this log
            for (StackTraceElement ste : stacks) {
                if (!CLASS_NAME.equals(ste.getClassName())) {
                    firstStackTraceElement = ste;
                    break;
                }
            }
        }
        return firstStackTraceElement;
    }

    public static class Element {

        public final int bufID;

        public final int priority;

        public final String tag;

        public final String msg;

        public Element(int bufID, int priority, String tag, String msg) {
            this.bufID = bufID;
            this.priority = priority;
            this.tag = tag;
            this.msg = msg;
        }
    }
}
