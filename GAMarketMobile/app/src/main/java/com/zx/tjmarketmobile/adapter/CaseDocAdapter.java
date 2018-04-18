package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.CaseDocEntity;
import com.zx.tjmarketmobile.util.DateUtil;

import java.util.List;

/**
 * Created by Xiangb on 2018/4/16.
 * 功能：
 */

public class CaseDocAdapter extends RecyclerView.Adapter<CaseDocAdapter.MyHolder> {

    private List<CaseDocEntity> dataList;
    private Context context;

    public CaseDocAdapter(List<CaseDocEntity> dataList) {
        this.dataList = dataList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complain, parent, false);
        context = parent.getContext();
        return new CaseDocAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.tvName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        holder.tvName.setText(dataList.get(position).getName());
        holder.tvValue.setText(DateUtil.getDateFromMillis(dataList.get(position).getAddDate()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView tvName,tvValue;

        public MyHolder(View parent) {
            super(parent);
            tvName = parent.findViewById(R.id.tvItemComplain_name);
            tvValue = parent.findViewById(R.id.tvItemComplain_value);
        }
    }

}
