package com.example.android_dev.qrtest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.model.Story;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryListArrayAdapter extends RecyclerView.Adapter<StoryListArrayAdapter.StoryViewHolder> {
    private ArrayList<Story> stories;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public StoryListArrayAdapter(ArrayList<Story> stories, OnItemClickListener onItemClickListener) {
        this.stories = stories;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_list_item, viewGroup, false);
        StoryViewHolder storyViewHolder = new StoryViewHolder(v);
        context = v.getContext();
        return storyViewHolder;
    }

    @Override
    public void onBindViewHolder(StoryViewHolder storyViewHolder, int i) {
        final int position = i;
        int imgRes = stories.get(position).getMedia().getImages().get(0);
        storyViewHolder.icon.setBackgroundResource(imgRes);
        storyViewHolder.name.setText(stories.get(position).getName());
        storyViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(stories.get(position));
            }
        });
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
        void onClick(Story story);
    }

}
