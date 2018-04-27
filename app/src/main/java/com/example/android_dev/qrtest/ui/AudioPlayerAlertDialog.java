package com.example.android_dev.qrtest.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_dev.qrtest.R;

import nl.changer.audiowife.AudioWife;

public class AudioPlayerAlertDialog implements IAudioPlayerAlertDialog {

    @Override
    public void playTrack(String filePath, Context context) {
        final View view = LayoutInflater.from(context).inflate(R.layout.alert_audio_player, null);

        AudioWife.getInstance().pause();
        AudioWife.getInstance().release();

        AlertDialog.Builder builder = new AlertDialog
                .Builder(new ContextThemeWrapper(view.getContext(), R.style.Theme_AppCompat_Light));
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        AudioWife.getInstance().init(view.getContext(), Uri.parse(filePath))
                .useDefaultUi((ViewGroup) view, LayoutInflater.from(view.getContext()));

    }
}
