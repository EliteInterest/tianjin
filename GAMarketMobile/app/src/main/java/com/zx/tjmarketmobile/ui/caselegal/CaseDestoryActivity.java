package com.zx.tjmarketmobile.ui.caselegal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.CaseInfoEntity;
import com.zx.tjmarketmobile.entity.SelectPopDataList;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.ICommonListener;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.ui.mainbase.LoginActivity;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.MyApplication;
import com.zx.tjmarketmobile.util.Util;
import com.zx.tjmarketmobile.view.SuperviseSelectPopuView;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2017/3/16
 * 功能：案件销毁
 */

public class CaseDestoryActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private LinearLayout llNexrPerson;//下一流程处理人
    private LinearLayout llDestory;//是否延期
    private EditText etDestoryOpinion;//意见
    private RadioGroup rgIsPass;//是否通过
    private RelativeLayout rlNextPerson;//下一流程处理人
    private Button btnDestory;//执行操作
    private TextView tvNextPerson;//下一流程处理人
    private SuperviseSelectPopuView mPopNextPerson;//下一流程处理人
    private List<SelectPopDataList> selectListPerson = new ArrayList<>();//下一流程处理人
    private TextView tvNextRole;//下一流程处理人角色

    private CaseInfoEntity mEntity;
    private String destoryCode = "";
    private int isPass = 1;//是否通过
    private SelectPopDataList fSlrInfo = null;//流程处理人信息

    private ApiData getNextPerson = new ApiData(ApiData.HTTP_ID_caseGetNextPerson);//获取下一流程处理人
    private ApiData doDestory = new ApiData(ApiData.HTTP_ID_caseDoDestory);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_destory);
        initView();
    }

    private void initView() {
        addToolBar(true);
        hideRightImg();
        setMidText("案件销案");

        llNexrPerson = (LinearLayout) findViewById(R.id.ll_case_destoryNextPerson);
        llDestory = (LinearLayout) findViewById(R.id.ll_case_destory);
        rgIsPass = (RadioGroup) findViewById(R.id.rg_case_isPass);
        rlNextPerson = (RelativeLayout) findViewById(R.id.rl_case_destoryDextPerson);
        tvNextPerson = (TextView) findViewById(R.id.tv_case_destoryNextPerson);
        etDestoryOpinion = (EditText) findViewById(R.id.et_case_destoryOpinion);
        btnDestory = (Button) findViewById(R.id.btn_case_destoryExcute);
        tvNextRole = (TextView) findViewById(R.id.tv_case_destoryNextPersonRole);

        if (getIntent().hasExtra("entity")) {
            mEntity = (CaseInfoEntity) getIntent().getSerializableExtra("entity");
        }
        if ("0".equals(mEntity.getIsPause())) {
            destoryCode = "0";
        } else {
            destoryCode = mEntity.getCaseName();
            loadNextPerson();
        }

        btnDestory.setOnClickListener(this);
        rlNextPerson.setOnClickListener(this);
        doDestory.setLoadingListener(this);
        getNextPerson.setLoadingListener(this);
        rgIsPass.setOnCheckedChangeListener(this);
        setViewVisableByCode(true);
    }

    /**
     * 根据code，设置界面的隐藏
     *
     * @param isShow 是否显示
     */
    private void setViewVisableByCode(boolean isShow) {
        switch (destoryCode) {
            case "0"://启动销案
                if (isShow) {
                    etDestoryOpinion.setVisibility(View.GONE);
                    btnDestory.setText("启动销案");
                }
                break;
            case "销案申请":
                if (isShow) {
                    llNexrPerson.setVisibility(View.VISIBLE);
                }
                break;
            case "销案审查":
                if (isShow) {
                    llDestory.setVisibility(View.VISIBLE);
                    llNexrPerson.setVisibility(View.VISIBLE);
                } else {
                    llNexrPerson.setVisibility(View.GONE);
                }
                break;
            case "销案审批":
                if (isShow) {
                    llDestory.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    //根据环节编码，执行完成操作
    private void doExcuteByCode() {
        String userId = userInfo.getId();
        String description = etDestoryOpinion.getText().toString().trim();
        String taskId = mEntity.getTaskId();
        if (etDestoryOpinion.getVisibility() == View.VISIBLE && description.length() == 0) {
            showToast("请填写说明");
            return;
        } else if (userId.length() == 0) {
            showToast("用户信息获取失败，请重新登录");
            clearLogin();
            startActivity(new Intent(this, LoginActivity.class));
            MyApplication.getInstance().removeUntilLogin();
            return;
        } else if (llNexrPerson.getVisibility() == View.VISIBLE && selectListPerson.size() > 0 && fSlrInfo == null) {
            showToast("请选择下一流程处理人");
            return;
        }
        //对于结案时不需要选择下一流程处理人
        String name = "", id = "";
        if (fSlrInfo != null) {
            name = fSlrInfo.fTaskName.substring(fSlrInfo.fTaskName.indexOf("-") + 1, fSlrInfo.fTaskName.length());
            id = fSlrInfo.fTaskId;
        }
        switch (destoryCode) {
            case "0"://启动销案
                doDestory.loadData("doAjxaLcStartPro", mEntity.getId(), isPass, userId, description, taskId);
                break;
            case "销案申请":
                doDestory.loadData("doAjxaLcSqPro", mEntity.getId(), isPass, userId, description, taskId,
                        "fSlr", name,
                        "assignee", id);
                break;
            case "销案审查":
                doDestory.loadData("doAjxaLcScPro", mEntity.getId(), isPass, userId, description, taskId,
                        "fSlr", name,
                        "assignee", id);
                break;
            case "销案审批":
                doDestory.loadData("doAjxaLcSpPro", mEntity.getId(), isPass, userId, description, taskId,
                        "fSlr", name,
                        "assignee", id);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_case_destoryExcute:
                Util.showYesOrNoDialog(this, "提示", "是否提交?", "确定", "取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doExcuteByCode();
                        Util.dialog.dismiss();
                    }
                }, null);
                break;
            case R.id.rl_case_destoryDextPerson:
                showSelectWindow(rlNextPerson);
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_caseGetNextPerson://获取下一流程处理人
                List<SelectPopDataList> list = (List<SelectPopDataList>) b.getEntry();
                selectListPerson.clear();
                if (list.size() > 1) {
                    tvNextRole.setText("下一流程角色：" + list.get(0).fTaskName);
                    selectListPerson.addAll(list.subList(1, list.size()));
                } else {
                    tvNextRole.setText("下一流程角色：" + "未获取到下一流程处理人");
                }
                break;
            case ApiData.HTTP_ID_caseDoDestory:
                showToast("处理成功");
                setResult(ConstStrings.Request_Success, null);
                finish();
                break;
            default:
                break;
        }
    }

    //加载下一流程处理人
    private void loadNextPerson() {
        getNextPerson.loadData(mEntity.getId(), isPass, mEntity.getTaskId());
    }

    //打开选择框
    private boolean showSelectWindow(View view) {
        closeSoftInput();
        if (mPopNextPerson == null) {
            mPopNextPerson = new SuperviseSelectPopuView(this);
            mPopNextPerson.setDataSelectListener(selectListener);
        }
        return mPopNextPerson.show(view, view.getWidth(), selectListPerson, 0);

    }

    //下拉框的选择事件
    ICommonListener.IOnInfoSelectListener selectListener = new ICommonListener.IOnInfoSelectListener() {
        @Override
        public void onSelect(int pos, SelectPopDataList data, int index) {
            switch (index) {
                case 0://流程角色
                    tvNextPerson.setText(data.fTaskName);
                    fSlrInfo = data;
                    break;
            }
        }
    };

    //radiogrouop的切换事件
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.rg_case_isPass://是否通过
                if (rgIsPass.getCheckedRadioButtonId() == R.id.rb_case_isPass1) {
                    isPass = 1;
                    setViewVisableByCode(true);
                } else {
                    isPass = 0;
                    setViewVisableByCode(false);
                }
                fSlrInfo = null;
                tvNextPerson.setText("下一流程处理人");
                loadNextPerson();
                break;
            default:
                break;
        }
    }
}
