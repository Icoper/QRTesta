package com.example.android_dev.qrtest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.android_dev.qrtest.R;

public class ImageViewer extends AppCompatActivity {
    final static String LOG_TAG = "ImageViewer";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        ImageView imageView = (ImageView) findViewById(R.id.aiv_image_view);

        Log.i(LOG_TAG, "onCreate");
        Intent intent = getIntent();
        String filepath = intent.getStringExtra("path");
        Glide.with(this).load(filepath).into(imageView);
    }


}
