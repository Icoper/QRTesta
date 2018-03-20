package com.example.android_dev.qrtest.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.ui.fragment.ma.CharacterInfoFragment;
import com.example.android_dev.qrtest.ui.fragment.ma.GeneralHistoryFragment;
import com.example.android_dev.qrtest.ui.fragment.ma.HistoryScanFragment;
import com.example.android_dev.qrtest.ui.fragment.ma.QrReaderFragment;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";

    private String actorId;
    // ui
    private QrReaderFragment mQrReaderFragment;
    private CharacterInfoFragment mCharacterInfoFragment;
    private GeneralHistoryFragment mGeneralHistoryFragment;
    private HistoryScanFragment mHistoryScanFragment;

    private FragmentTransaction mFragmentTransaction;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

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
            }

            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Intent intent = getIntent();
        actorId = intent.getStringExtra("id");

        initializeUi();
    }

    private void initializeUi() {
        mQrReaderFragment = new QrReaderFragment();
        mCharacterInfoFragment = new CharacterInfoFragment();
        mGeneralHistoryFragment = new GeneralHistoryFragment();
        mHistoryScanFragment = new HistoryScanFragment();
        mFragmentTransaction = getFragmentTransaction();
        mFragmentTransaction.add(R.id.ma_fragment_container, mGeneralHistoryFragment).commit();
        Toast.makeText(this, actorId, Toast.LENGTH_SHORT).show();
    }

    private FragmentTransaction getFragmentTransaction() {
        FragmentManager fragmentManager = getFragmentManager();
        mFragmentTransaction = fragmentManager.beginTransaction();

        return mFragmentTransaction;
    }

}
