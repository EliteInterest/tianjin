package com.zx.tjmarketmobile.util;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.CasePoiPagerAdapter;
import com.zx.tjmarketmobile.adapter.CompPoiPagerAdapter;
import com.zx.tjmarketmobile.adapter.TaskPoiPagerAdapter;
import com.zx.tjmarketmobile.adapter.ZtPoiPagerAdapter;
import com.zx.tjmarketmobile.entity.CaseInfoEntity;
import com.zx.tjmarketmobile.entity.ComplainInfoEntity;
import com.zx.tjmarketmobile.entity.EntityDetail;
import com.zx.tjmarketmobile.entity.HttpEventEntity;
import com.zx.tjmarketmobile.entity.HttpLoginEntity;
import com.zx.tjmarketmobile.entity.HttpSearchZtEntity;
import com.zx.tjmarketmobile.entity.HttpTaskEntity;
import com.zx.tjmarketmobile.entity.HttpZtEntity;
import com.zx.tjmarketmobile.entity.SuperviseDetailInfo;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.http.BaseRequestData;
import com.zx.tjmarketmobile.listener.PAOnClickListener;
import com.zx.tjmarketmobile.manager.UserManager;
import com.zx.tjmarketmobile.ui.map.EntityDetailActivity;
import com.zx.tjmarketmobile.ui.map.EventEntityActivity;
import com.zx.tjmarketmobile.ui.map.WorkInMapShowActivity;
import com.zx.tjmarketmobile.ui.supervise.SuperviseDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiangb on 2017/3/29.
 * 功能：用于处理从任务进入地图时的界面操作
 */
@SuppressWarnings("deprecation")
public class MapMarkerTool implements BaseRequestData.OnHttpLoadingListener<BaseHttpResult> {

    private WorkInMapShowActivity activity;
    private List<HttpTaskEntity> mTaskList;
    private List<CaseInfoEntity> mCaseList;
    private List<ComplainInfoEntity> mCompList;
    private int mType = ConstStrings.MapType_Main;
    public Dialog dialog;
    private View diaView = null;
    public double startLongitude;
    public double startLatitude;
    public double endLongitude;
    public double endLatitude;
    private double mLongitude;
    private double mLatitude;
    public ViewPager mPoiViewPager;
    private LinearLayout mLlPoi;
    public GraphicsLayer mMarkersGLayer = null;// 用于展示主体或任务结果注记
    private MapView mMapView;
    public HttpSearchZtEntity mSearchZtEntity = null;
    public int mCurId = -1;
    public final String attributenme = "ztindex";
    private String fEntityType;
    private HttpZtEntity zt;
    private HttpLoginEntity userInfo;
    public boolean mIsFromMapTap = false;// 标明滑动面板切换是否由点击地图标注引进
    private ApiData mChangeposData = new ApiData(ApiData.HTTP_ID_change_pos);
    private ApiData taskComplaintDetailData = new ApiData(ApiData.HTTP_ID_searchtask_detail);
    private ApiData taskSuperviseDetailData = new ApiData(ApiData.HTTP_ID_supervisetask_detail);
    private ApiData eventDetailData = new ApiData(ApiData.HTTP_ID_searchevent_detail);
    public ApiData taskData = new ApiData(ApiData.HTTP_ID_entity_detail);
    private UserManager userManager = new UserManager();


    public MapMarkerTool(WorkInMapShowActivity activity, MapView mMapView) {
        this.activity = activity;
        this.mMapView = mMapView;
        userInfo = userManager.getUser(activity);
    }

    public void initPoiViewPager() {

        mPoiViewPager = (ViewPager) activity.findViewById(R.id.vp_poidetails);
        mLlPoi = (LinearLayout) activity.findViewById(R.id.llpoi);

        mMapView.setOnSingleTapListener(singleTapListener);
        mMarkersGLayer = new GraphicsLayer();
        mMapView.addLayer(mMarkersGLayer);

        taskComplaintDetailData.setLoadingListener(this);
        taskSuperviseDetailData.setLoadingListener(this);
        eventDetailData.setLoadingListener(this);
        mChangeposData.setLoadingListener(this);
        taskData.setLoadingListener(this);
        //初始化内容
        initMarkerAndViewPager();
    }

    public String getfEntityType() {
        return fEntityType;
    }

    public void setfEntityType(String fEntityType) {
        this.fEntityType = fEntityType;
    }

    /**
     * 初始化底部及图标
     */
    @SuppressWarnings("unchecked")
    private void initMarkerAndViewPager() {
        Intent intent = activity.getIntent();
        Bundle bundle = null;
        if (intent != null) {
            bundle = intent.getExtras();
            if (bundle != null) {
                mType = bundle.getInt("type");
            }
        }

        switch (mType) {
            case ConstStrings.MapType_Main:
                activity.mLlChangePos.setVisibility(View.GONE);
                activity.mTVList.setVisibility(View.GONE);
                mLlPoi.setVisibility(View.GONE);
                break;
            case ConstStrings.MapType_SearchZt:// 主体列表查看地图
                activity.mLlChangePos.setVisibility(View.VISIBLE);
                activity.mTVList.setVisibility(View.VISIBLE);
                if (bundle != null) {
                    mSearchZtEntity = (HttpSearchZtEntity) bundle.getSerializable("entity");
                    if (mSearchZtEntity != null && mSearchZtEntity.getZtList().size() > 0) {
                        mLlPoi.setVisibility(View.VISIBLE);
                        initZtInfo(mSearchZtEntity.getZtList());
                    }
                }
                break;

            case ConstStrings.MapType_Task:// 任务列表查看地图
                activity.mLlChangePos.setVisibility(View.VISIBLE);
                activity.mTVList.setVisibility(View.VISIBLE);
                if (bundle != null) {
                    mTaskList = (List<HttpTaskEntity>) bundle.getSerializable("entity");
                    if (mTaskList != null && mTaskList.size() > 0) {
                        mLlPoi.setVisibility(View.VISIBLE);
                        initTaskInfo(mTaskList);
                    }
                }
                break;
            case ConstStrings.MapType_ZtDetail:// 主体详情查看地图
                activity.mLlChangePos.setVisibility(View.VISIBLE);
                activity.mTVTitle.setVisibility(View.VISIBLE);
                activity.mTVTitle.setText(R.string.title_zt);
                activity.mLlSearch.setVisibility(View.GONE);
                activity.mTVList.setVisibility(View.GONE);
                // mLlBottomTask.setVisibility(View.GONE);
                if (bundle != null) {
                    HttpZtEntity zt = (HttpZtEntity) bundle.getSerializable("entity");
                    if (zt != null) {
                        mSearchZtEntity = new HttpSearchZtEntity();
                        mSearchZtEntity.setZtList(new ArrayList<HttpZtEntity>());
                        mSearchZtEntity.getZtList().add(zt);
                        mLlPoi.setVisibility(View.VISIBLE);
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLlPoi.getLayoutParams();
                        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        // mLlPoi.setLayoutParams(params);
                        initZtInfo(mSearchZtEntity.getZtList());
                    }
                }
                break;

            case ConstStrings.MapType_TaskDetail:// 任务详情查看地图
                activity.mLlChangePos.setVisibility(View.VISIBLE);
                // mEditTextSearch.setVisibility(View.GONE);
                activity.mTVTitle.setVisibility(View.VISIBLE);
                activity.mTVTitle.setText(R.string.title_task);
                activity.mLlSearch.setVisibility(View.GONE);
                activity.mTVList.setVisibility(View.GONE);
                // mLlBottomTask.setVisibility(View.GONE);
                if (bundle != null) {
                    HttpTaskEntity task = (HttpTaskEntity) bundle.getSerializable("entity");
                    if (task != null) {
                        mTaskList = new ArrayList<HttpTaskEntity>();
                        mTaskList.add(task);
                        mLlPoi.setVisibility(View.VISIBLE);
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLlPoi.getLayoutParams();
                        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        // mLlPoi.setLayoutParams(params);
                        initTaskInfo(mTaskList);
                    }
                }
                break;
            case ConstStrings.MapType_CaseDetail://案件详情查看地图
                activity.mLlChangePos.setVisibility(View.GONE);
                activity.mTVTitle.setVisibility(View.VISIBLE);
                activity.mTVTitle.setText("案件位置");
                activity.mLlSearch.setVisibility(View.GONE);
                activity.mTVList.setVisibility(View.GONE);
                if (bundle != null) {
                    CaseInfoEntity task = (CaseInfoEntity) bundle.getSerializable("entity");
                    if (task != null) {
                        mCaseList = new ArrayList<>();
                        mCaseList.add(task);
                        mLlPoi.setVisibility(View.VISIBLE);
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLlPoi.getLayoutParams();
                        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        initCaseInfo(mCaseList);
                    }
                }
                break;
            case ConstStrings.MapType_CompDetail://投诉举报详情查看地图
                activity.mLlChangePos.setVisibility(View.GONE);
                activity.mTVTitle.setVisibility(View.VISIBLE);
                activity.mTVTitle.setText("案件位置");
                activity.mLlSearch.setVisibility(View.GONE);
                activity.mTVList.setVisibility(View.GONE);
                if (bundle != null) {
                    ComplainInfoEntity task = (ComplainInfoEntity) bundle.getSerializable("entity");
                    if (task != null) {
                        mCompList = new ArrayList<>();
                        mCompList.add(task);
                        mLlPoi.setVisibility(View.VISIBLE);
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLlPoi.getLayoutParams();
                        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        initCompInfo(mCompList);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 地图单击事件
     */
    private OnSingleTapListener singleTapListener = new OnSingleTapListener() {

        /**
         *
         */
        private static final long serialVersionUID = -3600140739331681076L;

        @Override
        public void onSingleTap(float x, float y) {
            if (!mMapView.isLoaded()) {
                return;
            }
            int[] ids = mMarkersGLayer.getGraphicIDs(x, y, 1);
            if (ids != null && ids.length > 0) {
                if (mCurId != ids[0]) {// 说明本次选中标注与上一次不一样
                    switch (mType) {
                        case ConstStrings.MapType_Main:
                            break;
                        case ConstStrings.MapType_SearchZt:// 主体列表查看地图
                        {
                            if (mSearchZtEntity != null) {
                                mMarkersGLayer.removeGraphic(mCurId);

                                Graphic graphic = mMarkersGLayer.getGraphic(ids[0]);
                                if (graphic != null) {
                                    Object obj = graphic.getAttributes().get(attributenme);
                                    int index = Integer.parseInt(String.valueOf(obj));
                                    HttpZtEntity zt = mSearchZtEntity.getZtList().get(index);

                                    Map<String, Object> attributes = new HashMap<String, Object>();
                                    PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(ContextCompat.getDrawable(activity, R.mipmap.tjd1));
                                    double ptx = Double.valueOf(zt.getLongitude());
                                    double pty = Double.valueOf(zt.getLatitude());
                                    Point pt = new Point(ptx, pty);
                                    SpatialReference sr4326 = SpatialReference.create(4490);
                                    Point mappt = (Point) GeometryEngine.project(pt, sr4326,
                                            mMapView.getSpatialReference());
                                    attributes.put(attributenme, index);
                                    mCurId = mMarkersGLayer.addGraphic(new Graphic(mappt, picSymbol, attributes));
                                    mMapView.centerAt(mappt, true);
                                    mIsFromMapTap = true;
                                    mPoiViewPager.setCurrentItem(index);
                                }
                            }
                        }
                        break;

                        case ConstStrings.MapType_Task:// 任务列表查看地图
                        {
                            if (mTaskList != null) {
                                mMarkersGLayer.removeGraphic(mCurId);
                                Graphic graphic = mMarkersGLayer.getGraphic(ids[0]);
                                if (graphic != null) {
                                    Object obj = graphic.getAttributes().get(attributenme);
                                    int index = Integer.parseInt(String.valueOf(obj));
                                    HttpTaskEntity task = mTaskList.get(index);

                                    Map<String, Object> attributes = new HashMap<String, Object>();
                                    PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(ContextCompat.getDrawable(activity, R.mipmap.tjd1));
                                    double ptx = task.getLongitude();
                                    double pty = task.getLatitude();
                                    Point pt = new Point(ptx, pty);
                                    SpatialReference sr4326 = SpatialReference.create(task.getWkid());
                                    Point mappt = (Point) GeometryEngine.project(pt, sr4326,
                                            mMapView.getSpatialReference());
                                    attributes.put(attributenme, index);
                                    mCurId = mMarkersGLayer.addGraphic(new Graphic(mappt, picSymbol, attributes));
                                    mMapView.centerAt(mappt, true);

                                    mIsFromMapTap = true;
                                    mPoiViewPager.setCurrentItem(index);
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
    };

    /**
     * 主体相关
     *
     * @param ztList
     */
    private void addZtMarkers(List<HttpZtEntity> ztList) {
        addZtMarkers(ztList, 0);
    }

    public void addZtMarkers(List<HttpZtEntity> ztList, int position) {
        mMarkersGLayer.removeAll();
        for (int i = 0; i < ztList.size(); i++) {
            HttpZtEntity zt = ztList.get(i);
            Map<String, Object> attributes = new HashMap<String, Object>();
            int id = getMarkId(zt);
            PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(ContextCompat.getDrawable(activity, id));
            if (zt.getLongitude() != 0 && zt.getLatitude() != 0) {
                double x = zt.getLongitude();
                double y = zt.getLatitude();
                Point pt = new Point(x, y);
                SpatialReference sr4326 = SpatialReference.create(4490);
                Point mappt = (Point) GeometryEngine.project(pt, sr4326, mMapView.getSpatialReference());
                attributes.put(attributenme, i);
                mMarkersGLayer.addGraphic(new Graphic(mappt, picSymbol, attributes));
                if (i == position) {
                    Map<String, Object> attributes1 = new HashMap<String, Object>();
                    PictureMarkerSymbol picSymbol1 = new PictureMarkerSymbol(ContextCompat.getDrawable(activity, R.mipmap.tjd1));
                    double x1 = Double.valueOf(zt.getLongitude());
                    double y1 = Double.valueOf(zt.getLatitude());
                    Point pt1 = new Point(x1, y1);
                    SpatialReference sr43261 = SpatialReference.create(4490);
                    Point mappt1 = (Point) GeometryEngine.project(pt1, sr4326, mMapView.getSpatialReference());
                    attributes.put(attributenme, 0);
                    mCurId = mMarkersGLayer.addGraphic(new Graphic(mappt1, picSymbol1, attributes1));
                }
            }
        }
    }

    /**
     * 协同相关
     *
     * @param taskList
     */
    private void addTaskMarkers(List<HttpTaskEntity> taskList) {
        addTaskMarkers(taskList, 0);
    }

    public void addTaskMarkers(List<HttpTaskEntity> taskList, int position) {
        mMarkersGLayer.removeAll();
        for (int i = 0; i < taskList.size(); i++) {
            HttpTaskEntity task = taskList.get(i);
            Map<String, Object> attributes = new HashMap<String, Object>();
            int id = getMarkId(task.getZtEntity());
            PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(ContextCompat.getDrawable(activity, id));
            double x = task.getLongitude();
            double y = task.getLatitude();
            Point pt = new Point(x, y);
            SpatialReference sr4326 = SpatialReference.create(task.getWkid());
            Point mappt = (Point) GeometryEngine.project(pt, sr4326, mMapView.getSpatialReference());
            attributes.put(attributenme, i);
            mMarkersGLayer.addGraphic(new Graphic(mappt, picSymbol, attributes));
            if (i == position) {
                Map<String, Object> attributes1 = new HashMap<String, Object>();
                PictureMarkerSymbol picSymbol1 = new PictureMarkerSymbol(ContextCompat.getDrawable(activity, R.mipmap.tjd1));
                double x1 = task.getLongitude();
                double y1 = task.getLatitude();
                Point pt1 = new Point(x1, y1);
                SpatialReference sr43261 = SpatialReference.create(task.getWkid());
                Point mappt1 = (Point) GeometryEngine.project(pt1, sr43261, mMapView.getSpatialReference());
                attributes.put(attributenme, 0);
                mCurId = mMarkersGLayer.addGraphic(new Graphic(mappt1, picSymbol1, attributes1));
            }
        }
    }

    /**
     * 案件相关
     *
     * @param caseList
     */
    private void addCaseMarkers(List<CaseInfoEntity> caseList) {
        addCaseMarkers(caseList, 0);
    }

    private void addCaseMarkers(List<CaseInfoEntity> caseList, int position) {
        mMarkersGLayer.removeAll();
        for (int i = 0; i < caseList.size(); i++) {
            CaseInfoEntity caseInfo = caseList.get(i);
            Map<String, Object> attributes = new HashMap<String, Object>();
            PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(ContextCompat.getDrawable(activity, R.mipmap.tjd1));
            double x = Double.valueOf(caseInfo.getLongitude());
            double y = Double.valueOf(caseInfo.getLatitude());
            Point pt = new Point(x, y);
            SpatialReference sr4326 = SpatialReference.create(4490);
            Point mappt = (Point) GeometryEngine.project(pt, sr4326, mMapView.getSpatialReference());
            attributes.put(attributenme, i);
            mMarkersGLayer.addGraphic(new Graphic(mappt, picSymbol, attributes));
            if (i == position) {
                Map<String, Object> attributes1 = new HashMap<String, Object>();
                PictureMarkerSymbol picSymbol1 = new PictureMarkerSymbol(ContextCompat.getDrawable(activity, R.mipmap.tjd1));
                double x1 = Double.valueOf(caseInfo.getLongitude());
                double y1 = Double.valueOf(caseInfo.getLatitude());
                Point pt1 = new Point(x1, y1);
                SpatialReference sr43261 = SpatialReference.create(4490);
                Point mappt1 = (Point) GeometryEngine.project(pt1, sr43261, mMapView.getSpatialReference());
                attributes.put(attributenme, 0);
                mCurId = mMarkersGLayer.addGraphic(new Graphic(mappt1, picSymbol1, attributes1));
            }
        }
    }

    /**
     * 投诉相关
     *
     * @param compList
     */
    private void addCompMarkers(List<ComplainInfoEntity> compList) {
        addCompMarkers(compList, 0);
    }

    private void addCompMarkers(List<ComplainInfoEntity> compList, int position) {
        mMarkersGLayer.removeAll();
        for (int i = 0; i < compList.size(); i++) {
            ComplainInfoEntity compInfo = compList.get(i);
            Map<String, Object> attributes = new HashMap<String, Object>();
            PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(ContextCompat.getDrawable(activity, R.mipmap.tjd1));
            double x = Double.valueOf(compInfo.getSInfo().getX());
            double y = Double.valueOf(compInfo.getSInfo().getY());
            Point pt = new Point(x, y);
            SpatialReference sr4326 = SpatialReference.create(4490);
            Point mappt = (Point) GeometryEngine.project(pt, sr4326, mMapView.getSpatialReference());
            attributes.put(attributenme, i);
            mMarkersGLayer.addGraphic(new Graphic(mappt, picSymbol, attributes));
            if (i == position) {
                Map<String, Object> attributes1 = new HashMap<String, Object>();
                PictureMarkerSymbol picSymbol1 = new PictureMarkerSymbol(ContextCompat.getDrawable(activity, R.mipmap.tjd1));
                double x1 = Double.valueOf(compInfo.getSInfo().getX());
                double y1 = Double.valueOf(compInfo.getSInfo().getY());
                Point pt1 = new Point(x1, y1);
                SpatialReference sr43261 = SpatialReference.create(4490);
                Point mappt1 = (Point) GeometryEngine.project(pt1, sr43261, mMapView.getSpatialReference());
                attributes.put(attributenme, 0);
                mCurId = mMarkersGLayer.addGraphic(new Graphic(mappt1, picSymbol1, attributes1));
            }
        }
    }


    public int getMarkId(HttpZtEntity zt) {
        int id = R.mipmap.xfd;
        try {
            String creditLevel = zt.getCreditCode().toLowerCase();
            if ("b".equals(creditLevel)) {
//                if ("先照后证".equals(zt.fTags)) {
//                    id = R.mipmap.mark_ab;
//                } else if ("无照无证".equals(zt.fTags)) {
//                    id = R.mipmap.mark_bc;
//                } else {
//                    id = R.mipmap.mark_b;
//                }
            } else {
                id = activity.getResources().getIdentifier("mark_" + creditLevel, "drawable", activity.getPackageName());
            }
        } catch (Exception e) {
        }
        if (id == 0) {
            id = R.mipmap.xfd;
        }
        return id;
    }

    private void initZtViewPager(List<HttpZtEntity> ztList) {
        ZtPoiPagerAdapter adapter = new ZtPoiPagerAdapter(activity, ztList, true);
        mPoiViewPager.setAdapter(adapter);
        adapter.setOnClickListener(new PAOnClickListener() {
            @Override
            public void onClick(int resid) {
                switch (resid) {
                    case R.id.ll_search_result_list_view:
                        switch (mType) {
                            case ConstStrings.MapType_SearchZt:// 主体列表查看地图
                                int index = mPoiViewPager.getCurrentItem();
                                zt = mSearchZtEntity.getZtList().get(index);
                                if (zt != null) {
                                    setfEntityType(zt.getEnterpriseType());
                                }
                                taskData.loadData(userInfo.getId(), zt.getId(), zt.getCreditCode(), zt.getEnterpriseName());
                                break;
                            case ConstStrings.MapType_ZtDetail:// 主体详情查看地图
                                activity.finish();
                                break;
                            case ConstStrings.MapType_Main:
                            case ConstStrings.MapType_Task:// 任务列表查看地图
                            case ConstStrings.MapType_TaskDetail:// 任务详情查看地图
                            default:
                                break;
                        }
                        break;

                    case R.id.ibtn_zt_changepos:
                        try {
                            int position = mPoiViewPager.getCurrentItem();
                            HttpZtEntity info = mSearchZtEntity.getZtList().get(position);
                            showNaviDialog(Double.valueOf(info.getLatitude()), Double.valueOf(info.getLongitude()));
                        } catch (Exception e) {
                            activity.showToast("获取坐标导航信息失败，请稍后再试");
                            e.printStackTrace();
                        }
                        break;

                    default:
                        break;
                }

            }
        });
        mPoiViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (!mIsFromMapTap) {
                    mMarkersGLayer.removeGraphic(mCurId);

                    HttpZtEntity zt = mSearchZtEntity.getZtList().get(position);

                    Map<String, Object> attributes = new HashMap<String, Object>();
                    PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(ContextCompat.getDrawable(activity, R.mipmap.tjd1));
                    if (zt.getLongitude() == 0 || zt.getLatitude() == 0) {
                        activity.showToast("该主体没有坐标信息");
                    } else {
                        double ptx = zt.getLongitude();
                        double pty = zt.getLatitude();
                        Point pt = new Point(ptx, pty);
                        SpatialReference sr4326 = SpatialReference.create(4490);
                        Point mappt = (Point) GeometryEngine.project(pt, sr4326, mMapView.getSpatialReference());
                        attributes.put(attributenme, position);
                        mCurId = mMarkersGLayer.addGraphic(new Graphic(mappt, picSymbol, attributes));

                        mMapView.centerAt(mappt, true);
                    }
                } else {
                    mIsFromMapTap = false;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private void initTaskViewPager(List<HttpTaskEntity> taskList) {
        TaskPoiPagerAdapter adapter = new TaskPoiPagerAdapter(activity, taskList, true);
        mPoiViewPager.setAdapter(adapter);
        adapter.setOnClickListener(new PAOnClickListener() {
            @Override
            public void onClick(int resid) {
                switch (resid) {
                    case R.id.ll_search_task_result_list_view:
                        switch (mType) {
                            case ConstStrings.MapType_Main:
                            case ConstStrings.MapType_Task:// 任务列表查看地图
                                int index = mPoiViewPager.getCurrentItem();
                                HttpTaskEntity task = mTaskList.get(index);
                                loadTaskDetailData(task);
                                break;
                            case ConstStrings.MapType_TaskDetail:// 任务详情查看地图
                                activity.finish();
                                break;
                            case ConstStrings.MapType_SearchZt:// 主体列表查看地图
                            case ConstStrings.MapType_ZtDetail:// 主体详情查看地图
                            default:
                                break;
                        }
                        break;

                    case R.id.ibtn_task_changepos:
                        try {
                            int position = mPoiViewPager.getCurrentItem();
                            HttpTaskEntity info = mTaskList.get(position);
                            showNaviDialog(info.getLatitude(), info.getLongitude());
                        } catch (Exception e) {
                            activity.showToast("获取坐标导航信息失败，请稍后再试");
                            e.printStackTrace();
                        }
                        break;

                    default:
                        break;
                }
            }
        });
        mPoiViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (!mIsFromMapTap) {
                    mMarkersGLayer.removeGraphic(mCurId);

                    HttpTaskEntity task = mTaskList.get(position);

                    Map<String, Object> attributes = new HashMap<>();
                    PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(ContextCompat.getDrawable(activity, R.mipmap.tjd1));
                    double ptx = task.getLongitude();
                    double pty = task.getLatitude();
                    if (ptx == 0 || ptx == 0) {
                        activity.showToast("该主体没有坐标信息");
                    } else {
                        Point pt = new Point(ptx, pty);
                        SpatialReference sr4326 = SpatialReference.create(task.getWkid());
                        Point mappt = (Point) GeometryEngine.project(pt, sr4326, mMapView.getSpatialReference());
                        attributes.put(attributenme, position);
                        mCurId = mMarkersGLayer.addGraphic(new Graphic(mappt, picSymbol, attributes));
                        mMapView.centerAt(mappt, true);
                    }
                } else {
                    mIsFromMapTap = false;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private void initCaseViewPager(List<CaseInfoEntity> taskList) {
        CasePoiPagerAdapter adapter = new CasePoiPagerAdapter(activity, taskList, false);
        mPoiViewPager.setAdapter(adapter);
        adapter.setOnClickListener(new PAOnClickListener() {
            @Override
            public void onClick(int resid) {
                switch (resid) {
                    case R.id.ll_search_task_result_list_view:
                        activity.finish();
                        switch (mType) {
                            case ConstStrings.MapType_CaseDetail:
                                activity.finish();
                                break;
                            case ConstStrings.MapType_Task:// 任务列表查看地图
//                                int index = mPoiViewPager.getCurrentItem();
//                                HttpTaskEntity task = mTaskList.get(index);
//                                loadTaskDetailData(task);
                                break;
                            default:
                                break;
                        }
                        break;
                    case R.id.ibtn_task_changepos:
                        try {
                            int position = mPoiViewPager.getCurrentItem();
                            CaseInfoEntity info = mCaseList.get(position);
                            showNaviDialog(Double.valueOf(info.getLatitude()), Double.valueOf(info.getLongitude()));
                        } catch (Exception e) {
                            activity.showToast("获取坐标导航信息失败，请稍后再试");
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        mPoiViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (!mIsFromMapTap) {
                    mMarkersGLayer.removeGraphic(mCurId);

                    CaseInfoEntity task = mCaseList.get(position);

                    Map<String, Object> attributes = new HashMap<>();
                    PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(ContextCompat.getDrawable(activity, R.mipmap.tjd1));
                    double ptx = Double.parseDouble(task.getLongitude());
                    double pty = Double.parseDouble(task.getLatitude());
                    if (ptx == 0 || ptx == 0) {
                        activity.showToast("该主体没有坐标信息");
                    } else {
                        Point pt = new Point(ptx, pty);
                        SpatialReference sr4326 = SpatialReference.create(4490);
                        Point mappt = (Point) GeometryEngine.project(pt, sr4326, mMapView.getSpatialReference());
                        attributes.put(attributenme, position);
                        mCurId = mMarkersGLayer.addGraphic(new Graphic(mappt, picSymbol, attributes));
                        mMapView.centerAt(mappt, true);
                    }
                } else {
                    mIsFromMapTap = false;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private void initCompViewPager(List<ComplainInfoEntity> taskList) {
        CompPoiPagerAdapter adapter = new CompPoiPagerAdapter(activity, taskList, false);
        mPoiViewPager.setAdapter(adapter);
        adapter.setOnClickListener(new PAOnClickListener() {
            @Override
            public void onClick(int resid) {
                switch (resid) {
                    case R.id.ll_search_task_result_list_view:
                        activity.finish();
                        switch (mType) {
                            case ConstStrings.MapType_CompDetail:
                                activity.finish();
                                break;
                            case ConstStrings.MapType_Task:// 任务列表查看地图
//                                int index = mPoiViewPager.getCurrentItem();
//                                HttpTaskEntity task = mTaskList.get(index);
//                                loadTaskDetailData(task);
                                break;
                            default:
                                break;
                        }
                        break;
                    case R.id.ibtn_task_changepos:
                        try {
                            int position = mPoiViewPager.getCurrentItem();
                            ComplainInfoEntity info = mCompList.get(position);
                            showNaviDialog(Double.valueOf(info.getSInfo().getX()), Double.valueOf(info.getSInfo().getY()));
                        } catch (Exception e) {
                            activity.showToast("获取坐标导航信息失败，请稍后再试");
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        mPoiViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (!mIsFromMapTap) {
                    mMarkersGLayer.removeGraphic(mCurId);

                    ComplainInfoEntity task = mCompList.get(position);

                    Map<String, Object> attributes = new HashMap<>();
                    PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(ContextCompat.getDrawable(activity, R.mipmap.tjd1));
                    double ptx = Double.valueOf(task.getSInfo().getX());
                    double pty = Double.valueOf(task.getSInfo().getY());
                    if (ptx == 0 || ptx == 0) {
                        activity.showToast("该主体没有坐标信息");
                    } else {
                        Point pt = new Point(ptx, pty);
                        SpatialReference sr4326 = SpatialReference.create(4490);
                        Point mappt = (Point) GeometryEngine.project(pt, sr4326, mMapView.getSpatialReference());
                        attributes.put(attributenme, position);
                        mCurId = mMarkersGLayer.addGraphic(new Graphic(mappt, picSymbol, attributes));
                        mMapView.centerAt(mappt, true);
                    }
                } else {
                    mIsFromMapTap = false;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    /**
     * 查询详情
     *
     * @param task
     */
    public void loadTaskDetailData(HttpTaskEntity task) {
        int taskType = task.getTaskType();
        switch (taskType) {
            case 0:
                taskSuperviseDetailData.loadData(task.getTaskId(), task.getGuid(), userInfo.getId());
                break;
            case 1:
                taskComplaintDetailData.loadData(task.getGuid(), taskType);
                break;
            case 2:
                eventDetailData.loadData(task.getGuid(), userInfo.getId());
                break;

            default:
                break;
        }
    }

    /**
     * 选择跳转高德或者应用内导航
     *
     * @param latitude
     * @param longitude
     */
    public void showNaviDialog(double latitude, double longitude) {
        if (dialog == null || diaView == null) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            diaView = inflater.inflate(R.layout.popu_dialogues_navi, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setView(diaView);
            Button amapNavi = (Button) diaView.findViewById(R.id.pop_dialogues_AMapNavi);
            amapNavi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (endLatitude == 0 || endLongitude == 0) {
                        Toast.makeText(activity, "主体坐标不存在!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        return;
                    }
                    try {
                        Intent intent = Intent.getIntent("androidamap://viewMap?sourceApplication=移动监督管理执法&poiname=" + "天津执法主体位置" + "&" + "lat=" + endLatitude + "&lon=" + endLongitude + "&dev=0&style=2");
                        activity.startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(activity, "请确认手机已安装高德地图!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            });
            Button gaNavi = (Button) diaView.findViewById(R.id.pop_dialogues_GANavi);
            gaNavi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (endLatitude == 0 || endLongitude == 0) {
                        Toast.makeText(activity, "主体坐标不存在!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        return;
                    }
                    try {
                        Intent intent = Intent.getIntent("baidumap://map/marker?location=" + endLatitude + "," + endLongitude + "&title=移动监督管理执法&content=天津执法主体位置&traffic=on");
//                        Intent intent = Intent.getIntent("http://api.map.baidu.com/direction?origin=" + latitude + "," + longitude + "&destination=" + endLatitude + "," + endLongitude + "&mode=driving&output=&src=移动监督管理执法&region=天津执法主体位置");
                        activity.startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(activity, "请确认手机已安装百度地图!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    dialog.dismiss();
//                    if (endLatitude == 0 || endLongitude == 0) {
//                        Toast.makeText(activity, "主体坐标不存在!", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                        return;
//                    }
//                    Location l = activity.locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                    if (l == null) {
//                        l = activity.locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                    }
//                    if (l == null) {
//                        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        }
//                        activity.isStartNavi = true;
//                        activity.showProgressDialog("正在搜索您的位置");
//                        activity.locManager.requestLocationUpdates(activity.bestprovider, 1000, 0.01f, activity.loclistener);
//                    } else {
//                        Intent intent = new Intent(activity, AMapNaviActivity.class);
//                        intent.putExtra("startLatitude", l.getLatitude());
//                        intent.putExtra("startLongitude", l.getLongitude());
//                        intent.putExtra("endLatitude", endLatitude);
//                        intent.putExtra("endLongitude", endLongitude);
//                        activity.startActivity(intent);
//                        dialog.dismiss();
//                    }
                }
            });
            Button naviCancel = (Button) diaView.findViewById(R.id.pop_dialogues_navi_cancel);
            naviCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog = new AlertDialog.Builder(activity).show();
        } else {
            dialog.show();
        }
        dialog_window(diaView, dialog);// 给dialog设置动画以及全屏
        endLatitude = latitude;
        endLongitude = longitude;
    }

    /**
     * 设置定位信息
     */
    public void showPointLocate() {
        Point pt = new Point(ConstStrings.Longitude, ConstStrings.Latitude);
        switch (mType) {
            case ConstStrings.MapType_SearchZt:// 主体列表查看地图
                try {
                    if (mSearchZtEntity.getZtList().size() > 0) {
                        HttpZtEntity zt = mSearchZtEntity.getZtList().get(0);
                        pt.setXY(Double.valueOf(zt.getLongitude()), Double.valueOf(zt.getLatitude()));
                        SpatialReference sr4326 = SpatialReference.create(4490);
                        pt = (Point) GeometryEngine.project(pt, sr4326, mMapView.getSpatialReference());
                    }
                    mMapView.setScale(ConstStrings.LocationScale);
                    mMapView.centerAt(pt, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ConstStrings.MapType_ZtDetail:// 任务列表查看地图
                if (mSearchZtEntity.getZtList().size() > 0) {
                    HttpZtEntity zt = mSearchZtEntity.getZtList().get(0);
                    if (zt.getLatitude() != 0 && zt.getLongitude() != 0) {
                        pt.setXY(Double.valueOf(zt.getLongitude()), Double.valueOf(zt.getLatitude()));
                        SpatialReference sr4326 = SpatialReference.create(4490);
                        pt = (Point) GeometryEngine.project(pt, sr4326, mMapView.getSpatialReference());
                    }
//                if (Double.valueOf(mMapView.getMaxScale())!=null){
//                    mMapView.setScale(mMapView.getMaxScale()-1);
//                }else{
//                    mMapView.setScale(ConstStrings.LocationScale);
//                }
                    mMapView.setScale(ConstStrings.LocationScale);
                    mMapView.centerAt(pt, true);
                }
                break;
            case ConstStrings.MapType_Task:// 主体详情查看地图
            case ConstStrings.MapType_TaskDetail:// 任务详情查看地图
                if (mTaskList.size() > 0) {
                    HttpTaskEntity task = mTaskList.get(0);
                    pt.setXY(task.getLongitude(), task.getLatitude());
                    SpatialReference sr4326 = SpatialReference.create(task.getWkid());
                    pt = (Point) GeometryEngine.project(pt, sr4326, mMapView.getSpatialReference());
                }
//                if (Double.valueOf(mMapView.getMaxScale())!=null){
//                    mMapView.setScale(mMapView.getMaxScale()-1);
//                }else{
//                    mMapView.setScale(ConstStrings.LocationScale);
//                }
                mMapView.setScale(ConstStrings.LocationScale);
                mMapView.centerAt(pt, true);
                break;
            case ConstStrings.MapType_CaseDetail:
                if (mCaseList.size() > 0) {
                    CaseInfoEntity task = mCaseList.get(0);
                    pt.setXY(Double.valueOf(task.getLongitude()), Double.valueOf(task.getLatitude()));
                    SpatialReference sr4326 = SpatialReference.create(4490);
                    pt = (Point) GeometryEngine.project(pt, sr4326, mMapView.getSpatialReference());
                }
                mMapView.setScale(ConstStrings.LocationScale);
                mMapView.centerAt(pt, true);
                break;
            case ConstStrings.MapType_CompDetail:
                if (mCompList.size() > 0) {
                    ComplainInfoEntity task = mCompList.get(0);
                    pt.setXY(Double.valueOf(task.getSInfo().getX()), Double.valueOf(task.getSInfo().getY()));
                    SpatialReference sr4326 = SpatialReference.create(4490);
                    pt = (Point) GeometryEngine.project(pt, sr4326, mMapView.getSpatialReference());
                }
                mMapView.setScale(ConstStrings.LocationScale);
                mMapView.centerAt(pt, true);
                break;
            case ConstStrings.MapType_Main:
            default:
//                mMapView.setScale(ConstStrings.LocationScale);
//                mMapView.centerAt(pt, true);
                activity.doLocation();
                break;
        }
        mMarkersGLayer.removeGraphic(mCurId);
        if (pt.getX() == ConstStrings.Longitude || pt.getY() == ConstStrings.Latitude) {
//            Toast.makeText(activity, "当前主体暂无坐标！", Toast.LENGTH_SHORT).show();
        } else {
            PictureMarkerSymbol picSymbol1 = new PictureMarkerSymbol(ContextCompat.getDrawable(activity, R.mipmap.tjd1));
            SpatialReference sr43261 = SpatialReference.create(4490);
            Point mappt1 = (Point) GeometryEngine.project(pt, sr43261, mMapView.getSpatialReference());
            mCurId = mMarkersGLayer.addGraphic(new Graphic(mappt1, picSymbol1, new HashMap<String, Object>()));
        }

    }


    private void dialog_window(View view, Dialog dialog) {
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setWindowAnimations(R.style.animationDialog);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    public void initZtInfo(List<HttpZtEntity> ztList) {
        addZtMarkers(ztList);
        initZtViewPager(ztList);
    }

    public void initTaskInfo(List<HttpTaskEntity> mTaskList) {
        addTaskMarkers(mTaskList);
        initTaskViewPager(mTaskList);
    }

    public void initCaseInfo(List<CaseInfoEntity> mCaseList) {
        addCaseMarkers(mCaseList);
        initCaseViewPager(mCaseList);
    }

    public void initCompInfo(List<ComplainInfoEntity> mCompList) {
        addCompMarkers(mCompList);
        initCompViewPager(mCompList);
    }

    /**
     * 纠正我给自信息
     *
     * @param l
     */
    public void upLoadPos(Location l) {
        int index = mPoiViewPager.getCurrentItem();
        mLongitude = l.getLongitude();
        mLatitude = l.getLatitude();
        switch (mType) {
            case ConstStrings.MapType_TaskDetail:
                HttpTaskEntity taskdetail = mTaskList.get(index);
                mChangeposData.loadData(taskdetail.getEntityGuid(), userInfo.getId(), mLongitude, mLatitude,
                        taskdetail.getGuid());
                break;
            case ConstStrings.MapType_Task:
                HttpTaskEntity task = mTaskList.get(index);
                mChangeposData.loadData(task.getEntityGuid(), userInfo.getId(), mLongitude, mLatitude,
                        task.getGuid());
                break;
            case ConstStrings.MapType_SearchZt:
                HttpZtEntity zt = mSearchZtEntity.getZtList().get(index);
                mChangeposData.loadData(zt.getId(), userInfo.getId(), mLongitude, mLatitude, null);
                break;
            case ConstStrings.MapType_ZtDetail:
                HttpZtEntity zt2 = mSearchZtEntity.getZtList().get(index);
                mChangeposData.loadData(zt2.getId(), userInfo.getId(), mLongitude, mLatitude, null);
                break;
            default:
                break;
        }
    }

    /**
     * 位置纠正成功
     */
    public void changePos_success() {
        int index = mPoiViewPager.getCurrentItem();
        switch (mType) {
            case ConstStrings.MapType_SearchZt:
                HttpZtEntity zt = mSearchZtEntity.getZtList().get(index);
                zt.setLatitude(mLatitude);
                zt.setLongitude(mLongitude);
                addZtMarkers(mSearchZtEntity.getZtList(), index);
                break;
            case ConstStrings.MapType_ZtDetail:
                HttpZtEntity zt2 = mSearchZtEntity.getZtList().get(index);
                zt2.setLatitude(mLatitude);
                zt2.setLongitude(mLongitude);
                addZtMarkers(mSearchZtEntity.getZtList(), index);
                break;
            case ConstStrings.MapType_Task:
                HttpTaskEntity task = mTaskList.get(index);
                task.setLatitude(mLatitude);
                task.setLongitude(mLongitude);
                addTaskMarkers(mTaskList, index);
                break;
            case ConstStrings.MapType_TaskDetail:
                HttpTaskEntity taskDetail = mTaskList.get(index);
                taskDetail.setLatitude(mLatitude);
                taskDetail.setLongitude(mLongitude);
                addTaskMarkers(mTaskList, index);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadStart(int id) {

    }

    @Override
    public void onLoadError(int id, boolean isAPIError, String error_message) {

    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        switch (id) {
            case ApiData.HTTP_ID_change_pos:
                if (b.isSuccess()) {
                    changePos_success();
                    Util.showToast(activity, "纠正成功");
                } else {
                    Util.showToast(activity, "纠正失败");
                }
                break;
            case ApiData.HTTP_ID_entity_detail: {
                EntityDetail mEntityDetail = (EntityDetail) b.getEntry();
                Intent intent = new Intent(activity, EntityDetailActivity.class);
                intent.putExtra("type", 2);
                intent.putExtra("entity", mEntityDetail);
                intent.putExtra("ztEntity", zt);
                if (getfEntityType() != null) {
                    String fEntityType = getfEntityType();
                    intent.putExtra("fEntityType", fEntityType);
                }
                activity.startActivity(intent);
                Util.activity_In(activity);
            }
            break;

            case ApiData.HTTP_ID_supervisetask_detail: {
                SuperviseDetailInfo superviseDetailInfo = (SuperviseDetailInfo) b.getEntry();
                String procedure = activity.getIntent().getStringExtra("procedure");
                Intent intent = new Intent(activity, SuperviseDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("task", superviseDetailInfo);
                bundle.putSerializable("status", procedure);
                intent.putExtras(bundle);
                activity.startActivity(intent);
                Util.activity_In(activity);
                break;
            }

            case ApiData.HTTP_ID_searchevent_detail: {
                HttpEventEntity event = (HttpEventEntity) b.getEntry();
                Intent intent = new Intent(activity, EventEntityActivity.class);
                intent.putExtra("entity", event);
                Bundle bundle = new Bundle();
                bundle.putSerializable("emergency", event);
                intent.putExtras(bundle);
                activity.startActivity(intent);
                Util.activity_In(activity);
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void onLoadPregress(int id, int progress) {

    }
}
