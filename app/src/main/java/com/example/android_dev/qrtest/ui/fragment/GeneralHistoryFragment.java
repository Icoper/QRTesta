package com.example.android_dev.qrtest.ui.fragment;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
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

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.presenter.generalHistory.GeneralHistoryFragmentPresenter;
import com.example.android_dev.qrtest.ui.activity.ImageViewer;
import com.example.android_dev.qrtest.ui.activity.SimpleVideoPlayer;
import com.example.android_dev.qrtest.ui.adapter.mediaAdapter.MediaArrayAdapter;

import java.util.List;

public class GeneralHistoryFragment extends Fragment {
    private Context mContext;
    private RecyclerView recyclerView;
    private List<Integer> resIds;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_general_history, container, false);
        mContext = v.getContext();
        InMemoryStoryRepository inMemoryStoryRepository = new InMemoryStoryRepository();
        IStory ourStory = inMemoryStoryRepository.getSelectedStory();

        recyclerView = (RecyclerView) v.findViewById(R.id.gh_media_recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        resIds = ourStory.getHistoryAssetTypesID();

        MediaArrayAdapter mediaArrayAdapter = new MediaArrayAdapter(new MediaArrayAdapter.OnItemStoryClickListener() {
            @Override
            public void onClick(AssetTypes resource) {
                new GeneralHistoryFragmentPresenter(new IGeneralHistoryFragment() {
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
                        try {
                            Intent intent = new Intent();
                            intent.setAction(android.content.Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.parse(filePath), "audio/*");
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void startImageViewerActivity(String filepath) {
                        Intent intent = new Intent(GeneralHistoryFragment.this.mContext, ImageViewer.class);
                        intent.putExtra("path", filepath);
                        startActivity(intent);
                    }
                }).playMediaData(resource);
            }
        }, resIds);
        recyclerView.setAdapter(mediaArrayAdapter);
        return v;
    }

}
