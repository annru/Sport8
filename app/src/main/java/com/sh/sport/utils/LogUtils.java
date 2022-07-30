package com.sh.sport.utils;

import android.os.FileUtils;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

public class LogUtils {
    public static final int LEVEL_ALL = 7;

    public static final int LEVEL_DEBUG = 2;

    public static final int LEVEL_ERROR = 5;

    public static final int LEVEL_INFO = 3;

    public static final int LEVEL_OFF = 0;

    public static final int LEVEL_SYSTEM = 6;

    public static final int LEVEL_VERBOSE = 1;

    public static final int LEVEL_WARN = 4;

    private static int mDebuggable;

    private static final Object mLogLock;

    private static String mTag = "LogUtils";

    private static long mTimestamp;

    static {
        mDebuggable = 7;
        mTimestamp = 0L;
        mLogLock = new Object();
    }

    public static void d(String paramString) {
        if (mDebuggable >= 2)
            Log.d(mTag, paramString);
    }

    public static void d(String paramString1, String paramString2) {
        if (mDebuggable >= 2)
            Log.d(paramString1, paramString2);
    }

    public static void e(String paramString) {
        if (mDebuggable >= 5)
            Log.e(mTag, paramString);
    }

    public static void e(String paramString1, String paramString2) {
        if (mDebuggable >= 5)
            Log.e(paramString1, paramString2);
    }

    public static void e(String paramString, Throwable paramThrowable) {
        if (mDebuggable >= 5 && paramString != null)
            Log.e(mTag, paramString, paramThrowable);
    }

    public static void e(Throwable paramThrowable) {
        if (mDebuggable >= 5)
            Log.e(mTag, "", paramThrowable);
    }

    public static void elapsed(String paramString) {
        long l1 = System.currentTimeMillis();
        long l2 = mTimestamp;
        mTimestamp = l1;
//        e("[Elapsed+ (l1 - l2) + "]+")";
    }

    public static void i(String paramString) {
        if (mDebuggable >= 3)
            Log.i(mTag, paramString);
    }

    public static void i(String paramString1, String paramString2) {
        if (mDebuggable >= 3)
            Log.i(paramString1, paramString2);
    }

    public static void log2File(String paramString1, String paramString2) {
        log2File(paramString1, paramString2, true);
    }

    public static void log2File(String paramString1, String paramString2, boolean paramBoolean) {
        synchronized (mLogLock) {
            StringBuilder stringBuilder = new StringBuilder();
//            this();
//            FileUtils.writeFile(stringBuilder.append(paramString1).append("\r\n").toString(), paramString2, paramBoolean);
            return;
        }
    }

    public static void logParamter(String paramString, String[]... paramVarArgs) {
        e("url = " + paramString);
        int i = paramVarArgs.length;
        for (byte b = 0; b < i; b++) {
            String[] arrayOfString = paramVarArgs[b];
            e(arrayOfString[0] + " = " + arrayOfString[1]);
        }
    }

    public static void msgStartTime(String paramString) {
        mTimestamp = System.currentTimeMillis();
//        if (!TextUtils.isEmpty(paramString))
//            e("[Started+ mTimestamp + "]" + paramString);
    }

    public static <T> void printArray(T[] paramArrayOfT) {
        if (paramArrayOfT != null && paramArrayOfT.length >= 1) {
            int i = paramArrayOfT.length;
            i("---begin---");
            for (byte b = 0; b < i; b++)
                i(b + ":" + paramArrayOfT[b].toString());
            i("---end---");
        }
    }

    public static <T> void printList(List<T> paramList) {
        if (paramList != null && paramList.size() >= 1) {
            int i = paramList.size();
            i("---begin---");
            for (byte b = 0; b < i; b++)
                i(b + ":" + paramList.get(b).toString());
            i("---end---");
        }
    }

    public static void s(String paramString) {
        if (mDebuggable >= 5)
            System.out.println(paramString);
    }

    public static void sf(String paramString) {
        if (mDebuggable >= 5)
            System.out.println("----------" + paramString + "----------");
    }

    public static void v(String paramString) {
        if (mDebuggable >= 1)
            Log.v(mTag, paramString);
    }

    public static void v(String paramString1, String paramString2) {
        if (mDebuggable >= 1)
            Log.v(paramString1, paramString2);
    }

    public static void w(String paramString) {
        if (mDebuggable >= 4)
            Log.w(mTag, paramString);
    }

    public static void w(String paramString1, String paramString2) {
        if (mDebuggable >= 4)
            Log.w(paramString1, paramString2);
    }

    public static void w(String paramString, Throwable paramThrowable) {
        if (mDebuggable >= 4 && paramString != null)
            Log.w(mTag, paramString, paramThrowable);
    }

    public static void w(Throwable paramThrowable) {
        if (mDebuggable >= 4)
            Log.w(mTag, "", paramThrowable);
    }
}
