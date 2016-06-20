package com.survivalsos.goldentime.util;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;


public class DebugUtil {
    public static boolean IS_DEBUG_LOG = true;
    public static boolean IS_TOAST_LOG = true;
    public static String DEBUG_TAG = "[Survival] : GoldenTime";

    public static void showDebug(String msg) {
        if (TextUtil.isNull(msg))
            return;
        if (IS_DEBUG_LOG) {
            Log.d(DEBUG_TAG, msg);
        }
    }

    public static void showDebug(String tag, String location, String msg){
        if (TextUtil.isNull(msg))
            return;
        if (IS_DEBUG_LOG) {
            Log.d(DEBUG_TAG, tag +", " + location + " :: " + msg);
        }
    }

    public static void showToast(Context context, int msg) {
        if (IS_TOAST_LOG) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

    public static void showToast(Context context, String msg) {
        if (IS_TOAST_LOG) {
            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
