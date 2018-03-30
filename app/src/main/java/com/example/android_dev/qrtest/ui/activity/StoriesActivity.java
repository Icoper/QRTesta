package com.example.android_dev.qrtest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.model.JsonStory;
import com.example.android_dev.qrtest.ui.adapter.StoryListArrayAdapter;

import java.util.ArrayList;
import java.util.List;

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
        List<IStory> stories = new ArrayList<>();
        stories.addAll(storyRepository.getStoriesList());

        StoryListArrayAdapter storyListArrayAdapter = new StoryListArrayAdapter(stories,
                new StoryListArrayAdapter.OnItemClickListener() {
                    @Override
                    public void onClick() {
                        Intent intent = new Intent(getApplicationContext(), RolesActivity.class);
                        startActivity(intent);
                    }
                });
        storiesRv.setAdapter(storyListArrayAdapter);
    }


}

