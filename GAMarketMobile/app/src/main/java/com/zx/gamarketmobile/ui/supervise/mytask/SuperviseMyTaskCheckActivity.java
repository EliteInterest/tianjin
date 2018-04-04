package com.zx.gamarketmobile.ui.supervise.mytask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.adapter.supervise.SuperviseMyTaskCheckResultAdapter;
import com.zx.gamarketmobile.entity.CheckInfo;
import com.zx.gamarketmobile.entity.EntityPictureBean;
import com.zx.gamarketmobile.entity.supervise.MyTaskCheckEntity;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.http.BaseHttpResult;
import com.zx.gamarketmobile.ui.base.BaseActivity;
import com.zx.gamarketmobile.util.ConstStrings;
import com.zx.gamarketmobile.util.InScrollRecylerManager;
import com.zx.gamarketmobile.util.Util;
import com.zx.gamarketmobile.util.ZXBitmapUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.widget.MultiPickResultView;


/**
 * Created by zhouzq on 2017/3/23.
 */

public class SuperviseMyTaskCheckActivity extends BaseActivity implements View.OnClickListener {

    private ApiData getCheckinfo = new ApiData(ApiData.HTTP_ID_supervisetask_searchcheck);
    private ApiData updateImg = new ApiData(ApiData.FILE_UPLOAD);
    private MyTaskCheckEntity mEntityBean;
    private RecyclerView checkResultRecycleView;
    private SuperviseMyTaskCheckResultAdapter mAdapter;
    private List<CheckInfo> dataList = new ArrayList<>();
    //    private LinearLayout checkExcuteLayout, checkResultLayout;
    private Button checkExcuteBtn;
    private int qualify = 0;//0代表违法，1代表合法
    private String remark = "";
    private int index = 0;
    private int type = 0;
    private int mixCharNum = 150;//最大字数
    private MultiPickResultView mprv_task;//图片选择器
    private ArrayList<String> taskphotoList = new ArrayList<>();//图片集合
    private List<EntityPictureBean> entityPictureList = new ArrayList<>();//图片集

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervise_mytask_check);
        initView();
    }

    //初始化
    private void initView() {
        addToolBar(true);
        hideRightImg();
        setMidText("任务处理");
        checkExcuteBtn = findViewById(R.id.btn_supervise_check_excute);
        checkResultRecycleView = findViewById(R.id.check_result_recycle);
        mprv_task = findViewById(R.id.mprv_mytask);
        checkExcuteBtn.setOnClickListener(this);
        updateImg.setLoadingListener(this);
        getCheckinfo.setLoadingListener(this);

        if (getIntent().getExtras().get("entity") != null) {
            mEntityBean = (MyTaskCheckEntity) getIntent().getExtras().get("entity");
//            taskState = mEntityBean.getFSTATUS();
            index = (int) getIntent().getExtras().get("index");
            type = (int) getIntent().getExtras().get("type");
        } else {
            checkResultRecycleView.setVisibility(View.GONE);
        }
        checkResultRecycleView.setLayoutManager(new InScrollRecylerManager(this));
        boolean isExcute = (type != 1 && index == 0);
        mAdapter = new SuperviseMyTaskCheckResultAdapter(dataList, index, isExcute);
        checkResultRecycleView.setAdapter(mAdapter);

        if (isExcute) {
            checkExcuteBtn.setVisibility(View.VISIBLE);
            mprv_task.setMaxPicNum(9).init(this, MultiPickResultView.ACTION_SELECT, taskphotoList);
        } else {
            if (type == 1) {
                setMidText("主体指标项");
            }
//            getTaskImg.loadData("", mEntityBean == null ? "" : mEntityBean.getFGuid());
            checkExcuteBtn.setVisibility(View.GONE);
            mprv_task.init(this, MultiPickResultView.ACTION_ONLY_SHOW, taskphotoList);
        }

        getCheckinfo.loadData(mEntityBean.getTaskId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mprv_task.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult baseHttpResult) {
        if (id != ApiData.FILE_UPLOAD) {
            super.onLoadComplete(id, baseHttpResult);
        }
        switch (id) {
            case ApiData.HTTP_ID_supervisetask_searchcheck:
                List<CheckInfo> checkInfoList = (List<CheckInfo>) baseHttpResult.getEntry();
                dataList.clear();
                resetData(checkInfoList);
//                dataList.addAll(checkInfoList);
                mAdapter.setStatus(-1, 0, 1);
                mAdapter.notifyDataSetChanged();
                break;
            case ApiData.FILE_UPLOAD:

                break;
            default:
                break;
        }
    }

    private void resetData(List<CheckInfo> checkInfoList) {
        for (int i = 0; i < checkInfoList.size(); i++) {
            checkInfoList.get(i).setItemName((i + 1) + "、" + checkInfoList.get(i).getItemName());
            dataList.add(checkInfoList.get(i));
            if (checkInfoList.get(i).getChildren() != null && checkInfoList.get(i).getChildren().size() > 0) {
                for (int j = 0; j < checkInfoList.get(i).getChildren().size(); j++) {
                    CheckInfo checkInfo2 = checkInfoList.get(i).getChildren().get(j);
                    checkInfo2.setItemName((i + 1) + "." + (j + 1) + "、" + checkInfo2.getItemName());
                    checkInfo2.setIndex(1);
                    dataList.add(checkInfo2);
                    if (checkInfo2.getChildren() != null && checkInfo2.getChildren().size() > 0) {
                        for (int k = 0; k < checkInfo2.getChildren().size(); k++) {
                            CheckInfo checkInfo3 = checkInfo2.getChildren().get(k);
                            checkInfo3.setItemName((i + 1) + "." + (j + 1) + "." + (k + 1) + "、" + checkInfo3.getItemName());
                            checkInfo3.setIndex(2);
                            dataList.add(checkInfo3);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onLoadError(int id, boolean bool, String errorMsg) {
        super.onLoadError(id, bool, errorMsg);
        showToast(errorMsg);
        if (id == ApiData.FILE_UPLOAD) {
            Util.closeProgressDialog();
        }
    }

    @Override
    public void onLoadPregress(int id, int progress) {
        if (id == ApiData.FILE_UPLOAD) {
            dismissProgressDialog();
        } else {
            super.onLoadPregress(id, progress);
        }
    }

    @Override
    public void onLoadStart(int id) {
        if (id == ApiData.FILE_UPLOAD) {
//            Util.showProgressDialog(this, 0, "正在上传中");
        } else {
            super.onLoadStart(id);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_supervise_check_excute:
                if (checkExcuteBtn.isEnabled()) {
                    if (taskphotoList.size() == 0) {
                        showToast("请上传主体相关照片");
                        break;
                    }
                    Util.showProgressDialog(this, 0, "正在上传图片中(图片需要压缩，请耐心等候)");
                } else {
                    showToast("未在主体区域内，无法提进行提交！");
                }
                break;
            default:
                break;
        }


    }

    private File compassImgFile(File file) {
        File outputFile = null;
        try {
            Bitmap bitmap = ZXBitmapUtil.fileToBitmap(file.getPath(), 480, 800);
            File dir = new File(ConstStrings.INI_PATH + "/" + ConstStrings.APPNAME + "/" + "file_cache/");
            dir.mkdirs();
            outputFile = new File(dir, file.getName());
            outputFile.createNewFile();
            ZXBitmapUtil.bitmapToFile(bitmap, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile;
    }

}
