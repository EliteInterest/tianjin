package com.zx.tjmarketmobile.ui.base;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.zx.tjmarketmobile.entity.HttpLoginEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.http.BaseRequestData.OnHttpLoadingListener;
import com.zx.tjmarketmobile.manager.UserManager;
import com.zx.tjmarketmobile.ui.mainbase.GuideActivity;
import com.zx.tjmarketmobile.ui.mainbase.HomeActivity;
import com.zx.tjmarketmobile.util.MyApplication;
import com.zx.tjmarketmobile.util.Util;

import java.util.ArrayList;

/**
 * Create By Stanny On 2016/9/19
 * 功能：BaseFragment
 */
@SuppressWarnings("deprecation")
public class BaseFragment extends Fragment implements OnHttpLoadingListener<BaseHttpResult> {
    protected ProgressDialog progressDialog;
    protected Handler handler = new Handler();
    public HttpLoginEntity userInfo;
    protected ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    public LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserManager userManager = new UserManager();
        userInfo = userManager.getUser(getActivity());
    }

    public void onDestroy() {
        handler = null;
        dismissProgressDialog();
        super.onDestroy();
    }

    public void load(final String msg) {
    }

    public void load(final String msg, Object object) {

    }

    public void showProgressDialog(String str) {
        if (null == getActivity()) {
            return;
        }
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(getActivity(), "", str);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(true);
        }
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (handler != null) {
            handler.post(new Runnable() {
                public void run() {
                    if (null != progressDialog && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }

    public void showToast(String msg) {
        try {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoadStart(int id) {
        showProgressDialog("正在加载数据,请稍后..");
    }

    @Override
    public void onLoadError(int id, boolean isAPIError, String error_message) {
        dismissProgressDialog();
        if (!TextUtils.isEmpty(error_message)) {
            if (error_message.equals("Not login!")) {
                showToast("登录失效，请重新登录");
                SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                edit.putString("curuser", "");
                edit.commit();
                MyApplication.getInstance().remove(HomeActivity.class);
                MyApplication.getInstance().remove(GuideActivity.class);
                getActivity().finish();
            } else if (error_message.contains("系统")) {
                showToast("后台服务器错误，请稍候再试");
            } else {
                showToast(error_message);
            }
        } else {
            showToast("网络连接失败");
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        dismissProgressDialog();
    }

    @Override
    public void onLoadPregress(int id, int progress) {
        if (id == ApiData.FILE_DOWNLOAD) {
            dismissProgressDialog();
            Util.showProgressDialog(getActivity(), progress, "正在下载中");
        }
    }
}
