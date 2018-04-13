package com.zx.tjmarketmobile.ui.system;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.HttpUpdateEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.CQDigitalUtil;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.MapDownLoadUtil;
import com.zx.tjmarketmobile.util.TxtUtil;
import com.zx.tjmarketmobile.util.Util;

/**
 * Create By Xiangb On 2016/9/19
 * 功能：地图设置
 */
public class SettingsMapActivity extends BaseActivity implements OnClickListener {

    private ImageButton mImgBtnGoback;
    private RadioButton mRadioBtnPublish, mRadioBtnOffline, mRadioBtnCustomer;
    private EditText mEtIp1, mEtIp2, mEtIp3, mEtIp4;
    private Button mBtnOk;
    private EditText mEdtTopicMapIp0;
    private EditText mEdtTopicMapIp1;
    private EditText mEdtTopicMapIp2;
    private EditText mEdtTopicMapIp3;
    public TextView tvMapName;
    private int mMapType = 0;// 地图设置类型 0：公共服务平台地图 1：离线地图 2：自定义地图
    private ApiData mapUpdate = new ApiData(ApiData.HTTP_ID_map_update);//离线地图更新

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_map);

        addToolBar(true);
        hideRightImg();
        setMidText("设置-地图");

        mEtIp1 = (EditText) findViewById(R.id.et_settings_map_ip_1);
        mEtIp2 = (EditText) findViewById(R.id.et_settings_map_ip_2);
        mEtIp3 = (EditText) findViewById(R.id.et_settings_map_ip_3);
        mEtIp4 = (EditText) findViewById(R.id.et_settings_map_ip_4);
        mEdtTopicMapIp0 = (EditText) findViewById(R.id.edtActMapSetting_ip0);
        mEdtTopicMapIp1 = (EditText) findViewById(R.id.edtActMapSetting_ip1);
        mEdtTopicMapIp2 = (EditText) findViewById(R.id.edtActMapSetting_ip2);
        mEdtTopicMapIp3 = (EditText) findViewById(R.id.edtActMapSetting_ip3);
        tvMapName = (TextView) findViewById(R.id.tv_settingMap_versionName);
        findViewById(R.id.btn_settingMap_checkUpdate).setOnClickListener(this);
        mapUpdate.setLoadingListener(this);
        mEtIp1.setEnabled(false);
        mEtIp2.setEnabled(false);
        mEtIp3.setEnabled(false);
        mEtIp4.setEnabled(false);
        mEdtTopicMapIp0.setEnabled(false);
        mEdtTopicMapIp1.setEnabled(false);
        mEdtTopicMapIp2.setEnabled(false);
        mEdtTopicMapIp3.setEnabled(false);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String mapIp = sp.getString("topicIp", ConstStrings.MAP_TOPIC);
        mapIp = mapIp.replace("http://", "");
        String[] ips = mapIp.split(":")[0].split("\\.");
        mEdtTopicMapIp0.setText(ips[0]);
        mEdtTopicMapIp1.setText(ips[1]);
        mEdtTopicMapIp2.setText(ips[2]);
        mEdtTopicMapIp3.setText(ips[3]);
        mRadioBtnPublish = (RadioButton) findViewById(R.id.radiobtn_publish);
        mRadioBtnPublish.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean checked) {
                if (checked) {
                    mMapType = 0;
                    refreshMapSetting(mMapType);
                }
            }
        });
        mRadioBtnOffline = (RadioButton) findViewById(R.id.radiobtn_offline);
        mRadioBtnOffline.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean checked) {
                if (checked) {
                    mMapType = 1;
                    refreshMapSetting(mMapType);
                }
            }
        });
        mRadioBtnCustomer = (RadioButton) findViewById(R.id.radiobtn_customer);
        mRadioBtnCustomer.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean checked) {
                if (checked) {
                    mMapType = 2;
                    refreshMapSetting(mMapType);
                }
            }
        });
        mBtnOk = (Button) findViewById(R.id.btn_settings_map_ok);
        mBtnOk.setOnClickListener(this);

        mMapType = sp.getInt("maptype", 2);
        mEtIp1.setText(sp.getString("mapip1", ConstStrings.ip1));
        mEtIp2.setText(sp.getString("mapip2", ConstStrings.ip2));
        mEtIp3.setText(sp.getString("mapip3", ConstStrings.ip3));
        mEtIp4.setText(sp.getString("mapip4", ConstStrings.ip4));

        getMapVersion();
        refreshMapSetting(mMapType);

    }


    private void getMapVersion() {
        String versionName = TxtUtil.readTxt(CQDigitalUtil.getDataPath() + "/" + ConstStrings.VECTOR_DATA_NAME, "mapVersion");
        if (versionName == null) {
            tvMapName.setText("当前版本可更新");
        } else {
            tvMapName.setText("当前版本号:" + versionName);
        }
    }

    private void refreshMapSetting(int mapType) {
        switch (mapType) {
            case 0:
                mRadioBtnPublish.setChecked(true);
                mRadioBtnOffline.setChecked(false);
                mRadioBtnCustomer.setChecked(false);
                mEtIp1.setEnabled(false);
                mEtIp2.setEnabled(false);
                mEtIp3.setEnabled(false);
                mEtIp4.setEnabled(false);
                break;
            case 1:
                mRadioBtnPublish.setChecked(false);
                mRadioBtnOffline.setChecked(true);
                mRadioBtnCustomer.setChecked(false);
                mEtIp1.setEnabled(false);
                mEtIp2.setEnabled(false);
                mEtIp3.setEnabled(false);
                mEtIp4.setEnabled(false);
                break;
            case 2:
                mRadioBtnPublish.setChecked(false);
                mRadioBtnOffline.setChecked(false);
                mRadioBtnCustomer.setChecked(true);
//			mEtIp1.setEnabled(true);
//			mEtIp2.setEnabled(true);
//			mEtIp3.setEnabled(true);
//			mEtIp4.setEnabled(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_settings_map_ok: {
                Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
                String mapIp0 = mEdtTopicMapIp0.getText().toString();
                String mapIp1 = mEdtTopicMapIp1.getText().toString();
                String mapIp2 = mEdtTopicMapIp2.getText().toString();
                String mapIp3 = mEdtTopicMapIp3.getText().toString();
                if (!TextUtils.isEmpty(mapIp0) && !TextUtils.isEmpty(mapIp1) && !TextUtils.isEmpty(mapIp2)
                        && !TextUtils.isEmpty(mapIp3)) {
                    if (Util.isIpValid(this, mapIp0, mapIp1, mapIp2, mapIp3)) {
                        editor.putString("topicIp", "http://" + mapIp0 + "." + mapIp1 + "." + mapIp2 + "." + mapIp3
                                + ":" + ConstStrings.mapport + "/arcgis/rest/services/GASCJG/gascjg/MapServer");
                    } else {
                        editor.remove("topicIp");
                    }
                } else {
                    editor.remove("topicIp");
                }
                editor.commit();
                if (mMapType == 2) {
                    String ip1 = mEtIp1.getText().toString().trim();
                    String ip2 = mEtIp2.getText().toString().trim();
                    String ip3 = mEtIp3.getText().toString().trim();
                    String ip4 = mEtIp4.getText().toString().trim();
                    if (Util.isIpValid(this, ip1, ip2, ip3, ip4)) {
                        ConstStrings.setMapUrl("http://" + ip1 + "." + ip2 + "." + ip3 + "." + ip4 + ":" + ConstStrings.mapport, mMapType);
                        ConstStrings.setLocalImgUrl("http://" + ip1 + "." + ip2 + "." + ip3 + "." + ip4 + ":" + ConstStrings.mapport);
                        editor.putInt("maptype", mMapType);
                        editor.putString("mapip1", ip1);
                        editor.putString("mapip2", ip2);
                        editor.putString("mapip3", ip3);
                        editor.putString("mapip4", ip4);
                        editor.commit();

                        Util.showToast(this, "设置成功");
                        this.finish();
                        Util.activity_Out(this);
                    }
                } else {
                    editor.putInt("maptype", mMapType);
                    editor.commit();
                    Util.showToast(this, "设置成功");
                    this.finish();
                    Util.activity_Out(this);
                }
                break;
            }
            case R.id.btn_settingMap_checkUpdate:
                mapUpdate.loadData("矢量地图");
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_map_update:

                final HttpUpdateEntity updateInfo = (HttpUpdateEntity) b.getEntry();
                String versionName = TxtUtil.readTxt(CQDigitalUtil.getDataPath() + ConstStrings.VECTOR_DATA_NAME, "mapVersion");
                //判断是否需要下载
                try {
                    if (versionName != null && Integer.parseInt(versionName.substring(versionName.lastIndexOf(":") + 1)) >= updateInfo.versionCode) {
                        showToast("当前已是最新版本");
                        return;
                    }
                } catch (Exception e) {
                    showToast("获取地图版本号错误");
                    e.printStackTrace();
                    return;
                }
                Util.showYesOrNoDialog(this, "提示", "检测到地图更新，是否更新?", "确定", "取消", new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Util.dialog.dismiss();
                        MapDownLoadUtil mapDownloadUtil = new MapDownLoadUtil(SettingsMapActivity.this, updateInfo);
                        mapDownloadUtil.downloadMap();
                    }
                }, null);
                break;
            default:
                break;
        }
    }

}
