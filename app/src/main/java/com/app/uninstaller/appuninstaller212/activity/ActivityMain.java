package com.app.uninstaller.appuninstaller212.activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.uninstaller.appuninstaller212.NotificationListener;
import com.app.uninstaller.appuninstaller212.R;
import com.app.uninstaller.appuninstaller212.RepeatNotification;
//import com.app.uninstaller.appuninstaller212.ads.MyAdsController;
import com.app.uninstaller.appuninstaller212.ads.MainActivity;
import com.app.uninstaller.appuninstaller212.dao.SettingDAO;
import com.app.uninstaller.appuninstaller212.databases.CreateDatabases;
import com.app.uninstaller.appuninstaller212.fragment.FragmentAppSystem;
import com.app.uninstaller.appuninstaller212.fragment.FragmentAppUser;
import com.app.uninstaller.appuninstaller212.model.SettingModel;
import com.app.uninstaller.appuninstaller212.utils.GetAppName;
import com.app.uninstaller.appuninstaller212.utils.Memory;

import java.util.Calendar;
import java.util.Date;

public class ActivityMain extends MainActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    NavigationView mNavigationView;
    PendingIntent pendingIntent;
    DrawerLayout mDrawerLayout;
    ImageView mImageMenu;
    Dialog mDialogThongTin, mDialogMore, mDialogSort;
    TextView mTxtChinhSachBaoMat, mTxtEmail, mTxtOk, mTxtTacGia, mTxtAppUse, mTxtAppSystem;
    TextView mTxtSort, mTxtOK, mTxtHuy, mTxtCaiDat, mTxtChonTatCa, mTxtBoNhoTrong;
    LinearLayout mLnAppUser, mLnAppSystem, mLnTitle;
    FrameLayout mFrameLayout, mFrameLayoutSearch;
    FragmentManager mFragmentManager;
    RadioGroup mGroupSort, mGroupDESC;
    LinearLayout ln1, lnKhung;
    public static String sSort = "NAME";
    public static String sDesc = "AZ";
    public static ImageView mImageDelete, mImageMore, mImageSearch;
    public static int iAppUser;
    public static int iAppSystem;

    public static String s;
    public static String s1;
    SharedPreferences pre;
    SharedPreferences.Editor edit;
    CreateDatabases createDatabases;
    @SuppressLint("StaticFieldLeak")
    public static EditText edtTimKiem;
    @SuppressLint("StaticFieldLeak")
    public static ImageView imgClose;
    public static TextView mTxtTitle, mTxtTongApp;
    ProgressBar progressBar;
    SettingDAO settingDAO;

    @SuppressLint({"SetTextI18n", "CommitPrefEdits"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        mNavigationView = findViewById(R.id.NavigationViewTrangChu);
        mNavigationView.setItemIconTintList(null);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mImageMenu = findViewById(R.id.imgMenu);
        mLnAppSystem = findViewById(R.id.lnAppSystem);
        mLnAppUser = findViewById(R.id.lnAppUser);
        mTxtAppSystem = findViewById(R.id.txtAppSystem);
        mTxtAppUse = findViewById(R.id.txtAppUser);
        mFrameLayout = findViewById(R.id.frameLayout);
        mImageMore = findViewById(R.id.imgMore);
        mTxtTongApp = findViewById(R.id.txtTongApp);
        edtTimKiem = findViewById(R.id.edtTimKiem);
        imgClose = findViewById(R.id.imgClose);
        mImageSearch = findViewById(R.id.imgSearch);
        mLnTitle = findViewById(R.id.lnTitle);
        mFrameLayoutSearch = findViewById(R.id.frameLayoutSearch);
        mTxtBoNhoTrong = findViewById(R.id.txtBoNhoTrong);
        mTxtTitle = findViewById(R.id.txtTitle);
        mImageDelete = findViewById(R.id.imgDelete);
        progressBar = findViewById(R.id.progressBar);
        ln1 = findViewById(R.id.ln1);
        lnKhung = findViewById(R.id.lnKhung);

        mImageMenu.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
        mLnAppUser.setOnClickListener(this);
        mLnAppSystem.setOnClickListener(this);
        mImageMore.setOnClickListener(this);
        imgClose.setOnClickListener(this);
        mImageSearch.setOnClickListener(this);
        //  getPackages();

        mFragmentManager = getSupportFragmentManager();

        pre = getSharedPreferences("SORTAPP", MODE_PRIVATE);
        edit = pre.edit();

        createDatabases = new CreateDatabases(this);
        createDatabases.open();

        backgroundThread.start();

        settingDAO = new SettingDAO(this);

        if (settingDAO.count() == 0) {
            SettingModel settingModel = new SettingModel();
            settingModel.setMode("false");
            settingModel.setStatusBar("true");
            settingModel.setStatusBarIcon("true");

            settingDAO.addSetting(settingModel);
        }

        Intent intent = new Intent(this, RepeatNotification.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        pushNotification();


    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onResume() {
        super.onResume();
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {

            mTxtAppUse.setText(getResources().getString(R.string.ung_dung_ng_dung) + "(" + iAppUser + ")");
            mTxtAppSystem.setText(getResources().getString(R.string.ung_dung_he_thong) + "(" + iAppSystem + ")");
            mTxtTongApp.setText(getResources().getString(R.string.tong) + " " + iAppUser + "+" + iAppSystem + " " + getResources().getString(R.string.ung_dung));
            showDefault();

            progressBar.setVisibility(View.GONE);
            ln1.setVisibility(View.VISIBLE);
            mFrameLayout.setVisibility(View.VISIBLE);


            float f1 = Float.parseFloat(Memory.formatSize(Memory.getAvailableInternalMemorySize()));
            float f2 = Float.parseFloat(Memory.formatSize(Memory.getTotalInternalMemorySize()));
            s = String.format("%.2f", f1);
             s1 = String.format("%.2f", f2);

            mTxtBoNhoTrong.setText(getResources().getString(R.string.bo_nho_trong) + " "
                    + s + " GB" + "/" + s1 + " GB");


            SettingModel settingModel = settingDAO.getSetting();
            if(settingModel.getStatusBarIcon().equals("true")){
                Intent iNotification = new Intent(ActivityMain.this, NotificationListener.class);
               // pendingIntentNotifi = PendingIntent.getBroadcast(ActivityMain.this, 0, iNotification, PendingIntent.FLAG_UPDATE_CURRENT);
                int tongApp = iAppSystem + iAppUser;
                iNotification.putExtra("CancelNotification","OK");
                iNotification.putExtra("BoNhoTrong", s);
                iNotification.putExtra("TongBoNho", s1);
                iNotification.putExtra("TongApp", tongApp);

                sendBroadcast(iNotification);


            }

        }
    };

    Thread backgroundThread = new Thread(new Runnable() {
        @Override
        public void run() {
            iAppUser = GetAppName.getAppUse(ActivityMain.this).size();
            iAppSystem = GetAppName.getAppSystem(ActivityMain.this).size();

            //handler.postDelayed(runnable, 1000);
            handler.post(runnable);
        }
    });

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgMore:
                mDialogMore = new Dialog(this);
                mDialogMore.setContentView(R.layout.dialog_more_info);
                mTxtSort = mDialogMore.findViewById(R.id.txtSort);
                mTxtCaiDat = mDialogMore.findViewById(R.id.txtCaiDat);

                mTxtCaiDat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialogMore.cancel();

                                startActivity(new Intent(ActivityMain.this, ActivityAppSetting.class));


                    }
                });
                mTxtSort.setOnClickListener(this);
                mDialogMore.show();
                break;
            case R.id.imgMenu:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.txtChinhSachBaoMat:
                String url = getResources().getString(R.string.link_chinh_sach_bao_mat);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
//            case R.id.txtEmail:
////                Intent Email = new Intent(Intent.ACTION_SEND);
////                Email.setType("text/email");
////                Email.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.EmailFeedback)});
////                Email.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.feedback_app));
////                startActivity(Intent.createChooser(Email, getResources().getString(R.string.send_feesback)));
//                break;
            case R.id.txtOK:
                mDialogThongTin.cancel();
                break;
//            case R.id.txtTacGia:
////                String url1 = getResources().getString(R.string.link_home);
////                Intent i1 = new Intent(Intent.ACTION_VIEW);
////                i1.setData(Uri.parse(url1));
////                startActivity(i1);
//                break;
            case R.id.lnAppUser:
                showDefault();
                break;
            case R.id.lnAppSystem:
                mLnAppSystem.setBackgroundResource(R.drawable.bg_pink);
                mTxtAppSystem.setTextColor(getResources().getColor(R.color.color_while));

                mLnAppUser.setBackgroundResource(R.drawable.bg_white);
                mTxtAppUse.setTextColor(getResources().getColor(R.color.color_black));

                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                FragmentAppSystem fragmentAppSystem = new FragmentAppSystem();
                transaction.replace(R.id.frameLayout, fragmentAppSystem);
                transaction.commitAllowingStateLoss(); //DuyLH - fix crash report
                break;
            case R.id.txtSort:
                mDialogSort = new Dialog(this);
                mDialogSort.setContentView(R.layout.dialoag_sort_layout);
                mGroupSort = mDialogSort.findViewById(R.id.grSort);
                mGroupDESC = mDialogSort.findViewById(R.id.grDESC);
                mTxtOK = mDialogSort.findViewById(R.id.txtOK);
                mTxtHuy = mDialogSort.findViewById(R.id.txtHuy);

                mGroupSort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i) {
                            case R.id.rdTen:
                                sSort = "NAME";
                                break;
                            case R.id.rdNgay:
                                sSort = "DATE";
                                break;
                            case R.id.rdDungLuong:
                                sSort = "DUNGLUONG";
                                break;
                        }
                    }
                });
                mGroupDESC.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i) {
                            case R.id.rdAZ:
                                sDesc = "AZ";
                                break;
                            case R.id.rdZA:
                                sDesc = "ZA";
                                break;
                        }
                    }
                });
                mTxtOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edit.putString("SORT", sSort);
                        edit.putString("DESC", sDesc);
                        edit.commit();
                        finish();
                        startActivity(new Intent(ActivityMain.this, ActivityMain.class));
                        mDialogSort.cancel();
                        mDialogMore.cancel();
                    }
                });
                mDialogSort.show();
                break;
            case R.id.imgClose:
                edtTimKiem.setText("");
                break;
            case R.id.imgSearch:
                mFrameLayoutSearch.setVisibility(View.VISIBLE);
                mLnTitle.setVisibility(View.GONE);
                mImageSearch.setVisibility(View.GONE);
                break;
        }
    }

    private void showDefault() {
        mLnAppUser.setBackgroundResource(R.drawable.bg_pink);
        mTxtAppUse.setTextColor(getResources().getColor(R.color.color_while));

        mLnAppSystem.setBackgroundResource(R.drawable.bg_white);
        mTxtAppSystem.setTextColor(getResources().getColor(R.color.color_black));

        FragmentTransaction transactionAppUser = mFragmentManager.beginTransaction();
        FragmentAppUser fragmentAppUser = new FragmentAppUser();
        transactionAppUser.replace(R.id.frameLayout, fragmentAppUser);
        transactionAppUser.commitAllowingStateLoss(); //DuyLH - fix crash report
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itCaiDat:

                startActivity(new Intent(ActivityMain.this, ActivityAppSetting.class));

                break;
//            case R.id.itDanhGia:
//                Uri uri = Uri.parse("market://details?id=" + getPackageName());
//                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
//                // To count with Play market backstack, After pressing back button,
//                // to taken back to our application, we need to add following flags to intent.
//                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
//                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
//                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                try {
//                    startActivity(goToMarket);
//                } catch (ActivityNotFoundException e) {
//                    startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
//                }
//                break;
//            case R.id.itShare:
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
//                shareIntent.putExtra(Intent.EXTRA_TEXT, getPackageName());
//                startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.choose)));
//                break;
//            case R.id.itThongTin:
////                mDialogThongTin = new Dialog(this);
////                mDialogThongTin.setContentView(R.layout.dialog_in_and_for);
////                mTxtChinhSachBaoMat = mDialogThongTin.findViewById(R.id.txtChinhSachBaoMat);
////                mTxtEmail = mDialogThongTin.findViewById(R.id.txtEmail);
////                mTxtOk = mDialogThongTin.findViewById(R.id.txtOK);
////                mTxtTacGia = mDialogThongTin.findViewById(R.id.txtTacGia);
////
////                mTxtOk.setOnClickListener(this);
////                mTxtTacGia.setOnClickListener(this);
////                mTxtChinhSachBaoMat.setOnClickListener(this);
////                mTxtEmail.setOnClickListener(this);
////                mDialogThongTin.show();
//                break;
            case R.id.itLichSu:

                startActivity(new Intent(ActivityMain.this, ActivityUninstallHistory.class));

                break;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, getResources().getString(R.string.go_thanh_cong), Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(ActivityMain.this, ActivityMain.class));
            } else if (resultCode == RESULT_CANCELED) {
                Log.d("check", "");

            } else if (resultCode == RESULT_FIRST_USER) {
                Log.d("check", "");
            }
        }
    }

    private void pushNotification() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);

        if (calendar.getTime().compareTo(new Date()) < 0)
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

}
