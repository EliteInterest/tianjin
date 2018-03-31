package com.zx.gamarketmobile.ui.caselegal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.entity.CaseInfoEntity;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.http.BaseHttpResult;
import com.zx.gamarketmobile.manager.UserManager;
import com.zx.gamarketmobile.ui.base.BaseActivity;
import com.zx.gamarketmobile.util.ConstStrings;
import com.zx.gamarketmobile.util.Util;

import java.util.ArrayList;

import me.iwf.photopicker.widget.MultiPickResultView;

/**
 * Create By Xiangb On 2017/3/16
 * 功能：强制措施
 */

public class CaseForceActivity extends BaseActivity {

    private EditText etForceOpinion;//说明
    private Button btnExcute;
    private TextView tvDate;
    private EditText etPerson;
    private MultiPickResultView mprvPhoto;//图片上传界面
    private ArrayList<String> photoPaths;

    private CaseInfoEntity mEntity;

    private ApiData doForce = new ApiData(ApiData.HTTP_ID_caseDoForce);
    private ApiData picUpload = new ApiData(ApiData.FILE_UPLOAD);
    private ApiData caseSave = new ApiData(ApiData.HTTP_ID_caseSaveAyfj);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_force);
        initView();
    }

    private void initView() {
        addToolBar(true);
        hideRightImg();
        setMidText("强制措施");

        etForceOpinion = (EditText) findViewById(R.id.et_case_forceOpinion);
        btnExcute = (Button) findViewById(R.id.btn_case_forceExcute);
        tvDate = (TextView) findViewById(R.id.tv_case_forceDate);
        etPerson = (EditText) findViewById(R.id.et_case_forcePerson);
        mprvPhoto = (MultiPickResultView) findViewById(R.id.mprv_photo);

        if (getIntent().hasExtra("entity")) {
            mEntity = (CaseInfoEntity) getIntent().getSerializableExtra("entity");
        }

        mprvPhoto.init(this, MultiPickResultView.ACTION_SELECT, null);

        tvDate.setOnClickListener(this);
        btnExcute.setOnClickListener(this);
        doForce.setLoadingListener(this);
        picUpload.setLoadingListener(this);
        caseSave.setLoadingListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_case_forceExcute:
                Util.showYesOrNoDialog(this, "提示", "是否提交?", "确定", "取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (etForceOpinion.getText().toString().trim().length() == 0) {
                            showToast("请填写强制措施执行说明...");
                        } else if (tvDate.getText().toString().trim().length() == 0) {
                            showToast("请选择执行时间");
                        } else if (etPerson.getText().toString().trim().length() == 0) {
                            showToast("请输入执行人");
                        } else {

                            //有图片就进行图片上传
                            if (mprvPhoto.getVisibility() == View.VISIBLE && mprvPhoto.getPhotos().size() > 0) {
                                if (photoPaths == null) {
                                    photoPaths = new ArrayList<>();
                                }
                                photoPaths.clear();
                                for (int i = 0; i < mprvPhoto.getPhotos().size(); i++) {
                                    photoPaths.add(Util.getSmallBitmapPath(mprvPhoto.getPhotos().get(i)));
                                }
//            photoPaths = mprvPhoto.getPhotos();
                                if (photoPaths.size() > 0) {
                                    String[] paths = new String[photoPaths.size()];
                                    for (int i = 0; i < photoPaths.size(); i++) {
                                        paths[i] = photoPaths.get(i);
                                    }
                                    picUpload.loadData(0, paths, "case");
                                }
                            } else {//无图片选项就直接进行提交操作
                                doExcute();
                            }

                        }
                        Util.dialog.dismiss();
                    }
                }, null);
                break;
            case R.id.tv_case_forceDate:
                Util.setDateIntoText(this, tvDate);
                break;
            default:
                break;
        }
    }

    /**
     * 提交
     */
    private void doExcute() {
        String userId = userInfo.getId();
        String date = tvDate.getText().toString().replace("年", "-").replace("月", "-").replace("日", "");
        doForce.loadData(mEntity.getfId(), userId, etForceOpinion.getText().toString().trim(), etPerson.getText().toString().trim(), date);
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
            Util.showProgressDialog(this, progress, "正在上传中");
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        if (id != ApiData.FILE_UPLOAD) {
            super.onLoadComplete(id, b);
        }
        switch (id) {
            case ApiData.HTTP_ID_caseDoForce:
                showToast("处理成功");
                setResult(ConstStrings.Request_Success, null);
                finish();
                break;
            case ApiData.FILE_UPLOAD:
                Util.closeProgressDialog();
                String realNameListStr = b.getEntry().toString();
                StringBuffer realNameBuffer = new StringBuffer();
                String fileNameListStr;
                if (photoPaths != null && photoPaths.size() > 0) {
                    for (int i = 0; i < photoPaths.size(); i++) {
                        String path = photoPaths.get(i);
                        if (!TextUtils.isEmpty(path)) {
                            String name = path.substring(path.lastIndexOf("/") + 1, path.length());
                            realNameBuffer.append(",").append(name);
                        }
                    }
                    fileNameListStr = realNameBuffer.toString();
                    fileNameListStr = fileNameListStr.substring(1);
                    if (!TextUtils.isEmpty(realNameListStr)) {
                        String fUploadPerson = userInfo.getUserName();
                        if (!TextUtils.isEmpty(mEntity.getfId()) && !TextUtils.isEmpty(fUploadPerson)) {
                            caseSave.loadData(mEntity.getfId(), userInfo.getId(), realNameListStr, fileNameListStr);
                        }
                    }
                }
                break;
            case ApiData.HTTP_ID_caseSaveAyfj:
//                showToast("上传成功");
                doExcute();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mprvPhoto.onActivityResult(requestCode, resultCode, data);
    }
}
