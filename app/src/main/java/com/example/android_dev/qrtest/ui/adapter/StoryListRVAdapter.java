package com.example.android_dev.qrtest.ui.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.util.GlobalNames;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryListRVAdapter extends RecyclerView.Adapter<StoryListRVAdapter.StoryViewHolder> {
    private List<IStory> stories;
    private final double calculationPercent = 5.4;
    private Context context;
    private ViewGroup.LayoutParams deafLayoutParams;
    private OnItemClickListener onItemClickListener;

    public StoryListRVAdapter(List<IStory> stories, OnItemClickListener onItemClickListener) {
        this.stories = stories;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history_list, viewGroup, false);
        context = v.getContext();
        StoryViewHolder storyViewHolder = new StoryViewHolder(v);
        return storyViewHolder;
    }

    @Override
    public void onBindViewHolder(StoryViewHolder storyViewHolder, int i) {
        final int position = i;

        if (deafLayoutParams == null) {
            calculateLayoutParams(storyViewHolder);
        }
        String imgPath = GlobalNames.ENVIRONMENT_STORE +
                stories.get(position).getResFolderName() + "/Resource1";

        String imgName = stories.get(position).getPreviewImg();

        storyViewHolder.icon.setImageBitmap(getBitMapByPath(imgPath, imgName));
        storyViewHolder.name.setText(stories.get(position).getPreviewText());
        storyViewHolder.relativeLayout.setLayoutParams(deafLayoutParams);
        storyViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(stories.get(position));
            }
        });
    }

    private void calculateLayoutParams(StoryViewHolder holder) {
        int optimisationPixelCount = (int) (getMaximumWeight() * calculationPercent) / 100;
        int width = getMaximumWeight() / 2 - optimisationPixelCount;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);

        deafLayoutParams = holder.relativeLayout.getLayoutParams();
        deafLayoutParams.height = layoutParams.height + 100;
        deafLayoutParams.width = layoutParams.width;
    }

    private Integer getMaximumWeight() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }

    private Bitmap getBitMapByPath(String path, String name) {
        File imgFile = new File(path, name);

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public class StoryViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CircleImageView icon;
        RelativeLayout relativeLayout;

        StoryViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.as_story_name);
            icon = (CircleImageView) v.findViewById(R.id.as_story_icon);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.as_relative_layout);
        }
    }

    public interface OnItemClickListener {
        void onClick(IStory iStory);
    }

}
