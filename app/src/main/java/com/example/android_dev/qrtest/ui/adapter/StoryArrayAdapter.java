package com.example.android_dev.qrtest.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.model.Story;
import com.example.android_dev.qrtest.util.GlobalNames;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StoryArrayAdapter extends RecyclerView.Adapter<StoryArrayAdapter.StoryViewHolder> {
    private static final Integer IS_HEAD = 0;
    private static final Integer IS_VIEW = 1;


    private ArrayList<Integer> mediaData;
    private Story changedStory;
    private Context context;
    private StoryArrayAdapter.OnItemStoryClickListener onItemClickListener;
    private View view;

    public StoryArrayAdapter(Story changedStory, StoryArrayAdapter.OnItemStoryClickListener onItemClickListener) {
        this.changedStory = changedStory;
        this.onItemClickListener = onItemClickListener;
        createMediaData();
    }

    private void createMediaData() {
        mediaData = new ArrayList<>();
        mediaData.add(changedStory.getAbout());
        ArrayList<Integer> img = changedStory.getMedia().getImages();
        ArrayList<Integer> video = changedStory.getMedia().getVideo();
        ArrayList<Integer> audio = changedStory.getMedia().getAudio();
        mediaData.addAll(img);
        mediaData.addAll(video);
        mediaData.addAll(audio);
    }

    @Override
    public StoryArrayAdapter.StoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_story_info,
                viewGroup, false);
        StoryArrayAdapter.StoryViewHolder storyViewHolder = new StoryArrayAdapter.StoryViewHolder(view);
        context = view.getContext();
        return storyViewHolder;
    }

    @Override
    public void onBindViewHolder(StoryArrayAdapter.StoryViewHolder storyViewHolder, int i) {
        final int position = i;
        String itemType = "";

        if (getItemViewType(i) == IS_HEAD) {
            storyViewHolder.imgView.setVisibility(View.GONE);
            storyViewHolder.videoView.setVisibility(View.GONE);

            InputStream inputStream = view.getResources().openRawResource(changedStory.getAbout());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String about = "";
            try {
                about = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            storyViewHolder.text.setText(about);

        } else {
            storyViewHolder.text.setVisibility(View.GONE);
            if (context.getResources().getResourceName(mediaData.get(position))
                    .contains(GlobalNames.VIDEO_RES) &&
                    !context.getResources().getResourceName(mediaData.get(position))
                            .contains(GlobalNames.ABOUT_TAG)) {

                itemType = GlobalNames.VIDEO_RES;
            } else if (context.getResources().getResourceName(mediaData.get(position))
                    .contains(GlobalNames.AUDIO_RES) &&
                    !context.getResources().getResourceName(mediaData.get(position))
                            .contains(GlobalNames.ABOUT_TAG)) {

                itemType = GlobalNames.AUDIO_RES;
            } else if (context.getResources().getResourceName(mediaData.get(position))
                    .contains(GlobalNames.IMG_RES) &&
                    !context.getResources().getResourceName(mediaData.get(position))
                            .contains(GlobalNames.ABOUT_TAG)) {

                itemType = GlobalNames.IMG_RES;
            }
        }

        if (!itemType.isEmpty()) {
            showContentByType(storyViewHolder, position, itemType);
        }

        final String finalItemType = itemType;
        storyViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(finalItemType, mediaData.get(position));
            }
        });
    }

    private void showContentByType(StoryViewHolder storyViewHolder, int position, String type) {
        storyViewHolder.text.setVisibility(View.GONE);
        int width = getMaximumWeight();
        int height = width / 2 + 100;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        switch (type) {
            case GlobalNames.VIDEO_RES:

                String filepath = "android.resource://" + context.getPackageName() + "/" +
                        mediaData.get(position);
                Uri videoURI = Uri.parse(filepath);
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(context, videoURI);
                Bitmap bitmap = retriever
                        .getFrameAtTime(100000, MediaMetadataRetriever.OPTION_PREVIOUS_SYNC);
                Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                storyViewHolder.imgView.setVisibility(View.GONE);
                storyViewHolder.videoView.setVisibility(View.VISIBLE);
                storyViewHolder.videoView.setBackground(drawable);
                storyViewHolder.videoView.setLayoutParams(layoutParams);
                break;

            case GlobalNames.AUDIO_RES:
                storyViewHolder.imgView.setVisibility(View.VISIBLE);
                storyViewHolder.videoView.setVisibility(View.GONE);
                storyViewHolder.imgView.setBackgroundResource(R.drawable.audio_img);
                storyViewHolder.imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                storyViewHolder.imgView.setLayoutParams(
                        new LinearLayout.LayoutParams(200, 200));
                break;

            case GlobalNames.IMG_RES:
                storyViewHolder.videoView.setVisibility(View.GONE);
                storyViewHolder.imgView.setBackgroundResource(mediaData.get(position));
                storyViewHolder.imgView.setLayoutParams(layoutParams);
                storyViewHolder.imgView.setVisibility(View.VISIBLE);
                break;
        }
    }

    private Integer getMaximumWeight() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return IS_HEAD;
        else
            return IS_VIEW;
    }

    @Override
    public int getItemCount() {
        return mediaData.size();
    }

    public class StoryViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView imgView;
        RelativeLayout videoView;
        LinearLayout linearLayout;

        StoryViewHolder(View v) {
            super(v);
            videoView = (RelativeLayout) v.findViewById(R.id.ilsi_video_layout);
            text = (TextView) v.findViewById(R.id.ilsi_text_view);
            imgView = (ImageView) v.findViewById(R.id.ilsi_image_view);
            linearLayout = (LinearLayout) v.findViewById(R.id.ilsi_linear_layout);
        }
    }

    public interface OnItemStoryClickListener {
        void onClick(String finalItemType, int res);
    }

}

