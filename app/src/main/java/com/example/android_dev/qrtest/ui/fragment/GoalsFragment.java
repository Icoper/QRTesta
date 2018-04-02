package com.example.android_dev.qrtest.ui.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.IGoalsDataStore;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.presenter.goals_fragment.GoalsFragmentPresenter;
import com.example.android_dev.qrtest.ui.activity.SimpleAudioPlayer;
import com.example.android_dev.qrtest.ui.activity.SimpleVideoPlayer;
import com.example.android_dev.qrtest.ui.adapter.MediaArrayAdapter;

public class GoalsFragment extends Fragment {
    private MediaArrayAdapter mediaArrayAdapter;
    private View view;
    private Context mContext;
    private RecyclerView recyclerView;
    private IGoalsDataStore iGoalsDataStore;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goals, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.gf_recycler_view);
        mContext = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mediaArrayAdapter = new MediaArrayAdapter(new MediaArrayAdapter.OnItemStoryClickListener() {
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
                        Intent intent = new Intent(mContext, SimpleAudioPlayer.class);
                        intent.putExtra("path", filePath);
                        startActivity(intent);
                    }
                },iGoalsDataStore).playMediaData(resource);
            }
        }, iGoalsDataStore.getAll());
        recyclerView.setAdapter(mediaArrayAdapter);
        return view;
    }
    public void setupRepository( IGoalsDataStore iGoalsDataStore){
        this.iGoalsDataStore = iGoalsDataStore;
    }
}
