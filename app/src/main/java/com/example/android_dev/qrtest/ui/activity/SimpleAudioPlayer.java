package com.example.android_dev.qrtest.ui.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.android_dev.qrtest.R;

import java.io.File;

public class SimpleAudioPlayer extends AppCompatActivity implements OnClickListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener {
    private final String LOG_TAG = "SimpleAudioPlayer";

    private String soundPath;
    private Button playBtn;
    private Button stopBtn;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_audio_player);
        Intent intent = getIntent();
        soundPath = intent.getStringExtra("path");
        playBtn = (Button) findViewById(R.id.sap_play_btn);
        stopBtn = (Button) findViewById(R.id.sap_stop_btn);

        playBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.sap_play_btn:
                playSound();
                break;
            case R.id.sap_stop_btn:
                stopPlaySound();
                break;
        }
    }

    public void playSound() {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.reset();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this, Uri.parse(new File(soundPath).getPath()));
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.start();

    }

    public void stopPlaySound() {
        try {
            mediaPlayer.stop();
            releaseMP();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d(LOG_TAG, "onCompletion");
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(LOG_TAG, "onPrepared");
        mediaPlayer.start();
    }

    private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        stopPlaySound();
        super.onDestroy();
    }
}
