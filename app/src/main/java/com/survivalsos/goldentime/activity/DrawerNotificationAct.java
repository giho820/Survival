package com.survivalsos.goldentime.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.common.view.NanumGothicTextView;
import com.survivalsos.goldentime.util.DebugUtil;
import com.survivalsos.goldentime.util.MoveActUtil;

public class DrawerNotificationAct extends AppCompatActivity implements View.OnClickListener {

    ScrollView scrollView;
    LinearLayout linearLayoutDrawerCloseX;
    LinearLayout linearLayoutDrawerNoti;
    LinearLayout linearLayoutDrawerCopyright;
    LinearLayout linearLayoutDrawerContact;

    NanumGothicTextView link1;
    NanumGothicTextView link2;
    NanumGothicTextView link3;
    NanumGothicTextView link4;

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

        link1 = (NanumGothicTextView) findViewById(R.id.link1);
        link2 = (NanumGothicTextView) findViewById(R.id.link2);
        link3 = (NanumGothicTextView) findViewById(R.id.link3);
        link4 = (NanumGothicTextView) findViewById(R.id.link4);

        link1.setOnClickListener(this);
        link2.setOnClickListener(this);
        link3.setOnClickListener(this);
        link4.setOnClickListener(this);

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

            case R.id.link1:
                String uri1 = "http://blog.naver.com/ssssus21";
                linkingWebview(uri1);
                break;


            case R.id.link2:
                String uri2 = "https://commons.wikimedia.org/wiki/File:80_Bealey_Avenue_after_earthquake.jpg";
                linkingWebview(uri2);
                break;

            case R.id.link3:
                String uri3 = "https://commons.wikimedia.org/wiki/File:2010_Chile_earthquake_-_Building_destroyed_in_Concepci%C3%B3n.jpg";
                linkingWebview(uri3);
                break;

            case R.id.link4:
                String uri4 = "https://commons.wikimedia.org/wiki/File:Vespucio_Norte_Highway_after_2010_earthquake.jpg";
                linkingWebview(uri4);
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


    public void linkingWebview(final String uri) {
        android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(this).create();
        alertDialog.setTitle("알림");
        alertDialog.setMessage("원본의 링크로 이동합니다. 와이파이에 연결되어 있지 않은 경우 데이터 요금이 적용됩니다. \n웹브라우저를 여시겠습니까?");
        alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE, "취소",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE, "예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intentUpdate = new Intent(Intent.ACTION_VIEW);
                        intentUpdate.setData(Uri.parse(uri));
                        if (this == null) {
                            DebugUtil.showDebug("parentAct is null");
                        } else {
                            DebugUtil.showDebug("parentAct is not null ");
                            MoveActUtil.moveActivity(DrawerNotificationAct.this, intentUpdate, -1, -1, false, false);
                        }
                    }
                });

        alertDialog.show();
    }

}
