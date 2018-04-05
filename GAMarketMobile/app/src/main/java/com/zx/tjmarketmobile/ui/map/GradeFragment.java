package com.zx.tjmarketmobile.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.EntityDetail;
import com.zx.tjmarketmobile.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Stanny On 2016/9/22
 * 功能：等级信息
 */
public class GradeFragment extends BaseFragment {

    private List<EntityDetail.GradeBean> gradeBeans = new ArrayList<>();// 等级信息
    private LinearLayout mElvSkill;
    private String mCreditLevel;

    public static GradeFragment newInstance(int index, List<EntityDetail.GradeBean> creditInfo) {
        GradeFragment details = new GradeFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        details.setArguments(args);
        details.gradeBeans = creditInfo;
        return details;
    }

//    private void addCreditList() {
//        gradeBeans.clear();
//        EntityDetail.BusinessBean.GradeBean creditInfo = new CreditInfo();
//        creditInfo.type = "异常名录";
//        creditInfo.number = 0;
//        gradeBeans.add(creditInfo);
//        creditInfo = new CreditInfo();
//        creditInfo.type = "动产抵押";
//        creditInfo.number = 0;
//        gradeBeans.add(creditInfo);
//        creditInfo = new CreditInfo();
//        creditInfo.type = "投诉举报";
//        creditInfo.number = 0;
//        gradeBeans.add(creditInfo);
//        creditInfo = new CreditInfo();
//        creditInfo.type = "行政处罚";
//        creditInfo.number = 0;
//        gradeBeans.add(creditInfo);
//        creditInfo = new CreditInfo();
//        creditInfo.type = "监管信息";
//        creditInfo.number = 0;
//        gradeBeans.add(creditInfo);
//        creditInfo = new CreditInfo();
//        creditInfo.type = "抽检信息";
//        creditInfo.number = 0;
//        gradeBeans.add(creditInfo);
//        creditInfo = new CreditInfo();
//        creditInfo.type = "计量信息";
//        creditInfo.number = 0;
//        gradeBeans.add(creditInfo);
//        creditInfo = new CreditInfo();
//        creditInfo.type = "认证信息";
//        creditInfo.number = 0;
//        gradeBeans.add(creditInfo);
//
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //添加假数据
//        addCreditList();
        View view = inflater.inflate(R.layout.fragment_credit, container, false);
        mElvSkill = (LinearLayout) view.findViewById(R.id.ll_credit);
        if (gradeBeans != null) {
            for (int i = 0; i < gradeBeans.size(); i++) {
                View item = View.inflate(getActivity(), R.layout.item_eventinfo, null);
                TextView type = (TextView) item.findViewById(R.id.eventKey);
                TextView number = (TextView) item.findViewById(R.id.eventValue);
                EntityDetail.GradeBean cInfo = gradeBeans.get(i);
                type.setText(cInfo.getType());
                number.setText(cInfo.getLevel() + "");
                mElvSkill.addView(item);
            }
        }
        return view;
    }

}
