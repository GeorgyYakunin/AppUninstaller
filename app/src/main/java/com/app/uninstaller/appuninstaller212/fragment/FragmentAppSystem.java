package com.app.uninstaller.appuninstaller212.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.uninstaller.appuninstaller212.ITotalSize;
import com.app.uninstaller.appuninstaller212.R;
import com.app.uninstaller.appuninstaller212.activity.ActivityMain;
import com.app.uninstaller.appuninstaller212.adapter.LoadAppAdapter;
import com.app.uninstaller.appuninstaller212.model.PInfo;
import com.app.uninstaller.appuninstaller212.utils.GetAppName;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class FragmentAppSystem extends Fragment implements ITotalSize {

    RecyclerView recycleAppSystem;
    LoadAppAdapter appAdapter;
    SharedPreferences pre;
    String sSort = "NAME";
    String sDesc = "AZ";
    List<PInfo> list;
    List<PInfo> listTam;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_app_system, container, false);
        recycleAppSystem = view.findViewById(R.id.recycleAppSystem);
        list = GetAppName.getAppSystem(getContext());
        listTam = new ArrayList<>();
        listTam.addAll(list);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        pre = getActivity().getSharedPreferences("SORTAPP", Context.MODE_PRIVATE);
        sSort = pre.getString("SORT", "");
        sDesc = pre.getString("DESC", "");
        if (sDesc.equals("AZ")) {
            switch (sSort) {
                case "NAME":
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
                    break;
                case "DATE":
                    Collections.sort(list, new Comparator<PInfo>() {
                        @Override
                        public int compare(PInfo p1, PInfo p2) {
                            return p1.getDate().compareTo(p2.getDate());
                        }
                    });
                    break;
                case "DUNGLUONG":
                    Collections.sort(list, new Comparator<PInfo>() {
                        @Override
                        public int compare(PInfo p1, PInfo p2) {
                            return p1.getSize().compareTo(p2.getSize());
                        }
                    });
                    break;
            }
        }else if(sDesc.equals("ZA")){
            switch (sSort) {
                case "NAME":
                    Log.d("KiemTraName","lalala"+sSort +"-- "+sDesc);


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
                    break;
                case "DATE":
                    Collections.sort(list, new Comparator<PInfo>() {
                        @Override
                        public int compare(PInfo p1, PInfo p2) {
                            return p1.getDate().compareTo(p2.getDate());
                        }
                    });
                    break;
                case "DUNGLUONG":
                    Collections.sort(list, new Comparator<PInfo>() {
                        @Override
                        public int compare(PInfo p1, PInfo p2) {
                            return p1.getSize().compareTo(p2.getSize());
                        }
                    });
                    break;
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
        recycleAppSystem.setLayoutManager(layoutManager);
        appAdapter = new LoadAppAdapter(getContext(), R.layout.item_home, list, false, this);
        recycleAppSystem.setAdapter(appAdapter);
    }
    @Override
    public void total(int count, double total, List<PInfo> list) {
    }
    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
