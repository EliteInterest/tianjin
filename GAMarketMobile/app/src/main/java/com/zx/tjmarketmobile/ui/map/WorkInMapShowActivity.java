package com.zx.tjmarketmobile.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.Layer;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISLocalTiledLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.SpatialReference;
import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.R.id;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.tianditu.TianDiTuLayer;
import com.zx.tjmarketmobile.tianditu.TianDiTuLayerTypes;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.ui.system.SettingsMapActivity;
import com.zx.tjmarketmobile.util.CQDigitalUtil;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.MapMarkerTool;
import com.zx.tjmarketmobile.util.Util;
import com.zx.tjmarketmobile.view.ArcGISLocalDataLayer;

import java.io.File;
import java.io.Serializable;

/**
 * Create By Xiangb On 2016/9/22
 * 功能：地图界面
 */
public class WorkInMapShowActivity extends BaseActivity implements OnClickListener {
    private static final int REQUEST_FINE_LOCATION = 1;

    public MapView mMapView;
    private Layer mVecLayer, mImgLayer, mImgLabLayer, mVecLabLayer;
    public LinearLayout mLlBasemap, mLlLocation, mLlVec, mLlImg, mLlBaseMapLeft, mLlChangePos;
    private GraphicsLayer mGLayer = null;

    //    private TianDiTuLayer tdtVectorLayer,tdtVectorAnnotationLayer,tdtImageLayer,tdtImageAnnotationLayer;
    public LocationManager locManager;
    public String bestprovider;
    public LocationListener loclistener;
    private Point m_LoctionPt;

    private boolean isChangepos = false;
    public boolean isStartNavi = false;
    private String mKeyword = "";
    private ApiData ztsearchData = new ApiData(ApiData.HTTP_ID_searchzt);
    private ApiData ztSearchJyfwData = new ApiData(ApiData.HTTP_ID_searchzt_Jyfw);

    private ApiData mTaskCountData = new ApiData(ApiData.HTTP_ID_search_taskcount);
    private ApiData mTaskPageData = new ApiData(ApiData.HTTP_ID_search_task_bypage);
    private LinearLayout mLLGoBack;// 返回
    private ImageButton mImgBtnGoBack, mImgBtnSearch;
    private EditText mEditTextSearch;

    public LinearLayout mLlSearch;
    public TextView mTVList;
    public TextView mTVTitle;

    private MapMarkerTool mapMarkerTool;

    private String SDPath = ConstStrings.INI_PATH + "/" + ConstStrings.APPNAME + "/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workinmapshow);
        addToolBar(false);

        showProgressDialog("正在初始化地图，请稍后...");

        SDPath = CQDigitalUtil.getDataPath();
        mTaskCountData.setLoadingListener(this);
        mTaskPageData.setLoadingListener(this);
//        mChangeposData.setLoadingListener(this);
        ArcGISRuntime.setClientId("5SKIXc21JlankElJ");
        ArcGISRuntime.License.setLicense("runtimestandard,101,rux00000,none,XXXXXXX");

        mMapView = (MapView) findViewById(R.id.mapview);
        mMapView.setMapBackground(-1, -1, 0.0f, 0.0f);
        //设置地图边框范围
//        Envelope envelope = new Envelope();
//        envelope.setXMax(106.295325);
//        envelope.setYMax(26.525839);
//        envelope.setXMin(106.682293);
//        envelope.setYMin(26.175393);
//        mMapView.setExtent(envelope);
//        mMapView.setMaxExtent(envelope);
        locManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int mapType = sp.getInt("maptype", 2);
        switch (mapType) {
            case 0:// 公共服务平台地图
            {
                mVecLayer = new ArcGISTiledMapServiceLayer(ConstStrings.Map_Vec_Public);
                mMapView.addLayer(mVecLayer);
                mImgLayer = new ArcGISTiledMapServiceLayer(ConstStrings.Map_Img_Public);
                mMapView.addLayer(mImgLayer);
                mImgLayer.setVisible(false);
                mImgLabLayer = new ArcGISTiledMapServiceLayer(ConstStrings.Map_ImgLabel_public);
                mMapView.addLayer(mImgLabLayer);
                mImgLabLayer.setVisible(false);
            }
            break;
            case 1:// 离线地图
            {
                String ip1 = sp.getString("mapip1", ConstStrings.ip1);
                String ip2 = sp.getString("mapip2", ConstStrings.ip2);
                String ip3 = sp.getString("mapip3", ConstStrings.ip3);
                String ip4 = sp.getString("mapip4", ConstStrings.ip4);
                ConstStrings.setMapUrl("http://" + ip1 + "." + ip2 + "." + ip3 + "." + ip4 + ":" + ConstStrings.mapport, mapType);

                String localVectorDataPath = ConstStrings.INI_PATH
                        + "/"
                        + ConstStrings.APPNAME
                        + "/" + ConstStrings.VECTOR_DATA_NAME
                        + "/" + ConstStrings.VECTOR_DATA_NAME;
                ConstStrings.setMapUrl(localVectorDataPath, mapType);
                File file = new File(localVectorDataPath + "." + ConstStrings.VECTOR_DATA_TYPE);
                if (!file.exists()) {
                    showToast("离线地图未下载,请先进行下载");
                    startActivity(new Intent(this, SettingsMapActivity.class));
                    finish();
                }
                mVecLayer = new ArcGISLocalTiledLayer(localVectorDataPath + "." + ConstStrings.VECTOR_DATA_TYPE);
                mMapView.addLayer(mVecLayer);
                mVecLabLayer = new ArcGISTiledMapServiceLayer(ConstStrings.Map_VecLabel);
                mMapView.addLayer(mVecLabLayer);

                mImgLayer = new ArcGISLocalDataLayer(this, ConstStrings.Map_Img);
                mMapView.addLayer(mImgLayer);
                mImgLayer.setVisible(false);
                mImgLabLayer = new ArcGISLocalDataLayer(this, ConstStrings.Map_ImgLabel);
                mMapView.addLayer(mImgLabLayer);
                mImgLabLayer.setVisible(false);
            }
            break;
            case 2:// 在线地图
            {
//                String ip1 = sp.getString("mapip1", ConstStrings.ip1);
//                String ip2 = sp.getString("mapip2", ConstStrings.ip2);
//                String ip3 = sp.getString("mapip3", ConstStrings.ip3);
//                String ip4 = sp.getString("mapip4", ConstStrings.ip4);
//                ConstStrings.setMapUrl("http://" + ip1 + "." + ip2 + "." + ip3 + "." + ip4 + ":" + ConstStrings.mapport, mapType);

                TianDiTuLayer tianDiTuLayer = new TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_VECTOR_2000);
                mMapView.addLayer(tianDiTuLayer);

//                mVecLayer = new ArcGISTiledMapServiceLayer(ConstStrings.Map_Vec);
//                mVecLayer.setVisible(true);
//                mVecLabLayer = new ArcGISTiledMapServiceLayer(ConstStrings.Map_VecLabel);
//                mVecLabLayer.setVisible(true);
//                mMapView.addLayer(mVecLayer);
//                mMapView.addLayer(mVecLabLayer);
//
//                mImgLayer = new ArcGISLocalDataLayer(this, ConstStrings.Map_Img);
//                mMapView.addLayer(mImgLayer);
//                mImgLayer.setVisible(false);
//                mImgLabLayer = new ArcGISLocalDataLayer(this, ConstStrings.Map_ImgLabel);
//                mMapView.addLayer(mImgLabLayer);
//                mImgLabLayer.setVisible(false);
            }
            break;
            default:
                break;
        }
//        String mapIp = sp.getString("topicIp", ConstStrings.MAP_TOPIC);
//        SearcLayer mLayerTopic = new SearcLayer(mapIp);
//        mMapView.addLayer(mLayerTopic);
        mMapView.setOnStatusChangedListener(new OnStatusChangedListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void onStatusChanged(Object source, STATUS status) {
                if (status == STATUS.INITIALIZED || status == STATUS.LAYER_LOADED) {
                    dismissProgressDialog();
                    mapMarkerTool.showPointLocate();
                    LocationDisplayManager locationManager = mMapView.getLocationDisplayManager();
                    locationManager.setAllowNetworkLocation(true);
                    locationManager.setAutoPanMode(LocationDisplayManager.AutoPanMode.OFF);
                    locationManager.start();
                }
            }
        });
        mGLayer = new GraphicsLayer();
        mMapView.addLayer(mGLayer);

        mLlVec = (LinearLayout) findViewById(id.llbasemapvec);
        mLlVec.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (mVecLayer != null) {
                    mVecLayer.setVisible(true);
                }
                if (mVecLabLayer != null) {
                    mVecLabLayer.setVisible(true);
                }
                if (mImgLayer != null) {
                    mImgLayer.setVisible(false);
                }
                if (mImgLabLayer != null) {
                    mImgLabLayer.setVisible(false);
                }

//                tdtVectorLayer.setVisible(true);
//                tdtVectorAnnotationLayer.setVisible(true);
//                tdtImageLayer.setVisible(false);
//                tdtImageAnnotationLayer.setVisible(false);

                mMapView.refreshDrawableState();
                mLlBaseMapLeft.setVisibility(View.GONE);
            }
        });

        mLlImg = (LinearLayout) findViewById(R.id.llbasemapimg);
        mLlImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showImageLayer();
            }
        });

        mLlBaseMapLeft = (LinearLayout) findViewById(id.llbasemapleft);

        //位置纠正
        mLlChangePos = (LinearLayout) findViewById(R.id.changePos);
        mLlChangePos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.checkLocEnabled(WorkInMapShowActivity.this, locManager)) {
                    showProgressDialog("正在搜索您的位置");
                    isChangepos = true;
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                    locManager.requestLocationUpdates(bestprovider, 1000, 0.01f, loclistener);
                }
            }
        });

        mLlBasemap = (LinearLayout) findViewById(id.llbasemap);
        mLlBasemap.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (mLlBaseMapLeft.getVisibility() == View.VISIBLE) {
                    mLlBaseMapLeft.setVisibility(View.INVISIBLE);
                } else {
                    mLlBaseMapLeft.setVisibility(View.VISIBLE);
                }
                mMapView.refreshDrawableState();
            }
        });

        bestprovider = LocationManager.NETWORK_PROVIDER;

        // if (bestprovider == LocationManager.NETWORK_PROVIDER) {
        // 判断是否联网
        ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cwjManager.getActiveNetworkInfo();
        // if (!locManager.isProviderEnabled(bestprovider)) {
        if (info == null || !info.isAvailable()) {
            if (bestprovider.equalsIgnoreCase(LocationManager.NETWORK_PROVIDER)) {
                bestprovider = LocationManager.GPS_PROVIDER;

            }
        }
        // }

        loclistener = new LocationListener() {
            public void onLocationChanged(Location l) {
                dismissProgressDialog();

                Point ptLatLon = new Point(l.getLongitude(), l.getLatitude());
                mapMarkerTool.startLatitude = l.getLatitude();
                mapMarkerTool.startLongitude = l.getLongitude();
                SpatialReference sr4326 = SpatialReference.create(4326);
                Point pt = (Point) GeometryEngine.project(ptLatLon, sr4326, mMapView.getSpatialReference());

                if (pt != null) {
                    boolean bDrawLocation = false;
                    // 如果m_LoctionPt为空，说明为刚打开设备的状态，还未获取过位置信息
                    if (m_LoctionPt == null) {
                        m_LoctionPt = pt;
                        bDrawLocation = true;

                    } else {
                        double distance = GeometryEngine.distance(pt, m_LoctionPt, mMapView.getSpatialReference());
                        if (distance > ConstStrings.TolDistance) {
                            bDrawLocation = true;
                        }
                    }
                    // 纠正位置信息
                    if (isChangepos) {
                        isChangepos = false;
                        uploadPos(l);
                        mMapView.centerAt(m_LoctionPt, true);
                        return;
                    }
//                    if (isStartNavi) {
//                        dismissProgressDialog();
//                        isStartNavi = false;
//                        Intent intent = new Intent(mContext, AMapNaviActivity.class);
//                        intent.putExtra("startLatitude", l.getLatitude());
//                        intent.putExtra("startLongitude", l.getLongitude());
//                        intent.putExtra("endLatitude", mapMarkerTool.endLatitude);
//                        intent.putExtra("endLongitude", mapMarkerTool.endLongitude);
//                        mContext.startActivity(intent);
//                        mapMarkerTool.dialog.dismiss();
//                    }
                    if (bDrawLocation) {
//                        PictureMarkerSymbol locationPH = new PictureMarkerSymbol(ContextCompat.getDrawable(mContext, R.mipmap.location32));
//                        locationPH.setOffsetX(7.5f);
//                        locationPH.setOffsetY(-7.5f);
//                        SimpleFillSymbol locationSF = new SimpleFillSymbol(Color.BLUE);
//                        locationSF.setAlpha(50);
//                        Polygon polygon = new Polygon();
//                        getCircle(m_LoctionPt, 0.0005, polygon);

                        // 定位到地图上相应位置，如果为第一次定位，则缩放比例尺
                        boolean bFirstLoc = true;
                        int[] ids = mGLayer.getGraphicIDs();
                        if (ids != null && ids.length > 0) {
                            bFirstLoc = false;
                        }
                        mGLayer.removeAll();
//                        mGLayer.addGraphic(new Graphic(polygon, locationSF));
//                        mGLayer.addGraphic(new Graphic(m_LoctionPt, locationPH));

//                        if (bFirstLoc) {
//                            mMapView.centerAt(m_LoctionPt, true);
//                            mMapView.setScale(ConstStrings.LocationScale);
//                        } else {
//                            mMapView.centerAt(m_LoctionPt, true);
//                        }


//                        if (Double.valueOf(mMapView.getMaxScale())!=null){
//                            mMapView.setScale(mMapView.getMaxScale()-1);
//                        }else{
//                            mMapView.setScale(ConstStrings.LocationScale);
//                        }
                        mMapView.setScale(ConstStrings.LocationScale);
                        mMapView.centerAt(m_LoctionPt, true);
                    }
                }

            }

            public void onProviderDisabled(String provider) {
                dismissProgressDialog();
                showToast("定位失败");
            }

            public void onProviderEnabled(String provider) {
                dismissProgressDialog();
                showToast("定位失败，请重试");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
        };

        mLlLocation = (LinearLayout) findViewById(id.lllocation);
        mLlLocation.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                doLocation();
            }
        });

        initView();

        if (mMapView.isLoaded()) {
            mapMarkerTool.showPointLocate();
        }
    }

    public void doLocation() {
        if (Util.checkLocEnabled(WorkInMapShowActivity.this, locManager)) {
//            showToast("正在搜索您的位置");
            int locationPermission = ActivityCompat.checkSelfPermission(WorkInMapShowActivity.this, Manifest.permission.LOCATION_HARDWARE);
            Location l = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (l == null) {
                l = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            if (l != null) {
                Point ptLatLon = new Point(l.getLongitude(), l.getLatitude());
                SpatialReference sr4326 = SpatialReference.create(4326);
                m_LoctionPt = (Point) GeometryEngine.project(ptLatLon, sr4326, mMapView.getSpatialReference());

//                PictureMarkerSymbol locationPH = new PictureMarkerSymbol(ContextCompat.getDrawable(mContext, R.mipmap.my_location));
//                locationPH.setOffsetX(7.5f);
//                locationPH.setOffsetY(-7.5f);
//                SimpleFillSymbol locationSF = new SimpleFillSymbol(Color.BLUE);
//                locationSF.setAlpha(50);
//                Polygon polygon = new Polygon();
//                getCircle(m_LoctionPt, 0.0005, polygon);

                // 定位到地图上相应位置，如果为第一次定位，则缩放比例尺
                boolean bFirstLoc = true;
                int[] ids = mGLayer.getGraphicIDs();
                if (ids != null && ids.length > 0) {
                    bFirstLoc = false;
                }
                mGLayer.removeAll();
//                mGLayer.addGraphic(new Graphic(polygon, locationSF));
//                mGLayer.addGraphic(new Graphic(m_LoctionPt, locationPH));

//                            if (bFirstLoc) {
//                                mMapView.centerAt(m_LoctionPt, true);
//                                mMapView.setScale(ConstStrings.LocationScale);
//                            } else {
//                                mMapView.centerAt(m_LoctionPt, true);
//                            }

//                if (Double.valueOf(mMapView.getMaxScale())!=null){
//                    mMapView.setScale(mMapView.getMaxScale()-1);
//                }else{
//                    mMapView.setScale(ConstStrings.LocationScale);
//                }
                mMapView.setScale(ConstStrings.LocationScale);
                mMapView.centerAt(m_LoctionPt, true);


            }
            locManager.requestLocationUpdates(bestprovider, 1000, 5.0f, loclistener);
//            if (locationPermission == PackageManager.PERMISSION_GRANTED) {
//
//            } else {
//                ActivityCompat.requestPermissions(WorkInMapShowActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_FINE_LOCATION);
//            }


        }
    }

    private void showImageLayer() {
        if (mImgLayer != null) {
            mImgLayer.setVisible(true);
        }
        if (mImgLabLayer != null) {
            mImgLabLayer.setVisible(true);
        }
        if (mVecLayer != null) {
            mVecLayer.setVisible(false);
        }
        if (mVecLabLayer != null) {
            mVecLabLayer.setVisible(false);
        }

//                tdtVectorLayer.setVisible(false);
//                tdtVectorAnnotationLayer.setVisible(false);
//                tdtImageLayer.setVisible(true);
//                tdtImageAnnotationLayer.setVisible(true);

        mMapView.refreshDrawableState();
        mLlBaseMapLeft.setVisibility(View.GONE);
    }

    private void uploadPos(final Location l) {
        Util.showYesOrNoDialog(WorkInMapShowActivity.this, "位置纠正", "确定要纠正主体位置？\n经纬度:（" + l.getLatitude() + "," + l.getLongitude() + "）", "是", "否",
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        if (Util.dialog.isShowing()) {
                            Util.dialog.dismiss();
                        }

                        mapMarkerTool.upLoadPos(l);
                        //                        }
                    }
                }, null);
    }

    @SuppressWarnings("unchecked")
    private void initView() {
        mapMarkerTool = new MapMarkerTool(this, mMapView);

        mLlSearch = (LinearLayout) findViewById(id.workinmapshow_activity_searchview);
        mTVTitle = (TextView) findViewById(id.tv_workinmapshow_activity_title);
        mTVList = (TextView) findViewById(id.btn_list);
        mTVList.setOnClickListener(this);

        mapMarkerTool.initPoiViewPager();


        mImgBtnGoBack = (ImageButton) findViewById(id.activity_workshowinmap_header_back);
        mImgBtnGoBack.setOnClickListener(this);

        mLLGoBack = (LinearLayout) findViewById(id.activity_workshowinmap_ll_header_back);
        mLLGoBack.setOnClickListener(this);

        mImgBtnSearch = (ImageButton) findViewById(id.ibtn_search);
        mImgBtnSearch.setOnClickListener(this);

        mEditTextSearch = (EditText) findViewById(R.id.et_search);
        mEditTextSearch.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    Util.closeKeybord(mEditTextSearch, WorkInMapShowActivity.this);
                    mKeyword = mEditTextSearch.getText().toString().trim();
                    ztsearchData.loadData("1", "10", mKeyword, "", "", "", "", "");
                }
                return false;
            }
        });

        ztsearchData.setLoadingListener(this);
        ztSearchJyfwData.setLoadingListener(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (locManager != null && loclistener != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            locManager.removeUpdates(loclistener);
        }
    }

    // 画圈圈~
    private void getCircle(Point center, double radius, Polygon circle) {
        circle.setEmpty();
        Point[] points = getPoints(center, radius);
        circle.startPath(points[0]);
        for (int i = 1; i < points.length; i++)
            circle.lineTo(points[i]);
    }

    private Point[] getPoints(Point center, double radius) {
        Point[] points = new Point[80];
        double sin;
        double cos;
        double x;
        double y;
        for (double i = 0; i < 80; i++) {
            sin = Math.sin(Math.PI * 2 * i / 80);
            cos = Math.cos(Math.PI * 2 * i / 80);
            x = center.getX() + radius * sin;
            y = center.getY() + radius * cos;
            points[(int) i] = new Point(x, y);
        }
        return points;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.activity_workshowinmap_ll_header_back:
            case id.activity_workshowinmap_header_back:// 返回
                this.finish();
                break;
            case id.ibtn_search:// 主体查询
                // mProgressDialog.showMap();
                Util.closeKeybord(mEditTextSearch, this);
                mKeyword = mEditTextSearch.getText().toString().trim();
                ztsearchData.loadData("1", "10", mKeyword, "", "", "", "", "");
                break;
            case id.btn_list:
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_searchzt: {
                if (b.isSuccess()) {
                    Intent intent = new Intent(this, SearchZtListShowActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("keyword", mKeyword);
                    bundle.putSerializable("entity", (Serializable) b.getEntry());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Util.activity_In(this);
                }
            }
            break;

            case ApiData.HTTP_ID_search_taskcount:
                break;
            case ApiData.HTTP_ID_search_task_bypage: {
                if (b.isSuccess()) {
                    finish();
                } else {
                    Util.showToast(this, "查询任务列表失败");
                }
            }
            break;
            default:
                break;
        }
    }


    //回调函数，无论用户是否允许都会调用执行此方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    Location l = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (l == null) {
                        l = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                    if (l != null) {
                        Point ptLatLon = new Point(l.getLongitude(), l.getLatitude());
                        SpatialReference sr4326 = SpatialReference.create(4326);
                        m_LoctionPt = (Point) GeometryEngine.project(ptLatLon, sr4326, mMapView.getSpatialReference());

//                        PictureMarkerSymbol locationPH = new PictureMarkerSymbol(ContextCompat.getDrawable(mContext, R.mipmap.location32));
//                        locationPH.setOffsetX(7.5f);
//                        locationPH.setOffsetY(-7.5f);
//                        SimpleFillSymbol locationSF = new SimpleFillSymbol(Color.BLUE);
//                        locationSF.setAlpha(50);
//                        Polygon polygon = new Polygon();
//                        getCircle(m_LoctionPt, 0.0005, polygon);

                        // 定位到地图上相应位置，如果为第一次定位，则缩放比例尺
                        boolean bFirstLoc = true;
                        int[] ids = mGLayer.getGraphicIDs();
                        if (ids != null && ids.length > 0) {
                            bFirstLoc = false;
                        }
                        mGLayer.removeAll();
//                        mGLayer.addGraphic(new Graphic(polygon, locationSF));
//                        mGLayer.addGraphic(new Graphic(m_LoctionPt, locationPH));

//                        if (bFirstLoc) {
//                            mMapView.centerAt(m_LoctionPt, true);
//                            mMapView.setScale(ConstStrings.LocationScale);
//                        } else {
//                            mMapView.centerAt(m_LoctionPt, true);
//                        }

//                        if (Double.valueOf(mMapView.getMaxScale())!=null){
//                            mMapView.setScale(mMapView.getMaxScale()-1);
//                        }else{
//                            mMapView.setScale(ConstStrings.LocationScale);
//                        }
                        mMapView.setScale(ConstStrings.LocationScale);
                        mMapView.centerAt(m_LoctionPt, true);

                    }

                    locManager.requestLocationUpdates(bestprovider, 1000, 5.0f, loclistener);

                } else {
                    // Permission Denied 拒绝
                    Toast.makeText(this, "获取位置权限失败！", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
