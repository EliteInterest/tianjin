package com.zx.tjmarketmobile.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.zx.tjmarketmobile.entity.HttpLoginEntity;
import com.zx.tjmarketmobile.http.HttpConstant;


/**
 * 用户管理
 *
 * @author zx-wt
 */
public class UserManager {
    private HttpLoginEntity user;

    public UserManager() {
        user = new HttpLoginEntity();
    }

    public HttpLoginEntity getUser(Context context) {
        init(context);
        return user;
    }

    /***
     *
     * @return 是否登录成功过 true 登录过
     */
    public boolean isLogin() {
        if (user == null)
            return false;
        return user.isLogin();
    }

    public void setNoLogin(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean("isLogin", false).commit();
        user.setIsLogin(false);
    }

    public void init(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        HttpConstant.AppCode = sp.getString("loginCode", "first");
        user = new HttpLoginEntity();
        user.setId(sp.getString("fUserId", null));
        user.setRealName(sp.getString("fRealName", null));
        user.setGender(sp.getString("fGender", null));
        user.setAge(sp.getString("fAge", null));
        user.setUserName(sp.getString("fUserName", null));
        user.setDuty(sp.getString("fDuty", null));
        user.setDepartment(sp.getString("fDepartment", null));
        user.setDepartmentCode(sp.getString("departmentCode", ""));
        user.setDepartmentAlias(sp.getString("fDepartmentAlias", null));
        user.setDesc(sp.getString("fDesc", null));
        user.setTelephone(sp.getString("fTelephone", null));
        user.setAuthority(sp.getString("authority", null));
        // user.setPassword(sp.getParams(id).get("password").toString(), null);
        user.setPassword(sp.getString("fPassword", null));
        user.setGrid(sp.getString("fGrid", null));
        user.setIsLogin(sp.getBoolean("isLogin", false));
    }

    public void setUser(Context context, HttpLoginEntity userinfo) {
        user = userinfo;
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("fUserId", user.getId());
        editor.putString("fRealName", user.getRealName());
        editor.putString("fGender", user.getGender());
        editor.putString("fAge", user.getAge());
        editor.putString("fUserName", user.getUserName());
        editor.putString("fDuty", user.getDuty());
        editor.putString("fDepartment", user.getDepartment());
        editor.putString("fDepartmentAlias", user.getDepartmentAlias());
        editor.putString("fDesc", user.getDesc());
        editor.putString("fTelephone", user.getTelphone());
        editor.putString("fPassword", user.getPassword());
        editor.putString("authority", user.getAuthority());
        editor.putString("fGrid", user.getGrid());
        editor.putBoolean("isLogin", user.isLogin());
        editor.putString("departmentCode", user.getDepartmentCode());
        editor.commit();
    }

}
