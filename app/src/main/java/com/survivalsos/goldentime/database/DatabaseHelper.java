package com.survivalsos.goldentime.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.survivalsos.goldentime.database.util.DatabaseConstantUtil;
import com.survivalsos.goldentime.database.util.DatabaseUtil;
import com.survivalsos.goldentime.util.DebugUtil;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;
    public static SQLiteDatabase sqLiteDatabase;
    private static Context context;
    private static Cursor cursor;

    private DatabaseHelper(Context context) {
        super(context, DatabaseConstantUtil.DATABASE_DB_NAME, null, DatabaseConstantUtil.DATABASE_VERSION);
//        DebugUtil.showDebug("constructor : " + DatabaseConstantUtil.DATABASE_DB_NAME + " / " + DatabaseConstantUtil.DATABASE_VERSION + " / " + SharedPreUtil.getInstance().getIntPreference(ConstantUtil.KEY_SHARED_PREFERENCE.CHECK_DATABASE));
    }

    public static synchronized DatabaseHelper getInstacnce(Context context) {
        DebugUtil.showDebug("DatabaseHelper, getInstance()");

        databaseHelper = new DatabaseHelper(context);

        DatabaseHelper.context = context;
        DatabaseHelper.sqLiteDatabase = databaseHelper.getReadableDatabase();
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DebugUtil.showDebug("DatabaseHelper onCreate()");

        if (!DatabaseUtil.checkDatabase()) {
            DebugUtil.showDebug("Database is not existed");
        } else {
            DebugUtil.showDebug("Database is existed");
        }

        db.beginTransaction();
        try {
            //Todo database Table Create 하는 부분
//            db.execSQL(DatabaseConstantUtil.CREATE_INTELLIGENT_GALLERY_TABLE);
//            DebugUtil.showDebug(DatabaseConstantUtil.CREATE_INTELLIGENT_GALLERY_TABLE);
            db.setTransactionSuccessful();

        } catch (Exception err) {
            DebugUtil.showDebug(err.toString());
        }
        db.endTransaction();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Todo 데이터 베이스 변경되었을 시 업데이트 하는 부분
        DebugUtil.showDebug("DatabaseHelper onUpgrade()");
//        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstantUtil.TABLE_INTELLIGENT_GALLERY_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstantUtil.TABLE_ALBUM_COVER);

        onCreate(db);

    }

    @Override
    public synchronized void close() {

        if (sqLiteDatabase != null)
            sqLiteDatabase.close();

        super.close();
    }


}
