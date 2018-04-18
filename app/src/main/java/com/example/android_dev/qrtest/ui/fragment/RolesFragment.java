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
import com.example.android_dev.qrtest.db.IStoryRepository;
import com.example.android_dev.qrtest.model.Role;
import com.example.android_dev.qrtest.ui.adapter.RolesRVAdapter;

/**
 * Created by user on 17-Apr-18.
 */

public class RolesFragment extends Fragment {
    private Context mContext;
    private IStoryRepository iStoryRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_roles, container, false);
        mContext = v.getContext();
        RecyclerView rolesRV = (RecyclerView) v.findViewById(R.id.fr_recycler_view);
        rolesRV.setLayoutManager(new LinearLayoutManager(mContext));
        RolesRVAdapter rolesRVAdapter = new RolesRVAdapter(iStoryRepository.getSelectedStory(), new RolesRVAdapter.OnItemClickListener() {
            @Override
            public void onClick(Role role) {
                // do nothing
            }
        }, iStoryRepository);

        rolesRV.setAdapter(rolesRVAdapter);
        return v;
    }

    public void setiStoryRepository(IStoryRepository iStoryRepository) {
        this.iStoryRepository = iStoryRepository;
    }
}
