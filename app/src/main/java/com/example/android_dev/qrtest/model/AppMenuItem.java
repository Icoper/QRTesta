package com.example.android_dev.qrtest.model;

import android.app.Fragment;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by user on 11-Apr-18.
 */

public class AppMenuItem implements Serializable {
    private Fragment fragment;
    private Drawable itemIcon;
    private String itemName;
    private boolean isActive;

    public AppMenuItem(Fragment fragment, Drawable itemIcon, String itemName, boolean isActive) {
        this.fragment = fragment;
        this.itemIcon = itemIcon;
        this.itemName = itemName;
        this.isActive = isActive;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public Drawable getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(Drawable itemIcon) {
        this.itemIcon = itemIcon;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public AppMenuItem(Drawable itemIcon, String itemName, boolean isActive) {
        this.itemIcon = itemIcon;
        this.itemName = itemName;
        this.isActive = isActive;
    }
}
