package com.example.android_dev.qrtest.ui.fragment;

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
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.goals.IGoalsDataStore;
import com.example.android_dev.qrtest.db.historyScan.IHistoryScanDataStore;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.presenter.qr.QRFPresenter;
import com.example.android_dev.qrtest.ui.activity.ImageViewer;
import com.example.android_dev.qrtest.ui.activity.SimpleVideoPlayer;
import com.example.android_dev.qrtest.ui.adapter.mediaAdapter.MediaArrayAdapter;
import com.example.android_dev.qrtest.util.GlobalNames;
import com.example.android_dev.qrtest.util.NotificationWorker;
import com.google.zxing.Result;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.List;

import static android.Manifest.permission.CAMERA;

public class QrReaderFragment extends Fragment {
    private static final int REQUEST_CAMERA = 1;
    private static final String LOG_TAG = "QrReaderFragment";
    private CodeScanner mCodeScanner;
    // Repository
    private IHistoryScanDataStore historyScanDataStore;
    private IGoalsDataStore goalsDataStore;

    private OnCloseQrReaderButtonClickListener onCloseQrReaderButtonClickListener;
    //view
    private Context mContext;
    private QRFPresenter qrfPresenter;
    private CodeScannerView scannerView;
    private AHBottomNavigation bottomNavigationView;
    private RelativeLayout closeQrReaderButton;
    private FrameLayout pnlFlash;


    public void setupOnCloseQrListener(OnCloseQrReaderButtonClickListener onCloseQrReaderButtonClickListener) {
        this.onCloseQrReaderButtonClickListener = onCloseQrReaderButtonClickListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_qr_reader, container, false);
        mContext = v.getContext();
        scannerView = v.findViewById(R.id.scanner_view);
        closeQrReaderButton = (RelativeLayout) v.findViewById(R.id.fqr_close);
        pnlFlash = (FrameLayout) v.findViewById(R.id.pnlFlash);

        closeQrReaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCloseQrReaderButtonClickListener.onClick();
            }
        });

        if (!checkPermission()) {
            requestPermission();
        } else {
            setupPresenter();
        }

        return v;
    }

    private void forceWrapContent(View v) {
        // Start with the provided view
        View current = v;
        // Travel up the tree until fail, modifying the LayoutParams
        do {
            // Get the parent
            ViewParent parent = current.getParent();

            // Check if the parent exists
            if (parent != null) {
                // Get the view
                try {
                    current = (View) parent;
                    ViewGroup.LayoutParams layoutParams = current.getLayoutParams();
                    if (layoutParams instanceof FrameLayout.LayoutParams) {
                        ((FrameLayout.LayoutParams) layoutParams).
                                gravity = Gravity.CENTER_HORIZONTAL;
                    } else if (layoutParams instanceof WindowManager.LayoutParams) {
                        ((WindowManager.LayoutParams) layoutParams).
                                gravity = Gravity.CENTER_HORIZONTAL;
                    }
                } catch (ClassCastException e) {
                    // This will happen when at the top view, it cannot be cast to a View
                    break;
                }

                // Modify the layout
                current.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            }
        } while (current.getParent() != null);

        // Request a layout to be re-done
        current.requestLayout();
    }

    private void hardCodeQR() {
        qrfPresenter.checkCode("1111");
    }

    private void setupPresenter() {
        setupScanner();
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
                try {
                    if (modeScan == GlobalNames.QR_MODE_FIRST_SCAN) {
                        progressBar.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                try {
                                    new NotificationWorker(mContext).showNotification(getResources()
                                            .getString(R.string.notify_service_done), GlobalNames.NOTIFICATION_LOAD_DATA_ID);
                                    progressBar.setVisibility(View.GONE);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                                    recyclerView.setAdapter(storyArrayAdapter);
                                    storyArrayAdapter.notifyDataSetChanged();
                                    notifyAboutGoal();
                                    forceWrapContent(view);
                                } catch (IllegalStateException e) {
                                    e.printStackTrace();
                                    return;
                                }
                            }
                        }, 3000);
                    } else {
                        if (recyclerView != null) {

                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                            recyclerView.setAdapter(storyArrayAdapter);
                        }

                    }
                    // if scanner get result, but user is go to another fragment
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    return;
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
                forceWrapContent(view);

            }

            @Override
            public void startVideoPlayerActivity(String filePath) {
                Intent intent = new Intent(mContext, SimpleVideoPlayer.class);
                intent.putExtra("res", filePath);
                startActivity(intent);
            }

            @Override
            public void showMsg(int msg) {
                Toast.makeText(mContext, getString(msg), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void startImageViewerActivity(String filepath) {
                Intent intent = new Intent(mContext, ImageViewer.class);
                intent.putExtra("path", filepath);
                startActivity(intent);
            }
        }, historyScanDataStore, goalsDataStore);

    }

    private void setupScanner() {
        final Activity activity = getActivity();
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.getText() == null) {
                            Toast.makeText(mContext, getString(R.string.qr_code_not_found), Toast.LENGTH_LONG).show();
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
        mCodeScanner.startPreview();
    }

    private void showAnimation() {
        pnlFlash.setVisibility(View.VISIBLE);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(pnlFlash, "translationX", 0, 90),
                ObjectAnimator.ofFloat(pnlFlash, "translationY", 0, 90),
                ObjectAnimator.ofFloat(pnlFlash, "scaleX", 1, 1.5f),
                ObjectAnimator.ofFloat(pnlFlash, "scaleY", 1, 0.5f),
                ObjectAnimator.ofFloat(pnlFlash, "alpha", 1, 0.25f, 1),
                ObjectAnimator.ofFloat(pnlFlash, "Y", 2500)
        );
        set.setDuration(1500).start();
    }

    public void notifyAboutGoal() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                new NotificationWorker(mContext).showNotification(getString(R.string.notify_new_goal),
                        GlobalNames.NOTIFICATION_NEW_GOAL_ID);
                showAnimation();
                int countNotification = goalsDataStore.getNewGoals().size();
                bottomNavigationView.setNotification(String.valueOf(countNotification), 4);
            }
        }, 2000);


    }

    public void setBottomNavigationView(AHBottomNavigation bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }

    public void setupRepository(IHistoryScanDataStore iHistoryScanDataStore, IGoalsDataStore iGoalsDataStore) {
        this.goalsDataStore = iGoalsDataStore;
        this.historyScanDataStore = iHistoryScanDataStore;
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(mContext, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, REQUEST_CAMERA);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;
        switch (requestCode) {
            case REQUEST_CAMERA:
                for (int res : grantResults) {
                    // if user granted all permissions.
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                // if user not granted permissions.
                allowed = false;
                break;
        }
        if (allowed) {
            //user granted all permissions we can perform our task.
            setupPresenter();
        } else {
            Toast.makeText(mContext, "Camera Permissions denied.\nCan't open QR code reader.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCodeScanner != null) {
            Log.d(LOG_TAG, "onResume | mCodeScanner  != null");
            mCodeScanner.startPreview();
        } else {
            Log.d(LOG_TAG, "onResume | mCodeScanner  == null");
        }
    }

    @Override
    public void onPause() {
        if (mCodeScanner != null) {
            Log.d(LOG_TAG, "onPause | mCodeScanner  != null");
            mCodeScanner.releaseResources();
        } else {
            Log.d(LOG_TAG, "onPause | mCodeScanner  == null");
        }
        super.onPause();
    }

    public interface OnCloseQrReaderButtonClickListener {
        void onClick();
    }
}
