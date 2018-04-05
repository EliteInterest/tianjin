package com.zx.tjmarketmobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.SynergyNumEntity;

import java.util.List;

/**
 * Created by Xiangb on 2017/12/26.
 * 功能：
 */

public class SynergySpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private List<SynergyNumEntity> dataList;

    public SynergySpinnerAdapter(List<SynergyNumEntity> synergyNumEntities) {
        dataList = synergyNumEntities;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_synergy_spinner, parent, false);
        TextView tvName = convertView.findViewById(R.id.tv_synergy_spinner_name);
        TextView tvNum = convertView.findViewById(R.id.tv_synergy_spinner_num);
        tvName.setText(dataList.get(position).getName());
            tvNum.setText(dataList.get(position).getNum()+"");
        return convertView;
    }
}
