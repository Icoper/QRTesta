package com.example.android_dev.qrtest.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.model.Role;
import com.example.android_dev.qrtest.ui.adapter.RolesRVAdapter;

public class RolesActivity extends AppCompatActivity {
    private InMemoryStoryRepository inMemoryStoryRepository;
    private final static String LOG_TAG = "RolesActivity";
    private IStory story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate");
        setContentView(R.layout.activity_actors);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setTitle(R.string.character_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.aa_my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        inMemoryStoryRepository = new InMemoryStoryRepository();
        Intent intent = getIntent();
        story = (IStory) intent.getSerializableExtra("story");
        initializeRv(story);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void initializeRv(final IStory story) {
        RecyclerView rolesRV = (RecyclerView) findViewById(R.id.aa_recycler_view);
        rolesRV.setLayoutManager(new LinearLayoutManager(this));
        RolesRVAdapter rolesRVAdapter = new RolesRVAdapter(story, new RolesRVAdapter.OnItemClickListener() {
            @Override
            public void onClick(Role role) {
                showAlertDialog(role);
            }
        }, inMemoryStoryRepository);

        rolesRV.setAdapter(rolesRVAdapter);

    }

    public void showAlertDialog(final Role role) {
        final View view = LayoutInflater.from(this).inflate(R.layout.alert_actor_validate, null);
        final EditText enterId = (EditText) view.findViewById(R.id.aav_enter_id_et);

        AlertDialog.Builder builder = new AlertDialog
                .Builder(new ContextThemeWrapper(view.getContext(), R.style.MyAlertDialogTheme));
        builder.setView(view);
        builder.setCancelable(false)
                .setNegativeButton(view.getContext().getString(R.string.ok_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (enterId.getText().toString().equals(role.getCode())) {
                            inMemoryStoryRepository.setSelectedRole(role);
                            inMemoryStoryRepository.setSelectedStory(story);
                            showHistoryContent();
                        } else
                            Toast.makeText(view.getContext(), getString(R.string.wrong_id), Toast.LENGTH_SHORT).show();
                    }
                }).setNeutralButton(view.getContext().getString(R.string.cancel_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle(R.string.validate_actor);
        alertDialog.show();

    }

    private void showHistoryContent() {
        Intent intent = new Intent(RolesActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
