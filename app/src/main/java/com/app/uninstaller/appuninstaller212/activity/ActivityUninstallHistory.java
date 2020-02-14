package com.app.uninstaller.appuninstaller212.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.uninstaller.appuninstaller212.R;
import com.app.uninstaller.appuninstaller212.adapter.HistoryAdapter;
import com.app.uninstaller.appuninstaller212.ads.ActivityMyBase;
import com.app.uninstaller.appuninstaller212.dao.HistoryDAO;
import com.app.uninstaller.appuninstaller212.model.PInfo;

import java.text.ParseException;
import java.util.List;

public class ActivityUninstallHistory extends ActivityMyBase implements View.OnClickListener {
    ImageView mImageBack, mImageDelete;
    RecyclerView mRecyclerView;
    HistoryAdapter adapter;
    List<PInfo> list;
    Dialog mDialogDelete;
    HistoryDAO historyDAO;
    TextView mTxtHuy, mTxtDongY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_uninstall_history);
        mImageBack = findViewById(R.id.imgBack);
        mImageDelete = findViewById(R.id.imgDelete);
        mRecyclerView = findViewById(R.id.recycleHistory);

        historyDAO = new HistoryDAO(this);
        try {
            list = historyDAO.getAllHistory();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        adapter = new HistoryAdapter(this, R.layout.item_history, list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);

        mImageDelete.setOnClickListener(this);
        mImageBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgDelete:

                mDialogDelete = new Dialog(this);
                mDialogDelete.setContentView(R.layout.dialog_delete_all_items);
                mTxtDongY = mDialogDelete.findViewById(R.id.txtDongY);
                mTxtHuy = mDialogDelete.findViewById(R.id.txtHuy);

                mTxtHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialogDelete.cancel();
                    }
                });
                mTxtDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean kt = historyDAO.deleteAll();
                        if (kt) {
                            Toast.makeText(ActivityUninstallHistory.this, getResources().getString(R.string.xoa_thanh_cong), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ActivityUninstallHistory.this, ActivityUninstallHistory.class));
                            finish();
                            mDialogDelete.cancel();
                        } else {
                            Toast.makeText(ActivityUninstallHistory.this, getResources().getString(R.string.xoa_that_bai), Toast.LENGTH_LONG).show();
                            mDialogDelete.cancel();
                        }
                    }
                });
                mDialogDelete.show();
                break;
        }
    }

}