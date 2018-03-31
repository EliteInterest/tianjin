package com.zx.gamarketmobile.ui.complain;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.adapter.ComplainDetailFlowAdapter;
import com.zx.gamarketmobile.entity.CompFlowEntity;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.http.BaseHttpResult;
import com.zx.gamarketmobile.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Stanny On 2017/3/22
 * 功能：投诉举报处置动态Fragment
 */
public class ComplainDetailFlowFragment extends BaseFragment {

    private ComplainDetailFlowAdapter mCompAdapter;
    private RecyclerView mRvInfo;
    private String fId = "";
    private ApiData getLcgj = new ApiData(ApiData.HTTP_ID_compLcgj);
    private List<CompFlowEntity> dataInfoList = new ArrayList<>();

    public static ComplainDetailFlowFragment newInstance(String fId) {
        ComplainDetailFlowFragment details = new ComplainDetailFlowFragment();
        details.fId = fId;
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comp_flow, container, false);
        mRvInfo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        getLcgj.setLoadingListener(this);
        mRvInfo.setLayoutManager(mLinearLayoutManager);
        mCompAdapter = new ComplainDetailFlowAdapter(getActivity(), dataInfoList);
        mRvInfo.setAdapter(mCompAdapter);
        return view;
    }

    //当界面可见之后再进行数据加载（懒加载）
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && dataInfoList.size() == 0) {
            getLcgj.loadData(fId);
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_compLcgj:
                dataInfoList.clear();
                List<CompFlowEntity> list = (List<CompFlowEntity>) b.getEntry();
                dataInfoList.addAll(list);
                mCompAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

}
