package com.zx.gamarketmobile.ui.caselegal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.entity.CaseInfoEntity;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.http.BaseHttpResult;
import com.zx.gamarketmobile.ui.base.BaseActivity;
import com.zx.gamarketmobile.util.ConstStrings;
import com.zx.gamarketmobile.util.Util;

/**
 * Create By Xiangb On 2017/3/16
 * 功能：案件移送
 */

public class CaseTransferActivity extends BaseActivity {

    private EditText etTransferDept;//移送单位
    private EditText etTransferPerson;//移送人
    private TextView tvDate;//移送时间
    private Button btnExcute;

    private CaseInfoEntity mEntity;

    private ApiData doTransfer = new ApiData(ApiData.HTTP_ID_caseDoTransfer);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_transfer);
        initView();
    }

    private void initView() {
        addToolBar(true);
        hideRightImg();
        setMidText("案件移送");

        etTransferDept = (EditText) findViewById(R.id.et_case_transferDept);
        etTransferPerson = (EditText) findViewById(R.id.et_case_transferPerson);
        tvDate = (TextView) findViewById(R.id.tv_case_transferDate);
        btnExcute = (Button) findViewById(R.id.btn_case_transferExcute);

        if (getIntent().hasExtra("entity")) {
            mEntity = (CaseInfoEntity) getIntent().getSerializableExtra("entity");
        }

        tvDate.setOnClickListener(this);
        btnExcute.setOnClickListener(this);
        doTransfer.setLoadingListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_case_transferExcute:
                Util.showYesOrNoDialog(this, "提示", "是否提交?", "确定", "取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userId = userInfo.getId();
                        if (etTransferDept.getText().toString().trim().length() == 0) {
                            showToast("请填写移送单位");
                        } else if (etTransferPerson.getText().toString().trim().length() == 0) {
                            showToast("请填写移送人");
                        } else if (tvDate.getText().toString().length() == 0) {
                            showToast("请选择移送时间");
                        } else {
                            String person = etTransferPerson.getText().toString().trim();
                            String dept = etTransferDept.getText().toString().trim();
                            String date = tvDate.getText().toString().replace("年", "-").replace("月", "-").replace("日", "");
                            doTransfer.loadData(mEntity.getId(), userId, person, dept, date);
                        }
                        Util.dialog.dismiss();
                    }
                }, null);
                break;
            case R.id.tv_case_transferDate:
                Util.setDateIntoText(this, tvDate);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_caseDoTransfer:
                showToast("处理成功");
                setResult(ConstStrings.Request_Success, null);
                finish();
                break;
            default:
                break;
        }
    }
}
