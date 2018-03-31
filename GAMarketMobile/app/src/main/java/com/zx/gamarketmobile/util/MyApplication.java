package com.zx.gamarketmobile.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.StrictMode;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.zx.gamarketmobile.entity.HttpTaskEntity;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.ui.mainbase.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2016/9/23
 * 功能：自定义Application类
 */
public class MyApplication extends Application {

    private ArrayList<Activity> activityList = new ArrayList<Activity>();
    private static MyApplication instance;
    private List<HttpTaskEntity> mTaskList;
    private static RequestQueue queue;
    public List<Integer> tagList = new ArrayList<Integer>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        queue = Volley.newRequestQueue(getApplicationContext());
        if (ApiData.ISRELEASE) {
            CrashHandler.getInstance().init(this);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    public static Context getAppContext() {
        return instance;
    }

    public static Resources getAppResources() {
        return instance.getResources();
    }

    public static RequestQueue getQueue() {
        return queue;
    }

    public void addTag(int tag) {
        tagList.add(tag);
    }

    public void cancelAllQueue() {
        if (tagList.size() > 0) {
            for (int tag : tagList) {
                queue.cancelAll(tag);
            }
        }
    }


    // 单例模式中获取唯一的ExitApplication实例
    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;

    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // 遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
//		App.getInstance().destroyMap();
        System.exit(0);
    }


    //销毁某个activity实例
    public void remove(Class<? extends Activity> t) {
        for (Activity activity : activityList) {
            if (activity.getClass() == t) {
                activity.finish();
            }
        }
    }

    /**
     * 进行activity的移除，直到当前类为登录
     *
     * @param
     */
    public void removeUntilLogin() {
        if (haveActivity(LoginActivity.class)) {// 该类首先要存在
            int length = activityList.size() - 1;
            for (int i = length; i >= 0; i--) {
                if (activityList.get(i) instanceof LoginActivity) {
                    return;
                } else {
                    remove(activityList.get(i).getClass());
                }
            }
        }
    }
    // 遍历所有Activity并finish

    public void finishAll() {
        for (Activity activity : activityList) {
            activity.finish();
        }
//		App.getInstance().destroyMap();
    }

    public boolean haveActivity(Class<? extends Activity> t) {
        for (Activity activity : activityList) {
            if (activity.getClass() == t) {
                return true;
            }
        }
        return false;
    }

    public void clearActivityList() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
    }

    public ArrayList<Activity> getActivityList() {
        return activityList;
    }

    public List<HttpTaskEntity> getTaskList() {
        return mTaskList;
    }

    public void setTaskList(List<HttpTaskEntity> taskList) {
        this.mTaskList = taskList;
    }
}
