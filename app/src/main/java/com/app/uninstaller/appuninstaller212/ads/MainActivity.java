package com.app.uninstaller.appuninstaller212.ads;

import android.os.Bundle;



public abstract class MainActivity extends ActivityMyBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        MyAdsController.setTypeAds(getApplicationContext());

//        MyAdsController.handleInterstitialAds(this);

//        MyAdsController.listenNetworkChangeToRequestAdsFull(this);
    }

    @Override
    protected void onDestroy() {

//        MyAdsController.releaseAds_Callbacks();

        super.onDestroy();
    }
}
