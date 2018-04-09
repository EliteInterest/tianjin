package com.zx.tjmarketmobile.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.zx.tjmarketmobile.entity.HttpLoginEntity;
import com.zx.tjmarketmobile.http.HttpConstant;

import java.util.Arrays;


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
        user.setId(sp.getString("id", null));
        user.setRealName(sp.getString("realname", null));
        user.setGender(sp.getString("gender", null));
        user.setAge(sp.getString("age", null));
        user.setUserName(sp.getString("username", null));
        user.setDuty(sp.getString("duty", null));
        user.setDepartment(sp.getString("department", null));
        user.setDepartmentCode(sp.getString("departmentCode", ""));
        user.setDepartmentAlias(sp.getString("departmentAlias", null));
        user.setDesc(sp.getString("desc", null));
        user.setTelephone(sp.getString("telephone", null));
        user.setAuthority(sp.getString("authority", null));
        // user.setPassword(sp.getParams(id).get("password").toString(), null);
        user.setPassword(sp.getString("password", null));
        user.setGrid(sp.getString("grid", null));
        user.setIsLogin(sp.getBoolean("isLogin", false));
        user.setRole(Arrays.asList(sp.getString("role", "").split(",")));
    }

    public void setUser(Context context, HttpLoginEntity userinfo) {
        user = userinfo;
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("id", user.getId());
        editor.putString("realname", user.getRealName());
        editor.putString("gender", user.getGender());
        editor.putString("age", user.getAge());
        editor.putString("username", user.getUserName());
        editor.putString("duty", user.getDuty());
        editor.putString("department", user.getDepartment());
        editor.putString("departmentAlias", user.getDepartmentAlias());
        editor.putString("desc", user.getDesc());
        editor.putString("telephone", user.getTelphone());
        editor.putString("password", user.getPassword());
        editor.putString("authority", user.getAuthority());
        editor.putString("grid", user.getGrid());
        editor.putBoolean("isLogin", user.isLogin());
        editor.putString("departmentCode", user.getDepartmentCode());
        editor.putString("role", user.getRoleString());
        editor.commit();
    }

}
