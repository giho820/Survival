package com.survivalsos.goldentime;

import android.graphics.Typeface;

/**
 * Created by kiho on 2016. 6. 20..
 */
public class Definitions {

    public static Typeface NanumGothic;
    public static Typeface LatoBlack;

    public interface SECTION_TYPE {
        int NATURE_DISASTER = 1;
        int ACCIDENT_FIRE = 2;
        int SURVIVAL_PRINCIPLE = 3;
        int READY_FOR_EMERGENCY = 4;
    }

    public interface ARTICLE_TYPE {
        int NEXT = 1;
        int RELATED = 2;
        int DEFAULT = 3;
    }
}
