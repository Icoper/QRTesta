package com.example.android_dev.qrtest.ui.fragment.ma;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.model.Actor;
import com.example.android_dev.qrtest.presenter.character_info.CharacterInfoPresenter;
import com.example.android_dev.qrtest.util.ICharacterInfoFragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class CharacterInfoFragment extends Fragment {

    private View view;
    private TextView aboutText;
    private TextView actorName;
    private ImageView actorImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.character_info_fragment, container, false);
        initializeView();
        CharacterInfoPresenter characterInfoPresenter = new CharacterInfoPresenter(new ICharacterInfoFragment() {
            @Override
            public void showActorInfo(Actor actor) {
                actorName.setText(actor.getName());

                String about = "";
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(actor.getAboutRes()));
                    about = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                aboutText.setText(about);
                actorImg.setImageBitmap(getBitMapByPath(actor.getImgRes()));
            }
        });
        characterInfoPresenter.showActorInfo();
        return view;
    }

    private Bitmap getBitMapByPath(String path) {
        File imgFile = new File(path);

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;
        }
        return null;
    }

    private void initializeView() {
        actorName = (TextView) view.findViewById(R.id.cif_actor_name);
        aboutText = (TextView) view.findViewById(R.id.cif_about_text);
        actorImg = (ImageView) view.findViewById(R.id.cif_about_img);
    }
}
