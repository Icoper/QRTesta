package com.example.android_dev.qrtest.ui.fragment.ma;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.model.Story;
import com.example.android_dev.qrtest.presenter.historyscan.HistoryScanPresenter;
import com.example.android_dev.qrtest.util.IHistoryScanFragment;

import java.io.File;
import java.util.ArrayList;

public class HistoryScanFragment extends Fragment {
    private Context mContext;
    private GridView gridView;
    private HistoryScanPresenter historyScanPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.history_scan_fragment, container, false);
        mContext = v.getContext();
        gridView = (GridView) v.findViewById(R.id.hs_grid_view);
        setupPresenter();
        return v;
    }

    private void setupPresenter() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        historyScanPresenter = new HistoryScanPresenter(new IHistoryScanFragment() {
            @Override
            public void showGridView(final ArrayList<Story> scannedStoryList) {
                ArrayAdapter<Story> adapter = new ArrayAdapter<Story>(mContext, R.layout.scan_history_item, scannedStoryList) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        if (convertView == null) {
                            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            convertView = inflater.inflate(R.layout.scan_history_item, null);
                        }

                        ImageView appIcon = (ImageView) convertView.findViewById(R.id.shi_story_img);
                        appIcon.setImageBitmap(getBitMapByPath(scannedStoryList.get(position).getMedia().getImages().get(0)));

                        TextView appLabel = (TextView) convertView.findViewById(R.id.shi_story_name);
                        appLabel.setText(scannedStoryList.get(position).getName());

                        return convertView;
                    }
                };
                gridView.setAdapter(adapter);
            }

            @Override
            public void showMsg(String msg) {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        }, sp);
        historyScanPresenter.getScannedStoryList();
    }

    private Bitmap getBitMapByPath(String path) {
        File imgFile = new File(path);

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;
        }
        return null;
    }
}
