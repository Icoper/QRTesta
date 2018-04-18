package com.example.android_dev.qrtest.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.ui.adapter.mediaAdapter.MediaArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 17-Apr-18.
 */

public class TasksFragment extends Fragment {
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tasks, container, false);
        mContext = v.getContext();
        InMemoryStoryRepository inMemoryStoryRepository = new InMemoryStoryRepository();
        IStory ourStory = inMemoryStoryRepository.getSelectedStory();

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.ft_recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        List<Integer> resIds = new ArrayList<>();
        resIds.add(3);

        MediaArrayAdapter storyArrayAdapter = new MediaArrayAdapter(new MediaArrayAdapter.OnItemStoryClickListener() {
            @Override
            public void onClick(AssetTypes resource) {

            }
        }, resIds);
        recyclerView.setAdapter(storyArrayAdapter);

        return v;
    }
}
