package com.survivalsos.goldentime.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.survivalsos.goldentime.R;

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

    public static ImageLoaderConfiguration GlobalImageLoaderConfiguration(Context context) {
        DisplayImageOptions options;
        ImageLoaderConfiguration config;

        /**info : https://github.com/nostra13/Android-Universal-Image-Loader/wiki/Useful-Info
         http://m.blog.naver.com/d_onepiece/100210301983
         http://d2.naver.com/helloworld/429368 */

        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_stub) //로딩 전 이미지
                .showImageOnLoading(R.color.c_ff222222)
                .resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.color.c_ff222222)
                .cacheInMemory(true)
//                    .cacheOnDisc(false) //deprecated
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(0))
                .build();

        config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
//                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
//                .memoryCacheSize(20 * 1024 * 1024) // 20 Mb
                .denyCacheImageMultipleSizesInMemory()
                .defaultDisplayImageOptions(options)
//                .diskCacheSize(20 * 1024 * 1024) //20 Mb
                .threadPoolSize(5)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
//                .discCacheFileNameGenerator(new Md5FileNameGenerator())

                .memoryCacheExtraOptions(480, 800)
                .diskCacheExtraOptions(480, 800, null)
//                .diskCacheExtraOptions(ImageUtil.getDeviceWidth(context), ImageUtil.getDeviceHeight(context), null)
//                    .writeDebugLogs() // Remove for release app
                .build();

        return config;
    }


    /**
     * 폰의 가로 사이즈를 구함
     *
     * @param mContext
     * @return
     */
    public static int getDeviceWidth(Context mContext) {
        int width = 0;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT > 12) {
            Point size = new Point();
            display.getSize(size);
            width = size.x;
        } else {
            width = display.getWidth();  // Deprecated
        }
        return width;
    }

    /**
     * 폰의 세로 사이즈를 구함
     *
     * @param mContext
     * @return
     */
    public static int getDeviceHeight(Context mContext) {
        int height = 0;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT > 12) {
            Point size = new Point();
            display.getSize(size);
            height = size.y;
        } else {
            height = display.getHeight();  // Deprecated
        }
        return height;
    }


}
