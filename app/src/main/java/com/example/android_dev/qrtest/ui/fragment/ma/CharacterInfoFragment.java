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
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.json.AssertItems;
import com.example.android_dev.qrtest.presenter.character_info.CharacterInfoPresenter;
import com.example.android_dev.qrtest.ui.activity.SimpleAudioPlayer;
import com.example.android_dev.qrtest.ui.activity.SimpleVideoPlayer;
import com.example.android_dev.qrtest.ui.adapter.MediaArrayAdapter;
import com.example.android_dev.qrtest.util.ICharacterInfoFragment;

public class CharacterInfoFragment extends Fragment {
    private InMemoryStoryRepository inMemoryStoryRepository;
    private View view;
    private TextView actorName;
    private RecyclerView recyclerView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.character_info_fragment, container, false);
        mContext = view.getContext();
        inMemoryStoryRepository = new InMemoryStoryRepository();
        initializeView();
        actorName.setText(inMemoryStoryRepository.getSelectedRole().getName());
        MediaArrayAdapter mediaArrayAdapter = new MediaArrayAdapter(new MediaArrayAdapter.OnItemStoryClickListener() {
            @Override
            public void onClick(AssertItems.Resource resource) {
                new CharacterInfoPresenter(new ICharacterInfoFragment() {
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
        }, inMemoryStoryRepository.getRoleResId());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mediaArrayAdapter);
        return view;
    }

    private void initializeView() {
        actorName = (TextView) view.findViewById(R.id.cif_actor_name);
        recyclerView = (RecyclerView) view.findViewById(R.id.cif_recycler_view);
    }
}
