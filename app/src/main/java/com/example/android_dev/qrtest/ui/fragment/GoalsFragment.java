package com.example.android_dev.qrtest.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.ui.adapter.mediaAdapter.MediaArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class GoalsFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goals, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.gf_recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        List<Integer> resIds = new ArrayList<>();
        resIds.add(3);

        MediaArrayAdapter storyArrayAdapter = new MediaArrayAdapter(new MediaArrayAdapter.OnItemStoryClickListener() {
            @Override
            public void onClick(AssetTypes resource) {

            }
        }, resIds);
        recyclerView.setAdapter(storyArrayAdapter);

        return view;
    }


}
