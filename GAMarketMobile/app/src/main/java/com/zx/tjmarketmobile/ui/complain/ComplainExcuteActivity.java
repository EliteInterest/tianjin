package com.zx.tjmarketmobile.ui.complain;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.ComplainInfoEntity;
import com.zx.tjmarketmobile.entity.KeyValueInfo;
import com.zx.tjmarketmobile.entity.SelectPopDataList;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.ICommonListener;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.view.SuperviseSelectPopuView;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2017/3/22
 * 功能：投诉举报处置
 */

public class ComplainExcuteActivity extends BaseActivity {
    private LinearLayout llShunt;//分流
    private LinearLayout llAssign;//指派
    private EditText etTjOpinion;//调解情况
    private RelativeLayout rlShunt;//分流
    private TextView tvShunt;//分流
    private RelativeLayout rlAssign;//指派
    private TextView tvAssign;//指派
    private Button btnExcute, btnCancel;

    private SuperviseSelectPopuView mPopShunt;//分流
    private List<SelectPopDataList> selectListShunt = new ArrayList<>();//分流
    private SuperviseSelectPopuView mPopAssign;//指派
    private List<SelectPopDataList> selectListAssign = new ArrayList<>();//指派

    private ComplainInfoEntity mEntity;
    private String fSlrId = "";//受理人id
    private String departId = "";//部门id

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

        llShunt = findViewById(R.id.ll_comp_shunt);
        llAssign = findViewById(R.id.ll_comp_assign);
        etTjOpinion = findViewById(R.id.et_comp_tjOpinion);
        rlShunt = findViewById(R.id.rl_comp_shunt);
        rlAssign = findViewById(R.id.rl_comp_assign);
        tvAssign = findViewById(R.id.tv_comp_assign);
        tvShunt = findViewById(R.id.tv_comp_shunt);
        btnExcute = findViewById(R.id.btn_comp_excute);
        btnCancel = findViewById(R.id.btn_comp_cancel);

        btnCancel.setOnClickListener(this);
        rlAssign.setOnClickListener(this);
        rlShunt.setOnClickListener(this);
        btnExcute.setOnClickListener(this);
        getAllDept.setLoadingListener(this);
        getUserByDept.setLoadingListener(this);
        compHandle.setLoadingListener(this);

        //根据状态显示控件
        switch (mEntity.getFStatus()) {
            case 10://待受理
                btnExcute.setText("受理");
                btnCancel.setText("不受理");
                break;
            case 20://待分流
                getAllDept.loadData();
                llShunt.setVisibility(View.VISIBLE);
                etTjOpinion.setVisibility(View.VISIBLE);
                btnExcute.setText("分流");
                btnCancel.setText("不分流");
                break;
            case 30://待指派
                getUserByDept.loadData("", "", "", userInfo.getDepartmentCode(), mEntity.getFStatus(), "1002");
                llAssign.setVisibility(View.VISIBLE);
                etTjOpinion.setVisibility(View.VISIBLE);
                btnExcute.setText("指派");
                btnCancel.setText("退回");
                break;
            case 50://待处置
                etTjOpinion.setVisibility(View.VISIBLE);
                btnExcute.setText("提交");
                btnCancel.setText("取消");
                break;
            case 60://待审核
                etTjOpinion.setVisibility(View.VISIBLE);
                btnExcute.setText("通过");
                btnCancel.setText("查看");
                break;
            case 80://待办结
                etTjOpinion.setVisibility(View.VISIBLE);
                btnExcute.setText("办结");
                btnCancel.setText("查看");
                break;
            default:
                break;
        }

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
            case R.id.btn_comp_excute:
                if (llShunt.getVisibility() == View.VISIBLE && tvShunt.getText().toString().contains("选择")) {
                    showToast("请选择分流对象");
                    return;
                } else if (llAssign.getVisibility() == View.VISIBLE && tvAssign.getText().toString().contains("选择")) {
                    showToast("请选择指派对象");
                    return;
                }
                doSendInfo("pass");
                break;
            case R.id.btn_comp_cancel:
                doSendInfo("return");
                break;
            default:
                break;
        }
    }

    //进行信息发送
    private void doSendInfo(String dispose) {
        switch (mEntity.getFStatus()) {
            case 10://待受理
                compHandle.loadData(mEntity.getFGuid(), dispose, "", "", "");
                break;
            case 20://待分流
                compHandle.loadData(mEntity.getFGuid(), dispose, etTjOpinion.getText().toString(), departId, "");
                break;
            case 30://待指派
                compHandle.loadData(mEntity.getFGuid(), dispose, etTjOpinion.getText().toString(), "", fSlrId);
                break;
            case 50://待处置
                compHandle.loadData(mEntity.getFGuid(), dispose, etTjOpinion.getText().toString(), "", "");
                break;
            case 60://待审核
                compHandle.loadData(mEntity.getFGuid(), dispose, etTjOpinion.getText().toString(), "", "");
                break;
            case 80://待办结
                compHandle.loadData(mEntity.getFGuid(), dispose, etTjOpinion.getText().toString(), "", "");
                break;
            default:
                break;
        }
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
                    departId = data.fTaskId;
                    break;
                case 1://指派
                    tvAssign.setText(data.fTaskName);
                    fSlrId = data.fTaskId;
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
}
