package com.example.android_dev.qrtest.ui.fragment.ma;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.Story;
import com.example.android_dev.qrtest.model.StoryMediaPath;
import com.example.android_dev.qrtest.presenter.qr.QRFPresenter;
import com.example.android_dev.qrtest.ui.adapter.StoryArrayAdapter;
import com.example.android_dev.qrtest.util.GlobalNames;
import com.example.android_dev.qrtest.util.IQRFragment;
import com.example.android_dev.qrtest.util.NotificationWorker;
import com.example.android_dev.qrtest.util.SimpleVideoPlayer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import static android.Manifest.permission.CAMERA;

public class QrReaderFragment extends Fragment {
    private static final int REQUEST_CAMERA = 1;
    private static final String LOG_TAG = "QrReaderFragment";
    //view
    private ImageView openQrBtn;
    private Context mContext;
    private InMemoryStoryRepository inMemoryStoryRepository;
    //qr code scanner object
    private IntentIntegrator qrScan;
    private QRFPresenter qrfPresenter;
    private MediaPlayer mediaPlayer;
    private boolean isSoundPlay = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.qr_reader_fragment, container, false);

        qrScan = IntentIntegrator.forFragment(this);
        mContext = v.getContext();
        inMemoryStoryRepository = new InMemoryStoryRepository();
        openQrBtn = (ImageView) v.findViewById(R.id.qrf_qr_image_view);
        openQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    qrScan.initiateScan();
                } else {
                    requestPermission();
                }
            }
        });

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                qrScan.initiateScan();
            } else {
                requestPermission();
            }
        }
        qrfPresenter = new QRFPresenter(mContext, new IQRFragment() {
            @Override
            public void showAlertDialog(int modeScan, final int modeShow) {
                final View view = LayoutInflater.from(mContext).inflate(R.layout.alert_qrscan, null);
                final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.aqr_recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.aqr_progress_bar);
                Story sendedStory = new Story();
                if (modeShow == GlobalNames.ALERT_MODE_SMALL_INFO) {

                    Story fullStory = inMemoryStoryRepository.getSelectedStory();
                    StoryMediaPath storyMediaPath = new StoryMediaPath();
                    storyMediaPath.setAudio(new ArrayList<Integer>());
                    storyMediaPath.setVideo(new ArrayList<Integer>());
                    storyMediaPath.setImages(fullStory.getMedia().getImages());
                    sendedStory.setMedia(storyMediaPath);
                    sendedStory.setAbout(fullStory.getAbout());

                } else if (modeShow == GlobalNames.ALERT_MODE_FULL_INFO) {
                    sendedStory = inMemoryStoryRepository.getSelectedStory();
                }

                final StoryArrayAdapter storyArrayAdapter = new StoryArrayAdapter(sendedStory, new StoryArrayAdapter.OnItemStoryClickListener() {
                    @Override
                    public void onClick(String finalItemType, int res) {
                        processData(finalItemType, res);
                    }
                });
                if (modeScan == GlobalNames.QR_MODE_FIRST_SCAN) {
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            new NotificationWorker(mContext).showNotification();
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setAdapter(storyArrayAdapter);
                            storyArrayAdapter.notifyDataSetChanged();
                        }
                    }, 3000);

                } else {
                    recyclerView.setAdapter(storyArrayAdapter);
                    storyArrayAdapter.notifyDataSetChanged();
                }


                AlertDialog.Builder builder = new AlertDialog
                        .Builder(new ContextThemeWrapper(view.getContext(), R.style.Theme_AppCompat_Light_Dialog_Alert));
                builder.setView(view);
                builder.setCancelable(false)
                        .setNeutralButton(view.getContext().getString(R.string.more_text), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                qrfPresenter.changeAlertMode(GlobalNames.QR_MODE_SIMPLE_SCAN, GlobalNames.ALERT_MODE_FULL_INFO);
                            }
                        }).setNegativeButton(view.getContext().getString(R.string.cancel_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

            @Override
            public void showMsg(String msg) {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    private void processData(String type, int res) {
        if (type.equals(GlobalNames.AUDIO_RES)) {
            if (isSoundPlay) {
                Toast.makeText(mContext, "Stop", Toast.LENGTH_SHORT).show();
                isSoundPlay = false;
                mediaPlayer.stop();
            } else {
                Toast.makeText(mContext, "Play", Toast.LENGTH_SHORT).show();
                isSoundPlay = true;
                mediaPlayer = MediaPlayer.create(getActivity().getApplication().getApplicationContext(), res);
                mediaPlayer.start();
            }
        } else if (type.equals(GlobalNames.VIDEO_RES)) {
            Intent intent = new Intent(mContext, SimpleVideoPlayer.class);
            intent.putExtra("res", String.valueOf(res));
            mContext.startActivity(intent);
        }
    }

    //Getting the scan results
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(LOG_TAG, "onActivityResult is called");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qr code has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(mContext, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                String code = result.getContents();
                qrfPresenter.checkCode(code);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(mContext, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, REQUEST_CAMERA);
        if (checkPermission()) {
            qrScan.initiateScan();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(mContext, "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mContext, "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(mContext)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
