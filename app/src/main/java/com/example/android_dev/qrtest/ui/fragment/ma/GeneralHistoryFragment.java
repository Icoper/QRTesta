package com.example.android_dev.qrtest.ui.fragment.ma;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.Story;
import com.example.android_dev.qrtest.ui.adapter.StoryArrayAdapter;
import com.example.android_dev.qrtest.util.GlobalNames;
import com.example.android_dev.qrtest.util.SimpleVideoPlayer;


public class GeneralHistoryFragment extends Fragment {
    private Context mContext;
    private RecyclerView recyclerView;
    private StoryArrayAdapter storyArrayAdapter;
    private Story ourStory;
    private InMemoryStoryRepository inMemoryStoryRepository;
    private MediaPlayer mediaPlayer;
    private boolean isSoundPlay = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.general_history_fragment, container, false);
        mContext = v.getContext();
        inMemoryStoryRepository = new InMemoryStoryRepository();
        ourStory = inMemoryStoryRepository.getSelectedStory();
        recyclerView = (RecyclerView) v.findViewById(R.id.gh_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        storyArrayAdapter = new StoryArrayAdapter(ourStory, new StoryArrayAdapter.OnItemStoryClickListener() {
            @Override
            public void onClick(String finalItemType, int res) {
                processData(finalItemType, res);
            }
        });
        recyclerView.setAdapter(storyArrayAdapter);
        return v;
    }

    private void processData(String type, int res) {
        if (type.equals(GlobalNames.AUDIO_RES)) {
            if (isSoundPlay) {
                Toast.makeText(mContext, "Stop", Toast.LENGTH_SHORT).show();
                isSoundPlay = false;
                mediaPlayer.stop();
            } else {
                Toast.makeText(mContext, "Play", Toast.LENGTH_SHORT).show();
                isSoundPlay = true;
                mediaPlayer = MediaPlayer.create(getActivity().getApplication().getApplicationContext(), res);
                mediaPlayer.start();
            }
        } else if (type.equals(GlobalNames.VIDEO_RES)) {
            Intent intent = new Intent(mContext, SimpleVideoPlayer.class);
            intent.putExtra("res", String.valueOf(res));
            mContext.startActivity(intent);
        }
    }
}
