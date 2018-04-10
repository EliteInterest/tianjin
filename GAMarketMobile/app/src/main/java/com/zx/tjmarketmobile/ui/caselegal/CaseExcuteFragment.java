package com.zx.tjmarketmobile.ui.caselegal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.CaseInfoEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.util.ConstStrings;

/**
 * Created by Xiangb on 2017/4/14.
 * 功能：
 */
@SuppressWarnings("deprecation")
public class CaseExcuteFragment extends BaseFragment implements View.OnClickListener {
    private Button btnExcute, btnCancel;
    private EditText etExcuteOpinion;//处理意见
    private TextView tvStatus;

    private CaseInfoEntity mEntity;

    private CaseExcuteActivity activity;

    private ApiData case01 = new ApiData(ApiData.HTTP_ID_case_01);
    private ApiData caseY01 = new ApiData(ApiData.HTTP_ID_case_Y01);
    private ApiData caseY02 = new ApiData(ApiData.HTTP_ID_case_Y02);
    private ApiData caseY03 = new ApiData(ApiData.HTTP_ID_case_Y03);
    private ApiData caseY05 = new ApiData(ApiData.HTTP_ID_case_Y05);
    private ApiData caseY06 = new ApiData(ApiData.HTTP_ID_case_Y06);
    private ApiData caseY07 = new ApiData(ApiData.HTTP_ID_case_Y07);
    private ApiData caseY08 = new ApiData(ApiData.HTTP_ID_case_Y08);
    private ApiData caseY10 = new ApiData(ApiData.HTTP_ID_case_Y10);
    private ApiData caseY11 = new ApiData(ApiData.HTTP_ID_case_Y11);
    private ApiData caseY12 = new ApiData(ApiData.HTTP_ID_case_Y12);
    private ApiData caseY13 = new ApiData(ApiData.HTTP_ID_case_Y13);
    private ApiData caseY14 = new ApiData(ApiData.HTTP_ID_case_Y14);
    private ApiData case02 = new ApiData(ApiData.HTTP_ID_case_02);

    public static CaseExcuteFragment newInstance(CaseExcuteActivity activity, CaseInfoEntity mEntity) {
        CaseExcuteFragment details = new CaseExcuteFragment();
        details.activity = activity;
        details.mEntity = mEntity;
        return details;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_excute, container, false);
        initView(view);
        return view;
    }

    //初始化
    private void initView(View view) {
        btnCancel = view.findViewById(R.id.btn_case_cancel);
        btnExcute = (Button) view.findViewById(R.id.btn_case_excute);
        etExcuteOpinion = (EditText) view.findViewById(R.id.et_case_excuteOpinion);
        tvStatus = view.findViewById(R.id.tv_case_status);
        btnExcute.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        case01.setLoadingListener(this);
        caseY01.setLoadingListener(this);
        caseY02.setLoadingListener(this);
        caseY03.setLoadingListener(this);
        caseY05.setLoadingListener(this);
        caseY06.setLoadingListener(this);
        caseY07.setLoadingListener(this);
        caseY08.setLoadingListener(this);
        caseY10.setLoadingListener(this);
        caseY11.setLoadingListener(this);
        caseY12.setLoadingListener(this);
        caseY13.setLoadingListener(this);
        caseY14.setLoadingListener(this);
        case02.setLoadingListener(this);

        switch (mEntity.getStatus()) {
            case "01"://线索核查
                tvStatus.setText("线索核查");
                btnExcute.setText("立案");
                btnCancel.setText("不立案");
                break;
            case "Y01"://立案申请
                tvStatus.setText("立案申请");
                btnExcute.setText("提交申请");
                btnCancel.setVisibility(View.GONE);
                break;
            case "Y02"://立案审批
                tvStatus.setText("立案审批");
                btnExcute.setText("同意");
                btnCancel.setText("不同意");
                break;
            case "Y03"://调查取证
                tvStatus.setText("调查取证");
                btnExcute.setText("终结调查");
                btnCancel.setText("终止调查");
                break;
            case "Y04"://终止调查
                tvStatus.setText("终止调查");
                btnExcute.setText("结案");
                btnCancel.setVisibility(View.GONE);
                break;
            case "Y05"://终结报告
                tvStatus.setText("终结报告");
                btnExcute.setText("提交初审");
                btnCancel.setVisibility(View.GONE);
                break;
            case "Y06"://初审
                tvStatus.setText("初审");
                btnExcute.setText("同意");
                btnCancel.setText("不同意");
                break;
            case "Y07"://委员会集体审理
                tvStatus.setText("委员会集体审理");
                btnExcute.setText("同意");
                btnCancel.setText("不同意");
                break;
            case "Y08"://行政处罚告知
                tvStatus.setText("行政处罚告知");
                btnExcute.setText("听证");
                btnCancel.setText("不听证");
                break;
            case "Y10"://听证
                tvStatus.setText("听证");
                btnExcute.setText("提交");
                btnCancel.setVisibility(View.GONE);
                break;
            case "Y11"://处罚决定
                tvStatus.setText("处罚决定");
                btnExcute.setText("提交审核");
                btnCancel.setVisibility(View.GONE);
                break;
            case "Y12"://处罚决定审核
                tvStatus.setText("处罚决定审核");
                btnExcute.setText("同意");
                btnCancel.setText("不同意");
                break;
            case "Y13"://送达当事人
                tvStatus.setText("送达当事人");
                btnExcute.setText("提交");
                btnCancel.setVisibility(View.GONE);
                break;
            case "Y14"://行政处罚的执行
                tvStatus.setText("行政处罚的执行");
                btnExcute.setText("结案");
                btnCancel.setVisibility(View.GONE);
                break;
            case "02"://结案
                tvStatus.setText("结案");
                btnExcute.setText("归档");
                btnCancel.setVisibility(View.GONE);
                break;
            case "N01"://反馈
                tvStatus.setText("反馈");
                btnExcute.setText("结案");
                btnCancel.setVisibility(View.GONE);
                break;
            default:
                activity.finish();
                showToast("当前状态无可处理流程");
                break;
        }

    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_case_excute:
                loadData(0);
                break;
            case R.id.btn_case_cancel:
                loadData(1);
                break;
            default:
                break;
        }
    }

    private void loadData(int isPass) {
        String opinion = etExcuteOpinion.getText().toString().trim();
        switch (mEntity.getStatus()) {
            case "01"://线索核查
                case01.loadData(mEntity.getId(), opinion, isPass, mEntity.getTaskId());
                break;
            case "Y01"://立案申请
                caseY01.loadData(mEntity.getId(), opinion, mEntity.getTaskId());
                break;
            case "Y02"://立案审批
                caseY02.loadData(mEntity.getId(), opinion, mEntity.getTaskId(), isPass);
                break;
            case "Y03"://调查取证
                caseY03.loadData(mEntity.getId(), opinion, mEntity.getTaskId(), isPass);
                break;
            case "Y04"://终止调查
                caseY14.loadData(mEntity.getId(), opinion, mEntity.getTaskId());
                break;
            case "Y05"://终结报告
                caseY05.loadData(mEntity.getId(), opinion, mEntity.getTaskId());
                break;
            case "Y06"://初审
                caseY06.loadData(mEntity.getId(), opinion, mEntity.getTaskId(), isPass);
                break;
            case "Y07"://委员会集体审理
                caseY07.loadData(mEntity.getId(), opinion, mEntity.getTaskId(), isPass);
                break;
            case "Y08"://行政处罚告知
                caseY08.loadData(mEntity.getId(), opinion, mEntity.getTaskId(), isPass);
                break;
            case "Y10"://听证
                caseY10.loadData(mEntity.getId(), opinion, mEntity.getTaskId());
                break;
            case "Y11"://处罚决定
                caseY11.loadData(mEntity.getId(), opinion, mEntity.getTaskId());
                break;
            case "Y12"://处罚决定审核
                caseY12.loadData(mEntity.getId(), opinion, mEntity.getTaskId(), isPass);
                break;
            case "Y13"://送达当事人
                caseY13.loadData(mEntity.getId(), opinion, mEntity.getTaskId());
                break;
            case "Y14"://行政处罚的执行
                caseY14.loadData(mEntity.getId(), opinion, mEntity.getTaskId());
                break;
            case "02"://结案
                case02.loadData(mEntity.getId(), opinion, mEntity.getTaskId());
                break;
            case "N01"://反馈
                caseY14.loadData(mEntity.getId(), opinion, mEntity.getTaskId());
                break;
            default:
                break;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onLoadComplete(int id, BaseHttpResult b) {
        showToast("处理成功");
        activity.setResult(ConstStrings.Request_Success, null);
        activity.finish();

    }
}
