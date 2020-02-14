package com.app.uninstaller.appuninstaller212.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDays {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String  getDayHere(){
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        return dateFormat.format( date);
    }
    public static Date stringToDate(String dtStart){
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        try {
            date = format.parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date stringToDate1(String dtStart){
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat();
        try {
            date = format.parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("KiemTraDate",e.toString());
        }
        return date;
    }



}
