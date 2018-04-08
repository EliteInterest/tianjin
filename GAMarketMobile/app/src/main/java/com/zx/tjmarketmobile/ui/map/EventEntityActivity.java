package com.zx.tjmarketmobile.ui.map;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.R.id;
import com.zx.tjmarketmobile.R.style;
import com.zx.tjmarketmobile.entity.EmergencyListInfo;
import com.zx.tjmarketmobile.entity.HttpTaskEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.Util;

/**
 * Create By Xiangb On 2016/9/22
 * 功能：事件界面
 */
@SuppressWarnings("deprecation")
public class EventEntityActivity extends BaseActivity implements OnClickListener {
    private Button mBtnExcute;//响应、签到、处置按钮
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ApiData mEventDetail = new ApiData(ApiData.HTTP_ID_searchevent_detail);
    private EmergencyListInfo mEvent;
    private int mCurStatus = 0;//用户当前处置状态 0：未响应 1：已响应 2：已签到 3：处置中
    private LocationManager mLocManager;
    private LocationListener mLocListener;
    private boolean mIsCheckin = true;//是否签到
    private ProgressDialog mProgDlg;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetail);

        addToolBar(true);
        setMidText("任务详情");
        getRightImg().setOnClickListener(this);


        mLocManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mEvent = (EmergencyListInfo) bundle.getSerializable("entity");
            }
        }
        mProgDlg = new ProgressDialog(this, style.dialogTransparent);
        mEventDetail.setLoadingListener(this);
        mEventDetail.loadData(mEvent.getfGuid(), "yj");

        mTabLayout = (TabLayout) findViewById(id.tb_normal_layout);
        mViewPager = (ViewPager) findViewById(id.vp_normal_pager);
        mBtnExcute = (Button) findViewById(id.btn_event_execute);
        mBtnExcute.setOnClickListener(this);

    }

    public void initViewPager(){
        myPagerAdapter.addFragment(EventEntityInfoFragment.newInstance(0, mEvent), "基本信息");
//        myPagerAdapter.addFragment(EventEntityPersonFragment.newInstance(0, mEvent), "应急人员");
//        myPagerAdapter.addFragment(EventEntityDepartFragment.newInstance(0, mEvent), "相关单位");
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(myPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setVisibility(View.GONE);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult baseHttpResult) {
        super.onLoadComplete(id, baseHttpResult);
        switch (id){
            case ApiData.HTTP_ID_searchevent_detail:
                mEvent = (EmergencyListInfo) baseHttpResult.getEntry();
                initViewPager();
                break;
        }
    }

    private HttpTaskEntity generateTaskEntity(EmergencyListInfo event) {
        HttpTaskEntity task = new HttpTaskEntity();
        task.setGuid(event.getfGuid());
        task.setAddress(event.getfAddress());
        task.setEntityName(event.getfEntityName());
        task.setEntityGuid(event.getfEntityGuid());
        task.setTaskName(event.getfCategory());
        task.setTaskTime(event.getfInsertDate());
        task.setTaskType(2);
        task.setWkid(4490);
        task.setTimeType(0);
        task.setLatitude(event.getfLatitude());
        task.setLongitude(event.getfLongitude());
        task.setTaskType(2);
        return task;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.toolbar_right:
                Intent intent = new Intent(this, WorkInMapShowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type", ConstStrings.MapType_TaskDetail);
                HttpTaskEntity task = generateTaskEntity(mEvent);
                bundle.putSerializable("entity", task);
                intent.putExtras(bundle);
                startActivity(intent);
                Util.activity_In(this);
                break;
            default:
                break;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (mLocManager != null && mLocListener != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                mLocManager.removeUpdates(mLocListener);
            }
        }
    }
}
