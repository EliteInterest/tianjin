package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.HttpZtEntity;

import java.util.List;

/**
 * Created by Xiangb on 2016/9/22.
 * 功能：搜索列表适配器
 */
public class PaginationAdapter extends MyRecycleAdapter {

    private List<HttpZtEntity> ztItems;
    private Context context;
    private Holder myHolder;

    public PaginationAdapter(Context c, List<HttpZtEntity> ztItems) {
        this.ztItems = ztItems;
        this.context = c;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result_list, parent, false);
            return new Holder(view);
        } else {//footer
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_foot_view, parent, false);
            return new FooterViewHolder(view);
        }
    }

    //从holder中获取控件并设置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Holder) {
            myHolder = (Holder) holder;
            final HttpZtEntity mEntity = ztItems.get(position);
            myHolder.tvZtName.setText(mEntity.getEnterpriseName());
//            setDrawable(mEntity, myHolder.imgCreditLevel);
            myHolder.tvZtAddress.setText(mEntity.getAddress());
            if (mEntity.getContactInfo() != null) {
                myHolder.tvZtTel.setText(mEntity.getContactInfo());
                myHolder.tvZtTel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mEntity.getContactInfo()));
                        context.startActivity(phoneIntent);
                    }
                });
            } else {
                myHolder.tvZtTel.setText("");
            }
        } else {
            footerViewHolder = (FooterViewHolder) holder;
        }
    }

    private void setDrawable(HttpZtEntity zt, ImageView imgCreditLevel) {
        String creditLevel = zt.getCreditCode();
        // 默认为A级
        if (creditLevel == null) {
            imgCreditLevel.setBackgroundResource(R.mipmap.a);
        } else if (creditLevel.equalsIgnoreCase("B")) {
            imgCreditLevel.setBackgroundResource(R.mipmap.b);
        } else if (creditLevel.equalsIgnoreCase("C")) {
            imgCreditLevel.setBackgroundResource(R.mipmap.c);
        } else if (creditLevel.equalsIgnoreCase("D")) {
            imgCreditLevel.setBackgroundResource(R.mipmap.d);
        } else if (creditLevel.equalsIgnoreCase("Z")) {
            imgCreditLevel.setBackgroundResource(R.mipmap.z);
        } else {
            imgCreditLevel.setBackgroundResource(R.mipmap.a);
        }
    }

    @Override
    public int getItemCount() {
        return ztItems == null ? 0 : ztItems.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvZtName, tvZtAddress, tvZtTel;
        private ImageView imgCreditLevel;

        public Holder(View parent) {
            super(parent);
            tvZtName = (TextView) parent.findViewById(R.id.tv_zt_name);
            tvZtAddress = (TextView) parent.findViewById(R.id.tv_zt_address);
            tvZtTel = (TextView) parent.findViewById(R.id.tv_zt_tel);
            imgCreditLevel = (ImageView) parent.findViewById(R.id.iv_creditlevel);
            parent.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

}
