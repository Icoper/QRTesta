package com.example.android_dev.qrtest.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.db.SavedDataStore;
import com.example.android_dev.qrtest.db.SingletonStoryData;
import com.example.android_dev.qrtest.db.goals.GoalsDataStore;
import com.example.android_dev.qrtest.db.goals.IGoalsDataStore;
import com.example.android_dev.qrtest.db.historyScan.HistoryScanDataStore;
import com.example.android_dev.qrtest.db.historyScan.IHistoryScanDataStore;
import com.example.android_dev.qrtest.model.HistoryScansQRInformationIDs;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.ui.fragment.GeneralHistoryFragment;
import com.example.android_dev.qrtest.ui.fragment.GoalsFragment;
import com.example.android_dev.qrtest.ui.fragment.HistoryScanFragment;
import com.example.android_dev.qrtest.ui.fragment.QrReaderFragment;
import com.example.android_dev.qrtest.ui.fragment.RoleInfoFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG = "MainActivity";

    private InMemoryStoryRepository inMemoryStoryRepository;
    private IHistoryScanDataStore iHistoryScanDataStore;
    private IGoalsDataStore iGoalsDataStore;
    private IStory selectedStory;
    // ui
    private Toolbar mActionBarToolbar;
    private boolean doubleBackToExitPressedOnce = false;
    private FloatingActionButton floatingActionButton;
    private AHBottomNavigation ahBottomNavigationView;
    private RelativeLayout progressLayout;

    private RelativeLayout loadContentLayout;
    private ConstraintLayout mainContentLayout;
    // fragment
    private QrReaderFragment mQrReaderFragment;
    private RoleInfoFragment mRoleInfoFragment;
    private GeneralHistoryFragment mGeneralHistoryFragment;
    private HistoryScanFragment mHistoryScanFragment;
    private GoalsFragment mGoalsFragment;
    private FragmentTransaction mFragmentTransaction;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                logoutUser();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        inMemoryStoryRepository = new InMemoryStoryRepository();
        iGoalsDataStore = new GoalsDataStore();
        iHistoryScanDataStore = new HistoryScanDataStore();
        selectedStory = inMemoryStoryRepository.getSelectedStory();
        progressLayout = (RelativeLayout) findViewById(R.id.ma_progress_bar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.ma_fab);
        loadContentLayout = (RelativeLayout) findViewById(R.id.ma_load_content_layout);
        mainContentLayout = (ConstraintLayout) findViewById(R.id.ma_main_content_layout);
        loadContentLayout.setVisibility(View.GONE);
        floatingActionButton.setOnClickListener(this);
        floatingActionButton.setBackgroundResource(R.drawable.ic_tasks);
        mActionBarToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mActionBarToolbar);
        setTitle(selectedStory.getPreviewText());
        mActionBarToolbar.setElevation(8);
        setupBottomNavigationView();

        initGeneralHistoryFragment();
        getFragmentTransaction().add(R.id.ma_fragment_container, mGeneralHistoryFragment).commit();

    }

    private void setupBottomNavigationView() {
        ahBottomNavigationView = (AHBottomNavigation) findViewById(R.id.ma_bottom_navigation);
        AHBottomNavigationItem itemGeneralHistory = new AHBottomNavigationItem(R.string.title_general_history, R.drawable.ic_history, R.color.colorGrey);
        AHBottomNavigationItem itemActorInfo = new AHBottomNavigationItem(R.string.title_characters_info, R.drawable.ic_character, R.color.colorGrey);
        AHBottomNavigationItem itemHistoryScan = new AHBottomNavigationItem(R.string.title_history_scan, R.drawable.ic_hist_scan, R.color.colorGrey);
        AHBottomNavigationItem itemQrReader = new AHBottomNavigationItem(R.string.title_qr_reader, R.drawable.ic_qr, R.color.colorGrey);
        AHBottomNavigationItem itemGoals = new AHBottomNavigationItem(R.string.title_goals, R.drawable.ic_tasks, R.color.colorGrey);

        ahBottomNavigationView.addItem(itemGeneralHistory);
        ahBottomNavigationView.addItem(itemActorInfo);
        ahBottomNavigationView.addItem(itemQrReader);
        ahBottomNavigationView.addItem(itemHistoryScan);
        ahBottomNavigationView.addItem(itemGoals);

        ahBottomNavigationView.setNotificationAnimationDuration(700);
        ahBottomNavigationView.setColored(true);
        ahBottomNavigationView.setForceTint(true);
        ahBottomNavigationView.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        ahBottomNavigationView.setInactiveColor(getResources().getColor(R.color.colorGrayLight));
        ahBottomNavigationView.setAccentColor(getResources().getColor(R.color.colorBlue));
        ahBottomNavigationView.setNotificationTextColor(getResources().getColor(R.color.colorWhite));
        ahBottomNavigationView.setNotificationBackgroundColor(getResources().getColor(R.color.colorNotification));
        if (iGoalsDataStore.getNewGoals().size() > 0) {
            ahBottomNavigationView.setNotification(iGoalsDataStore.getNewGoals().size(), 4);
        }
        ahBottomNavigationView.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        if (!wasSelected) {
                            initGeneralHistoryFragment();
                            fragment = mGeneralHistoryFragment;
                            showFab(false);
                        }
                        break;
                    case 1:
                        if (!wasSelected) {
                            initRoleInfoFragment();
                            fragment = mRoleInfoFragment;
                            showFab(false);
                        }
                        break;
                    case 2:
                        if (!wasSelected) {
                            initQrReaderFragment();
                            fragment = mQrReaderFragment;
                            showFab(false);
                        }
                        break;
                    case 3:
                        if (!wasSelected) {
                            initHistoryScanFragment();
                            fragment = mHistoryScanFragment;
                            showFab(false);
                        }
                        break;
                    case 4:
                        if (!wasSelected) {
                            initGoalsFragment();
                            fragment = mGoalsFragment;
                            showFab(false);
                        }
                        break;
                }
                if (fragment != null) {
                    getFragmentTransaction().replace(R.id.ma_fragment_container, fragment).commit();
                }
                return true;
            }
        });
    }

    private void initQrReaderFragment() {
        if (mQrReaderFragment == null) {
            mQrReaderFragment = new QrReaderFragment();
            mQrReaderFragment.setupRepository(iHistoryScanDataStore, iGoalsDataStore);
            mQrReaderFragment.setBottomNavigationView(ahBottomNavigationView);
            mQrReaderFragment.setupOnCloseQrListener(new QrReaderFragment.OnCloseQrReaderButtonClickListener() {
                @Override
                public void onClick() {
                    getFragmentTransaction().replace(R.id.ma_fragment_container, mGeneralHistoryFragment).commit();
                    ahBottomNavigationView.setCurrentItem(0);
                }
            });
        }
    }

    private void initRoleInfoFragment() {
        if (mRoleInfoFragment == null) {
            mRoleInfoFragment = new RoleInfoFragment();
        }
    }

    private void initGeneralHistoryFragment() {
        if (mGeneralHistoryFragment == null) {
            mGeneralHistoryFragment = new GeneralHistoryFragment();
        }
    }

    private void initHistoryScanFragment() {
        if (mHistoryScanFragment == null) {
            mHistoryScanFragment = new HistoryScanFragment();
            mHistoryScanFragment.setupRepository(iHistoryScanDataStore);
        }
    }

    private void initGoalsFragment() {
        if (mGoalsFragment == null) {
            mGoalsFragment = new GoalsFragment();
            mGoalsFragment.setupRepository(iGoalsDataStore);
            mGoalsFragment.setBottomNavigationView(ahBottomNavigationView);
        }
    }


    private FragmentTransaction getFragmentTransaction() {
        FragmentManager fragmentManager = getFragmentManager();
        mFragmentTransaction = fragmentManager.beginTransaction();

        return mFragmentTransaction;
    }

    private void updateJson() {
        // Need to clean scan history from all stories
        for (HistoryScansQRInformationIDs historyScansQRInfo : selectedStory.getHistoryScansQRInformationsIDList()) {
            historyScansQRInfo.setShortInfo(true);
        }
    }


    private void showExitDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.alert_user_info, null);
        TextView textView = (TextView) view.findViewById(R.id.aui_info_text);
        textView.setText(getText(R.string.action_logout_info));
        AlertDialog.Builder builder = new AlertDialog
                .Builder(new ContextThemeWrapper(view.getContext(), R.style.Theme_AppCompat_Light_Dialog_Alert));
        builder.setView(view);

        builder.setCancelable(false)
                .setNeutralButton(R.string.ok_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SavedDataStore savedDataStore = SingletonStoryData.getInstance().getSavedDataStore();
                        savedDataStore.cleanUserData();
                        updateJson();
                        closeApp();
                    }
                }).setNegativeButton(R.string.cancel_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    private void showFab(boolean needToShow) {
        if (needToShow) {
            Animation anim = AnimationUtils.loadAnimation(floatingActionButton.getContext(), R.anim.scale_up);
            anim.setDuration(500);
            floatingActionButton.startAnimation(anim);
            floatingActionButton.setVisibility(View.VISIBLE);
        } else {
            if (floatingActionButton.getVisibility() == View.VISIBLE) {
                Animation anim = AnimationUtils.loadAnimation(floatingActionButton.getContext(), R.anim.scale_down);
                anim.setDuration(500);
                floatingActionButton.startAnimation(anim);
                floatingActionButton.setVisibility(View.GONE);
            }

        }
    }

    private void closeApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        int id = android.os.Process.myPid();
        android.os.Process.killProcess(id);
    }

    private void logoutUser() {
        showExitDialog();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            closeApp();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadContentLayout.setVisibility(View.GONE);
        mainContentLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ma_fab:
                // updateNewGoals data
                break;
        }
    }


}
