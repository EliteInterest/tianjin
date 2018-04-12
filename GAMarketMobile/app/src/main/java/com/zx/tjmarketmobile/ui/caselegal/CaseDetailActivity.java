package com.zx.tjmarketmobile.ui.caselegal;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.CaseInfoEntity;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.ui.map.WorkInMapShowActivity;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.Util;

/**
 * Create By Xiangb On 2017/3/10
 * 功能：案件详情界面
 */
public class CaseDetailActivity extends BaseActivity {

    private ViewPager mVpContent;
    private TabLayout mTabLayout;
    private Button btnExcute;
    private Button btnOther;
    private CaseInfoEntity mEntity;
    private boolean showExcute = false;
    private boolean monitor = false;
    public Dialog dialog = null;
    private View diaView = null;
    private int caseType = 1;//1综合执法  2案件延期   3案件销案

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_detail);
        initView();
    }

    private void initView() {
        addToolBar(true);
        setMidText("案件详情");
//        hideRightImg();
        getRightImg().setOnClickListener(this);

        //获取传递的参数
        if (getIntent().hasExtra("entity")) {
            mEntity = (CaseInfoEntity) getIntent().getSerializableExtra("entity");
        }
        if (getIntent().hasExtra("showExcute")) {
            showExcute = getIntent().getBooleanExtra("showExcute", false);
        }
        if (getIntent().hasExtra("monitor")) {
            monitor = getIntent().getBooleanExtra("monitor", false);
        }

        btnExcute = (Button) findViewById(R.id.btnActCase_execute);
        btnExcute.setOnClickListener(this);
        btnOther = (Button) findViewById(R.id.btnActCase_other);
        btnOther.setOnClickListener(this);
        //TODO
        btnOther.setVisibility(View.GONE);

//        if (mEntity.getPROC_DEF_ID_().indexOf("AJYQLC") != -1) {//延期流程
//            btnOther.setVisibility(View.GONE);
//            btnExcute.setText("案件延期-处理");
//            caseType = 2;
//        } else if (mEntity.getPROC_DEF_ID_().indexOf("AJXALC") != -1) {//销案流程
//            btnOther.setVisibility(View.GONE);
//            btnExcute.setText("案件销案-处理");
//            caseType = 3;
//        } else {//综合执法
//            caseType = 1;
//            if ("0".equals(mEntity.getfHjBm()) || "99999".equals(mEntity.getfHjBm())) {
//                btnOther.setVisibility(View.GONE);
//            }
//        }
        //流程处理只能在待办中
        if (showExcute) {
            btnExcute.setVisibility(View.VISIBLE);
        } else {
            btnExcute.setVisibility(View.GONE);
        }
        if (monitor) {
            btnExcute.setVisibility(View.GONE);
            btnOther.setVisibility(View.GONE);
        }


        mTabLayout = (TabLayout) findViewById(R.id.tb_normal_layout);
        mVpContent = (ViewPager) findViewById(R.id.vp_normal_pager);
        myPagerAdapter.addFragment(CaseDetailInfoFragment.newInstance(mEntity.getId()), "基本信息");
        myPagerAdapter.addFragment(CaseDetailFileFragment.newInstance(mEntity.getId()), "案件资料");
        myPagerAdapter.addFragment(CaseDetailFlowFragment.newInstance(mEntity.getId()), "流程轨迹");
        myPagerAdapter.addFragment(CaseDetailChartFragment.newInstance(mEntity, showExcute), "流程图");

        mVpContent.setOffscreenPageLimit(4);
        mVpContent.setAdapter(myPagerAdapter);
        mTabLayout.setupWithViewPager(mVpContent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_right:
                if (mEntity.getLongitude() != null && mEntity.getLatitude() != null
                        &&mEntity.getLongitude().length() > 0 && mEntity.getLatitude().length() > 0) {
                    Intent mapIntent = new Intent(this, WorkInMapShowActivity.class);
                    mapIntent.putExtra("type", ConstStrings.MapType_CaseDetail);
                    mapIntent.putExtra("entity", mEntity);
                    startActivity(mapIntent);
                } else {
                    showToast("当前主体未录入坐标信息");
                }
                break;
            case R.id.btnActCase_execute: {
                //TODO
//                if (caseType == 1) {//综合执法
                Intent intent = new Intent(this, CaseExcuteActivity.class);
                intent.putExtra("entity", mEntity);
                startActivityForResult(intent, 0);
//                } else if (caseType == 2) {//案件延期
//                    Intent intent = new Intent(this, CaseDelayActivity.class);
//                    intent.putExtra("entity", mEntity);
//                    startActivityForResult(intent, 0);
//                } else if (caseType == 3) {//案件销案
//                    Intent intent = new Intent(this, CaseDestoryActivity.class);
//                    intent.putExtra("entity", mEntity);
//                    startActivityForResult(intent, 0);
//                }
                break;
            }
            case R.id.btnActCase_other:
                showDialog();
                break;
            case R.id.pop_dialogues_case_yq://延期
            {
                Intent intent = new Intent(this, CaseDelayActivity.class);
                intent.putExtra("entity", mEntity);
                startActivityForResult(intent, 0);
                dialog.dismiss();
            }
            break;
            case R.id.pop_dialogues_case_cancel:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    //打开弹出dialog
    public void showDialog() {
        if (dialog == null || diaView == null) {
            LayoutInflater inflater = LayoutInflater.from(this);
            diaView = inflater.inflate(R.layout.popu_dialogues_case_excute, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(diaView);
            Button tvCaseYq = (Button) diaView.findViewById(R.id.pop_dialogues_case_yq);
            tvCaseYq.setOnClickListener(this);
            Button tvCaseXa = (Button) diaView.findViewById(R.id.pop_dialogues_case_xa);
            tvCaseXa.setOnClickListener(this);
            Button tvCaseYs = (Button) diaView.findViewById(R.id.pop_dialogues_case_ys);
            tvCaseYs.setOnClickListener(this);
            Button tvCaseQz = (Button) diaView.findViewById(R.id.pop_dialogues_case_qz);
            tvCaseQz.setOnClickListener(this);
            Button tvCancel = (Button) diaView.findViewById(R.id.pop_dialogues_case_cancel);
            tvCancel.setOnClickListener(this);
//            if (!"".equals(mEntity.getfSfyq())) {
//                tvCaseYq.setVisibility(View.GONE);
//            }
//            if (mEntity.isfSfxa()) {
//                tvCaseXa.setVisibility(View.GONE);
//            }
//            if (mEntity.isfSfys()) {
//                tvCaseYs.setVisibility(View.GONE);
//            }
//            if (mEntity.isfSfqzcs()) {
//                tvCaseQz.setVisibility(View.GONE);
//            }
            //如果所有都被隐藏
            if (tvCaseYq.getVisibility() == View.GONE && tvCaseXa.getVisibility() == View.GONE && tvCaseYs.getVisibility() == View.GONE && tvCaseQz.getVisibility() == View.GONE) {
                showToast("无可执行操作");
                return;
            }
            dialog = new AlertDialog.Builder(this).show();
        } else {
            dialog.show();
        }
        Util.dialog_window(diaView, dialog);// 给dialog设置动画以及全屏
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ConstStrings.Request_Success == resultCode && requestCode == 0) {
            btnExcute.setVisibility(View.GONE);
            btnOther.setVisibility(View.GONE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
