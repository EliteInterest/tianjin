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
 * 功能：案件延期
 */

public class CaseDelayActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private LinearLayout llDate;//延期时限
    private LinearLayout llNexrPerson;//下一流程处理人
    private LinearLayout llDelay;//是否延期
    private EditText etDelayOpinion;//意见
    private TextView tvDate;//选择延期时限
    private RadioGroup rgIsPass;//是否通过
    private RelativeLayout rlNextPerson;//下一流程处理人
    private Button btnDelay;//执行操作
    private TextView tvNextPerson;//下一流程处理人
    private SuperviseSelectPopuView mPopNextPerson;//下一流程处理人
    private List<SelectPopDataList> selectListPerson = new ArrayList<>();//下一流程处理人
    private TextView tvNextRole;//下一流程处理人角色


    private CaseInfoEntity mEntity;
    private String delayCode = "";
    private int isPass = 1;//是否通过
    private SelectPopDataList fSlrInfo = null;//流程处理人信息

    private ApiData getNextPerson = new ApiData(ApiData.HTTP_ID_caseGetNextPerson);//获取下一流程处理人
    private ApiData doDelay = new ApiData(ApiData.HTTP_ID_caseDoDelay);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_delay);
        initView();
    }

    private void initView() {
        addToolBar(true);
        hideRightImg();
        setMidText("案件延期");

        llDate = (LinearLayout) findViewById(R.id.ll_case_delayDate);
        llNexrPerson = (LinearLayout) findViewById(R.id.ll_case_delayNextPerson);
        tvDate = (TextView) findViewById(R.id.et_case_delayDate);
        llDelay = (LinearLayout) findViewById(R.id.ll_case_delay);
        rgIsPass = (RadioGroup) findViewById(R.id.rg_case_isPass);
        rlNextPerson = (RelativeLayout) findViewById(R.id.rl_case_delayDextPerson);
        tvNextPerson = (TextView) findViewById(R.id.tv_case_delayNextPerson);
        etDelayOpinion = (EditText) findViewById(R.id.et_case_delayOpinion);
        btnDelay = (Button) findViewById(R.id.btn_case_delayExcute);
        tvNextRole = (TextView) findViewById(R.id.tv_case_delayNextPersonRole);

        if (getIntent().hasExtra("entity")) {
            mEntity = (CaseInfoEntity) getIntent().getSerializableExtra("entity");
        }
        if ("0".equals(mEntity.getIsOverdue())) {//已延期

        } else if ("1".equals(mEntity.getIsOverdue())) {//正在延期
//            delayCode = mEntity.getfTaskName();
            loadNextPerson();
        } else {//未延期
            delayCode = "0";
        }

        btnDelay.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        rlNextPerson.setOnClickListener(this);
        doDelay.setLoadingListener(this);
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
        switch (delayCode) {
            case "0"://启动延期
                if (isShow) {
                    etDelayOpinion.setVisibility(View.GONE);
                    btnDelay.setText("启动延期");
                }
                break;
            case "延期申请":
                if (isShow) {
                    llNexrPerson.setVisibility(View.VISIBLE);
                    llDate.setVisibility(View.VISIBLE);
                }
                break;
            case "延期审查":
                if (isShow) {
                    llDelay.setVisibility(View.VISIBLE);
                    llNexrPerson.setVisibility(View.VISIBLE);
                } else {
                    llNexrPerson.setVisibility(View.GONE);
                }
                break;
            case "延期审批":
                if (isShow) {
                    llDelay.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    //根据环节编码，执行完成操作
    private void doExcuteByCode() {
        String userId = userInfo.getId();
        String description = etDelayOpinion.getText().toString().trim();
        String taskId = mEntity.getTaskId();
        if (etDelayOpinion.getVisibility() == View.VISIBLE && description.length() == 0) {
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
        } else if (llDate.getVisibility() == View.VISIBLE && tvDate.getText().toString().trim().length() == 0) {
            showToast("请选择延期时限");
            return;
        }
        String date = tvDate.getText().toString().replace("年", "-").replace("月", "-").replace("日", "");
        //对于结案时不需要选择下一流程处理人
        String name = "", id = "";
        if (fSlrInfo != null) {
            name = fSlrInfo.fTaskName.substring(fSlrInfo.fTaskName.indexOf("-") + 1, fSlrInfo.fTaskName.length());
            id = fSlrInfo.fTaskId;
        }
        switch (delayCode) {
            case "0"://启动延期
                doDelay.loadData("doAjyqLcStartPro", mEntity.getId(), isPass, userId, description, taskId);
                break;
            case "延期申请":
                doDelay.loadData("doAjyqLcSqPro", mEntity.getId(), isPass, userId, description, taskId,
                        "fSlr", name,
                        "assignee", id,
                        "fYqSx", date);
                break;
            case "延期审查":
                doDelay.loadData("doAjyqLcScPro", mEntity.getId(), isPass, userId, description, taskId,
                        "fSlr", name,
                        "assignee", id);
                break;
            case "延期审批":
                doDelay.loadData("doAjyqLcSpPro", mEntity.getId(), isPass, userId, description, taskId,
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
            case R.id.et_case_delayDate:
                Util.setDateIntoText(this, tvDate);
                break;
            case R.id.btn_case_delayExcute:
                Util.showYesOrNoDialog(this, "提示", "是否提交?", "确定", "取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doExcuteByCode();
                        Util.dialog.dismiss();
                    }
                }, null);
                break;
            case R.id.rl_case_delayDextPerson:
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
            case ApiData.HTTP_ID_caseDoDelay:
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
