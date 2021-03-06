package com.example.android_dev.qrtest.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.model.Role;

public class RolesActivity extends AppCompatActivity {
    private InMemoryStoryRepository inMemoryStoryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actors);
        inMemoryStoryRepository = new InMemoryStoryRepository();
        IStory story = inMemoryStoryRepository.getSelectedStory();
        initializeLv(story);
    }

    public void initializeLv(final IStory story) {
        ListView lvMain = (ListView) findViewById(R.id.fal_list_view);
        String[] actorsArray = new String[story.getRoleList().size()];
        for (int i = 0; i < story.getRoleList().size(); i++) {
            actorsArray[i] = story.getRoleList().get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, actorsArray);
        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                inMemoryStoryRepository.setSelectedRole(story.getRoleList().get(position));
                showAlertDialog(story.getRoleList().get(position));
            }
        });
    }

    public void showAlertDialog(final Role role) {

        final View view = LayoutInflater.from(this).inflate(R.layout.alert_actor_validate, null);
        final EditText enterId = (EditText) view.findViewById(R.id.aav_enter_id_et);

        AlertDialog.Builder builder = new AlertDialog
                .Builder(new ContextThemeWrapper(view.getContext(), R.style.Theme_AppCompat_Light_Dialog_Alert));
        builder.setView(view);
        builder.setCancelable(false)
                .setNeutralButton(view.getContext().getString(R.string.ok_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (enterId.getText().toString().equals(role.getCode())) {
                            inMemoryStoryRepository.setSelectedRole(role);
                            showHistoryContent();
                        } else
                            Toast.makeText(view.getContext(), getString(R.string.wrong_id), Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton(view.getContext().getString(R.string.cancel_text), new DialogInterface.OnClickListener() {
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

}
