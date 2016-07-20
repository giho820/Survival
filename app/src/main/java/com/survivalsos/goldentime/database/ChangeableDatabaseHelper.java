package com.survivalsos.goldentime.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.survivalsos.goldentime.database.util.DatabaseConstantUtil;
import com.survivalsos.goldentime.database.util.DatabaseUtil;
import com.survivalsos.goldentime.util.DebugUtil;

public class ChangeableDatabaseHelper extends SQLiteOpenHelper {

    public static ChangeableDatabaseHelper changeableDatabaseHelper;
    public static SQLiteDatabase changeableSqLiteDatabase;
    public static Context context;

    private ChangeableDatabaseHelper(Context context) {
        super(context, DatabaseConstantUtil.DATABASE_CHANGEABLE_DB_NAME, null, DatabaseConstantUtil.CHANGEABLE_DB_VERSION);
//        DebugUtil.showDebug("constructor : " + DatabaseConstantUtil.DATABASE_DB_NAME + " / " + DatabaseConstantUtil.DATABASE_VERSION + " / " + SharedPreUtil.getInstance().getIntPreference(ConstantUtil.KEY_SHARED_PREFERENCE.CHECK_DATABASE));
    }

    public static synchronized ChangeableDatabaseHelper getInstacnce(Context context) {
        DebugUtil.showDebug("ChangeableDatabaseHelper, getInstance()");

        changeableDatabaseHelper = new ChangeableDatabaseHelper(context);

        changeableDatabaseHelper.context = context;
        changeableDatabaseHelper.changeableSqLiteDatabase = changeableDatabaseHelper.getReadableDatabase(); //이때 onCreate()나 onUpgrade()
        return changeableDatabaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DebugUtil.showDebug("ChangeableDatabaseHelper onCreate()");

        if (!DatabaseUtil.checkChangeableDatabase()) {
            DebugUtil.showDebug("Changeable DB is not existed");
        } else {
            DebugUtil.showDebug("Changeable DB is existed");
        }

//        db.beginTransaction();
//        try {
//            db.execSQL(DatabaseConstantUtil.CREATE_USER_BOOKMARK_TABLE);
//            db.execSQL(DatabaseConstantUtil.CREATE_USER_CHECKED_LIST_TABLE);
//            DebugUtil.showDebug(DatabaseConstantUtil.CREATE_USER_BOOKMARK_TABLE);
//            DebugUtil.showDebug(DatabaseConstantUtil.CREATE_USER_CHECKED_LIST_TABLE);
//
//            db.setTransactionSuccessful();
//        } catch (Exception err) {
//            err.getMessage();
//            DebugUtil.showDebug(err.toString());
//        }
//        db.endTransaction();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DebugUtil.showDebug("ChangeableDatabaseHelper onUpgrade()");

//        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstantUtil.TABLE_USER_BOOKMARK);
//        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST);

        onCreate(db);
    }

    @Override
    public synchronized void close() {
        if (changeableSqLiteDatabase != null)
            changeableSqLiteDatabase.close();

        super.close();
    }
}
