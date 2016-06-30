package com.survivalsos.goldentime.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.survivalsos.goldentime.Definitions;
import com.survivalsos.goldentime.database.util.DatabaseConstantUtil;
import com.survivalsos.goldentime.model.Article;
import com.survivalsos.goldentime.model.CheckList;
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

    //메인화면 그림가져오기, asset의 적절한 파일 이름 리턴
    public static ArrayList<Integer> getMainImageFileNameFromAssetFolder(int section) {
        ArrayList<Integer> result = new ArrayList<>();

        String sqlQueryForMainPage = "SELECT CODE FROM CATEGORY WHERE CODE like '" + section + "%'";
//        DebugUtil.showDebug("query :: " + sqlQueryForMainPage);

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery(sqlQueryForMainPage, null);

        if (cursor == null)
            return null;

        while (cursor.moveToNext()) {
            result.add(cursor.getInt(0));
        }

        cursor.close();
        return result;
    }

    public static ArrayList<MainImageItemInfo> getMainImageItemInfoFromAssetFolder(int section) {
        ArrayList<MainImageItemInfo> result = new ArrayList<>();

        String sqlQueryForMainPage = "SELECT * FROM CATEGORY WHERE CODE like '" + section + "%'";
//        DebugUtil.showDebug("query :: " + sqlQueryForMainPage);

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

        cursor.close();
        return result;
    }


    //메인화면에서 클릭한 카테고리에 해당하는 ArticleList 가져오기
    public static ArrayList<Article> getArticleList(int section) {
        ArrayList<Article> result = new ArrayList<>();

        String sqlQueryForArticleList = "SELECT * FROM ARTICLE WHERE HIGH_RANK_CODE = " + section + "";
//        DebugUtil.showDebug("query :: " + sqlQueryForArticleList);

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

        cursor.close();
        return result;
    }


    //특정 Article_Id를 통해서 Article의 정보를 가져오는 함수
    public static Article getArticleInfo(int articleId) {
        Article result = new Article();

        String sqlQueryForArticleList = "SELECT * FROM ARTICLE WHERE ARTICLE_ID = " + articleId;
//        DebugUtil.showDebug("query :: " + sqlQueryForArticleList);

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


    //검색 화면에서 특정 검색어를 통해서 Article의 정보를 가져오는 함수
    public static ArrayList<Article> getSearchList(String inputText) {
        ArrayList<Article> result = new ArrayList<>();

        String sqlQueryForArticleList = "SELECT * FROM ARTICLE WHERE ARTICLE_TEXT LIKE '%" + inputText + "%'";
//        DebugUtil.showDebug("query :: " + sqlQueryForArticleList);

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

        cursor.close();
        return result;
    }


    //북마크 정보가 있는 아티클 인지를 확인하는 함수
    public static boolean isBookmarked(Integer inputInteger) {
        boolean isExist = false;

        String sqlQueryForArticleList = "SELECT * FROM " + DatabaseConstantUtil.TABLE_USER_BOOKMARK + " WHERE " +
                DatabaseConstantUtil.COLUMN_ARTICLE_ID + " = " + inputInteger;
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




    public static ArrayList<CheckList> getOriginCheckListFromDb() {
        ArrayList<CheckList> result = new ArrayList<>();

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery("select * from CHECK_LIST order by NO;", null);

        if (cursor == null) {
            DebugUtil.showDebug("DatabaseCRUD, getOriginCheckListFromDb() 결과가 없습니다");
            return null;
        }
        //헤더 여부를 가려내기 위한 작업
        ArrayList<Integer> categoryIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            CheckList checkList = new CheckList();
            checkList.no = cursor.getInt(0);
            checkList.title = cursor.getString(1);
            checkList.link = cursor.getInt(2);
            checkList.articleId = cursor.getInt(3);
            checkList.categoryId = cursor.getInt(4);
            checkList.isHeader = false;
            checkList.isInMyList = Definitions.CHECK_BOX_CHECKED.UNCHECKED;

            if (checkList.articleId != null) {
                checkList.setCategoryName(checkList.categoryId);
            }

            if (!categoryIds.contains(checkList.categoryId)) {
                categoryIds.add(checkList.categoryId);
                checkList.isHeader = true;
            }
            result.add(checkList);
        }
        cursor.close();
        return result;
    }

    //테이블 디버그로 확인하는 것
    public static String selectCheckedListDBQuery(String tableName) { // "CHECK_LIST" 이거나 DatabaseConstantUtil.TABLE_USER_CHECKED_LIST
        String result = "";

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery("select * from " + tableName + ";", null);

        if (cursor == null)
            return "결과 없음";
        if(tableName.equalsIgnoreCase(DatabaseConstantUtil.TABLE_USER_CHECKED_LIST)) {
            while (cursor.moveToNext()) {
                result += cursor.getInt(0) + ". "
                        + cursor.getString(1) + ", "
                        + cursor.getInt(2) + ", "
                        + cursor.getInt(3) + ", "
                        + cursor.getInt(4) + ", "
                        + cursor.getInt(5) + ", "
                        + cursor.getInt(6)
                        + "\n";
            }
        } else {
            while (cursor.moveToNext()) {
                result += cursor.getInt(0) + ". "
                        + cursor.getString(1) + ", "
                        + cursor.getInt(2) + ", "
                        + cursor.getInt(3) + ", "
                        + cursor.getInt(4) + ", "
//                        + cursor.getInt(5) + ", "
//                        + cursor.getInt(6)
                        + "\n";
            }
        }

        cursor.close();
        return result;
    }


    //체크리스트 all에서 보이는 화면에서 import된 모든 체크리스트를 반환
    public static ArrayList<CheckList> getUserCheckedListFromDb() {
        ArrayList<CheckList> result = new ArrayList<>();

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery("select * from " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST
                + " where " + DatabaseConstantUtil.COLUMN_IS_IN_MY_LIST_CHECKED_LIST + "=" + Definitions.CHECK_BOX_IMPORTED.IMPORTED
                + " order by NO " + ";", null);

        if (cursor == null) {
            DebugUtil.showDebug("DatabaseCRUD, getOriginCheckListFromDb() 결과가 없습니다");
            return null;
        }
        //헤더 여부를 가려내기 위한 작업
        ArrayList<Integer> categoryIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            CheckList checkList = new CheckList();
            checkList.no = cursor.getInt(0);
            checkList.title = cursor.getString(1);
            checkList.link = cursor.getInt(2);
            checkList.articleId = cursor.getInt(3);
            checkList.categoryId = cursor.getInt(4);
            checkList.isInMyList = cursor.getInt(5);
            checkList.isChecked = cursor.getInt(6);
            checkList.isHeader = false;

            if (checkList.articleId != null) {
                checkList.setCategoryName(checkList.categoryId);
            }

            if (!categoryIds.contains(checkList.categoryId)) {
                categoryIds.add(checkList.categoryId);
                checkList.isHeader = true;
            }

            DebugUtil.showDebug("checkList :: " + checkList.toString());
            result.add(checkList);
        }

        cursor.close();
        return result;
    }

    //유저 체크리스트에서 import도 되고 check도 되는 것
    public static ArrayList<CheckList> getUserCheckedAndImportListFromDb() {
        ArrayList<CheckList> result = new ArrayList<>();

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery("select * from " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST
                + " where " + DatabaseConstantUtil.COLUMN_IS_IN_MY_LIST_CHECKED_LIST + "=" + Definitions.CHECK_BOX_IMPORTED.IMPORTED
                + " and " + DatabaseConstantUtil.COLUMN_IS_CHECKED + "=" + Definitions.CHECK_BOX_CHECKED.CHECKED
                + " order by NO " + ";", null);

        if (cursor == null) {
            DebugUtil.showDebug("DatabaseCRUD, getOriginCheckListFromDb() 결과가 없습니다");
            return null;
        }
        //헤더 여부를 가려내기 위한 작업
        ArrayList<Integer> categoryIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            CheckList checkList = new CheckList();
            checkList.no = cursor.getInt(0);
            checkList.title = cursor.getString(1);
            checkList.link = cursor.getInt(2);
            checkList.articleId = cursor.getInt(3);
            checkList.categoryId = cursor.getInt(4);
            checkList.isInMyList = cursor.getInt(5);
            checkList.isChecked = cursor.getInt(6);
            checkList.isHeader = false;

            if (checkList.articleId != null) {
                checkList.setCategoryName(checkList.categoryId);
            }

            if (!categoryIds.contains(checkList.categoryId)) {
                categoryIds.add(checkList.categoryId);
                checkList.isHeader = true;
            }

            DebugUtil.showDebug("checkList :: " + checkList.toString());
            result.add(checkList);
        }

        cursor.close();
        return result;
    }



    //최초 1번만 실행
    public static String getInsertQueryFromCheckListTable() {
        String result = "";
        String startQuery = "insert or ignore into " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " (" + DatabaseConstantUtil.COLUMN_NO_USER_CHECKED_LIST + ", " +
                DatabaseConstantUtil.COLUMN_TITLE_USER_CHECKED_LIST + ", " + DatabaseConstantUtil.COLUMN_LINK_CHECKED_LIST + ", " +
                DatabaseConstantUtil.COLUMN_ARTICLE_ID_CHECKED_LIST + ", " + DatabaseConstantUtil.COLUMN_CATEGORY_ID_CHECKED_LIST + ", " +
                DatabaseConstantUtil.COLUMN_IS_IN_MY_LIST_CHECKED_LIST + ", " +
                DatabaseConstantUtil.COLUMN_IS_CHECKED + ") values ";
        String middleQuery = "";

        //String insertQuery = "insert or ignore into " + DatabaseConstantUtil.TABLE_USER_BOOKMARK + "(" + DatabaseConstantUtil.COLUMN_ARTICLE_ID + ", " +
        //DatabaseConstantUtil.COLUMN_TITLE+") values (" + currentArticle.articleId + ", '" + currentArticle.title+"')";

        ArrayList<CheckList> checkLists = new ArrayList<>();
        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery("select * from CHECK_LIST order by NO;", null);

        if (cursor == null) {
            DebugUtil.showDebug("DatabaseCRUD, getOriginCheckListFromDb() 결과가 없습니다");
            return null;
        }
        //헤더 여부를 가려내기 위한 작업
        ArrayList<Integer> categoryIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            CheckList checkList = new CheckList();
            checkList.no = cursor.getInt(0);
            checkList.title = cursor.getString(1);
            checkList.link = cursor.getInt(2);
            checkList.articleId = cursor.getInt(3);
            checkList.categoryId = cursor.getInt(4);

            checkList.isHeader = false;
            checkList.isInMyList = Definitions.CHECK_BOX_CHECKED.UNCHECKED;
            checkList.isChecked = Definitions.CHECK_BOX_CHECKED.UNCHECKED;

            if (checkList.articleId != null) {
                checkList.setCategoryName(checkList.categoryId);
            }

            if (!categoryIds.contains(checkList.categoryId)) {
                categoryIds.add(checkList.categoryId);
                checkList.isHeader = true;
            }

            if (checkList.no != cursor.getCount()) {
                middleQuery += "(" + checkList.no + ", '" + checkList.title + "', " + checkList.link + ", " + checkList.articleId + ", " + checkList.categoryId + ", " + checkList.isInMyList + ", " + checkList.isChecked + "), ";
                DebugUtil.showDebug("checkList.no :: " + checkList.no + " != " + "cursor.getCount() :: " + cursor.getCount());
            } else if (checkList.no == cursor.getCount()) {
                middleQuery += "(" + checkList.no + ", '" + checkList.title + "', " + checkList.link + ", " + checkList.articleId + ", " + checkList.categoryId + ", " + checkList.isInMyList + ", " + checkList.isChecked + ");";
                DebugUtil.showDebug("checkList.no :: " + checkList.no + " == " + "cursor.getCount() :: " + cursor.getCount());
            }
        }
        cursor.close();

        result = startQuery + middleQuery;
//        DebugUtil.showDebug("result :: " + result);
        return result;
    }


    //사용자가 추가하는 새로운 항목 insert 하는 쿼리
    public static String getInsertNewCheckListToUserCheckListTable(String inputString) {
        String result = "";
        String startQuery = "insert or ignore into " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " (" + DatabaseConstantUtil.COLUMN_NO_USER_CHECKED_LIST + ", " +
                DatabaseConstantUtil.COLUMN_TITLE_USER_CHECKED_LIST + ", " + DatabaseConstantUtil.COLUMN_LINK_CHECKED_LIST + ", " +
                DatabaseConstantUtil.COLUMN_ARTICLE_ID_CHECKED_LIST + ", " + DatabaseConstantUtil.COLUMN_CATEGORY_ID_CHECKED_LIST + ", " +
                DatabaseConstantUtil.COLUMN_IS_IN_MY_LIST_CHECKED_LIST + ", " +
                DatabaseConstantUtil.COLUMN_IS_CHECKED + ") values ";
        String middleQuery = "";

        int next = getImportCheckedListFromDb().size() + 1;
        middleQuery += "(" + next + ", '" + inputString + "', " + 0 + ", " + 0 + ", " + 8 + ", "
                + Definitions.CHECK_BOX_IMPORTED.IMPORTED + ", " + Definitions.CHECK_BOX_CHECKED.UNCHECKED + ")";

        result = startQuery + middleQuery;
        return result;
    }

    //유저 고유의 체크리스트
    public static ArrayList<CheckList> getImportCheckedListFromDb() {
        ArrayList<CheckList> result = new ArrayList<>();

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery("select * from " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST
                + " order by NO", null);

        if (cursor == null) {
            DebugUtil.showDebug("DatabaseCRUD, getOriginCheckListFromDb() 결과가 없습니다");
            return null;
        }
        //헤더 여부를 가려내기 위한 작업
        ArrayList<Integer> categoryIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            CheckList checkList = new CheckList();
            checkList.no = cursor.getInt(0);
            checkList.title = cursor.getString(1);
            checkList.link = cursor.getInt(2);
            checkList.articleId = cursor.getInt(3);
            checkList.categoryId = cursor.getInt(4);
            checkList.isInMyList = cursor.getInt(5);
            checkList.isChecked = cursor.getInt(6);
            checkList.isHeader = false;

            if (checkList.articleId != null) {
                checkList.setCategoryName(checkList.categoryId);
            }

            if (!categoryIds.contains(checkList.categoryId)) {
                categoryIds.add(checkList.categoryId);
                checkList.isHeader = true;
            }

            DebugUtil.showDebug("checkList :: " + checkList.toString());
            result.add(checkList);
        }

        cursor.close();
        return result;
    }

    //유저 테이블이 있는지에 대한 여부
    public static boolean doesCheckedListTableExist() {
        boolean doesCheckedListTableExist = false;
        int count = 0;
        Cursor cursor   =   null;
        try{
            cursor = DatabaseHelper.sqLiteDatabase.rawQuery("select * from " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " order by NO;", null);
            if (cursor == null) {
                DebugUtil.showDebug("DatabaseCRUD, getOriginCheckListFromDb() 결과가 없습니다");
                doesCheckedListTableExist = false;
            } else {
                count = cursor.getCount();
                if (count > 0) {
                    doesCheckedListTableExist = true;
                }
            }

        }catch (Exception err){
            doesCheckedListTableExist = true;
        }finally {
            cursor.close();
        }



        return doesCheckedListTableExist;
    }


    //유저가 개별적으로 조작이 가능한 테이블에 대한 select
    public static ArrayList<CheckList> selectUserCheckList(String query) {
        ArrayList<CheckList> result = new ArrayList<>();

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery(query, null);

        if (cursor == null) {
            DebugUtil.showDebug("DatabaseCRUD, getOriginCheckListFromDb() 결과가 없습니다");
            return null;
        }
        //헤더 여부를 가려내기 위한 작업
        ArrayList<Integer> categoryIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            CheckList checkList = new CheckList();
            checkList.no = cursor.getInt(0);
            checkList.title = cursor.getString(1);
            checkList.link = cursor.getInt(2);
            checkList.articleId = cursor.getInt(3);
            checkList.categoryId = cursor.getInt(4);
            checkList.isInMyList = cursor.getInt(5);
            checkList.isChecked = cursor.getInt(6);
            checkList.isHeader = false;

            if (checkList.articleId != null) {
                checkList.setCategoryName(checkList.categoryId);
            }

            if (!categoryIds.contains(checkList.categoryId)) {
                categoryIds.add(checkList.categoryId);
                checkList.isHeader = true;//의미없음
            }
            result.add(checkList);
        }
        cursor.close();
        return result;
    }

    //모든 유저가 동일한 테이블에서 셀랙트
    public static ArrayList<CheckList> selectOriginalCheckList(String query) {
        ArrayList<CheckList> result = new ArrayList<>();

        Cursor cursor = DatabaseHelper.sqLiteDatabase.rawQuery(query, null);

        if (cursor == null) {
            DebugUtil.showDebug("DatabaseCRUD, getOriginCheckListFromDb() 결과가 없습니다");
            return null;
        }
        //헤더 여부를 가려내기 위한 작업
        ArrayList<Integer> categoryIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            CheckList checkList = new CheckList();
            checkList.no = cursor.getInt(0);
            checkList.title = cursor.getString(1);
            checkList.link = cursor.getInt(2);
            checkList.articleId = cursor.getInt(3);
            checkList.categoryId = cursor.getInt(4);
//            checkList.isInMyList = cursor.getInt(5);
//            checkList.isChecked = cursor.getInt(6);
            checkList.isHeader = false;

            if (checkList.articleId != null) {
                checkList.setCategoryName(checkList.categoryId);
            }

            if (!categoryIds.contains(checkList.categoryId)) {
                categoryIds.add(checkList.categoryId);
                checkList.isHeader = true;//의미없음
            }

            result.add(checkList);
        }
        cursor.close();
        return result;
    }



}
