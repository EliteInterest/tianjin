package com.zx.tjmarketmobile.ui.supervise;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.R.id;
import com.zx.tjmarketmobile.R.layout;
import com.zx.tjmarketmobile.entity.CheckInfo;
import com.zx.tjmarketmobile.entity.HttpTaskEntity;
import com.zx.tjmarketmobile.entity.SuperviseDetailInfo;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.ui.map.WorkInMapShowActivity;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.GpsTool;
import com.zx.tjmarketmobile.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2016/9/22
 * 功能：监管任务待处置页面
 */
public class SuperviseDisposeActivity extends BaseActivity implements OnClickListener {

    private LinearLayout llCheck;
    private List<CheckInfo> mCheckList;
    private ApiData searchkData = new ApiData(ApiData.HTTP_ID_supervisetask_searchcheck);
    private ApiData saveData = new ApiData(ApiData.HTTP_ID_supervisetask_save);
    private ApiData submitData = new ApiData(ApiData.HTTP_ID_supervisetask_submit);
    private ApiData submitLocation = new ApiData(ApiData.HTTP_ID_submitLocation);
    private String mGuid;
    private EditText et_ohter;
    private Dialog mCheckDialog;
    private String mStrCheckResult;// 处理结果
    private int valueType = 0;
    private Location location = null;
    private int mCheckResult = 1;// 0代表违规，1代表未违规
    private SuperviseDetailInfo mSuperviseDetailInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisedispose);

        addToolBar(true);
        hideRightImg();
        setMidText("任务处理");

        searchkData.setLoadingListener(this);
        saveData.setLoadingListener(this);
        submitLocation.setLoadingListener(this);
        submitData.setLoadingListener(this);
        mStrCheckResult = "未发现违法违规行为";
        mCheckList = new ArrayList<CheckInfo>();
        et_ohter = (EditText) findViewById(id.etActSuperviseDispose_other);
        llCheck = (LinearLayout) findViewById(R.id.llActSuperviseDispost_check);
        findViewById(id.btn_gotomap).setOnClickListener(this);
        findViewById(id.btnActDispose_execute).setOnClickListener(this);
        mSuperviseDetailInfo = (SuperviseDetailInfo) getIntent().getSerializableExtra("detailInfo");
        mGuid = mSuperviseDetailInfo.getGuid();
//        mGuid = getIntent().getStringExtra("guid");
//        String type = getIntent().getStringExtra("type");
        searchkData.loadData(mGuid);
    }

    //添加指标选择View
    private void addView() {
        llCheck.removeAllViews();
        View viewTitle = View.inflate(this, R.layout.item_check_fordispose, null);
//		LinearLayout llMainTitle = (LinearLayout) viewTitle.findViewById(R.id.ll_main);
        TextView tvNameTitle = (TextView) viewTitle.findViewById(R.id.tvItemComplain_name_fordispose);
        tvNameTitle.setText("指标名称");
        tvNameTitle.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        tvNameTitle.setGravity(Gravity.CENTER);
        viewTitle.findViewById(R.id.rg_flag_fordispose).setVisibility(View.GONE);
        viewTitle.findViewById(R.id.tv_foryes).setVisibility(View.VISIBLE);
        viewTitle.findViewById(R.id.tv_forno).setVisibility(View.VISIBLE);
        llCheck.addView(viewTitle);
        for (int i = 0; i < mCheckList.size(); i++) {
            final CheckInfo checkInfo = mCheckList.get(i);
            View view = View.inflate(this, R.layout.item_check_fordispose, null);
            TextView tvName = (TextView) view.findViewById(id.tvItemComplain_name_fordispose);
            final RadioGroup rgCheck = (RadioGroup) view.findViewById(id.rg_flag_fordispose);
            final RadioButton rbCheckYes = (RadioButton) view.findViewById(id.rb_flag_yes_fordispose);
            final RadioButton rbCheckNo = (RadioButton) view.findViewById(id.rb_flag_no_fordispose);
            LinearLayout llNum = (LinearLayout) view.findViewById(id.llItemCheck_right_fordispose);
            EditText edtNum = (EditText) view.findViewById(R.id.edtItemCheck_num_fordispose);
//            if ("0".equals(checkInfo.fValueType)) {
            rgCheck.setVisibility(View.VISIBLE);
            llNum.setVisibility(View.GONE);
//            } else if ("1".equals(checkInfo.fValueType)) {
//                rgCheck.setVisibility(View.GONE);
//                llNum.setVisibility(View.VISIBLE);
//                edtNum.setText(checkInfo.fValueMax);
//            }
            rgCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checId) {
                    group.check(checId);
                    switch (checId) {
                        case id.rb_flag_yes_fordispose:
                            checkInfo.setCheckResult("1");
                            break;
                        case id.rb_flag_no_fordispose:
                            checkInfo.setCheckResult("0");
                            break;
                        default:
                            break;
                    }
                }
            });
            edtNum.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    checkInfo.setCheckResult(s.toString());
                }
            });
            tvName.setText((i + 1) + "." + checkInfo.getItemName());
            llCheck.addView(view);
        }
    }

    //坐标纠正
    private void addLocationChangeView() {
        findViewById(id.btn_gotomap).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_locationlayout).setVisibility(View.VISIBLE);
        if (!GpsTool.isOpen(this)) {
            GpsTool.openGPS(this);
        } else {
            location = GpsTool.getGpsLocation(this);
            if (location != null) {
                ((TextView) findViewById(id.tv_location)).setText(location.getLongitude() + "，" + location.getLatitude());
            } else {
                ((TextView) findViewById(id.tv_location)).setText("当前坐标定位失败，请重试");
            }
        }
    }

    //添加其他
    private void addOtherView() {
        et_ohter.setVisibility(View.VISIBLE);
    }

    private void showDialog() {
        if (mCheckDialog == null) {
            mCheckDialog = new Dialog(mContext);
            mCheckDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mCheckDialog.setContentView(layout.dialog_check);
            mCheckDialog.setCanceledOnTouchOutside(true);
        }
        RadioGroup rgTransgress = (RadioGroup) mCheckDialog.findViewById(id.rgDialogCheck_transgress);
        final EditText edtRemark = (EditText) mCheckDialog.findViewById(R.id.edtDialogCheck_remark);
        rgTransgress.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case id.rbDialogCheck_transgress:
                        mCheckResult = 0;
                        edtRemark.setText("");
                        mStrCheckResult = "发现违法违规行为";
                        break;
                    case id.rbDialogCheck_notransgress:
                        mCheckResult = 1;
                        edtRemark.setText("");
                        mStrCheckResult = "未发现违法违规行为";
                        break;
                    default:
                        break;
                }
            }
        });
        mCheckDialog.findViewById(id.btnDialogCheck_cancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckDialog.dismiss();
            }
        });
        mCheckDialog.findViewById(id.btnDialogCheck_submit).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckDialog.dismiss();
                String str = edtRemark.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    mStrCheckResult += "-" + str;
                }
                submitData.loadData(userInfo.getId(), mGuid, mStrCheckResult, mCheckResult, new Gson().toJson(mCheckList));
            }
        });
        mCheckDialog.show();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        if (b.isSuccess()) {
            switch (id) {
                case ApiData.HTTP_ID_supervisetask_searchcheck:
                    mCheckList = (List<CheckInfo>) b.getEntry();
                    for (int i = 0; i < mCheckList.size(); i++) {
                        if (mCheckList.get(i).getType().equals("2")) {
                            valueType = 2;
                        } else if (mCheckList.get(i).getType().equals("1") && valueType != 2) {
                            valueType = 1;
                        }
                    }
                    switch (valueType) {
                        case 0:
                            addView();
                            break;
                        case 1:
                            addView();
//                            addOtherView();
                            break;
                        case 2:
                            addLocationChangeView();
                            break;
                        default:
                            break;
                    }
                    break;
                case ApiData.HTTP_ID_supervisetask_save:
                    showToast("保存成功");
                    setResult(ConstStrings.Request_Success, null);
                    finish();
                    break;
                case ApiData.HTTP_ID_supervisetask_submit:
                    showToast("提交成功");
                    setResult(ConstStrings.Request_Success, null);
                    finish();
                    break;
                case ApiData.HTTP_ID_submitLocation:
                    submitData.loadData(userInfo.getId(), mGuid, "", "", "");
                    break;
                default:
                    break;
            }
        } else {
            showToast("系统异常，请稍后再试");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.btnActDispose_execute:
                switch (valueType) {
                    case 0://仅判断
                        if (checkItems()) {
                            // 提交
                            showDialog();
                        } else {
                            Util.showToast(this, "请完成指标项");
                        }
                        break;
                    case 1://其他

                        break;
                    case 2://定位
                        if (location != null) {
                            submitLocation.loadData(mGuid, location.getLongitude(), location.getLatitude());
                        } else {
                            showToast("坐标定位不成功，请先进行定位");
                        }
                        break;
                    default:
                        break;
                }
                break;
            case R.id.btn_gotomap:
                if (location != null) {
                    HttpTaskEntity task = new HttpTaskEntity();
                    task.setAddress(mSuperviseDetailInfo.getEntityAddress());
                    task.setTaskId(mSuperviseDetailInfo.getTaskCode());
                    task.setEntityName(mSuperviseDetailInfo.getEntityName());
                    task.setEntityGuid(mSuperviseDetailInfo.getEntityId());
                    task.setTaskName(mSuperviseDetailInfo.getTaskName());
                    task.setTaskType(0);
                    task.setWkid(4490);
                    task.setLatitude(location.getLatitude());
                    task.setLongitude(location.getLongitude());
//			task.setTimeType(mSuperviseDetailInfo.timeType);
                    Intent intent = new Intent(SuperviseDisposeActivity.this, WorkInMapShowActivity.class);
                    intent.putExtra("type", ConstStrings.MapType_TaskDetail);
                    intent.putExtra("entity", (Serializable) task);
                    startActivity(intent);
                } else {
                    ((TextView) findViewById(id.tv_location)).setText("当前坐标定位失败，请重试");
                }
                break;
            default:
                break;
        }
    }

    private boolean checkItems() {
        for (int i = 1; i < llCheck.getChildCount(); i++) {
            View view = llCheck.getChildAt(i);
            RadioGroup rg = (RadioGroup) view.findViewById(id.rg_flag_fordispose);
            if (rg.getCheckedRadioButtonId() == -1) {
                return false;
            }
        }
        return true;
    }

}