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
import com.example.android_dev.qrtest.db.GoalsDataStore;
import com.example.android_dev.qrtest.db.HistoryScanDataStore;
import com.example.android_dev.qrtest.db.IGoalsDataStore;
import com.example.android_dev.qrtest.db.IHistoryScanDataStore;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.db.SavedDataStore;
import com.example.android_dev.qrtest.db.SingletonStoryData;
import com.example.android_dev.qrtest.db.tempData.ITempDataRepository;
import com.example.android_dev.qrtest.db.tempData.TempDataRepository;
import com.example.android_dev.qrtest.model.AppMenuItem;
import com.example.android_dev.qrtest.model.HistoryScansQRInformationIDs;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.ui.fragment.AppMenuFragment;
import com.example.android_dev.qrtest.ui.fragment.GeneralHistoryFragment;
import com.example.android_dev.qrtest.ui.fragment.GoalsFragment;
import com.example.android_dev.qrtest.ui.fragment.HistoryScanFragment;
import com.example.android_dev.qrtest.ui.fragment.LocationFragment;
import com.example.android_dev.qrtest.ui.fragment.QrReaderFragment;
import com.example.android_dev.qrtest.ui.fragment.RoleInfoFragment;
import com.example.android_dev.qrtest.ui.fragment.RolesFragment;
import com.example.android_dev.qrtest.ui.fragment.TasksFragment;
import com.example.android_dev.qrtest.ui.fragment.filesFragment.FilesFragmentFound;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG = "MainActivity";

    private InMemoryStoryRepository inMemoryStoryRepository;
    private IHistoryScanDataStore iHistoryScanDataStore;
    private IGoalsDataStore iGoalsDataStore;
    private IStory selectedStory;
    private ITempDataRepository iTempDataRepository;
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
    private AppMenuFragment mAppMenuFragment;
    private FragmentTransaction mFragmentTransaction;
    private RolesFragment mRolesFragment;
    private TasksFragment mTasksFragment;
    private LocationFragment mLocationFragment;
    private FilesFragmentFound mFilesFragmentFound;

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
        iTempDataRepository = new TempDataRepository();
        selectedStory = inMemoryStoryRepository.getSelectedStory();

        progressLayout = (RelativeLayout) findViewById(R.id.ma_progress_bar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.ma_fab);
        loadContentLayout = (RelativeLayout) findViewById(R.id.ma_load_content_layout);
        mainContentLayout = (ConstraintLayout) findViewById(R.id.ma_main_content_layout);
        loadContentLayout.setVisibility(View.GONE);
        floatingActionButton.setOnClickListener(this);
        mActionBarToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mActionBarToolbar);
        setTitle(selectedStory.getPreviewText());
        mActionBarToolbar.setElevation(8);

        setupBottomNavigationView();
        initializeFragment();

    }

    private void setupBottomNavigationView() {
        ahBottomNavigationView = (AHBottomNavigation) findViewById(R.id.ma_bottom_navigation);
        AHBottomNavigationItem itemMenu = new AHBottomNavigationItem(R.string.title_menu, R.drawable.ic_menu, R.color.colorWhite);
        AHBottomNavigationItem itemQrReader = new AHBottomNavigationItem(R.string.title_qr_reader, R.drawable.ic_qr_scan, R.color.colorWhite);
        AHBottomNavigationItem itemGoals = new AHBottomNavigationItem(R.string.title_history_scan, R.drawable.ic_hist_scan, R.color.colorWhite);
        ahBottomNavigationView.addItem(itemMenu);
        ahBottomNavigationView.addItem(itemQrReader);
        ahBottomNavigationView.addItem(itemGoals);

        ahBottomNavigationView.setNotificationAnimationDuration(3000);
        ahBottomNavigationView.setColored(true);
        ahBottomNavigationView.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        ahBottomNavigationView.setInactiveColor(getResources().getColor(R.color.colorGrayLight));
        ahBottomNavigationView.setAccentColor(getResources().getColor(R.color.colorBlue));
        ahBottomNavigationView.setDefaultBackgroundColor(getResources().getColor(R.color.colorGrayLight));
        ahBottomNavigationView.setNotificationTextColor(getResources().getColor(R.color.colorWhite));
        ahBottomNavigationView.setNotificationBackgroundColor(getResources().getColor(R.color.colorNotification));
        ahBottomNavigationView.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        if (!wasSelected) {
                            getFragmentTransaction().replace(R.id.ma_fragment_container, mAppMenuFragment).commit();
                            showFab(false);
                        }
                        break;
                    case 1:
                        if (!wasSelected) {
                            getFragmentTransaction().replace(R.id.ma_fragment_container, mQrReaderFragment).commit();
                            showFab(false);
                        }
                        break;
                    case 2:
                        if (!wasSelected) {
                            getFragmentTransaction().replace(R.id.ma_fragment_container, mHistoryScanFragment).commit();
                            showFab(false);
                        }
                        break;
                }
                return true;
            }
        });
    }


    private void initializeFragment() {
        mFilesFragmentFound = new FilesFragmentFound();
        mTasksFragment = new TasksFragment();
        mRolesFragment = new RolesFragment();
        mLocationFragment = new LocationFragment();
        mRolesFragment.setiStoryRepository(inMemoryStoryRepository);
        mQrReaderFragment = new QrReaderFragment();
        mQrReaderFragment.setupRepository(iHistoryScanDataStore, iGoalsDataStore);
        mQrReaderFragment.setBottomNavigationView(ahBottomNavigationView);
        mQrReaderFragment.setupOnCloseQrListener(new QrReaderFragment.OnCloseQrReaderButtonClickListener() {
            @Override
            public void onClick() {
                getFragmentTransaction().replace(R.id.ma_fragment_container, mAppMenuFragment).commit();
                ahBottomNavigationView.setCurrentItem(0);
            }
        });
        mRoleInfoFragment = new RoleInfoFragment();
        mGeneralHistoryFragment = new GeneralHistoryFragment();
        mHistoryScanFragment = new HistoryScanFragment();
        mHistoryScanFragment.setupRepository(iHistoryScanDataStore);
        mGoalsFragment = new GoalsFragment();
        mTasksFragment.setupRepository(iGoalsDataStore);
        mTasksFragment.setBottomNavigationView(ahBottomNavigationView);
        mAppMenuFragment = new AppMenuFragment();

        List<AppMenuItem> appMenuItemList = new ArrayList<>();

        AppMenuItem menuItemRole = new AppMenuItem(mRoleInfoFragment, getResources().getDrawable(R.drawable.ic_character), getString(R.string.title_characters_info), true);
        AppMenuItem menuItemGhistory = new AppMenuItem(mGeneralHistoryFragment, getResources().getDrawable(R.drawable.ic_history), getString(R.string.title_general_history), true);
        AppMenuItem menuItemRoles = new AppMenuItem(mRolesFragment, getResources().getDrawable(R.drawable.ic_participants), getString(R.string.title_roles), true);
        AppMenuItem menuItemGoal = new AppMenuItem(mGoalsFragment, getResources().getDrawable(R.drawable.ic_purpose), getString(R.string.title_goals), true);
        AppMenuItem menuItemFiles = new AppMenuItem(new Fragment(), getResources().getDrawable(R.drawable.ic_file), getString(R.string.title_files), true);
        AppMenuItem menuItemTasks = new AppMenuItem(mTasksFragment, getResources().getDrawable(R.drawable.ic_tasks), getString(R.string.title_tasks), true);
        AppMenuItem menuItemTimer = new AppMenuItem(null, getResources().getDrawable(R.drawable.ic_time_n), getString(R.string.title_timer), false);
        AppMenuItem menuItemTips = new AppMenuItem(null, getResources().getDrawable(R.drawable.ic_tips_n), getString(R.string.title_tips), false);
        AppMenuItem menuItemLocation = new AppMenuItem(null, getResources().getDrawable(R.drawable.ic_location_n), getString(R.string.title_location), false);

        appMenuItemList.add(menuItemRole);
        appMenuItemList.add(menuItemGhistory);
        appMenuItemList.add(menuItemRoles);
        appMenuItemList.add(menuItemGoal);
        appMenuItemList.add(menuItemFiles);
        appMenuItemList.add(menuItemTasks);
        appMenuItemList.add(menuItemTimer);
        appMenuItemList.add(menuItemTips);
        appMenuItemList.add(menuItemLocation);

        mAppMenuFragment.setItemList(appMenuItemList);
        mAppMenuFragment.setFragmentItemClickListener(new AppMenuFragment.OnFragmentItemClickListener() {
            @Override
            public void onClick(AppMenuItem appMenuItem) {
                if (appMenuItem.getFragment() != null) {
                    loadContentLayout.setVisibility(View.VISIBLE);
                    mainContentLayout.setVisibility(View.GONE);
                    iTempDataRepository.setAppMenuItem(appMenuItem);
                    startActivity(new Intent(MainActivity.this, ContentActivity.class));
                }
            }
        });

        mFragmentTransaction = getFragmentTransaction();
        mFragmentTransaction.add(R.id.ma_fragment_container, mAppMenuFragment).commit();
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
            anim.setDuration(200L);
            floatingActionButton.startAnimation(anim);
            floatingActionButton.setVisibility(View.VISIBLE);
        } else {
            if (floatingActionButton.getVisibility() == View.VISIBLE) {
                Animation anim = AnimationUtils.loadAnimation(floatingActionButton.getContext(), R.anim.scale_down);
                anim.setDuration(200L);
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
                // update data
                mTasksFragment.setupRecyclerView();
                break;
        }
    }


}
