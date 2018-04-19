package com.example.android_dev.qrtest.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.IStoryRepository;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.AppMenuItem;
import com.example.android_dev.qrtest.model.Role;
import com.example.android_dev.qrtest.presenter.appMenu.AppMenuFragmentPresenter;
import com.example.android_dev.qrtest.ui.adapter.AppMenuRVAdapter;
import com.example.android_dev.qrtest.util.GlobalNames;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AppMenuFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private CircleImageView iconPerson;
    private TextView personName;
    private List<AppMenuItem> itemList;
    private OnFragmentItemClickListener fragmentItemClickListener;
    private Role role;

    public void setItemList(List<AppMenuItem> itemList) {
        this.itemList = itemList;
    }

    public void setFragmentItemClickListener(OnFragmentItemClickListener fragmentItemClickListener) {
        this.fragmentItemClickListener = fragmentItemClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_app_menu, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fam_recycler_view);
        personName = (TextView) view.findViewById(R.id.fam_role_name);
        iconPerson = (CircleImageView) view.findViewById(R.id.fam_role_icon);
        IStoryRepository iStoryRepository = new InMemoryStoryRepository();
        role = iStoryRepository.getSelectedRole();
        AppMenuFragmentPresenter appMenuFragmentPresenter = new AppMenuFragmentPresenter(iStoryRepository);
        String personText = getString(R.string.your_person_name_text) + role.getName();
        personName.setText(personText);
        iconPerson.setImageBitmap(appMenuFragmentPresenter.getIconBitmap());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), GlobalNames.DEFAULT_GRID_COLUMN_COUNT);
        gridLayoutManager.generateLayoutParams(container.getLayoutParams());

        AppMenuRVAdapter appMenuRVAdapter = new AppMenuRVAdapter(itemList, new AppMenuRVAdapter.OnItemClickListener() {
            @Override
            public void onClick(AppMenuItem menuItem) {
                fragmentItemClickListener.onClick(menuItem);
            }
        });
        recyclerView.setAdapter(appMenuRVAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        return view;
    }


    public interface OnFragmentItemClickListener {
        void onClick(AppMenuItem appMenuItem);
    }


}