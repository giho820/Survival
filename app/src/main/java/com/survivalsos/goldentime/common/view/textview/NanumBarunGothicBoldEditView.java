package com.survivalsos.goldentime.common.view.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.survivalsos.goldentime.Definitions;


public class NanumBarunGothicBoldEditView extends EditText {

	public NanumBarunGothicBoldEditView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(Definitions.NanumBarunGothicBold !=null)
			setTypeface(Definitions.NanumBarunGothicBold);
		//setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
	}

	public NanumBarunGothicBoldEditView(Context context) {
		super(context);
		if(Definitions.NanumBarunGothicBold !=null)
			setTypeface(Definitions.NanumBarunGothicBold);
		//setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
	}

	public NanumBarunGothicBoldEditView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if(Definitions.NanumBarunGothicBold !=null)
			setTypeface(Definitions.NanumBarunGothicBold);
		//setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
	}

}
