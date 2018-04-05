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
import com.zx.tjmarketmobile.entity.DeviceEmergencyDetialEntity;
import com.zx.tjmarketmobile.entity.EmergencyListInfo;
import com.zx.tjmarketmobile.entity.HttpTaskEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.Util;

/**
 * Created by zhouzq on 2016/11/30.
 */
public class DeviceEmergencyDetialActivity extends BaseActivity implements View.OnClickListener {

    private ApiData mEmergencyDetail = new ApiData(ApiData.HTTP_ID_getEmergency_detail);
    private EmergencyListInfo mEvent;
    private LocationManager mLocManager;
    private String mTaskProcedure;
    private LocationListener mLocListener;
    private TextView deviceRegisterNumTextView,deviceFactoryCodeTextView,deviceFactoryTimeTextView,
            deviceTestPersonTextView,deviceTestDateTextView,deviceUseStateTextView,deviceAddressTextView,
            deviceModelTextView,deviceMaintenanceUnitTextView, deviceTestPersonTelTextView,deviceNextTestDateTextView,
            deviceManufacturerTextView,deviceOperatorCertificateNameTextView,deviceOperatorCertificateIdTextView,
            deviceOperatorCertificateProjectTextView,deviceOperatorCertificateValidityDateTextView,
            deviceMaintenanceContractValidityDateTextView,deviceSafeNextTestDateTextView,deviceLargeClassTextView,deviceSmallClassTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_emergency_detial);

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
                if (!TextUtils.isEmpty(mTaskProcedure))
                setMidText(mTaskProcedure+"详情");
            }
        }
        mEmergencyDetail.setLoadingListener(this);
        mEmergencyDetail.loadData(mEvent.getfGuid());

        deviceRegisterNumTextView = (TextView) findViewById(R.id.tvd_deviceRegisterNum);
        deviceFactoryCodeTextView = (TextView) findViewById(R.id.tv_deviceFactoryCode);
        deviceFactoryTimeTextView = (TextView) findViewById(R.id.tv_deviceFactoryTime);
        deviceLargeClassTextView = (TextView) findViewById(R.id.tv_deviceLargeClass);
        deviceSmallClassTextView = (TextView) findViewById(R.id.tv_deviceSmallClass);
        deviceTestPersonTextView = (TextView) findViewById(R.id.tv_deviceTestPerson);
        deviceTestDateTextView = (TextView) findViewById(R.id.tv_deviceTestDate);
        deviceUseStateTextView = (TextView) findViewById(R.id.tv_deviceUseState);
        deviceAddressTextView = (TextView) findViewById(R.id.tv_deviceAddress);
        deviceModelTextView = (TextView) findViewById(R.id.tv_device_model);
        deviceMaintenanceUnitTextView = (TextView) findViewById(R.id.tv_device_maintenance_unit);
        deviceTestPersonTelTextView = (TextView) findViewById(R.id.tv_device_manager_tel);
        deviceNextTestDateTextView = (TextView) findViewById(R.id.tv_device_next_check_date);
        deviceManufacturerTextView = (TextView) findViewById(R.id.tv_device_manufacturer);
        deviceOperatorCertificateNameTextView = (TextView) findViewById(R.id.tv_device_operator_certificate_name);
        deviceOperatorCertificateIdTextView = (TextView) findViewById(R.id.tv_device_operator_certificate_id);
        deviceOperatorCertificateProjectTextView = (TextView) findViewById(R.id.tv_device_operator_certificate_project);
        deviceOperatorCertificateValidityDateTextView = (TextView) findViewById(R.id.tv_device_operator_certificate_validity_date);
        deviceMaintenanceContractValidityDateTextView = (TextView) findViewById(R.id.tv_device_maintenance_contract_validity_date);
        deviceSafeNextTestDateTextView = (TextView) findViewById(R.id.tv_device_safe_next_check_date);

    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        if (b.isSuccess()) {
            switch (id) {
                case ApiData.HTTP_ID_getEmergency_detail:
                    DeviceEmergencyDetialEntity deviceEmergencyDetialEntity = (DeviceEmergencyDetialEntity) b.getEntry();
                    setDeviceBaseInfo(deviceEmergencyDetialEntity);
                    break;
                default:
                    break;
            }
        }
    }


    private void setDeviceBaseInfo(DeviceEmergencyDetialEntity deviceEmergencyDetialEntity) {
        if (deviceEmergencyDetialEntity!=null) {
            if (deviceEmergencyDetialEntity.getfRegCode()!=null&&!"".equals(deviceEmergencyDetialEntity.getfRegCode())) {
                deviceRegisterNumTextView.setText(deviceEmergencyDetialEntity.getfRegCode());
                LinearLayout layout = (LinearLayout)deviceRegisterNumTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (deviceEmergencyDetialEntity.getfOriginNum()!=null&&!"".equals(deviceEmergencyDetialEntity.getfOriginNum())) {
                deviceFactoryCodeTextView.setText(deviceEmergencyDetialEntity.getfOriginNum());
                LinearLayout layout = (LinearLayout)deviceFactoryCodeTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (deviceEmergencyDetialEntity.getfOriginDate()!=null&&!"".equals(deviceEmergencyDetialEntity.getfOriginDate())) {
                deviceFactoryTimeTextView.setText(deviceEmergencyDetialEntity.getfOriginDate());
                LinearLayout layout = (LinearLayout)deviceFactoryTimeTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (deviceEmergencyDetialEntity.getfCategory()!=null&&!"".equals(deviceEmergencyDetialEntity.getfCategory())) {
                deviceLargeClassTextView.setText(deviceEmergencyDetialEntity.getfCategory());
                LinearLayout layout = (LinearLayout)deviceLargeClassTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(deviceEmergencyDetialEntity.getfType())&&!"".equals(deviceEmergencyDetialEntity.getfType())) {
                deviceSmallClassTextView.setText(deviceEmergencyDetialEntity.getfType().toString());
                LinearLayout layout = (LinearLayout)deviceSmallClassTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (deviceEmergencyDetialEntity.getfCheckDate()!=null&&!"".equals(deviceEmergencyDetialEntity.getfCheckDate())) {
                deviceTestDateTextView.setText(deviceEmergencyDetialEntity.getfCheckDate());
                LinearLayout layout = (LinearLayout)deviceTestDateTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (deviceEmergencyDetialEntity.getfUserStatus()!=null&&!"".equals(deviceEmergencyDetialEntity.getfUserStatus())) {
                deviceUseStateTextView.setText(deviceEmergencyDetialEntity.getfUserStatus());
                LinearLayout layout = (LinearLayout)deviceUseStateTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (deviceEmergencyDetialEntity.getfManagerPerson()!=null&&!"".equals(deviceEmergencyDetialEntity.getfManagerPerson())) {
                deviceTestPersonTextView.setText(deviceEmergencyDetialEntity.getfManagerPerson());
                LinearLayout layout = (LinearLayout)deviceTestPersonTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (deviceEmergencyDetialEntity.getfEquipmentAddress()!= null&&!"".equals(deviceEmergencyDetialEntity.getfEquipmentAddress())) {
                deviceAddressTextView.setText(deviceEmergencyDetialEntity.getfEquipmentAddress());
                LinearLayout layout = (LinearLayout)deviceAddressTextView.getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (deviceEmergencyDetialEntity.getfDeviceModel()!=null&&!"".equals(deviceEmergencyDetialEntity.getfDeviceModel())) {
                deviceModelTextView.setText(deviceEmergencyDetialEntity.getfDeviceModel());
                LinearLayout layout = (LinearLayout)deviceModelTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (deviceEmergencyDetialEntity.getfMaintenanceName()!= null&&!"".equals(deviceEmergencyDetialEntity.getfMaintenanceName())) {
                deviceMaintenanceUnitTextView.setText(deviceEmergencyDetialEntity.getfMaintenanceName());
                LinearLayout layout = (LinearLayout)deviceMaintenanceUnitTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (deviceEmergencyDetialEntity.getfManagerTel()!=null&&!"".equals(deviceEmergencyDetialEntity.getfManagerTel())) {
                deviceTestPersonTelTextView.setText(deviceEmergencyDetialEntity.getfManagerTel());
                LinearLayout layout = (LinearLayout)deviceTestPersonTelTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (deviceEmergencyDetialEntity.getfNextCheckDate()!=null&&!"".equals(deviceEmergencyDetialEntity.getfNextCheckDate())) {
                deviceNextTestDateTextView.setText(deviceEmergencyDetialEntity.getfNextCheckDate());
                LinearLayout layout = (LinearLayout)deviceNextTestDateTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (deviceEmergencyDetialEntity.getfMakeUnit()!=null&&!"".equals(deviceEmergencyDetialEntity.getfMakeUnit())) {
                deviceManufacturerTextView.setText(deviceEmergencyDetialEntity.getfMakeUnit());
                LinearLayout layout = (LinearLayout)deviceManufacturerTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (deviceEmergencyDetialEntity.getfEquitmentCardName()!=null&&!"".equals(deviceEmergencyDetialEntity.getfEquitmentCardName())) {
                deviceOperatorCertificateNameTextView.setText(deviceEmergencyDetialEntity.getfEquitmentCardName());
                LinearLayout layout = (LinearLayout)deviceOperatorCertificateNameTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (deviceEmergencyDetialEntity.getfEquitmentCardId()!= null&&!"".equals(deviceEmergencyDetialEntity.getfEquitmentCardId())) {
                deviceOperatorCertificateIdTextView.setText(deviceEmergencyDetialEntity.getfEquitmentCardId());
                LinearLayout layout = (LinearLayout)deviceOperatorCertificateIdTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (deviceEmergencyDetialEntity.getfItem()!=null&&!"".equals(deviceEmergencyDetialEntity.getfItem())) {
                deviceOperatorCertificateProjectTextView.setText(deviceEmergencyDetialEntity.getfItem());
                LinearLayout layout = (LinearLayout)deviceOperatorCertificateProjectTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (deviceEmergencyDetialEntity.getfItemValidityDate()!=null&&!"".equals(deviceEmergencyDetialEntity.getfItemValidityDate())) {
                deviceOperatorCertificateValidityDateTextView.setText(deviceEmergencyDetialEntity.getfItemValidityDate());
                LinearLayout layout = (LinearLayout)deviceOperatorCertificateValidityDateTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (deviceEmergencyDetialEntity.getfMaintenanceDate()!= null&&!"".equals(deviceEmergencyDetialEntity.getfMaintenanceDate())) {
                deviceMaintenanceContractValidityDateTextView.setText(deviceEmergencyDetialEntity.getfMaintenanceDate());
                LinearLayout layout = (LinearLayout)deviceMaintenanceContractValidityDateTextView.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
            }
            if (deviceEmergencyDetialEntity.getfSafeNextCheckDate()!=null&&!"".equals(deviceEmergencyDetialEntity.getfSafeNextCheckDate())) {
                deviceSafeNextTestDateTextView.setText(deviceEmergencyDetialEntity.getfSafeNextCheckDate());
                LinearLayout layout = (LinearLayout)deviceSafeNextTestDateTextView.getParent().getParent();
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
