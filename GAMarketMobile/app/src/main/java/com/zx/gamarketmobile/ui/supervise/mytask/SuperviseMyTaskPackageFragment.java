package com.zx.gamarketmobile.ui.supervise.mytask;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.adapter.supervise.SuperviseDetialPackageAdapter;
import com.zx.gamarketmobile.entity.supervise.MyTaskListEntity;
import com.zx.gamarketmobile.entity.supervise.MyTaskPageEntity;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.http.BaseHttpResult;
import com.zx.gamarketmobile.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzq on 2017/4/6.
 */

public class SuperviseMyTaskPackageFragment extends BaseFragment {


    private SuperviseDetialPackageAdapter superviseDetialPackageAdapter;
    private RecyclerView mRvInfo;
    private String fId = "";
    private ApiData getThisTaskPackageTask = new ApiData(ApiData.HTTP_ID_Supervise_getThisTaskPackageTask);
    private List<MyTaskPageEntity.DataBean> dataInfoList = new ArrayList<>();
    private MyTaskListEntity.RowsBean mEntity;

    public static SuperviseMyTaskPackageFragment newInstance(MyTaskListEntity.RowsBean rowsBean) {
        SuperviseMyTaskPackageFragment details = new SuperviseMyTaskPackageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mEntity", rowsBean);
        details.setArguments(bundle);
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supervise_package, container, false);

        mEntity = (MyTaskListEntity.RowsBean) getArguments().getSerializable("mEntity");

        mRvInfo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        getThisTaskPackageTask.setLoadingListener(this);
        mRvInfo.setLayoutManager(mLinearLayoutManager);
        fId = mEntity.getId();
        superviseDetialPackageAdapter = new SuperviseDetialPackageAdapter(getActivity(), dataInfoList);
        mRvInfo.setAdapter(superviseDetialPackageAdapter);
        getThisTaskPackageTask.loadData(fId, mEntity.getUserId());

        return view;
    }


    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_Supervise_getThisTaskPackageTask:
                dataInfoList.clear();
                MyTaskPageEntity myTaskPageEntity = (MyTaskPageEntity) b.getEntry();
                dataInfoList.addAll(myTaskPageEntity.getData());
                superviseDetialPackageAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

}
