package com.zx.tjmarketmobile.ui.system;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.ui.mainbase.GuideActivity;
import com.zx.tjmarketmobile.ui.mainbase.HomeActivity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.view.ClearEditText;
import com.zx.tjmarketmobile.util.MyApplication;
import com.zx.tjmarketmobile.util.Util;

/**
 * Created by zhouzq on 2016/12/15.
 */
public class SettingsPassWordActivity extends BaseActivity implements View.OnClickListener {

    private ClearEditText oldPswEditText, newPswEditText, renewPswEditText;
    private Button btnConfirm;
    private CheckBox showPswBox;
    private ApiData updatePswData = new ApiData(ApiData.HTTP_ID_psw_update);
    private SharedPreferences mSharePreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_psw);

        addToolBar(true);
        setMidText("密码修改");
        hideRightImg();

        initView();

    }

    private void initView() {

        oldPswEditText = (ClearEditText) findViewById(R.id.old_psw_edit);
        newPswEditText = (ClearEditText) findViewById(R.id.new_psw_edit);
        renewPswEditText = (ClearEditText) findViewById(R.id.renew_psw_edit);
        btnConfirm = (Button) findViewById(R.id.btn_yes);
        btnConfirm.setOnClickListener(this);
        showPswBox = (CheckBox) findViewById(R.id.btn_show_psw);
        showPswBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    oldPswEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    newPswEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    renewPswEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    oldPswEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    newPswEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    renewPswEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        mSharePreferences = PreferenceManager.getDefaultSharedPreferences(this);
        updatePswData.setLoadingListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                submitToServer();
                break;
            default:
                break;
        }
    }


    private void submitToServer() {
        if (!Util.checkContentLengthIsOverRun(new EditText[]{oldPswEditText, newPswEditText, renewPswEditText}, 5, 15)) {
            String useName = mSharePreferences.getString("curuser", "");
            String oldPswStr = oldPswEditText.getText().toString();
            String newPswStr = newPswEditText.getText().toString();
            String renewPswStr = renewPswEditText.getText().toString();
            if (!renewPswStr.equals(newPswStr)) {
                Util.setRequired(newPswEditText, "两次新密码不一致，请检查后再试！");
            } else if (oldPswStr.equals(newPswStr)) {
                Util.setRequired(newPswEditText, "新密码与旧密码相同，请重新设置！");
            } else {
                updatePswData.loadData(oldPswStr, newPswStr);
            }
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult baseHttpResult) {
        super.onLoadComplete(id, baseHttpResult);
        if (baseHttpResult.isSuccess()) {
            showToast("密码修改成功！");
            MyApplication.getInstance().remove(SettingsActivity.class);
            MyApplication.getInstance().remove(HomeActivity.class);
            MyApplication.getInstance().remove(GuideActivity.class);
            finish();
        } else {
            showToast(baseHttpResult.getMessage());
        }
    }
}
