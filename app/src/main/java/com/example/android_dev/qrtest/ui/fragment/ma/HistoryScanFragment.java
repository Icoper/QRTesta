package com.example.android_dev.qrtest.ui.fragment.ma;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.IMemoryStoryRepository;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.model.JsonStory;
import com.example.android_dev.qrtest.model.QrInformation;
import com.example.android_dev.qrtest.presenter.historyscan.HistoryScanPresenter;
import com.example.android_dev.qrtest.ui.activity.SimpleAudioPlayer;
import com.example.android_dev.qrtest.ui.activity.SimpleVideoPlayer;
import com.example.android_dev.qrtest.ui.adapter.MediaArrayAdapter;
import com.example.android_dev.qrtest.util.GlobalNames;
import com.example.android_dev.qrtest.util.IHistoryScanFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HistoryScanFragment extends Fragment {
    private Context mContext;
    private GridView gridView;
    private HistoryScanPresenter historyScanPresenter;
    private QrInformation selectedQrInformation;
    private IMemoryStoryRepository iMemoryStoryRepository;
    private JsonStory jsonStory;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.history_scan_fragment, container, false);
        mContext = v.getContext();

        gridView = (GridView) v.findViewById(R.id.hs_grid_view);
        setupPresenter();
        return v;
    }

    private void setupPresenter() {
        historyScanPresenter = new HistoryScanPresenter(new IHistoryScanFragment() {
            @Override
            public void showGridView(final ArrayList<QrInformation> scannedQrInfo) {
                iMemoryStoryRepository = new InMemoryStoryRepository();
                jsonStory = iMemoryStoryRepository.getSelectedStory();
                ArrayAdapter<QrInformation> adapter = new ArrayAdapter<QrInformation>(
                        mContext, R.layout.scan_history_item, scannedQrInfo) {
                    @NonNull
                    @Override
                    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                        if (convertView == null) {
                            LayoutInflater inflater = (LayoutInflater) mContext
                                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            convertView = inflater.inflate(R.layout.scan_history_item, null);
                        }

                        ArrayList<Integer> integers = new ArrayList<>();
                        integers.add(scannedQrInfo.get(position).getPreviewImage());
                        List<AssetTypes> assetTypes = iMemoryStoryRepository.getResourceById(integers);

                        String imgPath = GlobalNames.ENVIRONMENT_STORE + jsonStory.getResFolderName() +
                                "/Resource1/" + assetTypes.get(0).getFileName();

                        ImageView appIcon = (ImageView) convertView.findViewById(R.id.shi_story_img);
                        TextView appLabel = (TextView) convertView.findViewById(R.id.shi_story_name);

                        appIcon.setImageBitmap(getBitMapByPath(imgPath));
                        appLabel.setText(jsonStory.getPreviewText() + " : " + scannedQrInfo.get(position).getCode());

                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                selectedQrInformation = scannedQrInfo.get(position);
                                historyScanPresenter.showStoryContent(selectedQrInformation);
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
                Intent intent = new Intent(mContext, SimpleAudioPlayer.class);
                intent.putExtra("path", filePath);
                startActivity(intent);
            }


        });
        historyScanPresenter.getScannedStoryList();
    }


    private Bitmap getBitMapByPath(String path) {
        Uri uri = Uri.parse(path);
        File imgFile = new File(uri.getPath());

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;
        }
        return null;
    }
}
