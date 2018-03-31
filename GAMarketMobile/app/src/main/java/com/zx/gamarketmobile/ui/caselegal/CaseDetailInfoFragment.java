package com.zx.gamarketmobile.ui.caselegal;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.adapter.CaseCompDetailInfoAdapter;
import com.zx.gamarketmobile.entity.CaseInfoEntity;
import com.zx.gamarketmobile.entity.KeyValueInfo;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.http.BaseHttpResult;
import com.zx.gamarketmobile.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Stanny On 2017/3/13
 * 功能：基本信息Fragment
 */
public class CaseDetailInfoFragment extends BaseFragment {

    private CaseCompDetailInfoAdapter mCaseAdapter;
    private RecyclerView mRvInfo;
    private String fId = "";
    private ApiData getAyxxById = new ApiData(ApiData.HTTP_ID_caseGetAyxxDetailById);
    private List<KeyValueInfo> dataInfoList = new ArrayList<>();

    public static CaseDetailInfoFragment newInstance(String fId) {
        CaseDetailInfoFragment details = new CaseDetailInfoFragment();
        details.fId = fId;
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.normal_swipe_recycler_view, container, false);
        mRvInfo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        view.findViewById(R.id.srl_normal_layout).setEnabled(false);
        getAyxxById.setLoadingListener(this);
        mRvInfo.setLayoutManager(mLinearLayoutManager);
        mCaseAdapter = new CaseCompDetailInfoAdapter(getActivity(), dataInfoList);
        mRvInfo.setAdapter(mCaseAdapter);
        return view;
    }

    //当界面可见之后再进行数据加载（懒加载）
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && dataInfoList.size() == 0) {
            getAyxxById.loadData(fId);
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_caseGetAyxxDetailById:
                CaseInfoEntity caseInfoEntity = (CaseInfoEntity) b.getEntry();
                getDataList(caseInfoEntity);
                mCaseAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private void getDataList(CaseInfoEntity caseInfoEntity) {
        dataInfoList.clear();
        KeyValueInfo info = new KeyValueInfo("案件名称: ", caseInfoEntity.getfAyAymc());
        dataInfoList.add(info);
        info = new KeyValueInfo("案件来源: ", caseInfoEntity.getfAyXxly());
        dataInfoList.add(info);
        info = new KeyValueInfo("案件领域: ", caseInfoEntity.getfAyAjly());
        dataInfoList.add(info);
        info = new KeyValueInfo("违法类型: ", caseInfoEntity.getfAyWflx());
        dataInfoList.add(info);
        info = new KeyValueInfo("监管地区: ", caseInfoEntity.getfDsrJgs());
        dataInfoList.add(info);
        info = new KeyValueInfo("当事人: ", caseInfoEntity.getfDsrMc());
        dataInfoList.add(info);
        info = new KeyValueInfo("当事人类型: ", caseInfoEntity.getfDsrLx());
        dataInfoList.add(info);
        info = new KeyValueInfo("登记时间: ", caseInfoEntity.getfSjCjsj());
        dataInfoList.add(info);
        info = new KeyValueInfo("登记人: ", caseInfoEntity.getfSjCjr());
        dataInfoList.add(info);
        info = new KeyValueInfo("登记部门: ", caseInfoEntity.getfCjrDept());
        dataInfoList.add(info);
        info = new KeyValueInfo("内容摘要: ", caseInfoEntity.getfAyNrzy());
        dataInfoList.add(info);
        info = new KeyValueInfo("定性依据: ", caseInfoEntity.getfAyWffg().replace(",", "\n"));
        dataInfoList.add(info);
        info = new KeyValueInfo("处罚依据: ", caseInfoEntity.getfPunishLaw().replace(",", "\n"));
        dataInfoList.add(info);
    }

}
