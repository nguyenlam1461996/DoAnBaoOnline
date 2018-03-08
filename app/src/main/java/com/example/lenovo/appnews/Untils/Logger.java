package com.example.lenovo.appnews.Untils;

import java.util.Locale;

/**
 * Created by LENOVO on 3/2/2018.
 */

public class Logger {
    public static boolean isUsingJUnitTest = false;
    private String mTag;

    public Logger(String tag) {
        mTag = tag;
        if (!isUsingJUnitTest) {
        }
    }

    /**
     * Định dạng tin nhắn được cung cấp của người gọi và thêm vào các thông tin hữu ích như gọi ID luồng và tên phương thức.
     */
    private String buildMessage(String format, Object... args) {
        String msg  = "";
        String caller = "<unknown>";
        try {
            msg  = (args == null) ? format : String.format(Locale.US, format, args);
            StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

            caller = "<unknown>";
            // Walk up the stack looking for the first caller outside of VolleyLog.
            // It will be at least two frames up, so start there.
            for (int i = 2; i < trace.length; i++) {
                Class<?> clazz = trace[i].getClass();
                if (!clazz.equals(Logger.class)) {
                    String callingClass = trace[i].getClassName();
                    callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                    callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);

                    caller = callingClass + "." + trace[i].getMethodName();
                    break;
                }
            }
        } catch (Exception e) {
            msg = format;
            return msg;
        }
        return String.format(Locale.US, "[%d] %s: %s",
                Thread.currentThread().getId(), caller, msg);
    }

    public void d(String format, Object... args) {
        if (!isUsingJUnitTest) {
            String logText = buildMessage(format, args);
            android.util.Log.d(getTag(), logText);
        }
    }

    public void e(String format, Object... args) {
        if (!isUsingJUnitTest) {
            String logText = buildMessage(format, args);
            android.util.Log.e(getTag(), logText);
        }
    }

    public void e(Throwable tr, String format, Object... args) {
        if (!isUsingJUnitTest) {
            String logText = buildMessage(format, args);
            android.util.Log.e(getTag(), logText, tr);
        }
    }

    public void wtf(String format, Object... args) {
        if (!isUsingJUnitTest)
            android.util.Log.wtf(getTag(), buildMessage(format, args));
    }

    public void wtf(Throwable tr, String format, Object... args) {
        if (!isUsingJUnitTest)
            android.util.Log.wtf(getTag(), buildMessage(format, args), tr);
    }

    public void v(String format, Object... args) {
        if (!isUsingJUnitTest)
            android.util.Log.v(getTag(), buildMessage(format, args));
    }

    protected String getTag() {
        return mTag;
    }
}
