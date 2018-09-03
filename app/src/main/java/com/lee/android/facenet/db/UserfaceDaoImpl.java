package com.lee.android.facenet.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.gson.Gson;
import com.lee.android.facenet.FaceFeature;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by llakcs on 2018/3/21.
 */

public class UserfaceDaoImpl {
   public static final String TAG="UserfaceDaoImpl";
    DBHelper dbHelper;
    public static String userface ="useface";
    private Gson gson= new Gson();;
    public UserfaceDaoImpl(Context context) {
        dbHelper= new DBHelper(context);
    }
    /**
     *添加数据
     * @param faceFeature
     */
    public void Save(FaceFeature faceFeature){
        //获取写数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //生成要修改或者插入的键值
        ContentValues cv = new ContentValues();
        //1.float[]数组转换为json
        String facestr = gson.toJson(faceFeature.getFeature());
        cv.put(userface,facestr);
        // insert 操作
        db.insert(DBHelper.DB_TABLE_USER, null, cv);
        //关闭数据库
        db.close();
        cv.clear();
    }

    /**
     *  更新一条数据
     * @param faceFeature
     */
    public void Update(FaceFeature faceFeature){
        //生成条件语句
        StringBuffer whereBuffer = new StringBuffer();
        //1.float[]数组转换为json
        String facestr = gson.toJson(faceFeature.getFeature());
        whereBuffer.append(userface).append(" = ").append("'").append(facestr).append("'");
        //生成要修改或者插入的键值
        ContentValues cv = new ContentValues();
        cv.put(userface, facestr);
        //获取写数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // update 操作
        db.update(DBHelper.DB_TABLE_USER, cv, whereBuffer.toString(), null);
        //关闭数据库
        db.close();
        cv.clear();
    }


    /**
     * 删除一条数据
     * @param faceFeature
     */
    public void Delete(FaceFeature faceFeature){
        //1.float[]数组转换为json
        String facestr = gson.toJson(faceFeature.getFeature());
        //生成条件语句
        StringBuffer whereBuffer = new StringBuffer();
        whereBuffer.append(userface).append(" = ").append("'").append(facestr).append("'");
        //获取写数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // update 操作
        db.delete(DBHelper.DB_TABLE_USER, whereBuffer.toString(), null);
        //关闭数据库
        db.close();
    }


    /**
     * 清空表数据
     */
    public void DeleteAll(){
        //获取写数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // update 操作
        String sql = "DELETE FROM  " + DBHelper.DB_TABLE_USER +";";
//        String sql2 = "update sqlite_sequence set seq=0 where name='"+DBHelper.DB_TABLE_USER+"'";
        db.execSQL(sql);
//        db.execSQL(sql2);
        //关闭数据库
        db.close();
    }


    /**
     * 查询所有数据
     */
    public List<String> Query() {
        List<String> jsonstr = new ArrayList<>();
        //获取写数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.DB_TABLE_USER, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String facegson =cursor.getString(cursor.getColumnIndex(userface));
                jsonstr.add(facegson);
            }
        }
        cursor.close();
        return jsonstr;
    }
}
