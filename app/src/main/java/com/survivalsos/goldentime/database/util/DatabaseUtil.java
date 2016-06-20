package com.survivalsos.goldentime.database.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.survivalsos.goldentime.util.DebugUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseUtil {

    public static boolean checkDatabase() {
        SQLiteDatabase checkDatabase = null;

        try {
            String path = DatabaseConstantUtil.DATABASE_PATH;
            checkDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException sqlE) {
            DebugUtil.showDebug("checkDatabase() : " + sqlE.getMessage());
        }

        if (checkDatabase != null)
            checkDatabase.close();

        return checkDatabase != null ? true : false;
    }

    public static void copyDataBase(Context context) throws IOException {
        DebugUtil.showDebug("DatabaseUtil, copyDataBase() asset에서 데이터베이스 복사");

        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DatabaseConstantUtil.DATABASE_SQLITE_NAME);

        // Path to the just created empty db
        String outFileName = DatabaseConstantUtil.DATABASE_PATH;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

}
