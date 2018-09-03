package com.lee.android.facenet;

public class FaceN {



    public static FaceNetManger facenet(){
        if(Ext.faceNetManger == null){
            FaceNetimpl.registerInstance();
        }
        return Ext.faceNetManger;
    }

    /**
     * 扩展类
     */
    public static class Ext{

        private static FaceNetManger faceNetManger;
        private Ext(){
        }


        public static void setOpencvManager(FaceNetManger faceNetManger){
            Ext.faceNetManger = faceNetManger;
        }


    }
}
