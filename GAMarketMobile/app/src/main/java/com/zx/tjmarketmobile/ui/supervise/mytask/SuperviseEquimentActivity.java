package com.zx.tjmarketmobile.ui.supervise.mytask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.esri.core.geometry.Point;
import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.supervise.SuperviseEquipmentAdapter;
import com.zx.tjmarketmobile.entity.NormalListEntity;
import com.zx.tjmarketmobile.entity.supervise.SuperviseEquimentEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.OnMapDialogListener;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.Util;
import com.zx.tjmarketmobile.view.MapViewDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperviseEquimentActivity extends BaseActivity {

    private ApiData getDTByCondition = new ApiData(ApiData.HTTP_ID_superviseGetDTByCondition);
    private ApiData updateDTPositon = new ApiData(ApiData.HTTP_ID_superviseUpdateDTPosition);

    private int pageSize = 10;
    private int pageNo = 1;
    private int totalNo = 0;

    private String equipId = "";

    private List<SuperviseEquimentEntity> dataList = new ArrayList<>();
    private Map<String, Object> mapInfo = new HashMap<>();

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private SuperviseEquipmentAdapter mAdapter;
    private MapViewDialog mapViewDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervise_equiment);
        initView();
    }

    public static void startAction(Activity activity, String entityGuid) {
        Intent intent = new Intent(activity, SuperviseEquimentActivity.class);
        intent.putExtra("entityGuid", entityGuid);
        activity.startActivity(intent);
    }

    private void initView() {
        addToolBar(true);
        hideRightImg();
        setMidText("特种设备列表");

        swipeRefreshLayout = findViewById(R.id.srl_normal_layout);
        recyclerView = findViewById(R.id.rv_normal_view);

        getDTByCondition.setLoadingListener(this);
        updateDTPositon.setLoadingListener(this);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (pageNo > 1) {
                pageNo--;
            }
            loadData();
        });
        mapViewDialog = new MapViewDialog(SuperviseEquimentActivity.this, MapViewDialog.MapType.selectPoint, new OnMapDialogListener() {
            @Override
            public void selectPoint(Point selectPoint) {
                Util.showYesOrNoDialog(SuperviseEquimentActivity.this, "提示！",
                        "是否将当前设备的坐标纠正为：\n" + selectPoint.getX() + "," + selectPoint.getY() + "?", "提交", "取消",
                        v -> {
                            updateDTPositon.loadData(selectPoint.getY(), selectPoint.getX(), equipId);
                            Util.dialog.dismiss();
                        }, null);
            }

            @Override
            public void onHide() {

            }
        });
        mAdapter = new SuperviseEquipmentAdapter(this, dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, position) -> SuperviseEquipmentDetailActivity.startAction(SuperviseEquimentActivity.this, dataList.get(position)));
        mAdapter.setOnLoadMoreListener(() -> {
            if (pageNo * pageSize < totalNo) {
                pageNo++;
                mAdapter.setStatus(1, pageNo, totalNo);
                loadData();
            }
        });
        mAdapter.setEquipmentLocationListener((position, id) -> {
            equipId = id;
            mapInfo.clear();
            mapInfo.put("oldLocation", new Point(dataList.get(position).getGx(), dataList.get(position).getGy()));
            mapViewDialog.showMap(mapInfo);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        getDTByCondition.loadData(getIntent().getStringExtra("entityGuid"), pageSize, pageNo);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult baseHttpResult) {
        super.onLoadComplete(id, baseHttpResult);
        switch (id) {
            case ApiData.HTTP_ID_superviseGetDTByCondition:
                swipeRefreshLayout.setRefreshing(false);
                NormalListEntity normalListEntity = (NormalListEntity) baseHttpResult.getEntry();
                totalNo = normalListEntity.getTotal();
                mAdapter.setStatus(0, pageNo, totalNo);
                List<SuperviseEquimentEntity> list = normalListEntity.getEquimentEntityList();
                dataList.clear();
                dataList.addAll(list);
                mAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(0);
                break;
            case ApiData.HTTP_ID_superviseUpdateDTPosition:
                showToast("坐标纠正成功");
                loadData();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapViewDialog != null) {
            mapViewDialog.dismiss();
        }
    }
}
