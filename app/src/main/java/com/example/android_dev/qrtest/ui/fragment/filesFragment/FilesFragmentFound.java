package com.example.android_dev.qrtest.ui.fragment.filesFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.GoalsDataStore;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.presenter.goals.GoalsFragmentPresenter;
import com.example.android_dev.qrtest.ui.activity.SimpleVideoPlayer;
import com.example.android_dev.qrtest.ui.adapter.mediaAdapter.MediaArrayAdapter;
import com.example.android_dev.qrtest.ui.fragment.IGoalsFragment;

/**
 * Created by user on 11-Apr-18.
 */

public class FilesFragmentFound extends Fragment {
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_files, container, false);
        mContext = v.getContext();
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.ff_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        MediaArrayAdapter mediaArrayAdapter = new MediaArrayAdapter(new MediaArrayAdapter.OnItemStoryClickListener() {
            @Override
            public void onClick(AssetTypes resource) {
                new GoalsFragmentPresenter(new IGoalsFragment() {
                    @Override
                    public void showMsg(String msg) {
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void startVideoPlayerActivity(String filePath) {
                        Intent intent = new Intent(mContext, SimpleVideoPlayer.class);
                        intent.putExtra("res", filePath);
                        startActivity(intent);
                    }

                    @Override
                    public void startAudioPlayerActivity(String filePath) {
                        Intent intent = new Intent();
                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(filePath), "audio/*");
                        startActivity(intent);
                    }
                }, new GoalsDataStore()).playMediaData(resource);
            }
        }, new GoalsDataStore().getAll());
        recyclerView.setAdapter(mediaArrayAdapter);
        return v;
    }
}
