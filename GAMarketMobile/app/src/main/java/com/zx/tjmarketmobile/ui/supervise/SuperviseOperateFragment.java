package com.zx.tjmarketmobile.ui.supervise;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.CheckInfo;
import com.zx.tjmarketmobile.entity.SuperviseDetailInfo;
import com.zx.tjmarketmobile.ui.base.BaseFragment;

import java.util.List;

/**
 * Create By Stanny On 2016/9/22
 * 功能：监管指标Fragment
 */
public class SuperviseOperateFragment extends BaseFragment {

    private SuperviseDetailInfo mSuperviseDetailInfo;
    private ScrollView svCheck;
    private LinearLayout llCheck;
    private TextView tvResult;

    public static SuperviseOperateFragment newInstance(int index, SuperviseDetailInfo task) {
        SuperviseOperateFragment details = new SuperviseOperateFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        details.setArguments(args);
        details.mSuperviseDetailInfo = task;
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supervisedispose, container, false);
        svCheck = (ScrollView) view.findViewById(R.id.svFmSuperviseDispose_check);
        llCheck = (LinearLayout) view.findViewById(R.id.llFmSuperviseDispose_check);
        tvResult = (TextView) view.findViewById(R.id.tvFmSuperviseDispose_result);
        addView();
        return view;
    }

    private void addView() {
        if (mSuperviseDetailInfo == null || mSuperviseDetailInfo.getCheckList() == null) {
            return;
        }
        List<CheckInfo> chickList = mSuperviseDetailInfo.getCheckList();
        if (mSuperviseDetailInfo.getResult() == null || mSuperviseDetailInfo.getResult().toString().length() == 0) {
            tvResult.setText("无处置结论");
        } else {
            tvResult.setText("处置结论：" + mSuperviseDetailInfo.getResult());
        }
        llCheck.removeAllViews();
        View viewTitle = View.inflate(getActivity(), R.layout.item_check_fordispose, null);
//		LinearLayout llMainTitle = (LinearLayout) viewTitle.findViewById(R.id.ll_main);
        TextView tvNameTitle = (TextView) viewTitle.findViewById(R.id.tvItemComplain_name_fordispose);
        tvNameTitle.setText("指标名称");
        tvNameTitle.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        tvNameTitle.setGravity(Gravity.CENTER);
//		View viewTitleSeperator = (View) viewTitle.findViewById(R.id.view_seperator);
//		int wTitle = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
//		int hTile = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
//		llMainTitle.measure(wTitle, hTile);
//		int titleHeight = llMainTitle.getMeasuredHeight();
//		RelativeLayout.LayoutParams lpTitle = (RelativeLayout.LayoutParams)viewTitleSeperator.getLayoutParams();
//		lpTitle.height = titleHeight;
//		viewTitleSeperator.setLayoutParams(lpTitle);
        viewTitle.findViewById(R.id.rg_flag_fordispose).setVisibility(View.GONE);
        viewTitle.findViewById(R.id.tv_foryes).setVisibility(View.VISIBLE);
        viewTitle.findViewById(R.id.tv_forno).setVisibility(View.VISIBLE);
        llCheck.addView(viewTitle);
        for (int i = 0; i < chickList.size(); i++) {
            final CheckInfo checkInfo = chickList.get(i);
            View view = View.inflate(getActivity(), R.layout.item_check_fordispose, null);
            TextView tvName = (TextView) view.findViewById(R.id.tvItemComplain_name_fordispose);
            final RadioGroup rgCheck = (RadioGroup) view.findViewById(R.id.rg_flag_fordispose);
            final RadioButton rbCheckYes = (RadioButton) view.findViewById(R.id.rb_flag_yes_fordispose);
            final RadioButton rbCheckNo = (RadioButton) view.findViewById(R.id.rb_flag_no_fordispose);
            LinearLayout llNum = (LinearLayout) view.findViewById(R.id.llItemCheck_right_fordispose);
            EditText edtNum = (EditText) view.findViewById(R.id.edtItemCheck_num_fordispose);
            edtNum.setEnabled(false);
            rgCheck.setEnabled(false);
            rbCheckYes.setEnabled(false);
            rbCheckNo.setEnabled(false);
            if ("0".equals(checkInfo.getType())) {
                rgCheck.setVisibility(View.VISIBLE);
                llNum.setVisibility(View.GONE);
                if ("0".equals(checkInfo.getCheckResult())) {
                    rbCheckYes.setChecked(false);
                    rbCheckNo.setChecked(true);
                } else {
                    rbCheckYes.setChecked(true);
                    rbCheckNo.setChecked(false);
                }
            } else if ("1".equals(checkInfo.getType())) {
                rgCheck.setVisibility(View.GONE);
                llNum.setVisibility(View.VISIBLE);
                edtNum.setText(checkInfo.getMax());
            }
            tvName.setText((i + 1) + "." + checkInfo.getItemName());
            llCheck.addView(view);
        }
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        svCheck.measure(w, h);
        int svCheckHeight = svCheck.getMeasuredHeight();
        tvResult.measure(w, h);
        int tvResultHeight = tvResult.getMeasuredHeight();
        if (svCheckHeight - tvResultHeight < 1000) {
            svCheck.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 0.0f));
        } else {
            svCheck.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f));
        }
    }
}