package com.zx.tjmarketmobile.ui.caselegal;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.MyPagerAdapter;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.ui.mainbase.HomeActivity;
import com.zx.tjmarketmobile.view.MyViewPager;

/**
 * Created by Xiangb on 2017/3/13.
 * 功能：案件处理界面
 */
public class CaseMyListFragment extends BaseFragment {
    private MyViewPager vpCase;
    private TabLayout tbCase;
    private MyPagerAdapter myPagerAdapter;

    public static CaseMyListFragment newInstance() {
        CaseMyListFragment fragment = new CaseMyListFragment();
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
        myPagerAdapter.addFragment(CaseDisposeListFragment.newInstance(this, 0), "待办任务");
        myPagerAdapter.addFragment(CaseDisposeListFragment.newInstance(this, 1), "已办任务");
        vpCase.setOffscreenPageLimit(2);
        vpCase.setAdapter(myPagerAdapter);
        tbCase.setupWithViewPager(vpCase);
    }

    public void setTabText(int position, String text) {
        tbCase.getTabAt(position).setText(text);
    }


}
