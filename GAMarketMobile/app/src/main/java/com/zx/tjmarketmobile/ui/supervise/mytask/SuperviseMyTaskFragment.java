package com.zx.tjmarketmobile.ui.supervise.mytask;

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
 * Created by zhouzq on 2017/3/23.
 */

public class SuperviseMyTaskFragment extends BaseFragment{
    private MyViewPager vpCase;
    private TabLayout tbCase;
    private MyPagerAdapter myPagerAdapter;

    public static SuperviseMyTaskFragment newInstance() {
        SuperviseMyTaskFragment fragment = new SuperviseMyTaskFragment();
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
        myPagerAdapter.addFragment(SuperviseMyTaskListFragment.newInstance(0), "我的待办");
        myPagerAdapter.addFragment(SuperviseMyTaskListFragment.newInstance(1), "我的已办");
        vpCase.setOffscreenPageLimit(2);
        vpCase.setAdapter(myPagerAdapter);
        tbCase.setupWithViewPager(vpCase);
        vpCase.setCurrentItem(0);
    }
}
