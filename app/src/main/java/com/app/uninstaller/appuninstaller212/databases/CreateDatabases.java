package com.app.uninstaller.appuninstaller212.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class CreateDatabases extends SQLiteOpenHelper {

    public static final String TB_HISTORY = "HISTORY";
    public static final String TB_HISTORY_ID = "ID";
    public static final String TB_HISTORY_NAME = "APPNAME";
    public static final String TB_HISTORY_ICON = "ICON";
    public static final String TB_HISTORY_DATE = "DATE";
    public static final String TB_HISTORY_PACKAGE = "PACKAGE_NAME";
    public static final String TB_HISTORY_STATUS = "STATUS";


    public static final String TB_SETTING = "SETTING";
    public static final String TB_SETTING_ID = "ID";
    public static final String TB_SETTING_MODE = "MODE";
    public static final String TB_SETTING_STATUSBAR = "STATUSBAR";
    public static final String TB_SETTING_STATUSBAR_ICON = "STATUSBAR_ICON";


    public CreateDatabases(@Nullable Context context) {
        super(context, "AppIntaller", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tbHis = "CREATE TABLE " + TB_HISTORY + " ( " + TB_HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                TB_HISTORY_NAME + " TEXT ," + TB_HISTORY_ICON + " text ," + TB_HISTORY_DATE + " TEXT ," +
                TB_HISTORY_STATUS + " text ," +
                TB_HISTORY_PACKAGE + " TEXT )";

        String tbSetting = "CREATE TABLE " + TB_SETTING + " ( " + TB_SETTING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                TB_SETTING_STATUSBAR + " TEXT , "
                +TB_SETTING_STATUSBAR_ICON+ " TEXT ,"
                + TB_SETTING_MODE + " TEXT )";

        sqLiteDatabase.execSQL(tbHis);
        sqLiteDatabase.execSQL(tbSetting);
        //  sqLiteDatabase.execSQL(tbStatus);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public SQLiteDatabase open() {
        return this.getReadableDatabase();
    }
}
