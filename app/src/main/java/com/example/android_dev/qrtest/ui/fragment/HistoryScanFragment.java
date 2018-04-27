package com.example.android_dev.qrtest.ui.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.IHistoryScanDataStore;
import com.example.android_dev.qrtest.db.IStoryRepository;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.model.QrInformation;
import com.example.android_dev.qrtest.presenter.historyScan.HistoryScanPresenter;
import com.example.android_dev.qrtest.ui.AudioPlayerAlertDialog;
import com.example.android_dev.qrtest.ui.IAudioPlayerAlertDialog;
import com.example.android_dev.qrtest.ui.activity.SimpleVideoPlayer;
import com.example.android_dev.qrtest.ui.adapter.StoryListRVAdapter;
import com.example.android_dev.qrtest.ui.adapter.mediaAdapter.MediaArrayAdapter;
import com.example.android_dev.qrtest.util.GlobalNames;

import java.util.ArrayList;
import java.util.List;

public class HistoryScanFragment extends Fragment {
    private Context mContext;
    private RecyclerView recyclerView;
    private HistoryScanPresenter historyScanPresenter;
    private QrInformation selectedQrInformation;
    private IStoryRepository iStoryRepository;
    private IStory jsonStory;
    private IHistoryScanDataStore iHistoryScanDataStore;
    private IAudioPlayerAlertDialog audioPlayerAlertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_scan, container, false);
        mContext = view.getContext();
        audioPlayerAlertDialog = new AudioPlayerAlertDialog();

        recyclerView = (RecyclerView) view.findViewById(R.id.fhs_recycler_view);
        setupPresenter();
        return view;
    }


    private void setupPresenter() {
        historyScanPresenter = new HistoryScanPresenter(new IHistoryScanFragment() {
            @Override
            public void showGridView(final ArrayList<QrInformation> scannedQrInfo) {
                iStoryRepository = new InMemoryStoryRepository();
                jsonStory = iStoryRepository.getSelectedStory();
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
                recyclerView.setLayoutManager(mLayoutManager);
                List<IStory> stories = new ArrayList<>();
                stories.add(jsonStory);

                StoryListRVAdapter storyListRVAdapter = new StoryListRVAdapter(stories,
                        new StoryListRVAdapter.OnItemClickListener() {
                            @Override
                            public void onClick(IStory iStory) {
                                selectedQrInformation = iStory.getQrInformationList().get(0);
                                historyScanPresenter.showStoryContent(selectedQrInformation);
                            }
                        });
                recyclerView.setAdapter(storyListRVAdapter);
            }

            @Override
            public void showMsg(String msg) {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showAlertDialog(List<Integer> resIds, int modeShow) {
                final View view = LayoutInflater.from(mContext).inflate(R.layout.alert_qrscan, null);
                final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.aqr_recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

                final MediaArrayAdapter storyArrayAdapter = new MediaArrayAdapter(new MediaArrayAdapter.OnItemStoryClickListener() {
                    @Override
                    public void onClick(AssetTypes resource) {
                        historyScanPresenter.playMediaData(resource);
                    }
                }, resIds);

                recyclerView.setAdapter(storyArrayAdapter);

                AlertDialog.Builder builder = new AlertDialog
                        .Builder(new ContextThemeWrapper(view.getContext(), R.style.Theme_AppCompat_Light_Dialog_Alert));
                builder.setView(view);

                builder.setCancelable(false).setNegativeButton(view.getContext().getString(R.string.ok_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                if (modeShow == GlobalNames.ALERT_MODE_SMALL_INFO) {
                    builder.setNeutralButton(view.getContext().getString(R.string.more_text),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    historyScanPresenter.changeAlertMode(
                                            selectedQrInformation,
                                            GlobalNames.ALERT_MODE_FULL_INFO
                                    );
                                }
                            });
                }
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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


        }, iHistoryScanDataStore);
        historyScanPresenter.getScannedStoryList();
    }


    public void setupRepository(IHistoryScanDataStore iHistoryScanDataStore) {
        this.iHistoryScanDataStore = iHistoryScanDataStore;
    }
}
