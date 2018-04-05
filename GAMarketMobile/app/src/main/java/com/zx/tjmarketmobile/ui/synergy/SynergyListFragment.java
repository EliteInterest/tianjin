package com.zx.tjmarketmobile.ui.synergy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.SynergyListAdapter;
import com.zx.tjmarketmobile.adapter.SynergySpinnerAdapter;
import com.zx.tjmarketmobile.entity.SynergyInfoBean;
import com.zx.tjmarketmobile.entity.SynergyNumEntity;
import com.zx.tjmarketmobile.entity.SynergyTabEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.MyItemClickListener;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.util.ZXItemClickSupport;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Xiangb on 2017/12/25.
 * 功能：
 */

public class SynergyListFragment extends BaseFragment implements MyItemClickListener {

    private SynergyTabEntity synergyTabEntity;
    private AppCompatSpinner spinner;
    private RecyclerView rvList;
    private SynergyListAdapter mAdapter;
    private SynergySpinnerAdapter spinnerAdapter;

    private boolean isManager = false;//是否为局长或管理员
    private boolean isRead = false;//是否为已阅或已审核
    private int openPosition = -1;
    private List<SynergyInfoBean> dataList = new ArrayList<>();
    private List<SynergyNumEntity> spinnerList = new ArrayList<>();

    private ApiData getParentItems = new ApiData(ApiData.HTTP_ID_synergyGetCountCheckInfoItem);//获取审核与未审核/已阅和未阅的主体名称和条数
    private ApiData getChildItems = new ApiData(ApiData.HTTP_ID_synergyGetCheckInfoItems);//获取审核与未审核/已阅和未阅某个主体具体的几条

    public static SynergyListFragment newInstance(SynergyTabEntity synergyTabEntity, boolean isManager) {
        SynergyListFragment synergyListFragment = new SynergyListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("synergy", synergyTabEntity);
        bundle.putBoolean("isManager", isManager);
        synergyListFragment.setArguments(bundle);
        return synergyListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_synergy_list, container, false);
        synergyTabEntity = (SynergyTabEntity) getArguments().getSerializable("synergy");
        isManager = getArguments().getBoolean("isManager");
        spinner = view.findViewById(R.id.sp_synergy);
        rvList = view.findViewById(R.id.rv_synergy_list);

        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SynergyListAdapter(dataList);
        rvList.setAdapter(mAdapter);
        spinnerList = synergyTabEntity.getSmallTypes();
        spinnerAdapter = new SynergySpinnerAdapter(spinnerList);
        spinner.setAdapter(spinnerAdapter);

        getChildItems.setLoadingListener(this);
        getParentItems.setLoadingListener(this);
        ZXItemClickSupport.addTo(rvList)
                .setOnItemClickListener((recyclerView, position, view1) -> {
                    SynergyInfoBean mEntity = dataList.get(position);
                    openPosition = position;
                    if (mEntity.getItemType() == SynergyListAdapter.PARENT_TYPE && !mEntity.isOpen()) {
                        mEntity.setOpen(true);
                        if (mEntity.getChildList() != null && mEntity.getChildList().size() > 0) {
                            dataList.addAll(openPosition + 1, mEntity.getChildList());
                            mAdapter.notifyItemRangeInserted(openPosition + 1, mEntity.getChildList().size());
                        } else {
                            getChildItems.loadData(isManager, userInfo.getId(), ((SynergyNumEntity) spinner.getSelectedItem()).getDataKey(), isRead ? "1" : "0", mEntity.getFEntityGuid());
                        }
                    } else if (mEntity.getItemType() == SynergyListAdapter.PARENT_TYPE && mEntity.isOpen()) {
                        mEntity.setOpen(false);
                        removeChildItems(mEntity.getFEntityGuid());
                    } else {//Child
                        SynergyDetailActivity.startAction(getActivity(), ((SynergyNumEntity) spinner.getSelectedItem()).getDataKey(), isRead, mEntity.getfGuid());
                    }
                });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkUserDuty();
        loadData();
        return view;
    }

    /**
     * 移除子布局
     *
     * @param parentId
     */
    private void removeChildItems(String parentId) {
        List<SynergyInfoBean> tempList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getItemType() == SynergyListAdapter.CHILD_TYPE && dataList.get(i).getFEntityGuid().equals(parentId)) {
                tempList.add(dataList.get(i));
            }
        }
        dataList.removeAll(tempList);
        mAdapter.notifyItemRangeRemoved(openPosition + 1, tempList.size());
    }

    @Override
    public void onResume() {
        super.onResume();
//        loadData();
    }

    /**
     * 根据用户角色判断界面显示
     */
    private void checkUserDuty() {

    }

    /**
     * 加载数据
     */
    private void loadData() {
        getParentItems.loadData(isManager, userInfo.getId(), ((SynergyNumEntity) spinner.getSelectedItem()).getDataKey(), isRead ? "1" : "0");
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_synergyGetCountCheckInfoItem:
                List<SynergyInfoBean> infoBeans = (List<SynergyInfoBean>) b.getEntry();
                for (int i = 0; i < infoBeans.size(); i++) {
                    infoBeans.get(i).setItemType(SynergyListAdapter.PARENT_TYPE);
                }
                dataList.clear();
                dataList.addAll(infoBeans);
                mAdapter.notifyDataSetChanged();
                break;
            case ApiData.HTTP_ID_synergyGetCheckInfoItems:
                List<SynergyInfoBean> childBeans = (List<SynergyInfoBean>) b.getEntry();
                for (int i = 0; i < childBeans.size(); i++) {
                    childBeans.get(i).setItemType(SynergyListAdapter.CHILD_TYPE);
                }
                dataList.get(openPosition).setChildList(childBeans);
                dataList.addAll(openPosition + 1, childBeans);
                mAdapter.notifyItemRangeInserted(openPosition + 1, childBeans.size());
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    /**
     * 数据刷新
     *
     * @param countNumEntity
     * @param isRead
     */
    public void refreshData(JSONObject countNumEntity, boolean isRead) {
        this.isRead = isRead;
        for (int i = 0; i < spinnerList.size(); i++) {
            try {
                spinnerList.get(i).setNum(countNumEntity.getInt(spinnerList.get(i).getDataKey() + "Num"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinnerAdapter.notifyDataSetChanged();
        loadData();
    }
}
