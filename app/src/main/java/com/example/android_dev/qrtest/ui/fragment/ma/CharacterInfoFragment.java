package com.example.android_dev.qrtest.ui.fragment.ma;

import android.app.Fragment;
import android.content.Context;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class CharacterInfoFragment extends Fragment {

    private View view;
    private Context mContext;
    private TextView aboutText;
    private TextView actorName;
    private ImageView actorImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.character_info_fragment, container, false);
        mContext = view.getContext();
        initializeView();
        CharacterInfoPresenter characterInfoPresenter = new CharacterInfoPresenter(new ICharacterInfoFragment() {
            @Override
            public void showActorInfo(Actor actor) {
                actorName.setText(actor.getName());

                InputStream inputStream = view.getResources().openRawResource(actor.getAboutRes());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String about = "";
                try {
                    about = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                aboutText.setText(about);
                actorImg.setImageResource(actor.getImgRes());
            }
        });
        characterInfoPresenter.showActorInfo();
        return view;
    }

    private void initializeView() {
        actorName = (TextView) view.findViewById(R.id.cif_actor_name);
        aboutText = (TextView) view.findViewById(R.id.cif_about_text);
        actorImg = (ImageView) view.findViewById(R.id.cif_about_img);
    }
}
