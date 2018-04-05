package com.zx.tjmarketmobile.ui.emergency;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.ui.map.WorkInMapShowActivity;
import com.zx.tjmarketmobile.entity.EmergencyListInfo;
import com.zx.tjmarketmobile.entity.HttpTaskEntity;
import com.zx.tjmarketmobile.entity.PermitEmergencyDetialEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.Util;

/**
 * Created by zhouzq on 2016/12/5.
 */
public class PermitEmergencyDetialActivity extends BaseActivity implements View.OnClickListener{

    private ApiData mEmergencyDetail = new ApiData(ApiData.HTTP_ID_getPermit_Emergency_detail);
    private EmergencyListInfo mEvent;
    private String type;
    private LocationManager mLocManager;
    private String mTaskProcedure;
    private LocationListener mLocListener;
    private TextView permitLicNumTextView, permitEntityNameTextView, permitLegalPersonTextView,
            permitBizScopeTextView, permitRegOrgTextView, permitRegDateTextView, permitExpireDateTextView,
            permitTeleponeTextView,permitAddressTextView, permitTypeTextView,permitQcManagerTextView,
            permitDepotAddrTextView,permitContactInfoTextView,permitBizTypeTextView,
            permitLicTypeTextView,permitAddrTextView,permitApprovalProjectTextView,
            permitApprovalScopeTextView,permitApprovalDateTextView,permitRegNumTextView,
            permitMarkTextView,permitOrgNameTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permit_emergency_detial);

        addToolBar(true);
        setMidText("预警详情");
        getRightImg().setOnClickListener(this);


        mLocManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mEvent = (EmergencyListInfo) bundle.getSerializable("entity");
                mTaskProcedure = (String) bundle.getSerializable("mTaskProcedure");
                type = (String) bundle.getSerializable("type");
                if (!TextUtils.isEmpty(mTaskProcedure))
                    setMidText(mTaskProcedure+"详情");
            }
        }
        mEmergencyDetail.setLoadingListener(this);
        mEmergencyDetail.loadData(type,mEvent.getfGuid());

        permitLicNumTextView = (TextView) findViewById(R.id.tv_fLicNum);
        permitEntityNameTextView = (TextView) findViewById(R.id.tv_fEntityName);
        permitLegalPersonTextView = (TextView) findViewById(R.id.tv_fLegalPerson);
        permitAddressTextView = (TextView) findViewById(R.id.tv_fAddress);
        permitTypeTextView = (TextView) findViewById(R.id.tv_fType);
        permitBizScopeTextView = (TextView) findViewById(R.id.tv_fBizScope);
        permitRegOrgTextView = (TextView) findViewById(R.id.tv_fRegOrg);
        permitRegDateTextView = (TextView) findViewById(R.id.tv_fRegDate);
        permitExpireDateTextView = (TextView) findViewById(R.id.tv_fExpireDate);
        permitTeleponeTextView = (TextView) findViewById(R.id.tv_fTelepone);

        permitQcManagerTextView = (TextView) findViewById(R.id.tv_fQcManager);
        permitDepotAddrTextView = (TextView) findViewById(R.id.tv_fDepotAddr);
        permitContactInfoTextView = (TextView) findViewById(R.id.tv_fContactInfo);
        permitBizTypeTextView = (TextView) findViewById(R.id.tv_fBizType);

        permitLicTypeTextView = (TextView) findViewById(R.id.tv_fLicType);
        permitAddrTextView = (TextView) findViewById(R.id.tv_fAddr);
        permitApprovalProjectTextView = (TextView) findViewById(R.id.tv_fApprovalProject);
        permitApprovalScopeTextView = (TextView) findViewById(R.id.tv_fApprovalScope);
        permitApprovalDateTextView = (TextView) findViewById(R.id.tv_fApprovalDate);
        permitRegNumTextView = (TextView) findViewById(R.id.tv_fRegNum);
        permitMarkTextView = (TextView) findViewById(R.id.tv_fMark);
        permitOrgNameTextView = (TextView) findViewById(R.id.tv_fOrgName);



    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        if (b.isSuccess()) {
            switch (id) {
                case ApiData.HTTP_ID_getPermit_Emergency_detail:
                    PermitEmergencyDetialEntity permitEmergencyDetialEntity = (PermitEmergencyDetialEntity) b.getEntry();
                    setPermitBaseInfo(permitEmergencyDetialEntity);
                    break;
                default:
                    break;
            }
        }
    }


    private void setPermitBaseInfo(PermitEmergencyDetialEntity permitEmergencyDetialEntity) {
        if (permitEmergencyDetialEntity!=null) {
            if (permitEmergencyDetialEntity.getfLicNum()!=null&&!"".equals(permitEmergencyDetialEntity.getfLicNum())) {
                permitLicNumTextView.setText(permitEmergencyDetialEntity.getfLicNum());
                LinearLayout layout = (LinearLayout) permitLicNumTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (permitEmergencyDetialEntity.getfEntityName()!=null&&!"".equals(permitEmergencyDetialEntity.getfEntityName())) {
                permitEntityNameTextView.setText(permitEmergencyDetialEntity.getfEntityName());
                LinearLayout layout = (LinearLayout) permitEntityNameTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (permitEmergencyDetialEntity.getfLegalPerson()!=null&&!"".equals(permitEmergencyDetialEntity.getfLegalPerson())) {
                permitLegalPersonTextView.setText(permitEmergencyDetialEntity.getfLegalPerson());
                LinearLayout layout = (LinearLayout) permitLegalPersonTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (permitEmergencyDetialEntity.getfAddress()!=null&&!"".equals(permitEmergencyDetialEntity.getfAddress())) {
                permitAddressTextView.setText(permitEmergencyDetialEntity.getfAddress());
                LinearLayout layout = (LinearLayout) permitAddressTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(permitEmergencyDetialEntity.getfType())&&!"".equals(permitEmergencyDetialEntity.getfType())) {
                permitTypeTextView.setText(permitEmergencyDetialEntity.getfType().toString());
                LinearLayout layout = (LinearLayout) permitTypeTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (permitEmergencyDetialEntity.getfRegOrg()!=null&&!"".equals(permitEmergencyDetialEntity.getfRegOrg())) {
                permitRegOrgTextView.setText(permitEmergencyDetialEntity.getfRegOrg());
                LinearLayout layout = (LinearLayout) permitRegOrgTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (permitEmergencyDetialEntity.getfRegDate()!=null&&!"".equals(permitEmergencyDetialEntity.getfRegDate())) {
                permitRegDateTextView.setText(permitEmergencyDetialEntity.getfRegDate());
                LinearLayout layout = (LinearLayout) permitRegDateTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (permitEmergencyDetialEntity.getfBizScope()!=null&&!"".equals(permitEmergencyDetialEntity.getfBizScope())) {
                permitBizScopeTextView.setText(permitEmergencyDetialEntity.getfBizScope());
                LinearLayout layout = (LinearLayout) permitBizScopeTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (permitEmergencyDetialEntity.getfExpireDate()!= null&&!"".equals(permitEmergencyDetialEntity.getfExpireDate())) {
                permitExpireDateTextView.setText(permitEmergencyDetialEntity.getfExpireDate());
                LinearLayout layout = (LinearLayout) permitExpireDateTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (permitEmergencyDetialEntity.getfTelepone()!=null&&!"".equals(permitEmergencyDetialEntity.getfTelepone())) {
                permitTeleponeTextView.setText(permitEmergencyDetialEntity.getfTelepone());
                LinearLayout layout = (LinearLayout) permitTeleponeTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }

            if (permitEmergencyDetialEntity.getfQcManager()!=null&&!"".equals(permitEmergencyDetialEntity.getfQcManager())) {
                permitQcManagerTextView.setText(permitEmergencyDetialEntity.getfQcManager());
                LinearLayout layout = (LinearLayout) permitQcManagerTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (permitEmergencyDetialEntity.getfDepotAddr()!=null&&!"".equals(permitEmergencyDetialEntity.getfDepotAddr())) {
                permitDepotAddrTextView.setText(permitEmergencyDetialEntity.getfDepotAddr());
                LinearLayout layout = (LinearLayout) permitDepotAddrTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (permitEmergencyDetialEntity.getfContactInfo()!= null&&!"".equals(permitEmergencyDetialEntity.getfContactInfo())) {
                permitContactInfoTextView.setText(permitEmergencyDetialEntity.getfContactInfo());
                LinearLayout layout = (LinearLayout) permitContactInfoTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (permitEmergencyDetialEntity.getfBizType()!=null&&!"".equals(permitEmergencyDetialEntity.getfBizType())) {
                permitBizTypeTextView.setText(permitEmergencyDetialEntity.getfBizType());
                LinearLayout layout = (LinearLayout) permitBizTypeTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }


            if (permitEmergencyDetialEntity.getfLicType()!=null&&!"".equals(permitEmergencyDetialEntity.getfLicType())) {
                permitLicTypeTextView.setText(permitEmergencyDetialEntity.getfLicType());
                LinearLayout layout = (LinearLayout) permitLicTypeTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (permitEmergencyDetialEntity.getfAddr()!=null&&!"".equals(permitEmergencyDetialEntity.getfAddr())) {
                permitAddrTextView.setText(permitEmergencyDetialEntity.getfAddr());
                LinearLayout layout = (LinearLayout) permitAddrTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (permitEmergencyDetialEntity.getfApprovalProject()!= null&&!"".equals(permitEmergencyDetialEntity.getfApprovalProject())) {
                permitApprovalProjectTextView.setText(permitEmergencyDetialEntity.getfApprovalProject());
                LinearLayout layout = (LinearLayout) permitApprovalProjectTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (permitEmergencyDetialEntity.getfApprovalScope()!=null&&!"".equals(permitEmergencyDetialEntity.getfApprovalScope())) {
                permitApprovalScopeTextView.setText(permitEmergencyDetialEntity.getfApprovalScope());
                LinearLayout layout = (LinearLayout) permitApprovalScopeTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (permitEmergencyDetialEntity.getfApprovalDate()!=null&&!"".equals(permitEmergencyDetialEntity.getfApprovalDate())) {
                permitApprovalDateTextView.setText(permitEmergencyDetialEntity.getfApprovalDate());
                LinearLayout layout = (LinearLayout) permitApprovalDateTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (permitEmergencyDetialEntity.getfRegNum()!=null&&!"".equals(permitEmergencyDetialEntity.getfRegNum())) {
                permitRegNumTextView.setText(permitEmergencyDetialEntity.getfRegNum());
                LinearLayout layout = (LinearLayout) permitRegNumTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (permitEmergencyDetialEntity.getfMark()!= null&&!"".equals(permitEmergencyDetialEntity.getfMark())) {
                permitMarkTextView.setText(permitEmergencyDetialEntity.getfMark());
                LinearLayout layout = (LinearLayout) permitMarkTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (permitEmergencyDetialEntity.getfOrgName()!=null&&!"".equals(permitEmergencyDetialEntity.getfOrgName())) {
                permitOrgNameTextView.setText(permitEmergencyDetialEntity.getfOrgName());
                LinearLayout layout = (LinearLayout) permitOrgNameTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }

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
            case R.id.toolbar_right:
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
