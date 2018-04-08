package com.zx.tjmarketmobile.ui.caselegal;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.CaseDetailFlowAdapter;
import com.zx.tjmarketmobile.entity.CaseFlowEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Stanny On 2017/3/14
 * 功能：案件轨迹Fragment
 */
public class CaseDetailFlowFragment extends BaseFragment {

    private CaseDetailFlowAdapter mCaseAdapter;
    private RecyclerView mRvInfo;
    private String fId = "";
    private String fType = "常规流程";
    private RadioGroup rgCaseFlow;
    private ApiData getLcgj = new ApiData(ApiData.HTTP_ID_caseGetAyLcgjPageInfo);
    private List<CaseFlowEntity> dataInfoList = new ArrayList<>();

    public static CaseDetailFlowFragment newInstance(String fId) {
        CaseDetailFlowFragment details = new CaseDetailFlowFragment();
        details.fId = fId;
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_flow, container, false);
        rgCaseFlow = (RadioGroup) view.findViewById(R.id.rg_case_flow);
        mRvInfo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        getLcgj.setLoadingListener(this);
        mRvInfo.setLayoutManager(mLinearLayoutManager);
        mCaseAdapter = new CaseDetailFlowAdapter(getActivity(), dataInfoList);
        mRvInfo.setAdapter(mCaseAdapter);
//        rgCaseFlow.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.rb_case_flow_1://常规流程
//                        fType = "常规流程";
//                        break;
//                    case R.id.rb_case_flow_2://案件延期
//                        fType = "案件延期";
//                        break;
//                    case R.id.rb_case_flow_3://案件销案
//                        fType = "案件销案";
//                        break;
//                }
                getLcgj.loadData(fId);
//            }
//        });
        return view;
    }

    //当界面可见之后再进行数据加载（懒加载）
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && dataInfoList.size() == 0) {
            getLcgj.loadData(fId, fType);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_caseGetAyLcgjPageInfo:
                dataInfoList.clear();
                List<CaseFlowEntity> list = (List<CaseFlowEntity>) b.getEntry();
                dataInfoList.addAll(list);
                mCaseAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

}
