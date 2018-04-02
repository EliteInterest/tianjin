package com.zx.gamarketmobile.ui.complain;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.entity.ComplainInfoEntity;
import com.zx.gamarketmobile.ui.base.BaseActivity;
import com.zx.gamarketmobile.ui.map.WorkInMapShowActivity;
import com.zx.gamarketmobile.util.ConstStrings;

/**
 * Create By Xiangb On 2017/3/22
 * 功能：统计分析详情界面
 */
public class ComplainDetailActivity extends BaseActivity {

    private ViewPager mVpContent;
    private TabLayout mTabLayout;
    private Button btnExcute;
    private ComplainInfoEntity mEntity;
    private boolean showExcute = false;
    private boolean monitor = false;
    public Dialog dialog = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_detail);
        initView();
    }

    private void initView() {
        addToolBar(true);
        setMidText("投诉举报详情");
        getRightImg().setOnClickListener(this);

        //获取传递的参数
        if (getIntent().hasExtra("entity")) {
            mEntity = (ComplainInfoEntity) getIntent().getSerializableExtra("entity");
        }
        if (getIntent().hasExtra("showExcute")) {
            showExcute = getIntent().getBooleanExtra("showExcute", false);
        }
        if (getIntent().hasExtra("monitor")) {
            monitor = getIntent().getBooleanExtra("monitor", false);
        }

        btnExcute = (Button) findViewById(R.id.btnActComp_execute);
        btnExcute.setOnClickListener(this);

        //流程处理只能在待办中
        if (showExcute) {
            btnExcute.setVisibility(View.VISIBLE);
        } else {
            btnExcute.setVisibility(View.GONE);
        }
        if (monitor) {
            btnExcute.setVisibility(View.GONE);
        }
//        if (mEntity.getfStatus().equals("已办结") || mEntity.getfStatus().equals("未受理")) {
//            btnExcute.setVisibility(View.GONE);
//        }


        mTabLayout = (TabLayout) findViewById(R.id.tb_normal_layout);
        mVpContent = (ViewPager) findViewById(R.id.vp_normal_pager);
        myPagerAdapter.addFragment(ComplainDetailInfoFragment.newInstance(mEntity.getFGuid()), "基本信息");
        myPagerAdapter.addFragment(ComplainDetailFlowFragment.newInstance(mEntity.getFGuid()), "处置动态");

        mVpContent.setOffscreenPageLimit(2);
        mVpContent.setAdapter(myPagerAdapter);
        mTabLayout.setupWithViewPager(mVpContent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_right:
                if (mEntity.getSInfo().getX() > 0 && mEntity.getSInfo().getY() > 0) {
                    Intent mapIntent = new Intent(this, WorkInMapShowActivity.class);
                    mapIntent.putExtra("type", ConstStrings.MapType_CompDetail);
                    mapIntent.putExtra("entity", mEntity);
                    startActivity(mapIntent);
                } else {
                    showToast("当前案件未录入坐标信息");
                }
                break;
            case R.id.btnActComp_execute: {
                Intent intent = new Intent(this, ComplainExcuteActivity.class);
                intent.putExtra("entity", mEntity);
                startActivityForResult(intent, 0);
                break;
            }
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ConstStrings.Request_Success == resultCode && requestCode == 0) {
            btnExcute.setVisibility(View.GONE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
