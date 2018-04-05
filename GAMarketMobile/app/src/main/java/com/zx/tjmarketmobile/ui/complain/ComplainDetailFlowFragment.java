package com.zx.tjmarketmobile.ui.complain;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.ComplainDetailFlowAdapter;
import com.zx.tjmarketmobile.entity.ComplainInfoDetailsBean;
import com.zx.tjmarketmobile.ui.base.BaseFragment;

import java.util.List;

/**
 * Create By Stanny On 2017/3/22
 * 功能：投诉举报处置动态Fragment
 */
public class ComplainDetailFlowFragment extends BaseFragment {

    private ComplainDetailFlowAdapter mCompAdapter;
    private RecyclerView mRvInfo;
    private String fId = "";
    private List<ComplainInfoDetailsBean.StatusInfoBean> dataInfoList;

    public static ComplainDetailFlowFragment newInstance(List<ComplainInfoDetailsBean.StatusInfoBean> beans) {
        ComplainDetailFlowFragment details = new ComplainDetailFlowFragment();
        details.dataInfoList = beans;
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comp_flow, container, false);
        mRvInfo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        mRvInfo.setLayoutManager(mLinearLayoutManager);
        mCompAdapter = new ComplainDetailFlowAdapter(getActivity(), dataInfoList);
        mRvInfo.setAdapter(mCompAdapter);
        return view;
    }

}
