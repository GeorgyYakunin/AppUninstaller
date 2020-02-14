package com.app.uninstaller.appuninstaller212.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.app.uninstaller.appuninstaller212.databases.CreateDatabases;
import com.app.uninstaller.appuninstaller212.model.PInfo;
import com.app.uninstaller.appuninstaller212.utils.GetDays;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryDAO {
    private SQLiteDatabase database;

    public HistoryDAO(Context context) {
        CreateDatabases createDatabases = new CreateDatabases(context);
        database = createDatabases.open();
    }

    public boolean addHistory(PInfo pInfo) throws IOException {
        ContentValues contentValues = new ContentValues();
        // contentValues.put(CreateDatabases.TB_HISTORY_ICON,pInfo.getIcon());
        contentValues.put(CreateDatabases.TB_HISTORY_NAME, pInfo.getAppname());
        contentValues.put(CreateDatabases.TB_HISTORY_DATE, pInfo.getDateUn());
        contentValues.put(CreateDatabases.TB_HISTORY_PACKAGE, pInfo.getPname());
        contentValues.put(CreateDatabases.TB_HISTORY_ICON, extractBytes(pInfo.getIcon()));

        long kt = database.insert(CreateDatabases.TB_HISTORY, null, contentValues);
        return kt != 0;
    }

    public boolean updateStatus(String status, int id) {
        status = "'" + status + "'";
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabases.TB_HISTORY_STATUS, status);
        long kt = database.update(CreateDatabases.TB_HISTORY, contentValues, CreateDatabases.TB_HISTORY_ID + " = " + id, null);
        if (kt != 0) {
            return true;
        }
        return false;
    }

    public List<PInfo> getAllHistory() throws ParseException {
        List<PInfo> list = new ArrayList<>();
        String query = "select * from " + CreateDatabases.TB_HISTORY + " ORDER BY " + CreateDatabases.TB_HISTORY_ID + " DESC ";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PInfo pInfo = new PInfo();

            pInfo.setId(cursor.getInt(cursor.getColumnIndex(CreateDatabases.TB_HISTORY_ID)));
            pInfo.setPname(cursor.getString(cursor.getColumnIndex(CreateDatabases.TB_HISTORY_PACKAGE)));


            pInfo.setDateUn(cursor.getString(cursor.getColumnIndex(CreateDatabases.TB_HISTORY_DATE)));

            pInfo.setAppname(cursor.getString(cursor.getColumnIndex(CreateDatabases.TB_HISTORY_NAME)));

            byte[] b = cursor.getBlob(cursor.getColumnIndex(CreateDatabases.TB_HISTORY_ICON));
            Drawable image = new BitmapDrawable(BitmapFactory.decodeByteArray(b, 0, b.length));

            pInfo.setIcon(image);
            list.add(pInfo);
            cursor.moveToNext();


        }
        return list;
    }

    public boolean deleteHistory(int id) {
        long kt = database.delete(CreateDatabases.TB_HISTORY, CreateDatabases.TB_HISTORY_ID + " = " + id, null);
        return kt != 0;
    }

    public boolean deleteAll() {
        long kt = database.delete(CreateDatabases.TB_HISTORY, null, null);
        return kt != 0;
    }

    private static byte[] extractBytes(Drawable image) throws IOException {
        // Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
        Bitmap bitmap = Bitmap.createBitmap(image.getIntrinsicWidth(), image.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        image.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        image.draw(canvas);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
}
