package cn.edu.zucc.lc1298.exchange_rate.LineChart;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.DashPathEffect;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import cn.edu.zucc.lc1298.exchange_rate.Control.mSQLHelper;

/**
 * Created by Lee on 2017/7/12.
 */

public class LineChangeData {

    private String sqlwords;//sql语句

    public LineData lineData;

    public LineDataSet dataSet;
    public ArrayList<Entry> Values=new ArrayList<>();

    Float vy=0F;
    private mSQLHelper dbhelper;
    private SQLiteDatabase db;


    public LineChangeData(Context context){

        dbhelper=new mSQLHelper(context);
        db=dbhelper.getReadableDatabase();
        String sqlwords="select * from currencyRecords order by recordId";//由于我的recordId是自动生成的所以是Id按照时间顺序的
        Cursor cursor=db.rawQuery(sqlwords,null);
        int i=0;
       while(cursor.moveToNext()){
            vy=Float.parseFloat(cursor.getString(cursor.getColumnIndex("currency")));
            Values.add(new Entry(i,vy));
            i++;
       }
        dataSet=new LineDataSet(Values,"Dataset");
        dataSet.setDrawIcons(false);
        dataSet.enableDashedLine(10f, 5f, 0f);
        dataSet.enableDashedHighlightLine(10f, 5f, 0f);
        dataSet.setColor(Color.BLACK);
        dataSet.setCircleColor(Color.BLACK);
        dataSet.setLineWidth(1f);
        dataSet.setCircleRadius(3f);
        dataSet.setDrawCircleHole(false);
        dataSet.setValueTextSize(9f);
        dataSet.setDrawFilled(true);
        dataSet.setFormLineWidth(1f);
        dataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        dataSet.setFormSize(15.f);

        lineData=new LineData(dataSet);
    }

}
