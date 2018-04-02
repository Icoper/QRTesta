package com.example.android_dev.qrtest.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.IStoryRepository;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.util.GlobalNames;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MediaArrayAdapter extends RecyclerView.Adapter<MediaArrayAdapter.StoryViewHolder> {
    private final static String LOG_TAG = "MediaArrayAdapter";
    private ArrayList<AssetTypes> mediaData;
    private Context context;
    private MediaArrayAdapter.OnItemStoryClickListener onItemClickListener;
    private IStoryRepository iStoryRepository;
    private View view;
    private IStory jsonStory;

    public MediaArrayAdapter(MediaArrayAdapter.OnItemStoryClickListener onItemClickListener, List<Integer> resId) {
        Log.d(LOG_TAG, "constructor is called");
        this.onItemClickListener = onItemClickListener;
        createMediaData(resId);
    }

    private void createMediaData(List<Integer> resId) {
        Log.d(LOG_TAG, "createMediaData is called");
        iStoryRepository = new InMemoryStoryRepository();
        mediaData = new ArrayList<>(iStoryRepository.getResourceById(resId));
        jsonStory = iStoryRepository.getSelectedStory();
    }

    @Override
    public MediaArrayAdapter.StoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d(LOG_TAG, "onCreateViewHolder is called");
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_story_info,
                viewGroup, false);
        MediaArrayAdapter.StoryViewHolder storyViewHolder = new MediaArrayAdapter.StoryViewHolder(view);
        context = view.getContext();
        return storyViewHolder;
    }

    @Override
    public void onBindViewHolder(MediaArrayAdapter.StoryViewHolder storyViewHolder, int i) {
        final int position = i;
        showContentByType(storyViewHolder, mediaData.get(position));
        storyViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(mediaData.get(position));
            }
        });
    }

    private void showContentByType(final StoryViewHolder storyViewHolder, AssetTypes resource) {
        Log.d(LOG_TAG, "showContentByType is called - " + resource.getFileType());
        storyViewHolder.text.setVisibility(View.GONE);

        int width = getMaximumWeight();
        int height = width / 2 + 100;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);

        String filepath = GlobalNames.ENVIRONMENT_STORE +
                jsonStory.getResFolderName() + "/" + "Resource1/" + resource.getFileName();

        switch (resource.getFileType()) {
            case GlobalNames.VIDEO_RES:
                storyViewHolder.imgView.setVisibility(View.GONE);
                storyViewHolder.videoView.setVisibility(View.VISIBLE);
                storyViewHolder.text.setVisibility(View.GONE);

                Uri videoURI = Uri.parse(filepath);
                VideoPreviewDrawableTaskParams taskParams = new VideoPreviewDrawableTaskParams(videoURI, context, storyViewHolder);
                GetVideoPreviewDrawable getVideoPreviewDrawable = new GetVideoPreviewDrawable();
                getVideoPreviewDrawable.execute(taskParams);
                storyViewHolder.videoView.setLayoutParams(layoutParams);
                break;

            case GlobalNames.AUDIO_RES:
                storyViewHolder.imgView.setVisibility(View.VISIBLE);
                storyViewHolder.videoView.setVisibility(View.GONE);
                storyViewHolder.text.setVisibility(View.GONE);

                storyViewHolder.imgView.setVisibility(View.VISIBLE);
                storyViewHolder.videoView.setVisibility(View.GONE);
                storyViewHolder.imgView.setBackgroundResource(R.drawable.audio_img);
                storyViewHolder.imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                storyViewHolder.imgView.setLayoutParams(
                        new LinearLayout.LayoutParams(200, 200));
                break;

            case GlobalNames.IMG_RES:
                storyViewHolder.imgView.setVisibility(View.VISIBLE);
                storyViewHolder.videoView.setVisibility(View.GONE);
                storyViewHolder.text.setVisibility(View.GONE);

                storyViewHolder.videoView.setVisibility(View.GONE);
                Glide.with(view).load(getBitMapByPath(filepath)).into(storyViewHolder.imgView);
                storyViewHolder.imgView.setLayoutParams(layoutParams);
                storyViewHolder.imgView.setVisibility(View.VISIBLE);
                break;
            case GlobalNames.DOC_RES:
                storyViewHolder.imgView.setVisibility(View.GONE);
                storyViewHolder.videoView.setVisibility(View.GONE);
                storyViewHolder.text.setVisibility(View.VISIBLE);

                String text = "";
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
                    text = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                storyViewHolder.text.setText(text);
                break;
        }
    }

    private Bitmap getBitMapByPath(String path) {
        Uri uri = Uri.parse(path);
        File imgFile = new File(uri.getPath());

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;
        }
        return null;
    }

    private Integer getMaximumWeight() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
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

    private class VideoPreviewDrawableTaskParams {
        private Uri uri;
        private Context context;
        private StoryViewHolder storyViewHolder;
        private Drawable drawable;

        public VideoPreviewDrawableTaskParams(Uri uri, Context context, StoryViewHolder storyViewHolder) {
            this.uri = uri;
            this.context = context;
            this.storyViewHolder = storyViewHolder;
        }

        public Drawable getDrawable() {
            return drawable;
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }

        public StoryViewHolder getStoryViewHolder() {
            return storyViewHolder;
        }

        public Uri getUri() {
            return uri;
        }

        public Context getContext() {
            return context;
        }
    }

    public static class GetVideoPreviewDrawable extends AsyncTask<VideoPreviewDrawableTaskParams, Void, VideoPreviewDrawableTaskParams> {
        @Override
        protected VideoPreviewDrawableTaskParams doInBackground(VideoPreviewDrawableTaskParams... params) {
            Log.d(LOG_TAG, " doInBackground is called ");

            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(params[0].getContext(), params[0].getUri());
            Bitmap bitmap = retriever
                    .getFrameAtTime(100000, MediaMetadataRetriever.OPTION_PREVIOUS_SYNC);
            Drawable drawable = new BitmapDrawable(params[0].getContext().getResources(), bitmap);
            VideoPreviewDrawableTaskParams taskParams = params[0];
            taskParams.setDrawable(drawable);
            return taskParams;
        }

        @Override
        protected void onPostExecute(VideoPreviewDrawableTaskParams videoPreviewDrawableTaskParams) {
            Drawable drawable = videoPreviewDrawableTaskParams.getDrawable();
            videoPreviewDrawableTaskParams.getStoryViewHolder().videoView.setBackground(drawable);
        }
    }

    public interface OnItemStoryClickListener {
        void onClick(AssetTypes resource);
    }

}

