package com.song.a3gcacheutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mIv;
    private String url = "https://avatars2.githubusercontent.com/u/19726652?s=400&u=c865a4834f48ac196fda121513015f1711720bee&v=4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIv = findViewById(R.id.iv);
        MyBitmapUtils.instance(this).display(mIv,url);
    }
}
