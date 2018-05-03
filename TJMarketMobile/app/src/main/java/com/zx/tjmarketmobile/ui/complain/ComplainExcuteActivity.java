package com.zx.tjmarketmobile.ui.complain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.CompVideoAdapter;
import com.zx.tjmarketmobile.entity.ComplainInfoDetailsBean;
import com.zx.tjmarketmobile.entity.KeyValueInfo;
import com.zx.tjmarketmobile.entity.SelectPopDataList;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.ICommonListener;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.ui.camera.CameraActivity;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.ZXFileUtil;
import com.zx.tjmarketmobile.util.ZXItemClickSupport;
import com.zx.tjmarketmobile.view.SuperviseSelectPopuView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.widget.MultiPickResultView;

/**
 * Create By Xiangb On 2017/3/22
 * 功能：投诉举报处置
 */

public class ComplainExcuteActivity extends BaseActivity {
    private LinearLayout llShunt;//分流
    private LinearLayout llAssign;//指派
    private EditText etTjOpinion, etFeedBack1, etFeedBack2;
    private RelativeLayout rlShunt;//分流
    private TextView tvShunt;//分流
    private RelativeLayout rlAssign;//指派
    private TextView tvAssign;//指派
    private RelativeLayout rlVidelContent;
    private RecyclerView rvVideo;
    private LinearLayout llVideo;
    private MultiPickResultView mprvComplain;
    private Button btnExcute, btnCancel, bntCancel2;

    private SuperviseSelectPopuView mPopShunt;//分流
    private List<SelectPopDataList> selectListShunt = new ArrayList<>();//分流
    private SuperviseSelectPopuView mPopAssign;//指派
    private List<SelectPopDataList> selectListAssign = new ArrayList<>();//指派

    private ComplainInfoDetailsBean mEntity;
    private String fSlrId = "";//受理人id
    private String departId = "";//部门id

    private CompVideoAdapter videoAdapter;

    private List<KeyValueInfo> videoList = new ArrayList<>();
    private ArrayList<String> dataPath = new ArrayList<>();

    private ApiData getAllDept = new ApiData(ApiData.HTTP_ID_getAllUserDept);
    private ApiData getUserByDept = new ApiData(ApiData.HTTP_ID_getUsersByDept);
    private ApiData compHandle = new ApiData(ApiData.HTTP_ID_compFlowhandle);
    private ApiData updateFile = new ApiData(ApiData.FILE_UPLOAD);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_excute);

        initView();
    }

    private void initView() {
        addToolBar(true);
        mEntity = (ComplainInfoDetailsBean) getIntent().getSerializableExtra("entity");
        setMidText("任务处置");
        hideRightImg();

        llShunt = findViewById(R.id.ll_comp_shunt);
        llAssign = findViewById(R.id.ll_comp_assign);
        etTjOpinion = findViewById(R.id.et_comp_tjOpinion);
        etFeedBack1 = findViewById(R.id.et_comp_feedback1);
        etFeedBack2 = findViewById(R.id.et_comp_feedback2);
        rlShunt = findViewById(R.id.rl_comp_shunt);
        rlAssign = findViewById(R.id.rl_comp_assign);
        tvAssign = findViewById(R.id.tv_comp_assign);
        tvShunt = findViewById(R.id.tv_comp_shunt);
        btnExcute = findViewById(R.id.btn_comp_excute);
        btnCancel = findViewById(R.id.btn_comp_cancel);
        mprvComplain = findViewById(R.id.mprv_complain);
        bntCancel2 = findViewById(R.id.btn_comp_cancel2);
        rlVidelContent = findViewById(R.id.rl_videoContent);
        rvVideo = findViewById(R.id.rv_video);
        llVideo = findViewById(R.id.ll_vedio);

        btnCancel.setOnClickListener(this);
        rlAssign.setOnClickListener(this);
        rlShunt.setOnClickListener(this);
        btnExcute.setOnClickListener(this);
        getAllDept.setLoadingListener(this);
        getUserByDept.setLoadingListener(this);
        compHandle.setLoadingListener(this);
        updateFile.setLoadingListener(this);
        bntCancel2.setOnClickListener(this);
        llVideo.setOnClickListener(this);

        mprvComplain.setMaxPicNum(9).init(this, MultiPickResultView.ACTION_SELECT, dataPath);
        rvVideo.setLayoutManager(new LinearLayoutManager(this));
        videoAdapter = new CompVideoAdapter(videoList);
        rvVideo.setAdapter(videoAdapter);
        ZXItemClickSupport.addTo(rvVideo)
                .setOnItemClickListener(new ZXItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                        Intent intent = new Intent(ComplainExcuteActivity.this, CameraActivity.class);
                        intent.putExtra("type", "play");
                        intent.putExtra("path", videoList.get(position).key);
                        startActivity(intent);
                    }
                });

        //根据状态显示控件
        switch (mEntity.getBaseInfo().getFStatus()) {
            case 10://待受理
                btnExcute.setText("受理");
                if (!"09".equals(userManager.getUser(this).getDepartmentCode())
                        && mEntity.getBaseInfo().getfShuntRole() != null && mEntity.getBaseInfo().getfShuntRole().length() > 0) {
                    btnCancel.setText("退回");
                    btnCancel.setVisibility(View.VISIBLE);
                } else {
                    btnCancel.setVisibility(View.GONE);
                }
                bntCancel2.setVisibility(View.VISIBLE);
                mprvComplain.setVisibility(View.GONE);
                bntCancel2.setText("不受理");
                break;
            case 20://待分流
                getAllDept.loadData();
                llShunt.setVisibility(View.VISIBLE);
                etTjOpinion.setVisibility(View.VISIBLE);
                btnExcute.setText("分流");
                btnCancel.setText("退回");
                mprvComplain.setVisibility(View.GONE);
                break;
            case 30://待指派
                getUserByDept.loadData(userInfo.getDepartmentCode(), "1002");
                llAssign.setVisibility(View.VISIBLE);
                etTjOpinion.setVisibility(View.VISIBLE);
                mprvComplain.setVisibility(View.GONE);
                btnExcute.setText("指派");
                btnCancel.setText("退回");
                break;
            case 50://待处置
                etTjOpinion.setVisibility(View.VISIBLE);
                btnExcute.setText("提交");
                btnCancel.setText("退回");
                etFeedBack1.setVisibility(View.VISIBLE);
                etFeedBack2.setVisibility(View.VISIBLE);
                rlVidelContent.setVisibility(View.VISIBLE);
                etTjOpinion.setHint("请输入反馈内容...");
                break;
            case 60://待审核
                etTjOpinion.setVisibility(View.VISIBLE);
                btnExcute.setText("通过");
                btnCancel.setText("不通过");
                mprvComplain.setVisibility(View.GONE);
                if (!userManager.getUser(this).getRole().contains("1001")) {
                    showToast("当前用户无审核权限");
                    finish();
                    return;
                }
                break;
            case 80://待办结
            case 81:
                etTjOpinion.setVisibility(View.VISIBLE);
                etTjOpinion.setFocusable(false);
                etTjOpinion.setText("回访结果：" + mEntity.getBaseInfo().getfReviewResult());
                btnExcute.setText("办结");
                mprvComplain.setVisibility(View.GONE);
                btnCancel.setText("退回");
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 101) {
            String path = data.getStringExtra("path");
            if (path.length() > 0 && ZXFileUtil.isFileExists(path)) {
                videoList.add(new KeyValueInfo(path, ""));
                videoAdapter.notifyDataSetChanged();
            }
        } else {
            mprvComplain.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_comp_shunt://分流
                showSelectWindow(rlShunt, 0);
                break;
            case R.id.rl_comp_assign://指派
                showSelectWindow(rlAssign, 1);
                break;
            case R.id.btn_comp_excute:
                if (llShunt.getVisibility() == View.VISIBLE && tvShunt.getText().toString().contains("选择")) {
                    showToast("请选择分流对象");
                    return;
                } else if (llAssign.getVisibility() == View.VISIBLE && tvAssign.getText().toString().contains("选择")) {
                    showToast("请选择指派对象");
                    return;
                }
                doSendInfo("pass");
                break;
            case R.id.btn_comp_cancel:
                doSendInfo("return");
                break;
            case R.id.btn_comp_cancel2:
                doSendInfo("not");
                break;
            case R.id.ll_vedio:
                startActivityForResult(new Intent(this, CameraActivity.class), 1);
                break;
            default:
                break;
        }
    }

    //进行信息发送
    private void doSendInfo(String dispose) {
        switch (mEntity.getBaseInfo().getFStatus()) {
            case 10://待受理
                compHandle.loadData(mEntity.getBaseInfo().getFGuid(), dispose, "");
                break;
            case 20://待分流
                compHandle.loadData(mEntity.getBaseInfo().getFGuid(), dispose, etTjOpinion.getText().toString(),
                        "fShunt", departId);
                break;
            case 30://待指派
                compHandle.loadData(mEntity.getBaseInfo().getFGuid(), dispose, etTjOpinion.getText().toString(),
                        "fDisposeUser", fSlrId);
                break;
            case 50://待处置
                if (dataPath.size() > 0 || videoList.size() > 0) {
                    Map<String, String> map = new HashMap<>();
                    map.put("itemId", mEntity.getBaseInfo().getFGuid());
                    map.put("tableName", "tComplaintsInfo");
                    map.put("field", "fFileName");
                    List<String> filePaths = new ArrayList<>();
                    filePaths.addAll(dataPath);
                    for (KeyValueInfo video : videoList) {
                        filePaths.add(video.key);
                    }
                    updateFile.loadData(0, filePaths.toArray(new String[filePaths.size()]), "/TJComplaint/file/uploadFiles.do", map);
                } else {
                    compHandle.loadData(mEntity.getBaseInfo().getFGuid(), dispose, "",
                            "fReviewResult", etFeedBack2.getText().toString(),
                            "fReplyContent", etFeedBack1.getText().toString(),
                            "fFeedbackContent", etTjOpinion.getText().toString());
                }
                break;
            case 60://待审核
                compHandle.loadData(mEntity.getBaseInfo().getFGuid(), dispose, etTjOpinion.getText().toString());
                break;
            case 80://待办结
            case 81:
                compHandle.loadData(mEntity.getBaseInfo().getFGuid(), dispose, etTjOpinion.getText().toString());
                break;
            default:
                break;
        }
    }

    //打开选择框
    private boolean showSelectWindow(View view, int index) {
        closeSoftInput();
        switch (index) {
            case 0://分流
                if (mPopShunt == null) {
                    mPopShunt = new SuperviseSelectPopuView(this);
                    mPopShunt.setDataSelectListener(selectListener);
                }
                return mPopShunt.show(view, view.getWidth(), selectListShunt, index);
            case 1://指派
                if (mPopAssign == null) {
                    mPopAssign = new SuperviseSelectPopuView(this);
                    mPopAssign.setDataSelectListener(selectListener);
                }
                return mPopAssign.show(view, view.getWidth(), selectListAssign, index);
            default:
                return false;
        }
    }

    //下拉框的选择事件
    ICommonListener.IOnInfoSelectListener selectListener = new ICommonListener.IOnInfoSelectListener() {
        @Override
        public void onSelect(int pos, SelectPopDataList data, int index) {
            switch (index) {
                case 0://分流
                    tvShunt.setText(data.fTaskName);
                    departId = data.fTaskId;
                    break;
                case 1://指派
                    tvAssign.setText(data.fTaskName);
                    fSlrId = data.fTaskId;
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    @SuppressWarnings("unchecked")
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_getAllUserDept:
                List<SelectPopDataList> list = (List<SelectPopDataList>) b.getEntry();
                selectListShunt.clear();
                selectListShunt.addAll(list);
                break;
            case ApiData.HTTP_ID_getUsersByDept:
                List<KeyValueInfo> userList = (List<KeyValueInfo>) b.getEntry();
                selectListAssign.clear();
                for (int i = 0; i < userList.size(); i++) {
                    KeyValueInfo userInfo = userList.get(i);
                    selectListAssign.add(new SelectPopDataList(userInfo.key, userInfo.value));
                }
                break;
            case ApiData.HTTP_ID_compFlowhandle:
                showToast("处理成功");
                setResult(ConstStrings.Request_Success, null);
                finish();
                break;
            case ApiData.FILE_UPLOAD:
                showToast("文件上传成功");
                compHandle.loadData(mEntity.getBaseInfo().getFGuid(), "pass", "", "", "", etFeedBack2.getText().toString(), etFeedBack1.getText().toString(), etTjOpinion.getText().toString());
                break;
            default:
                break;
        }
    }
}
