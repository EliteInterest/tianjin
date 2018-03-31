package com.zx.gamarketmobile.ui.synergy;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.entity.SynergyNumEntity;
import com.zx.gamarketmobile.entity.SynergyTabEntity;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.http.BaseHttpResult;
import com.zx.gamarketmobile.ui.base.BaseActivity;
import com.zx.gamarketmobile.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SynergyActivity extends BaseActivity {

    public ViewPager mVpContent;
    private TabLayout homeTabLayout;
    private boolean isRead = false;

    private ApiData countCheckInfo = new ApiData(ApiData.HTTP_ID_synergyCountCheckInfo);//统计审核和未审核/已阅和未阅

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synergy);

        addToolBar(true);
        setMidText("协同监管");
        getRightImg().setVisibility(View.GONE);

        countCheckInfo.setLoadingListener(this);

        mVpContent = findViewById(R.id.vp_normal_pager);
        homeTabLayout = findViewById(R.id.tb_normal_layout);

        String department = "";
        if (userInfo != null) {
            department = userInfo.getDepartmentAlias();
        }
        List<SynergyTabEntity> dataInfo = createSynergyData();
        if (department.contains("分局") || department.contains("管理员")) {
            for (int i = 0; i < dataInfo.size(); i++) {
                myPagerAdapter.addFragment(SynergyListFragment.newInstance(dataInfo.get(i), true), dataInfo.get(i).getTypeName());
            }
            initRightImage(true);
        } else if (department.contains("质监处")) {
            myPagerAdapter.addFragment(SynergyListFragment.newInstance(dataInfo.get(2), false), dataInfo.get(2).getTypeName());
            myPagerAdapter.addFragment(SynergyListFragment.newInstance(dataInfo.get(3), false), dataInfo.get(3).getTypeName());
            initRightImage(false);
        } else if (department.contains("特种设备处")) {
            myPagerAdapter.addFragment(SynergyListFragment.newInstance(dataInfo.get(1), false), dataInfo.get(1).getTypeName());
            initRightImage(false);
        } else if (department.contains("药监处")) {
            myPagerAdapter.addFragment(SynergyListFragment.newInstance(dataInfo.get(0), false), dataInfo.get(0).getTypeName());
            initRightImage(false);
        } else {
            showToast("暂无您所在科室的协同监管");
            finish();
            initRightImage(false);
        }

        mVpContent.setOffscreenPageLimit(myPagerAdapter.getCount());
        mVpContent.setAdapter(myPagerAdapter);
        homeTabLayout.setupWithViewPager(mVpContent);
        Util.dynamicSetTabLayoutMode(homeTabLayout);
        mVpContent.setCurrentItem(0);
    }

    /**
     * 协同监管
     *
     * @return
     */
    private List<SynergyTabEntity> createSynergyData() {
        List<SynergyTabEntity> synergyInfo = new ArrayList<>();
        List<SynergyNumEntity> ypInfo = new ArrayList<>();
        ypInfo.add(new SynergyNumEntity("特殊药品销售", "specialdrugStore"));
        ypInfo.add(new SynergyNumEntity("特殊药品使用", "specialdrugUsing"));
        ypInfo.add(new SynergyNumEntity("三类器械使用", "medUsing"));
        ypInfo.add(new SynergyNumEntity("器械检验", "medCheck"));
        ypInfo.add(new SynergyNumEntity("疫苗使用", "vacUsing"));
        synergyInfo.add(new SynergyTabEntity("药品", "", ypInfo));

        List<SynergyNumEntity> tzsbInfo = new ArrayList<>();
        tzsbInfo.add(new SynergyNumEntity("电梯", "elevator"));
        tzsbInfo.add(new SynergyNumEntity("预警处理", "hiddenDanger"));
        synergyInfo.add(new SynergyTabEntity("特种设备", "", tzsbInfo));

        List<SynergyNumEntity> zlxxInfo = new ArrayList<>();
        zlxxInfo.add(new SynergyNumEntity("标准公开说明", "bizCheckStatement"));
        zlxxInfo.add(new SynergyNumEntity("不合格处理", "bizCheckSpot"));
        zlxxInfo.add(new SynergyNumEntity("强制检定申请", "bizCheckCompulsory"));
        synergyInfo.add(new SynergyTabEntity("质量信息", "", zlxxInfo));

        List<SynergyNumEntity> yhclInfo = new ArrayList<>();
        yhclInfo.add(new SynergyNumEntity("隐患处理", "zdyyh"));
        synergyInfo.add(new SynergyTabEntity("隐患处理", "", yhclInfo));

        return synergyInfo;
    }

    private void initRightImage(boolean isManager) {
        getRightImg().setVisibility(View.VISIBLE);
        getRightImg().setBackground(ContextCompat.getDrawable(this, R.mipmap.not_read));
        getRightImg().setOnClickListener(v -> {
            if (isRead) {
                getRightImg().setBackground(ContextCompat.getDrawable(SynergyActivity.this, R.mipmap.not_read));
            } else {
                getRightImg().setBackground(ContextCompat.getDrawable(SynergyActivity.this, R.mipmap.has_read));
            }
            isRead = !isRead;
            countCheckInfo.loadData(isManager, userInfo.getId(), isRead ? "1" : "0");
        });
        countCheckInfo.loadData(isManager, userInfo.getId(), isRead ? "1" : "0");
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        if (b.isSuccess()) {
            switch (id) {
                case ApiData.HTTP_ID_synergyCountCheckInfo:
                    JSONObject countNumEntity = (JSONObject) b.getEntry();
                    if (countNumEntity != null) {
                        for (int i = 0; i < myPagerAdapter.fragmentList.size(); i++) {
                            if (myPagerAdapter.fragmentList.get(i) instanceof SynergyListFragment) {
                                SynergyListFragment mFragment = (SynergyListFragment) myPagerAdapter.fragmentList.get(i);
                                mFragment.refreshData(countNumEntity, isRead);
                            }
                        }
                    }
                    break;
                default:
                    break;
            }

        }
    }
}
