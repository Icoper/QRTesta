package com.example.android_dev.qrtest.ui.fragment.ma;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
import com.example.android_dev.qrtest.model.JsonStory;
import com.example.android_dev.qrtest.presenter.general_history.GeneralHistoryFragmentPresenter;
import com.example.android_dev.qrtest.ui.activity.SimpleAudioPlayer;
import com.example.android_dev.qrtest.ui.activity.SimpleVideoPlayer;
import com.example.android_dev.qrtest.ui.adapter.MediaArrayAdapter;
import com.example.android_dev.qrtest.util.IGeneralHistoryFragment;

import java.util.List;


public class GeneralHistoryFragment extends Fragment {
    private static final String GENERAL_HISTORY_RES_ID = "4";
    private Context mContext;
    private RecyclerView recyclerView;
    private MediaArrayAdapter storyArrayAdapter;
    private JsonStory ourStory;
    private InMemoryStoryRepository inMemoryStoryRepository;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.general_history_fragment, container, false);
        mContext = v.getContext();
        inMemoryStoryRepository = new InMemoryStoryRepository();
        ourStory = inMemoryStoryRepository.getSelectedStory();
        recyclerView = (RecyclerView) v.findViewById(R.id.gh_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        List<Integer> resIds = ourStory.getHistoryAssetTypesID();

        storyArrayAdapter = new MediaArrayAdapter(new MediaArrayAdapter.OnItemStoryClickListener() {
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
                        Intent intent = new Intent(mContext, SimpleAudioPlayer.class);
                        intent.putExtra("path", filePath);
                        startActivity(intent);
                    }
                }).playMediaData(resource);
            }
        }, resIds);
        recyclerView.setAdapter(storyArrayAdapter);
        return v;
    }
}
