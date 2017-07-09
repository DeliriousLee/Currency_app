package cn.edu.zucc.lc1298.exchange_rate.Activities;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import cn.edu.zucc.lc1298.exchange_rate.Fragments.mFragChange;
import cn.edu.zucc.lc1298.exchange_rate.Fragments.mFragMoney;
import cn.edu.zucc.lc1298.exchange_rate.Fragments.mFragRecords;
import cn.edu.zucc.lc1298.exchange_rate.R;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    //声明Tab的布局文件

    //声明Tab的Button
    private Button btnMoney;
    private Button btnChange;
    private Button btnRecord;
    //声明Tab分别对应的Fragment
    private mFragMoney mMoney;
    private mFragChange mChange;
    private mFragRecords mRecords;
    private LinearLayout tab_T;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();//初始化控件
        initEvents();//初始化事件
        selectTab(0);//默认选中第一个Tab

    }
     private  void initViews(){

         btnChange=(Button)findViewById(R.id.btn_change);
         btnMoney=(Button)findViewById(R.id.btn_money);
         btnRecord=(Button)findViewById(R.id.btn_records);
         tab_T=(LinearLayout)findViewById(R.id.tab_top);
     }
     private void initEvents() {
         btnMoney.setOnClickListener(this);
         btnChange.setOnClickListener(this);
         btnRecord.setOnClickListener(this);
        }

     public void onClick(View v) {

         switch (v.getId()) {
              case R.id.btn_money:
                 selectTab(0);
                 break;
              case R.id.btn_change:
                 selectTab(1);
                 break;
             case R.id.btn_records:
                 selectTab(2);
                 break;

         }
     }
    private void selectTab(int i) {
        //获取FragmentManager对象
        FragmentManager manager = getSupportFragmentManager();
        //获取FragmentTransaction对象
        FragmentTransaction transaction = manager.beginTransaction();
        //先隐藏所有的Fragment
       // hideFragments(transaction);

        switch (i){
            case 0:
                if (mMoney == null) {
                    mMoney = new mFragMoney();
                    transaction.replace(R.id.id_content, mMoney);
                    transaction.commit();
                } else {


                    transaction.replace(R.id.id_content, mMoney);
                    transaction.commit();

                }
                 break;
            case 1:
                if (mChange == null) {
                    mChange = new mFragChange();
                    transaction.replace(R.id.id_content, mMoney);
                    transaction.commit();
                } else {
                    //如果微信对应的Fragment已经实例化，则直接显示出来
                    transaction.replace(R.id.id_content, mChange);
                    transaction.commit();

                }
                break;
            case 2:
            if(mRecords==null){
                mRecords=new mFragRecords();
                transaction.replace(R.id.id_content,mRecords);
                transaction.commit();
            }else{
                transaction.replace(R.id.id_content, mRecords);
                transaction.commit();
            }
        }

    }

}
