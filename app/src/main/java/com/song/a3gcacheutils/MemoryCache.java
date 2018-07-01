package com.song.a3gcacheutils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * Created by song on 2018/6/30.
 * Email：815464927@qq.com
 */
public class MemoryCache {

    private LruCache<String,Bitmap> mLruCach;

    public MemoryCache(){
        long maxMemory = Runtime.getRuntime().maxMemory()/8;
        mLruCach = new LruCache<String, Bitmap>((int)maxMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return  value.getByteCount();
            }
        };
    }

    public Bitmap getBitmapCacheFromMemory(String url) {
        return  mLruCach.get(url);
    }

    public void setBitmapCacheFromMemory(String url, Bitmap bitmap) {
        mLruCach.put(url,bitmap);
        Log.d("song--->","save cache to memory");
    }
}
