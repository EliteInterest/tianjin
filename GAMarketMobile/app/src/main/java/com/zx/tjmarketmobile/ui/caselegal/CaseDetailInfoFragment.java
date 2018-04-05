package com.zx.tjmarketmobile.ui.caselegal;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.CaseCompDetailInfoAdapter;
import com.zx.tjmarketmobile.entity.CaseDetailEntity;
import com.zx.tjmarketmobile.entity.CaseInfoEntity;
import com.zx.tjmarketmobile.entity.KeyValueInfo;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.util.DateUtil;

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
                CaseDetailEntity caseDetailEntity = (CaseDetailEntity) b.getEntry();
                getDataList(caseDetailEntity.getInfo());
                mCaseAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private void getDataList(CaseInfoEntity caseInfoEntity) {
        dataInfoList.clear();
        KeyValueInfo info = new KeyValueInfo("案件名称: ", caseInfoEntity.getCaseName());
        dataInfoList.add(info);
        info = new KeyValueInfo("案源类型: ", caseInfoEntity.getTypeName());
        dataInfoList.add(info);
        info = new KeyValueInfo("部门: ", caseInfoEntity.getDepartmentName());
        dataInfoList.add(info);
        info = new KeyValueInfo("登记内容: ", caseInfoEntity.getRegContent());
        dataInfoList.add(info);
        info = new KeyValueInfo("登记时间: ", DateUtil.getDateFromMillis(caseInfoEntity.getRegDate()));
        dataInfoList.add(info);
        info = new KeyValueInfo("登记人: ", caseInfoEntity.getRegUser());
        dataInfoList.add(info);
        info = new KeyValueInfo("处理意见: ", caseInfoEntity.getDisOpinion());
        dataInfoList.add(info);
        info = new KeyValueInfo("提供者姓名名称: ", caseInfoEntity.getProvideName());
        dataInfoList.add(info);
        info = new KeyValueInfo("提供者联系方式: ", caseInfoEntity.getProvideContact());
        dataInfoList.add(info);
        info = new KeyValueInfo("提供者地址: ", caseInfoEntity.getProvideAddress());
        dataInfoList.add(info);
        info = new KeyValueInfo("涉案主体名称: ", caseInfoEntity.getEnterpriseName());
        dataInfoList.add(info);
        info = new KeyValueInfo("涉案主体法人: ", caseInfoEntity.getEnterprisePerson());
        dataInfoList.add(info);
        info = new KeyValueInfo("涉案主体联系电话: ", caseInfoEntity.getEnterpriseContact());
        dataInfoList.add(info);
        info = new KeyValueInfo("涉案主体联系地址: ", caseInfoEntity.getEnterpriseAddress());
        dataInfoList.add(info);
    }

}
