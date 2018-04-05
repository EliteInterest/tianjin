package com.zx.tjmarketmobile.ui.map;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.R.id;
import com.zx.tjmarketmobile.entity.EntityDetail;
import com.zx.tjmarketmobile.entity.HttpZtEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.Util;

/**
 * Create By Xiangb On 2016/9/22
 * 功能：主体详情
 */
public class EntityDetailActivity extends BaseActivity implements OnClickListener {


    private ApiData mChangeposData = new ApiData(ApiData.HTTP_ID_change_pos);
    private ApiData entityClaimed = new ApiData(ApiData.HTTP_ID_doClaimed);
    private ViewPager mVpContent;
    private LinearLayout llClaimedLayout;
    private Button btnClaimedLocation, btnDoClaimed;
    private TabLayout tb_entity;
    private EntityDetail mEntityDetail;
    private Location location = null;
    private LocationManager locationManager;
    private HttpZtEntity entity;
    private EntityImageFragment entityImageFragment;
    private String fEntityType, fEntityGuid;
    private int type = 0;//0纠正  1认领  2其他

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entitydetail);

        addToolBar(true);
        getRightImg().setOnClickListener(this);
        setMidText("主体详情");
        entityClaimed.setLoadingListener(this);
        mChangeposData.setLoadingListener(this);
        llClaimedLayout = (LinearLayout) findViewById(id.ll_claimedLayout);
        btnClaimedLocation = (Button) findViewById(id.btn_claimedLocation);
        btnDoClaimed = (Button) findViewById(R.id.btn_DoClaimed);
        tb_entity = (TabLayout) findViewById(id.tb_normal_layout);
        mVpContent = (ViewPager) findViewById(id.vp_normal_pager);
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        type = getIntent().getIntExtra("type", 1);
        entity = (HttpZtEntity) getIntent().getSerializableExtra("ztEntity");
        if (type == 0) {//纠正
            btnDoClaimed.setVisibility(View.GONE);
            btnClaimedLocation.setVisibility(View.VISIBLE);
            btnClaimedLocation.setOnClickListener(this);
        } else if (type == 1) {//认领
            btnClaimedLocation.setVisibility(View.GONE);
            btnDoClaimed.setVisibility(View.VISIBLE);
            btnDoClaimed.setOnClickListener(this);
        } else {
            llClaimedLayout.setVisibility(View.GONE);
        }
        mEntityDetail = (EntityDetail) getIntent().getSerializableExtra("entity");
        fEntityType = getIntent().getStringExtra("fEntityType");
        fEntityGuid = getIntent().getStringExtra("fEntityGuid");
        myPagerAdapter.addFragment(EntityFragment.newInstance(0, mEntityDetail.getBaseInfo()), "基本信息");
        myPagerAdapter.addFragment(BusinessFragment.newInstance(0, mEntityDetail.getBusiness()), "业务信息");
        myPagerAdapter.addFragment(GradeFragment.newInstance(1, mEntityDetail.getGrade()), "等级信息");
        mVpContent.setAdapter(myPagerAdapter);
        mVpContent.setOffscreenPageLimit(4);
        tb_entity.setupWithViewPager(mVpContent);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_change_pos:
                showToast("纠正成功");
                Util.dialog.dismiss();
                break;
            case ApiData.HTTP_ID_doClaimed:
                showToast("主体认领成功");
                Util.dialog.dismiss();
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.toolbar_right:
//                HttpZtEntity Ztentity = new HttpZtEntity();
//                Ztentity.setGuid(key.fEntityGuid);
//                Ztentity.setAddress(key.fAddress);
//                Ztentity.setEntityName(key.fEntityName);
//                Ztentity.setCreditLevel(key.fCreditLevel);
//                Ztentity.setLatitude(locationEntity.getfLatitude());
//                Ztentity.setLongitude(locationEntity.getfLongitude());
//                Ztentity.setContactInfo(key.fContactInfo);
//                Ztentity.setWkid(4490);
//                if (TextUtils.isEmpty(Ztentity.getLatitude()) ||
//                        TextUtils.isEmpty(Ztentity.getLongitude())) {
//                    Ztentity.setLongitude(entity.getLongitude());
//                    Ztentity.setLatitude(entity.getLatitude());
//                }
                Intent intent = new Intent(EntityDetailActivity.this, WorkInMapShowActivity.class);
                intent.putExtra("entity", entity);
                intent.putExtra("type", ConstStrings.MapType_ZtDetail);
                startActivity(intent);
                break;
            case id.btn_claimedLocation://位置纠正
//                if (!GpsTool.isOpen(this)) {
//                    GpsTool.openGPS(this);
//                } else {
//                    location = GpsTool.getGpsLocation(this);
//                    if (location != null) {
//                        Util.showYesOrNoDialog(this, "提示！", "是否将该主体的位置纠正为当前坐标：\n" + location.getLongitude() + "," + location.getLatitude(), "确认", "取消", new OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                mChangeposData.loadData(entity.getGuid(), userInfo.getId(), location.getLongitude(), location.getLatitude(), null);
//                            }
//                        }, null);
//                    } else {
//                        showToast("当前坐标定位失败，请重试");
//                    }
//                }
                break;
            case id.btn_DoClaimed://主体认领
//                Util.showYesOrNoDialog(this, "提示！", "是否认领该主体？", "确认", "取消", new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        entityClaimed.loadData(entity.getGuid(), userInfo.getId(), userInfo.getDepartment());
//                    }
//                }, null);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        entityImageFragment.mprvPhoto.onActivityResult(requestCode, resultCode, data);
    }


    //重写返回键监听返回事件，判断是否为查看详情状态
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

}
