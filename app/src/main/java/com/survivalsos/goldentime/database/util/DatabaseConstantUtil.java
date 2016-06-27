package com.survivalsos.goldentime.database.util;


public class DatabaseConstantUtil {

    public static final String DATABASE_SQLITE_NAME = "SURVIVAL_V2.sqlite";
    public static final String DATABASE_DB_NAME = "Survival.db";
    public static final String PAKAGE_NAME = "com.survivalsos.goldentime";
    public static final String DATABASE_PATH = "/data/data/" + PAKAGE_NAME + "/databases/" + DATABASE_DB_NAME;

    //CATEGORY 테이블
    public static final String TABLE_CATEGORY = "CATEGORY";
    public static final String COLUMN_CODE = "IDX";
    public static final String COLUMN_CODE_NAME = "CODE_NAME";

    //BOOKMARK_TABLE
    public static final String TABLE_USER_BOOKMARK= "USER_BOOKMARK_TABLE";
    public static final String COLUMN_ARTICLE_ID = "ARTICLE_ID";
    public static final String COLUMN_TITLE = "TITLE";



//
//    public static final String CREATE_INTELLIGENT_GALLERY_TABLE = "create table " + DatabaseConstantUtil.TABLE_INTELLIGENT_GALLERY_NAME + "(" +
//            DatabaseConstantUtil.COLUMN_AUTO_INCREMENT_KEY + " integer primary key autoincrement Not null UNIQUE, " +
//            DatabaseConstantUtil.COLUMN_DID + " text Not null, " +
//            DatabaseConstantUtil.COLUMN_CATEGORY_ID + " Integer Not null, " +
//            DatabaseConstantUtil.COLUMN_RANK + " integer Not null, " +
//            DatabaseConstantUtil.COLUMN_SCORE + " double Not null, " +
//            "unique (" +DatabaseConstantUtil.COLUMN_DID +", "+ DatabaseConstantUtil.COLUMN_RANK + ")" +
//            ");";
//
//    public static final String CREATE_INTELLIGENT_GALLERY_ALBUM_COVER_TABLE = "create table " + DatabaseConstantUtil.TABLE_ALBUM_COVER + " (" +
//            DatabaseConstantUtil.COLUMN_AUTO_INCREMENT_KEY_TABLE_ALBUM_COVER + " integer primary key autoincrement Not null UNIQUE, " +
//            DatabaseConstantUtil.COLUMN_ALBUM_BUCKET_ID + " text Not null, " +
//            DatabaseConstantUtil.COLUMN_ALBUM_COVER_IMAGE_ID + " Integer Not null, " +
//            "unique (" +DatabaseConstantUtil.COLUMN_AUTO_INCREMENT_KEY_TABLE_ALBUM_COVER +", "+ DatabaseConstantUtil.COLUMN_ALBUM_BUCKET_ID + ")" +
//            ");";



    public static final String CREATE_USER_BOOKMARK_TABLE = "CREATE TABLE " + DatabaseConstantUtil.TABLE_USER_BOOKMARK +"(" +
            DatabaseConstantUtil.COLUMN_ARTICLE_ID + " INTEGER PRIMARY KEY  NOT NULL, " +
            DatabaseConstantUtil.COLUMN_TITLE + " TEXT);";

    //Todo 업데이트 테스트 수행한 결과 잘 되지 않았음 -> 고칠 것
    public static int DATABASE_VERSION = 3;


}
