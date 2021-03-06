package com;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.survivalsos.goldentime.Definitions;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.dialog.CommonLoadingDialog;
import com.survivalsos.goldentime.util.DebugUtil;
import com.survivalsos.goldentime.util.DeviceUtil;
import com.survivalsos.goldentime.util.ToforUtil;

import java.util.ArrayList;

/**
 * @author jooyoung
 */
public abstract class ParentAct extends AppCompatActivity {

	private int IMAGE_SIZE = 500;

	private InputMethodManager inputManager;
	private CommonLoadingDialog loading;

	public static ArrayList<Activity> activityList;

    public void onUIRefresh() {
	}

	public void hiddenKeyboard() {
		if (inputManager == null)
			inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputManager.isActive()) {
			inputManager.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
		}
	}

	public void showKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		toforAppInit();
	}

	public void showLoading() {
		try {
			if (loading == null)
				return;
			loading.show();
		} catch (Exception e) {
		}
	}

	public void hideLoading() {
		try {
			if (loading == null)
				return;
			loading.dismiss();
		} catch (Exception e) {
		}
	}

	public void setLoading(Activity a) {
		loading = new CommonLoadingDialog(a);
	}

	public void setLoading(Activity a, boolean isTransBG) {
		loading = new CommonLoadingDialog(a, isTransBG);
	}


	@Override
	protected void onResume() {
		super.onResume();

		toforAppInit();

	}

	public void toforAppInit() {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 화면을 portrait(세로) 화면으로 고정하고 싶은 경우
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 화면을 landscape(가로) 화면으로 고정하고 싶은 경우


        if (ToforUtil.PHONE_W <= 0 || ToforUtil.PHONE_H <= 0) {
			Display defaultDisplay = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
			ToforUtil.PHONE_W = defaultDisplay.getWidth();
			ToforUtil.PHONE_H = defaultDisplay.getHeight();
			ToforUtil.SCREENRATE = DeviceUtil.getScreenRate(getApplicationContext(), (int) ToforUtil.PHONE_BASIC_W, (int) ToforUtil.PHONE_BASIC_H);
		}
		if (Definitions.NanumGothic == null) {
			Definitions.NanumGothic = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
		}

		if (Definitions.NanumGothicBold == null) {
			Definitions.NanumGothicBold = Typeface.createFromAsset(getAssets(), "NanumGothicBold.ttf");
		}

		if (Definitions.LatoBlack == null) {
			Definitions.LatoBlack = Typeface.createFromAsset(getAssets(), "Lato-Black.ttf");
		}

		if (Definitions.LatoBold == null) {
			Definitions.LatoBold = Typeface.createFromAsset(getAssets(), "Lato-Bold.ttf");
		}

		if (Definitions.NanumBarunGothic == null) {
			Definitions.NanumBarunGothic = Typeface.createFromAsset(getAssets(), "NanumBarunGothic.ttf");
		}

		if (Definitions.NanumBarunGothicBold == null) {
			Definitions.NanumBarunGothicBold = Typeface.createFromAsset(getAssets(), "NanumBarunGothicBold.ttf");
		}

	}

	/**
	 * @param frag
	 * @param dlgIdx
	 */
	public void dialogShow(Fragment frag, String dlgIdx) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment prev = getSupportFragmentManager().findFragmentByTag(dlgIdx);
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);
		((DialogFragment) frag).show(ft, dlgIdx);
	}

	public void switchContent(Fragment fragment, int contentId, boolean isHistory, boolean isAni) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		if (isHistory)
			ft.addToBackStack(null);
		if (isAni) {
			ft.setCustomAnimations(R.anim.left, R.anim.left2, R.anim.right2, R.anim.right);
		}
		ft.replace(contentId, fragment).commit();
	}

	/*
	 * Animation Right to left
	 */
	public void switchContent2(Fragment fragment, int contentId, boolean isHistory, boolean isAni) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		if (isHistory)
			ft.addToBackStack(null);
		if (isAni) {
			ft.setCustomAnimations(R.anim.right2, R.anim.right, R.anim.left, R.anim.left2);
		}
		ft.replace(contentId, fragment).commit();
	}

	//Todo 스크롤뷰 안에 있는 리스트뷰의 항목이 모두 보이도록하는 방법 잘되면 ParentAct로 옮기도록 하자
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null)
			return;

		int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
		int totalHeight = 0;
		View view = null;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			view = listAdapter.getView(i, view, listView);
			if (i == 0)
				view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

			view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
			totalHeight += view.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		DebugUtil.showDebug("setListViewHeightBasedOnChildren() :: " + params.height);
		listView.setLayoutParams(params);
	}
}
