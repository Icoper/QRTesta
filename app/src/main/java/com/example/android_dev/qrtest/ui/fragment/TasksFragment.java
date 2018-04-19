package com.example.android_dev.qrtest.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.IGoalsDataStore;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.presenter.goals.GoalsFragmentPresenter;
import com.example.android_dev.qrtest.ui.activity.SimpleVideoPlayer;
import com.example.android_dev.qrtest.ui.adapter.mediaAdapter.MediaArrayAdapter;

/**
 * Created by user on 17-Apr-18.
 */

public class TasksFragment extends Fragment {
    private Context mContext;
    private RecyclerView recyclerView;
    private AHBottomNavigation bottomNavigationView;
    private View view;
    private IGoalsDataStore iGoalsDataStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tasks, container, false);
        mContext = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.ft_recycler_view);
        setupRecyclerView();
        bottomNavigationView.setNotification("", 2);

        return view;
    }

    public void setupRepository(IGoalsDataStore iGoalsDataStore) {
        this.iGoalsDataStore = iGoalsDataStore;
    }

    public void setBottomNavigationView(AHBottomNavigation bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }

    public void setupRecyclerView() {
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
                        Intent intent = new Intent();
                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(filePath), "audio/*");
                        startActivity(intent);
                    }
                }, iGoalsDataStore).playMediaData(resource);
            }
        }, iGoalsDataStore.getAll());
        recyclerView.setAdapter(mediaArrayAdapter);
    }
}
