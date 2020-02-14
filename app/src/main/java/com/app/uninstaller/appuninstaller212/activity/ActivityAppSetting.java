package com.app.uninstaller.appuninstaller212.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.uninstaller.appuninstaller212.NotificationListener;
import com.app.uninstaller.appuninstaller212.R;
import com.app.uninstaller.appuninstaller212.ads.ActivityMyBase;
import com.app.uninstaller.appuninstaller212.dao.SettingDAO;
import com.app.uninstaller.appuninstaller212.model.SettingModel;

public class ActivityAppSetting extends ActivityMyBase implements View.OnClickListener {
    ImageView mImageBack, mImageStatusBar, imgStatusBarIcon;
    Toolbar mToolbar;
    ImageView mImageMode;
    TextView mTxtMode, txtStatusBar, txtStatusBarIcon;
    String checkMode, checkStatusBar, checkStatusBarIcon;
    SettingDAO settingDAO;
    LinearLayout lnStatusBarIcon;
    Intent intentService;
    int tongApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_app_settings);
        mImageBack = findViewById(R.id.imgBack);
        mToolbar = findViewById(R.id.toolBar);
        mImageMode = findViewById(R.id.imgMode);
        mImageStatusBar = findViewById(R.id.imgStatusBar);
        txtStatusBar = findViewById(R.id.txtStatusBar);
        lnStatusBarIcon = findViewById(R.id.lnStatusBarIcon);
        imgStatusBarIcon = findViewById(R.id.imgStatusBarIcon);
        txtStatusBarIcon = findViewById(R.id.txtStatusBarIcon);

        mToolbar.setTitle(getResources().getString(R.string.cai_dat));
        settingDAO = new SettingDAO(this);
        mTxtMode = findViewById(R.id.txtMode);
        intentService = new Intent(this, NotificationListener.class);
        tongApp = ActivityMain.iAppSystem + ActivityMain.iAppUser;

        Log.d("KiemTraSetting", tongApp + "");

        updateStatus();

        mImageBack.setOnClickListener(this);
        mImageMode.setOnClickListener(this);
        mImageStatusBar.setOnClickListener(this);
        imgStatusBarIcon.setOnClickListener(this);
    }

    private void updateStatus() {

        SettingModel settingModel = settingDAO.getSetting();

        // Xử lý Statusbar

        checkStatusBar = settingModel.getStatusBar();
        if (checkStatusBar.equals("true")) {
            txtStatusBar.setText(getResources().getString(R.string.kich_hoat));
            mImageStatusBar.setImageResource(R.drawable.ic_on);
            lnStatusBarIcon.setVisibility(View.VISIBLE);
        } else if (checkStatusBar.equals("false")) {
            mImageStatusBar.setImageResource(R.drawable.ic_off);
            txtStatusBar.setText(getResources().getString(R.string.ngung_kich_hoat));
            lnStatusBarIcon.setVisibility(View.GONE);
        }
        // Xử lý Statusbar Icon
        checkStatusBarIcon = settingModel.getStatusBarIcon();
        if (checkStatusBarIcon.equals("true")) {
            txtStatusBarIcon.setText(getResources().getString(R.string.kich_hoat));
            imgStatusBarIcon.setImageResource(R.drawable.ic_on);

            // Start Service
            intentService.putExtra("CancelNotification","OK");
            intentService.putExtra("TongApp",tongApp);
            intentService.putExtra("BoNhoTrong", ActivityMain.s);
            intentService.putExtra("TongBoNho", ActivityMain.s1);
           sendBroadcast(intentService);
        } else if (checkStatusBarIcon.equals("false")) {
            txtStatusBarIcon.setText(getResources().getString(R.string.ngung_kich_hoat));
            imgStatusBarIcon.setImageResource(R.drawable.ic_off);

            // Stop service
            intentService.putExtra("CancelNotification","Cancel");
            sendBroadcast(intentService);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgMode:
                break;
            case R.id.imgStatusBar:
                if (checkStatusBar.equals("true")) {
                    settingDAO.updateStatusBar("false");
                    settingDAO.updateStatusBarICON("false");
                    updateStatus();
                } else if (checkStatusBar.equals("false")) {
                    settingDAO.updateStatusBar("true");
                    settingDAO.updateStatusBarICON("true");
                    updateStatus();
                }
                break;
            case R.id.imgStatusBarIcon:

                if (checkStatusBarIcon.equals("true")) {
                    settingDAO.updateStatusBarICON("false");
                    intentService.putExtra("CancelNotification","Cancel");
                    sendBroadcast(intentService);
                    updateStatus();
                } else if (checkStatusBarIcon.equals("false")) {
                    intentService.putExtra("CancelNotification","OK");
                    intentService.putExtra("TongApp",tongApp);
                    intentService.putExtra("BoNhoTrong", ActivityMain.s);
                    intentService.putExtra("TongBoNho", ActivityMain.s1);
                    ContextCompat.startForegroundService(this, intentService);
                    sendBroadcast(intentService);
                    settingDAO.updateStatusBarICON("true");
                    updateStatus();
                }
                break;
        }
    }
}
