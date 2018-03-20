package com.example.android_dev.qrtest.ui.fragment.ma;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_dev.qrtest.R;


public class GeneralHistoryFragment extends Fragment {
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.general_history_fragment, container, false);
        mContext = v.getContext();

        return v;
    }
}
