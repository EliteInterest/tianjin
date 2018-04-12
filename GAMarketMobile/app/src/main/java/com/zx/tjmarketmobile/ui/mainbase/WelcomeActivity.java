package com.zx.tjmarketmobile.ui.mainbase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.WindowManager;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.HttpLoginEntity;
import com.zx.tjmarketmobile.entity.HttpUpdateEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.NetWorkReceiver;
import com.zx.tjmarketmobile.util.SYSUtil;

/**
 * Create By Xiangb On 2016/9/13
 * 功能：欢迎界面
 */
public class WelcomeActivity extends BaseActivity {
    private SharedPreferences mSharePreferences;
    private ApiData mUpdateData = new ApiData(ApiData.HTTP_ID_version_update);
    private ApiData loginData = new ApiData(ApiData.HTTP_ID_login);
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        //判断是否联网
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            NetWorkReceiver.changeServiceStatus(this, true);
        }
        setContentView(R.layout.activity_welcome);
        addToolBar(false);
        userManager.init(getApplicationContext());

        mSharePreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = mSharePreferences.edit();
        edit.remove("update_version");
        edit.commit();
        mUpdateData.setLoadingListener(this);
        loginData.setLoadingListener(this);
        //检查是否为正式环境
        String ip = "";
        if (ApiData.ISRELEASE) {
//            ip = ConstStrings.ip1 + "." + ConstStrings.ip2 + "." + ConstStrings.ip3 + "." + ConstStrings.ip4 + ":" + ConstStrings.ipport;
            ip = ApiData.LOCAL_HOST;
        } else {
            ip = mSharePreferences.getString("ip1", ConstStrings.ip1) + "." + mSharePreferences.getString("ip2", ConstStrings.ip2) + "."
                    + mSharePreferences.getString("ip3", ConstStrings.ip3) + "." + mSharePreferences.getString("ip4", ConstStrings.ip4) + ":"
                    + mSharePreferences.getString("ipport", ConstStrings.ipport);
        }
        ApiData.setBaseUrl(ip);

        // pause the update thread to check new version
        mUpdateData.loadData(SYSUtil.getVersionCode(this));
//        gotoActivity();
    }

    @Override
    public void onLoadError(int id, boolean bool, String errorMsg) {
        super.onLoadError(id, bool, errorMsg);
        userManager.setNoLogin(this);
        toLogin();
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        if (b.isSuccess()) {
            switch (id) {
                case ApiData.HTTP_ID_version_update:
                    HttpUpdateEntity updateInfo = (HttpUpdateEntity) b.getEntry();
                    int versionCode = SYSUtil.getVersionCode(this);
                    String name = mSharePreferences.getString("curuser", "");
                    String psw = mSharePreferences.getString(name, "");
                    SharedPreferences.Editor edit = mSharePreferences.edit();
                    if (updateInfo != null && versionCode < updateInfo.versionCode) {
                        edit.putBoolean("update_version", true);
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        intent.putExtra("url", updateInfo.url);
                        intent.putExtra("version", updateInfo.versionName);
                        intent.putExtra("remark", updateInfo.remark);
                        intent.putExtra("content", updateInfo.content);
                        startActivity(intent);
                        finish();
                    } else {
                        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(psw)) {
                            loginData.loadData(name, psw);
                        } else {
                            userManager.setNoLogin(this);
                            gotoActivity();
                        }
                        edit.putBoolean("update_version", false);
                    }
                    edit.commit();
                    break;
                case ApiData.HTTP_ID_login:
                    HttpLoginEntity loginInfo = (HttpLoginEntity) b.getEntry();
                    loginInfo.setIsLogin(true);
                    String username = mSharePreferences.getString("curuser", "");
                    String password = mSharePreferences.getString(username, "");
                    loginInfo.setPassword(username);
                    loginInfo.setUserName(password);
                    userManager.setUser(WelcomeActivity.this, loginInfo);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toLogin();
                        }
                    }, 1000);
                    break;

                default:
                    break;
            }
        }
    }

    private void toLogin() {
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void gotoActivity() {
        new CountDownTimer(1300, 500) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                toLogin();
                finish();
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences.Editor edit = mSharePreferences.edit();
        long timeStr = mSharePreferences.getLong("setting_time", 0);
        long hoursStr = mSharePreferences.getLong("setting_hours", 8);
        if (timeStr == 0) {
            timeStr = System.currentTimeMillis();
            edit.putLong("setting_time", timeStr);
            edit.commit();
        }
        long selectTime = timeStr + hoursStr * 60 * 60 * 1000;
        if (selectTime < System.currentTimeMillis()) {
            userManager.getUser(this).setIsLogin(false);
            String curuser = mSharePreferences.getString("curuser", "");
            edit.putString("curuser", "");
            edit.putString(curuser, "");
            edit.commit();
        }


    }


}
