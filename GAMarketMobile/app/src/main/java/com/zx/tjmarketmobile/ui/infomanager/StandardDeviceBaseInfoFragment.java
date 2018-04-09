package com.zx.tjmarketmobile.ui.infomanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.CaseCompDetailInfoAdapter;
import com.zx.tjmarketmobile.entity.KeyValueInfo;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerBiaozhun;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerBiaozhunDetail;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.manager.UserManager;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/6.
 * 点击“信息管理” - “标准信息” - “XXX企业”显示具体信息
 */

public class StandardDeviceBaseInfoFragment extends BaseFragment {
    private CaseCompDetailInfoAdapter mCaseAdapter;
    private String fId = "";
    private InfoManagerBiaozhun.RowsBean mEntity;
    private RecyclerView rvBaseInfo;
    private ApiData getTaskBaseInfo = new ApiData(ApiData.HTTP_ID_info_manager_biaozhun_detail);

    private List<KeyValueInfo> dataInfoList = new ArrayList<>();

    public static StandardDeviceBaseInfoFragment newInstance(Context context, InfoManagerBiaozhun.RowsBean mEntity) {
        StandardDeviceBaseInfoFragment details = new StandardDeviceBaseInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mEntity", mEntity);
        details.mEntity = mEntity;
        UserManager userManager = new UserManager();
        details.userInfo = userManager.getUser(context);
        details.setArguments(bundle);
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_mytask_baseinfo, container, false);
        mEntity = (InfoManagerBiaozhun.RowsBean) getArguments().getSerializable("mEntity");

        rvBaseInfo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        rvBaseInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        getTaskBaseInfo.setLoadingListener(this);
        mCaseAdapter = new CaseCompDetailInfoAdapter(getActivity(), dataInfoList);
        rvBaseInfo.setAdapter(mCaseAdapter);
        return view;
    }

    //当界面可见之后再进行数据加载（懒加载）
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mEntity != null) {
            if (isVisibleToUser && dataInfoList.size() == 0) {
                fId = mEntity.getId();
                getTaskBaseInfo.loadData(fId);
            }
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_info_manager_biaozhun_detail:
                InfoManagerBiaozhunDetail myTaskBaseInfo = (InfoManagerBiaozhunDetail) b.getEntry();
                getDataList(myTaskBaseInfo);
                mCaseAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private void getDataList(InfoManagerBiaozhunDetail myTaskBaseInfo) {
        dataInfoList.clear();
        KeyValueInfo info = new KeyValueInfo("设备编号: ", myTaskBaseInfo.getId());
        dataInfoList.add(info);
        info = new KeyValueInfo("唯一标识: ", myTaskBaseInfo.getUniquelyIdentifies());
        dataInfoList.add(info);
        info = new KeyValueInfo("机构代码: ", myTaskBaseInfo.getAgencyCode());
        dataInfoList.add(info);
        info = new KeyValueInfo("联系电话: ", myTaskBaseInfo.getContactPhone());
        dataInfoList.add(info);
        info = new KeyValueInfo("行政区划: ", myTaskBaseInfo.getAdministrativeDivisions());
        dataInfoList.add(info);
        info = new KeyValueInfo("主体名称: ", myTaskBaseInfo.getEnterpriseName());
        dataInfoList.add(info);
        info = new KeyValueInfo("注册地址: ", myTaskBaseInfo.getRegisteredAddress());
        dataInfoList.add(info);
        info = new KeyValueInfo("标准编号: ", myTaskBaseInfo.getStarandNumber());
        dataInfoList.add(info);
        info = new KeyValueInfo("标准名称: ", myTaskBaseInfo.getStandardName());
        dataInfoList.add(info);
        info = new KeyValueInfo("标准类型: ", myTaskBaseInfo.getStandardType());
        dataInfoList.add(info);
        info = new KeyValueInfo("标准状态: ", myTaskBaseInfo.getStandardStatus());
        dataInfoList.add(info);
        info = new KeyValueInfo("发布时间: ", DateUtil.getDateTimeFromMillis(myTaskBaseInfo.getReleaseDate()));
        dataInfoList.add(info);
        info = new KeyValueInfo("网址: ", myTaskBaseInfo.getEnterpriseUrl());
        dataInfoList.add(info);
    }
}
