package com.example.android_dev.qrtest.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
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
import com.example.android_dev.qrtest.model.AppMenuItem;
import com.example.android_dev.qrtest.util.GlobalNames;

import java.util.List;

public class AppMenuRVAdapter extends RecyclerView.Adapter<AppMenuRVAdapter.MenuViewHolder> {
    private final double calculationPercent = 9.4;
    private ViewGroup.LayoutParams deafLayoutParams;
    private List<AppMenuItem> appMenuItemList;
    private OnItemClickListener onItemClickListener;
    private Context mContext;


    public AppMenuRVAdapter(List<AppMenuItem> appMenuItemList, OnItemClickListener onItemClickListener) {
        this.appMenuItemList = appMenuItemList;
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_app_menu_list, viewGroup, false);
        mContext = v.getContext();
        MenuViewHolder holder = new MenuViewHolder(v);
        return holder;
    }

    private void calculateLayoutParams(MenuViewHolder holder) {
        int optimisationPixelCount = (int) (getMaximumWeight() * calculationPercent) / 100;
        int width = getMaximumWeight() / GlobalNames.DEFAULT_GRID_COLUMN_COUNT - optimisationPixelCount;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);

        deafLayoutParams = holder.layout.getLayoutParams();
        deafLayoutParams.height = layoutParams.height;
        deafLayoutParams.width = layoutParams.width;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MenuViewHolder holder, int i) {
        final int position = i;
        if (deafLayoutParams == null) {
            calculateLayoutParams(holder);
        }
        holder.layout.setLayoutParams(deafLayoutParams);
        holder.icon.setBackground(appMenuItemList.get(position).getItemIcon());
        holder.name.setText(appMenuItemList.get(position).getItemName());
        if (!appMenuItemList.get(position).isActive()) {
            holder.layout.setBackgroundResource(R.drawable.menu_border_disable);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.layout.setElevation(0);
            }
        } else {
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(appMenuItemList.get(position));
                }
            });
        }


    }

    private Integer getMaximumWeight() {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }

    @Override
    public int getItemCount() {
        return appMenuItemList.size();
    }


    public interface OnItemClickListener {
        void onClick(AppMenuItem appMenuItem);
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layout;
        private ImageView icon;
        private TextView name;

        private MenuViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.iaml_fragment_icon);
            name = (TextView) itemView.findViewById(R.id.iaml_fragment_name);
            layout = (RelativeLayout) itemView.findViewById(R.id.iaml_fragment_layout);
        }
    }
}