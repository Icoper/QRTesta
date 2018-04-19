package com.example.android_dev.qrtest.presenter.appMenu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.android_dev.qrtest.db.IStoryRepository;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.model.Role;
import com.example.android_dev.qrtest.util.GlobalNames;

import java.io.File;

/**
 * Created by user on 18-Apr-18.
 */

public class AppMenuFragmentPresenter implements IAppMenuFragmentPresenter {
    private IStoryRepository iStoryRepository;

    public AppMenuFragmentPresenter(IStoryRepository iStoryRepository) {
        this.iStoryRepository = iStoryRepository;
    }

    @Override
    public Bitmap getIconBitmap() {
        IStory iStory = iStoryRepository.getSelectedStory();
        Role role = iStoryRepository.getSelectedRole();

        String imgPath = GlobalNames.ENVIRONMENT_STORE +
                iStory.getResFolderName() + "/Resource1";
        String imgName = "";
        for (int id : role.getInformationAssertIDList()) {
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
        return getBitMapByPath(imgPath, imgName);
    }

    private Bitmap getBitMapByPath(String path, String name) {
        File imgFile = new File(path, name);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;
        }
        return null;
    }
}
