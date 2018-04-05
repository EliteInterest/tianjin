package com.zx.tjmarketmobile.ui.caselegal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.CaseInfoEntity;
import com.zx.tjmarketmobile.ui.base.BaseActivity;

/**
 * Created by Xiangb on 2017/4/14.
 * 功能：
 */

public class CaseExcuteActivity extends BaseActivity {

    private ViewPager mVpContent;
    private TabLayout mTabLayout;
    private CaseInfoEntity mEntity;
    private CaseImageFragment caseImageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_tab_viewpager);
        initView();
    }

    private void initView() {

        addToolBar(true);
        hideRightImg();
        setMidText("案件处置");
        if (getIntent().hasExtra("entity")) {
            mEntity = (CaseInfoEntity) getIntent().getSerializableExtra("entity");
        }
        mTabLayout = (TabLayout) findViewById(R.id.tb_normal_layout);
        mVpContent = (ViewPager) findViewById(R.id.vp_normal_pager);
        mTabLayout.setVisibility(View.GONE);
        myPagerAdapter.addFragment(CaseExcuteFragment.newInstance(this, mEntity), "处理");

//        String hjbm = mEntity.getfHjBm();
//        if (hjbm.equals("20101") || hjbm.equals("30101") || hjbm.equals("30104") || hjbm.equals("40101")) {
//            mTabLayout.setVisibility(View.VISIBLE);
//            caseImageFragment = CaseImageFragment.newInstance(this, mEntity);
//            myPagerAdapter.addFragment(caseImageFragment, "图片上传");
//        }

//        mVpContent.setOffscreenPageLimit(2);
        mVpContent.setAdapter(myPagerAdapter);
        mTabLayout.setupWithViewPager(mVpContent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        caseImageFragment.mprvPhoto.onActivityResult(requestCode, resultCode, data);
    }

}
