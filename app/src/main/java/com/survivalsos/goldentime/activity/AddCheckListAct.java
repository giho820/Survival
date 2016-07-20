package com.survivalsos.goldentime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ParentAct;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.common.view.textview.NanumBarunGothicEditView;
import com.survivalsos.goldentime.util.DebugUtil;
import com.survivalsos.goldentime.util.TextUtil;

public class AddCheckListAct extends ParentAct implements View.OnClickListener {

    LinearLayout linearLayoutBackgroundBlur;
    LinearLayout linearLayoutCheckListBack;

    String userInput;
    NanumBarunGothicEditView evInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 1;
//        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.guide_bg, options);
//        Bitmap newImg = Blur.fastblur(this, image, 3);
        setContentView(R.layout.activity_add_check_list);

        linearLayoutCheckListBack = (LinearLayout) findViewById(R.id.linearlayout_check_list_add_close_xx);
        linearLayoutBackgroundBlur = (LinearLayout) findViewById(R.id.linearlayout_add_check_list_blur);
        evInput = (NanumBarunGothicEditView) findViewById(R.id.edittext_input_search);
        evInput.setFocusable(true);

        linearLayoutBackgroundBlur.setOnClickListener(this);
        linearLayoutCheckListBack.setOnClickListener(this);
        evInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                userInput = v.getText().toString();
                DebugUtil.showDebug("userInput :: " + userInput);
                hiddenKeyboard();
                onBackPressed();
                return false;
            }
        });

//        BitmapDrawable ob = new BitmapDrawable(getResources(), newImg);
//        linearLayoutBackgroundBlur.setBackground(ob);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearlayout_check_list_add_close_xx:
            case R.id.linearlayout_add_check_list_blur:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        userInput = evInput.getText().toString();

        if (!TextUtil.isNull(userInput)) {
            Intent intent = new Intent(this, CheckListAct.class);
            intent.putExtra("userInputString", userInput);
            this.setResult(RESULT_OK, intent);
        }
        super.onBackPressed();
    }
}
