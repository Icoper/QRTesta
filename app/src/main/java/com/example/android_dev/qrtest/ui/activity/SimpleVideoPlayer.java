package com.example.android_dev.qrtest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.android_dev.qrtest.R;

public class SimpleVideoPlayer extends AppCompatActivity {
    private VideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_shower);
        videoView = (VideoView) findViewById(R.id.avs_video_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.avs_my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("");
        Intent intent = getIntent();
        String filepath = intent.getStringExtra("res");

        startPlay(filepath);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void startPlay(String filePath) {
        videoView.setVideoPath(filePath);
        videoView.setMediaController(new MediaController(this));
        videoView.start();
    }
}
