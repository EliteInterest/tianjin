package com.zx.tjmarketmobile.ui.caselegal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.CaseDocAdapter;
import com.zx.tjmarketmobile.entity.CaseDocEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.ui.system.ShowHtmlActivity;
import com.zx.tjmarketmobile.util.ZXItemClickSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2018/4/16.
 * 功能：
 */

public class CaseDocFragment extends BaseFragment {
    private RecyclerView rvDoc;
    private CaseDocAdapter mAdapter;
    private String name;
    private List<CaseDocEntity> dataList = new ArrayList<>();

    private ApiData getDocList = new ApiData(ApiData.HTTP_ID_case_getDocList);
    private ApiData getDocHtml = new ApiData(ApiData.HTTP_ID_case_getDocHtml);

    public static CaseDocFragment newInstance(String fId) {
        CaseDocFragment details = new CaseDocFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", fId);
        details.setArguments(bundle);
        return details;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_doc, container, false);

        getDocList.setLoadingListener(this);
        getDocHtml.setLoadingListener(this);

        rvDoc = view.findViewById(R.id.rv_case_doc);
        rvDoc.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CaseDocAdapter(dataList);
        rvDoc.setAdapter(mAdapter);

        getDocList.loadData(getArguments().getString("id"));

        ZXItemClickSupport.addTo(rvDoc)
                .setOnItemClickListener((recyclerView, position, view1) -> {
                    name = dataList.get(position).getName();
                    getDocHtml.loadData(dataList.get(position).getId());
                });
        return view;
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_case_getDocHtml:
                String path = (String) b.getEntry();
                ShowHtmlActivity.startAction(getActivity(), path, name);
                break;
            case ApiData.HTTP_ID_case_getDocList:
                List<CaseDocEntity> docEntityList = (List<CaseDocEntity>) b.getEntry();
                dataList.clear();
                dataList.addAll(docEntityList);
                mAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
}
