package com.app.uninstaller.appuninstaller212.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.uninstaller.appuninstaller212.databases.CreateDatabases;
import com.app.uninstaller.appuninstaller212.model.SettingModel;

public class SettingDAO {

    private SQLiteDatabase database;

    public SettingDAO(Context context) {
        CreateDatabases createDatabases = new CreateDatabases(context);
        database = createDatabases.open();
    }

    public boolean addSetting(SettingModel settingModel) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(CreateDatabases.TB_SETTING_MODE, settingModel.getMode());
        contentValues.put(CreateDatabases.TB_SETTING_STATUSBAR, settingModel.getStatusBar());
        contentValues.put(CreateDatabases.TB_SETTING_STATUSBAR_ICON, settingModel.getStatusBarIcon());

        long kt = database.insert(CreateDatabases.TB_SETTING, null, contentValues);
        if (kt != 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateStatusBarICON(String check) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabases.TB_SETTING_STATUSBAR_ICON, check);

        long kt = database.update(CreateDatabases.TB_SETTING, contentValues, null, null);

        if (kt != 0) {
            return false;
        } else {
            return true;
        }
    }
    public boolean updateStatusBar(String check) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabases.TB_SETTING_STATUSBAR, check);

        long kt = database.update(CreateDatabases.TB_SETTING, contentValues, null, null);

        if (kt != 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateMode(String check) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabases.TB_SETTING_MODE, check);

        long kt = database.update(CreateDatabases.TB_SETTING, contentValues, null, null);

        if (kt != 0) {
            return false;
        } else {
            return true;
        }
    }

    public SettingModel getSetting() {
        SettingModel setting = new SettingModel();
        String sql = "select * from " + CreateDatabases.TB_SETTING;

        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        setting.setId(cursor.getInt(cursor.getColumnIndex(CreateDatabases.TB_SETTING_ID)));
        setting.setMode(cursor.getString(cursor.getColumnIndex(CreateDatabases.TB_SETTING_MODE)));
        setting.setStatusBar(cursor.getString(cursor.getColumnIndex(CreateDatabases.TB_SETTING_STATUSBAR)));
        setting.setStatusBarIcon(cursor.getString(cursor.getColumnIndex(CreateDatabases.TB_SETTING_STATUSBAR_ICON)));

        return setting;
    }

    public int count() {
        String sql = "select * from " + CreateDatabases.TB_SETTING;

        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(sql, null);
        return cursor.getCount();
    }
}
