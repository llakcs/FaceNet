package com.lee.android.facenet;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.List;

public interface FaceNetManger {
    /**
     * 初始化SDK
     * @param pdPath  人脸数据库路径
     * @param context
     */
    void Init(String pdPath,Context context);

    /**
     * 保存人脸数据到数据库
     * @param bm  bitmap
     */
    void Savefeature(Bitmap bm);

    /**
     * 提取特征值
     * @param bm
     * @return
     */
    FaceFeature Getfeature(Bitmap bm);

    /**
     * 对比人脸
     * @param faceFeature
     */
    Boolean Compare(FaceFeature faceFeature);
}
