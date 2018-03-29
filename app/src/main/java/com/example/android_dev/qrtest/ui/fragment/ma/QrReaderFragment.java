package com.example.android_dev.qrtest.ui.fragment.ma;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.presenter.qr.QRFPresenter;
import com.example.android_dev.qrtest.ui.activity.SimpleAudioPlayer;
import com.example.android_dev.qrtest.ui.activity.SimpleVideoPlayer;
import com.example.android_dev.qrtest.ui.adapter.MediaArrayAdapter;
import com.example.android_dev.qrtest.util.GlobalNames;
import com.example.android_dev.qrtest.util.IQRFragment;
import com.example.android_dev.qrtest.util.NotificationWorker;
import com.google.zxing.Result;

import java.util.List;

import static android.Manifest.permission.CAMERA;

public class QrReaderFragment extends Fragment {
    private static final int REQUEST_CAMERA = 1;
    private static final String LOG_TAG = "QrReaderFragment";
    private CodeScanner mCodeScanner;

    //view
    private Context mContext;
    private QRFPresenter qrfPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.qr_reader_fragment, container, false);
        final Activity activity = getActivity();

        CodeScannerView scannerView = v.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.getText() == null) {
                            Toast.makeText(mContext, "Result Not Found", Toast.LENGTH_LONG).show();
                        } else {
                            //if qr contains data
                            String code = result.getText();
                            qrfPresenter.checkCode(code);
                        }
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        mContext = v.getContext();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (!checkPermission()) {
                requestPermission();
            }
        }
        qrfPresenter = new QRFPresenter(new IQRFragment() {
            @Override
            public void showAlertDialog(int modeScan, List<Integer> resIds, int modeShow) {
                final View view = LayoutInflater.from(mContext).inflate(R.layout.alert_qrscan, null);
                final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.aqr_recyclerView);
                final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.aqr_progress_bar);

                final MediaArrayAdapter storyArrayAdapter = new MediaArrayAdapter(new MediaArrayAdapter.OnItemStoryClickListener() {
                    @Override
                    public void onClick(AssetTypes resource) {
                        qrfPresenter.playMediaData(resource);
                    }
                }, resIds);
                if (modeScan == GlobalNames.QR_MODE_FIRST_SCAN) {
                    progressBar.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            new NotificationWorker(mContext).showNotification(getResources()
                                    .getString(R.string.notify_service_done));
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                            recyclerView.setAdapter(storyArrayAdapter);
                            storyArrayAdapter.notifyDataSetChanged();
                            notifiAboutGoal();
                        }
                    }, 3000);
                } else {
                    if (recyclerView != null) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                        recyclerView.setAdapter(storyArrayAdapter);
                    }

                }

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
            public void showMsg(String msg) {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void startAudioPlayerActivity(String filePath) {
                Intent intent = new Intent(mContext, SimpleAudioPlayer.class);
                intent.putExtra("path", filePath);
                startActivity(intent);
            }


        });
        return v;
    }

    public void notifiAboutGoal() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                new NotificationWorker(mContext).showNotification(getString(R.string.title_goals));
            }
        }, 1000);

    }


    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(mContext, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}
