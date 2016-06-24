package com.survivalsos.goldentime.common.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.survivalsos.goldentime.Definitions;

/**
 * Created by kiho on 2016. 6. 23..
 */
public class LatoBoldTextView extends TextView {

    public LatoBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Definitions.LatoBold == null) {
            Definitions.LatoBold = Typeface.createFromAsset(context.getAssets(), "Lato-Bold.ttf");
        }
        if (Definitions.LatoBold != null)
            setTypeface(Definitions.LatoBold);
        //setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
    }

    public LatoBoldTextView(Context context) {
        super(context);
        if (Definitions.LatoBold == null) {
            Definitions.LatoBold = Typeface.createFromAsset(context.getAssets(), "Lato-Bold.ttf");
        }
        if (Definitions.LatoBold != null)
            setTypeface(Definitions.LatoBold);
        //setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
    }

    public LatoBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (Definitions.LatoBold == null) {
            Definitions.LatoBold = Typeface.createFromAsset(context.getAssets(), "Lato-Bold.ttf");
        }
        if (Definitions.LatoBold != null)
            setTypeface(Definitions.LatoBold);
        //setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
    }

}
