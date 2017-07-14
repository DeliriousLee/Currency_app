package cn.edu.zucc.lc1298.exchange_rate.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.lc1298.exchange_rate.Adapter.RecordsAdapter;
import cn.edu.zucc.lc1298.exchange_rate.Control.mSQLHelper;
import cn.edu.zucc.lc1298.exchange_rate.Model.Records;
import cn.edu.zucc.lc1298.exchange_rate.R;

/**
 * Created by Lee on 2017/7/7.
 */
//这个类准备做成根据选择的item国家生成相应的数据记录
public class mFragRecords extends Fragment {

    private ListView listRecords;
    private SearchView srcView;
    private View FrmRecords;
    private Spinner spinner;
    private SQLiteDatabase db;
    private mSQLHelper dbhelper;

    private String strInput;
    private RecordsAdapter adapter;
    private String sqlwords;
    private static  int optPosition=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrmRecords=inflater.inflate(R.layout.tab_records,container,false);
        intiView();
        initEvent();
        initRecords();
        return  FrmRecords;
    }
    private void enQuery(String input){
        strInput=input;
        dbhelper=new mSQLHelper(getContext());
        db=dbhelper.getReadableDatabase();
        listRecords.setAdapter(null);
        switch (optPosition){
            case 0:
                sqlwords="select* from searchRecords where moneyCode like '%"+strInput+"%' or countryName like '%"+strInput+"%' or time like '%"+strInput+"%'";
                break;
            case 1:
                sqlwords="select * from searchRecords where moneyCode like '%"+strInput+"%'";
                break;
            case 2:
                sqlwords="select * from searchRecords where countryName like '%"+strInput+"%'";
                break;
        }

        Cursor cursor=db.rawQuery(sqlwords,null);
        List<Records>recordsList=new  ArrayList<Records>();
        while(cursor.moveToNext()){
            Records line=new Records();
            line.setMoneyCode(cursor.getString(cursor.getColumnIndex("moneyCode")));
            line.setCountry_id(cursor.getInt(cursor.getColumnIndex("countryId")));
            line.setCountryName(cursor.getString(cursor.getColumnIndex("countryName")));
            line.setTime(cursor.getString(cursor.getColumnIndex("time")));
            recordsList.add(line);
        }
        adapter=new RecordsAdapter(getContext(),R.layout.record_item,recordsList);
        listRecords.setAdapter(adapter);

    }
    private void initRecords(){
        String sql = "select * from searchRecords";
        dbhelper = new mSQLHelper(getContext());
        //只可读
        db=dbhelper.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,null);//rawQuery适用于select语句，第二个参数用于占位符及传递参数进入sql语句
        List<Records> recordsList=new ArrayList<Records>();
        while(cursor.moveToNext()){
            Records line=new Records();//cursor为检索后生成的一行，getgetColumnIndex根据列名得到列号
            line.setMoneyCode(cursor.getString(cursor.getColumnIndex("moneyCode")));
            line.setCountry_id(cursor.getInt(cursor.getColumnIndex("countryId")));
            line.setCountryName(cursor.getString(cursor.getColumnIndex("countryName")));
            line.setTime(cursor.getString(cursor.getColumnIndex("time")));
            recordsList.add(line);
        }
        adapter=new RecordsAdapter(getContext(),R.layout.record_item,recordsList);
        listRecords.setAdapter(adapter);
    }
    private void intiView(){
        spinner=(Spinner)FrmRecords.findViewById(R.id.spn_history);
        spinner.setSelection(0);//默认选择按照货币代码

        listRecords=(ListView) FrmRecords.findViewById(R.id.list_records);
        listRecords.setTextFilterEnabled(true);//这个方法的作用是用来过滤选项的.例如在软键盘上打出一个a,则会过滤掉除了a开头的所有选项
        srcView=(SearchView)FrmRecords.findViewById(R.id.src_searchInput);
    }
    private void initEvent(){
        /* <item>货币名查询</item>
        <item>国家名查询</item>
        <item>货币查询次数统计</item>*/

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                optPosition=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
       srcView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           //点击图标运行
           public boolean onQueryTextSubmit(String query) {
                enQuery(query);
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               enQuery(newText);
               return false;
           }
       });
    }
}
