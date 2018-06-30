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
public class MyBitmapUtils {

    private static MyBitmapUtils mInstance;
    private NetCatcheUtils mNetCatcheUitls;
    private LocalCacheUtils mLocalCacheUitls;
    private MemoryCacheUtils mMemoryCacheUtils;

    public static MyBitmapUtils instance(Context context){
        if(null == mInstance){
            synchronized (MyBitmapUtils.class){
                if(null == mInstance) {
                    mInstance = new MyBitmapUtils(context);
                }
            }
        }
        return mInstance;
    }

    private MyBitmapUtils(Context context){
        mMemoryCacheUtils = new MemoryCacheUtils();
        mLocalCacheUitls = new LocalCacheUtils(context);
        mNetCatcheUitls = new NetCatcheUtils(mMemoryCacheUtils,mLocalCacheUitls);
    }

    public void display(ImageView iv, String url){
        iv.setImageResource(R.mipmap.ic_launcher);
        Bitmap bm;
        //get cache form memory
        bm = mMemoryCacheUtils.getBitmapCacheFromMemory(url);
        if(null != bm){
            iv.setImageBitmap(bm);
            Log.d("song--->","get cache form memory");
            return;
        }

        //get cache form local
        bm = mLocalCacheUitls.getBitmapCacheFromLocal(url);
        if(null != bm){
            iv.setImageBitmap(bm);
            Log.d("song--->","get cache form local");
            return;
        }

        //get cache form net
        mNetCatcheUitls.getBitmapFromNet(iv,url);
    }

}
