package cn.edu.zucc.lc1298.exchange_rate.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.zucc.lc1298.exchange_rate.Model.Records;
import cn.edu.zucc.lc1298.exchange_rate.R;

/**
 * Created by Lee on 2017/7/7.
 */

public class RecordsAdapter extends ArrayAdapter<Records> {
    private int resourceId;
    public RecordsAdapter(Context context, int textViewResourceId, List<Records> objects) {
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Records record=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView txt_moneycode=(TextView)view.findViewById(R.id.item_moneyCode);
        TextView txt_countryName=(TextView)view.findViewById(R.id.item_outCountry);
        TextView txt_time=(TextView)view.findViewById(R.id.item_outTime);

        txt_countryName.setText(record.getCountryName());
        txt_moneycode.setText(record.getMoneyCode());
        txt_time.setText(record.getTime());
        return view;
    }
}
