package com.zx.tjmarketmobile.adapter.supervise;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.MyRecycleAdapter;
import com.zx.tjmarketmobile.entity.HttpZtEntity;
import com.zx.tjmarketmobile.listener.ICheckedChangeListener;

import java.util.List;

/**
 * Created by Xiangb on 2016/9/22.
 * 功能：主体认领适配器
 */
public class SuperviseClaimedAdapter extends MyRecycleAdapter {

    private List<HttpZtEntity> ztItems;
    private ICheckedChangeListener checkedChangeListener;
    private Context context;
    private Holder myHolder;
    private int type = 0;

    public SuperviseClaimedAdapter(Context c, List<HttpZtEntity> ztItems, int type) {
        this.ztItems = ztItems;
        this.context = c;
        this.type = type;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_claimed_list, parent, false);
            return new Holder(view);
        } else {//footer
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_foot_view, parent, false);
            return new FooterViewHolder(view);
        }
    }

    //从holder中获取控件并设置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof Holder) {
            myHolder = (Holder) holder;
//            final HttpZtEntity mEntity = ztItems.get(position);
//            myHolder.tvName.setText(mEntity.getEntityName());
//            myHolder.tvAddress.setText(mEntity.getAddress());
//            if (!mEntity.getfContactPhone().isEmpty() && !"null".equals(mEntity.getfContactPhone())) {
//                myHolder.tvTel.setText(mEntity.getfContactPhone());
//                myHolder.tvTel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View arg0) {
//                        Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mEntity.getfContactPhone()));
//                        context.startActivity(phoneIntent);
//                    }
//                });
//            } else {
//                myHolder.tvTel.setText("");
//            }
//            if (type == 1) {
//                if (mEntity.isSelected()) {
//                    myHolder.cbClaimed.setChecked(true);
//                } else {
//                    myHolder.cbClaimed.setChecked(false);
//                }
//            }else {
//                myHolder.cbClaimed.setVisibility(View.GONE);
//            }
        } else {
            footerViewHolder = (FooterViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return ztItems == null ? 0 : ztItems.size() + 1;
    }

    public void setOnCheckedChangeListener(ICheckedChangeListener listener) {
        this.checkedChangeListener = listener;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName, tvAddress, tvTel;
        private CheckBox cbClaimed;
        private LinearLayout layoutTel;

        public Holder(View parent) {
            super(parent);
            tvName = (TextView) parent.findViewById(R.id.tv_claimed_name);
            tvAddress = (TextView) parent.findViewById(R.id.tv_claimed_address);
            layoutTel = (LinearLayout) parent.findViewById(R.id.tv_claimed_tel_layout);
            tvTel = (TextView) parent.findViewById(R.id.tv_claimed_tel);
            cbClaimed = (CheckBox) parent.findViewById(R.id.cb_claimed);
            parent.setOnClickListener(this);
            cbClaimed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (checkedChangeListener != null) {
                        checkedChangeListener.CheckedChange(getAdapterPosition(), isChecked);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

}
