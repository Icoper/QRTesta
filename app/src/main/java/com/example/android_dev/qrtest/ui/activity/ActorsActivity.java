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
import com.example.android_dev.qrtest.model.Story;

public class ActorsActivity extends AppCompatActivity {
    private InMemoryStoryRepository inMemoryStoryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actors);
        inMemoryStoryRepository = new InMemoryStoryRepository();
        Intent intent = getIntent();
        Story story = intent.getParcelableExtra("story");
        initializeLv(story);
    }

    public void initializeLv(final Story story) {
        ListView lvMain = (ListView) findViewById(R.id.fal_list_view);
        String[] actorsArray = new String[story.getActors().size()];
        for (int i = 0; i < story.getActors().size(); i++) {
            actorsArray[i] = story.getActors().get(i).getName() +
                    " id : " +
                    story.getActors().get(i).getId();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, actorsArray);
        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);

//        final String[] finalActorsArray = actorsArray;
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int i = position;
//                String s = finalActorsArray[i];
//                String[] splitArray = s.split("");
//                int arraySize = splitArray.length;
//                String actorId = splitArray[arraySize - 1];
                showAlertDialog(story.getActors().get(position).getId());
            }
        });
    }

    public void showAlertDialog(final String actorId) {

        final View view = LayoutInflater.from(this).inflate(R.layout.alert_actor_validate, null);
        final EditText enterId = (EditText) view.findViewById(R.id.aav_enter_id_et);

        AlertDialog.Builder builder = new AlertDialog
                .Builder(new ContextThemeWrapper(view.getContext(), R.style.Theme_AppCompat_Light_Dialog_Alert));
        builder.setView(view);
        builder.setCancelable(false)
                .setNeutralButton(view.getContext().getString(R.string.ok_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (enterId.getText().toString().equals(actorId)) {
                            showHistoryContent(actorId);
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

    private void showHistoryContent(String actorId) {
        Intent intent = new Intent(ActorsActivity.this, MainActivity.class);
        intent.putExtra("id", actorId);
        startActivity(intent);
    }

}
