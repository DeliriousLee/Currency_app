package cn.edu.zucc.lc1298.exchange_rate.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.zucc.lc1298.exchange_rate.R;

/**
 * Created by Lee on 2017/7/2.
 */

public class mFragChange extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab_change,container,false);
        return view;
    }

    private Context getApplicationContext() {
        return this.getContext();
    }
}
