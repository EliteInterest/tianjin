package com.zx.tjmarketmobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.KeyValueInfo;

import java.util.List;

/**
 * Created by Xiangb on 2018/4/17.
 * 功能：
 */

public class CompVideoAdapter extends RecyclerView.Adapter<CompVideoAdapter.MyHolder> {

    private List<KeyValueInfo> dataList;

    public CompVideoAdapter(List<KeyValueInfo> dataList) {
        this.dataList = dataList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_view, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        KeyValueInfo mEntify = dataList.get(position);
        holder.tvName.setText(mEntify.key.substring(mEntify.key.lastIndexOf("/") + 1));
        holder.tvDate.setText(mEntify.value);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvDate;
        private ImageView ivDelete;

        public MyHolder(View parent) {
            super(parent);
            tvName = (TextView) parent.findViewById(R.id.tv_video_name);
            tvDate = (TextView) parent.findViewById(R.id.tv_video_date);
            ivDelete = parent.findViewById(R.id.iv_video_delete);
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataList.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

    public interface VideoDeleteListener {
        void onVideoDelete(int position);
    }

}
