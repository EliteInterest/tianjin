package com.zx.tjmarketmobile.ui.mainbase;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.HttpLoginEntity;
import com.zx.tjmarketmobile.helper.PermissionsChecker;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.ui.system.SettingsIPActivity;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.MyApplication;
import com.zx.tjmarketmobile.util.SYSUtil;
import com.zx.tjmarketmobile.util.Util;

import java.io.File;

/**
 * Create By Xiangb On 2016/9/19
 * 功能：登录界面
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

    private EditText mEditTextName, mEditTextPwd;
    private Button mBtnLogin;
    private TextView mTvIpsetting;
    private SwitchCompat mSwRemenberPsw;
    private SharedPreferences mSharedPreferences;
    private boolean updateVersion = false;
    private ApiData loginData = new ApiData(ApiData.HTTP_ID_login);
    private PermissionsChecker mPermissionsChecker;
    private static final int REQUEST_CODE = 0; // 请求码
    private static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO};
    private ApiData downLoadFile = new ApiData(ApiData.FILE_DOWNLOAD);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addToolBar(false);
        //Android 6.0之后需要手动请求读写权限
        mPermissionsChecker = new PermissionsChecker(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditTextName = (EditText) findViewById(R.id.activity_user_login_name);
        mEditTextPwd = (EditText) findViewById(R.id.activity_user_login_pwd);
        mSwRemenberPsw = (SwitchCompat) findViewById(R.id.switch_remenber_psw);
        updateVersion = mSharedPreferences.getBoolean("update_version", false);
        String curuser = mSharedPreferences.getString("curuser", "");
        if (!curuser.equals("")) {
            mEditTextName.setText(curuser);
            String psw = mSharedPreferences.getString(curuser, "");
            mEditTextPwd.setText(psw);
            if (psw.equals("")) {
                mSwRemenberPsw.setChecked(false);
            } else {
                mSwRemenberPsw.setChecked(true);
            }
        }
        mEditTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = mEditTextName.getText().toString().trim();
                String psw = mSharedPreferences.getString(name, "");
                mEditTextPwd.setText(psw);
                if (psw.equals("")) {
                    mSwRemenberPsw.setChecked(false);
                } else {
                    mSwRemenberPsw.setChecked(true);
                }
            }
        });
        mBtnLogin = (Button) findViewById(R.id.activity_user_login_button);
        mBtnLogin.setOnClickListener(this);
        mTvIpsetting = (TextView) findViewById(R.id.tv_ip_setting);
        mTvIpsetting.setOnClickListener(this);
        if (ApiData.ISRELEASE) {
            mTvIpsetting.setVisibility(View.GONE);
        } else {
            mTvIpsetting.setVisibility(View.VISIBLE);
        }
        mTvIpsetting.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        loginData.setLoadingListener(this);
        downLoadFile.setLoadingListener(this);
        if (updateVersion) {
            showUpdateDialog();
        } else if (userManager.isLogin()) {
            gotoMainActivity();
        }
    }

    private void downloadApk(String url) {
        //检查是否为正式环境
//        String ip = "";
//        if (ApiData.ISRELEASE) {
//            ip = "http://" + ConstStrings.ip1 + "." + ConstStrings.ip2 + "." + ConstStrings.ip3 + "." + ConstStrings.ip4 + ":" + ConstStrings.ipport;
//        } else {
//            ip = "http://" + mSharedPreferences.getString("ip1", ConstStrings.ip1) + "."
//                    + mSharedPreferences.getString("ip2", ConstStrings.ip2) + "."
//                    + mSharedPreferences.getString("ip3", ConstStrings.ip3) + "."
//                    + mSharedPreferences.getString("ip4", ConstStrings.ip4) + ":"
//                    + mSharedPreferences.getString("ipport", ConstStrings.ipport);
//        }
        downLoadFile.loadData(ApiData.baseUrl + url, "APK/天津移动监督" + getIntent().getStringExtra("version") + ".apk", true);

    }

    private void showUpdateDialog() {
        String url = getIntent().getStringExtra("url");
        String fRemark = getIntent().getStringExtra("remark");
        String fContent = getIntent().getStringExtra("content");
        final Dialog dialog = new Dialog(this, R.style.fullDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_update, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        TextView tvRemark = dialog.findViewById(R.id.tvDialogUpdate_remark);
        TextView tvContent = dialog.findViewById(R.id.tvDialogUpdate_content);
        Button btnConfirm = dialog.findViewById(R.id.btnDialogUpdate_confirm);
        Button btnCancel = dialog.findViewById(R.id.btnDialogUpdate_cancel);
//        if (!TextUtils.isEmpty(fRemark) && !"null".equals(fRemark)) {
        tvRemark.setText(fRemark);
        tvContent.setText(fContent);
//        }
        btnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadApk(url);
                dialog.cancel();
            }
        });
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        } else {
            SYSUtil.initWorkSpaceDir(LoginActivity.this);
        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        } else {
            SYSUtil.initWorkSpaceDir(LoginActivity.this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_user_login_button:
                if (updateVersion) {
                    showUpdateDialog();
                    return;
                }
                login();
                break;
            case R.id.tv_ip_setting:
                Intent intent = new Intent();
                intent.setClass(this, SettingsIPActivity.class);
                startActivity(intent);
                Util.activity_Out(this);
                break;
            default:
                break;
        }
    }

    private void login() {
        String name = mEditTextName.getText().toString().trim();
        String psw = mEditTextPwd.getText().toString().trim();
        if (name.equals("")) {
            showToast("用户名不能为空");
            return;
        } else if (psw.equals("")) {
            showToast("密码不能为空");
            return;
        }
        Editor edit = mSharedPreferences.edit();
        if (mSwRemenberPsw.isChecked()) {
            edit.putString(name, psw);
        } else {
            edit.putString(name, "");
        }
        edit.putString("curuser", name);
        edit.commit();
        loginData.loadData(name, psw);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_login:
                showToast("登陆成功");
                if (b.isSuccess()) {
                    if (!mSwRemenberPsw.isChecked()) {
                        mEditTextPwd.setText("");
                    }
                    HttpLoginEntity loginInfo = (HttpLoginEntity) b.getEntry();
                    loginInfo.setIsLogin(true);
                    loginInfo.setPassword(mEditTextPwd.getText().toString().trim());
                    loginInfo.setUserName(mEditTextName.getText().toString().trim());
                    userManager.setUser(LoginActivity.this, loginInfo);
                    Editor edit = mSharedPreferences.edit();
                    edit.putLong("setting_time", System.currentTimeMillis());
                    edit.putString("update_version", "");
//                    BaseActivity.sIsLoginClear = false;

                    gotoMainActivity();
//                    if (mSharedPreferences.getBoolean("hasShowHelp", false)) {
//                        gotoMainActivity();
//                        edit.commit();
//                    } else {
//                        edit.putBoolean("hasShowHelp", true);
//                        edit.commit();
//                        Intent intent = new Intent();
//                        intent.setClass(LoginActivity.this, HelpActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
                    edit.commit();
                }
                break;
            case ApiData.FILE_DOWNLOAD:
                Util.closeProgressDialog();
                String path = ConstStrings.getDownloadPath() + "APK/天津移动监督" + getIntent().getStringExtra("version") + ".apk";
                File apkFile = new File(path);
                Intent intent = new Intent(Intent.ACTION_VIEW);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
//                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
//                } else {
                intent.setDataAndType(Uri.parse("file://" + apkFile.getPath()), "application/vnd.android.package-archive");
//                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (null != apkFile && apkFile.exists()) {
                    startActivity(intent);
                } else {
                    showToast("下载出错，请重试");
                }
                break;
            default:
                break;
        }
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(LoginActivity.this, GuideActivity.class);
        startActivity(intent);
        Util.activity_In(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().getActivityList().remove(this);
    }
}
