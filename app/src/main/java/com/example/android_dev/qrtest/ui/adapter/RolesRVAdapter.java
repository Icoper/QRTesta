package com.example.android_dev.qrtest.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.IStoryRepository;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.model.Role;
import com.example.android_dev.qrtest.util.GlobalNames;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RolesRVAdapter extends RecyclerView.Adapter<RolesRVAdapter.RoleViewHolder> {
    private final IStoryRepository iStoryRepository;
    private IStory iStory;
    private List<Role> roles;
    private RolesRVAdapter.OnItemClickListener onItemClickListener;
    ;

    public RolesRVAdapter(IStory iStory, RolesRVAdapter.OnItemClickListener onItemClickListener, IStoryRepository iStoryRepository) {
        this.iStory = iStory;
        this.onItemClickListener = onItemClickListener;
        this.iStoryRepository = iStoryRepository;
        roles = iStory.getRoleList();

    }

    @Override
    public RolesRVAdapter.RoleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_role_list, viewGroup, false);
        RolesRVAdapter.RoleViewHolder storyViewHolder = new RolesRVAdapter.RoleViewHolder(v);
        return storyViewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(RolesRVAdapter.RoleViewHolder roleViewHolder, int i) {
        final int position = i;
//
//        if (iStoryRepository.getSelectedRole() != null){
//            Role activeRole = iStoryRepository.getSelectedRole();
//            if (activeRole.getId() == roles.get(position).getId()){
//                roleViewHolder.linearLayout.setBackgroundColor(R.color.colorAccent);
//            }
//        }

        String imgPath = GlobalNames.ENVIRONMENT_STORE +
                iStory.getResFolderName() + "/Resource1";

        String imgName = "";
        for (int id : roles.get(position).getInformationAssertIDList()) {
            if (imgName.isEmpty()) {
                for (AssetTypes assetType : iStory.getAssetTypeList()) {
                    if (assetType.getId() == id) {
                        if (assetType.getFileType().equals(GlobalNames.IMG_RES)) {
                            imgName = assetType.getFileName();
                            break;
                        }
                    }

                }
            }

        }

        roleViewHolder.icon.setImageBitmap(getBitMapByPath(imgPath, imgName));
        roleViewHolder.name.setText(roles.get(position).getName());
        roleViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(roles.get(position));
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
        return roles.size();
    }

    public class RoleViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CircleImageView icon;
        LinearLayout linearLayout;

        RoleViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.irl_role_name);
            icon = (CircleImageView) v.findViewById(R.id.irl_role_icon);
            linearLayout = (LinearLayout) v.findViewById(R.id.irl_item_layout);
        }
    }

    public interface OnItemClickListener {
        void onClick(Role role);
    }

}