package com.app.uninstaller.appuninstaller212.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import android.os.Bundle;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.uninstaller.appuninstaller212.ITotalSize;
import com.app.uninstaller.appuninstaller212.R;
import com.app.uninstaller.appuninstaller212.activity.ActivityMain;
import com.app.uninstaller.appuninstaller212.adapter.LoadAppAdapter;
import com.app.uninstaller.appuninstaller212.dao.HistoryDAO;
import com.app.uninstaller.appuninstaller212.model.PInfo;
import com.app.uninstaller.appuninstaller212.utils.GetAppName;


import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class FragmentAppUser extends Fragment implements ITotalSize {
    RecyclerView recyclerAppUse;
    LoadAppAdapter appAdapter;
    SharedPreferences pre;
    String sSort = "NAME";
    String sDesc = "AZ";
    List<PInfo> list;
    List<PInfo> listTam;
    HistoryDAO historyDAO;
    boolean check = false;
    int size = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_app_user, container, false);
        recyclerAppUse = view.findViewById(R.id.recyclerAppUse);
        list = GetAppName.getAppUse(getContext());
        listTam = new ArrayList<>();
        listTam.addAll(list);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        list = GetAppName.getAppUse(getContext());
        setAdapter();
        pre = getActivity().getSharedPreferences("SORTAPP", Context.MODE_PRIVATE);
        sSort = pre.getString("SORT", "");
        sDesc = pre.getString("DESC", "");

        if (sDesc.equals("AZ")) {
            if (sSort.equals("NAME")) {
                Collections.sort(list, new Comparator<PInfo>() {
                    @Override
                    public int compare(PInfo p1, PInfo p2) {
                        String s1 = p1.getAppname().toUpperCase().trim();
                        String s2 = p2.getAppname().toUpperCase().trim();
                        s1 = removeAccent(s1);
                        s2 = removeAccent(s2);
                        return s1.compareToIgnoreCase(s2);
                    }
                });
            } else if (sSort.equals("DATE")) {
                Collections.sort(list, new Comparator<PInfo>() {
                    @Override
                    public int compare(PInfo p1, PInfo p2) {
                        return p1.getDate().compareTo(p2.getDate());
                    }
                });
            } else if (sSort.equals("DUNGLUONG")) {
                Collections.sort(list, new Comparator<PInfo>() {
                    @Override
                    public int compare(PInfo p1, PInfo p2) {
                        return p1.getSize().compareTo(p2.getSize());
                    }
                });
            }

        }else if(sDesc.equals("ZA")){
            if (sSort.equals("NAME")) {
                Collections.sort(list, new Comparator<PInfo>() {
                    @Override
                    public int compare(PInfo p1, PInfo p2) {
                        String s1 = p1.getAppname().toUpperCase().trim();
                        String s2 = p2.getAppname().toUpperCase().trim();
                        s1 = removeAccent(s1);
                        s2 = removeAccent(s2);
                        return s1.compareToIgnoreCase(s2);
                    }
                });
            } else if (sSort.equals("DATE")) {
                Collections.sort(list, new Comparator<PInfo>() {
                    @Override
                    public int compare(PInfo p1, PInfo p2) {
                        return p1.getDate().compareTo(p2.getDate());
                    }
                });
            } else if (sSort.equals("DUNGLUONG")) {
                Collections.sort(list, new Comparator<PInfo>() {
                    @Override
                    public int compare(PInfo p1, PInfo p2) {
                        return p1.getSize().compareTo(p2.getSize());
                    }
                });
            }
            Collections.reverse(list);
        }
        setAdapter();


        ActivityMain.edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.clear();
                String s = ActivityMain.edtTimKiem.getText().toString().toLowerCase(Locale.getDefault());
                if (s.length() == 0) {
                    list.addAll(listTam);
                    ActivityMain.imgClose.setVisibility(View.GONE);
                } else {
                    ActivityMain.imgClose.setVisibility(View.VISIBLE);
                    for (PInfo pInfo : listTam) {
                        if (pInfo.getAppname().toLowerCase(Locale.getDefault()).contains(s)) {
                            list.add(pInfo);
                        }
                    }
                }
                setAdapter();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void setAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerAppUse.setLayoutManager(layoutManager);
        appAdapter = new LoadAppAdapter(getContext(), R.layout.item_home, list, true, this);
        recyclerAppUse.setAdapter(appAdapter);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void total(int count, double total, final List<PInfo> listDelete) {
        final HistoryDAO historyDAO = new HistoryDAO(getContext());
        DecimalFormat df = new DecimalFormat("#.##");
        String s = df.format(total);
        if (count != 0) {
            check = true;
            size = listDelete.size();
            ActivityMain.mImageDelete.setVisibility(View.VISIBLE);
            ActivityMain.mImageSearch.setVisibility(View.GONE);
            ActivityMain.mImageMore.setVisibility(View.GONE);
            ActivityMain.mTxtTitle.setText(getActivity().getResources().getString(R.string.chon) + " " + count + " " + getActivity().getResources().getString(R.string.ung_dung));
            ActivityMain.mTxtTongApp.setText(getActivity().getResources().getString(R.string.tong) + " " + s + " MB");
            ActivityMain.mImageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (PInfo p : listDelete) {
                        Intent intent = new Intent(Intent.ACTION_DELETE);
                        intent.setData(Uri.parse("package:" + p.getPname()));
                        intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
                        getActivity().startActivityForResult(intent, 1010);

                        // Save history
                        Date date = Calendar.getInstance().getTime();
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                        String strDate = dateFormat.format(date);
                        p.setDateUn(strDate);

                        try {
                            historyDAO.addHistory(p);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            check = false;
            ActivityMain.mImageDelete.setVisibility(View.GONE);
            ActivityMain.mImageSearch.setVisibility(View.VISIBLE);
            ActivityMain.mImageMore.setVisibility(View.VISIBLE);
            ActivityMain.mTxtTitle.setText(getActivity().getResources().getString(R.string.go_cai_dat_ung_dung));
            ActivityMain.mTxtTongApp.setText(getResources().getString(R.string.tong) + " " + ActivityMain.iAppUser + "+" + ActivityMain.iAppSystem + " " + getResources().getString(R.string.ung_dung));
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
