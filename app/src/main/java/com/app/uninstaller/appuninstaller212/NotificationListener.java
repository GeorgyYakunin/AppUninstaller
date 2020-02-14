package com.app.uninstaller.appuninstaller212;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.widget.RemoteViews;

import com.app.uninstaller.appuninstaller212.activity.ActivityMain;
import com.app.uninstaller.appuninstaller212.activity.ActivityAppSetting;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class NotificationListener extends BroadcastReceiver {

    private static final String CHANNEL_ID = "Channel2019";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intenMain = new Intent(context, ActivityMain.class);
        intenMain.setAction("intenMain");

        Intent intentSetting = new Intent(context, ActivityAppSetting.class);
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_customize_layout);

        String boNhoTrong = intent.getStringExtra("BoNhoTrong")+"GB";
        String tongBoNho = intent.getStringExtra("TongBoNho")+"GB";
        int tongApp = intent.getIntExtra("TongApp",0);

        contentView.setTextViewText(R.id.txtBoNhoTrongNoti,boNhoTrong+"/"+tongBoNho);
        contentView.setTextViewText(R.id.txtTongAppNoti,tongApp+ " "+context.getResources().getString(R.string.ung_dung));

        contentView.setOnClickPendingIntent(R.id.lnNotification, PendingIntent.getActivity(context,100,intenMain, 0));
        contentView.setOnClickPendingIntent(R.id.imgSettingNoti, PendingIntent.getActivity(context,10,intentSetting, 0));


        Notification.Builder builder = new Notification.Builder(context);

        Notification notification = builder.setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.notification))
                .setSmallIcon(R.drawable.icon)
                .setAutoCancel(true).setDefaults(0).setOngoing(false)
                .setContent(contentView).build();


        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification.bigContentView = contentView;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    context.getString(R.string.app_name),
                    IMPORTANCE_DEFAULT
            );
            channel.setDescription("no sound");
            channel.setSound(null, null);
            channel.enableLights(false);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(false);
            notificationManager.createNotificationChannel(channel);
        }

      //  notificationManager.notify(999,notification);


        String CancelNotification = intent.getStringExtra("CancelNotification");
        if(CancelNotification.equals("OK")){
            notificationManager.notify(999,notification);
        }else if(CancelNotification.equals("Cancel")){
            notificationManager.cancel(999);
        }

    }
}
