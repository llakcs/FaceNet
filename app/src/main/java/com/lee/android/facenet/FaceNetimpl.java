package com.lee.android.facenet;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.lee.android.facenet.db.UserfaceDaoImpl;
import com.lee.android.facenet.tools.UnzipFromAssets;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class FaceNetimpl implements FaceNetManger {
    private static final Object lock = new Object();
    private static volatile FaceNetimpl instance;
    private Gson gson = new Gson();
    private Facenet facenet;
    private List<Float[]> facelist;
    private UserfaceDaoImpl userfaceDao;

    public static void registerInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new FaceNetimpl();
                }
            }
        }
        FaceN.Ext.setOpencvManager(instance);
    }

    @Override
    public void Init(final String pdPath, final Context context) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    userfaceDao = new UserfaceDaoImpl(context);
                    new File(pdPath).mkdirs();
                    UnzipFromAssets.unZip(context, "facetrain.zip", pdPath, true);
                    Thread.sleep(500);
                    File mfile = new File(pdPath);
                    FileInputStream in = new FileInputStream(mfile);
                    //载入facenet
                    facenet = new Facenet(in);
                    if(facenet == null){
                        return  null;
                    }
                    facelist = savememory(userfaceDao.Query());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }


    private List<Float[]> savememory(List<String> faces) {
        List<Float[]> ff = new ArrayList<>();
        for (String facegson : faces) {
            Float[] ff1 = gson.fromJson(facegson, Float[].class);
            ff.add(ff1);
        }
        return ff;
    }

    @Override
    public void Savefeature(final Bitmap bm) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                FaceFeature ff1=facenet.recognizeImage(bm);
                userfaceDao.Save(ff1);
                updatememory();
                return null;
            }
        }.execute();
    }

    @Override
    public FaceFeature Getfeature(Bitmap bm) {
        return facenet.recognizeImage(bm);
    }

    @Override
    public Boolean Compare(FaceFeature faceFeature) {
        if(facelist != null){
            Double d;
            for(Float[] face:facelist){
                d =faceFeature.compare(face);
                Log.e("FacerecognActivity","### d ="+d);
                if(d<0.8){
                    Log.e("FacerecognActivity","###小于0.8");
                    return true;
                }
            }
        }
        return false;
    }

    private void updatememory(){
        List<String> facejson = userfaceDao.Query();
        facelist =savememory(facejson);
    }

}
