package com.survivalsos.goldentime.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.survivalsos.goldentime.database.util.DatabaseConstantUtil;
import com.survivalsos.goldentime.model.Article;
import com.survivalsos.goldentime.model.MainImageItemInfo;
import com.survivalsos.goldentime.util.DebugUtil;

import java.util.ArrayList;

public class DatabaseCRUD {

    private static SQLiteDatabase sqLiteDatabase;
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
    public static ArrayList<Integer> getMainImageFileNameFromAssetFolder(int section) {
        ArrayList<Integer> result = new ArrayList<>();

        String sqlQueryForMainPage = "SELECT CODE FROM CATEGORY WHERE CODE like '" + section + "%'";
        DebugUtil.showDebug("query :: " + sqlQueryForMainPage);

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery(sqlQueryForMainPage, null);

        if (cursor == null)
            return null;

        while (cursor.moveToNext()) {
            result.add(cursor.getInt(0));
        }

        for (Integer i : result) {
            DebugUtil.showDebug("result :: " + i);
        }
        cursor.close();
        return result;
    }

    public static ArrayList<MainImageItemInfo> getMainImageItemInfoFromAssetFolder(int section) {
        ArrayList<MainImageItemInfo> result = new ArrayList<>();

        String sqlQueryForMainPage = "SELECT * FROM CATEGORY WHERE CODE like '" + section + "%'";
        DebugUtil.showDebug("query :: " + sqlQueryForMainPage);

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery(sqlQueryForMainPage, null);

        if (cursor == null)
            return null;

        while (cursor.moveToNext()) {
            MainImageItemInfo tempMainImageItem = new MainImageItemInfo();
            tempMainImageItem.mainImageCode = cursor.getInt(0);
            tempMainImageItem.mainImageName = cursor.getString(1);
            tempMainImageItem.doesLocked = cursor.getInt(2);

            result.add(tempMainImageItem);
        }

        for (MainImageItemInfo i : result) {
            DebugUtil.showDebug("result :: " + i.mainImageName);
        }
        cursor.close();
        return result;
    }


    //메인화면에서 클릭한 카테고리에 해당하는 ArticleList 가져오기
    public static ArrayList<Article> getArticleList(int section) {
        ArrayList<Article> result = new ArrayList<>();

        String sqlQueryForArticleList = "SELECT * FROM ARTICLE WHERE HIGH_RANK_CODE = " + section + "";
        DebugUtil.showDebug("query :: " + sqlQueryForArticleList);

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery(sqlQueryForArticleList, null);
        if (cursor == null)
            return null;

        while (cursor.moveToNext()) {
            Article article = new Article();
            article.articleId = cursor.getInt(0);
            article.title = cursor.getString(1);
            article.highRankCode = cursor.getInt(2);
            article.nextArticleId = cursor.getInt(3);
            article.relatedArticleId = cursor.getInt(4);
            article.isSoundFile = cursor.getString(5);
            article.articleText = cursor.getString(6);

            result.add(article);
        }

        for (Article article : result) {
            DebugUtil.showDebug("result :: " + article.articleId);
        }
        cursor.close();
        return result;
    }


    //특정 Article_Id를 통해서 Article의 정보를 가져오는 함수
    public static Article getArticleInfo(int articleId) {
        Article result = new Article();

        String sqlQueryForArticleList = "SELECT * FROM ARTICLE WHERE ARTICLE_ID = " + articleId;
        DebugUtil.showDebug("query :: " + sqlQueryForArticleList);

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery(sqlQueryForArticleList, null);
        if (cursor == null)
            return null;

        while (cursor.moveToNext()) {
            Article article = new Article();
            article.articleId = cursor.getInt(0);
            article.title = cursor.getString(1);
            article.highRankCode = cursor.getInt(2);
            article.nextArticleId = cursor.getInt(3);
            article.relatedArticleId = cursor.getInt(4);
            article.isSoundFile = cursor.getString(5);
            article.articleText = cursor.getString(6);

            DebugUtil.showDebug("toString :: " + article.toString());
            result = article;
        }

        cursor.close();
        return result;
    }

    //특정 Article_Id를 통해서 Article의 정보를 가져오는 함수
    public static String getArticleTitle(int articleId) {
        String title = "";

        String sqlQueryForArticleTitle = "SELECT TITLE FROM ARTICLE WHERE ARTICLE_ID = " + articleId;
        DebugUtil.showDebug("query :: " + sqlQueryForArticleTitle);

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery(sqlQueryForArticleTitle, null);
        if (cursor == null)
            return null;

        while (cursor.moveToNext()) {
            title = cursor.getString(0);
        }

        cursor.close();
        return title;
    }

    //검색 화면에서 특정 검색어를 통해서 Article의 정보를 가져오는 함수
    public static ArrayList<Article> getSearchList(String inputText) {
        ArrayList<Article> result = new ArrayList<>();

        String sqlQueryForArticleList = "SELECT * FROM ARTICLE WHERE ARTICLE_TEXT LIKE '%" + inputText + "%'";
        DebugUtil.showDebug("query :: " + sqlQueryForArticleList);

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery(sqlQueryForArticleList, null);
        if (cursor == null)
            return null;

        while (cursor.moveToNext()) {
            Article article = new Article();
            article.articleId = cursor.getInt(0);
            article.title = cursor.getString(1);
            article.highRankCode = cursor.getInt(2);
            article.nextArticleId = cursor.getInt(3);
            article.relatedArticleId = cursor.getInt(4);
            article.isSoundFile = cursor.getString(5);
            article.articleText = cursor.getString(6);

            result.add(article);
        }

        for (Article article : result) {
            DebugUtil.showDebug("result :: " + article.articleId);
        }
        cursor.close();
        return result;
    }


    //북마크 정보가 있는 아티클 인지를 확인하는 함수
    public static boolean isBookmarked(Integer inputInteger) {
        boolean isExist = false;

        String sqlQueryForArticleList = "SELECT * FROM " + DatabaseConstantUtil.TABLE_USER_BOOKMARK +" WHERE "+
                DatabaseConstantUtil.COLUMN_ARTICLE_ID +" = " + inputInteger;
        DebugUtil.showDebug("query :: " + sqlQueryForArticleList);

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery(sqlQueryForArticleList, null);
        if (cursor == null)
            return false;

        if (cursor.getCount() > 0)
            isExist = true;
        else
            isExist = false;
        cursor.close();

        return isExist;
    }

    public static String selectBookmarkDBQuery() {
        String result = "";

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery("select * from " + DatabaseConstantUtil.TABLE_USER_BOOKMARK, null);

        while (cursor != null && cursor.moveToNext()) {
            result += cursor.getInt(0)
                    + ". "
                    + cursor.getString(1)
                    + "\n";
        }

        cursor.close();
        return result;
    }

    public static ArrayList<Article> getBookmarkArticlesFromDB() {
        ArrayList<Article> results = new ArrayList<>();

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery("select * from " + DatabaseConstantUtil.TABLE_USER_BOOKMARK, null);

        while (cursor != null && cursor.moveToNext()) {
            Article article = new Article();
            article.articleId = cursor.getInt(0);
            article.title = cursor.getString(1);
            results.add(article);
        }

        cursor.close();
        return results;
    }

}
