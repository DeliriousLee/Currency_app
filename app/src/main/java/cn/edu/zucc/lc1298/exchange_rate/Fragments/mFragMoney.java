package cn.edu.zucc.lc1298.exchange_rate.Fragments;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import cn.edu.zucc.lc1298.exchange_rate.Adapter.CountryAdapter;
import cn.edu.zucc.lc1298.exchange_rate.Control.JSONParser;
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


    public static Country current_country;
    public static final String RATES = "rates";

    CountryAdapter adapter;
    public static final String SELECTED="SELECTED_CURRENCY";

    //this will contain my developers key
    private String mKey;
    //used to fetch the 'rates' json object from openexchangerates.org
    public static final String URL_BASE = "http://openexchangerates.org/api/latest.json?app_id=";



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrgMoney=inflater.inflate(R.layout.tab_money,container,false);
        mKey = getKey("open_key");
        initView();
        if(countryList!=null)
            countryList.clear();
        initCurrentCountry();
        new FetchRatesTask().execute(URL_BASE+mKey);
        initCountry();
        initEvent();
        if(savedInstanceState==null){
            spCountry.setSelection(0);
            PrefsMgr.setString(this.getContext(), SELECTED,spCountry.getSelectedItem().toString().substring(0,2));
        }

        adapter=new CountryAdapter(this.getContext(),R.layout.list_item,countryList);
        listCountry.setAdapter(adapter);
        return FrgMoney;
    }

    private void initCountry(){
        //初始化器会将初始化完成放入数据（货币ID，国家名，货币字母，图片）
        Toast.makeText(getContext(),"数据0",Toast.LENGTH_SHORT).show();
       countries=new Countries();
       countryList=Countries.addCountriesList(countryList,countries);
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


    public void initCurrentCountry_temp(){
        current_country.currency.toAUD=0.6;
        current_country.currency.toCAD=3.2;
        current_country.currency.toCNY=1.6;
        current_country.currency.toEUR=0.7;
        current_country.currency.toGBP=0.4;
        current_country.currency.toHKD=0.2;
        current_country.currency.toJPY=0.8;
        current_country.currency.toRUB=0.5;
        current_country.currency.toUSD=0.3;
        }
    private void initCurrentCountry(){
        current_country=new Country();
        current_country.setCountryId(0);
        current_country.currency=new Currency();
        //initCurrentCountry_temp();
    }
    private void removeIncorrect(int position){
        countryList.clear();
        if(position!=0) {
            countries.AUD.setStr_currency("");
            countryList.add(countries.AUD);
        }
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
            InputStream inputStream = assetManager.open("keys.properties");
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

                          removeIncorrect(0);
                    PrefsMgr.setString(getContext(), SELECTED,"AUD");

                     break;
                case 1:
                    mFragMoney.current_country.setCountryId(1);
                    mFragMoney.current_country.setMoneyCode("CAD");

                    removeIncorrect(1);
                    PrefsMgr.setString(getContext(), SELECTED,"CAD");



                     //   }
                  //  }).start();
                    break;
                case 2:
                    mFragMoney.current_country.setCountryId(2);
                    mFragMoney.current_country.setMoneyCode("CNY");

                            removeIncorrect(2);
                    PrefsMgr.setString(getContext(), SELECTED,"CNY");
                    break;
                case 3:
                    mFragMoney.current_country.setCountryId(3);
                    mFragMoney.current_country.setMoneyCode("EUR");
                     removeIncorrect(3);
                    PrefsMgr.setString(getContext(), SELECTED,"EUR");

                    break;
                case 4:
                    mFragMoney.current_country.setCountryId(4);
                    mFragMoney.current_country.setMoneyCode("GBP");

                            removeIncorrect(4);
                    PrefsMgr.setString(getContext(), SELECTED,"GBP");

                    break;
                case 5:
                    mFragMoney.current_country.setCountryId(5);
                    mFragMoney.current_country.setMoneyCode("HKD");
                          removeIncorrect(5);
                    PrefsMgr.setString(getContext(), SELECTED,"HKD");

                    break;
                case 6:
                    mFragMoney.current_country.setCountryId(6);
                    mFragMoney.current_country.setMoneyCode("JPY");

                            removeIncorrect(6);
                    PrefsMgr.setString(getContext(), SELECTED,"JPY");

                    break;
                case 7:
                    mFragMoney.current_country.setCountryId(7);
                    mFragMoney.current_country.setMoneyCode("RUB");

                            removeIncorrect(7);
                    PrefsMgr.setString(getContext(), SELECTED,"RUB");
                     break;
                case 8:
                    mFragMoney.current_country.setCountryId(8);
                    mFragMoney.current_country.setMoneyCode("USD");

                            removeIncorrect(8);
                    PrefsMgr.setString(getContext(), SELECTED,"USD");

                     break;
                }
                    System.out.print("shuju2");
                    Toast.makeText(getContext(), "有数据1",Toast.LENGTH_SHORT).show();
                    new FetchRatesTask().execute(URL_BASE+mKey);
                    adapter.notifyDataSetChanged();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    class FetchRatesTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            //String... excludeProperty表示不定参数，也就是调用这个方法的时候这里可以传入多个String对象

            return new JSONParser().getJSONFromUrl(params[0]);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            try {
                if (jsonObject == null) {
                    throw new JSONException("no data available.");
                }
                Toast.makeText(getContext(), "有数据2",Toast.LENGTH_SHORT).show();
                JSONObject jsonRates = jsonObject.getJSONObject(RATES);

                current_country.currency.toAUD=jsonRates.getDouble(current_country.moneyCode)/jsonRates.getDouble("AUD");
                current_country.currency.toCAD=jsonRates.getDouble(current_country.moneyCode)/jsonRates.getDouble("CAD");
                current_country.currency.toCNY=jsonRates.getDouble(current_country.moneyCode)/jsonRates.getDouble("CNY");
                current_country.currency.toEUR=jsonRates.getDouble(current_country.moneyCode)/jsonRates.getDouble("EUR");
                current_country.currency.toGBP=jsonRates.getDouble(current_country.moneyCode)/jsonRates.getDouble("GBP");
                current_country.currency.toHKD=jsonRates.getDouble(current_country.moneyCode)/jsonRates.getDouble("HKD");
                current_country.currency.toJPY=jsonRates.getDouble(current_country.moneyCode)/jsonRates.getDouble("JPY");
                current_country.currency.toRUB=jsonRates.getDouble(current_country.moneyCode)/jsonRates.getDouble("RUB");
                current_country.currency.toUSD=jsonRates.getDouble(current_country.moneyCode)/jsonRates.getDouble("USD");
            } catch (JSONException e) {

                e.printStackTrace();
            }
        }
    }

}
