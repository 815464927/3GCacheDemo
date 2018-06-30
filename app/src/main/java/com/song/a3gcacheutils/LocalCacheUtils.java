package com.song.a3gcacheutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by song on 2018/6/30.
 * Email：815464927@qq.com
 */
public class LocalCacheUtils {

    private Context mContext;
    public LocalCacheUtils(Context ctx){
        mContext = ctx;
    }
    /**
     * get cache form local
     * @param url cache url
     * @return bitmap
     */
    public Bitmap getBitmapCacheFromLocal(String url) {
        String name = null;
        try {
            name = MD5Encoder.encode(url);
            File file = new File(getCachePath(),name);
            return BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * save bitmap to local
     * @param url bitmap url
     * @param bitmap bitmap
     */
    public void setBitmapCacheFromLocal(String url, Bitmap bitmap) {

        String name = null;
        try {
            name = MD5Encoder.encode(url);
            File file = new File(getCachePath(),name);
            File parentFile = file.getParentFile();
            if(!parentFile.exists())
                parentFile.mkdirs();
            //save bitmap to local file
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));
            Log.d("song--->","save cache to local");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * get cache path
     * @return path
     */
    private String getCachePath(){
        String state = Environment.getExternalStorageState();
        String path = "LocalCache/"+mContext.getPackageName()+"/data";
        File dir = null;
        if(!TextUtils.isEmpty(state) && Environment.MEDIA_MOUNTED.equals(state)){
            //has sdCard
            dir = new File(Environment.getExternalStorageDirectory(),path);
        } else {
            //no scCard
            dir = new File(mContext.getCacheDir(),path);
        }
        return dir.getAbsolutePath();
    }

}