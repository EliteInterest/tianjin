package com.zx.gamarketmobile.ui.complain;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.entity.ComplainInfoEntity;
import com.zx.gamarketmobile.entity.KeyValueInfo;
import com.zx.gamarketmobile.entity.SelectPopDataList;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.http.BaseHttpResult;
import com.zx.gamarketmobile.listener.ICommonListener;
import com.zx.gamarketmobile.ui.base.BaseActivity;
import com.zx.gamarketmobile.util.ConstStrings;
import com.zx.gamarketmobile.util.Util;
import com.zx.gamarketmobile.view.SuperviseSelectPopuView;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2017/3/22
 * 功能：投诉举报处置
 */

public class ComplainExcuteActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private LinearLayout llExcute;//操作
    private LinearLayout llShunt;//分流
    private LinearLayout llAssign;//指派
    private LinearLayout llResult;//调解结果
    private LinearLayout llMode;//调解方式
    private LinearLayout llSaveMonney;//挽回损失
    private LinearLayout llArgueMonney;//争议金额
    private LinearLayout llTjNum;//调解号
    private LinearLayout llDate;//时间
    private LinearLayout llTscz;//投诉操作
    private LinearLayout llCheckDate;//核查日期
    private EditText etTjOpinion;//调解情况
    private RadioGroup rgExcute;//操作
    private RelativeLayout rlShunt;//分流
    private TextView tvShunt;//分流
    private RelativeLayout rlAssign;//指派
    private TextView tvAssign;//指派
    private RelativeLayout rlResult;//调解结果
    private TextView tvResult;//调解结果
    private RelativeLayout rlMode;//调解方式
    private TextView tvMode;//调解方式
    private TextView tvDate;//调解时间
    private Button btnExcute;
    private EditText etSaveMonney;//挽回损失
    private EditText etArgueMonney;//争议金额
    private EditText etTjNum;//调解号
    private RelativeLayout rlTscz;//投诉操作
    private TextView tvTscz;//投诉操作
    private TextView tvCheckDate;//核查日期
    private EditText etCheckOpinion;//核查情况

    private SuperviseSelectPopuView mPopShunt;//分流
    private List<SelectPopDataList> selectListShunt = new ArrayList<>();//分流
    private SuperviseSelectPopuView mPopAssign;//指派
    private List<SelectPopDataList> selectListAssign = new ArrayList<>();//指派
    private SuperviseSelectPopuView mPopResult;//结果
    private List<SelectPopDataList> selectListResult = new ArrayList<>();//结果
    private SuperviseSelectPopuView mPopMode;//方式
    private List<SelectPopDataList> selectListMode = new ArrayList<>();//方式
    private SuperviseSelectPopuView mPopTscz;//投诉操作
    private List<SelectPopDataList> selectListTscz = new ArrayList<>();//投诉操作

    private ComplainInfoEntity mEntity;
    private String fSlrId = "";//受理人id

    private ApiData getAllDept = new ApiData(ApiData.HTTP_ID_getAllUserDept);
    private ApiData getUserByDept = new ApiData(ApiData.HTTP_ID_getUsersByDept);
    private ApiData compHandle = new ApiData(ApiData.HTTP_ID_compFlowhandle);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_excute);

        initView();
    }

    private void initView() {
        addToolBar(true);
        mEntity = (ComplainInfoEntity) getIntent().getSerializableExtra("entity");
        setMidText(mEntity.getFType());
        hideRightImg();

        llExcute = (LinearLayout) findViewById(R.id.ll_comp_excute);
        llShunt = (LinearLayout) findViewById(R.id.ll_comp_shunt);
        llAssign = (LinearLayout) findViewById(R.id.ll_comp_assign);
        llResult = (LinearLayout) findViewById(R.id.ll_comp_tjResult);
        llMode = (LinearLayout) findViewById(R.id.ll_comp_tjMode);
        llSaveMonney = (LinearLayout) findViewById(R.id.ll_comp_saveMonney);
        llArgueMonney = (LinearLayout) findViewById(R.id.ll_comp_argueMonney);
        llTjNum = (LinearLayout) findViewById(R.id.ll_comp_tjNum);
        llDate = (LinearLayout) findViewById(R.id.ll_comp_date);
        llCheckDate = (LinearLayout) findViewById(R.id.ll_comp_checkDate);
        etTjOpinion = (EditText) findViewById(R.id.et_comp_tjOpinion);
        rgExcute = (RadioGroup) findViewById(R.id.rg_comp_excute);
        rlShunt = (RelativeLayout) findViewById(R.id.rl_comp_shunt);
        rlAssign = (RelativeLayout) findViewById(R.id.rl_comp_assign);
        rlResult = (RelativeLayout) findViewById(R.id.rl_comp_tjResult);
        rlMode = (RelativeLayout) findViewById(R.id.rl_comp_tjMode);
        tvAssign = (TextView) findViewById(R.id.tv_comp_assign);
        tvResult = (TextView) findViewById(R.id.tv_comp_tjResult);
        tvShunt = (TextView) findViewById(R.id.tv_comp_shunt);
        tvMode = (TextView) findViewById(R.id.tv_comp_tjMode);
        tvDate = (TextView) findViewById(R.id.tv_comp_date);
        btnExcute = (Button) findViewById(R.id.btn_comp_excute);
        etSaveMonney = (EditText) findViewById(R.id.et_comp_saveMonney);
        etArgueMonney = (EditText) findViewById(R.id.et_comp_argueMonney);
        etTjNum = (EditText) findViewById(R.id.et_comp_tjNum);
        llTscz = (LinearLayout) findViewById(R.id.ll_comp_tscz);
        rlTscz = (RelativeLayout) findViewById(R.id.rl_comp_tscz);
        tvTscz = (TextView) findViewById(R.id.tv_comp_tscz);
        tvCheckDate = (TextView) findViewById(R.id.tv_comp_checkDate);
        etCheckOpinion = (EditText) findViewById(R.id.et_comp_checkOpinion);

        rgExcute.setOnCheckedChangeListener(this);
        rlAssign.setOnClickListener(this);
        rlShunt.setOnClickListener(this);
        rlResult.setOnClickListener(this);
        rlMode.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        btnExcute.setOnClickListener(this);
        rlTscz.setOnClickListener(this);
        tvCheckDate.setOnClickListener(this);
        getAllDept.setLoadingListener(this);
        getUserByDept.setLoadingListener(this);
        compHandle.setLoadingListener(this);

//        //根据状态显示控件
//        if ("待分流".equals(mEntity.getfStatus())) {
//            llShunt.setVisibility(View.VISIBLE);
//            getAllDept.loadData();
//        } else if ("待指派".equals(mEntity.getfStatus())) {
//            if (!mEntity.getfRegUnit().endsWith("分局")) {
//                llExcute.setVisibility(View.VISIBLE);
//            }
//            llAssign.setVisibility(View.VISIBLE);
//            getUserByDept.loadData(userInfo.getDepartmentAlias());
//        } else if ("处理反馈".equals(mEntity.getfStatus())) {
//            if ("举报".equals(mEntity.getfType())) {
//                llTscz.setVisibility(View.VISIBLE);
//                llCheckDate.setVisibility(View.VISIBLE);
//                etCheckOpinion.setVisibility(View.VISIBLE);
//            } else if ("举报".equals(mEntity.getfType())) {
//                llTjNum.setVisibility(View.VISIBLE);
//                llResult.setVisibility(View.VISIBLE);
//                llMode.setVisibility(View.VISIBLE);
//                llArgueMonney.setVisibility(View.VISIBLE);
//                llSaveMonney.setVisibility(View.VISIBLE);
//                etTjOpinion.setVisibility(View.VISIBLE);
//                llDate.setVisibility(View.VISIBLE);
//            }else {
//                etTjOpinion.setVisibility(View.VISIBLE);
//            }
//        }

        //初始化下拉选择项
        selectListResult.add(new SelectPopDataList("达成调解协议", "达成调解协议"));
        selectListResult.add(new SelectPopDataList("未达成调解协议", "未达成调解协议"));
        selectListResult.add(new SelectPopDataList("撤回", "撤回"));
        selectListResult.add(new SelectPopDataList("其它", "其它"));

        selectListMode.add(new SelectPopDataList("现场调解", "现场调解"));
        selectListMode.add(new SelectPopDataList("三方通话", "三方通话"));
        selectListMode.add(new SelectPopDataList("其它", "其它"));

        selectListTscz.add(new SelectPopDataList("情况属实", "情况属实"));
        selectListTscz.add(new SelectPopDataList("情况不实", "情况不实"));
        selectListTscz.add(new SelectPopDataList("撤销", "撤销"));
        selectListTscz.add(new SelectPopDataList("立案", "立案"));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_comp_shunt://分流
                showSelectWindow(rlShunt, 0);
                break;
            case R.id.rl_comp_assign://指派
                showSelectWindow(rlAssign, 1);
                break;
            case R.id.rl_comp_tjMode://调解方式
                showSelectWindow(rlMode, 2);
                break;
            case R.id.rl_comp_tjResult://调解结果
                showSelectWindow(rlResult, 3);
                break;
            case R.id.rl_comp_tscz://投诉操作
                showSelectWindow(rlTscz, 4);
                break;
            case R.id.tv_comp_date://时间
                Util.setDateIntoText(this, tvDate);
                break;
            case R.id.tv_comp_checkDate://核查日期
                Util.setDateIntoText(this, tvCheckDate);
                break;
            case R.id.btn_comp_excute:
                if (llDate.getVisibility() == View.VISIBLE && tvDate.getText().toString().trim().length() == 0) {
                    showToast("请输入调解时间");
                    return;
                } else if (llCheckDate.getVisibility() == View.VISIBLE && tvCheckDate.getText().toString().trim().length() == 0) {
                    showToast("请输入核查日期");
                    return;
                } else if (llShunt.getVisibility() == View.VISIBLE && tvShunt.getText().toString().contains("选择")) {
                    showToast("请选择分流对象");
                    return;
                } else if (llAssign.getVisibility() == View.VISIBLE && tvAssign.getText().toString().contains("选择")) {
                    showToast("请选择指派对象");
                    return;
                } else if (llResult.getVisibility() == View.VISIBLE && tvResult.getText().toString().contains("选择")) {
                    showToast("请选择调解结果");
                    return;
                } else if (llMode.getVisibility() == View.VISIBLE && tvMode.getText().toString().contains("选择")) {
                    showToast("请选择调解方式");
                    return;
                } else if (llTscz.getVisibility() == View.VISIBLE && tvTscz.getText().toString().contains("选择")) {
                    showToast("请选择投诉操作");
                    return;
                } else if (etCheckOpinion.getVisibility() == View.VISIBLE && etCheckOpinion.getText().toString().trim().length() == 0) {
                    showToast("请输入核查情况说明");
                    return;
                }
                doSendInfo();
                break;
            default:
                break;
        }
    }

    //进行信息发送
    private void doSendInfo() {
//        if ("待分流".equals(mEntity.getfStatus())) {
//            compHandle.loadData(mEntity.getfRegId(), mEntity.getfStatus(), userInfo.getId(),
//                    "fSlr", tvShunt.getText().toString());
//        } else if ("待指派".equals(mEntity.getfStatus())) {
//            if (llAssign.getVisibility() == View.VISIBLE) {
//                compHandle.loadData(mEntity.getfRegId(), mEntity.getfStatus(), userInfo.getId(),
//                        "fSlr", tvAssign.getText().toString(),
//                        "assignee", fSlrId,
//                        "isPass", "1");
//            } else {
//                compHandle.loadData(mEntity.getfRegId(), mEntity.getfStatus(), userInfo.getId(),
//                        "fSlr", tvAssign.getText().toString(),
//                        "isPass", "0");
//            }
//        } else if ("处理反馈".equals(mEntity.getfStatus())) {
//            if ("举报".equals(mEntity.getfType())) {
//                String date = tvCheckDate.getText().toString().replace("年", "-").replace("月", "-").replace("日", "");
//                compHandle.loadData(mEntity.getfRegId(), mEntity.getfStatus(), userInfo.getId(),
//                        "fComplaintOpreator", tvTscz.getText().toString(),
//                        "fCheckDate", date,
//                        "fCheckSituation", etCheckOpinion.getText().toString());
//            } else {
//                String date = tvDate.getText().toString().replace("年", "-").replace("月", "-").replace("日", "");
//                compHandle.loadData(mEntity.getfRegId(), mEntity.getfStatus(), userInfo.getId(),
//                        "fMediateDate", date,
//                        "fMediateResult", tvResult.getText().toString(),
//                        "fSaveLoseMoney", etSaveMonney.getText().toString(),
//                        "fDisputeAmount", etArgueMonney.getText().toString(),
//                        "fDocumentNumber", etTjNum.getText().toString(),
//                        "fMediateMode", tvMode.getText().toString(),
//                        "fMediateSituation", etTjOpinion.getText().toString());
//            }
//        }
    }

    //打开选择框
    private boolean showSelectWindow(View view, int index) {
        closeSoftInput();
        switch (index) {
            case 0://分流
                if (mPopShunt == null) {
                    mPopShunt = new SuperviseSelectPopuView(this);
                    mPopShunt.setDataSelectListener(selectListener);
                }
                return mPopShunt.show(view, view.getWidth(), selectListShunt, index);
            case 1://指派
                if (mPopAssign == null) {
                    mPopAssign = new SuperviseSelectPopuView(this);
                    mPopAssign.setDataSelectListener(selectListener);
                }
                return mPopAssign.show(view, view.getWidth(), selectListAssign, index);
            case 2://调解方式
                if (mPopMode == null) {
                    mPopMode = new SuperviseSelectPopuView(this);
                    mPopMode.setDataSelectListener(selectListener);
                }
                return mPopMode.show(view, view.getWidth(), selectListMode, index);
            case 3://调解结果
                if (mPopResult == null) {
                    mPopResult = new SuperviseSelectPopuView(this);
                    mPopResult.setDataSelectListener(selectListener);
                }
                return mPopResult.show(view, view.getWidth(), selectListResult, index);
            case 4://投诉操作
                if (mPopTscz == null) {
                    mPopTscz = new SuperviseSelectPopuView(this);
                    mPopTscz.setDataSelectListener(selectListener);
                }
                return mPopTscz.show(view, view.getWidth(), selectListTscz, index);
            default:
                return false;
        }
    }

    //下拉框的选择事件
    ICommonListener.IOnInfoSelectListener selectListener = new ICommonListener.IOnInfoSelectListener() {
        @Override
        public void onSelect(int pos, SelectPopDataList data, int index) {
            switch (index) {
                case 0://分流
                    tvShunt.setText(data.fTaskName);
                    break;
                case 1://指派
                    tvAssign.setText(data.fTaskName);
                    fSlrId = data.fTaskId;
                    break;
                case 2://方式
                    tvMode.setText(data.fTaskName);
                    break;
                case 3://结果
                    tvResult.setText(data.fTaskName);
                    break;
                case 4://投诉操作
                    tvTscz.setText(data.fTaskName);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_getAllUserDept:
                List<SelectPopDataList> list = (List<SelectPopDataList>) b.getEntry();
                selectListShunt.clear();
                selectListShunt.addAll(list);
                break;
            case ApiData.HTTP_ID_getUsersByDept:
                List<KeyValueInfo> userList = (List<KeyValueInfo>) b.getEntry();
                selectListAssign.clear();
                for (int i = 0; i < userList.size(); i++) {
                    KeyValueInfo userInfo = userList.get(i);
                    selectListAssign.add(new SelectPopDataList(userInfo.key, userInfo.value));
                }
                break;
            case ApiData.HTTP_ID_compFlowhandle:
                showToast("处理成功");
                setResult(ConstStrings.Request_Success, null);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.rg_comp_excute:
                if (rgExcute.getCheckedRadioButtonId() == R.id.rb_comp_pass) {//通过
                    llAssign.setVisibility(View.VISIBLE);
                } else {//退回
                    llAssign.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }
}
