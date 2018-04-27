package com.example.android_dev.qrtest.ui.adapter.mediaAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.util.GlobalNames;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PhotoVideoTypeAdapter extends RecyclerView.Adapter<PhotoVideoTypeAdapter.PhotoVideoTypeViewHolder> {
    private final static String LOG_TAG = "PhotoVideoTypeAdapter";
    private List<AssetTypes> assetTypes;
    private String resFolderFilePath;
    private Context context;
    private View view;
    private PhotoVideoTypeAdapter.OnItemStoryClickListener onItemStoryClickListener;
    private final double calculationPercent = 1.4;
    private ViewGroup.LayoutParams deafLayoutParams;


    public PhotoVideoTypeAdapter(List<AssetTypes> assetTypes, String resFolderFilePath, OnItemStoryClickListener onItemStoryClickListener) {
        this.onItemStoryClickListener = onItemStoryClickListener;
        this.assetTypes = assetTypes;
        this.resFolderFilePath = resFolderFilePath;
    }

    @Override
    public PhotoVideoTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(LOG_TAG, "onCreateViewHolder is called");
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_video_type_list,
                parent, false);
        context = view.getContext();

        return new PhotoVideoTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PhotoVideoTypeViewHolder holder, int i) {
        final int position = i;

        if (deafLayoutParams == null) {
            calculateLayoutParams(holder);
        }

        final String filepath = resFolderFilePath + assetTypes.get(position).getFileName();

        switch (assetTypes.get(position).getFileType()) {
            case GlobalNames.VIDEO_RES:
                Uri videoURI = Uri.parse(filepath);
                DrawableTaskParams taskParams = new DrawableTaskParams(videoURI, context, holder);
                GetVideoPreviewDrawable getVideoPreviewDrawable = new GetVideoPreviewDrawable();
                getVideoPreviewDrawable.execute(taskParams);
                holder.viewLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemStoryClickListener.onClick(assetTypes.get(position));
                    }
                });
                holder.playButton.setVisibility(View.VISIBLE);
                break;
            case GlobalNames.IMG_RES:

                DrawableTaskParams drawableTaskParams = new DrawableTaskParams(Uri.parse(filepath), context, holder);
                GetBitmapDrawable getBitmapDrawable = new GetBitmapDrawable();
                getBitmapDrawable.execute(drawableTaskParams);

                holder.viewLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemStoryClickListener.onClick(assetTypes.get(position));
                    }
                });
                holder.playButton.setVisibility(View.GONE);
                break;
        }
        holder.viewLayout.setLayoutParams(deafLayoutParams);
    }

    @Override
    public int getItemCount() {
        return assetTypes.size();
    }

    class PhotoVideoTypeViewHolder extends RecyclerView.ViewHolder {
        CardView viewLayout;
        ImageView playButton;
        ImageView backgroundImage;

        public PhotoVideoTypeViewHolder(View itemView) {
            super(itemView);
            viewLayout = (CardView) itemView.findViewById(R.id.ipvt_photo_video_layout);
            playButton = (ImageView) itemView.findViewById(R.id.ipvt_play_btn);
            backgroundImage = (ImageView) itemView.findViewById(R.id.ipvt_background_iv);
        }
    }


    private class DrawableTaskParams {
        private Uri uri;
        private Context context;
        private PhotoVideoTypeViewHolder storyViewHolder;
        private Drawable drawable;

        DrawableTaskParams(Uri uri, Context context, PhotoVideoTypeViewHolder storyViewHolder) {
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

        PhotoVideoTypeViewHolder getStoryViewHolder() {
            return storyViewHolder;
        }

        Uri getUri() {
            return uri;
        }

        public Context getContext() {
            return context;
        }
    }

    public static class GetBitmapDrawable extends AsyncTask<DrawableTaskParams, Void, DrawableTaskParams> {
        @Override
        protected DrawableTaskParams doInBackground(DrawableTaskParams... params) {
            DrawableTaskParams drawableTaskParams = params[0];
            Uri uri = drawableTaskParams.getUri();
            File imgFile = new File(String.valueOf(uri));
            if (imgFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
                Drawable drawable = new BitmapDrawable(params[0].getContext().getResources(), bitmap);
                drawableTaskParams.setDrawable(drawable);
            }

            return drawableTaskParams;
        }

        @Override
        protected void onPostExecute(DrawableTaskParams videoPreviewDrawableTaskParams) {
            Drawable drawable = videoPreviewDrawableTaskParams.getDrawable();
            try {
                Glide.with(videoPreviewDrawableTaskParams.context).load(drawable).into(videoPreviewDrawableTaskParams.getStoryViewHolder().backgroundImage);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    public static class GetVideoPreviewDrawable extends AsyncTask<DrawableTaskParams, Void, DrawableTaskParams> {
        @Override
        protected DrawableTaskParams doInBackground(DrawableTaskParams... params) {
            Log.d(LOG_TAG, " doInBackground is called ");
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(params[0].getContext(), params[0].getUri());
            Bitmap bitmap = retriever
                    .getFrameAtTime(100000, MediaMetadataRetriever.OPTION_PREVIOUS_SYNC);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
            Drawable drawable = new BitmapDrawable(params[0].getContext().getResources(), bitmap);
            DrawableTaskParams taskParams = params[0];
            taskParams.setDrawable(drawable);
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return taskParams;
        }

        @Override
        protected void onPostExecute(DrawableTaskParams videoPreviewDrawableTaskParams) {
            Drawable drawable = videoPreviewDrawableTaskParams.getDrawable();
            try {
                Glide.with(videoPreviewDrawableTaskParams.context).load(drawable).into(videoPreviewDrawableTaskParams.getStoryViewHolder().backgroundImage);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    private Integer getMaximumWeight() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return width;
    }


    private void calculateLayoutParams(PhotoVideoTypeViewHolder holder) {
        int optimisationPixelCount = (int) (getMaximumWeight() * calculationPercent) / 100;
        int width = getMaximumWeight() / GlobalNames.DEFAULT_GRID_COLUMN_COUNT - optimisationPixelCount;

        deafLayoutParams = holder.viewLayout.getLayoutParams();
        deafLayoutParams.height = width;
        deafLayoutParams.width = width;
    }

    public interface OnItemStoryClickListener {
        void onClick(AssetTypes resource);
    }
}
