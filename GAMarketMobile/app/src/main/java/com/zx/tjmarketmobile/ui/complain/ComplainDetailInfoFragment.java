package com.zx.tjmarketmobile.ui.complain;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.CaseCompDetailInfoAdapter;
import com.zx.tjmarketmobile.entity.ComplainInfoDetailsBean;
import com.zx.tjmarketmobile.entity.KeyValueInfo;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Stanny On 2017/3/22
 * 功能：统计分析基本信息Fragment
 */
public class ComplainDetailInfoFragment extends BaseFragment {

    private CaseCompDetailInfoAdapter mCompAdapter;
    private RecyclerView mRvInfo;
    private int type = 0;
    private List<KeyValueInfo> dataInfoList = new ArrayList<>();

    public static ComplainDetailInfoFragment newInstance(ComplainInfoDetailsBean.BaseInfoBean baseInfoBean, int type) {
        ComplainDetailInfoFragment details = new ComplainDetailInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", baseInfoBean);
        bundle.putInt("type", type);
        details.setArguments(bundle);
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.normal_swipe_recycler_view, container, false);
        mRvInfo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        view.findViewById(R.id.srl_normal_layout).setEnabled(false);
        mRvInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        getDataList();
        mCompAdapter = new CaseCompDetailInfoAdapter(getActivity(), dataInfoList);
        mRvInfo.setAdapter(mCompAdapter);
        return view;
    }

    private void getDataList() {
        ComplainInfoDetailsBean.BaseInfoBean baseInfoBean = (ComplainInfoDetailsBean.BaseInfoBean) getArguments().getSerializable("bean");
        type = getArguments().getInt("type");
        dataInfoList.clear();
        if (type == 0) {//基本信息
            KeyValueInfo info = new KeyValueInfo("投诉举报类别: ", baseInfoBean.getFType());
            dataInfoList.add(info);
            info = new KeyValueInfo("登记时间: ", DateUtil.getDateFromMillis(baseInfoBean.getFRegTime()));
            dataInfoList.add(info);
            info = new KeyValueInfo("登记人: ", baseInfoBean.getFRegName());
            dataInfoList.add(info);
            info = new KeyValueInfo("信息来源: ", baseInfoBean.getfInfoSource());
            dataInfoList.add(info);
            info = new KeyValueInfo("当前状态: ", baseInfoBean.getfStatusString());
            dataInfoList.add(info);
            info = new KeyValueInfo("处理单位: ", baseInfoBean.getfDisposeUnit());
            dataInfoList.add(info);
            info = new KeyValueInfo("投诉方: ", baseInfoBean.getFName());
            dataInfoList.add(info);
            info = new KeyValueInfo("投诉人电话: ", baseInfoBean.getFRegPhone());
            dataInfoList.add(info);
        } else if (type == 1) {//主体信息
            KeyValueInfo info = new KeyValueInfo("名称: ", baseInfoBean.getFEntityName());
            dataInfoList.add(info);
            info = new KeyValueInfo("姓名: ", baseInfoBean.getfEntityPerson());
            dataInfoList.add(info);
            info = new KeyValueInfo("联系电话: ", baseInfoBean.getFEntityPhone());
            dataInfoList.add(info);
            info = new KeyValueInfo("地址: ", baseInfoBean.getFEntityAddress());
            dataInfoList.add(info);
        } else if (type == 2) {//投诉举报信息
            KeyValueInfo info = new KeyValueInfo("办理期限: ", DateUtil.getDateFromMillis(baseInfoBean.getFLimitTime()));
            dataInfoList.add(info);
            info = new KeyValueInfo("销售方式: ", baseInfoBean.getfSalesWay());
            dataInfoList.add(info);
            info = new KeyValueInfo("事发地址: ", baseInfoBean.getFEventAddress());
            dataInfoList.add(info);
            info = new KeyValueInfo("产品分类: ", baseInfoBean.getFConsumeType());
            dataInfoList.add(info);
            info = new KeyValueInfo("投诉内容: ", baseInfoBean.getFContent());
            dataInfoList.add(info);
        } else {//处置信息
            KeyValueInfo info = new KeyValueInfo("反馈内容: ", baseInfoBean.getFFeedbackContent());
            dataInfoList.add(info);
            info = new KeyValueInfo("答复内容: ", baseInfoBean.getfReplyContent());
            dataInfoList.add(info);
            info = new KeyValueInfo("回访结果: ", baseInfoBean.getfReviewResult());
            dataInfoList.add(info);
        }
    }

}
