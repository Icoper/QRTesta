package com.example.android_dev.qrtest.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.ui.adapter.StoryListRVAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class StoriesActivity extends AppCompatActivity {
    private static final String LOG_TAG = "StoriesActivity";
    private static final int REQUEST_STORAGE = 1;

    private RecyclerView storiesRv;
    private InMemoryStoryRepository storyRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate");
        setContentView(R.layout.activity_stories);
        storyRepository = new InMemoryStoryRepository();
        setTitle(R.string.title_scripts);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            if (!checkPermission()) {
                requestPermission();
            } else {
                // setup all view elements
                if (itsFirstLaunch()) {
                    setupUI();
                } else {
                    goToStory();
                }

            }
        }

    }

    private void goToStory() {
        Intent intent = new Intent(StoriesActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private boolean itsFirstLaunch() {
        IStory iStory = storyRepository.getSelectedStory();
        if (iStory.getResFolderName() == null) {
            return true;
        }
        return false;
    }

    private void setupUI() {
        storiesRv = (RecyclerView) findViewById(R.id.as_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        storiesRv.setLayoutManager(mLayoutManager);
        List<IStory> stories = new ArrayList<>();
        stories.addAll(storyRepository.getStoriesList());

        StoryListRVAdapter storyListRVAdapter = new StoryListRVAdapter(stories,
                new StoryListRVAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(IStory iStory) {
                        Intent intent = new Intent(getApplicationContext(), RolesActivity.class);
                        intent.putExtra("story", (Serializable) iStory);
                        startActivity(intent);
                    }
                });
        storiesRv.setAdapter(storyListRVAdapter);
    }


    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode) {
            case REQUEST_STORAGE:
                for (int res : grantResults) {
                    // if user granted all permissions.
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                // if user not granted permissions.
                allowed = false;
                break;
        }

        if (allowed) {
            //user granted all permissions we can perform our task.
            setupUI();
        } else {
            Toast.makeText(this, "Storage Permissions denied.\nApplication can't run.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, " onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }
}

