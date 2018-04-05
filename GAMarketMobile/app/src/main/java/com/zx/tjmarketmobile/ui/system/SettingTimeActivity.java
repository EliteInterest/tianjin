package com.zx.tjmarketmobile.ui.system;

import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.DigitUtil;

/**
 * Create By Xiangb On 2016/9/19
 * 功能：时间设置
 */
public class SettingTimeActivity extends BaseActivity implements OnClickListener {

    private EditText mEdtTime;
    private boolean mIsDefined = false;
    private int mSelectTime = 8;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_time);
        addToolBar(true);
        hideRightImg();
        setMidText("设置-时限");

        RadioGroup rgSelect = (RadioGroup) findViewById(R.id.rgActSettingTime_select);
        mEdtTime = (EditText) findViewById(R.id.edtActSettingTime_time);

        rgSelect.setOnCheckedChangeListener(checkedChangeListener);
        findViewById(R.id.btnActSettingTime_ok).setOnClickListener(this);
        long hour = PreferenceManager.getDefaultSharedPreferences(SettingTimeActivity.this).getLong("setting_hours", 168);
        if (hour == 8 || hour == 24 || hour == 168) {
            rgSelect.check(getResources().getIdentifier("rbActSettingTime_" + hour, "id", getPackageName()));
        } else {
            rgSelect.check(R.id.rbActSettingTime_defined);
            mEdtTime.setText(hour + "");
        }
    }

    OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            mEdtTime.setVisibility(View.GONE);
            mIsDefined = false;
            switch (checkedId) {
                case R.id.rbActSettingTime_8:
                    // 8小时
                    mSelectTime = 8;
                    break;
                case R.id.rbActSettingTime_24:
                    // 一天
                    mSelectTime = 24;
                    break;
                case R.id.rbActSettingTime_168:
                    // 一周
                    mSelectTime = 168;
                    break;
                case R.id.rbActSettingTime_defined:
                    // 自定义
                    mEdtTime.setVisibility(View.VISIBLE);
                    mIsDefined = true;
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnActSettingTime_ok:
                if (mIsDefined) {
                    String selectTime = mEdtTime.getText().toString();
                    if (!TextUtils.isEmpty(selectTime)) {
                        mSelectTime = DigitUtil.StringToInt(selectTime);
                    } else {
                        showToast("请输入时间！");
                        break;
                    }
                }
                if (mSelectTime <= 0) {
                    mSelectTime = 8;
                }
                Editor edit = PreferenceManager.getDefaultSharedPreferences(SettingTimeActivity.this).edit();
                edit.putLong("setting_time", System.currentTimeMillis());
                edit.putLong("setting_hours", mSelectTime);
                edit.commit();
                finish();
                break;

            default:
                break;
        }

    }

}
