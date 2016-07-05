package com.survivalsos.goldentime.common.view.textview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.survivalsos.goldentime.Definitions;

public class NanumBarunGothicBoldTextView extends TextView {

    public NanumBarunGothicBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Definitions.NanumBarunGothicBold == null) {
            Definitions.NanumBarunGothicBold = Typeface.createFromAsset(context.getAssets(), "NanumBarunGothicBold.ttf");
        }
        if (Definitions.NanumBarunGothicBold != null)
            setTypeface(Definitions.NanumBarunGothicBold);
        //setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
    }

    public NanumBarunGothicBoldTextView(Context context) {
        super(context);
        if (Definitions.NanumBarunGothicBold == null) {
            Definitions.NanumBarunGothicBold = Typeface.createFromAsset(context.getAssets(), "NanumBarunGothicBold.ttf");
        }
        if (Definitions.NanumBarunGothicBold != null)
            setTypeface(Definitions.NanumBarunGothicBold);
        //setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
    }

    public NanumBarunGothicBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (Definitions.NanumBarunGothicBold == null) {
            Definitions.NanumBarunGothicBold = Typeface.createFromAsset(context.getAssets(), "NanumBarunGothicBold.ttf");
        }
        if (Definitions.NanumBarunGothicBold != null)
            setTypeface(Definitions.NanumBarunGothicBold);
        //setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
    }

}
