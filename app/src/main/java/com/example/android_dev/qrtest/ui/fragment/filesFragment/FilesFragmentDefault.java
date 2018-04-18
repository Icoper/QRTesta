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
import com.example.android_dev.qrtest.db.IStoryRepository;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.presenter.generalHistory.GeneralHistoryFragmentPresenter;
import com.example.android_dev.qrtest.ui.activity.SimpleVideoPlayer;
import com.example.android_dev.qrtest.ui.adapter.mediaAdapter.MediaArrayAdapter;
import com.example.android_dev.qrtest.ui.fragment.IGeneralHistoryFragment;

import java.util.List;

/**
 * Created by user on 18-Apr-18.
 */

public class FilesFragmentDefault extends Fragment {
    private Context mContext;
    private RecyclerView recyclerView;
    private IStoryRepository iStoryRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_files, container, false);
        mContext = v.getContext();
        recyclerView = (RecyclerView) v.findViewById(R.id.ff_recycler_view);
        mContext = v.getContext();
        iStoryRepository = new InMemoryStoryRepository();
        setupRecyclerView();
        return v;
    }

    public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        List<Integer> resIds = iStoryRepository.getSelectedStory().getHistoryAssetTypesID();

        MediaArrayAdapter storyArrayAdapter = new MediaArrayAdapter(new MediaArrayAdapter.OnItemStoryClickListener() {
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
                        Intent intent = new Intent();
                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(filePath), "audio/*");
                        startActivity(intent);
                    }
                }).playMediaData(resource);
            }
        }, resIds);
        recyclerView.setAdapter(storyArrayAdapter);
    }
}
