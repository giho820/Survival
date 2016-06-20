package com.survivalsos.goldentime.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.survivalsos.goldentime.util.DebugUtil;

import java.util.ArrayList;

public class DatabaseCRUD {

    private static SQLiteDatabase sqLiteDatabase;
    private static Cursor cursor;
    private static String keyword;

    public static boolean checkTable(DatabaseHelper databaseHelper, SQLiteDatabase sqLiteDatabase, String tableName) {

        boolean flag = true;

        if (sqLiteDatabase == null) {
            DebugUtil.showDebug("SQLiteDatabase is null");
            flag = false;
            return flag;
        }

        //Todo 이부분 정확히 이해할 것 테이블명을 어떤 디비에서 찾는다는 것이지?
        String sql_check_whether_table_exist_or_not = "SELECT count(*) as check_table FROM sqlite_master WHERE type='table' AND name='" + tableName + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql_check_whether_table_exist_or_not, null);
        cursor.moveToFirst();
        DebugUtil.showDebug("cursor count : " + cursor.getInt(cursor.getColumnIndex("check_table")));

        if (cursor.getInt(cursor.getColumnIndex("check_table")) != 1) {
            flag = false;
        }
        DebugUtil.showDebug("flag : " + flag);

        return flag;

    }

    public static void execRawQuery(String _query) {
        DatabaseHelper.sqLiteDatabase.execSQL(_query);
    }


    public static String selectQuery(String _query) {
        String result = "";

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery(_query, null);
        if (cursor == null)
            return null;

        while (cursor.moveToNext()) {
            result += cursor.getString(0) + ", \n";
            DebugUtil.showDebug("result :" + result);
        }

        cursor.close();
        return result;
    }

    //메인화면 그림가져오기, asset의 적절한 파일 이름 리턴
    public static ArrayList<Integer> getMainImageFileNameFromAssetFolder(int Section) {
        ArrayList<Integer> result = new ArrayList<>();

        String sqlQueryForMainPage = "SELECT CODE FROM CATEGORY WHERE CODE like '" + Section + "%'";
        DebugUtil.showDebug("query :: " + sqlQueryForMainPage);

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery(sqlQueryForMainPage, null);
        if(cursor == null)
            return null;

        while (cursor.moveToNext()) {
            result.add(cursor.getInt(0));
        }

        for(Integer i : result){
            DebugUtil.showDebug("result :: " + i );
        }
        cursor.close();
        return result;
    }
}
