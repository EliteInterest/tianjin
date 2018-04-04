package com.zx.gamarketmobile.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.entity.CreditInfo;
import com.zx.gamarketmobile.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Stanny On 2016/9/22
 * 功能：信用信息
 */
public class CreditFragment extends BaseFragment {

    private List<CreditInfo> mCreditInfo = new ArrayList<>();// 信用信息
    private LinearLayout mElvSkill;
    private String mCreditLevel;

    public static CreditFragment newInstance(int index, List<CreditInfo> creditInfo, String level) {
        CreditFragment details = new CreditFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        details.setArguments(args);
        details.mCreditInfo = creditInfo;
        details.mCreditLevel = level;
        return details;
    }

    private void addCreditList() {
        mCreditInfo.clear();
        CreditInfo creditInfo = new CreditInfo();
        creditInfo.type = "异常名录";
        creditInfo.number = 0;
        mCreditInfo.add(creditInfo);
        creditInfo = new CreditInfo();
        creditInfo.type = "动产抵押";
        creditInfo.number = 0;
        mCreditInfo.add(creditInfo);
        creditInfo = new CreditInfo();
        creditInfo.type = "投诉举报";
        creditInfo.number = 0;
        mCreditInfo.add(creditInfo);
        creditInfo = new CreditInfo();
        creditInfo.type = "行政处罚";
        creditInfo.number = 0;
        mCreditInfo.add(creditInfo);
        creditInfo = new CreditInfo();
        creditInfo.type = "监管信息";
        creditInfo.number = 0;
        mCreditInfo.add(creditInfo);
        creditInfo = new CreditInfo();
        creditInfo.type = "抽检信息";
        creditInfo.number = 0;
        mCreditInfo.add(creditInfo);
        creditInfo = new CreditInfo();
        creditInfo.type = "计量信息";
        creditInfo.number = 0;
        mCreditInfo.add(creditInfo);
        creditInfo = new CreditInfo();
        creditInfo.type = "认证信息";
        creditInfo.number = 0;
        mCreditInfo.add(creditInfo);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //添加假数据
        addCreditList();
        View view = inflater.inflate(R.layout.fragment_credit, container, false);
        mElvSkill = (LinearLayout) view.findViewById(R.id.ll_credit);
        for (int i = 0; i < mCreditInfo.size(); i++) {
            View item = View.inflate(getActivity(), R.layout.item_eventinfo, null);
            TextView type = (TextView) item.findViewById(R.id.eventKey);
            TextView number = (TextView) item.findViewById(R.id.eventValue);
            CreditInfo cInfo = mCreditInfo.get(i);
            type.setText(cInfo.type);
            number.setText(cInfo.number+"");
            mElvSkill.addView(item);
        }
        return view;
    }

}
