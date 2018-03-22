package com.example.android_dev.qrtest.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.android_dev.qrtest.R;

public class SimpleVideoPlayer extends AppCompatActivity {
    private int res;
    private VideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_shower);
        videoView = (VideoView) findViewById(R.id.avs_video_view);
        Intent intent = getIntent();
        String resString = intent.getStringExtra("res");
        res = Integer.parseInt(resString);
        String filepath = "android.resource://" + getPackageName() + "/" + res;
        videoView.setVideoPath(filepath);
        videoView.setMediaController(new MediaController(this));
        videoView.start();
    }

}
