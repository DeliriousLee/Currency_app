package cn.edu.zucc.lc1298.exchange_rate.Fragments;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import cn.edu.zucc.lc1298.exchange_rate.Adapter.CountryAdapter;
import cn.edu.zucc.lc1298.exchange_rate.Control.JSONParser;
import cn.edu.zucc.lc1298.exchange_rate.Control.mSQLHelper;
import cn.edu.zucc.lc1298.exchange_rate.Model.Countries;
import cn.edu.zucc.lc1298.exchange_rate.Model.Country;
import cn.edu.zucc.lc1298.exchange_rate.Model.Currency;
import cn.edu.zucc.lc1298.exchange_rate.R;
import cn.edu.zucc.lc1298.exchange_rate.SharedData.PrefsMgr;


/**
 * Created by Lee on 2017/7/2.
 */

public class mFragMoney extends Fragment {
    @Nullable
    Countries countries;
    public static int putMoney=0;
    private Spinner spCountry;
    private ListView listCountry;
    private EditText edtMoney;
    private View FrgMoney;
    private List<Country>countryList=new ArrayList<Country>();
    private static int recordId = 0;

    private mSQLHelper dbhelper=null;
    private SQLiteDatabase db=null;

    public static Country current_country;
    public static final String RATES = "rates";

    CountryAdapter adapter=null;
    public static final String SELECTED="SELECTED_CURRENCY";


    //this will contain my developers key
    private String mKey;
    //used to fetch the 'rates' json object from openexchangerates.org
    public static final String URL_BASE = "http://openexchangerates.org/api/latest.json?app_id=";

    private static final int UPDATE_RATES=1;
     Timer timer = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            // 需要做的事:发送消息
            Message message = new Message();
            message.what = UPDATE_RATES;
            handler.sendMessage(message);
        }
    };
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case UPDATE_RATES:
                  new UpdateRatesTask().execute(URL_BASE+mKey);
                   break;
           }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrgMoney=inflater.inflate(R.layout.tab_money,container,false);
        mKey = getKey("open_key");
        adapter=new CountryAdapter(this.getContext(),R.layout.list_item,countryList);
        initView();
        if(countryList!=null)
            countryList.clear();
        initCurrentCountry();
        initCountry();
        initDatabase();
        initEvent();
        if(savedInstanceState==null){
           spCountry.setSelection(0);

        }


        listCountry.setAdapter(adapter);
        timer.schedule(task,1000,100000);
        return FrgMoney;
    }

    private void initCountry(){
        //初始化器会将初始化完成放入数据（货币ID，国家名，货币字母，图片）

       countries=new Countries();
       countryList=Countries.addCountriesList(countryList,countries);
    }
    private void initDatabase(){
        dbhelper=new mSQLHelper(getContext());
        db=dbhelper.getWritableDatabase();
    }
    private void initEvent(){
        spCountry.setOnItemSelectedListener( new itemSelectedListener());

        edtMoney.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 try{

                             putMoney=Integer.parseInt(edtMoney.getText().toString());
                               adapter.notifyDataSetChanged();
                }catch (Exception e){

                    e.printStackTrace();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void initView(){


        spCountry=(Spinner) FrgMoney.findViewById(R.id.spn_coutry);
        listCountry=(ListView)FrgMoney.findViewById(R.id.list_result);
        edtMoney=(EditText)FrgMoney.findViewById(R.id.edt_money);

    }



    private void initCurrentCountry(){
        current_country=new Country();
        current_country.setCountryId(0);
        current_country.currency=new Currency();

    }
    private void removeIncorrect(int position){
        countryList.clear();
        if(position!=0)
         countryList.add(countries.AUD);

        if(position!=1)
        countryList.add(countries.CAD);
        if(position!=2)
        countryList.add(countries.CNY);
        if(position!=3)
        countryList.add(countries.EUR);
        if(position!=4)
        countryList.add(countries.GBP);
        if(position!=5)
        countryList.add(countries.HKD);
        if(position!=6)
        countryList.add(countries.JPY);
        if(position!=7)
        countryList.add(countries.RUB);
        if(position!=8)
        countryList.add(countries.USD);

    }

    private String getKey(String keyName){
        //从Bundle
        // appid初始化
        AssetManager assetManager = this.getResources().getAssets();
        Properties properties = new Properties();
        try {
            InputStream inputStream = assetManager.open("key.properties");
            properties.load(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  properties.getProperty(keyName);

    }

    private class itemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch (position){
                case 0:
                    mFragMoney.current_country.setCountryId(0);
                    mFragMoney.current_country.setMoneyCode("AUD");
                    mFragMoney.current_country.setCountryName("澳元");
                          removeIncorrect(0);
                     break;
                case 1:
                    mFragMoney.current_country.setCountryId(1);
                    mFragMoney.current_country.setMoneyCode("CAD");
                    mFragMoney.current_country.setCountryName("加元");
                    removeIncorrect(1);
                    break;
                case 2:
                    mFragMoney.current_country.setCountryId(2);
                    mFragMoney.current_country.setMoneyCode("CNY");
                    mFragMoney.current_country.setCountryName("人民币");
                    removeIncorrect(2);
                    break;
                case 3:
                    mFragMoney.current_country.setCountryId(3);
                    mFragMoney.current_country.setMoneyCode("EUR");
                    mFragMoney.current_country.setCountryName("欧元");
                    removeIncorrect(3);
                    break;
                case 4:
                    mFragMoney.current_country.setCountryId(4);
                    mFragMoney.current_country.setMoneyCode("GBP");
                    mFragMoney.current_country.setCountryName("英镑");
                    removeIncorrect(4);
                    break;
                case 5:
                    mFragMoney.current_country.setCountryId(5);
                    mFragMoney.current_country.setMoneyCode("HKD");
                    mFragMoney.current_country.setCountryName("港元");
                    removeIncorrect(5);
                    break;
                case 6:
                    mFragMoney.current_country.setCountryId(6);
                    mFragMoney.current_country.setMoneyCode("JPY");
                    mFragMoney.current_country.setCountryName("日元");
                    removeIncorrect(6);
                    break;
                case 7:
                    mFragMoney.current_country.setCountryId(7);
                    mFragMoney.current_country.setMoneyCode("RUB");
                    mFragMoney.current_country.setCountryName("卢布");
                    removeIncorrect(7);
                    break;
                case 8:
                    mFragMoney.current_country.setCountryId(8);
                    mFragMoney.current_country.setMoneyCode("USD");
                    mFragMoney.current_country.setCountryName("美元");
                    removeIncorrect(8);
                    break;
                }


                    new FetchRatesTask().execute(URL_BASE+mKey);
                    adapter.notifyDataSetChanged();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    class UpdateRatesTask extends AsyncTask<String,Void,JSONObject>{

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try {
                if (jsonObject == null) {
                    throw new JSONException("no data available.");
                }
                JSONObject jsonRates = jsonObject.getJSONObject(RATES);
                double rates=jsonRates.getDouble("CNY")/jsonRates.getDouble("USD");
                ContentValues currency=new ContentValues();
                currency.put("currency",rates);

                currency.put("recordId",recordId++);

                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String date = sDateFormat.format(curDate);
                currency.put("currencyTime",date);
                db.insert("currencyRecords",null,currency);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONParser temp= new JSONParser();
            JSONObject result=new JSONObject();
            result=temp.getJSONFromUrl(params[0]);
            return result;
        }
    }
    class FetchRatesTask extends AsyncTask<String, Void, JSONObject> {
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Getting Curreny...");
            progressDialog.setMessage("One moment please...");
            progressDialog.setCancelable(true);
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                    "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FetchRatesTask.this.cancel(true);
                            progressDialog.dismiss();
                        }
                    });
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            //String... excludeProperty表示不定参数，也就是调用这个方法的时候这里可以传入多个String对象
            JSONParser temp= new JSONParser();
            JSONObject result=new JSONObject();
            result=temp.getJSONFromUrl(params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            try {
                if (jsonObject == null) {
                    throw new JSONException("no data available.");
                }

                JSONObject jsonRates = jsonObject.getJSONObject(RATES);

                current_country.currency.toAUD=jsonRates.getDouble("AUD")/jsonRates.getDouble(current_country.moneyCode);
                current_country.currency.toCAD=jsonRates.getDouble("CAD")/jsonRates.getDouble(current_country.moneyCode);
                current_country.currency.toCNY=jsonRates.getDouble("CNY")/jsonRates.getDouble(current_country.moneyCode);
                current_country.currency.toEUR=jsonRates.getDouble("EUR")/jsonRates.getDouble(current_country.moneyCode);
                current_country.currency.toGBP=jsonRates.getDouble("GBP")/jsonRates.getDouble(current_country.moneyCode);
                current_country.currency.toHKD=jsonRates.getDouble("HKD")/jsonRates.getDouble(current_country.moneyCode);
                current_country.currency.toJPY=jsonRates.getDouble("JPY")/jsonRates.getDouble(current_country.moneyCode);
                current_country.currency.toRUB=jsonRates.getDouble("RUB")/jsonRates.getDouble(current_country.moneyCode);
                current_country.currency.toUSD=jsonRates.getDouble("USD")/jsonRates.getDouble(current_country.moneyCode);

                ContentValues records=new ContentValues();
                records.put("countryId",current_country.getCountryId());
                records.put("moneyCode",current_country.getMoneyCode());
                records.put("countryName",current_country.getCountryName());
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String date = sDateFormat.format(curDate);
                records.put("time",date);
                db.insert("searchRecords",null,records);
                Toast.makeText(getContext(), "JSON,数据库数据加载完成",Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {

                e.printStackTrace();
            }
        }
    }

}
