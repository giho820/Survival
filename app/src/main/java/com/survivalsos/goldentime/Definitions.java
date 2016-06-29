package com.survivalsos.goldentime;

import android.graphics.Typeface;

/**
 * Created by kiho on 2016. 6. 20..
 */
public class Definitions {

    public static Typeface NanumGothic;
    public static Typeface NanumGothicBold;
    public static Typeface LatoBlack;
    public static Typeface LatoBold;

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
        //Todo 카테고리가 있는 아티클인지 여부
        //Todo 사운드 파일이 있는 아티클인지 여부
    }

    public static String[] CATEGORY_NAME = {"필수", "방수 • 방진", "보온 • 의복", "통신 • 조명", "위생", "구급약", "쉘터 • 도구", "내가 추가한 아이템"};

    public interface CHECK_BOX_IMPORTED {
        int IMPORTED = 1;
        int UNIMPORTED= 0;
    }

    public interface CHECK_BOX_CHECKED {
        int CHECKED = 1;
        int UNCHECKED = 0;
    }
}
