package com.survivalsos.goldentime.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.survivalsos.goldentime.database.util.DatabaseConstantUtil;
import com.survivalsos.goldentime.database.util.DatabaseUtil;
import com.survivalsos.goldentime.util.DebugUtil;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static DatabaseHelper databaseHelper;
    public static SQLiteDatabase sqLiteDatabase;
    public static Context context;

    private DatabaseHelper(Context context) {
        super(context, DatabaseConstantUtil.DATABASE_DB_NAME, null, DatabaseConstantUtil.DATABASE_VERSION);
//        DebugUtil.showDebug("constructor : " + DatabaseConstantUtil.DATABASE_DB_NAME + " / " + DatabaseConstantUtil.DATABASE_VERSION + " / " + SharedPreUtil.getInstance().getIntPreference(ConstantUtil.KEY_SHARED_PREFERENCE.CHECK_DATABASE));
    }

    public static synchronized DatabaseHelper getInstacnce(Context context) {
        DebugUtil.showDebug("DatabaseHelper, getInstance()");

        databaseHelper = new DatabaseHelper(context);

        DatabaseHelper.context = context;
        DatabaseHelper.sqLiteDatabase = databaseHelper.getReadableDatabase(); //이때 onCreate()나 onUpgrade()
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
        //Todo user checked list Table Create 하는 부분
        try {
            db.execSQL(DatabaseConstantUtil.CREATE_USER_BOOKMARK_TABLE);
            db.execSQL(DatabaseConstantUtil.CREATE_USER_CHECKED_LIST_TABLE);
            DebugUtil.showDebug(DatabaseConstantUtil.CREATE_USER_BOOKMARK_TABLE);
            DebugUtil.showDebug(DatabaseConstantUtil.CREATE_USER_CHECKED_LIST_TABLE);

            db.setTransactionSuccessful();
        } catch (Exception err) {
            err.getMessage();
            DebugUtil.showDebug(err.toString());
        }
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DebugUtil.showDebug("DatabaseHelper onUpgrade()");

        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstantUtil.TABLE_USER_BOOKMARK);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST);

        onCreate(db);
    }

    @Override
    public synchronized void close() {
        if (sqLiteDatabase != null)
            sqLiteDatabase.close();

        super.close();
    }
}
