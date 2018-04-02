package com.example.android_dev.qrtest.ui.adapter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.SingletonStoryData;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.util.GlobalNames;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryListArrayAdapter extends RecyclerView.Adapter<StoryListArrayAdapter.StoryViewHolder> {
    private List<IStory> stories;
    private OnItemClickListener onItemClickListener;

    public StoryListArrayAdapter(List<IStory> stories, OnItemClickListener onItemClickListener) {
        this.stories = stories;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history_list, viewGroup, false);
        StoryViewHolder storyViewHolder = new StoryViewHolder(v);
        return storyViewHolder;
    }

    @Override
    public void onBindViewHolder(StoryViewHolder storyViewHolder, int i) {
        final int position = i;

        String imgPath = GlobalNames.ENVIRONMENT_STORE +
                stories.get(position).getResFolderName() + "/Resource1";

        String imgName = stories.get(position).getPreviewImg();

        storyViewHolder.icon.setImageBitmap(getBitMapByPath(imgPath, imgName));
        storyViewHolder.name.setText(stories.get(position).getPreviewText());
        storyViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingletonStoryData.getInstance().setSelectedStory(stories.get(position));
                onItemClickListener.onClick();
            }
        });
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
        LinearLayout linearLayout;

        StoryViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.hli_story_name);
            icon = (CircleImageView) v.findViewById(R.id.hli_story_icon);
            linearLayout = (LinearLayout) v.findViewById(R.id.hli_linear_layout);
        }
    }

    public interface OnItemClickListener {
        void onClick();
    }

}
