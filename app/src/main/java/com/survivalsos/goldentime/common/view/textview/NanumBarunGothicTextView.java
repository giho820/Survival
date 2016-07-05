package com.survivalsos.goldentime.common.view.textview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.survivalsos.goldentime.Definitions;

public class NanumBarunGothicTextView extends TextView {

    public NanumBarunGothicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Definitions.NanumBarunGothic == null) {
            Definitions.NanumBarunGothic = Typeface.createFromAsset(context.getAssets(), "NanumBarunGothic.ttf");
        }
        if (Definitions.NanumBarunGothic != null)
            setTypeface(Definitions.NanumBarunGothic);
        //setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
    }

    public NanumBarunGothicTextView(Context context) {
        super(context);
        if (Definitions.NanumBarunGothic == null) {
            Definitions.NanumBarunGothic = Typeface.createFromAsset(context.getAssets(), "NanumBarunGothic.ttf");
        }
        if (Definitions.NanumBarunGothic != null)
            setTypeface(Definitions.NanumBarunGothic);
        //setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
    }

    public NanumBarunGothicTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (Definitions.NanumBarunGothic == null) {
            Definitions.NanumBarunGothic = Typeface.createFromAsset(context.getAssets(), "NanumBarunGothic.ttf");
        }
        if (Definitions.NanumBarunGothic != null)
            setTypeface(Definitions.NanumBarunGothic);
        //setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
    }

}
