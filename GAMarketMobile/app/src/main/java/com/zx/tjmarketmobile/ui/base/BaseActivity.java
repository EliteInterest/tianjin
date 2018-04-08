package com.zx.tjmarketmobile.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.MyPagerAdapter;
import com.zx.tjmarketmobile.entity.HttpLoginEntity;
import com.zx.tjmarketmobile.helper.ToolBarHelper;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.http.BaseRequestData;
import com.zx.tjmarketmobile.manager.UserManager;
import com.zx.tjmarketmobile.ui.mainbase.GuideActivity;
import com.zx.tjmarketmobile.ui.mainbase.HomeActivity;
import com.zx.tjmarketmobile.ui.mainbase.LoginActivity;
import com.zx.tjmarketmobile.ui.mainbase.WelcomeActivity;
import com.zx.tjmarketmobile.util.MyApplication;
import com.zx.tjmarketmobile.util.Util;


/**
 * Create By Xiangb On 2016/9/13
 * 功能：BaseActivity
 */
@SuppressWarnings("deprecation")
public class BaseActivity extends AppCompatActivity implements BaseRequestData.OnHttpLoadingListener<BaseHttpResult>, View.OnClickListener {
    public ProgressDialog progressDialog;
    protected Handler handler = new Handler();
    private Toolbar mToolbar;
    private ToolBarHelper mToolBarHelper;
    public View toolbar_view;
    private ImageView base_img;
    public HttpLoginEntity userInfo;
    public UserManager userManager;
    private TextView base_text;
    public Context mContext;
    public MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
    public LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
    public static boolean sIsLoginClear = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        userManager = new UserManager();
        userInfo = userManager.getUser(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolBarHelper = new ToolBarHelper(this, layoutResID);
        mToolbar = mToolBarHelper.getToolBar();
    }

    public void addToolBar(boolean bool) {
        if (bool) {
            toolbar_view = mToolBarHelper.getContentView();
            setContentView(toolbar_view);
            /*把 toolbar 设置到Activity 中*/
            setSupportActionBar(mToolbar);
        } else {
            mToolbar.setVisibility(View.GONE);
        }
    }


    // 获得左侧图片
    public ImageView getLeftImg() {
        return (ImageView) toolbar_view.findViewById(android.R.id.home);
    }

    // 获得右侧图片
    public ImageView getRightImg() {
        return (ImageView) toolbar_view.findViewById(R.id.toolbar_right);
    }

    //获得中央图片
    public void showMidPic() {
        toolbar_view.findViewById(R.id.toolbar_midPic).setVisibility(View.VISIBLE);
    }

    // 获得中央文字
    public TextView getMidText() {
        return (TextView) toolbar_view.findViewById(R.id.toolbar_mid);
    }

    // 隐藏左侧图片
    public void hideLeftImg() {
        base_img = getLeftImg();
        base_img.setVisibility(View.INVISIBLE);
    }

    // 隐藏右侧图片
    public void hideRightImg() {
        base_img = getRightImg();
        base_img.setVisibility(View.INVISIBLE);
    }

    // 隐藏中央文本
    public void hideMidText() {
        TextView mid_text = getMidText();
        mid_text.setVisibility(View.INVISIBLE);
    }

    // 设置左侧背景图
    public void setLeftImgBG(int resId) {
        base_img = getLeftImg();
        base_img.setBackgroundResource(resId);
    }

    // 设置右侧背景图
    public void setRightImgBG(int resId) {
        base_img = getRightImg();
        base_img.setBackgroundResource(resId);
    }

    // 设置中央文本
    public void setMidText(String text) {
        base_text = getMidText();
        base_text.setText(text);
    }

    /**
     * 左侧返回按钮功能
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 显示加载进度条
     */
    public void showProgressDialog(String msg) {

        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this, "", msg);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        } else {
            progressDialog.setMessage(msg);
            progressDialog.show();
        }
    }

    /**
     * 用于判断是否登录超时
     */
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(BaseActivity.this);
        long timeStr = sp.getLong("setting_time", 0);
        long hoursStr = sp.getLong("setting_hours", 8);
        long selectTime = timeStr + hoursStr * 60 * 60 * 1000;
        if (selectTime < System.currentTimeMillis()) {
            if (!sIsLoginClear) {
                sIsLoginClear = true;
                clearLogin();
                if (!(mContext instanceof WelcomeActivity)) {
                    Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                    startActivity(intent);
                    showToast("登录超时！请重新登录");
                    Util.activity_In(this);
                }
                return;
            }
        }

    }

    public void clearLogin() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sp.edit();
        String curuser = sp.getString("curuser", "");
        edit.putString("curuser", "");
        edit.putString(curuser, "");
        edit.putString("fUserId", "");
        edit.putBoolean("isLogin", false);
        edit.commit();
        userManager.setNoLogin(this);
    }

    protected void onDestroy() {
        dismissProgressDialog();
        handler = null;
        super.onDestroy();
        MyApplication.getInstance().getActivityList().remove(this);
    }

    /**
     * 取消进度条
     */
    public void dismissProgressDialog() {
        if (handler != null) {
            handler.post(new Runnable() {
                public void run() {
                    if (null != progressDialog && progressDialog.isShowing()) {
                        try {
                            progressDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    // 关闭软键盘
    public void closeSoftInput() {
        if (getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 提示信息
     */
    public void showToast(String message) {
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 开始加载
     *
     * @param id
     */
    @Override
    public void onLoadStart(int id) {
        showProgressDialog("正在加载，请稍后...");
    }

    /**
     * 加载失败
     *
     * @param id
     * @param bool
     * @param errorMsg
     */
    @Override
    public void onLoadError(int id, boolean bool, String errorMsg) {
        dismissProgressDialog();
        if (!TextUtils.isEmpty(errorMsg)) {
            if (errorMsg.equals("Not login!")) {
                showToast("登录失效，请重新登录");
                SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
                edit.putString("curuser", "");
                edit.commit();
                MyApplication.getInstance().remove(HomeActivity.class);
                MyApplication.getInstance().remove(GuideActivity.class);
                finish();
            } else if (errorMsg.contains("系统")) {
                showToast("后台服务器错误，请稍候再试");
            } else {
                showToast(errorMsg);
            }
        } else {
            showToast("网络连接失败");
        }
    }

    /**
     * 加载成功
     *
     * @param id
     * @param baseHttpResult
     */
    @SuppressWarnings("unchecked")
    public void onLoadComplete(int id, BaseHttpResult baseHttpResult) {
        dismissProgressDialog();
    }

    @Override
    public void onLoadPregress(int id, int progress) {
        if (id == ApiData.FILE_DOWNLOAD) {
            dismissProgressDialog();
            Util.showProgressDialog(this, progress, "正在下载中");
        }
    }

    @Override
    public void onClick(View v) {

    }
}
