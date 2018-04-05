package com.zx.tjmarketmobile.ui.supervise;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.R.id;
import com.zx.tjmarketmobile.R.layout;
import com.zx.tjmarketmobile.R.string;
import com.zx.tjmarketmobile.entity.HttpTaskEntity;
import com.zx.tjmarketmobile.entity.SuperviseDetailInfo;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.ui.map.WorkInMapShowActivity;
import com.zx.tjmarketmobile.util.ConstStrings;

import java.io.Serializable;

/**
 * Create By Xiangb On 2016/9/22
 * 功能：任务监管详情
 */
public class SuperviseDetailActivity extends BaseActivity implements OnClickListener {

    private SuperviseDetailInfo mSuperviseDetailInfo;
    private Button mBtnExecute;
    private ViewPager mVpContent;
    private TabLayout mTabLayout;
    private String mStatus = "";
    private String mOperation = "";
    private Dialog mCheckDialog;
    private ApiData mStatusupdateData = new ApiData(ApiData.HTTP_ID_supervisetask_statusupdate);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervise);

        addToolBar(true);
        setMidText("监管详情");
        getRightImg().setOnClickListener(this);

        mStatusupdateData.setLoadingListener(this);
        mTabLayout = (TabLayout) findViewById(id.tb_normal_layout);
        mVpContent = (ViewPager) findViewById(id.vp_normal_pager);
        mBtnExecute = (Button) findViewById(id.btnActSupervise_execute);
        mSuperviseDetailInfo = (SuperviseDetailInfo) getIntent().getSerializableExtra("task");
        mBtnExecute.setOnClickListener(this);
        findViewById(id.btnActSupervise_execute).setOnClickListener(this);

        mStatus = getIntent().getStringExtra("status");

        initialViewPager();
        String type = getIntent().getStringExtra("type");
//		if(!TextUtils.isEmpty(type)) {
//			mBtnExecute.setVisibility(View.GONE);
//		}
        if (null != mSuperviseDetailInfo) {
            if ("待处置".equals(mStatus)) {
                if ("待处置".equals(mSuperviseDetailInfo.getTaskProgress())) {
                    mBtnExecute.setText(string.execute);
                } else {
                    mBtnExecute.setVisibility(View.GONE);
                }
            } else if ("待初审".equals(mStatus)) {
                if ("待初审".equals(mSuperviseDetailInfo.getTaskProgress())) {
                    mBtnExecute.setText(string.chushen);
                } else {
                    mBtnExecute.setVisibility(View.GONE);
                }
            } else if ("待核审".equals(mStatus)) {
                if ("待核审".equals(mSuperviseDetailInfo.getTaskProgress())) {
                    mBtnExecute.setText(string.heshen);
                } else {
                    mBtnExecute.setVisibility(View.GONE);
                }
            } else if ("待终审".equals(mStatus)) {
                if ("待终审".equals(mSuperviseDetailInfo.getTaskProgress())) {
                    mBtnExecute.setText(string.zhongshen);
                } else {
                    mBtnExecute.setVisibility(View.GONE);
                }
            } else if ("任务监控".equals(mStatus)) {
                mBtnExecute.setVisibility(View.GONE);
            }

        }
//		if("待提交".equals(status)) {
//			mBtnExecute.setText("继续执行");
//		}
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initialViewPager() {
        myPagerAdapter.addFragment(SuperviseDetailFragment.newInstance(0, mSuperviseDetailInfo), "任务信息");
        myPagerAdapter.addFragment(SuperviseDetailFragment.newInstance(1, mSuperviseDetailInfo), "主体信息");
        if ("待处置".equals(mStatus)) {
            myPagerAdapter.addFragment(CheckFragment.newInstance(2, mSuperviseDetailInfo.getMenuList()), "检查指标");
        } else {
            myPagerAdapter.addFragment(SuperviseOperateFragment.newInstance(0, mSuperviseDetailInfo), "检查指标");
        }
        myPagerAdapter.addFragment(SuperviseDisposeFragment.newInstance(0, mSuperviseDetailInfo.getTaskLog()), "处置动态");

        mVpContent.setOffscreenPageLimit(3);
        mVpContent.setAdapter(myPagerAdapter);
        mTabLayout.setupWithViewPager(mVpContent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ConstStrings.Request_Success == resultCode && requestCode == 2) {
            mBtnExecute.setVisibility(View.GONE);
            mSuperviseDetailInfo.setTaskProgress("待初审");
            if (myPagerAdapter.getCount() > 0) {
                SuperviseDetailFragment sdFragment = (SuperviseDetailFragment) myPagerAdapter.getItem(0);
                sdFragment.update(mSuperviseDetailInfo);
            }
            setResult(ConstStrings.Request_Success);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.btnActSupervise_execute:
                if ("待处置".equals(mStatus)) {
                    Intent disposeIntent = new Intent(mContext, SuperviseDisposeActivity.class);
                    disposeIntent.putExtra("detailInfo", mSuperviseDetailInfo);
//                    disposeIntent.putExtra("guid", mSuperviseDetailInfo.getGuid());
//                    disposeIntent.putExtra("type", mSuperviseDetailInfo.getTaskProgress());
                    startActivityForResult(disposeIntent, 2);
                } else if ("待初审".equals(mStatus) || "待核审".equals(mStatus) || "待终审".equals(mStatus)) {
                    showDialog();
                }
                break;
            case id.toolbar_right:
                HttpTaskEntity task = new HttpTaskEntity();
                task.setAddress(mSuperviseDetailInfo.getEntityAddress());
                task.setTaskId(mSuperviseDetailInfo.getTaskCode());
                task.setEntityName(mSuperviseDetailInfo.getEntityName());
                task.setEntityGuid(mSuperviseDetailInfo.getEntityId());
                task.setTaskName(mSuperviseDetailInfo.getTaskName());
                task.setTaskType(0);
                task.setWkid(4490);
                task.setLatitude(mSuperviseDetailInfo.getLatitude());
                task.setLongitude(mSuperviseDetailInfo.getLongitude());
//			task.setTimeType(mSuperviseDetailInfo.timeType);
                Intent intent = new Intent(this, WorkInMapShowActivity.class);
                intent.putExtra("type", ConstStrings.MapType_TaskDetail);
                intent.putExtra("entity", (Serializable) task);
                startActivity(intent);
                break;
        }
    }


    private void showDialog() {
        if (mCheckDialog == null) {
            mCheckDialog = new Dialog(mContext);
            mCheckDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mCheckDialog.setContentView(layout.dialog_chushen);
            mCheckDialog.setCanceledOnTouchOutside(true);
        }

        final RadioGroup rgTransgress = (RadioGroup) mCheckDialog.findViewById(id.rgDialogChushen_transgress);
        final EditText edtRemark = (EditText) mCheckDialog.findViewById(id.edtDialogChushen_remark);
        mCheckDialog.findViewById(id.btnDialogChushen_cancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckDialog.dismiss();
            }
        });
        mCheckDialog.findViewById(id.btnDialogChushen_submit).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckDialog.dismiss();
                String remark = edtRemark.getText().toString();
                int checkedId = rgTransgress.getCheckedRadioButtonId();
                switch (checkedId) {
                    case id.rbDialogChushen_transgress:
                        mOperation = "0";
                        if (remark == null || remark.isEmpty()) {
                            remark = "同意";
                        } else {
                            remark = "同意-" + remark;
                        }
                        break;
                    case id.rbDialogChushen_notransgress:
                        mOperation = "1";
                        if (remark == null || remark.isEmpty()) {
                            remark = "退回";
                        } else {
                            remark = "退回-" + remark;
                        }
                        break;
                    default:
                        break;
                }
                if (mSuperviseDetailInfo.getTaskResource().equals("临时任务") && mOperation.equals("0")) {
                    mStatus = "待终审";
                } else if (mSuperviseDetailInfo.getTaskResource().equals("临时任务") && mOperation.equals("1")) {
                    mStatus = "待处置";
                } else if (mStatus.equals("待初审") && mSuperviseDetailInfo.getfReviewUserId().length() == 0) {
                    mStatus = "待终审";
                } else if (mStatus.equals("待核审") && mSuperviseDetailInfo.getfLeaderUserId().length() == 0) {
                    mStatus = "待终审";
                }
                mStatusupdateData.loadData(mOperation, mSuperviseDetailInfo.getGuid(), mStatus, remark, userInfo.getId());
            }
        });
        mCheckDialog.show();
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_supervisetask_statusupdate:
                if (b.isSuccess()) {
                    showToast("操作成功");
                    mBtnExecute.setVisibility(View.GONE);
                    if ("待初审".equals(mStatus)) {
                        if ("0".equals(mOperation)) {
                            mSuperviseDetailInfo.setTaskProgress("待核审");
                        } else {
                            mSuperviseDetailInfo.setTaskProgress("待处置");
                        }
                    } else if ("待核审".equals(mStatus)) {
                        if ("0".equals(mOperation)) {
                            mSuperviseDetailInfo.setTaskProgress("待终审");
                        } else {
                            mSuperviseDetailInfo.setTaskProgress("待初审");
                        }
                    } else if ("待终审".equals(mStatus)) {
                        if ("0".equals(mOperation)) {
                            mSuperviseDetailInfo.setTaskProgress("已办结");
                        } else {
                            mSuperviseDetailInfo.setTaskProgress("待核审");
                        }
                    }

                    if (myPagerAdapter.getCount() > 0) {
                        SuperviseDetailFragment sdFragment = (SuperviseDetailFragment) myPagerAdapter.getItem(0);
                        sdFragment.update(mSuperviseDetailInfo);
                    }
                    setResult(ConstStrings.Request_Success);
                }
                break;
            default:
                break;
        }
    }
}
