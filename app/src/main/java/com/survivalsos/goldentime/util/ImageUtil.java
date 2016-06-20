package com.survivalsos.goldentime.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.InputStream;

/**
 * Created by kiho on 2016. 6. 20..
 */
public class ImageUtil {
    public static Drawable loadDrawableFromAssets(Context context, String path) {
        InputStream stream = null;
        try {
            stream = context.getAssets().open(path);
            return Drawable.createFromStream(stream, null);
        } catch (Exception ignored) {
            DebugUtil.showDebug("ImageUtil, loadDrawableFromAssets(), catch :: " + ignored.getMessage());
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception ignored) {
            }
        }

        return null;
    }
}
