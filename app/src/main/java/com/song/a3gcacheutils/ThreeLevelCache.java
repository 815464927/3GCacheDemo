package com.song.a3gcacheutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

/**
 * demo练习三级缓存
 * Created by song on 2018/6/30.
 * Email：815464927@qq.com
 */
public class ThreeLevelCache {

    private static ThreeLevelCache mInstance;
    private NetworkCatche mNetworkCatche;
    private LocalCache mLocalCache;
    private MemoryCache mMemoryCache;

    public static ThreeLevelCache instance(Context context){
        if(null == mInstance){
            synchronized (ThreeLevelCache.class){
                if(null == mInstance) {
                    mInstance = new ThreeLevelCache(context);
                }
            }
        }
        return mInstance;
    }

    private ThreeLevelCache(Context context){
        mMemoryCache = new MemoryCache();
        mLocalCache = new LocalCache(context);
        mNetworkCatche = new NetworkCatche(mMemoryCache,mLocalCache);
    }

    public void display(ImageView iv, String url){
        iv.setImageResource(R.mipmap.ic_launcher);
        Bitmap bm;
        //get cache form memory
        bm = mMemoryCache.getBitmapCacheFromMemory(url);
        if(null != bm){
            iv.setImageBitmap(bm);
            Log.d("song--->","get cache form memory");
            return;
        }

        //get cache form local
        bm = mLocalCache.getBitmapCacheFromLocal(url);
        if(null != bm){
            iv.setImageBitmap(bm);
            Log.d("song--->","get cache form local");
            return;
        }

        //get cache form net
        mNetworkCatche.getBitmapFromNet(iv,url);
    }

}
