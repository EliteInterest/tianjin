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
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerLicense;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerLicenseDetail;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerLicenseFood;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.manager.UserManager;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzq on 2017/3/23.
 */

public class LisenceBaseInfoFragment extends BaseFragment {

    private CaseCompDetailInfoAdapter mCaseAdapter;
    private String fId = "";
    private InfoManagerLicense.RowsBean mEntity;
    private InfoManagerLicenseFood.RowsBean mEntityFood;
    private static int type = 0;
    private RecyclerView rvBaseInfo;
    private ApiData getTaskBaseInfo = new ApiData(ApiData.HTTP_ID_info_manager_license_detail);

    private List<KeyValueInfo> dataInfoList = new ArrayList<>();

    public static LisenceBaseInfoFragment newInstance(Context context, InfoManagerLicense.RowsBean mEntity, int index) {
        LisenceBaseInfoFragment details = new LisenceBaseInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mEntity", mEntity);
        details.mEntity = mEntity;
        UserManager userManager = new UserManager();
        details.userInfo = userManager.getUser(context);
        details.setArguments(bundle);
        type = index;
        return details;
    }

    public static LisenceBaseInfoFragment newInstance(Context context, InfoManagerLicenseFood.RowsBean mEntity, int index) {
        LisenceBaseInfoFragment details = new LisenceBaseInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mEntity", mEntity);
        details.mEntityFood = mEntity;
        UserManager userManager = new UserManager();
        details.userInfo = userManager.getUser(context);
        details.setArguments(bundle);
        type = index;
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_mytask_baseinfo, container, false);
        if (type == 0)
            mEntityFood = (InfoManagerLicenseFood.RowsBean) getArguments().getSerializable("mEntity");
        else
            mEntity = (InfoManagerLicense.RowsBean) getArguments().getSerializable("mEntity");
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
        if (mEntity != null || mEntityFood != null) {
            if (isVisibleToUser && dataInfoList.size() == 0) {
//                fId = mEntity.getFTaskId();
                if (type == 0)
                    fId = mEntityFood.getEnterpriseId();
                else
                    fId = mEntity.getEnterpriseId();
//                getTaskBaseInfo.loadData(mEntity.getF_GUID(), mEntity.getFTaskStatus(), fId, userInfo.getId());
                getTaskBaseInfo.loadData(fId);
            }
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_info_manager_license_detail:
//                InfoManagerLicenseDetail myTaskBaseInfo = (InfoManagerLicenseDetail) b.getEntry();
//                getDataList(myTaskBaseInfo);

                List<InfoManagerLicenseDetail> myTaskBaseInfo = (List<InfoManagerLicenseDetail>) b.getEntry();
                dataInfoList.clear();
                for (int i = 0; i < myTaskBaseInfo.size(); i++) {
                    InfoManagerLicenseDetail detal = myTaskBaseInfo.get(i);
                    getBaseDataList(detal);
                    mCaseAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    private void getBaseDataList(InfoManagerLicenseDetail myTaskBaseInfo) {
        KeyValueInfo info = new KeyValueInfo("企业类型: ", myTaskBaseInfo.getEnterprise_mode());
        dataInfoList.add(info);
        info = new KeyValueInfo("企业名称: ", myTaskBaseInfo.getEnterpriseName());
        dataInfoList.add(info);
        info = new KeyValueInfo("许可证编号: ", myTaskBaseInfo.getLicNum());
        dataInfoList.add(info);
        info = new KeyValueInfo("企业地址: ", myTaskBaseInfo.getAddress());
        dataInfoList.add(info);
        info = new KeyValueInfo("法人: ", myTaskBaseInfo.getLegalPerson());
        dataInfoList.add(info);
        info = new KeyValueInfo("发证机关: ", myTaskBaseInfo.getIssueOrg());
        dataInfoList.add(info);
        info = new KeyValueInfo("有效期起始: ", DateUtil.getDateTimeFromMillis(myTaskBaseInfo.getExpireStartDate()));
        dataInfoList.add(info);
        info = new KeyValueInfo("有效期截止: ", DateUtil.getDateTimeFromMillis(myTaskBaseInfo.getExpireStartDate()));
        dataInfoList.add(info);
        info = new KeyValueInfo("发证时间: ", DateUtil.getDateTimeFromMillis(myTaskBaseInfo.getIsSueDate()));
        dataInfoList.add(info);
        info = new KeyValueInfo("类型: ", myTaskBaseInfo.getType());
        dataInfoList.add(info);
        info = new KeyValueInfo("企业类别: ", myTaskBaseInfo.getBusiness_category());
        dataInfoList.add(info);
        info = new KeyValueInfo("商业模式: ", myTaskBaseInfo.getBusiness_mode());
        dataInfoList.add(info);
    }

    private void getDataList(InfoManagerLicenseDetail myTaskBaseInfo) {
        dataInfoList.clear();
        KeyValueInfo info = new KeyValueInfo("许可证类型: ", myTaskBaseInfo.getType());
        dataInfoList.add(info);
        info = new KeyValueInfo("许可证号: ", myTaskBaseInfo.getLicNum());
        dataInfoList.add(info);
        info = new KeyValueInfo("发证时间: ", DateUtil.getDateTimeFromMillis(myTaskBaseInfo.getIsSueDate()));
        dataInfoList.add(info);
        info = new KeyValueInfo("有效期起始: ", DateUtil.getDateTimeFromMillis(myTaskBaseInfo.getExpireStartDate()));
        dataInfoList.add(info);
        info = new KeyValueInfo("有效期截止: ", DateUtil.getDateTimeFromMillis(myTaskBaseInfo.getExpireStartDate()));
        dataInfoList.add(info);
        info = new KeyValueInfo("发证机关: ", myTaskBaseInfo.getIssueOrg());
        dataInfoList.add(info);
    }


}
