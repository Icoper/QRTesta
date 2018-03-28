package com.example.android_dev.qrtest.ui.fragment.ma;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.IMemoryStoryRepository;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.json.AssertItems;
import com.example.android_dev.qrtest.model.json.JsonStory;
import com.example.android_dev.qrtest.presenter.historyscan.HistoryScanPresenter;
import com.example.android_dev.qrtest.presenter.qr.QRFPresenter;
import com.example.android_dev.qrtest.ui.activity.SimpleAudioPlayer;
import com.example.android_dev.qrtest.ui.activity.SimpleVideoPlayer;
import com.example.android_dev.qrtest.ui.adapter.MediaArrayAdapter;
import com.example.android_dev.qrtest.util.GlobalNames;
import com.example.android_dev.qrtest.util.IHistoryScanFragment;
import com.example.android_dev.qrtest.util.IQRFragment;
import com.example.android_dev.qrtest.util.NotificationWorker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HistoryScanFragment extends Fragment {
    private Context mContext;
    private GridView gridView;
    private HistoryScanPresenter historyScanPresenter;
    private QRFPresenter qrfPresenter;
    public IMemoryStoryRepository iMemoryStoryRepository;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.history_scan_fragment, container, false);
        mContext = v.getContext();
        gridView = (GridView) v.findViewById(R.id.hs_grid_view);
        setupPresenter();
        return v;
    }

    private void setupPresenter() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        historyScanPresenter = new HistoryScanPresenter(new IHistoryScanFragment() {
            @Override
            public void showGridView(final ArrayList<JsonStory> scannedStoryList) {
                ArrayAdapter<JsonStory> adapter = new ArrayAdapter<JsonStory>(mContext, R.layout.scan_history_item, scannedStoryList) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                        if (convertView == null) {
                            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            convertView = inflater.inflate(R.layout.scan_history_item, null);
                        }

                        ImageView appIcon = (ImageView) convertView.findViewById(R.id.shi_story_img);
                        appIcon.setImageBitmap(getBitMapByPath(
                                GlobalNames.ENVIRONMENT_STORE +
                                        scannedStoryList.get(position).getQrInformations().get(0).getCode() +
                                        "/" +
                                        scannedStoryList.get(position).getPreviewImg()
                        ));

                        TextView appLabel = (TextView) convertView.findViewById(R.id.shi_story_name);
                        appLabel.setText(scannedStoryList.get(position).getName());

                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(mContext, "Tap", Toast.LENGTH_SHORT).show();
                                showStoryDialog();
                            }
                        });

                        return convertView;
                    }
                };
                gridView.setAdapter(adapter);
            }

            @Override
            public void showMsg(String msg) {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        }, sp);
        historyScanPresenter.getScannedStoryList();
    }

    private void showStoryDialog() {
        iMemoryStoryRepository = new InMemoryStoryRepository();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        qrfPresenter = new QRFPresenter(sp, new IQRFragment() {
            @Override
            public void showAlertDialog(int modeScan, String storyResId, int modeShow) {
                final View view = LayoutInflater.from(mContext).inflate(R.layout.alert_qrscan, null);
                final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.aqr_recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.aqr_progress_bar);
                List<String> resIds = new ArrayList<>();
                resIds.add(storyResId);
                final MediaArrayAdapter storyArrayAdapter = new MediaArrayAdapter(new MediaArrayAdapter.OnItemStoryClickListener() {
                    @Override
                    public void onClick(AssertItems.Resource resource) {
                        qrfPresenter.playMediaData(resource);
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
                                    qrfPresenter.changeAlertMode(
                                            GlobalNames.QR_MODE_SIMPLE_SCAN,
                                            GlobalNames.ALERT_MODE_FULL_INFO,
                                            iMemoryStoryRepository.getSelectedStory()
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
            public void showMsg(String msg) {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void startAudioPlayerActivity(String filePath) {
                Intent intent = new Intent(mContext, SimpleAudioPlayer.class);
                intent.putExtra("path", filePath);
                startActivity(intent);
            }

            @Override
            public void sendNotificationMsg(final String msg) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        new NotificationWorker(mContext).showNotification(msg);
                    }
                }, 5000);

            }
        });
    }
    private Bitmap getBitMapByPath(String path) {
        File imgFile = new File(path);

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;
        }
        return null;
    }
}
