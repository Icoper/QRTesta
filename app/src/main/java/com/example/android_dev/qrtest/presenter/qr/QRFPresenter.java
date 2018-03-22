package com.example.android_dev.qrtest.presenter.qr;

import android.content.Context;

import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.Actor;
import com.example.android_dev.qrtest.model.Story;
import com.example.android_dev.qrtest.util.GlobalNames;
import com.example.android_dev.qrtest.util.IQRFragment;
import com.example.android_dev.qrtest.util.SPHelper;

public class QRFPresenter implements IQRPresenter {
    private InMemoryStoryRepository inMemoryStoryRepository;
    private Context context;
    private IQRFragment iqrFragment;

    public QRFPresenter(Context context, IQRFragment iqrFragment) {
        this.context = context;
        this.iqrFragment = iqrFragment;
    }

    private InMemoryStoryRepository getStoryRepo() {
        if (inMemoryStoryRepository == null) {
            inMemoryStoryRepository = new InMemoryStoryRepository();
        }
        return inMemoryStoryRepository;
    }

    @Override
    public void checkCode(String code) {
        int alertMode = GlobalNames.QR_MODE_FIRST_SCAN;
        Story story = getStoryRepo().getSelectedStory();
        for (Actor a : story.getActors()) {
            if (a.getId().equals(code)) {
                String[] scannStoryArray = SPHelper.getScanHistory(context).split("/");
                for (String _code : scannStoryArray) {
                    if (_code.equals(code)) {
                        alertMode = GlobalNames.QR_MODE_SIMPLE_SCAN;
                    }
                }
                saveCodeInHistory(code);
                iqrFragment.showMsg("Found");
                iqrFragment.showAlertDialog(alertMode, GlobalNames.ALERT_MODE_SMALL_INFO);
                return;
            }
        }
        iqrFragment.showMsg("Not Found");
    }

    @Override
    public void changeAlertMode(int modeScan, int modeShow) {
        iqrFragment.showAlertDialog(modeScan, modeShow);
    }

    private void saveCodeInHistory(String code) {
        String history = SPHelper.getScanHistory(context);
        history += "/" + code;
        SPHelper.setScanHistory(context, history);
    }
}
