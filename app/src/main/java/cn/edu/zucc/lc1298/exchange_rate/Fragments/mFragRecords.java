package cn.edu.zucc.lc1298.exchange_rate.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import cn.edu.zucc.lc1298.exchange_rate.R;

/**
 * Created by Lee on 2017/7/7.
 */
//这个类准备做成根据选择的item国家生成相应的数据记录
public class mFragRecords extends Fragment {

    private ListView listRecords;
    private EditText edtSearch;
    private View FrmRecords;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrmRecords=inflater.inflate(R.layout.tab_records,container,false);
        intiView();
        initEvent();
        return  FrmRecords;
    }
    private void intiView(){
        listRecords=(ListView) FrmRecords.findViewById(R.id.list_records);
        edtSearch=(EditText)FrmRecords.findViewById(R.id.edt_records);
    }
    private void initEvent(){
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
