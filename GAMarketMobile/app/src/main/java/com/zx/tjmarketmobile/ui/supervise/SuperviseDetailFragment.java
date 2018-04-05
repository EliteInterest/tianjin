package com.zx.tjmarketmobile.ui.supervise;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.supervise.SuperviseAdapter;
import com.zx.tjmarketmobile.entity.KeyValueInfo;
import com.zx.tjmarketmobile.entity.SuperviseDetailInfo;
import com.zx.tjmarketmobile.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Stanny On 2016/9/22
 * 功能：监管详情Fragment
 */
public class SuperviseDetailFragment extends BaseFragment {

    private SuperviseDetailInfo mSuperviseDetailInfo;
    private int mDetailFlag;// 0代表任务信息，1代表主体信息，2代表检查指标
    private SuperviseAdapter mSuperviseAdapter;
    private RecyclerView mRvInfo;

    public static SuperviseDetailFragment newInstance(int index, SuperviseDetailInfo task) {
        SuperviseDetailFragment details = new SuperviseDetailFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        details.setArguments(args);
        details.mSuperviseDetailInfo = null;
        details.mSuperviseDetailInfo = task;
        details.mDetailFlag = index;
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.normal_swipe_recycler_view, container, false);
        mRvInfo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        ((SwipeRefreshLayout)view.findViewById(R.id.srl_normal_layout)).setEnabled(false);
        mRvInfo.setLayoutManager(mLinearLayoutManager);
        mSuperviseAdapter = new SuperviseAdapter(getActivity(), getDataList());
        mRvInfo.setAdapter(mSuperviseAdapter);
        return view;
    }

    private List<KeyValueInfo> getDataList() {
        List<KeyValueInfo> list = new ArrayList<>();
        switch (mDetailFlag) {
            case 0:
                list = updateTaskData();
                break;
            case 1:
                list = updateEntity();
                break;
//		case 2:
//			list = updateCheck();
//			break;
            default:
                break;
        }
        return list;
    }

//	private List<KeyValueInfo> updateCheck() {
//		List<KeyValueInfo> list = new ArrayList<KeyValueInfo>();
//		List<CheckInfo> checkList = mSuperviseDetailInfo.checkList;
//		if (null != checkList && !checkList.isEmpty()) {
//			for (int i = 0; i < checkList.size(); i++) {
//				list.add(new KeyValueInfo((i + 1) + "." + checkList.get(i).fItemName, ""));
//			}
//		}
//		return list;
//	}

    private List<KeyValueInfo> updateTaskData() {
        List<KeyValueInfo> list = new ArrayList<KeyValueInfo>();
        KeyValueInfo info = new KeyValueInfo("任务编号: ", mSuperviseDetailInfo.getTaskCode());
        list.add(info);
        info = new KeyValueInfo("任务名称: ", mSuperviseDetailInfo.getTaskName());
        list.add(info);
        info = new KeyValueInfo("任务来源: ", mSuperviseDetailInfo.getTaskResource());
        list.add(info);
        info = new KeyValueInfo("任务时间: ", mSuperviseDetailInfo.getTaskDispatchTime() + " -- "
                + mSuperviseDetailInfo.getTaskDeadlineTime());
        list.add(info);
        info = new KeyValueInfo("办理进度: ", mSuperviseDetailInfo.getTaskProgress());
        list.add(info);
        info = new KeyValueInfo("监管责任人: ", mSuperviseDetailInfo.getDisposePerson());
        list.add(info);
        info = new KeyValueInfo("所属区域: ", mSuperviseDetailInfo.getTaskArea());
        list.add(info);
        info = new KeyValueInfo("任务说明: ", mSuperviseDetailInfo.getTaskRemark());
        list.add(info);
        return list;
    }

    private List<KeyValueInfo> updateEntity() {
        List<KeyValueInfo> list = new ArrayList<KeyValueInfo>();
        KeyValueInfo info = new KeyValueInfo("主体名称: ", mSuperviseDetailInfo.getEntityName());
        list.add(info);
        info = new KeyValueInfo("信用等级: ", mSuperviseDetailInfo.getCreditLevel());
        list.add(info);
        info = new KeyValueInfo("联系方式: ", mSuperviseDetailInfo.getLinkPhone());
        list.add(info);
        info = new KeyValueInfo("法定代表人: ", mSuperviseDetailInfo.getLegalPerson());
        list.add(info);
        info = new KeyValueInfo("营业执照注册号: ", mSuperviseDetailInfo.getBizlicCode());
        list.add(info);
        info = new KeyValueInfo("统一社会信用代码: ", mSuperviseDetailInfo.getfUniscid());
        list.add(info);
        info = new KeyValueInfo("许可证: ", mSuperviseDetailInfo.getLicenses());
        list.add(info);
        info = new KeyValueInfo("地址: ", mSuperviseDetailInfo.getEntityAddress());
        list.add(info);
        return list;
    }

    public void update(SuperviseDetailInfo superviseDetailInfo) {
        mSuperviseDetailInfo = superviseDetailInfo;
        if (mRvInfo != null) {
            mSuperviseAdapter = new SuperviseAdapter(getActivity(), getDataList());
            mRvInfo.setAdapter(mSuperviseAdapter);
        }
    }

}
