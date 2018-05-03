package com.zx.tjmarketmobile.ui.infomanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.CaseCompDetailInfoAdapter;
import com.zx.tjmarketmobile.entity.KeyValueInfo;
import com.zx.tjmarketmobile.entity.MeasureDetailTableEntity;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerMeasureLiebiao;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.manager.UserManager;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzq on 2017/3/23.
 */

public class MeasureBaseInfoFragment extends BaseFragment {

    private CaseCompDetailInfoAdapter mCaseAdapter;
    private String fId = "";
    private InfoManagerMeasureLiebiao.RowsBean mEntity;
    private RecyclerView rvBaseInfo;

    private String tableName = "";
    private List<MeasureDetailTableEntity> tableEntities = new ArrayList<>();

    private ApiData defind1Data = new ApiData(ApiData.HTTP_ID_info_manager_defined_table);
    private ApiData defindDetailData = new ApiData(ApiData.HTTP_ID_info_manager_defined_detail);

    private List<KeyValueInfo> dataInfoList = new ArrayList<>();

    public static MeasureBaseInfoFragment newInstance(Context context, InfoManagerMeasureLiebiao.RowsBean mEntity) {
        MeasureBaseInfoFragment details = new MeasureBaseInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mEntity", mEntity);
        details.mEntity = mEntity;
        UserManager userManager = new UserManager();
        details.userInfo = userManager.getUser(context);
        details.setArguments(bundle);
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_mytask_baseinfo, container, false);
        mEntity = (InfoManagerMeasureLiebiao.RowsBean) getArguments().getSerializable("mEntity");

        rvBaseInfo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        rvBaseInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        defind1Data.setLoadingListener(this);
        defindDetailData.setLoadingListener(this);
        mCaseAdapter = new CaseCompDetailInfoAdapter(getActivity(), dataInfoList);
        rvBaseInfo.setAdapter(mCaseAdapter);

        tableName = getActivity().getIntent().getStringExtra("type");
//        if (type == 0) {
//            tableName = "t_trading_tools";
//        } else if (type == 1) {
//            tableName = "t_medical_tools";
//        } else if (type == 2) {
//            tableName = "t_tanker_tools";
//        } else if (type == 3) {
//            tableName = "t_license_tools";
//        }
        defind1Data.loadData(tableName);

        return view;
    }


    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_info_manager_defined_table:
                tableEntities = (List<MeasureDetailTableEntity>) b.getEntry();
                defindDetailData.loadData(mEntity.getId(), tableName);
                break;
            case ApiData.HTTP_ID_info_manager_defined_detail:
                JSONObject jsonObject = (JSONObject) b.getEntry();
                dataInfoList.clear();
                for (MeasureDetailTableEntity mEntity : tableEntities) {
                    String value = "";
                    try {
                        if (jsonObject != null && jsonObject.has(mEntity.getCol())) {
                            value = jsonObject.getString(mEntity.getCol());
                            if ("3".equals(mEntity.getType())) {
                                value = DateUtil.getDateFromMillis(value);
                            }
                            if (value == null || "null".contains(value)) {
                                value = "";
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dataInfoList.add(new KeyValueInfo(mEntity.getName() + "：", value));
                }
                mCaseAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

}
