package com.zx.tjmarketmobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.CaseRefeEntity;
import com.zx.tjmarketmobile.listener.ICheckedChangeListener;

import java.util.List;

/**
 * Created by Xiangb on 2017/11/2.
 * 功能：
 */

public class CaseRefeAdapter extends MyRecycleAdapter {
    private List<CaseRefeEntity.RowsBean> mItemList;
    public Holder myHolder;
    private ICheckedChangeListener checkedChangeListener;

    public CaseRefeAdapter(List<CaseRefeEntity.RowsBean> itemList) {
        mItemList = itemList;
    }



    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_case_refe, parent, false);
            return new Holder(view);
        } else {//footer
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_foot_view, parent, false);
            return new MyRecycleAdapter.FooterViewHolder(view);
        }
    }

    //从holder中获取控件并设置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Holder) {
            myHolder = (Holder) holder;
            CaseRefeEntity.RowsBean entity = mItemList.get(position);
            myHolder.tvWfxw.setText(ChangeText(entity.getFIllegalAct()));
            myHolder.tvDxyj.setText(ChangeText(entity.getFViolateLaw()));
            myHolder.tvCfyj.setText(ChangeText(entity.getFPunishLaw()));
            if (entity.isHasChecked()){
                myHolder.cbRefe.setChecked(true);
            }else {
                myHolder.cbRefe.setChecked(false);
            }
        } else {
            footerViewHolder = (MyRecycleAdapter.FooterViewHolder) holder;
        }
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

    private String ChangeText(String intoString) {
        String info = Html.fromHtml(intoString).toString();
        do {
            info = info.substring(0, info.length() - 2);
        } while (info.endsWith("\n"));
        return info;
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size() + 1;
    }

    public void setOnCheckedChangeListener(ICheckedChangeListener listener) {
        this.checkedChangeListener = listener;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvWfxw, tvDxyj, tvCfyj;
        private CheckBox cbRefe;

        public Holder(View parent) {
            super(parent);
            tvWfxw = (TextView) parent.findViewById(R.id.tv_refe_wfxw);
            tvDxyj = (TextView) parent.findViewById(R.id.tv_refe_dxyj);
            tvCfyj = (TextView) parent.findViewById(R.id.tv_refe_cfyj);
            cbRefe = (CheckBox) parent.findViewById(R.id.cb_refe_checked);
            cbRefe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (checkedChangeListener != null) {
                        checkedChangeListener.CheckedChange(getAdapterPosition(), b);
                    }
                }
            });
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
