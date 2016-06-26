package com.survivalsos.goldentime.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.survivalsos.goldentime.Definitions;


public class LatoBlackEditView extends EditText {

	public LatoBlackEditView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (Definitions.LatoBlack != null)
			setTypeface(Definitions.LatoBlack);
		//setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
	}

	public LatoBlackEditView(Context context) {
		super(context);
		if (Definitions.LatoBlack != null)
			setTypeface(Definitions.LatoBlack);

		//setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
	}

	public LatoBlackEditView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (Definitions.LatoBlack != null)
			setTypeface(Definitions.LatoBlack);

		//setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
	}


}
