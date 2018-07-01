package com.song.a3gcacheutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by song on 2018/6/30.
 * Email：815464927@qq.com
 */
public class NetCatcheUtils {

    private MemoryCacheUtils mMemoryCacheUtils;
    private LocalCacheUtils mLocacheUtils;

    public NetCatcheUtils(MemoryCacheUtils memoryCacheUtils, LocalCacheUtils localCacheUitls) {
        mMemoryCacheUtils = memoryCacheUtils;
        mLocacheUtils = localCacheUitls;
    }

    public void getBitmapFromNet(ImageView iv, String url) {
        new BitmapTast().execute(iv,url);
    }

    class BitmapTast extends AsyncTask<Object,Void,Bitmap>{

        private ImageView iv;
        private String url;

        /**
         * do in thread
         * @param objects o
         * @return bitmap
         */
        @Override
        protected Bitmap doInBackground(Object... objects) {
            iv = (ImageView) objects[0];
            url = (String) objects[1];
            return downLoadBitmap(url);
        }

        /**
         * update progress
         * @param values v
         */
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        /**
         * call after doInBackground
         * @param bitmap bm
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(null != bitmap){
                iv.setImageBitmap(bitmap);
                Log.d("song--->","first get data form net");

                //add cache to Local
                mLocacheUtils.setBitmapCacheFromLocal(url,bitmap);
                //add cache to memory
                mMemoryCacheUtils.setBitmapCacheFromMemory(url,bitmap);
            }
        }
    }

    /**
     * get bitmap form net
     * @param url image url
     * @return bitmap of image
     */
    private Bitmap downLoadBitmap(String url) {

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5*1000);
            conn.setReadTimeout(5*1000);
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if(200 == responseCode){//200 == request  respon ok
                //压缩图片
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;//取样压缩原来的1/2
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                return BitmapFactory.decodeStream(conn.getInputStream(),null,options);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return null;
    }

}
