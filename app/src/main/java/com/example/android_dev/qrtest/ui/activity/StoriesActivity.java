package com.example.android_dev.qrtest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.Story;
import com.example.android_dev.qrtest.ui.adapter.StoryListArrayAdapter;

import java.util.ArrayList;

public class StoriesActivity extends AppCompatActivity {

    private RecyclerView storiesRv;
    private InMemoryStoryRepository storyRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);
        storyRepository = new InMemoryStoryRepository();
        // setup all view elements
        setupUI();
    }

    private void setupUI() {
        storiesRv = (RecyclerView) findViewById(R.id.as_recycler_view);
        storiesRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ArrayList<Story> stories = storyRepository.getStoriesList();

        StoryListArrayAdapter storyListArrayAdapter = new StoryListArrayAdapter(stories,
                new StoryListArrayAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Story story) {
                        Intent intent = new Intent(getApplicationContext(), ActorsActivity.class);
                        startActivity(intent);
                    }
                });
        storiesRv.setAdapter(storyListArrayAdapter);
    }


}

