package com.zx.gamarketmobile.ui.supervise.mytask;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.adapter.CaseCompDetailInfoAdapter;
import com.zx.gamarketmobile.entity.KeyValueInfo;
import com.zx.gamarketmobile.entity.supervise.MyTaskBaseInfo;
import com.zx.gamarketmobile.entity.supervise.MyTaskListEntity;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.http.BaseHttpResult;
import com.zx.gamarketmobile.manager.UserManager;
import com.zx.gamarketmobile.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzq on 2017/3/23.
 */

public class SuperviseMyTaskBaseInfoFragment extends BaseFragment {

    private CaseCompDetailInfoAdapter mCaseAdapter;
    private String fId = "";
    private MyTaskListEntity.RowsBean mEntity;
    private RecyclerView rvBaseInfo;
    private ApiData getTaskBaseInfo = new ApiData(ApiData.HTTP_ID_superviseTaskBaseInfo);

    private List<KeyValueInfo> dataInfoList = new ArrayList<>();

    public static SuperviseMyTaskBaseInfoFragment newInstance(Context context, MyTaskListEntity.RowsBean mEntity) {
        SuperviseMyTaskBaseInfoFragment details = new SuperviseMyTaskBaseInfoFragment();
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
        mEntity = (MyTaskListEntity.RowsBean) getArguments().getSerializable("mEntity");

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
                fId = mEntity.getFTaskId();
//                getTaskBaseInfo.loadData(mEntity.getF_GUID(), mEntity.getFTaskStatus(), fId, userInfo.getId());
                getTaskBaseInfo.loadData(fId);
            }
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_superviseTaskBaseInfo:
                MyTaskBaseInfo myTaskBaseInfo = (MyTaskBaseInfo) b.getEntry();
                getDataList(myTaskBaseInfo);
                mCaseAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private void getDataList(MyTaskBaseInfo myTaskBaseInfo) {
        dataInfoList.clear();
        KeyValueInfo info = new KeyValueInfo("任务编号: ", myTaskBaseInfo.getFTaskId());
        dataInfoList.add(info);
        info = new KeyValueInfo("任务名称: ", myTaskBaseInfo.getFTaskName());
        dataInfoList.add(info);
        info = new KeyValueInfo("任务状态: ", myTaskBaseInfo.getFTaskStatus());
        dataInfoList.add(info);
        info = new KeyValueInfo("创建部门: ", myTaskBaseInfo.getFCreateDepartment());
        dataInfoList.add(info);
        info = new KeyValueInfo("创建人: ", myTaskBaseInfo.getFCreateName());
        dataInfoList.add(info);
        info = new KeyValueInfo("提醒时间: ", myTaskBaseInfo.getFTipsTime());
        dataInfoList.add(info);
        info = new KeyValueInfo("创建时间: ", myTaskBaseInfo.getFCreateTime());
        dataInfoList.add(info);
        info = new KeyValueInfo("截止时间: ", myTaskBaseInfo.getFDeadline());
        dataInfoList.add(info);
        info = new KeyValueInfo("任务说明: ", myTaskBaseInfo.getfRemark());
        dataInfoList.add(info);
    }


}
