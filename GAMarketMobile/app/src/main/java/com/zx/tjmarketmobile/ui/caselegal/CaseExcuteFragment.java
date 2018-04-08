package com.zx.tjmarketmobile.ui.caselegal;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.CaseInfoEntity;
import com.zx.tjmarketmobile.entity.CaseRefeEntity;
import com.zx.tjmarketmobile.entity.SelectPopDataList;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.ICommonListener;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.ui.mainbase.LoginActivity;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.MyApplication;
import com.zx.tjmarketmobile.util.Util;
import com.zx.tjmarketmobile.view.CaseReferenceView;
import com.zx.tjmarketmobile.view.SuperviseSelectPopuView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2017/4/14.
 * 功能：
 */
@SuppressWarnings("deprecation")
public class CaseExcuteFragment extends BaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private LinearLayout llExcute;//处理意见
    private LinearLayout llPenaltyOpinion;//处罚建议
    private LinearLayout llDispose;//是否执行
    private LinearLayout llMonneyFinish;//罚款是否完结
    private LinearLayout llPublish;//是否公示
    private LinearLayout llPenaltyMonney;//罚款金额
    private LinearLayout llIllegalMonney;//违法所得金额
    private LinearLayout llConfisMonney;//没收金额
    private LinearLayout llNextFlow;//下一流程
    private LinearLayout llNextPerson;//下一流程处理人
    private LinearLayout llDxyj;//定性依据
    private LinearLayout llCfyj;//处罚依据
    private RadioGroup rgIsPass;//是否通过
    private RadioGroup rgPenalty;//是否处罚
    private RadioGroup rgDispose;//是否执行
    private RadioGroup rgFinishMonney;//罚款是否完结
    private RadioGroup rgPubnish;//是否公示
    private Button btnExcute;//确定执行
    private EditText etExcuteOpinion;//处理意见
    private EditText etPenaltyMonney;//处罚金额
    private EditText etIllegalMonney;//违法所得金额
    private EditText etConfisMonney;//没收金额
    private RadioButton rb_case_isPass2;
    private TextView tvRefrence;//参考
    private TextView tvAdvice;//建议
    private EditText etDxyj;//定性依据
    private EditText etCfyj;//处罚依据

    private RelativeLayout rlNextFlow;//下一流程
    private TextView tvNextFlow;//下一流程
    private SuperviseSelectPopuView mPopNextFlow;//下一流程
    private List<SelectPopDataList> selectListFlow = new ArrayList<>();//下一流程
    private RelativeLayout rlNextPerson;//下一流程处理人
    private TextView tvNextPerson;//下一流程处理人
    private SuperviseSelectPopuView mPopNextPerson;//下一流程处理人
    private List<SelectPopDataList> selectListPerson = new ArrayList<>();//下一流程处理人
    private TextView tvNextRole;//下一流程处理人角色
    private CaseInfoEntity mEntity;
    private CaseReferenceView caseReferenceView;
    private AlertDialog dialog;

    private int isPass = 1;//是否通过
    private int isPenalty = 1;//是否处罚
    private int isDispose = 1;//是否执行
    private int isPublish = 1;//是否公示
    private int isFinishMonney = 1;//罚款是否完结
    private String hjbm = "";//环节编码
    private SelectPopDataList fSlrInfo = null;//流程处理人信息
    private SelectPopDataList fFlowInfo = null;//流程信息

    private CaseExcuteActivity activity;
    private List<CaseRefeEntity.RowsBean> referenceBeans = new ArrayList<>();

    private ApiData getNextPerson = new ApiData(ApiData.HTTP_ID_caseGetNextPerson);//获取下一流程处理人
    private ApiData caseDoExcute = new ApiData(ApiData.HTTP_ID_caseDoExcute);//流程处理

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

        rlNextFlow = (RelativeLayout) view.findViewById(R.id.rl_case_nextFlow);
        rlNextPerson = (RelativeLayout) view.findViewById(R.id.rl_case_nextPerson);
        tvNextFlow = (TextView) view.findViewById(R.id.tv_case_nextFlow);
        tvNextPerson = (TextView) view.findViewById(R.id.tv_case_nextPerson);
        tvNextRole = (TextView) view.findViewById(R.id.tv_case_nextPersonRole);
        llNextPerson = (LinearLayout) view.findViewById(R.id.ll_case_nextPerson);
        llExcute = (LinearLayout) view.findViewById(R.id.ll_case_excute);
        rgIsPass = (RadioGroup) view.findViewById(R.id.rg_case_isPass);
        btnExcute = (Button) view.findViewById(R.id.btn_case_excute);
        etExcuteOpinion = (EditText) view.findViewById(R.id.et_case_excuteOpinion);
        llPenaltyOpinion = (LinearLayout) view.findViewById(R.id.ll_case_penaltyOpinion);
        rgPenalty = (RadioGroup) view.findViewById(R.id.rg_case_penalty);
        llPenaltyMonney = (LinearLayout) view.findViewById(R.id.ll_case_penaltyMonney);
        llIllegalMonney = (LinearLayout) view.findViewById(R.id.ll_case_illegalMonney);
        llConfisMonney = (LinearLayout) view.findViewById(R.id.ll_case_confisMonney);
        etPenaltyMonney = (EditText) view.findViewById(R.id.et_case_penaltyMonney);
        etIllegalMonney = (EditText) view.findViewById(R.id.et_case_illegalMonney);
        etConfisMonney = (EditText) view.findViewById(R.id.et_case_confisMonney);
        llNextFlow = (LinearLayout) view.findViewById(R.id.ll_case_nextFlow);
        llDispose = (LinearLayout) view.findViewById(R.id.ll_case_dispose);
        llMonneyFinish = (LinearLayout) view.findViewById(R.id.ll_case_monneyFinish);
        llPublish = (LinearLayout) view.findViewById(R.id.ll_case_publish);
        rgPubnish = (RadioGroup) view.findViewById(R.id.rg_case_publish);
        rgDispose = (RadioGroup) view.findViewById(R.id.rg_case_dispose);
        rgFinishMonney = (RadioGroup) view.findViewById(R.id.rg_case_monneyFinish);
        rb_case_isPass2 = (RadioButton) view.findViewById(R.id.rb_case_isPass2);
        tvRefrence = (TextView) view.findViewById(R.id.tv_case_reference);
        tvAdvice = (TextView) view.findViewById(R.id.tv_case_opinion);
        llDxyj = (LinearLayout) view.findViewById(R.id.ll_case_dxyj);
        llCfyj = (LinearLayout) view.findViewById(R.id.ll_case_cfyj);
        etDxyj = (EditText) view.findViewById(R.id.et_case_dxyj);
        etCfyj = (EditText) view.findViewById(R.id.et_case_cfyj);

        tvRefrence.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvRefrence.getPaint().setAntiAlias(true);//抗锯齿

        rlNextPerson.setOnClickListener(this);
        rlNextFlow.setOnClickListener(this);
        btnExcute.setOnClickListener(this);
        tvRefrence.setOnClickListener(this);
        tvAdvice.setOnClickListener(this);
        getNextPerson.setLoadingListener(this);
        caseDoExcute.setLoadingListener(this);
        rgIsPass.setOnCheckedChangeListener(this);
        rgPenalty.setOnCheckedChangeListener(this);
        rgFinishMonney.setOnCheckedChangeListener(this);
        rgDispose.setOnCheckedChangeListener(this);
        rgPubnish.setOnCheckedChangeListener(this);

        //根据环节编码，控制要显示的界面
//        hjbm = mEntity.getfHjBm();

        setViewVisableByHjbm(true);

        loadNextPerson();

    }

    //加载下一流程处理人
    private void loadNextPerson() {
        getNextPerson.loadData(mEntity.getId(), isPass, mEntity.getTaskId());
    }


    /**
     * 根据环节编码，设置界面的隐藏
     *
     * @param isShow 是否显示
     */
    private void setViewVisableByHjbm(boolean isShow) {
        switch (hjbm) {
            case "10101"://立案登记申请
                if (isShow) {
                    llNextPerson.setVisibility(View.VISIBLE);
                }
                break;
            case "10102"://立案登记立案审查
                if (isShow) {
                    llExcute.setVisibility(View.VISIBLE);
                    llNextPerson.setVisibility(View.VISIBLE);
                }
                break;
            case "10103"://立案登记立案审核
                if (isShow) {
                    llExcute.setVisibility(View.VISIBLE);
                    llNextPerson.setVisibility(View.VISIBLE);
                }
                break;
            case "10104"://立案登记立案审批
                if (isShow) {
                    llExcute.setVisibility(View.VISIBLE);
                    llNextPerson.setVisibility(View.VISIBLE);
                }
                break;
            case "20101"://调查取证
                if (isShow) {
                    llDxyj.setVisibility(View.VISIBLE);
                    llCfyj.setVisibility(View.VISIBLE);
                    tvAdvice.setVisibility(View.VISIBLE);
                    tvRefrence.setVisibility(View.VISIBLE);
                    llPenaltyOpinion.setVisibility(View.VISIBLE);
                    llNextPerson.setVisibility(View.VISIBLE);
                    etExcuteOpinion.setVisibility(View.GONE);
                    llPenaltyMonney.setVisibility(View.VISIBLE);
                    llIllegalMonney.setVisibility(View.VISIBLE);
                    llConfisMonney.setVisibility(View.VISIBLE);
                } else {
                    llDxyj.setVisibility(View.GONE);
                    llCfyj.setVisibility(View.GONE);
                    tvAdvice.setVisibility(View.GONE);
                    tvRefrence.setVisibility(View.GONE);
                    llPenaltyMonney.setVisibility(View.GONE);
                    llIllegalMonney.setVisibility(View.GONE);
                    llConfisMonney.setVisibility(View.GONE);
                }
                break;
            case "20102"://处罚审查
                selectListFlow.clear();
                if (isShow) {
                    selectListFlow.add(new SelectPopDataList("处罚审核", "1"));
                    selectListFlow.add(new SelectPopDataList("案审合议", "2"));
                    selectListFlow.add(new SelectPopDataList("案审办初审", "3"));
                    llExcute.setVisibility(View.VISIBLE);
                    llNextPerson.setVisibility(View.VISIBLE);
                    llNextFlow.setVisibility(View.VISIBLE);
                } else {
                    llNextFlow.setVisibility(View.GONE);
                }
                break;
            case "20103"://处罚核审
                selectListFlow.clear();
                if (isShow) {
                    selectListFlow.add(new SelectPopDataList("集体审核", "1"));
                    selectListFlow.add(new SelectPopDataList("处罚审批", "2"));
                    llExcute.setVisibility(View.VISIBLE);
                    llNextPerson.setVisibility(View.VISIBLE);
                    llNextFlow.setVisibility(View.VISIBLE);
                } else {
                    llNextFlow.setVisibility(View.GONE);
                }
                break;
            case "20104"://案审办初审
                selectListFlow.clear();
                if (isShow) {
                    selectListFlow.add(new SelectPopDataList("集体审核", "1"));
                    selectListFlow.add(new SelectPopDataList("处罚审批", "2"));
                    llExcute.setVisibility(View.VISIBLE);
                    llNextPerson.setVisibility(View.VISIBLE);
                    llNextFlow.setVisibility(View.VISIBLE);
                } else {
                    llNextFlow.setVisibility(View.GONE);
                }
                break;
            case "20105"://案审合议
                selectListFlow.clear();
                if (isShow) {
                    selectListFlow.add(new SelectPopDataList("集体审核", "1"));
                    selectListFlow.add(new SelectPopDataList("处罚审批", "2"));
                    llExcute.setVisibility(View.VISIBLE);
                    llNextPerson.setVisibility(View.VISIBLE);
                    llNextFlow.setVisibility(View.VISIBLE);
                } else {
                    llNextFlow.setVisibility(View.GONE);
                }
                break;
            case "20106"://集体审批
                if (isShow) {
                    llExcute.setVisibility(View.VISIBLE);
                    llNextPerson.setVisibility(View.VISIBLE);
                }
                break;
            case "20107"://处罚审批
                if (isShow) {
                    llExcute.setVisibility(View.VISIBLE);
                    llNextPerson.setVisibility(View.VISIBLE);
                }
                break;
            case "30101"://行政处罚决定
                if (isShow) {
                    llNextPerson.setVisibility(View.VISIBLE);
                } else {
                }
                break;
            case "30102"://处罚决定审查
//                rb_case_isPass2.setVisibility(View.VISIBLE);
//                if (isShow) {
//                    llExcute.setVisibility(View.VISIBLE);
//                    llNextPerson.setVisibility(View.VISIBLE);
//                    rlNextPerson.setClickable(true);
//                } else if (isShow == false && isPass == 0) {
//                    llExcute.setVisibility(View.VISIBLE);
//                    llNextPerson.setVisibility(View.VISIBLE);
//                    rlNextPerson.setClickable(true);
//                } else if (isPass == 2) {//重新办理
//                    llNextPerson.setVisibility(View.VISIBLE);
//                    selectListPerson.clear();
//                    fSlrInfo = new SelectPopDataList(mEntity.getfDcqzXzr(), mEntity.getfDcqzZxrId());
//                    selectListPerson.add(fSlrInfo);
//                    rlNextPerson.setClickable(false);
//                    tvNextPerson.setText(mEntity.getfDcqzXzr());
//                    Util.showInfoDialog(getActivity(), "提示", "该操作将转调查取证流程，重新调查");
//                }
                break;
            case "30103"://处罚决定审批
                if (isShow) {
                    llExcute.setVisibility(View.VISIBLE);
                    llNextPerson.setVisibility(View.VISIBLE);
                }
                break;
            case "30104"://行政处罚决定送达
                if (isShow) {
                    llNextPerson.setVisibility(View.VISIBLE);
                } else {
                }
                break;
            case "40101"://执行结案
                if (isShow) {
                    llNextPerson.setVisibility(View.GONE);
                    llMonneyFinish.setVisibility(View.VISIBLE);
                    llPublish.setVisibility(View.VISIBLE);
                    llDispose.setVisibility(View.VISIBLE);
                    etExcuteOpinion.setHint("请填写处理说明...");
                } else {
                    llMonneyFinish.setVisibility(View.GONE);
                    llPublish.setVisibility(View.GONE);
                    etExcuteOpinion.setHint("请填写未执行原因...");
                }
                break;
            default:
                showToast("环节编码获取失败，请重试");
                getActivity().finish();
                break;
        }
    }

    //根据环节编码，执行完成操作
    private void doExcuteByHjbm() {
        String userId = userInfo.getId();
        String description = etExcuteOpinion.getText().toString().trim();
        String taskId = mEntity.getTaskId();
        //对于结案时不需要选择下一流程处理人
        String name = "", id = "";
        if (fSlrInfo != null) {
            name = fSlrInfo.fTaskName.substring(fSlrInfo.fTaskName.indexOf("-") + 1, fSlrInfo.fTaskName.length());
            id = fSlrInfo.fTaskId;
        }
        switch (hjbm) {
            case "10101"://立案登记立案审查
                caseDoExcute.loadData("doLadjSqPro", mEntity.getId(), isPass, id, name, userId, description, taskId);
                break;
            case "10102"://立案登记立案审查
                caseDoExcute.loadData("doLadjscPro", mEntity.getId(), isPass, id, name, userId, description, taskId);
                break;
            case "10103"://立案登记立案审核
                caseDoExcute.loadData("doLadjshPro", mEntity.getId(), isPass, id, name, userId, description, taskId);
                break;
            case "10104"://立案登记立案审批
                caseDoExcute.loadData("doLadjspPro", mEntity.getId(), isPass, id, name, userId, description, taskId);
                break;
            case "20101"://调查取证
                String fAyBz = "";
                for (CaseRefeEntity.RowsBean rowsBean : referenceBeans) {
                    fAyBz += rowsBean.getFGuid() + ",";
                }
                caseDoExcute.loadData("doDcqzPro", mEntity.getId(), isPass, id, name, userId, description, taskId,
                        "fCfyj", isPenalty,
                        "fCfje", etPenaltyMonney.getText().toString().trim(),
                        "fWfsdje", etIllegalMonney.getText().toString().trim(),
                        "fMsje", etConfisMonney.getText().toString().trim(),
                        "fPunishLaw", etCfyj.getText().toString().replace("\n", ","),
                        "fAyWffg", etDxyj.getText().toString().replace("\n", ","),
                        "fAyBz", fAyBz);
                break;
            case "20102"://处罚审查
                caseDoExcute.loadData("doCfscPro", mEntity.getId(), isPass, id, name, userId, description, taskId);
                break;
            case "20103"://处罚核审
                caseDoExcute.loadData("doCfhsPro", mEntity.getId(), isPass, id, name, userId, description, taskId);
                break;
            case "20104"://案审办初审
                caseDoExcute.loadData("doAsbcsPro", mEntity.getId(), isPass, id, name, userId, description, taskId);
                break;
            case "20105"://案审合议
                caseDoExcute.loadData("doAshyPro", mEntity.getId(), isPass, id, name, userId, description, taskId);
                break;
            case "20106"://集体审批
                caseDoExcute.loadData("doCfJtsh", mEntity.getId(), isPass, id, name, userId, description, taskId);
                break;
            case "20107"://处罚审批
                caseDoExcute.loadData("doCfspPro", mEntity.getId(), isPass, id, name, userId, description, taskId);
                break;
            case "30101"://行政处罚决定
                caseDoExcute.loadData("doXzcfgzPro", mEntity.getId(), isPass, id, name, userId, description, taskId);
                break;
            case "30102"://处罚决定审查
                caseDoExcute.loadData("doXzfjdscPro", mEntity.getId(), isPass, id, name, userId, description, taskId);
                break;
            case "30103"://处罚决定审批
                caseDoExcute.loadData("doXzcfjdspPro", mEntity.getId(), isPass, id, name, userId, description, taskId);
                break;
            case "30104"://行政处罚决定送达
                caseDoExcute.loadData("doXzcfjdsdPro", mEntity.getId(), isPass, id, name, userId, description, taskId);
                break;
            case "40101"://执行结案
                caseDoExcute.loadData("doZxjaPro", mEntity.getId(), isPass, id, name, userId, description, taskId,
                        "fSfzx", isDispose,
                        "fFksfwj", isFinishMonney,
                        "fSfgs", isPublish,
                        "fWzxyy", description);
                break;
            default:
                showToast("环节编码获取失败，请重试");
                getActivity().finish();
                break;
        }
    }

    /**
     * 设置参考信息
     *
     * @param reBeanList
     */
    public void setReferenceInfo(List<CaseRefeEntity.RowsBean> reBeanList) {
        referenceBeans.clear();
        referenceBeans.addAll(reBeanList);
        String dxyj = "", cfyj = "";
        for (CaseRefeEntity.RowsBean rowsBean : referenceBeans) {
            String dxTemp = Html.fromHtml(rowsBean.getFViolateLaw()).toString();
            dxTemp = dxTemp.substring(0, dxTemp.indexOf("条") + 1);
            dxyj += dxTemp + "\n";
            String cfTemp = Html.fromHtml(rowsBean.getFPunishLaw()).toString();
            cfTemp = cfTemp.substring(0, cfTemp.indexOf("条") + 1);
            cfyj += cfTemp + "\n";
        }
        etDxyj.setText(dxyj.length() == 0 ? dxyj : dxyj.substring(0, dxyj.length() - 1));
        etCfyj.setText(cfyj.length() == 0 ? cfyj : cfyj.substring(0, cfyj.length() - 1));
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_case_excute:
                Util.showYesOrNoDialog(getActivity(), "提示", "是否提交?", "确定", "取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkInfo();
                        Util.dialog.dismiss();
                    }
                }, null);
                break;
            case R.id.rl_case_nextFlow:
                showSelectWindow(rlNextFlow, 0);
                break;
            case R.id.rl_case_nextPerson:
                showSelectWindow(rlNextPerson, 1);
                break;
            case R.id.tv_case_reference://参考
                if (caseReferenceView == null) {
                    caseReferenceView = new CaseReferenceView(this);
                }
                caseReferenceView.show();
                break;
            case R.id.tv_case_opinion:
                if (referenceBeans.size() > 0) {
                    String message = "";
                    for (CaseRefeEntity.RowsBean rowsBean : referenceBeans) {
                        message += Html.fromHtml(rowsBean.getFDiscretionStandard()).toString().replace("\n\n", "\n") + "\n";
                    }
                    if (dialog == null) {
                        AlertDialog.Builder buider = new AlertDialog.Builder(getActivity());
                        buider.setMessage(message);
                        buider.setNegativeButton("取消", null);
                        dialog = buider.show();
                        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
                        layoutParams.height = 600;
                        textView.setLayoutParams(layoutParams);
                        textView.setTextSize(10);
                        textView.setMovementMethod(new ScrollingMovementMethod());
                    } else {
                        dialog.setMessage(message);
                        dialog.show();
                    }
                } else {
                    showToast("请先在“参考”中选择依据");
                }
                break;
            default:
                break;
        }
    }

    //检查信息
    private void checkInfo() {
        String userId = userInfo.getId();
        String description = etExcuteOpinion.getText().toString().trim();
        if (etExcuteOpinion.getVisibility() == View.VISIBLE && description.length() == 0) {
            etExcuteOpinion.setError("请填写说明");
            return;
        } else if (userId.length() == 0) {
            showToast("用户信息获取失败，请重新登录");
            activity.clearLogin();
            startActivity(new Intent(activity, LoginActivity.class));
            MyApplication.getInstance().removeUntilLogin();
            return;
        } else if (selectListFlow.size() > 0 && fFlowInfo == null && llNextFlow.getVisibility() == View.VISIBLE) {
            tvNextFlow.setError("请选择下一流程");
            return;
        } else if (selectListPerson.size() > 0 && fSlrInfo == null && llNextPerson.getVisibility() == View.VISIBLE) {
            tvNextPerson.setError("请选择下一流程处理人");
            return;
        } else if (etDxyj.isShown() && etDxyj.getText().toString().length() == 0) {
            etDxyj.setError("请选择或输入定性依据");
            return;
        } else if (etCfyj.isShown() && etCfyj.getText().toString().length() == 0) {
            etCfyj.setError("请选择或输入处罚依据");
            return;
        }
        doExcuteByHjbm();
    }

    @Override
    public void onLoadStart(int id) {
        if (id == ApiData.FILE_UPLOAD) {
            showProgressDialog("图片正在上传，请稍后...");
        } else {
            super.onLoadStart(id);
        }
    }

    @Override
    public void onLoadPregress(int id, int progress) {
        if (id == ApiData.FILE_UPLOAD) {
            dismissProgressDialog();
            Util.showProgressDialog(activity, progress, "正在上传中");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onLoadComplete(int id, BaseHttpResult b) {
        if (id != ApiData.FILE_UPLOAD) {
            super.onLoadComplete(id, b);
        }
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
            case ApiData.HTTP_ID_caseDoExcute:
                showToast("处理成功");
                activity.setResult(ConstStrings.Request_Success, null);
                activity.finish();
                break;

            default:
                break;
        }
    }

    //打开选择框
    private boolean showSelectWindow(View view, int index) {
        activity.closeSoftInput();
        if (index == 0) {//下一流程
            if (mPopNextFlow == null) {
                mPopNextFlow = new SuperviseSelectPopuView(activity);
                mPopNextFlow.setDataSelectListener(selectListener);
            }
            return mPopNextFlow.show(view, view.getWidth(), selectListFlow, index);
        } else {//下一流程处理人
            if (mPopNextPerson == null) {
                mPopNextPerson = new SuperviseSelectPopuView(activity);
                mPopNextPerson.setDataSelectListener(selectListener);
            }
            return mPopNextPerson.show(view, view.getWidth(), selectListPerson, index);
        }

    }

    //下拉框的选择事件
    ICommonListener.IOnInfoSelectListener selectListener = new ICommonListener.IOnInfoSelectListener() {
        @Override
        public void onSelect(int pos, SelectPopDataList data, int index) {
            switch (index) {
                case 0://流程
                    tvNextFlow.setText(data.fTaskName);
                    fFlowInfo = data;
                    isPass = Integer.parseInt(data.fTaskId);
                    tvNextPerson.setText("下一流程处理人");
                    loadNextPerson();
                    break;
                case 1://流程角色
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
                if (rgIsPass.getCheckedRadioButtonId() == R.id.rb_case_isPass1) {//通过
                    isPass = 1;
                    setViewVisableByHjbm(true);
                } else if (rgIsPass.getCheckedRadioButtonId() == R.id.rb_case_isPass0) {//退回
                    isPass = 0;
                    setViewVisableByHjbm(false);
                } else {//重新办理
                    isPass = 2;
                    setViewVisableByHjbm(false);
                    return;
                }
                fSlrInfo = null;
                tvNextPerson.setText("下一流程处理人");
                loadNextPerson();
                break;
            case R.id.rg_case_penalty://是否处罚
                if (rgPenalty.getCheckedRadioButtonId() == R.id.rb_case_penalty1) {
                    setViewVisableByHjbm(true);
                    isPenalty = 1;
                } else {
                    setViewVisableByHjbm(false);
                    isPenalty = 0;
                }
                //不管是否处罚，都是通过
                break;
            case R.id.rg_case_dispose://是否执行
                if (rgDispose.getCheckedRadioButtonId() == R.id.rb_case_dispose1) {
                    setViewVisableByHjbm(true);
                    isDispose = 1;
                } else {
                    setViewVisableByHjbm(false);
                    isDispose = 0;
                }
                break;
            case R.id.rg_case_monneyFinish://罚款是否完结
                if (rgFinishMonney.getCheckedRadioButtonId() == R.id.rb_case_monneyFinish1) {
                    isFinishMonney = 1;
                } else {
                    isFinishMonney = 0;
                }
                break;
            case R.id.rg_case_publish://是否公示
                if (rgPubnish.getCheckedRadioButtonId() == R.id.rb_case_publish1) {
                    isPublish = 1;
                } else {
                    isPublish = 0;
                }
                break;
            default:
                break;
        }
    }
}
