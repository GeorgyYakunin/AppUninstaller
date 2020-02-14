package com.app.uninstaller.appuninstaller212.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.uninstaller.appuninstaller212.ITotalSize;
import com.app.uninstaller.appuninstaller212.R;
import com.app.uninstaller.appuninstaller212.dao.HistoryDAO;
import com.app.uninstaller.appuninstaller212.model.PInfo;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LoadAppAdapter extends RecyclerView.Adapter<LoadAppAdapter.ViewHolderLoadApp> implements View.OnClickListener {

    private Context context;
    private int layout;
    private List<PInfo> list;
    private Dialog mDialog, mDialogUn;
    private ImageView mImageIcon, mImageIconUn;
    private TextView mTxtAppName, mTxtTruyCap, txtSearchGP, mTxtChiTiet, mTxtGoCaiDat;
    private TextView mTxtNoiDungUn, mTxtAppNameUn, mTxtHuyUn, mTxtOkUn;
    private boolean checkApp;
    private int UNINSTALL_REQUEST_CODE = 1;
    private HistoryDAO historyDAO;
    ITotalSize totalSize;

    public LoadAppAdapter(Context context, int layout, List<PInfo> list, boolean checkApp, ITotalSize iTotalSize) {
        this.context = context;
        this.layout = layout;
        this.list = list;
        this.checkApp = checkApp;
        this.totalSize = iTotalSize;
    }

    @Override
    public void onClick(View view) {
    }

    class ViewHolderLoadApp extends RecyclerView.ViewHolder {
        ImageView iconApp, imgMore;
        TextView appName, txtSize, txtDate;
        CheckBox cbItem;

        ViewHolderLoadApp(View itemView) {
            super(itemView);
            iconApp = itemView.findViewById(R.id.iconApp);
            appName = itemView.findViewById(R.id.appName);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtDate = itemView.findViewById(R.id.txtDate);
            imgMore = itemView.findViewById(R.id.imgMore);
            cbItem = itemView.findViewById(R.id.cbItem);
        }
    }

    double tong = 0;
    int dem = 0;
    List<PInfo> listDelete = new ArrayList<>();

    @Override
    public ViewHolderLoadApp onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolderLoadApp(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolderLoadApp holder, final int position) {
        final PInfo pInfo = list.get(position);

        float size = pInfo.getSize();

        // tong += Double.parseDouble(size);
        /// start check box
        if (pInfo.getStatus().equals("true")) {
            holder.cbItem.setChecked(true);
        } else if (pInfo.getStatus().equals("false")) {
            holder.cbItem.setChecked(false);
        }

        final float finalSize = size;
        holder.cbItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pInfo.getStatus().equals("true")) {
                    tong -= finalSize;
                    dem--;
                    listDelete.remove(pInfo);
                    totalSize.total(dem, tong, listDelete);
                    pInfo.setStatus("false");
                } else if (pInfo.getStatus().equals("false")) {
                    tong += finalSize;
                    dem++;
                    listDelete.add(pInfo);
                    totalSize.total(dem, tong, listDelete);
                    pInfo.setStatus("true");
                }
            }
        });
        /// end check box
        String name = pInfo.getAppname();
        String version = pInfo.getVersionName();
        if (name.length() > 20) {
            name = name.substring(0, 15);
        }
        if (version.length() > 10) {
            version = version.substring(0, 6);
        }
        holder.appName.setText(name + " " + version);

        holder.iconApp.setImageDrawable(pInfo.getIcon());

        DecimalFormat df = new DecimalFormat("#.##");
        String s = df.format(pInfo.getSize());
        holder.txtSize.setText( s+ " MB");

        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        String strDate = dateFormat.format(pInfo.getDate());


        holder.txtDate.setText(strDate + "");

        holder.cbItem.setOnCheckedChangeListener(null);
        final String finalName = name;
        if (!checkApp) {
            holder.cbItem.setVisibility(View.GONE);
        }

        holder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog = new Dialog(context);
                mDialog.setContentView(R.layout.dialoag__layout_item);
                mImageIcon = mDialog.findViewById(R.id.imgIcon);
                mTxtAppName = mDialog.findViewById(R.id.txtNameApp);
                mTxtTruyCap = mDialog.findViewById(R.id.txtTruyCap);
                txtSearchGP = mDialog.findViewById(R.id.txtSearchGP);
                mTxtChiTiet = mDialog.findViewById(R.id.txtChiTiet);
                mTxtGoCaiDat = mDialog.findViewById(R.id.txtGoCaiDat);
                if (!checkApp) {
                    mTxtGoCaiDat.setVisibility(View.GONE);
                    txtSearchGP.setVisibility(View.GONE);
                }
                holder.cbItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    }
                });
                mTxtGoCaiDat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.cancel();
                        mDialogUn = new Dialog(context);
                        mDialogUn.setContentView(R.layout.dialog_uninstall_item);
                        mImageIconUn = mDialogUn.findViewById(R.id.imgIconUn);
                        mTxtAppNameUn = mDialogUn.findViewById(R.id.txtNameAppUn);
                        mTxtNoiDungUn = mDialogUn.findViewById(R.id.txtNoiDungUn);
                        mTxtHuyUn = mDialogUn.findViewById(R.id.txtHuy);
                        mTxtOkUn = mDialogUn.findViewById(R.id.txtOK);

                        mImageIconUn.setImageDrawable(pInfo.getIcon());
                        mTxtAppNameUn.setText(finalName + " " + pInfo.getVersionName());
                        mTxtNoiDungUn.setText(finalName + " " + context.getResources().getString(R.string.se_bi_go));

                        mTxtHuyUn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mDialogUn.cancel();
                            }
                        });
                        mTxtOkUn.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(View view) {
                                historyDAO = new HistoryDAO(context);
                                mDialogUn.cancel();
                                Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
                                intent.setData(Uri.parse("package:" + pInfo.getPname()));
                                intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
                                ((Activity) context).startActivityForResult(intent, UNINSTALL_REQUEST_CODE);
                                PInfo p = new PInfo();
                                p.setPname(pInfo.getPname());
                                p.setAppname(pInfo.getAppname());

                                Date date = Calendar.getInstance().getTime();

                                DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                                String strDate = dateFormat.format(date);
                                p.setDateUn(strDate);

                                p.setIcon(pInfo.getIcon());
                                try {
                                    historyDAO.addHistory(p);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        mDialogUn.show();
                    }
                });
                mImageIcon.setImageDrawable(pInfo.getIcon());
                mTxtAppName.setText(finalName + " " + pInfo.getVersionName());

                mTxtTruyCap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(pInfo.getPname());
                        if (launchIntent != null) {
                            context.startActivity(launchIntent);//null pointer check in case package name was not found
                        }
                    }
                });
                txtSearchGP.setOnClickListener(new View.OnClickListener() {
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
                mTxtChiTiet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        i.addCategory(Intent.CATEGORY_DEFAULT);
                        i.setData(Uri.parse("package:" + pInfo.getPname()));
                        context.startActivity(i);
                    }
                });
                mDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}