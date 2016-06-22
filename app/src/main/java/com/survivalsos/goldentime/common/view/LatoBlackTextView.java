package com.survivalsos.goldentime.common.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.survivalsos.goldentime.Definitions;

/**
 * Created by kiho on 2016. 6. 23..
 */
public class LatoBlackTextView extends TextView {

    public LatoBlackTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Definitions.LatoBlack == null) {
            Definitions.LatoBlack = Typeface.createFromAsset(context.getAssets(), "Lato-Black.ttf");
        }
        if (Definitions.LatoBlack != null)
            setTypeface(Definitions.LatoBlack);
        //setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
    }

    public LatoBlackTextView(Context context) {
        super(context);
        if (Definitions.LatoBlack == null) {
            Definitions.LatoBlack = Typeface.createFromAsset(context.getAssets(), "Lato-Black.ttf");
        }
        if (Definitions.LatoBlack != null)
            setTypeface(Definitions.LatoBlack);
        //setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
    }

    public LatoBlackTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (Definitions.LatoBlack == null) {
            Definitions.LatoBlack = Typeface.createFromAsset(context.getAssets(), "Lato-Black.ttf");
        }
        if (Definitions.LatoBlack != null)
            setTypeface(Definitions.LatoBlack);
        //setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
    }

}
