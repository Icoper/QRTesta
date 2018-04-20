package com.example.android_dev.qrtest.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android_dev.qrtest.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

public class ImageViewer extends AppCompatActivity {
    final static String LOG_TAG = "ImageViewer";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_image_viewer);

        Log.i(LOG_TAG, "onCreate");
        Intent intent = getIntent();
        String filepath = intent.getStringExtra("path");
        Fresco.initialize(this);

        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) findViewById(R.id.aiv_image_view);
        Log.d(LOG_TAG, "path - " + filepath);
        Uri uri = Uri.parse(filepath);
        simpleDraweeView.setImageURI(uri);
    }
}
