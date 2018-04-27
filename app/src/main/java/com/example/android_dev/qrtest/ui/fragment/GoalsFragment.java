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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.GoalsDataStore;
import com.example.android_dev.qrtest.db.IGoalsDataStore;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.presenter.goals.GoalsFragmentPresenter;
import com.example.android_dev.qrtest.ui.AudioPlayerAlertDialog;
import com.example.android_dev.qrtest.ui.IAudioPlayerAlertDialog;
import com.example.android_dev.qrtest.ui.activity.SimpleVideoPlayer;
import com.example.android_dev.qrtest.ui.adapter.mediaAdapter.MediaArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class GoalsFragment extends Fragment {
    private Context mContext;
    private RecyclerView oldGoalsRecyclerView;
    private RecyclerView newGoalsRecyclerView;
    private AHBottomNavigation bottomNavigationView;
    private View view;
    private IGoalsDataStore iGoalsDataStore;
    private IAudioPlayerAlertDialog audioPlayerAlertDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goals, container, false);
        mContext = view.getContext();
        iGoalsDataStore = new GoalsDataStore();
        audioPlayerAlertDialog = new AudioPlayerAlertDialog();
        setupNewGoals();
        setupOldGoals();

        bottomNavigationView.setNotification("", 4);
        return view;
    }

    private void setupOldGoals() {
        oldGoalsRecyclerView = (RecyclerView) view.findViewById(R.id.gf_recycler_view);
        List<Integer> oldGoalsRes = iGoalsDataStore.getAll();
        setupRecyclerView(oldGoalsRecyclerView, oldGoalsRes);
    }

    private void setupNewGoals() {
        List<Integer> newGoalsRes = new ArrayList<>();
        if (iGoalsDataStore.loadNewGoals().size() > 0) {
            newGoalsRes = iGoalsDataStore.loadNewGoals();
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.gf_new_goals_layout);
            linearLayout.setVisibility(View.VISIBLE);
            newGoalsRecyclerView = (RecyclerView) view.findViewById(R.id.gf_recycler_view_new_goals);
            setupRecyclerView(newGoalsRecyclerView, newGoalsRes);
        }

    }

    public void setupRepository(IGoalsDataStore iGoalsDataStore) {
        this.iGoalsDataStore = iGoalsDataStore;
    }

    public void setBottomNavigationView(AHBottomNavigation bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }

    public void setupRecyclerView(RecyclerView recyclerView, final List<Integer> resources) {
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
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
                        audioPlayerAlertDialog.playTrack(filePath, mContext);
                    }
                }).playMediaData(resource);
            }
        }, resources);
        recyclerView.setAdapter(mediaArrayAdapter);
    }
}
