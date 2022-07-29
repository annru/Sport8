package com.sh.sport.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ToastUtil {

    private static final String TAG = "ToastUtil";

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;
    private static boolean initialized;
    private static Mode sDefaultMode;
    private static int sDuration;

    /**
     * Initialize util.
     *
     * @param context The context to use.
     */
    public static void initialize(Context context) {
        initialize(context, Mode.REPLACEABLE);
    }

    /**
     * Initialize util with the mode from user.
     *
     * @param context The context to use.
     * @param mode    The default display mode tu use. Either{@link Mode#NORMAL} or {@link Mode#REPLACEABLE}
     */
    public static void initialize(Context context, Mode mode) {
        if (initialized) {
            Log.w(TAG, "Invalid initialize, ToastUtil is already initialized");
            return;
        }

        if (context == null) {
            throw new NullPointerException("context can not be null");
        }

        sContext = context.getApplicationContext();

        // nullable
        sDefaultMode = mode;

        initialized = true;
    }

    /**
     * Display mode
     */
    public enum Mode {

        /**
         * Show as a normal toast. This mode could be user-definable.  This is the default.
         */
        NORMAL,

        /**
         * When the toast is shown to the user , the text will be replaced if call the show() method again.  This mode could be user-definable.
         */
        REPLACEABLE
    }

    /**
     * Show a toast with the text form a resource.
     *
     * @param resId The resource id of the string resource to use.
     */
    public static void show(int resId) {
        show(sContext.getText(resId), false, sDefaultMode);
    }

    /**
     * Show a toast.
     *
     * @param text The text to show.
     */
    public static void show(CharSequence text) {
        if (text != null) {
            show(text, false, sDefaultMode);
        }
    }

    /**
     * Show a toast with the text form a resource.
     *
     * @param resId        The resource id of the string resource to use.
     * @param durationLong Whether the toast show for a long period of time?
     */
    public static void show(int resId, boolean durationLong) {
        show(sContext.getText(resId), durationLong, sDefaultMode);
    }

    /**
     * Show a toast.
     *
     * @param text         The text to show.
     * @param durationLong Whether the toast show for a long period of time?
     */
    public static void show(CharSequence text, boolean durationLong) {
        show(text, durationLong, sDefaultMode);
    }

    /**
     * Show a toast with the text form a resource.
     *
     * @param resId The resource id of the string resource to use.
     * @param mode  The display mode to use.  Either {@link Mode#NORMAL} or {@link Mode#REPLACEABLE}
     */
    public static void show(int resId, Mode mode) {
        show(sContext.getText(resId), false, mode);
    }

    /**
     * Show a toast.
     *
     * @param text The text to show.
     * @param mode The display mode to use.  Either {@link Mode#NORMAL} or {@link Mode#REPLACEABLE}
     */
    public static void show(CharSequence text, Mode mode) {
        show(text, false, mode);
    }

    /**
     * Show a toast with the text form a resource.
     *
     * @param resId        resId The resource id of the string resource to use.
     * @param durationLong Whether the toast show for a long period of time?
     * @param mode         The display mode to use.  Either {@link Mode#NORMAL} or {@link Mode#REPLACEABLE}
     */
    public static void show(int resId, boolean durationLong, Mode mode) {
        show(sContext.getText(resId), durationLong, mode);
    }

    /**
     * Show a toast.
     *
     * @param text         The text to show.
     * @param durationLong Whether the toast show for a long period of time?
     * @param mode         The display mode to use.  Either {@link Mode#NORMAL} or {@link Mode#REPLACEABLE}
     */
    public static void show(CharSequence text, boolean durationLong, Mode mode) {
        final int duration = durationLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;

        if (mode != Mode.REPLACEABLE) {
            Toast.makeText(sContext, text, duration).show();
            return;
        }
        Toast toast = new Toast(sContext);
        if (sDuration != duration) {
            sDuration = duration;
            toast = Toast.makeText(sContext, text, duration);
        } else {
            try {
                toast.setText(text);
            } catch (RuntimeException e) {
                toast = Toast.makeText(sContext, text, duration);
            }
        }
        toast.show();
    }


    private ToastUtil() {
    }
}