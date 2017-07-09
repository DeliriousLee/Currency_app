package cn.edu.zucc.lc1298.exchange_rate.Model;

import java.util.List;

import cn.edu.zucc.lc1298.exchange_rate.R;

/**
 * Created by Lee on 2017/7/5.
 */

public class Countries {
    public Country AUD;
    public Country CAD;
    public Country CNY;
    public Country EUR;
    public Country GBP;
    public Country HKD;
    public Country JPY;
    public Country RUB;
    public Country USD;
    public Countries(){
         AUD=new Country(0,"澳元","AUD", R.drawable.aud);
         CAD=new Country(1,"加元","CAD",R.drawable.cad);
         CNY=new Country(2,"人民币","CNY",R.drawable.cny);
         EUR=new Country(3,"欧元","EUR",R.drawable.eur);
         GBP=new Country(4,"英镑","GBP",R.drawable.gbp);
         HKD=new Country(5,"港元","HKD",R.drawable.hkd);
         JPY=new Country(6,"日元","JPY",R.drawable.jpy);
         RUB=new Country(7,"卢布","RUB",R.drawable.rub);
         USD=new Country(8,"美元","USD",R.drawable.usd);

    }
    public static List<Country> addCountriesList(List<Country>T,Countries countries){
        T.add(countries.AUD);
        T.add(countries.CAD);
        T.add(countries.CNY);
        T.add(countries.EUR);
        T.add(countries.GBP);
        T.add(countries.HKD);
        T.add(countries.JPY);
        T.add(countries.RUB);
        T.add(countries.USD);
        return T;
    }
}
