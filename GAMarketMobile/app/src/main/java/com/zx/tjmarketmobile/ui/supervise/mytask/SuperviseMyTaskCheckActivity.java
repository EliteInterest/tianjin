package com.zx.tjmarketmobile.ui.supervise.mytask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.supervise.SuperviseMyTaskCheckResultAdapter;
import com.zx.tjmarketmobile.entity.CheckInfo;
import com.zx.tjmarketmobile.entity.EntityPictureBean;
import com.zx.tjmarketmobile.entity.ImageEntity;
import com.zx.tjmarketmobile.entity.supervise.MyTaskCheckEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.InScrollRecylerManager;
import com.zx.tjmarketmobile.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.listener.OnDeleteListener;
import me.iwf.photopicker.widget.MultiPickResultView;


/**
 * Created by zhouzq on 2017/3/23.
 */

public class SuperviseMyTaskCheckActivity extends BaseActivity implements View.OnClickListener {

    private ApiData getCheckinfo = new ApiData(ApiData.HTTP_ID_supervisetask_searchcheck);
    private ApiData saveItemResult = new ApiData(ApiData.HTTP_ID_supervise_saceItemResult);
    private ApiData finishItem = new ApiData(ApiData.HTTP_ID_supervise_finishItem);
    private ApiData getFiles = new ApiData(ApiData.HTTP_ID_supervise_getTaskFiles);
    private ApiData deleteFile = new ApiData(ApiData.HTTP_ID_supervise_deleteFile);
    private ApiData updateImg = new ApiData(ApiData.FILE_UPLOAD);
    private MyTaskCheckEntity mEntityBean;
    private RecyclerView checkResultRecycleView;
    private SuperviseMyTaskCheckResultAdapter mAdapter;
    private List<CheckInfo> dataList = new ArrayList<>();
    private List<ImageEntity> imageEntities = new ArrayList<>();
    //    private LinearLayout checkExcuteLayout, checkResultLayout;
    private Button checkExcuteBtn;
    private RadioGroup rgillegal;
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
        rgillegal = findViewById(R.id.rg_super_illegal);
        checkExcuteBtn.setOnClickListener(this);
        updateImg.setLoadingListener(this);
        saveItemResult.setLoadingListener(this);
        finishItem.setLoadingListener(this);
        getCheckinfo.setLoadingListener(this);
        deleteFile.setLoadingListener(this);
        getFiles.setLoadingListener(this);

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
        mAdapter = new SuperviseMyTaskCheckResultAdapter(dataList, index, isExcute, mEntityBean.getStatus());
        checkResultRecycleView.setAdapter(mAdapter);

        if (isExcute) {
            checkExcuteBtn.setVisibility(View.VISIBLE);
            mprv_task.setMaxPicNum(9).init(this, MultiPickResultView.ACTION_SELECT, taskphotoList, new OnDeleteListener() {
                @Override
                public void OnDetele(int positon) {
                    if (taskphotoList.get(positon).startsWith("http")) {
                        for (ImageEntity imageEntity : imageEntities) {
                            if (taskphotoList.get(positon).contains(imageEntity.getPath())) {
                                deleteFile.loadData(imageEntity.getId());
                                break;
                            }
                        }
                    }
                    taskphotoList.remove(positon);
                    mprv_task.notifyDataSetChanged();
                }
            });
        } else {
            if (type == 1) {
                setMidText("主体指标项");
            }
//            getTaskImg.loadData("", mEntityBean == null ? "" : mEntityBean.getFGuid());
            checkExcuteBtn.setVisibility(View.GONE);
            findViewById(R.id.rb_super_illegal0).setEnabled(false);
            findViewById(R.id.rb_super_illegal1).setEnabled(false);
            mprv_task.init(this, MultiPickResultView.ACTION_ONLY_SHOW, taskphotoList);
        }

        getFiles.loadData(mEntityBean.getId());
        getCheckinfo.loadData(mEntityBean.getId());
        if ("0".equals(mEntityBean.getIllegal())) {
            rgillegal.check(R.id.rb_super_illegal0);
        } else if ("1".equals(mEntityBean.getIllegal())) {
            rgillegal.check(R.id.rb_super_illegal1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mprv_task.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    @SuppressWarnings("unchecked")
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
                showToast("图片上传成功");
                Util.closeProgressDialog();
                saveItemInfo();
                break;
            case ApiData.HTTP_ID_supervise_saceItemResult:
                showToast("处置成功");
                finish();
                break;
            case ApiData.HTTP_ID_supervise_finishItem:
                showToast("该主体已完成处置");
                finish();
                break;
            case ApiData.HTTP_ID_supervise_getTaskFiles:
                imageEntities.clear();
                imageEntities.addAll((List<ImageEntity>) baseHttpResult.getEntry());
                for (ImageEntity imageEntity : imageEntities) {
                    taskphotoList.add(ApiData.baseUrl + imageEntity.getPath());
                }
                mprv_task.notifyDataSetChanged();
                break;
            case ApiData.HTTP_ID_supervise_deleteFile:
                showToast("文件删除成功");
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
            Util.showProgressDialog(this, progress, "正在上传中");
//            dismissProgressDialog();
        } else {
            super.onLoadPregress(id, progress);
        }
    }

    @Override
    public void onLoadStart(int id) {
        if (id == ApiData.FILE_UPLOAD) {
            Util.showProgressDialog(this, 0, "正在上传中");
        } else {
            super.onLoadStart(id);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_supervise_check_excute:
                for (CheckInfo checkInfo : dataList) {
                    if ((checkInfo.getChildren() == null || checkInfo.getChildren().size() == 0)
                            && (checkInfo.getCheckResult() == null || checkInfo.getCheckResult().length() == 0)) {
                        showToast("请先完成任务指标处理");
                        return;
                    }
                }
                if (rgillegal.getCheckedRadioButtonId() == -1) {
                    showToast("请选择是否违法");
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("taskId", mEntityBean.getId());
                List<String> tempList = new ArrayList<>();
                for (String path : taskphotoList) {
                    if (!path.startsWith("http")) {
                        tempList.add(path);
                    }
                }
                if (tempList.size() > 0) {
                    updateImg.loadData(0, tempList.toArray(new String[tempList.size()]), "/TJsupervise/taskDo/uploadFile.do", map);
                } else {
                    saveItemInfo();
                }
                break;
            default:
                break;
        }


    }

    private void saveItemInfo() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (CheckInfo checkInfo : dataList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("checkItemId", checkInfo.getId());
                jsonObject.put("checkResult", checkInfo.getCheckResult());
                jsonArray.put(jsonObject);
            }
            saveItemResult.loadData(mEntityBean.getId(), jsonArray, rgillegal.getCheckedRadioButtonId() == R.id.rb_super_illegal0 ? "0" : "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}