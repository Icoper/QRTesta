package com.example.android_dev.qrtest.ui.activity;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.GoalsDataStore;
import com.example.android_dev.qrtest.db.HistoryScanDataStore;
import com.example.android_dev.qrtest.db.IGoalsDataStore;
import com.example.android_dev.qrtest.db.IHistoryScanDataStore;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.HistoryScansQRInformationIDs;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.ui.fragment.CharacterInfoFragment;
import com.example.android_dev.qrtest.ui.fragment.GeneralHistoryFragment;
import com.example.android_dev.qrtest.ui.fragment.GoalsFragment;
import com.example.android_dev.qrtest.ui.fragment.HistoryScanFragment;
import com.example.android_dev.qrtest.ui.fragment.QrReaderFragment;
import com.example.android_dev.qrtest.util.ColorUtil;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";

    private InMemoryStoryRepository inMemoryStoryRepository;
    private IHistoryScanDataStore iHistoryScanDataStore;
    private IGoalsDataStore iGoalsDataStore;

    private IStory selectedStory;
    // ui
    Toolbar mActionBarToolbar;
    BottomNavigationView navigation;
    private QrReaderFragment mQrReaderFragment;
    private CharacterInfoFragment mCharacterInfoFragment;
    private GeneralHistoryFragment mGeneralHistoryFragment;
    private HistoryScanFragment mHistoryScanFragment;
    private GoalsFragment mGoalsFragment;
    private FragmentTransaction mFragmentTransaction;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    {
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mFragmentTransaction = getFragmentTransaction();

                switch (item.getItemId()) {
                    case R.id.navigation_characters_info:
                        mFragmentTransaction.replace(R.id.ma_fragment_container, mCharacterInfoFragment).commit();
                        return true;
                    case R.id.navigation_general_history:
                        mFragmentTransaction.replace(R.id.ma_fragment_container, mGeneralHistoryFragment).commit();
                        return true;

                    case R.id.navigation_qr_reader:
                        mFragmentTransaction.replace(R.id.ma_fragment_container, mQrReaderFragment).commit();
                        return true;

                    case R.id.navigation_history_scan:
                        mFragmentTransaction.replace(R.id.ma_fragment_container, mHistoryScanFragment).commit();
                        return true;
                    case R.id.navigation_goals:
                        mFragmentTransaction.replace(R.id.ma_fragment_container, mGoalsFragment).commit();
                        return true;
                }


                return false;
            }
        };
    }


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        mActionBarToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(navigation);
        setSupportActionBar(mActionBarToolbar);

        inMemoryStoryRepository = new InMemoryStoryRepository();
        iGoalsDataStore = new GoalsDataStore();
        iHistoryScanDataStore = new HistoryScanDataStore();

        selectedStory = inMemoryStoryRepository.getSelectedStory();

        changeToolBarColor();

        setTitle(selectedStory.getPreviewText());
        initializeUi();
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
        navigation.setBackgroundColor(Color.parseColor(
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
        updateJson();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");

    }

    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d(LOG_TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onStart() {
        Log.d(LOG_TAG, "onStart");
        super.onStart();
    }
}
