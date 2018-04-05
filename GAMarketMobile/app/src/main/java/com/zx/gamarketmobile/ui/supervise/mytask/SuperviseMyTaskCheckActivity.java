package com.zx.gamarketmobile.ui.supervise.mytask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Line;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.SpatialReference;
import com.google.gson.Gson;
import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.adapter.supervise.SuperviseMyTaskCheckResultAdapter;
import com.zx.gamarketmobile.entity.EntityPictureBean;
import com.zx.gamarketmobile.entity.EntityPointBean;
import com.zx.gamarketmobile.entity.NormalListEntity;
import com.zx.gamarketmobile.entity.supervise.MyTaskCheckEntity;
import com.zx.gamarketmobile.entity.supervise.MyTaskCheckResultEntity;
import com.zx.gamarketmobile.entity.supervise.MyTaskCheckResultInfoEntity;
import com.zx.gamarketmobile.entity.supervise.MyTaskListEntity;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.http.BaseHttpResult;
import com.zx.gamarketmobile.listener.OnMapDialogListener;
import com.zx.gamarketmobile.ui.base.BaseActivity;
import com.zx.gamarketmobile.util.ConstStrings;
import com.zx.gamarketmobile.util.GpsTool;
import com.zx.gamarketmobile.util.InScrollRecylerManager;
import com.zx.gamarketmobile.util.ToastUtil;
import com.zx.gamarketmobile.util.Util;
import com.zx.gamarketmobile.util.ZXBitmapUtil;
import com.zx.gamarketmobile.util.ZXFileUtil;
import com.zx.gamarketmobile.view.MapViewDialog;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.iwf.photopicker.widget.MultiPickResultView;


/**
 * Created by zhouzq on 2017/3/23.
 */

public class SuperviseMyTaskCheckActivity extends BaseActivity implements View.OnClickListener {

    private ApiData executeMyTask = new ApiData(ApiData.HTTP_ID_Supervise_MyTask_executeMyTask);//获取相关处置信息
    private ApiData disposalTask = new ApiData(ApiData.HTTP_ID_Supervise_MyTask_disposalTask);//提交处置后结果
    private ApiData getCheckResultTask = new ApiData(ApiData.HTTP_ID_Supervise_MyTask_getCheckResult);//获取提交的处理结果和意见
    private ApiData getTaskImg = new ApiData(ApiData.HTTP_ID_superviseGetTaskImg);//获取图片
    private ApiData getEntityPoint = new ApiData(ApiData.HTTP_ID_superviseGetEntityPoint);//获取定位点
    private ApiData getDTByCondition = new ApiData(ApiData.HTTP_ID_superviseGetDTByCondition);//获取特种设备列表
    private ApiData appendEntityPoint = new ApiData(ApiData.HTTP_ID_superviceAppendEntityPoint);//追加主体任务点
    private ApiData getPicByEntityId = new ApiData(ApiData.HTTP_ID_getPicByEntityId);//根据id获取企业图片
    private ApiData updateImg = new ApiData(ApiData.FILE_UPLOAD);
    private MyTaskCheckEntity.RowsBean mEntityBean;
    private MyTaskListEntity.RowsBean mTaskBean;
    private RecyclerView checkResultRecycleView;
    private SuperviseMyTaskCheckResultAdapter mAdapter;
    private List<MyTaskCheckResultEntity.DataBean> dataList = new ArrayList<>();
    private int taskState;
    //    private LinearLayout checkExcuteLayout, checkResultLayout;
    private Button checkExcuteBtn;
    private RadioButton radioButtonSecord, radioButtonThird;
    private TextView tvLocationInfo, tvLocationRefresh, tvEquipment;
    private MyTaskCheckResultEntity myTaskCheckResultEntity;
    private int qualify = 0;//0代表违法，1代表合法
    private String remark = "";
    private int index = 0;
    private int type = 0;
    private int uploadIndex = 1;
    private String uploadImgUrl = "";
    private int mixCharNum = 150;//最大字数
    private EditText checkResultEdit;//结果
    private MultiPickResultView mprv_qymt, mprv_yyzz, mprv_task, mprv_task1, mprv_task2, mprv_task3;//图片选择器
    private ArrayList<String> qymtphotoList = new ArrayList<>();//图片集合
    private ArrayList<String> yyzzphotoList = new ArrayList<>();//图片集合
    private ArrayList<String> taskphotoList = new ArrayList<>();//图片集合
    private ArrayList<String> task1photoList = new ArrayList<>();//图片集合
    private ArrayList<String> task2photoList = new ArrayList<>();//图片集合
    private ArrayList<String> task3photoList = new ArrayList<>();//图片集合
    private List<EntityPointBean> entityPointList = new ArrayList<>();//定位点集合
    private List<EntityPictureBean> entityPictureList = new ArrayList<>();//图片集
    private MapViewDialog mapDialog;//地图弹出框
    private Point selectPoint;
    private Timer timer = new Timer();
    private Location location;

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
        radioButtonSecord = findViewById(R.id.radio_2);
        radioButtonThird = findViewById(R.id.radio_3);
        checkResultEdit = findViewById(R.id.et_supervise_task_check_excuteOpinion);
        checkResultRecycleView = findViewById(R.id.check_result_recycle);
        mprv_qymt = findViewById(R.id.mprv_qymt);
        mprv_yyzz = findViewById(R.id.mprv_yyzz);
        mprv_task = findViewById(R.id.mprv_mytask);
        mprv_task1 = findViewById(R.id.mprv_mytask1);
        mprv_task2 = findViewById(R.id.mprv_mytask2);
        mprv_task3 = findViewById(R.id.mprv_mytask3);
        tvLocationRefresh = findViewById(R.id.tv_location_refresh);
        tvLocationInfo = findViewById(R.id.tv_location_info);
        tvEquipment = findViewById(R.id.tv_supervise_equipment);
        executeMyTask.setLoadingListener(this);
        disposalTask.setLoadingListener(this);
        checkExcuteBtn.setOnClickListener(this);
        getCheckResultTask.setLoadingListener(this);
        getTaskImg.setLoadingListener(this);
        updateImg.setLoadingListener(this);
        tvEquipment.setOnClickListener(this);
        getEntityPoint.setLoadingListener(this);
        getDTByCondition.setLoadingListener(this);
        getPicByEntityId.setLoadingListener(this);
        appendEntityPoint.setLoadingListener(this);
        tvLocationRefresh.setOnClickListener(this);

        mapDialog = new MapViewDialog(this, MapViewDialog.MapType.pointRange,
                new OnMapDialogListener() {
                    @Override
                    public void selectPoint(Point selectPoint) {

                        Util.showEditDialog(SuperviseMyTaskCheckActivity.this, "提示", "是否添加该任务处置点？", "确定", "取消",
                                new Util.DialogEditListener() {
                                    @Override
                                    public void onOKBtnClick(String editString) {
                                        if (editString.length() > 0) {
                                            SuperviseMyTaskCheckActivity.this.selectPoint = selectPoint;
                                            appendEntityPoint.loadData(mEntityBean.getFGuid(), mEntityBean.getFEntityGuid(), selectPoint.getY(), selectPoint.getX(), editString);
                                            Util.dialog.dismiss();
                                        } else {
                                            showToast("请先填写任务处置点地址信息");
                                        }
                                    }
                                }, null);
                    }

                    @Override
                    public void onHide() {

                    }
                });
        mTaskBean = (MyTaskListEntity.RowsBean) getIntent().getExtras().get("myToDo");
        if (getIntent().getExtras().get("entity") != null) {
            mEntityBean = (MyTaskCheckEntity.RowsBean) getIntent().getExtras().get("entity");
            tvLocationInfo.setVisibility(View.VISIBLE);
            tvLocationRefresh.setVisibility(View.VISIBLE);
//            taskState = mEntityBean.getFSTATUS();
            index = (int) getIntent().getExtras().get("index");
            type = (int) getIntent().getExtras().get("type");
            getCheckResultTask.loadData(mEntityBean.getFGuid());

            dolocation();

            executeMyTask.loadData(mEntityBean.getFGuid());
            getEntityPoint.loadData(mEntityBean.getFEntityGuid());
            getDTByCondition.loadData(mEntityBean.getFEntityGuid(), 1, 1);
        } else {
            checkResultRecycleView.setVisibility(View.GONE);
        }
        taskState = mTaskBean.getStatus();
        radioButtonSecord.setChecked(true);
        radioButtonSecord.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                radioButtonThird.setChecked(false);
                qualify = 0;
            }
        });
        radioButtonThird.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                radioButtonSecord.setChecked(false);
                qualify = 1;
            }
        });
        checkResultEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                remark = s.toString();

                Editable editable = checkResultEdit.getText();
                int len = editable.length();
                if (len > mixCharNum) {
                    ToastUtil.getShortToastByString(SuperviseMyTaskCheckActivity.this, "超出字数限制,最多可输入150字！");
                    int selEndIndex = Selection.getSelectionEnd(editable);
                    String str = editable.toString();
                    //截取新字符串
                    String newStr = str.substring(0, mixCharNum);
                    checkResultEdit.setText(newStr);
                    editable = checkResultEdit.getText();
                    //新字符串的长度
                    int newLen = editable.length();
                    //旧光标位置超过字符串长度
                    if (selEndIndex > newLen) {
                        selEndIndex = editable.length();
                    }
                    //设置新光标所在的位置
                    Selection.setSelection(editable, selEndIndex);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        checkResultRecycleView.setLayoutManager(new InScrollRecylerManager(this));
        boolean isExcute = (type != 1 && index == 0 && /*"待处置".equals*/taskState == 1);
        mAdapter = new SuperviseMyTaskCheckResultAdapter(dataList, index, isExcute);
        checkResultRecycleView.setAdapter(mAdapter);

        if (isExcute) {
            checkExcuteBtn.setVisibility(View.VISIBLE);
//            checkResultEdit.setKeyListener(new BaseKeyListener() {
//                @Override
//                public int getInputType() {
//                    return InputType.TYPE_CLASS_TEXT;
//                }
//            });
            getPicByEntityId.loadData(mEntityBean.getFEntityGuid());
            mprv_qymt.setMaxPicNum(5).init(this, MultiPickResultView.ACTION_SELECT, qymtphotoList);
            mprv_yyzz.setMaxPicNum(5).init(this, MultiPickResultView.ACTION_SELECT, yyzzphotoList);
            mprv_task.setMaxPicNum(5).init(this, MultiPickResultView.ACTION_SELECT, taskphotoList);
            mprv_task1.setMaxPicNum(5).init(this, MultiPickResultView.ACTION_SELECT, task1photoList);
            mprv_task2.setMaxPicNum(5).init(this, MultiPickResultView.ACTION_SELECT, task2photoList);
            mprv_task3.setMaxPicNum(5).init(this, MultiPickResultView.ACTION_SELECT, task3photoList);
        } else {
            if (type == 1) {
                setMidText("主体指标项");
            }
            getTaskImg.loadData("", mEntityBean == null ? "" : mEntityBean.getFGuid());
            checkExcuteBtn.setVisibility(View.GONE);
            checkResultEdit.setKeyListener(null);
            radioButtonSecord.setClickable(false);
            radioButtonThird.setClickable(false);
            mprv_qymt.init(this, MultiPickResultView.ACTION_ONLY_SHOW, qymtphotoList);
            mprv_yyzz.init(this, MultiPickResultView.ACTION_ONLY_SHOW, yyzzphotoList);
            mprv_task.init(this, MultiPickResultView.ACTION_ONLY_SHOW, taskphotoList);
            mprv_task1.init(this, MultiPickResultView.ACTION_ONLY_SHOW, task1photoList);
            mprv_task2.init(this, MultiPickResultView.ACTION_ONLY_SHOW, task2photoList);
            mprv_task3.init(this, MultiPickResultView.ACTION_ONLY_SHOW, task3photoList);
        }
        //开启定时定位功能
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                locationHandle.sendEmptyMessage(0);
            }
        }, 0, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mprv_qymt.onActivityResult(requestCode, resultCode, data);
        mprv_yyzz.onActivityResult(requestCode, resultCode, data);
        mprv_task.onActivityResult(requestCode, resultCode, data);
        mprv_task1.onActivityResult(requestCode, resultCode, data);
        mprv_task2.onActivityResult(requestCode, resultCode, data);
        mprv_task3.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult baseHttpResult) {
        if (id != ApiData.FILE_UPLOAD) {
            super.onLoadComplete(id, baseHttpResult);
        }
        switch (id) {
            case ApiData.HTTP_ID_Supervise_MyTask_executeMyTask:
                myTaskCheckResultEntity = (MyTaskCheckResultEntity) baseHttpResult.getEntry();
                if (myTaskCheckResultEntity != null && myTaskCheckResultEntity.getData() != null) {
                    if (myTaskCheckResultEntity.getData().size() > 0) {
                        dataList.clear();
                        dataList.addAll(myTaskCheckResultEntity.getData());
                        mAdapter.setStatus(-1, 0, 1);
                        mAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case ApiData.HTTP_ID_Supervise_MyTask_disposalTask:
                showToast(baseHttpResult.getMessage());
                setResult(100);
                finish();
                break;
            case ApiData.HTTP_ID_Supervise_MyTask_getCheckResult:
                MyTaskCheckResultInfoEntity entity = (MyTaskCheckResultInfoEntity) baseHttpResult.getEntry();
                if (!TextUtils.isEmpty(entity.getfQualify())) {
                    if (entity.getfQualify().equals("0")) {//0代表未通过
                        radioButtonSecord.setChecked(true);
                        radioButtonThird.setChecked(false);
                    } else {
                        radioButtonSecord.setChecked(false);
                        radioButtonThird.setChecked(true);
                    }
                }
                if (!TextUtils.isEmpty(entity.getfResult()) && checkExcuteBtn.getVisibility() == View.GONE) {
                    checkResultEdit.setText(entity.getfResult());
                }
                break;
            case ApiData.HTTP_ID_superviseGetTaskImg:
                if (baseHttpResult.getEntry() != null) {
                    Map<String, List<String>> map = (Map<String, List<String>>) baseHttpResult.getEntry();
                    qymtphotoList.clear();
                    yyzzphotoList.clear();
                    task1photoList.clear();
                    task2photoList.clear();
                    task3photoList.clear();
                    List<String> url1List = map.get("1");
                    List<String> url2List = map.get("2");
                    List<String> url3List = map.get("3");
                    List<String> url4List = map.get("4");
                    List<String> url5List = map.get("5");
                    List<String> url6List = map.get("6");
                    for (String url : url1List) {
                        qymtphotoList.add("http://" + ApiData.getmIp() + "/" + url);
                    }
                    for (String url : url2List) {
                        yyzzphotoList.add("http://" + ApiData.getmIp() + "/" + url);
                    }
                    if (url3List.size() > 0) {
                        mprv_task.setVisibility(View.VISIBLE);
                    } else {
                        mprv_task.setVisibility(View.GONE);
                    }
                    for (String url : url3List) {
                        taskphotoList.add("http://" + ApiData.getmIp() + "/" + url);
                    }
                    for (String url : url4List) {
                        task1photoList.add("http://" + ApiData.getmIp() + "/" + url);
                    }
                    for (String url : url5List) {
                        task2photoList.add("http://" + ApiData.getmIp() + "/" + url);
                    }
                    for (String url : url6List) {
                        task3photoList.add("http://" + ApiData.getmIp() + "/" + url);
                    }
//                    photoList.addAll(urlList);
                    mprv_qymt.notifyDataSetChanged();
                    mprv_yyzz.notifyDataSetChanged();
                    mprv_task.notifyDataSetChanged();
                    mprv_task1.notifyDataSetChanged();
                    mprv_task2.notifyDataSetChanged();
                    mprv_task3.notifyDataSetChanged();
                }
                break;
            case ApiData.FILE_UPLOAD:
                uploadImgUrl += (String) baseHttpResult.getEntry();
                if (uploadIndex == 6) {
                    Util.closeProgressDialog();
                    sendInfo(uploadImgUrl);
                } else {
                    uploadIndex++;
                    compassFile();
                }
                break;
            case ApiData.HTTP_ID_superviseGetEntityPoint:
                List<EntityPointBean> list = (List<EntityPointBean>) baseHttpResult.getEntry();
                entityPointList.clear();
                entityPointList.addAll(list);
//                //获取定位信息
//                dolocation();
                break;
            case ApiData.HTTP_ID_superviseGetDTByCondition:
                NormalListEntity normalListEntity = (NormalListEntity) baseHttpResult.getEntry();
                if (normalListEntity.getTotal() > 0) {
                    tvEquipment.setVisibility(View.VISIBLE);
                } else {
                    tvEquipment.setVisibility(View.GONE);
                }
                break;
            case ApiData.HTTP_ID_superviceAppendEntityPoint:
                showToast("任务处置点添加成功");
                if (selectPoint != null) {
                    EntityPointBean entityPointBean = new EntityPointBean();
                    entityPointBean.setFEntityGuid(mEntityBean.getFEntityGuid());
                    entityPointBean.setFLatitude(selectPoint.getY() + "");
                    entityPointBean.setFLongitude(selectPoint.getX() + "");
                    entityPointBean.setFType("添加坐标");
                    entityPointList.add(entityPointBean);
                }
                break;
            case ApiData.HTTP_ID_getPicByEntityId:
                List<EntityPictureBean> pictureBeans = (List<EntityPictureBean>) baseHttpResult.getEntry();
                entityPictureList.clear();
                entityPictureList.addAll(pictureBeans);
                for (int i = 0; i < pictureBeans.size(); i++) {
                    if ("1".equals(pictureBeans.get(i).getFType())) {//企业门头
                        qymtphotoList.add("http://" + ApiData.getmIp() + pictureBeans.get(i).getFSaveName());
                    } else if ("2".equals(pictureBeans.get(i).getFType())) {//营业执照
                        yyzzphotoList.add("http://" + ApiData.getmIp() + pictureBeans.get(i).getFSaveName());
                    }
                }
                mprv_qymt.notifyDataSetChanged();
                mprv_yyzz.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private void dolocation() {
        if (entityPointList.size() > 0) {
            if (GpsTool.isOpen(this)) {
                location = GpsTool.getGpsLocation(this);
                double minLength = -1;
                for (int i = 0; i < entityPointList.size(); i++) {
                    if (entityPointList.get(i).getFLongitude() == null || entityPointList.get(i).getFLatitude() == null
                            || entityPointList.get(i).getFLongitude().length() == 0 || entityPointList.get(i).getFLatitude().length() == 0) {
                        continue;
                    }
                    Line line = new Line();
                    line.setStart(new Point(Double.parseDouble(entityPointList.get(i).getFLongitude()), Double.parseDouble(entityPointList.get(i).getFLatitude())));
                    line.setEnd(new Point(location.getLongitude(), location.getLatitude()));
                    Polyline polyline = new Polyline();
                    polyline.addSegment(line, true);
                    double length = Math.abs(GeometryEngine.geodesicLength(polyline, SpatialReference.create(4326), null));
                    if (minLength == -1) {
                        minLength = length;
                    } else {
                        minLength = minLength > length ? length : minLength;
                    }
                    //TODO
                    if (minLength < 300) {
                        checkExcuteBtn.setEnabled(true);
                        tvLocationInfo.setText("已处于主体所在定位区域");
                        break;
                    } else {
                        DecimalFormat df = new DecimalFormat("######0.00");
                        String lengthInfo = minLength - 300 < 1000 ? df.format(minLength - 300) + "米" : df.format((minLength - 300) / 1000) + "公里";
                        tvLocationInfo.setText("当前未处于主体所在定位区域内，距离最近主体定位区域还差：" + lengthInfo);
                        checkExcuteBtn.setEnabled(false);
                    }
                }
            } else {
                GpsTool.openGPS(this);
            }
        } else {
            tvLocationInfo.setText("当前主体没有坐标信息，请先添加");
            checkExcuteBtn.setEnabled(false);
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
            setUploadProgress(progress);
        } else {
            super.onLoadPregress(id, progress);
        }
    }

    //实时计算进度
    private void setUploadProgress(int progress) {
        int mProgress = 0;
        if (uploadIndex == 1) {
            mProgress = (qymtphotoList.size() * progress) / getTotalSize();
        } else if (uploadIndex == 2) {
            mProgress = (yyzzphotoList.size() * progress + qymtphotoList.size() * 100) / getTotalSize();
        } else if (uploadIndex == 3) {
//            mProgress = (taskphotoList.size() * progress + qymtphotoList.size() * 100 + yyzzphotoList.size() * 100) / getTotalSize();
        } else if (uploadIndex == 4) {
            mProgress = (task1photoList.size() * progress + qymtphotoList.size() * 100 + yyzzphotoList.size() * 100) / getTotalSize();
        } else if (uploadIndex == 5) {
            mProgress = (task2photoList.size() * progress + qymtphotoList.size() * 100 + yyzzphotoList.size() * 100 + task1photoList.size() * 100) / getTotalSize();
        } else if (uploadIndex == 6) {
            mProgress = (task3photoList.size() * progress + qymtphotoList.size() * 100 + yyzzphotoList.size() * 100 + task1photoList.size() * 100 + task2photoList.size() * 100) / getTotalSize();
        }
        if (progress != 0) {
            Util.showProgressDialog(this, mProgress, "正在上传图片中(图片需要压缩，请耐心等候)");
        }
    }

    private int getTotalSize() {
        int totalSize = qymtphotoList.size() + yyzzphotoList.size() + task1photoList.size() + task2photoList.size() + task3photoList.size();
        return totalSize;
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
                    if (qymtphotoList.size() == 0) {
                        showToast("请上传企业门头照片");
                        break;
                    }
                    if (yyzzphotoList.size() == 0) {
                        showToast("请上传营业执照照片");
                        break;
                    }
                    if (!(task1photoList.size() > 0 || task2photoList.size() > 0 || task3photoList.size() > 0)) {
                        showToast("请上传主体相关照片");
                        break;
                    }
                    uploadIndex = 1;
                    Util.showProgressDialog(this, 0, "正在上传图片中(图片需要压缩，请耐心等候)");
                    compassFile();
                } else {
                    showToast("未在主体区域内，无法提进行提交！");
                }
                break;
            case R.id.tv_location_refresh:
                Map<String, Object> map = new HashMap<>();
                map.put("locations", entityPointList);
                map.put("entity", mEntityBean);
                mapDialog.showMap(map);
                break;
            case R.id.tv_supervise_equipment:
                SuperviseEquimentActivity.startAction(this, mEntityBean.getFEntityGuid());
                break;
            default:
                break;
        }


    }

    private void compassFile() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                ArrayList<String> tempPhotoList = new ArrayList<>();
                ArrayList<String> uploadFilePath = new ArrayList<>();
                if (uploadIndex == 1) {
                    tempPhotoList = qymtphotoList;
                } else if (uploadIndex == 2) {
                    tempPhotoList = yyzzphotoList;
                } else if (uploadIndex == 3) {
//                    tempPhotoList = taskphotoList;
                } else if (uploadIndex == 4) {
                    tempPhotoList = task1photoList;
                } else if (uploadIndex == 5) {
                    tempPhotoList = task2photoList;
                } else if (uploadIndex == 6) {
                    tempPhotoList = task3photoList;
                }
                if (tempPhotoList.size() == 0 && uploadIndex < 6) {
                    uploadIndex++;
                    compassFile();
                    return;
                } else if (tempPhotoList.size() == 0 && uploadIndex == 6) {
                    Util.closeProgressDialog();
                    sendInfo(uploadImgUrl);
                    return;
                }

                for (int i = 0; i < tempPhotoList.size(); i++) {
                    if (tempPhotoList.get(i).startsWith("http")) {//此时为网络图片
                        continue;
                    }
                    File file = new File(tempPhotoList.get(i));
                    File uploadFile;
                    if (file.exists()) {
                        if (file.getPath().endsWith("png") || file.getPath().endsWith("jpg")) {
                            uploadFile = compassImgFile(file);
                            if (uploadFile != null && uploadFile.exists()) {
                                uploadFilePath.add(uploadFile.getPath());
                            } else {
                                uploadFilePath.add(file.getPath());
                            }
                        }
                    }
                }
                if (uploadFilePath != null && uploadFilePath.size() > 0) {
                    updateImg.loadData(0, ((String[]) uploadFilePath.toArray(new String[uploadFilePath.size()])), "task");
                } else {
                    uploadIndex++;
                    compassFile();
                }
//            }
//        }).start();
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

    private void sendInfo(String imgUrl) {
        ZXFileUtil.deleteFiles(ConstStrings.INI_PATH + "/" + ConstStrings.APPNAME + "/" + "file_cache/");
        if (mEntityBean != null) {
            String typeString = "";
            for (int i = 0; i < qymtphotoList.size(); i++) {
                if (!qymtphotoList.get(i).startsWith("http")) {
                    typeString += "1,";
                }
            }
            for (int i = 0; i < yyzzphotoList.size(); i++) {
                if (!yyzzphotoList.get(i).startsWith("http")) {
                    typeString += "2,";
                }
            }
//            for (int i = 0; i < taskphotoList.size(); i++) {
//                typeString += "3,";
//            }
            for (int i = 0; i < task1photoList.size(); i++) {
                typeString += "4,";
            }
            for (int i = 0; i < task2photoList.size(); i++) {
                typeString += "5,";
            }
            for (int i = 0; i < task3photoList.size(); i++) {
                typeString += "6,";
            }


            List<EntityPictureBean> tempList = new ArrayList<>();
            //处理entityPictureList中被删除的部分

            for (int i = 0; i < entityPictureList.size(); i++) {
                boolean breakOut = false;
                for (int j = 0; j < qymtphotoList.size(); j++) {
                    if (qymtphotoList.get(j).startsWith("http") && qymtphotoList.get(j).equals("http://" + ApiData.getmIp() + entityPictureList.get(i).getFSaveName())) {
                        tempList.add(entityPictureList.get(i));
                        breakOut = true;
                        break;
                    }
                }
                if (breakOut){
                    continue;
                }
                for (int j = 0; j < yyzzphotoList.size(); j++) {
                    if (yyzzphotoList.get(j).startsWith("http") && yyzzphotoList.get(j).equals("http://" + ApiData.getmIp() + entityPictureList.get(i).getFSaveName())) {
                        tempList.add(entityPictureList.get(i));
                        break;
                    }
                }
            }

            //将网络图片的参数进行拼装
            for (int i = 0; i < tempList.size(); i++) {
                imgUrl += tempList.get(i).getFRealName() + ",";
                if ("1".equals(tempList.get(i).getFType()) || "2".equals(tempList.get(i).getFType())) {
                    typeString += tempList.get(i).getFType() + ",";
                }
            }
            imgUrl = imgUrl.substring(0, imgUrl.length() - 1);
            typeString = typeString.substring(0, typeString.length() - 1);
            String taskitemjson = new Gson().toJson(dataList);
            disposalTask.loadData(userInfo.getId(),
                    mTaskBean.getUserId(), mEntityBean.getFGuid(),
                    remark, qualify, taskitemjson, imgUrl, mEntityBean.getFGuid(), typeString,
                    location == null ? "" : location.getLatitude(), location == null ? "" : location.getLongitude());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapDialog != null) {
            mapDialog.dismiss();
        }
        timer.cancel();
    }

    private Handler locationHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            dolocation();
            super.handleMessage(msg);
        }
    };
}
