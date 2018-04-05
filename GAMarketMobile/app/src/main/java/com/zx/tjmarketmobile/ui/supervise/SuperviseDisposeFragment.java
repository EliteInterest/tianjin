package com.zx.tjmarketmobile.ui.supervise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.SuperviseDisposeInfo;
import com.zx.tjmarketmobile.ui.base.BaseFragment;

import java.util.List;

/**
 * Create By Stanny On 2016/9/22
 * 功能：监管。。
 */
public class SuperviseDisposeFragment extends BaseFragment {

    private LinearLayout mLlDetail;
    private List<SuperviseDisposeInfo> mDisposeList;

    public static SuperviseDisposeFragment newInstance(int index, List<SuperviseDisposeInfo> taskLog) {
        SuperviseDisposeFragment details = new SuperviseDisposeFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        details.setArguments(args);
        details.mDisposeList = taskLog;
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dispose, container, false);
        mLlDetail = (LinearLayout) view.findViewById(R.id.llFmDispose_detail);
        updateView();
        return view;
    }

    private void updateView() {
        mLlDetail.removeAllViews();
        if (null != mDisposeList && !mDisposeList.isEmpty()) {
            for (SuperviseDisposeInfo disposeInfo : mDisposeList) {
                if (null != disposeInfo) {
                    updateItem(disposeInfo);
                }
            }
        }
    }

    private void updateItem(SuperviseDisposeInfo disposeInfo) {
        View view = View.inflate(getActivity(), R.layout.item_dispose, null);
        TextView tvTime = (TextView) view.findViewById(R.id.tvItemDispose_time);
        TextView tvPersion = (TextView) view.findViewById(R.id.tvItemDispose_persion);
        TextView tvOperate = (TextView) view.findViewById(R.id.tvItemDispose_operate);
        TextView tvRemark = (TextView) view.findViewById(R.id.tvItemDispose_remark);
        tvTime.setText(disposeInfo.fHandleTime);
        tvPersion.setText(disposeInfo.fRealName);
        tvOperate.setText(disposeInfo.fHandleOpt);
        tvRemark.setText(disposeInfo.fHandleRemark);
        mLlDetail.addView(view);
    }
}
