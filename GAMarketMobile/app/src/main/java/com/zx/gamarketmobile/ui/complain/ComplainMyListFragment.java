package com.zx.gamarketmobile.ui.complain;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.adapter.MyPagerAdapter;
import com.zx.gamarketmobile.ui.base.BaseFragment;
import com.zx.gamarketmobile.ui.mainbase.HomeActivity;
import com.zx.gamarketmobile.view.MyViewPager;

/**
 * Created by Xiangb on 2017/3/22
 * 功能：投诉举报-我的任务
 */
public class ComplainMyListFragment extends BaseFragment {
    private MyViewPager vpCase;
    private TabLayout tbCase;
    private MyPagerAdapter myPagerAdapter;

    public static ComplainMyListFragment newInstance() {
        ComplainMyListFragment fragment = new ComplainMyListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_tab_viewpager, null);
        vpCase = (MyViewPager) view.findViewById(R.id.vp_normal_pager);
        tbCase = (TabLayout) view.findViewById(R.id.tb_normal_layout);
        initView();
        return view;
    }

    private void initView() {
        vpCase.setViewPager(((HomeActivity) getActivity()).mVpContent);
        myPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        myPagerAdapter.addFragment(ComplainDisposeListFragment.newInstance(this, 0), "待办任务");
        tbCase.setVisibility(View.GONE);
//        myPagerAdapter.addFragment(ComplainDisposeListFragment.newInstance(this, 1), "已办任务");
        vpCase.setOffscreenPageLimit(2);
        vpCase.setAdapter(myPagerAdapter);
        tbCase.setupWithViewPager(vpCase);
    }

    public void setTabText(int position, String text) {
        tbCase.getTabAt(position).setText(text);
    }


}
