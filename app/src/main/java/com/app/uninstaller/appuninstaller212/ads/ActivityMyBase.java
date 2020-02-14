package com.app.uninstaller.appuninstaller212.ads;

import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;


public abstract class ActivityMyBase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected boolean isShowBannerAds = true;

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);

        if (isShowBannerAds) {
//            MyAdsController.showBannerAds(this);

            if (CheckInternet.isNetworkAvailable(getApplicationContext()))
                lastNetworkAvailable = true;

//            hCheckLoadBannerAds.post(rNetworkShowBannerAds);
        }

    }

    protected Handler hCheckLoadBannerAds = new Handler();

    boolean lastNetworkAvailable = false;

    Runnable rNetworkShowBannerAds = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub

            if (!lastNetworkAvailable
                    && CheckInternet.isNetworkAvailable(getApplicationContext())) {
//                MyAdsController.showBannerAds(ActivityMyBase.this);
            } else if (lastNetworkAvailable
                    && !CheckInternet.isNetworkAvailable(getApplicationContext())) {

            }

            if (CheckInternet.isNetworkAvailable(getApplicationContext())) {
                lastNetworkAvailable = true;
            } else {
                lastNetworkAvailable = false;
            }


            hCheckLoadBannerAds.postDelayed(rNetworkShowBannerAds, 4000);

        }
    };

    private boolean doubleBackToExitPressedOnce = false;


    public void onBackPressed() {

        hCheckLoadBannerAds.removeCallbacksAndMessages(null);

        if (!(this instanceof MainActivity)) {

            super.onBackPressed();


        } else {

            if (doubleBackToExitPressedOnce) {

//                MyAdsController.releaseAds_Callbacks();

                super.onBackPressed();
                return;
            }

            doubleBackToExitPressedOnce = true;

            String toast = "Press again to exit";
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();

            Handler h = new Handler();
            h.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);


        }
    }

}
