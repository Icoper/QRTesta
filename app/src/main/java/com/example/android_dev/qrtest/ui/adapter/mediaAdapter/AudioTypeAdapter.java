package com.example.android_dev.qrtest.ui.adapter.mediaAdapter;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.model.AssetTypes;

import java.util.List;


public class AudioTypeAdapter extends RecyclerView.Adapter<AudioTypeAdapter.AudioTypeViewHolder> {
    private static final String LOG_TAG = "AudioTypeAdapter";
    private final String resPath;
    private List<AssetTypes> assetTypesList;
    private View view;
    private AudioTypeAdapter.OnItemStoryClickListener onItemStoryClickListener;

    public AudioTypeAdapter(List<AssetTypes> assetTypesList, OnItemStoryClickListener onItemStoryClickListener, String resPath) {
        this.onItemStoryClickListener = onItemStoryClickListener;
        this.assetTypesList = assetTypesList;
        this.resPath = resPath;
    }

    @Override
    public AudioTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(LOG_TAG, "onCreateViewHolder is called");
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio_type_list,
                parent, false);
        AudioTypeViewHolder audioTypeViewHolder = new AudioTypeViewHolder(view);
        return audioTypeViewHolder;
    }

    @Override
    public void onBindViewHolder(AudioTypeViewHolder holder, int i) {
        int width = getMaximumWeight();
        int height = width / 2 + 100;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        final int position = i;
        holder.soundName.setText(assetTypesList.get(position).getFileName());
        holder.audioLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemStoryClickListener.onClick(assetTypesList.get(position));
            }
        });

        // load data file
        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
        metaRetriever.setDataSource(resPath + assetTypesList.get(i).getFileName());
        // convert duration to minute:seconds
        String duration =
                metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long dur = Long.parseLong(duration);
        String seconds = String.valueOf((dur % 60000) / 1000);
        String minutes = String.valueOf(dur / 60000);
        String out = minutes + ":" + seconds;

        holder.soundDuration.setText(out);
    }

    @Override
    public int getItemCount() {
        return assetTypesList.size();
    }

    private Integer getMaximumWeight() {
        WindowManager windowManager = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
        assert windowManager != null;
        return windowManager.getDefaultDisplay().getWidth();
    }

    class AudioTypeViewHolder extends RecyclerView.ViewHolder {
        LinearLayout audioLayout;
        TextView soundName;
        TextView soundDuration;

        AudioTypeViewHolder(View itemView) {
            super(itemView);
            audioLayout = (LinearLayout) itemView.findViewById(R.id.iatl_audio_layout);
            soundName = (TextView) itemView.findViewById(R.id.iatl_audio_name);
            soundDuration = (TextView) itemView.findViewById(R.id.iatl_sound_duration);
        }
    }

    public interface OnItemStoryClickListener {
        void onClick(AssetTypes resource);
    }
}
