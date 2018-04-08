package com.zx.tjmarketmobile.ui.supervise;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.R.id;
import com.zx.tjmarketmobile.adapter.supervise.SuperviseSearchAdapter;
import com.zx.tjmarketmobile.entity.EntityListInfo;
import com.zx.tjmarketmobile.entity.HttpMonitor;
import com.zx.tjmarketmobile.entity.SelectPopDataList;
import com.zx.tjmarketmobile.entity.SuperviseCountInfo;
import com.zx.tjmarketmobile.entity.TaskCountInfo;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.ICommonListener.IOnInfoSelectListener;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.view.SuperviseSelectPopuView;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2016/9/22
 * 功能：监管任务
 */
public class SuperviseSearchActivity extends BaseActivity implements OnClickListener {

    private SuperviseSearchAdapter mSuperviseSearchAdapter;
    private SuperviseSelectPopuView mSelectWindow;
    private RelativeLayout mRlName;
    private LinearLayout mLTaskLayout;
    private TextView mTvTaskName;
    private Button mBtnEnd;
    private ListView mLvDetail;
    private List<SelectPopDataList> mSuperviseList;
    private List<SuperviseCountInfo> mSuperviseCountList;
    private SuperviseCountInfo mCurrentSuperviseCountInfo;
    private LinearLayout mLlSearchByGroup, mLlSearchOnebyone;
    private ApiData taskListData = new ApiData(ApiData.HTTP_ID_supervisetasktype_list);
    private ApiData taskReviewListData = new ApiData(ApiData.HTTP_ID_supervisetasktype_reviewlist);
    private ApiData taskDetailData = new ApiData(ApiData.HTTP_ID_supervisetasktype_count);
    private ApiData taskMonitorData = new ApiData(ApiData.HTTP_ID_supervisetask_monitorlist);
    private ApiData taskUserQueryData = new ApiData(ApiData.HTTP_ID_supervisetask_queryuserentity);
    private ApiData taskAllQueryData = new ApiData(ApiData.HTTP_ID_supervisetask_queryallentity);
    private int mIsStatus = 0;// 0为任务审批，1为任务监控
    private TaskCountInfo mTaskInfo;
    private String mTaskId;
    //执法人员查看相关
    private int mTableFlag = 0;
    private TabLayout mTabLayout;
    private ViewPager mVpContent;
    private TextView mTvStation, mTvtotal, mTvCount1, mTvCount2, mTvCount3, mTvCount4;

    private static int mPageNo = 1;
    private static int mPageSize = 15;
    private boolean mIsFirst = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchsupervise);

        addToolBar(true);
        hideRightImg();

        mLTaskLayout = (LinearLayout) findViewById(id.rlActSearchSupervise_task_Layout);
        mRlName = (RelativeLayout) findViewById(id.rlActSearchSupervise_taskname);
        mTvTaskName = (TextView) findViewById(id.ivActSearchSupervise_taskname);
        mTvStation = (TextView) findViewById(id.tvActSearchSupervise_station);
        mTvtotal = (TextView) findViewById(id.tvActSearchSupervise_total);
        mTvCount1 = (TextView) findViewById(id.tvActSearchSupervise_count1);
        mTvCount2 = (TextView) findViewById(id.tvActSearchSupervise_count2);
        mTvCount3 = (TextView) findViewById(id.tvActSearchSupervise_count3);
        mTvCount4 = (TextView) findViewById(id.tvActSearchSupervise_count4);
        mBtnEnd = (Button) findViewById(id.btnActSearchSupervise_opt);
        mTabLayout = (TabLayout) findViewById(id.tb_superviseTab);
        mVpContent = (ViewPager) findViewById(id.vp_ActSearchTask_content);
        mLlSearchByGroup = (LinearLayout) findViewById(id.ll_searchbygroup);
        mLlSearchOnebyone = (LinearLayout) findViewById(id.ll_searchonebyone);
        mLvDetail = (ListView) findViewById(id.lvActSearchSupervise_detail);
        mBtnEnd.setVisibility(View.GONE);

        mTableFlag = getIntent().getIntExtra("type", 0);
        mTaskInfo = (TaskCountInfo) getIntent().getSerializableExtra("task");
        if ("任务监控".equals(mTaskInfo.status)) {
            mIsStatus = 1;
        }
        mLTaskLayout.setVisibility(View.VISIBLE);
        setMidText(mTaskInfo.status);
        mSuperviseCountList = new ArrayList<>();
        mSuperviseSearchAdapter = new SuperviseSearchAdapter(this, mIsStatus, mTaskInfo.status, mSuperviseCountList);
        mLvDetail.setAdapter(mSuperviseSearchAdapter);
        mLvDetail.setOnItemClickListener(itemListener);
        mLlSearchByGroup.setVisibility(View.GONE);
        mLlSearchOnebyone.setVisibility(View.VISIBLE);

        taskListData.setLoadingListener(this);
        taskReviewListData.setLoadingListener(this);
        taskDetailData.setLoadingListener(this);
        taskMonitorData.setLoadingListener(this);
        taskUserQueryData.setLoadingListener(this);
        taskAllQueryData.setLoadingListener(this);
        mRlName.setOnClickListener(this);
        mBtnEnd.setOnClickListener(this);
        findViewById(id.ll_FmSearchTask_address).setVisibility(View.GONE);
        findViewById(id.ll_FmSearchTask_status).setVisibility(View.GONE);
        findViewById(id.ll_FmSearchTask_select).setVisibility(View.GONE);

        if ("待核审".equals(mTaskInfo.status) || "待终审".equals(mTaskInfo.status)) {
            taskReviewListData.loadData(mTaskInfo.status, userInfo.getId());
        } else {
            taskListData.loadData();
        }
        initialViewPager();
        TextView tvStatus = (TextView) findViewById(id.tv_FmSearchTask_status);
        if (null != mTaskInfo) {
            tvStatus.setText(mTaskInfo.status);
            setMidText(mTaskInfo.status);
            mTabLayout.getTabAt(0).setText("全部(" + mTaskInfo.allCount + ")");
            mTabLayout.getTabAt(1).setText("即将到期(" + mTaskInfo.soonCount + ")");
            mTabLayout.getTabAt(2).setText("逾期(" + mTaskInfo.expireCount + ")");
        }
    }

    private void initialViewPager() {
        myPagerAdapter.addFragment(SuperviseListFragment.newInstance(0, mTaskInfo.status), "全部");
        myPagerAdapter.addFragment(SuperviseListFragment.newInstance(1,mTaskInfo.status), "即将到期");
        if (mTaskInfo.status.equals("状态监控") || mTaskInfo.status.equals("任务监控")) {
            String dept = userInfo.getDepartment();
            if (!dept.equals("委领导")) {
                myPagerAdapter.addFragment(SuperviseListFragment.newInstance(2, mTaskInfo.status), "逾期");
            }
        } else {
            myPagerAdapter.addFragment(SuperviseListFragment.newInstance(2,mTaskInfo.status), "逾期");
        }
        mVpContent.setOffscreenPageLimit(3);
        mVpContent.setAdapter(myPagerAdapter);
        mTabLayout.setupWithViewPager(mVpContent);
        mVpContent.setCurrentItem(mTableFlag);
    }

    public void setText(int flag, int num) {
        switch (flag) {
            case 0:
                mTabLayout.getTabAt(0).setText("全部(" + num + ")");
                break;
            case 1:
                mTabLayout.getTabAt(1).setText("即将到期(" + num + ")");
                break;
            case 2:
                if (mTabLayout.getTabCount() > 2) {
                    mTabLayout.getTabAt(2).setText("逾期(" + num + ")");
                }
                break;
            default:
                break;
        }
    }

    OnItemClickListener itemListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mCurrentSuperviseCountInfo = mSuperviseCountList.get(position);
            boolean isSelect = mCurrentSuperviseCountInfo.isSelected();
            for (SuperviseCountInfo item : mSuperviseCountList) {
                item.setSelected(false);
            }
            mCurrentSuperviseCountInfo.setSelected(!isSelect);
            mSuperviseSearchAdapter.notifyDataSetChanged();
        }
    };

    private boolean showSelectWindow(View view) {
        closeSoftInput();
        if (mSelectWindow == null) {
            mSelectWindow = new SuperviseSelectPopuView(this);
            mSelectWindow.setDataSelectListener(selectListener);
        }
        return mSelectWindow.show(view, mRlName.getWidth(), mSuperviseList, 0);
    }

    IOnInfoSelectListener selectListener = new IOnInfoSelectListener() {
        @Override
        public void onSelect(int pos, SelectPopDataList data, int index) {
            mTvTaskName.setText(data.fTaskName);
            if (pos == 0) {
                mLlSearchByGroup.setVisibility(View.GONE);
                mLlSearchOnebyone.setVisibility(View.VISIBLE);
                mTabLayout.setVisibility(View.VISIBLE);
                mTaskId = data.fTaskId;
            } else {
                mIsFirst = true;
                mLlSearchByGroup.setVisibility(View.VISIBLE);
                mLlSearchOnebyone.setVisibility(View.GONE);
                mTabLayout.setVisibility(View.GONE);
                if ("任务监控".equals(mTaskInfo.status)) {
                    mIsStatus = 1;
                }
                mTaskId = data.fTaskId;
                if ("任务监控".equals(mTaskInfo.status)) {
                    String type = "";
                    switch (mTabLayout.getSelectedTabPosition()) {
                        case 0:
                            type = "全部";
                            break;
                        case 1:
                            type = "即将逾期";
                            break;
                        case 2:
                            type = "逾期";
                            break;
                    }
                    taskMonitorData.loadData(userInfo.getId(), mTaskId, mPageNo, mPageSize, type);
                } else {
                    taskDetailData.loadData(mTaskId, mTaskInfo.status,userInfo.getId());
                }
            }
            mSuperviseSearchAdapter.setTaskId(mTaskId);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
//        if (mTaskId != null && !mTaskId.isEmpty() && mLlSearchByGroup.getVisibility() == View.VISIBLE) {
//            if ("任务监控".equals(mTaskInfo.status)) {
//                String type = "";
//                switch (mTabLayout.getSelectedTabPosition()) {
//                    case 0:
//                        type = "全部";
//                        break;
//                    case 1:
//                        type = "即将逾期";
//                        break;
//                    case 2:
//                        type = "逾期";
//                        break;
//                }
//                taskMonitorData.loadData(userInfo.getId(), mTaskId, mPageNo, mPageSize, type);
//            } else {
//                taskDetailData.loadData(mTaskId, mTaskInfo.status,userInfo.getId());
//            }
//        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        if (b.isSuccess()) {
            switch (id) {
                case ApiData.HTTP_ID_supervisetasktype_list:
                    mSuperviseList = (List<SelectPopDataList>) b.getEntry();
                    SelectPopDataList sTaskInfo = new SelectPopDataList();
                    sTaskInfo.fTaskId = "";
                    sTaskInfo.fTaskName = "查看所有任务";
                    sTaskInfo.fTaskStatus = "";
                    mSuperviseList.add(0, sTaskInfo);
                    break;
                case ApiData.HTTP_ID_supervisetasktype_reviewlist:
                    mSuperviseList = (List<SelectPopDataList>) b.getEntry();
                    SelectPopDataList sReviewTaskInfo = new SelectPopDataList();
                    sReviewTaskInfo.fTaskId = "";
                    sReviewTaskInfo.fTaskName = "查看所有任务";
                    sReviewTaskInfo.fTaskStatus = "";
                    mSuperviseList.add(0, sReviewTaskInfo);
                    break;
                case ApiData.HTTP_ID_supervisetasktype_count:
                    List<SuperviseCountInfo> superviseCountkList = (List<SuperviseCountInfo>) b.getEntry();
                    if (superviseCountkList.size() > 0) {
                        SuperviseCountInfo info = superviseCountkList.get(0);
                        if ("待处置".equals(mTaskInfo.status)) {
                            mTvStation.setText("小组名称");
                            mTvCount1.setText("待处置");
                            mTvCount2.setText("已处置");
                            mTvCount3.setVisibility(View.GONE);

                        } else if ("待初审".equals(mTaskInfo.status)) {
                            mTvStation.setText("小组名称");
                            mTvCount1.setText("办理中");
                            mTvCount2.setText("待初审");
                            mTvCount3.setText("已初审");

                        } else if ("待核审".equals(mTaskInfo.status)) {
                            mTvStation.setText("小组名称");
                            mTvCount1.setText("办理中");
                            mTvCount2.setText("待核审");
                            mTvCount3.setText("已核审");

                        } else if ("待终审".equals(mTaskInfo.status)) {
                            mTvStation.setText("小组名称");
                            mTvCount1.setText("办理中");
                            mTvCount2.setText("待终审");
                            mTvCount3.setText("已终审");
                        }
                    }
                    mSuperviseCountList.clear();
                    if (null != superviseCountkList && !superviseCountkList.isEmpty()) {
                        mSuperviseCountList.addAll(superviseCountkList);
                        if (mIsStatus == 0) {
                            mBtnEnd.setVisibility(View.VISIBLE);
                        }
                    } else {
                        mBtnEnd.setVisibility(View.GONE);
                        showToast("没有相应的监管任务");
                    }
                    mSuperviseSearchAdapter.notifyDataSetChanged();
                    break;

                case ApiData.HTTP_ID_supervisetask_monitorlist:
                    HttpMonitor monitor = (HttpMonitor) b.getEntry();
                    List<SuperviseCountInfo> taskList = monitor.getMonitorTaskList().getTaskList();
                    setText(0, monitor.getTotal());
                    setText(1, monitor.getTheOverdue());
                    setText(2, monitor.getOverdue());
                    if (taskList.size() > 0) {
                        mIsStatus = 1;
                        mTvStation.setText("小组名称");
                        mTvtotal.setText("待处置");
                        mTvCount1.setText("待初审");
                        mTvCount2.setText("待核审");
                        mTvCount3.setText("待终审");
                        mTvCount4.setText("已办结");
                        mTvCount4.setVisibility(View.VISIBLE);
                    }
                    mSuperviseCountList.clear();
                    if (null != taskList && !taskList.isEmpty()) {
                        mSuperviseCountList.addAll(taskList);
                        if (mIsStatus == 0) {
                            mBtnEnd.setVisibility(View.VISIBLE);
                        }
                    } else {
                        mBtnEnd.setVisibility(View.GONE);
                        showToast("没有相应的监管任务");
                    }
                    mSuperviseSearchAdapter.notifyDataSetChanged();
                    break;

                case ApiData.HTTP_ID_supervisetask_queryuserentity:
                    EntityListInfo entityUserList = (EntityListInfo) b.getEntry();
                    if (entityUserList.getTotal() > 0) {
                        String searchType = "";
                        if ("待处置".equals(mTaskInfo.status)) {
                            searchType = "dcz";
                        } else if ("待初审".equals(mTaskInfo.status)) {
                            searchType = "dcs";
                        } else if ("待核审".equals(mTaskInfo.status)) {
                            searchType = "dhs";
                        } else if ("待终审".equals(mTaskInfo.status)) {
                            searchType = "dzs";
                        }
                        Intent intent = new Intent(this, EntityListActivity.class);
                        intent.putExtra("superviseInfo", mCurrentSuperviseCountInfo);
                        intent.putExtra("EntityListInfo", entityUserList);
                        intent.putExtra("QueryEntityType", 2);
                        intent.putExtra("status", mTaskInfo.status);
                        intent.putExtra("taskId", mTaskId);
                        intent.putExtra("searchType", searchType);
                        startActivity(intent);
                    } else {
                        showToast("无" + mTaskInfo.status + "任务");
                    }
                    break;
                case ApiData.HTTP_ID_supervisetask_queryallentity:
                    EntityListInfo entityList = (EntityListInfo) b.getEntry();
                    if (entityList.getTotal() > 0) {
                        String searchType = "";
                        if ("待处置".equals(mTaskInfo.status)) {
                            searchType = "dcz";
                        } else if ("待初审".equals(mTaskInfo.status)) {
                            searchType = "dcs";
                        } else if ("待核审".equals(mTaskInfo.status)) {
                            searchType = "dhs";
                        } else if ("待终审".equals(mTaskInfo.status)) {
                            searchType = "dzs";
                        }
                        Intent intent = new Intent(this, EntityListActivity.class);
                        intent.putExtra("superviseInfo", mCurrentSuperviseCountInfo);
                        intent.putExtra("EntityListInfo", entityList);
                        intent.putExtra("QueryEntityType", 1);
                        intent.putExtra("status", mTaskInfo.status);
                        intent.putExtra("taskId", mTaskId);
                        intent.putExtra("searchType", searchType);
                        startActivity(intent);
                    } else {
                        showToast("无" + mTaskInfo.status + "任务");
                    }
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
            case id.rlActSearchSupervise_taskname:
                showSelectWindow(mRlName);
                break;
            case id.btnActSearchSupervise_opt:
                if ("待处置".equals(mTaskInfo.status)) {
                    taskUserQueryData.loadData(userInfo.getId(), mTaskInfo.status, mTaskId, mPageNo, mPageSize);
                } else if ("待初审".equals(mTaskInfo.status)) {
                    taskAllQueryData.loadData(userInfo.getId(), mTaskInfo.status, mTaskId, mPageNo, mPageSize);
                } else if ("待核审".equals(mTaskInfo.status)) {
                    taskAllQueryData.loadData(userInfo.getId(), mTaskInfo.status, mTaskId, mPageNo, mPageSize);
                } else if ("待终审".equals(mTaskInfo.status)) {
                    taskAllQueryData.loadData(userInfo.getId(), mTaskInfo.status, mTaskId, mPageNo, mPageSize);
                }
                break;
            default:
                break;
        }
    }

}
