package com.lee.android.facenet.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by llakcs on 2018/3/21.
 */

public class DBHelper extends SQLiteOpenHelper {
    private String TAG="DBHelper";
    private static final String DB_NAME = "dface_db";//数据库名字
    private static final int DB_VERSION = 1;   // 数据库版本
    public  static final String DB_TABLE_USER="face";
    private String CREATESQL2 ="create table if not exists "+DB_TABLE_USER+"("+UserfaceDaoImpl.userface +" varchar primary key );";
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 创建数据库
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG,"###onCreate#SQLiteDatabase");
        try {
            db.execSQL(CREATESQL2);
        } catch (SQLException e) {
            Log.e(TAG, "onCreate " + "" + "Error" + e.toString());
            return;
        }
    }

    /**
     * 数据库升级
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }



}
