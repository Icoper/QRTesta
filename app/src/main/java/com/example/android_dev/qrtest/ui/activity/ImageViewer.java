package com.example.android_dev.qrtest.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android_dev.qrtest.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class ImageViewer extends AppCompatActivity {
    final static String LOG_TAG = "ImageViewer";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        Log.i(LOG_TAG, "onCreate");
        Intent intent = getIntent();
        String filepath = intent.getStringExtra("path");

        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) findViewById(R.id.aiv_image_view);
        Log.d(LOG_TAG, "path - " + filepath);
        Uri uri = Uri.parse(filepath);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(50, 50))
                .build();
        simpleDraweeView.setController(
                Fresco.newDraweeControllerBuilder()
                        .setOldController(simpleDraweeView.getController())
                        .setImageRequest(request)
                        .build());
        simpleDraweeView.showContextMenu();
    }


}
