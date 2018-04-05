package com.zx.tjmarketmobile.ui.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.EmergencyListInfo;
import com.zx.tjmarketmobile.entity.HttpZtEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Xiangb on 2016/9/22.
 * 功能：事件信息
 */
public class EventEntityInfoFragment extends BaseFragment implements View.OnClickListener{
    private EmergencyListInfo mEvent;
    private ApiData mLoadZtData = new ApiData(ApiData.HTTP_ID_searchzt_detail);

    public static EventEntityInfoFragment newInstance(int index, EmergencyListInfo mEvent) {
        EventEntityInfoFragment details = new EventEntityInfoFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        details.setArguments(args);
        details.mEvent = mEvent;
        return details;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventinfo, null);
        LinearLayout ll_eventLayout = (LinearLayout) view.findViewById(R.id.ll_eventLayout);
        List<HashMap<String, String>> hashMapList = addData();
        View item = null;
        TextView itemKey,itemValue;
        for (int i = 0 ; i < hashMapList.size() ; i ++) {
            HashMap<String, String> info = hashMapList.get(i);
            item = View.inflate(getActivity() ,R.layout.item_eventinfo, null);
            itemKey = (TextView) item.findViewById(R.id.eventKey);
            itemValue = (TextView) item.findViewById(R.id.eventValue);
            itemKey.setText(info.get("key"));
            itemValue.setText(info.get("value"));
            ll_eventLayout.addView(item);
        }
        mLoadZtData.setLoadingListener(this);
        return view;
    }

    private List<HashMap<String, String>> addData() {
        List<HashMap<String, String>> infoMapList = new ArrayList<>();
        HashMap<String, String> infoMap = new HashMap<>();
        infoMap.put("key", "主体名称");
        infoMap.put("value", mEvent.getfEntityName());
        infoMapList.add(infoMap);
        infoMap = new HashMap<>();
        infoMap.put("key", "统一社会信用代码");
        infoMap.put("value", mEvent.getfUniscid());
        infoMapList.add(infoMap);
        infoMap = new HashMap<>();
        infoMap.put("key", "营业执照注册号");
        infoMap.put("value", mEvent.getfBizlicNum());
        infoMapList.add(infoMap);
        infoMap = new HashMap<>();
        infoMap.put("key", "企业类别");
        infoMap.put("value", mEvent.getfEntityType());
        infoMapList.add(infoMap);
        infoMap = new HashMap<>();
        infoMap.put("key", "许可证");
        infoMap.put("value", mEvent.getfLicenses());
        infoMapList.add(infoMap);
        infoMap = new HashMap<>();
        infoMap.put("key", "法定代表人（负责人）");
        infoMap.put("value", mEvent.getfLegalPerson());
        infoMapList.add(infoMap);
        infoMap = new HashMap<>();
        infoMap.put("key", "地址");
        infoMap.put("value", mEvent.getfAddress());
        infoMapList.add(infoMap);
        infoMap = new HashMap<>();
        infoMap.put("key", "联系方式");
        infoMap.put("value", mEvent.getfContactPhone());
        infoMapList.add(infoMap);
        return infoMapList;
    }

    @Override
    public void onClick(View v) {
        if (mEvent != null) {
//            mLoadZtData.loadData(mEvent.getEntityId());
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
            HttpZtEntity zt = (HttpZtEntity) b.getEntry();
            Intent intent = new Intent(getActivity(), EntityDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("type", 2);
            bundle.putSerializable("entity", zt);
            intent.putExtras(bundle);
            startActivity(intent);
            Util.activity_In(getActivity());
    }
}
