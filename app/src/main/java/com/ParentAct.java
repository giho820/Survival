package com;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.survivalsos.goldentime.Definitions;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.util.DeviceUtil;
import com.survivalsos.goldentime.util.ToforUtil;

import java.util.ArrayList;

/**
 * @author jooyoung
 */
public abstract class ParentAct extends AppCompatActivity {

	private int IMAGE_SIZE = 500;

	private InputMethodManager inputManager;

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


	@Override
	protected void onResume() {
		super.onResume();

		toforAppInit();

	}

	public void toforAppInit() {

		if (ToforUtil.PHONE_W <= 0 || ToforUtil.PHONE_H <= 0) {
			Display defaultDisplay = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
			ToforUtil.PHONE_W = defaultDisplay.getWidth();
			ToforUtil.PHONE_H = defaultDisplay.getHeight();
			ToforUtil.SCREENRATE = DeviceUtil.getScreenRate(getApplicationContext(), (int) ToforUtil.PHONE_BASIC_W, (int) ToforUtil.PHONE_BASIC_H);
		}
		if (Definitions.NanumGothic == null) {
			Definitions.NanumGothic = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
		}

		if (Definitions.LatoBlack == null) {
			Definitions.LatoBlack = Typeface.createFromAsset(getAssets(), "Lato-Black.ttf");
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


}
