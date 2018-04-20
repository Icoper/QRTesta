package com.example.android_dev.qrtest.ui.adapter.mediaAdapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.IStoryRepository;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.util.GlobalNames;

import java.util.ArrayList;
import java.util.List;

public class MediaArrayAdapter extends RecyclerView.Adapter<MediaArrayAdapter.StoryViewHolder> {

    private final static String LOG_TAG = "MediaArrayAdapter";
    private String resPath;
    private ArrayList<AssetTypes> audioData;
    private ArrayList<AssetTypes> photoVideoData;
    private ArrayList<AssetTypes> docData;
    private ArrayList<List<AssetTypes>> sortedMediaData;
    private Context context;
    private MediaArrayAdapter.OnItemStoryClickListener onItemClickListener;
    private IStoryRepository iStoryRepository;
    private View view;
    private IStory jsonStory;
    private AudioTypeAdapter audioTypeAdapter;
    private DocTypeAdapter docTypeAdapter;
    private PhotoVideoTypeAdapter photoVideoTypeAdapter;


    public MediaArrayAdapter(MediaArrayAdapter.OnItemStoryClickListener onItemClickListener, List<Integer> resId) {
        Log.d(LOG_TAG, "constructor is called");
        this.onItemClickListener = onItemClickListener;
        createMediaData(resId);
    }

    private void createMediaData(List<Integer> resId) {
        Log.d(LOG_TAG, "createMediaData is called");
        iStoryRepository = new InMemoryStoryRepository();
        ArrayList<AssetTypes> mediaData = new ArrayList<>(iStoryRepository.getResourceById(resId));
        sortedMediaData = new ArrayList<>();
        audioData = new ArrayList<>();
        photoVideoData = new ArrayList<>();
        docData = new ArrayList<>();

        jsonStory = iStoryRepository.getSelectedStory();

        for (AssetTypes assetType : mediaData) {
            switch (assetType.getFileType()) {
                case GlobalNames.AUDIO_RES:
                    audioData.add(assetType);
                    break;
                case GlobalNames.DOC_RES:
                    docData.add(assetType);
                    break;
                case GlobalNames.VIDEO_RES:
                    photoVideoData.add(assetType);
                    break;
                case GlobalNames.IMG_RES:
                    photoVideoData.add(assetType);
                    break;
            }
        }
        sortedMediaData.add(docData);
        sortedMediaData.add(audioData);
        sortedMediaData.add(photoVideoData);
        resPath = GlobalNames.ENVIRONMENT_STORE + jsonStory.getResFolderName() + "/" + "Resource1/";

    }

    @Override
    public MediaArrayAdapter.StoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d(LOG_TAG, "onCreateViewHolder is called");
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.media_array_adapter_container,
                viewGroup, false);
        MediaArrayAdapter.StoryViewHolder storyViewHolder = new MediaArrayAdapter.StoryViewHolder(view);
        context = view.getContext();
        // Travel up the tree until fail, modifying the LayoutParams
        do {
            // Get the parent
            ViewParent parent = view.getParent();
            // Check if the parent exists
            if (parent != null) {
                // Get the view
                try {
                    view = (View) parent;
                } catch (ClassCastException e) {
                    // This will happen when at the top view, it cannot be cast to a View
                    break;
                }
                // Modify the layout
                view.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            }
        } while (view.getParent() != null);
        view.requestLayout();
        return storyViewHolder;
    }

    @Override
    public void onBindViewHolder(MediaArrayAdapter.StoryViewHolder storyViewHolder, int i) {
        final int position = i;
        storyViewHolder.photoVideoLayout.setVisibility(View.GONE);
        storyViewHolder.textLayout.setVisibility(View.GONE);
        storyViewHolder.audioLayout.setVisibility(View.GONE);


        if (sortedMediaData.get(position).size() > 0) {
            List<AssetTypes> assetTypes = new ArrayList<>();
            assetTypes = sortedMediaData.get(position);
            if (assetTypes.get(0).getFileType().equals(GlobalNames.AUDIO_RES)) {
                if (audioTypeAdapter == null) {
                    audioTypeAdapter = new AudioTypeAdapter(sortedMediaData.get(position), new AudioTypeAdapter.OnItemStoryClickListener() {
                        @Override
                        public void onClick(AssetTypes resource) {
                            onItemClickListener.onClick(resource);
                        }
                    }, resPath);
                    storyViewHolder.audioTypeRv.setLayoutManager(new LinearLayoutManager(context));
                    storyViewHolder.audioTypeRv.setAdapter(audioTypeAdapter);
                }
                storyViewHolder.audioLayout.setVisibility(View.VISIBLE);

            } else if (assetTypes.get(0).getFileType().equals(GlobalNames.DOC_RES)) {

                if (docTypeAdapter == null) {
                    docTypeAdapter = new DocTypeAdapter(sortedMediaData.get(position), resPath);
                    storyViewHolder.docTypeRv.setLayoutManager(new LinearLayoutManager(context));
                    storyViewHolder.docTypeRv.setAdapter(docTypeAdapter);
                }
                storyViewHolder.textLayout.setVisibility(View.VISIBLE);

            } else if (assetTypes.get(0).getFileType().equals(GlobalNames.IMG_RES) ||
                    assetTypes.get(0).getFileType().equals(GlobalNames.VIDEO_RES)) {
                if (photoVideoTypeAdapter == null) {

                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, GlobalNames.DEFAULT_GRID_COLUMN_COUNT);

                    photoVideoTypeAdapter = new PhotoVideoTypeAdapter(sortedMediaData.get(position), resPath, new PhotoVideoTypeAdapter.OnItemStoryClickListener() {
                        @Override
                        public void onClick(AssetTypes resource) {
                            onItemClickListener.onClick(resource);
                        }
                    });
                    storyViewHolder.photoVideoRv.setLayoutManager(new LinearLayoutManager(context));
                    storyViewHolder.photoVideoRv.setAdapter(photoVideoTypeAdapter);
                    storyViewHolder.photoVideoRv.setLayoutManager(mLayoutManager);
                }
                storyViewHolder.photoVideoLayout.setVisibility(View.VISIBLE);

            }
        }

    }

    @Override
    public int getItemCount() {
        return sortedMediaData.size();
    }


    class StoryViewHolder extends RecyclerView.ViewHolder {
        LinearLayout audioLayout;
        LinearLayout textLayout;
        LinearLayout photoVideoLayout;
        RecyclerView audioTypeRv;
        RecyclerView docTypeRv;
        RecyclerView photoVideoRv;

        StoryViewHolder(View v) {
            super(v);
            audioLayout = (LinearLayout) v.findViewById(R.id.mac_audio_rv_container);
            textLayout = (LinearLayout) v.findViewById(R.id.mac_text_rv_container);
            photoVideoLayout = (LinearLayout) v.findViewById(R.id.mac_pvideo_rv_container);
            audioTypeRv = (RecyclerView) v.findViewById(R.id.mac_audio_type);
            docTypeRv = (RecyclerView) v.findViewById(R.id.mac_doc_type);
            photoVideoRv = (RecyclerView) v.findViewById(R.id.mac_photo_video_type);
        }
    }


    public interface OnItemStoryClickListener {
        void onClick(AssetTypes resource);
    }

}

