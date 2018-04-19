package com.example.android_dev.qrtest.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.tempData.ITempDataRepository;
import com.example.android_dev.qrtest.db.tempData.TempDataRepository;
import com.example.android_dev.qrtest.model.AppMenuItem;
import com.example.android_dev.qrtest.ui.adapter.FilesSectionsPageAdapter;
import com.example.android_dev.qrtest.ui.fragment.filesFragment.FilesFragmentDefault;
import com.example.android_dev.qrtest.ui.fragment.filesFragment.FilesFragmentFound;

public class ContentActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ContentActivity";
    private ViewPager mViewPager;
    private ITempDataRepository iTempDataRepository;
    private AppMenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        iTempDataRepository = new TempDataRepository();
        menuItem = iTempDataRepository.getAppMenuItem();

        if (menuItem.getItemName().equals("Files")) {
            setContentView(R.layout.activity_content_tabs);
            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.ac_viewPager_layout);
            setupViewPager(mViewPager);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.ac_tabs_layout);
            tabLayout.setupWithViewPager(mViewPager);
        } else {
            getFragmentTransaction().replace(R.id.ca_fragment_container, menuItem.getFragment()).commit();
        }
        setupToolBar();
    }

    private void setupViewPager(ViewPager viewPager) {
        FilesSectionsPageAdapter adapter = new FilesSectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new FilesFragmentFound(), "Found");
        adapter.addFragment(new FilesFragmentDefault(), "Default");

        viewPager.setAdapter(adapter);
    }

    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.ac_my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(menuItem.getItemName());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private FragmentTransaction getFragmentTransaction() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = fragmentManager.beginTransaction();

        return mFragmentTransaction;
    }

}
