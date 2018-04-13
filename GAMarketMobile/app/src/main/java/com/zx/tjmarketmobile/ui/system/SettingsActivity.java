package com.zx.tjmarketmobile.ui.system;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.SettingAdapter;
import com.zx.tjmarketmobile.entity.HttpUpdateEntity;
import com.zx.tjmarketmobile.helper.GADBHelper;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.MyItemClickListener;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.ui.camera.CameraActivity;
import com.zx.tjmarketmobile.util.SYSUtil;
import com.zx.tjmarketmobile.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create By Xiangb On 2016/9/19
 * 功能：设置界面
 */
public class SettingsActivity extends BaseActivity implements MyItemClickListener, OnClickListener {

    private RecyclerView mRecyclerView;
    private AppCompatButton mBtnLogout;
    private SQLiteDatabase mDb;
    private File file = null;
    private double fileSize = 0;
    private SettingAdapter settingsAdapter;
    //    private ApiData mUpdateData = new ApiData(ApiData.HTTP_ID_version_update);
    private ApiData loginOut = new ApiData(ApiData.HTTP_ID_loginOut);
    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        addToolBar(true);
        hideRightImg();
        setMidText("设置");

        GADBHelper gadbHelper = new GADBHelper(mContext, GADBHelper.TABLE_NAME);
        mDb = gadbHelper.getWritableDatabase();

//        mUpdateData.setLoadingListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_normal_view);
        ((SwipeRefreshLayout) findViewById(R.id.srl_normal_layout)).setEnabled(false);
        addList();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        settingsAdapter = new SettingAdapter(this, list);
        mRecyclerView.setAdapter(settingsAdapter);
        settingsAdapter.setOnItemClickListener(this);
        loginOut.setLoadingListener(this);

        mBtnLogout = (AppCompatButton) findViewById(R.id.btn_settings_logout);
        mBtnLogout.setOnClickListener(this);
    }

    private void addList() {
        list.clear();
        file = new File("/data/data/com.zx.gamarketmobile/databases/" + GADBHelper.TABLE_NAME);
        if (file.exists()) {
            fileSize = getFileSize(file);
            if (fileSize < 0.5) {
                fileSize = 0;
            }
        }

        Map<String, String> map = new HashMap<String, String>();
        map = new HashMap<String, String>();
        map.put("name", "地图");
        list.add(map);
        map = new HashMap<String, String>();
        map.put("name", "登陆时限");
        list.add(map);
        map = new HashMap<>();
        map.put("name", "清除地图缓存 (" + fileSize + "M)");
        list.add(map);
//		map = new HashMap<String, String>();
//		map.put("name", "检查更新");
//		map.put("flag", sharePreferences.getString("update_version", ""));
//		list.add(map);
//        map = new HashMap<String, String>();
//        map.put("name", "系统通知");
//        list.add(map);
        map = new HashMap<String, String>();
        map.put("name", "密码修改");
        list.add(map);
//        map = new HashMap<String, String>();
//        map.put("name", "关于我们");
//        list.add(map);
//        map = new HashMap<String, String>();
//        map.put("name", "帮助");
//        list.add(map);
        if (!ApiData.ISRELEASE) {
            map = new HashMap<String, String>();
            map.put("name", "服务器");
            list.add(map);
        }
    }

    //获取文件大小
    private double getFileSize(File file) {
        DecimalFormat df = new DecimalFormat("#.00");
        double size = 0;
        try {
            if (file.exists()) {
                FileInputStream fIs = new FileInputStream(file);
                size = Double.valueOf(df.format((double) fIs.available() / 1048576));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_settings_logout:// 用户退出登陆
                startActivityForResult(new Intent(SettingsActivity.this, CameraActivity.class),RESULT_OK);
//                Util.showDeleteDialog(SettingsActivity.this, "是否退出当前用户?", null, "确定", "取消", new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        loginOut.loadData();
//                        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this).edit();
//                        edit.putString("curuser", "");
//                        edit.apply();
//                        userManager.setNoLogin(SettingsActivity.this);
//                        MyApplication.getInstance().remove(HomeActivity.class);
//                        MyApplication.getInstance().remove(GuideActivity.class);
//                        finish();
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
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_loginOut:

                break;

            default:
                break;
        }
        if (b.isSuccess()) {
            HttpUpdateEntity updateInfo = (HttpUpdateEntity) b.getEntry();
            int versionCode = SYSUtil.getVersionCode(this);
            if (updateInfo != null && versionCode < updateInfo.versionCode) {
//				showUpdateDialog(updateInfo);
            } else {
                showToast("暂无更新！");
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {

            case 0:// 选择地图访问模式
            {
                Intent intent = new Intent();
                intent.setClass(this, SettingsMapActivity.class);
                startActivity(intent);
                Util.activity_Out(this);
            }
            break;
            case 1:// 设置登录状态清空时间
            {
                Intent intent = new Intent();
                intent.setClass(this, SettingTimeActivity.class);
                startActivity(intent);
                Util.activity_Out(this);
                break;
            }
            case 2: {//清除缓存
                if (fileSize == 0) {
                    showToast("没有地图缓存，无需清理");
                } else {
                    Util.showYesOrNoDialog(this, "提示!", "是否删除地图缓存?", "确认", "取消", new OnClickListener() {
                        @Override
                        public void onClick(View v) {//确认
                            try {
                                String sqlDelete = "delete from " + GADBHelper.TABLE_NAME;
                                mDb.execSQL(sqlDelete);
                                addList();
                                settingsAdapter.notifyDataSetChanged();
                                Util.dialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, null);
                }
            }
            break;
//		case 3:// 检查更新
//		{
//			mUpdateData.loadData(userInfo.getId());
//			break;
//		}
//            case 4:// 系统通知
//            {
//                Intent intent = new Intent();
//                intent.setClass(this, NotifyActivity.class);
//                startActivity(intent);
//                Util.activity_Out(this);
//                break;
//            }
            case 3:// 密码修改
            {
                Intent intent = new Intent();
                intent.setClass(this, SettingsPassWordActivity.class);
                startActivity(intent);
                Util.activity_Out(this);
                break;
            }
            case 4:// 关于我们
            {
                Intent intent = new Intent();
                intent.setClass(this, AboutActivity.class);
                startActivity(intent);
                Util.activity_Out(this);
            }
            break;
            case 5:// 帮助
            {
                Intent intent = new Intent();
                intent.setClass(this, HelpActivity.class);
                startActivity(intent);
                Util.activity_Out(this);
            }
            break;
            case 6:// 设置服务器IP
            {
                Intent intent = new Intent();
                intent.setClass(this, SettingsIPActivity.class);
                startActivity(intent);
                Util.activity_Out(this);
            }
            break;
            default:
                break;
        }
    }

//	private void showUpdateDialog(final HttpUpdateEntity updateInfo) {
//		final Dialog dialog = new Dialog(this, R.style.fullDialog);
//		View view = LayoutInflater.from(this).inflate(R.layout.dialog_update, null);
//		dialog.setContentView(view);
//		dialog.setCanceledOnTouchOutside(true);
//		TextView tvContent = (TextView) dialog.findViewById(R.id.tvDialogUpdate_content);
//		Button btnConfirm = (Button) dialog.findViewById(R.id.btnDialogUpdate_confirm);
//		Button btnCancel = (Button) dialog.findViewById(R.id.btnDialogUpdate_cancel);
//		if (!TextUtils.isEmpty(updateInfo.fRemark) && !"null".equals(updateInfo.fRemark)) {
//			tvContent.setText(updateInfo.fRemark);
//		}
//		btnConfirm.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				NotificationUtil n = new NotificationUtil(SettingsActivity.this);
//				n.downLoadApk(updateInfo.fUrl);
//				dialog.cancel();
//			}
//		});
//
//		btnCancel.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.cancel();
//			}
//		});
//		dialog.show();
//	}


}
