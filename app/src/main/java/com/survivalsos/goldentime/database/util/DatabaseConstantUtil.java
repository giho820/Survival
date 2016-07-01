package com.survivalsos.goldentime.database.util;


public class DatabaseConstantUtil {

    public static final String DATABASE_SQLITE_NAME = "SURVIVAL_V2.sqlite";
    public static final String PAKAGE_NAME = "com.survivalsos.goldentime";
    public static final String DATABASE_DB_NAME = "Survival.db";
    public static final String DATABASE_CHANGEABLE_DB_NAME = "ChangealbeSurvival.db";
    public static final String DATABASE_PATH = "/data/data/" + PAKAGE_NAME + "/databases/" + DATABASE_DB_NAME;
    public static final String CHANGEABLE_DB_PATH = "/data/data/" + PAKAGE_NAME + "/databases/" + DATABASE_CHANGEABLE_DB_NAME;

    //Todo 다른 디비를 만들어서 체크리스트나 북마크는 별도로 저장하도록 한다.

    //CHECK_LIST TABLE
    public static final String TABLE_CHECK_LIST = "CHECK_LIST";

    //BOOKMARK_TABLE
    public static final String TABLE_USER_BOOKMARK= "USER_BOOKMARK_TABLE";
    public static final String COLUMN_ARTICLE_ID = "ARTICLE_ID";
    public static final String COLUMN_TITLE = "TITLE";

    //북마크 테이블 Create문
    public static final String CREATE_USER_BOOKMARK_TABLE = "CREATE TABLE " + DatabaseConstantUtil.TABLE_USER_BOOKMARK +"(" +
            DatabaseConstantUtil.COLUMN_ARTICLE_ID + " INTEGER PRIMARY KEY  NOT NULL, " +
            DatabaseConstantUtil.COLUMN_TITLE + " TEXT);";


    //유저가 체크한 체크리스트
    // Todo (반드시! 새로운 디비가 와도 사용자가 체크한 내용은 변화가 없어야한다)
    public static final String TABLE_USER_CHECKED_LIST = "USER_CHECKED_LIST_TABLE";
    public static final String COLUMN_NO_USER_CHECKED_LIST = "NO";
    public static final String COLUMN_TITLE_USER_CHECKED_LIST = "TITLE";
    public static final String COLUMN_LINK_CHECKED_LIST = "LINK";
    public static final String COLUMN_ARTICLE_ID_CHECKED_LIST = "ARTICLE_ID";
    public static final String COLUMN_CATEGORY_ID_CHECKED_LIST = "CATEGORY_ID";
    public static final String COLUMN_IS_IN_MY_LIST_CHECKED_LIST = "IN_MY_LIST";
    public static final String COLUMN_IS_CHECKED = "IS_CHECKED";

    //체크리스트 테이블 Create 문
    public static final String CREATE_USER_CHECKED_LIST_TABLE = "create table " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + "(" +
            DatabaseConstantUtil.COLUMN_NO_USER_CHECKED_LIST + " integer primary key Not null, " +
            DatabaseConstantUtil.COLUMN_TITLE_USER_CHECKED_LIST + " text, " +
            DatabaseConstantUtil.COLUMN_LINK_CHECKED_LIST + " text , " +
            DatabaseConstantUtil.COLUMN_ARTICLE_ID_CHECKED_LIST + " integer , " +
            DatabaseConstantUtil.COLUMN_CATEGORY_ID_CHECKED_LIST + " integer , " +
            DatabaseConstantUtil.COLUMN_IS_IN_MY_LIST_CHECKED_LIST + " integer, " +
            DatabaseConstantUtil.COLUMN_IS_CHECKED +" integer);";


    //Todo 업데이트 테스트 수행한 결과 잘 되지 않았음 -> 고칠 것
    public static int DATABASE_VERSION = 5;
    public static int CHANGEABLE_DB_VERSION = 1;


}
