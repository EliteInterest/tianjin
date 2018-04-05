package com.zx.tjmarketmobile.ui.map;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.BusinessAdapter;
import com.zx.tjmarketmobile.entity.EntityDetail;
import com.zx.tjmarketmobile.ui.base.BaseFragment;

/**
 *
 * Create By Stanny On 2016/9/22
 * 功能：业务信息
 *
 */
public class BusinessFragment extends BaseFragment {

	private EntityDetail.BusinessBean businessBean;// 业务信息
	private RecyclerView mRvBusiness;

	public static BusinessFragment newInstance(int index, EntityDetail.BusinessBean bizInfo) {
		BusinessFragment details = new BusinessFragment();
		Bundle args = new Bundle();
		args.putInt("index", index);
		details.setArguments(args);
		details.businessBean = bizInfo;
		return details;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_business, container, false);
		mRvBusiness = (RecyclerView) view.findViewById(R.id.rv_normal_view);
		mRvBusiness.setLayoutManager(mLinearLayoutManager);
		((SwipeRefreshLayout)view.findViewById(R.id.srl_normal_layout)).setEnabled(false);
		BusinessAdapter businessAdapter = new BusinessAdapter(getActivity(), businessBean.getLic());
		mRvBusiness.setAdapter(businessAdapter);
		return view;
	}
}
