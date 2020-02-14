package com.app.uninstaller.appuninstaller212.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.app.uninstaller.appuninstaller212.model.PInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetAppName {

    public static ArrayList<PInfo> getInstalledApps(boolean getSysPackages, Context context) {
        ArrayList<PInfo> res = new ArrayList<PInfo>();
        List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(0);

        context.getPackageManager().getInstalledApplications(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue;
            }
            PInfo newInfo = new PInfo();
            newInfo.setAppname(p.applicationInfo.loadLabel(context.getPackageManager()).toString());
            newInfo.setPname(p.packageName);
            newInfo.setVersionName(p.versionName);
            newInfo.setVersionCode(p.versionCode);

            newInfo.setIcon(p.applicationInfo.loadIcon(context.getPackageManager()));
            res.add(newInfo);
        }
        return res;
    }
    public static ArrayList<PInfo> getAppSystem(Context context) {
        ArrayList<PInfo> res = new ArrayList<PInfo>();

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);

        for (ApplicationInfo app : apps) {
            PInfo pInfo = new PInfo();
            if (pm.getLaunchIntentForPackage(app.packageName) != null) {
                // apps with launcher intent
                if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 1) {
                    // updated system apps

                } else if ((app.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                    pInfo.setSize(getApkSize(context, app.packageName));

                    pInfo.setAppname(app.loadLabel(context.getPackageManager()).toString());
                    pInfo.setIcon(app.loadIcon(context.getPackageManager()));
                    pInfo.setPname(app.packageName);
                    pInfo.setStatus("false");

                    pInfo.setDate(getInstallTime(pm, app.packageName));                    // version name
                    PackageInfo eInfo = null;
                    try {
                        eInfo = context.getPackageManager().getPackageInfo(app.packageName, 0);
                        pInfo.setVersionName(eInfo.versionName);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    res.add(pInfo);
                } else {
                    // user installed apps
                }
            }
        }
        return res;
    }
    public static ArrayList<PInfo> getAppUse(Context context) {
        ArrayList<PInfo> res = new ArrayList<PInfo>();

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);

        for (ApplicationInfo app : apps) {
            PInfo pInfo = new PInfo();
            if (pm.getLaunchIntentForPackage(app.packageName) != null) {
                // apps with launcher intent
                if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 1) {
                    // updated system apps
                } else if ((app.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                    // system apps
                } else {
                    pInfo.setSize(getApkSize(context, app.packageName));
                    pInfo.setAppname(app.loadLabel(context.getPackageManager()).toString());

                    // Giảm dung lượng image
                    try {
                        Bitmap bitmap1;

                        Drawable drawable = app.loadIcon(context.getPackageManager());
                        bitmap1 = ((BitmapDrawable)drawable).getBitmap();

                        Drawable d = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap1, 50, 50, true));

                        pInfo.setIcon(d);
                    }catch (Exception e){
                        pInfo.setIcon(app.loadIcon(context.getPackageManager()));
                    }

                  //  pInfo.setIcon(app.loadIcon(context.getPackageManager()));
                    pInfo.setPname(app.packageName);
                    pInfo.setStatus("false");
                    pInfo.setDate(getInstallTime(pm, app.packageName));
                    // version name
                    PackageInfo eInfo = null;
                    try {
                        eInfo = context.getPackageManager().getPackageInfo(app.packageName, 0);
                        pInfo.setVersionName(eInfo.versionName);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    res.add(pInfo);
                }
            }
        }
        return res;
    }
    private static float getApkSize(Context context, String packageName) {
        try {
            float value = new File(context.getPackageManager().getApplicationInfo(packageName, 0).publicSourceDir).length() / 1048576F;

            if (value <= 0) {
                value = 1;
            }
            return value;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
       return 0;
    }

     private static  String formatDate(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("EE, dd/MM/yyyy");

         return dateFormat.format(date);
    }

     private static Date getInstallTime(
             PackageManager packageManager, String packageName) {

        return firstNonNull(
                installTimeFromPackageManager(packageManager, packageName),
                apkUpdateTime(packageManager, packageName));
    }
    private static Date apkUpdateTime(
            PackageManager packageManager, String packageName) {
        try {
            ApplicationInfo info = packageManager.getApplicationInfo(packageName, 0);
            File apkFile = new File(info.sourceDir);
            return apkFile.exists() ? new Date(apkFile.lastModified()) : null;
        } catch (PackageManager.NameNotFoundException e) {
            return null; // package not found
        }
    }

    private static Date installTimeFromPackageManager(
            PackageManager packageManager, String packageName) {
        try {
            PackageInfo info = packageManager.getPackageInfo(packageName, 0);
            Field field = PackageInfo.class.getField("firstInstallTime");
            long timestamp = field.getLong(info);
            return new Date(timestamp);
        } catch (PackageManager.NameNotFoundException e) {
            return null; // package not found
        } catch (IllegalAccessException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        // field wasn't found
        return null;
    }

    private static Date firstNonNull(Date... dates) {
        for (Date date : dates)
            if (date != null)
                return date;
        return null;
    }


}
