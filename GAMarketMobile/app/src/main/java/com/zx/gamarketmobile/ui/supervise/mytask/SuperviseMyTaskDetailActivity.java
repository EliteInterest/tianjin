package com.zx.gamarketmobile.ui.supervise.mytask;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.entity.supervise.MyTaskListEntity;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.http.BaseHttpResult;
import com.zx.gamarketmobile.ui.base.BaseActivity;

/**
 * Created by zhouzq on 2017/3/23.
 * 监管任务/我的任务/点击列表后跳转到详情的界面
 */

public class SuperviseMyTaskDetailActivity extends BaseActivity {

    private ViewPager mVpContent;
    private TabLayout mTabLayout;
    private Button btnExcute;
    private Button btnOther;
    private MyTaskListEntity.RowsBean mEntity;
    public Dialog dialog = null;
    private int index;//0待办  1已办
    private int type;//1任务监控
    public static SuperviseMyTaskDetailActivity instance = null;
    //    private ApiData getIsBackTask = new ApiData(ApiData.HTTP_ID_superviseIsBackTaskInfo);
    private ApiData sendBackTask = new ApiData(ApiData.HTTP_ID_superviseSendTaskBack);
    private boolean isSendBackVisible = false;
    private SuperviseMyTaskCheckFragment myCheckfragment;

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
            mEntity = (MyTaskListEntity.RowsBean) getIntent().getSerializableExtra("entity");
            index = getIntent().getIntExtra("index", 0);
            type = getIntent().getIntExtra("type", 0);
            setMidText(mEntity.getDepartmentId());
//            getIsBackTask.setLoadingListener(this);
            sendBackTask.setLoadingListener(this);
//            getIsBackTask.loadData(mEntity.getF_GUID(), mEntity.getFTaskId(), userInfo.getId());
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
        if (index == 1) {
            btnExcute.setVisibility(View.GONE);
            btnOther.setVisibility(View.GONE);
        }
//        if ("0".equals(mEntity.getfIsBack())) {
        if ("0".equals(mEntity.getOverdue())) {
            btnOther.setVisibility(View.GONE);
        }
    }

    private void initViewPager() {
        mTabLayout = (TabLayout) findViewById(R.id.tb_normal_layout);
        mVpContent = (ViewPager) findViewById(R.id.vp_normal_pager);
        myCheckfragment = SuperviseMyTaskCheckFragment.newInstance(mEntity, index, type);
        myPagerAdapter.addFragment(SuperviseMyTaskBaseInfoFragment.newInstance(this, mEntity), "基本信息");
//        myPagerAdapter.addFragment(SuperviseMyTaskFlowFragment.newInstance(this, mEntity), "流程轨迹");
        myPagerAdapter.addFragment(myCheckfragment, "检查主体");
//        myPagerAdapter.addFragment(SuperviseMyTaskPackageFragment.newInstance(mEntity), "打包任务");

        mVpContent.setOffscreenPageLimit(3);
        mVpContent.setAdapter(myPagerAdapter);
        mTabLayout.setupWithViewPager(mVpContent);
        mVpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0 && index == 0) {
                    btnExcute.setVisibility(View.VISIBLE);
                    if (isSendBackVisible) {
                        btnOther.setVisibility(View.VISIBLE);
                    }
                } else {
                    btnExcute.setVisibility(View.GONE);
                    btnOther.setVisibility(View.GONE);
                }
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
                    if (mEntity.getStatus() == 3) {//待处置
                        mVpContent.setCurrentItem(2);
                    } else if (mEntity.getStatus() == 1)//"审核未通过"))????
                     {
                        showToast("当前状态审核未通过，请在服务端重新修改提交！");
                    } else {
                        Intent intent = new Intent(this, SuperviseMyTaskExcuteActivity.class);
                        intent.putExtra("entity", mEntity);
                        startActivity(intent);
                    }
//                }
                break;
            case R.id.btnActCase_other:
                if (getIntent().hasExtra("entity")) {
                    mEntity = (MyTaskListEntity.RowsBean) getIntent().getSerializableExtra("entity");
                    sendBackTask.loadData(mEntity.getUserId(), mEntity.getId(), userInfo.getId());
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
            case ApiData.HTTP_ID_superviseIsBackTaskInfo:
                if (b.isSuccess()) {
                    btnOther.setVisibility(View.VISIBLE);
                    isSendBackVisible = true;
                }
                break;
            case ApiData.HTTP_ID_superviseSendTaskBack:
                if (b.isSuccess()) {
                    showToast(b.getMessage());
                    finish();
                }
                break;
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
        if (myCheckfragment.addEntityView != null && myCheckfragment.addEntityView.dialog != null) {
            myCheckfragment.addEntityView.dialog.hide();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myCheckfragment.addEntityView != null && myCheckfragment.addEntityView.dialog != null) {
            myCheckfragment.addEntityView.dialog.dismiss();
        }
    }
}
