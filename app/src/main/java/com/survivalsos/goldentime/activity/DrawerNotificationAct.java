package com.survivalsos.goldentime.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.util.DebugUtil;

public class DrawerNotificationAct extends AppCompatActivity implements View.OnClickListener {

    ScrollView scrollView;
    LinearLayout linearLayoutDrawerCloseX;
    LinearLayout linearLayoutDrawerNoti;
    LinearLayout linearLayoutDrawerCopyright;
    LinearLayout linearLayoutDrawerContact;

    int whichInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_notification);

        linearLayoutDrawerCloseX = (LinearLayout) findViewById(R.id.linearlayout_close_x_drawer_notification);
        linearLayoutDrawerCloseX.setOnClickListener(this);

        scrollView = (ScrollView) findViewById(R.id.scrollViewNotiCopyrightContact);
        linearLayoutDrawerNoti = (LinearLayout) findViewById(R.id.linear_drawer_noti_header);
        linearLayoutDrawerCopyright = (LinearLayout) findViewById(R.id.linear_drawer_copyright_header);
        linearLayoutDrawerContact = (LinearLayout) findViewById(R.id.linear_drawer_contact_header);

        whichInfo = getIntent().getIntExtra("whichInfo", 0);
        switch (whichInfo) {
            case 0:

                break;
            case 1:
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
//                        scrollView.scrollTo(0, (int) linearLayoutDrawerCopyright.getY());//저작권 공지
                        scrollView.smoothScrollTo(0, (int) linearLayoutDrawerCopyright.getY());//저작권 공지
                    }
                });
                break;
            case 2:
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(scrollView.FOCUS_DOWN);//Contact
                    }
                });
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearlayout_close_x_drawer_notification:
                finish();
                break;
        }
    }

    public boolean chkTouchInside(View view, int x, int y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        DebugUtil.showDebug("please :: (" + location[0] + ", " + location[1] + ")");
        if (x >= location[0]) {
            if (x <= location[0] + view.getWidth()) {
                if (y >= location[1]) {
                    if (y <= location[1] + view.getHeight()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
