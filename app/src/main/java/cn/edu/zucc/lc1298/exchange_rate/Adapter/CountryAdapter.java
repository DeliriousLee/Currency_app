package cn.edu.zucc.lc1298.exchange_rate.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import cn.edu.zucc.lc1298.exchange_rate.Fragments.mFragMoney;
import cn.edu.zucc.lc1298.exchange_rate.Model.Country;
import cn.edu.zucc.lc1298.exchange_rate.R;

/**
 * Created by Lee on 2017/7/4.
 */

public class CountryAdapter extends ArrayAdapter<Country> {
        DecimalFormat df;
        private int resourceId;
    public CountryAdapter(Context context, int textViewResourceId, List<Country> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
        df=new DecimalFormat("#.##");
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Country country=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        ImageView countryImage=(ImageView)view.findViewById(R.id.flag);
        TextView countryName=(TextView) view.findViewById(R.id.txt_moneyname);
        TextView currency=(TextView) view.findViewById(R.id.txt_currency);
        countryImage.setImageResource(country.getImageId());
        countryName.setText(country.getCountryName());
        currency.setText( df.format(mFragMoney.putMoney*getCurrency(country.getCountryId())));
        return view;
    }
        private double getCurrency(int countryId){
             double nowCurrency=0.0;
         switch (countryId){
            case 0:
                nowCurrency= mFragMoney.current_country.currency.toAUD;
                break;
            case 1:
                nowCurrency= mFragMoney.current_country.currency.toCAD;
            break;
            case 2:
                nowCurrency=mFragMoney.current_country.currency.toCNY;
            break;
            case 3:
                nowCurrency= mFragMoney.current_country.currency.toEUR;
            break;
            case 4:
                nowCurrency=mFragMoney.current_country.currency.toGBP;
            break;
            case 5:
                nowCurrency=mFragMoney.current_country.currency.toHKD;
            break;
            case 6:
                nowCurrency=mFragMoney.current_country.currency.toJPY;
            break;
            case 7:
                nowCurrency=mFragMoney.current_country.currency.toRUB;
            break;
            case 8:
                nowCurrency= mFragMoney.current_country.currency.toUSD;
            break;
        }
        return  nowCurrency;
    }
}
