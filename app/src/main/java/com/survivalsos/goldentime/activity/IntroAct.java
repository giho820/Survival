package com.survivalsos.goldentime.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import com.ParentAct;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.database.ChangeableDatabaseHelper;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.database.DatabaseHelper;
import com.survivalsos.goldentime.database.util.DatabaseConstantUtil;
import com.survivalsos.goldentime.database.util.DatabaseUtil;
import com.survivalsos.goldentime.util.DebugUtil;
import com.survivalsos.goldentime.util.MoveActUtil;

import java.io.IOException;

public class IntroAct extends ParentAct {

    private ChangeableDatabaseHelper changeableDatabaseHelper;//고객의 요청으로 인해 내용이 바뀔 수 있는 디비 헬퍼
    private DatabaseHelper databaseHelper;//고객의 요청이 있더라도 사용자마다 변하면 안되는 내용이 있는 디비 헬퍼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //여기서 디비를 asset에서 가져오도록 한다
        changeableDatabaseHelper = ChangeableDatabaseHelper.getInstacnce(this);
        copyDatabase(ChangeableDatabaseHelper.changeableSqLiteDatabase);

        databaseHelper = DatabaseHelper.getInstacnce(this);
        //copyDatabase(DatabaseHelper.sqLiteDatabase);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MoveActUtil.chageActivity(IntroAct.this, GuideAddedMainAct.class, -1, -1, true, false);
            }
        }, 500);
    }


    // to copy database
    private void copyDatabase(SQLiteDatabase sqLiteDatabase) {

        if (!DatabaseCRUD.checkTable(changeableDatabaseHelper, sqLiteDatabase, DatabaseConstantUtil.TABLE_CHECK_LIST)) {
            DebugUtil.showDebug("CHECK_LIST table is not existed");
            try {
                DatabaseUtil.copyDataBase(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            DebugUtil.showDebug("CHECK_LIST table is existed");
        }
    }
}
