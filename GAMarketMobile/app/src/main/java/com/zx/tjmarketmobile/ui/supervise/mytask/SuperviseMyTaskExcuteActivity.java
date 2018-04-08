package com.zx.tjmarketmobile.ui.supervise.mytask;

import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.supervise.MyTaskListEntity;
import com.zx.tjmarketmobile.entity.supervise.MyTaskProcessEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.ToastUtil;
import com.zx.tjmarketmobile.view.MultiSpinner;
import com.zx.tjmarketmobile.view.SimpleSpinnerOption;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzq on 2017/3/24.
 */

public class SuperviseMyTaskExcuteActivity extends BaseActivity {

    private MyTaskListEntity mEntity;
    private int taskState = 2;
    private List<MyTaskProcessEntity.DataBean> mProcessingPeopleList = new ArrayList<>();
    private List<MyTaskProcessEntity.DataBean> mSubDirectorList = new ArrayList<>();

    private enum State {

//        TOBEISSUED("待下发"),
//        TOBEAUDIT("待审核"),
//        TOBEASSIGNMETN("待指派"),
//        AUDITNOTPASSED("审核未通过"),
//        TOBEREVIEW("待核审"),
//        TOBEFEEDBACK("待反馈"),;

        //0待选主体1待选检查项2待下发3任务执行中4.完成
        TOBEISSUED(0),
        TOBEAUDIT(1),
        TOBEASSIGNMETN(2),
        AUDITNOTPASSED(3),
        TOBEREVIEW(4),;

        private int name;

        State(int name) {
            this.name = name;
        }

        public int getName() {
            return name;
        }

        public void setName(int name) {
            this.name = name;
        }

    }

    private ApiData getProcessingPeople = new ApiData(ApiData.HTTP_ID_Supervise_MyTask_getProcessingPeople);//获取当前用户的所在部门的下属
    private ApiData getSubDirector = new ApiData(ApiData.HTTP_ID_Supervise_MyTask_getSubDirector);//获取分局长
    private ApiData sendTask = new ApiData(ApiData.HTTP_ID_Supervise_MyTask_sendTask);//下发任务
    private ApiData examineTaskInfo = new ApiData(ApiData.HTTP_ID_Supervise_MyTask_examineTaskInfo);//审核任务
    private ApiData assignTaskInfo = new ApiData(ApiData.HTTP_ID_Supervise_MyTask_assignTaskInfo);//指派任务
    private ApiData reviewTask = new ApiData(ApiData.HTTP_ID_Supervise_MyTask_reviewTask);
    private ApiData feedbackTask = new ApiData(ApiData.HTTP_ID_Supervise_MyTask_feedbackTask);//反馈任务
    private Spinner spinner;
    private LinearLayout xbLayout;
    private MultiSpinner mutiSpinner, xbSpinner;
    private LinearLayout topLayout, opitionLayout, spinnerLayout;
    private EditText editText;//审核意见
    private RadioButton radioButton0;
    private RadioButton radioButton1;
    private Button buttonExe;
    private int mixCharNum = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervise_mytask_excute);
        initData();
    }

    //初始化
    private void initData() {
        addToolBar(true);
        hideRightImg();
        setMidText("任务处理");
        intiView();
        if (getIntent().hasExtra("entity")) {
            mEntity = (MyTaskListEntity) getIntent().getSerializableExtra("entity");
            taskState = mEntity.getStatus();
            radioButton0.setChecked(true);
            if (taskState == 0) {//待下发 进去进行下发操作
                radioButton0.setText("自办");
                radioButton1.setText("分局");
                topLayout.setVisibility(View.VISIBLE);
                opitionLayout.setVisibility(View.GONE);
                spinnerLayout.setVisibility(View.VISIBLE);
                sendTask.setLoadingListener(this);
                getProcessingPeople.setLoadingListener(this);
                getSubDirector.setLoadingListener(this);
                getProcessingPeople.loadData(userInfo.getId());
            } else if (taskState == 1) {//待审核  进去执行审核操作
                radioButton0.setText("未通过");
                radioButton1.setText("通过");
                topLayout.setVisibility(View.VISIBLE);
                opitionLayout.setVisibility(View.VISIBLE);
                spinnerLayout.setVisibility(View.GONE);
                examineTaskInfo.setLoadingListener(this);
            } else if (taskState == 2) {//待指派 执行指派操作
                topLayout.setVisibility(View.GONE);
                opitionLayout.setVisibility(View.GONE);
                spinnerLayout.setVisibility(View.VISIBLE);
                getProcessingPeople.setLoadingListener(this);
                getProcessingPeople.loadData(userInfo.getId());
            } else if (taskState == 3) {//待反馈
                topLayout.setVisibility(View.GONE);
                opitionLayout.setVisibility(View.VISIBLE);
                spinnerLayout.setVisibility(View.GONE);
                feedbackTask.setLoadingListener(this);
            } else if (taskState == 4) {//待核审
                radioButton0.setText("退回");
                radioButton1.setText("通过");
                topLayout.setVisibility(View.VISIBLE);
                opitionLayout.setVisibility(View.VISIBLE);
                spinnerLayout.setVisibility(View.GONE);
                reviewTask.setLoadingListener(this);
            }
        }

    }

    //待下发
    private void intiView() {
        radioButton0 = (RadioButton) findViewById(R.id.rb_supervise_task_publish0);
        radioButton0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (taskState == 2) {//待下发
                        getProcessingPeople.loadData(userInfo.getId());
                    }
                }
            }
        });
        radioButton1 = (RadioButton) findViewById(R.id.rb_supervise_task_publish1);
        radioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (taskState == 2) {//待下发
                        getSubDirector.loadData(userInfo.getId());
                    }
                }
            }
        });
        topLayout = (LinearLayout) findViewById(R.id.top_layout);
        opitionLayout = (LinearLayout) findViewById(R.id.et_supervise_task_excuteOpinion_layout);
        editText = (EditText) findViewById(R.id.et_supervise_task_excuteOpinion);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable editable = editText.getText();
                int len = editable.length();
                if (len > mixCharNum) {
                    ToastUtil.getShortToastByString(SuperviseMyTaskExcuteActivity.this, "超出字数限制,最多可输入150字！");
                    int selEndIndex = Selection.getSelectionEnd(editable);
                    String str = editable.toString();
                    //截取新字符串
                    String newStr = str.substring(0, mixCharNum);
                    editText.setText(newStr);
                    editable = editText.getText();
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
        spinnerLayout = (LinearLayout) findViewById(R.id.sp_task_issued_layout);
        spinner = (Spinner) findViewById(R.id.sp_task_issued);
        xbSpinner = (MultiSpinner) findViewById(R.id.sp_task_xb);
        xbLayout = (LinearLayout) findViewById(R.id.ll_task_issued_xb);
        spinner.setVisibility(View.VISIBLE);
        xbLayout.setVisibility(View.VISIBLE);
        mutiSpinner = (MultiSpinner) findViewById(R.id.msp_task_issued);
        mutiSpinner.setVisibility(View.GONE);
        buttonExe = (Button) findViewById(R.id.btn_supervise_check_excute);
        buttonExe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskState == 2) {//待下发
                    if (radioButton0.isChecked()) {//自办
                        String userId = getProcessingPeopleId(spinner.getSelectedItem().toString());
                        if (mProcessingPeopleList.size() > 0) {
                            List<SimpleSpinnerOption> options = xbSpinner.getCheckedOptions();
                            if (options != null && options.size() >= 0) {
                                StringBuffer nameBuffer = new StringBuffer();
                                StringBuffer idBuffer = new StringBuffer();
                                for (int i = 0; i < options.size(); i++) {
                                    String name = options.get(i).getName();
                                    for (int j = 0; j < mProcessingPeopleList.size(); j++) {
                                        String realName = mProcessingPeopleList.get(j).getFRealName();
                                        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(realName)) {
                                            if (name.equals(realName)) {
                                                nameBuffer.append(realName).append(",");
                                                idBuffer.append(mProcessingPeopleList.get(j).getFUserId()).append(",");
                                            }
                                        }
                                    }
                                }
                                String names = nameBuffer.toString();
                                if (names.contains(",")) {
                                    names = names.substring(0, names.lastIndexOf(","));
                                }
                                String ids = idBuffer.toString();
                                if (ids.contains(",")) {
                                    ids = ids.substring(0, ids.lastIndexOf(","));
                                }
                                sendTask.loadData(mEntity.getId(), "", userId, userInfo.getId(), ids, names);
                            }
                        }
                    } else if (radioButton1.isChecked()) {//分局
                        if (mutiSpinner.getCheckedOptions().size() > 0) {
                            String userId = getSubDirectorId(mutiSpinner.getCheckedOptions());
                            sendTask.loadData(mEntity.getId(), userId, "", userInfo.getId(), "", "");
                        } else {
                            showToast("请选择下发单位！");
                        }

                    }
                } else if (taskState == 1) {//待审核
                    if (radioButton0.isChecked()) {//未通过
                        examineTaskInfo.loadData(0, mEntity.getId(), editText.getText().toString(), userInfo.getId());
                    } else if (radioButton1.isChecked()) {//通过
                        examineTaskInfo.loadData(1, mEntity.getId(), editText.getText().toString(), userInfo.getId());
                    }
                } else if (taskState == 2) {//待指派
                    String userId = getProcessingPeopleId(spinner.getSelectedItem().toString());
                    assignTaskInfo.setLoadingListener(SuperviseMyTaskExcuteActivity.this);
                    if (mProcessingPeopleList.size() > 0) {
                        List<SimpleSpinnerOption> options = xbSpinner.getCheckedOptions();
                        if (options != null && options.size() >= 0) {
                            StringBuffer nameBuffer = new StringBuffer();
                            StringBuffer idBuffer = new StringBuffer();
                            for (int i = 0; i < options.size(); i++) {
                                String name = options.get(i).getName();
                                for (int j = 0; j < mProcessingPeopleList.size(); j++) {
                                    String realName = mProcessingPeopleList.get(j).getFRealName();
                                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(realName)) {
                                        if (name.equals(realName)) {
                                            nameBuffer.append(realName).append(",");
                                            idBuffer.append(mProcessingPeopleList.get(j).getFUserId()).append(",");
                                        }
                                    }
                                }
                            }
                            String names = nameBuffer.toString();
                            if (names.contains(",")) {
                                names = names.substring(0, names.lastIndexOf(","));
                            }
                            String ids = idBuffer.toString();
                            if (ids.contains(",")) {
                                ids = ids.substring(0, ids.lastIndexOf(","));
                            }
                            assignTaskInfo.loadData(userId, mEntity.getId(), userInfo.getId(), ids, names);
                        }
                    }

                } else if (taskState == 1) {//待核审
                    if (radioButton0.isChecked()) {//回退
                        reviewTask.loadData(mEntity.getUserId(), 0, editText.getText().toString(), userInfo.getId());
                    } else if (radioButton1.isChecked()) {//通过
                        reviewTask.loadData(mEntity.getUserId(), 1, editText.getText().toString(), userInfo.getId());
                    }
                } else if (taskState == 3) {//待反馈
                    feedbackTask.loadData(mEntity.getUserId(), mEntity.getId(), editText.getText().toString(), userInfo.getId());
                }
            }
        });
    }

    private String getSubDirectorId(List<SimpleSpinnerOption> deparentMentList) {
        StringBuffer buffer = new StringBuffer();
        String result = "";
        if (mSubDirectorList.size() > 0 && deparentMentList.size() > 0) {
            for (int i = 0; i < mSubDirectorList.size(); i++) {
                MyTaskProcessEntity.DataBean dataBean = mSubDirectorList.get(i);
                for (int j = 0; j < deparentMentList.size(); j++) {
                    if (dataBean.getFDepartment().equals(deparentMentList.get(j).getName())) {
                        String userId = dataBean.getFUserId();
                        buffer.append(userId).append(",");
                    }
                }
            }
        }
        result = buffer.toString();
        if (result.contains(",")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    private String getProcessingPeopleId(String realName) {
        if (mProcessingPeopleList.size() > 0) {
            for (int i = 0; i < mProcessingPeopleList.size(); i++) {
                MyTaskProcessEntity.DataBean dataBean = mProcessingPeopleList.get(i);
                if (dataBean.getFRealName().equals(realName)) {
                    return dataBean.getFUserId();
                }
            }
        }
        return "";
    }


    @Override
    @SuppressWarnings("unchecked")
    public void onLoadComplete(int id, BaseHttpResult baseHttpResult) {
        super.onLoadComplete(id, baseHttpResult);
        switch (id) {
            case ApiData.HTTP_ID_Supervise_MyTask_getProcessingPeople: {
                MyTaskProcessEntity myTaskProcessEntity = (MyTaskProcessEntity) baseHttpResult.getEntry();
                List<MyTaskProcessEntity.DataBean> list = myTaskProcessEntity.getData();
                if (list != null && list.size() > 0) {
                    mProcessingPeopleList.clear();
                    mProcessingPeopleList.addAll(list);
                    final List<String> stringList = new ArrayList<>();
                    final List<String> xbStringList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) != null && list.get(i).getFRealName() != null) {
                            stringList.add(list.get(i).getFRealName());
                        }
                    }
                    for (int i = 1; i < list.size(); i++) {
                        if (list.get(i) != null && list.get(i).getFRealName() != null) {
                            xbStringList.add(list.get(i).getFRealName());
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_layout, stringList);
                    spinner.setAdapter(adapter);

                    ArrayList multiXbSpinnerList = new ArrayList();
                    for (int i = 0; i < xbStringList.size(); i++) {
                        SimpleSpinnerOption option = new SimpleSpinnerOption();
                        option.setName(xbStringList.get(i));
                        option.setValue(i);
                        multiXbSpinnerList.add(option);
                    }
                    xbSpinner.setDataList(multiXbSpinnerList);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (stringList.size() > 0) {
                                xbSpinner.reMoveAll();
                                List<String> newList = new ArrayList<String>();
                                for (int i = 0; i < stringList.size(); i++) {
                                    if (i != position) {
                                        newList.add(stringList.get(i));
                                    }
                                }
                                ArrayList multiXbSpinnerList = new ArrayList();
                                for (int i = 0; i < newList.size(); i++) {
                                    SimpleSpinnerOption option = new SimpleSpinnerOption();
                                    option.setName(newList.get(i));
                                    option.setValue(i);
                                    multiXbSpinnerList.add(option);
                                }
                                xbSpinner.setDataList(multiXbSpinnerList);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    spinner.setVisibility(View.VISIBLE);
                    xbLayout.setVisibility(View.VISIBLE);
                    mutiSpinner.setVisibility(View.GONE);
                }
            }
            break;
            case ApiData.HTTP_ID_Supervise_MyTask_getSubDirector: {
                MyTaskProcessEntity myTaskProcessEntity = (MyTaskProcessEntity) baseHttpResult.getEntry();
                List<MyTaskProcessEntity.DataBean> list = myTaskProcessEntity.getData();
                if (list != null && list.size() > 0) {
                    mSubDirectorList.clear();
                    mSubDirectorList.addAll(list);
                    List<String> stringList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) != null && list.get(i).getFDepartment() != null) {
                            stringList.add(list.get(i).getFDepartment());
                        }
                    }
                    mutiSpinner.setTitle("分局选择");
                    ArrayList multiSpinnerList = new ArrayList();
                    for (int i = 0; i < stringList.size(); i++) {
                        SimpleSpinnerOption option = new SimpleSpinnerOption();
                        option.setName(stringList.get(i));
                        option.setValue(i);
                        multiSpinnerList.add(option);
                    }
                    mutiSpinner.setDataList(multiSpinnerList);
                    spinner.setVisibility(View.GONE);
                    xbLayout.setVisibility(View.GONE);
                    mutiSpinner.setVisibility(View.VISIBLE);
                }
            }
            break;
            case ApiData.HTTP_ID_Supervise_MyTask_sendTask:
                if (baseHttpResult.isSuccess()) {
                    showToast("下发成功！");
                    finish();
                    if (SuperviseMyTaskDetailActivity.instance != null) {
                        SuperviseMyTaskDetailActivity.instance.finish();
                    }
                }
                break;
            case ApiData.HTTP_ID_Supervise_MyTask_examineTaskInfo:
                showToast(baseHttpResult.getMessage());
                if (baseHttpResult.isSuccess()) {
                    editText.setText("");
                    finish();
                    if (SuperviseMyTaskDetailActivity.instance != null) {
                        SuperviseMyTaskDetailActivity.instance.finish();
                    }
                }
                break;
            case ApiData.HTTP_ID_Supervise_MyTask_assignTaskInfo:
                showToast(baseHttpResult.getMessage());
                if (baseHttpResult.isSuccess()) {
                    finish();
                    if (SuperviseMyTaskDetailActivity.instance != null) {
                        SuperviseMyTaskDetailActivity.instance.finish();
                    }
                }
                break;
            case ApiData.HTTP_ID_Supervise_MyTask_reviewTask:
                showToast(baseHttpResult.getMessage());
                if (baseHttpResult.isSuccess()) {
                    finish();
                    if (SuperviseMyTaskDetailActivity.instance != null) {
                        SuperviseMyTaskDetailActivity.instance.finish();
                    }
                }
                break;
            case ApiData.HTTP_ID_Supervise_MyTask_feedbackTask:
                showToast(baseHttpResult.getMessage());
                if (baseHttpResult.isSuccess()) {
                    finish();
                    if (SuperviseMyTaskDetailActivity.instance != null) {
                        SuperviseMyTaskDetailActivity.instance.finish();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadError(int id, boolean bool, String errorMsg) {
        super.onLoadError(id, bool, errorMsg);

        showToast(errorMsg);

    }


}
