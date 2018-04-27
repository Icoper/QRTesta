package com.example.android_dev.qrtest.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.presenter.character.CharacterInfoPresenter;
import com.example.android_dev.qrtest.ui.AudioPlayerAlertDialog;
import com.example.android_dev.qrtest.ui.IAudioPlayerAlertDialog;
import com.example.android_dev.qrtest.ui.activity.SimpleVideoPlayer;
import com.example.android_dev.qrtest.ui.adapter.mediaAdapter.MediaArrayAdapter;
import com.example.android_dev.qrtest.util.GlobalNames;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoleInfoFragment extends Fragment {
    private InMemoryStoryRepository inMemoryStoryRepository;
    private View view;
    private RecyclerView recyclerView;
    private Context mContext;
    private CircleImageView roleImg;
    private TextView roleName;
    private IAudioPlayerAlertDialog audioPlayerAlertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_role_info, container, false);
        mContext = view.getContext();
        inMemoryStoryRepository = new InMemoryStoryRepository();
        audioPlayerAlertDialog = new AudioPlayerAlertDialog();
        initializeView();
        List<Integer> resIds = new ArrayList<>();
        resIds.addAll(inMemoryStoryRepository.getSelectedRole().getInformationAssertIDList());
        MediaArrayAdapter mediaArrayAdapter = new MediaArrayAdapter(new MediaArrayAdapter.OnItemStoryClickListener() {
            @Override
            public void onClick(AssetTypes resource) {
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
                        audioPlayerAlertDialog.playTrack(filePath, mContext);
                    }
                }).playMediaData(resource);
            }
        }, resIds);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mediaArrayAdapter);
        return view;
    }

    private void initializeView() {
        roleName = (TextView) view.findViewById(R.id.irl_role_name);
        roleImg = (CircleImageView) view.findViewById(R.id.irl_role_icon);
        recyclerView = (RecyclerView) view.findViewById(R.id.cif_recycler_view);

        String imgName = "";
        for (int id : inMemoryStoryRepository.getSelectedRole().getInformationAssertIDList()) {
            if (imgName.isEmpty()) {
                for (AssetTypes assetType : inMemoryStoryRepository.getSelectedStory().getAssetTypeList()) {
                    if (assetType.getId() == id) {
                        if (assetType.getFileType().equals(GlobalNames.IMG_RES)) {
                            imgName = assetType.getFileName();
                            break;
                        }
                    }

                }
            }

        }


        String imgPath = GlobalNames.ENVIRONMENT_STORE + inMemoryStoryRepository
                .getSelectedStory()
                .getResFolderName() + "/Resource1/" +
                imgName;
        roleName.setText(inMemoryStoryRepository.getSelectedRole().getName());
        roleImg.setImageBitmap(getBitMapByPath(imgPath));
    }


    private Bitmap getBitMapByPath(String path) {
        Uri uri = Uri.parse(path);
        File imgFile = new File(uri.getPath());

        if (imgFile.exists()) {
            return BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        return null;
    }
}
