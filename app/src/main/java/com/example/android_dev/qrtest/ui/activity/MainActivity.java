package com.example.android_dev.qrtest.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.GoalsDataStore;
import com.example.android_dev.qrtest.db.HistoryScanDataStore;
import com.example.android_dev.qrtest.db.IGoalsDataStore;
import com.example.android_dev.qrtest.db.IHistoryScanDataStore;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.db.SavedDataStore;
import com.example.android_dev.qrtest.db.SingletonStoryData;
import com.example.android_dev.qrtest.model.HistoryScansQRInformationIDs;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.ui.fragment.CharacterInfoFragment;
import com.example.android_dev.qrtest.ui.fragment.GeneralHistoryFragment;
import com.example.android_dev.qrtest.ui.fragment.GoalsFragment;
import com.example.android_dev.qrtest.ui.fragment.HistoryScanFragment;
import com.example.android_dev.qrtest.ui.fragment.QrReaderFragment;
import com.example.android_dev.qrtest.util.ColorUtil;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String LOG_TAG = "MainActivity";

    private InMemoryStoryRepository inMemoryStoryRepository;
    private IHistoryScanDataStore iHistoryScanDataStore;
    private IGoalsDataStore iGoalsDataStore;

    private IStory selectedStory;
    // ui
    private Toolbar mActionBarToolbar;
    private BottomNavigationView bottomNavigationView;
    private QrReaderFragment mQrReaderFragment;
    private CharacterInfoFragment mCharacterInfoFragment;
    private GeneralHistoryFragment mGeneralHistoryFragment;
    private HistoryScanFragment mHistoryScanFragment;
    private GoalsFragment mGoalsFragment;
    private FragmentTransaction mFragmentTransaction;
    private DrawerLayout mDrawerLayout;
    private NavigationView drawerNavigationView;
    private boolean doubleBackToExitPressedOnce = false;
    private FloatingActionButton floatingActionButton;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    {
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mFragmentTransaction = getFragmentTransaction();

                switch (item.getItemId()) {
                    case R.id.navigation_characters_info:
                        mFragmentTransaction.replace(R.id.ma_fragment_container, mCharacterInfoFragment).commit();
                        showFab(false);
                        return true;
                    case R.id.navigation_general_history:
                        mFragmentTransaction.replace(R.id.ma_fragment_container, mGeneralHistoryFragment).commit();
                        showFab(false);
                        return true;

                    case R.id.navigation_qr_reader:
                        mFragmentTransaction.replace(R.id.ma_fragment_container, mQrReaderFragment).commit();
                        showFab(false);
                        return true;

                    case R.id.navigation_history_scan:
                        mFragmentTransaction.replace(R.id.ma_fragment_container, mHistoryScanFragment).commit();
                        showFab(false);
                        return true;
                    case R.id.navigation_goals:
                        mFragmentTransaction.replace(R.id.ma_fragment_container, mGoalsFragment).commit();
                        showFab(true);
                        return true;
                }


                return false;
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_exit:
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

        inMemoryStoryRepository = new InMemoryStoryRepository();
        iGoalsDataStore = new GoalsDataStore();
        iHistoryScanDataStore = new HistoryScanDataStore();
        selectedStory = inMemoryStoryRepository.getSelectedStory();

        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        drawerNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.ma_fab);
        floatingActionButton.setOnClickListener(this);

        mActionBarToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mActionBarToolbar);
        setTitle(selectedStory.getPreviewText());
        drawerNavigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mActionBarToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(bottomNavigationView);

        changeToolBarColor();
        setupNavHeader();
        initializeUi();
    }

    private void setupNavHeader() {
        View headerLayout = drawerNavigationView.getHeaderView(0);
        TextView navText = (TextView) headerLayout.findViewById(R.id.nav_header_text);
        navText.setText(inMemoryStoryRepository.getSelectedRole().getName());
        LinearLayout linearLayout = (LinearLayout) headerLayout.findViewById(R.id.nav_header_layout);
        linearLayout.setBackgroundColor(Color.parseColor(
                ColorUtil.changeColorHSB((selectedStory.getColor()))));
    }

    private void changeToolBarColor() {
        mActionBarToolbar.setBackgroundColor(Color.parseColor(
                ColorUtil.changeColorHSB((selectedStory.getColor()))));
        mActionBarToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().
                    setStatusBarColor(
                            Color.parseColor(
                                    ColorUtil.changeColorHSB((selectedStory.getColor()))));
        }
        bottomNavigationView.setBackgroundColor(Color.parseColor(
                ColorUtil.changeColorHSB((selectedStory.getColor()))));
    }

    private void initializeUi() {
        mQrReaderFragment = new QrReaderFragment();
        mQrReaderFragment.setupRepository(iHistoryScanDataStore, iGoalsDataStore);
        mCharacterInfoFragment = new CharacterInfoFragment();
        mGeneralHistoryFragment = new GeneralHistoryFragment();
        mHistoryScanFragment = new HistoryScanFragment();
        mHistoryScanFragment.setupRepository(iHistoryScanDataStore);
        mGoalsFragment = new GoalsFragment();
        mGoalsFragment.setupRepository(iGoalsDataStore);
        mFragmentTransaction = getFragmentTransaction();
        mFragmentTransaction.add(R.id.ma_fragment_container, mGeneralHistoryFragment).commit();

    }

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
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

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
        super.onDestroy();
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
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        } else if (doubleBackToExitPressedOnce) {
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.navigation_logout:
                logoutUser();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ma_fab:
                // update data
                mGoalsFragment.setupRecyclerView();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, " onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }


}
