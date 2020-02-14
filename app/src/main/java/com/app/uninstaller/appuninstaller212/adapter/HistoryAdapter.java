package com.app.uninstaller.appuninstaller212.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.uninstaller.appuninstaller212.R;
import com.app.uninstaller.appuninstaller212.activity.ActivityUninstallHistory;
import com.app.uninstaller.appuninstaller212.dao.HistoryDAO;
import com.app.uninstaller.appuninstaller212.model.PInfo;
import com.app.uninstaller.appuninstaller212.utils.GetDays;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewholderHistory> {

    private Context context;
    private int layout;
    private List<PInfo> list;
    private Dialog mDialogDelete;
    private TextView txtHuy, txtDongY;
    private HistoryDAO historyDAO;


    public HistoryAdapter(Context context, int layout, List<PInfo> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
        historyDAO = new HistoryDAO(context);

    }

    class ViewholderHistory extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView txtName, txtDate;
        LinearLayout lnHistory;

        ViewholderHistory(View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.iconHistory);
            txtName = itemView.findViewById(R.id.txtNameHistory);
            txtDate = itemView.findViewById(R.id.txtDateHistory);
            lnHistory = itemView.findViewById(R.id.lnHistory);
        }
    }

    @Override
    public ViewholderHistory onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewholderHistory(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewholderHistory holder, int position) {
        final PInfo pInfo = list.get(position);


        Date dateToDay = GetDays.stringToDate(GetDays.getDayHere());
        Date dateUnInstall = GetDays.stringToDate(pInfo.getDateUn());

        Log.d("dateUnInstall", dateUnInstall.toString());
        long getDiff = dateToDay.getTime() - dateUnInstall.getTime();
        long time = getDiff / (24 * 60 * 60 * 1000);

        if (time <= 90) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            String strDate = dateFormat.format(dateUnInstall);

            holder.txtDate.setText(context.getResources().getString(R.string.go_cai_dat_luc) + " " + strDate);
            holder.txtName.setText(pInfo.getAppname());
            holder.imgIcon.setImageDrawable(pInfo.getIcon());
        } else {
            historyDAO.deleteHistory(pInfo.getId());
            Intent intent = new Intent(context, ActivityUninstallHistory.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            context.startActivity(intent);
        }

        holder.lnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = context.getPackageManager().getLaunchIntentForPackage(pInfo.getPname());
                if (intent == null) {
                    // Bring user to the market or let them choose an app?
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=" + pInfo.getPname()));
                }
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + pInfo.getPname()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.lnHistory.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mDialogDelete = new Dialog(context);
                mDialogDelete.setContentView(R.layout.dialog_delete_item);
                txtDongY = mDialogDelete.findViewById(R.id.txtDongY);
                txtHuy = mDialogDelete.findViewById(R.id.txtHuy);

                txtHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialogDelete.cancel();
                    }
                });
                txtDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean kt = historyDAO.deleteHistory(pInfo.getId());
                        if (kt) {
                            Toast.makeText(context, context.getResources().getString(R.string.xoa_thanh_cong), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, ActivityUninstallHistory.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            context.startActivity(intent);
                            mDialogDelete.cancel();
                        } else {
                            Toast.makeText(context, context.getResources().getString(R.string.xoa_that_bai), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                mDialogDelete.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
