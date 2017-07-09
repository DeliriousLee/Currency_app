package cn.edu.zucc.lc1298.exchange_rate.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import cn.edu.zucc.lc1298.exchange_rate.Model.Records;

/**
 * Created by Lee on 2017/7/7.
 */

public class RecordsAdapter extends ArrayAdapter<Records> {
    private int resourceId;
    public RecordsAdapter(Context context, int textViewResourceId, List<Records> objects) {
        super(context,textViewResourceId,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
