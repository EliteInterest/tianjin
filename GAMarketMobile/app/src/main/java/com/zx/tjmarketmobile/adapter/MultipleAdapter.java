package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.SelectInfo;
import com.zx.tjmarketmobile.listener.MyItemClickListener;

import java.util.List;
/**
 *
 * Create By Stanny On 2016/9/21
 * 功能：多选列表适配器
 *
 */
public class MultipleAdapter extends MyRecycleAdapter {

	private List<SelectInfo> taskItems;
	private Context mContext;

	public MultipleAdapter(Context c, List<SelectInfo> taskItems) {
		this.taskItems = taskItems;
		this.mContext = c;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_muiltiple, parent, false);
		return new Holder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		Holder myHolder = (Holder) holder;
		SelectInfo selectInfo = taskItems.get(position);
		myHolder.tvName.setText(selectInfo.name);
		if(selectInfo.isSelected) {
			myHolder.ivFlag.setChecked(true);
		} else {
			myHolder.ivFlag.setChecked(false);
		}
	}

	@Override
	public int getItemCount() {
		return taskItems == null ? 0 : taskItems.size();
	}

	public void setOnItemClickListener(MyItemClickListener listener) {
		this.mListener = listener;
	}


	class Holder extends RecyclerView.ViewHolder{
		private TextView tvName;
		private CheckBox ivFlag;

		public Holder(View itemView) {
			super(itemView);
			ivFlag = (CheckBox) itemView.findViewById(R.id.cbItemMultiple_flag);
			tvName = (TextView) itemView.findViewById(R.id.tvItemMultiple_name);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mListener != null) {
						mListener.onItemClick(v, getAdapterPosition());
					}
				}
			});
		}
	}
}