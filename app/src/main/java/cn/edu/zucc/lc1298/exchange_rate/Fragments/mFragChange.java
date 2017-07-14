package cn.edu.zucc.lc1298.exchange_rate.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import cn.edu.zucc.lc1298.exchange_rate.Control.mSQLHelper;
import cn.edu.zucc.lc1298.exchange_rate.LineChart.LineChangeData;
import cn.edu.zucc.lc1298.exchange_rate.R;

/**
 * Created by Lee on 2017/7/2.
 */

public class mFragChange extends Fragment {
    private SQLiteDatabase db;
    private mSQLHelper dbhelper;
    private LineChart mLineChart;
    private View FrgChange;

    private int count=10;//默认有10个点

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrgChange=inflater.inflate(R.layout.tab_change,container,false);
        initView();
        LineChangeData data=new LineChangeData(getContext());
        mLineChart.setData(data.lineData);
        return FrgChange;
    }


    private void initView(){

        mLineChart=(LineChart)FrgChange.findViewById(R.id.changeLineChart);

    }


}
