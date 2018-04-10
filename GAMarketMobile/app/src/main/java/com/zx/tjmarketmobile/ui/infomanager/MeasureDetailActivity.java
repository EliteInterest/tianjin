package com.zx.tjmarketmobile.ui.infomanager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerMeasureLiebiao;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseActivity;

/**
 * Created by zhouzq on 2017/3/23.
 * 监管任务/我的任务/点击列表后跳转到详情的界面
 */

public class MeasureDetailActivity extends BaseActivity {

    private ViewPager mVpContent;
    private TabLayout mTabLayout;
    private Button btnExcute;
    private Button btnOther;
    private InfoManagerMeasureLiebiao.RowsBean mEntity;
    public Dialog dialog = null;
    public static MeasureDetailActivity instance = null;
    private boolean isSendBackVisible = false;
    private MeasureBaseInfoFragment myCheckfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_detail);
        initView();
    }


    private void initView() {
        instance = this;
        addToolBar(true);
        isSendBackVisible = false;
        getRightImg().setOnClickListener(this);
        getRightImg().setVisibility(View.GONE);

        //获取传递的参数
        if (getIntent().hasExtra("entity")) {

            mEntity = (InfoManagerMeasureLiebiao.RowsBean) getIntent().getSerializableExtra("entity");
            setMidText(mEntity.getEnterpriseName());
            initViewPager();
        } else {
            showToast("未获取到信息，请重试");
            finish();
            return;
        }


        btnExcute = (Button) findViewById(R.id.btnActCase_execute);
        btnExcute.setOnClickListener(this);
        btnOther = (Button) findViewById(R.id.btnActCase_other);
        btnOther.setOnClickListener(this);
        btnOther.setVisibility(View.GONE);
        btnExcute.setText("处理");
        btnOther.setText("退回");
        btnExcute.setVisibility(View.GONE);
        btnOther.setVisibility(View.GONE);
    }

    private void initViewPager() {
        mTabLayout = (TabLayout) findViewById(R.id.tb_normal_layout);
        mVpContent = (ViewPager) findViewById(R.id.vp_normal_pager);
            myCheckfragment = MeasureBaseInfoFragment.newInstance(this, mEntity);
        myPagerAdapter.addFragment(myCheckfragment, "基本信息");

        mVpContent.setOffscreenPageLimit(3);
        mVpContent.setAdapter(myPagerAdapter);
        mTabLayout.setVisibility(View.GONE);
        mTabLayout.setupWithViewPager(mVpContent);
        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (position == 0 && index == 0) {
//                    btnExcute.setVisibility(View.VISIBLE);
//                    if (isSendBackVisible) {
//                        btnOther.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    btnExcute.setVisibility(View.GONE);
//                    btnOther.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_right:

                break;
            case R.id.btnActCase_execute:
//                if (mEntity.getFTaskStatus() != null) {

                Intent intent = new Intent(this, DeviceListFragment.class);
                intent.putExtra("entity", mEntity);
                startActivity(intent);

                break;
            case R.id.btnActCase_other:
                if (getIntent().hasExtra("entity")) {
//                    mEntity = (MyTaskListEntity.RowsBean) getIntent().getSerializableExtra("entity");
//                    sendBackTask.loadData(mEntity.getUserId(), mEntity.getId(), userInfo.getId());
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            btnExcute.setVisibility(View.GONE);
            btnOther.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            default:
                break;
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (ConstStrings.Request_Success == resultCode && requestCode == 0) {
//            btnExcute.setVisibility(View.GONE);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (myCheckfragment.addEntityView != null && myCheckfragment.addEntityView.dialog != null) {
//            myCheckfragment.addEntityView.dialog.hide();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (myCheckfragment.addEntityView != null && myCheckfragment.addEntityView.dialog != null) {
//            myCheckfragment.addEntityView.dialog.dismiss();
//        }
    }
}
