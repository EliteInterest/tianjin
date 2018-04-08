package com.zx.tjmarketmobile.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.FillSymbol;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.EntityPointBean;
import com.zx.tjmarketmobile.entity.supervise.MyTaskCheckEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.http.BaseRequestData;
import com.zx.tjmarketmobile.listener.OnMapDialogListener;
import com.zx.tjmarketmobile.manager.UserManager;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.GpsTool;
import com.zx.tjmarketmobile.util.Util;
import com.zx.tjmarketmobile.util.ZXDialogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiangb on 2017/12/19.
 * 功能：
 */

public class MapViewDialog extends AlertDialog {

    private MapView mapView;
    private Activity activity;
    private TextView tvTitle;
    private LinearLayout llAddPoint;
    private LinearLayout llChangePos;
    private MapType mapType;//类型
    private Map<String, Object> map;//参数集合
    private OnMapDialogListener mListener;//回调
    private GraphicsLayer markerLayer;//点图层
    private GraphicsLayer polygonLayer;//面图层
    private GraphicsLayer tapLyer;//点击图层
    private PictureMarkerSymbol picSymbol;//图标标记
    private Point tapPoint;//点击点
    private MyTaskCheckEntity mEntityBean;
    private List<EntityPointBean> pointList = new ArrayList<>();//点集合
    private Location myLocation;
    private ApiData mChangeposData = new ApiData(ApiData.HTTP_ID_change_pos);

    public enum MapType {
        selectPoint,//选点
        pointRange,//点范围
    }

    public MapViewDialog(@NonNull Activity activity, MapType mapType) {
        this(activity, mapType, null);
    }

    public MapViewDialog(@Nullable Activity activity, MapType mapType, OnMapDialogListener mapDialogListener) {
        super(activity);
        this.activity = activity;
        this.mapType = mapType;
        mListener = mapDialogListener;
        init();
    }

    private void init() {
        getWindow().setDimAmount(0);
        setCanceledOnTouchOutside(false);
        create();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_mapview);
        mapView = findViewById(R.id.dialog_mapview);
        llChangePos = findViewById(R.id.ll_dialog_changePos);
        tvTitle = findViewById(R.id.tv_dialog_title);
        llAddPoint = findViewById(R.id.ll_dialog_addPoint);
        initMap();
        findViewById(R.id.iv_dialog_close).setOnClickListener(view -> {
            mListener.onHide();
            hide();
        });
        findViewById(R.id.ll_dialog_location).setOnClickListener(view -> {
            doLocation(false);
        });
        llChangePos.setOnClickListener(v -> {
            try {

                if (myLocation == null) {
                    if (GpsTool.isOpen(activity)) {
                        myLocation = GpsTool.getGpsLocation(activity);
                    } else {
                        GpsTool.openGPS(activity);
                    }
                }
                Util.showYesOrNoDialog(activity, "提示", "确定要纠正主体位置？\n经纬度:（" + myLocation.getLatitude() + "," + myLocation.getLongitude() + "）", "是", "否", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserManager userManager = new UserManager();
//                        mChangeposData.loadData(mEntityBean.getFEntityGuid(), userManager.getUser(activity).getId(), myLocation.getLongitude(), myLocation.getLatitude(),
//                                mEntityBean.getFGuid() );
                    }
                }, null);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(activity, "获取主体位置失败", Toast.LENGTH_SHORT).show();
            }
        });
        llAddPoint.setOnClickListener(v -> {
            if (mListener == null) {
                return;
            }
            if (mapType == MapType.selectPoint) {
                mListener.selectPoint(tapPoint);
                mListener.onHide();

                hide();
            } else if (mapType == MapType.pointRange) {
                mListener.selectPoint(tapPoint);
                mListener.onHide();
                hide();
            }
        });
        if (mapType == MapType.selectPoint) {
            tvTitle.setText("点击地图进行选点");
        }
        doLocation(true);
        mChangeposData.setLoadingListener(new ChangePosListener());
    }

    private class ChangePosListener implements BaseRequestData.OnHttpLoadingListener<BaseHttpResult> {

        @Override
        public void onLoadStart(int id) {
            ZXDialogUtil.showLoadingDialog(activity, "正在加载，请稍后...");
        }

        @Override
        public void onLoadError(int id, boolean isAPIError, String error_message) {
            ZXDialogUtil.dismissDialog();
        }

        @Override
        public void onLoadComplete(int id, BaseHttpResult b) {
            ZXDialogUtil.dismissDialog();
            Toast.makeText(activity, "坐标纠正成功！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLoadPregress(int id, int progress) {

        }
    }

    private void doLocation(boolean isFirst) {
        if (GpsTool.isOpen(activity)) {
            myLocation = GpsTool.getGpsLocation(activity);
            if (myLocation != null) {
                if (isFirst) {
                    tapPoint = new Point(myLocation.getLongitude(), myLocation.getLatitude());
                    addTapMarker(tapPoint);
                }
                mapView.zoomTo(new Point(myLocation.getLongitude(), myLocation.getLatitude()), 20);
            }
        } else {
            GpsTool.openGPS(activity);
        }
    }

    @Override
    public void show() {
        super.show();
        markerLayer.removeAll();
        polygonLayer.removeAll();
        tapLyer.removeAll();
    }

    public void showMap(Map<String, Object> map) {
        super.show();
        markerLayer.removeAll();
        polygonLayer.removeAll();
        tapLyer.removeAll();

        this.map = map;
        if (map.containsKey("entity")) {
            llChangePos.setVisibility(View.VISIBLE);
//            mEntityBean = (MyTaskCheckEntity.RowsBean) map.get("entity");
        } else {
            llChangePos.setVisibility(View.GONE);
        }
        loadMap();
    }

    @SuppressWarnings("unchecked")
    private void loadMap() {
        if (mapView.isLoaded() && map != null) {
            if (mapType == MapType.selectPoint) {
                if (map.containsKey("oldLocation")) {
                    Point point = (Point) map.get("oldLocation");
                    if (point.getX() == 0 && point.getY() == 0) {
                        doLocation(false);
                        return;
                    }
                    mapView.zoomTo(point, 20);
                    addmarker(point);
                }
            } else if (mapType == MapType.pointRange) {
                if (map.containsKey("locations")) {
                    List<EntityPointBean> list = (List<EntityPointBean>) map.get("locations");
                    pointList.clear();
                    pointList.addAll(list);
                    doLocation(true);
                    for (int i = 0; i < pointList.size(); i++) {
                        if (pointList.get(i) != null && pointList.get(i).getFLatitude() != null && pointList.get(i).getFLatitude().length() > 0
                                && pointList.get(i).getFLongitude() != null && pointList.get(i).getFLongitude().length() > 0) {
                            Point point = new Point(Double.parseDouble(pointList.get(i).getFLongitude()), Double.parseDouble(pointList.get(i).getFLatitude()));
                            addRange(point);
                            addmarker(point);
                        }
                    }
                }
            }
        }
    }

    private void initMap() {
        ArcGISRuntime.setClientId("5SKIXc21JlankElJ");
        ArcGISRuntime.License.setLicense("runtimestandard,101,rux00000,none,XXXXXXX");
        mapView.setMapBackground(-1, -1, 0.0f, 0.0f);
        //设置地图边框范围
        Envelope envelope = new Envelope();
        envelope.setXMax(106.295325);
        envelope.setYMax(26.525839);
        envelope.setXMin(106.682293);
        envelope.setYMin(26.175393);
        mapView.setExtent(envelope);
        mapView.addLayer(new ArcGISTiledMapServiceLayer("http://42.123.116.156:6080/arcgis/rest/services/GASCJG/GAVector/MapServer"));
        mapView.addLayer(new ArcGISTiledMapServiceLayer("http://42.123.116.156:6080/arcgis/rest/services/GASCJG/GAVector_Label/MapServer"));
        picSymbol = new PictureMarkerSymbol(ContextCompat.getDrawable(activity, R.mipmap.tjd1));
        picSymbol.setOffsetX(0f);
        picSymbol.setOffsetY(8f);
        String mapIp = PreferenceManager.getDefaultSharedPreferences(activity).getString("topicIp", ConstStrings.MAP_TOPIC);
        SearcLayer mLayerTopic = new SearcLayer(mapIp);
        mapView.addLayer(mLayerTopic);
        markerLayer = new GraphicsLayer();
        polygonLayer = new GraphicsLayer();
        tapLyer = new GraphicsLayer();
        mapView.addLayer(polygonLayer);
        mapView.addLayer(markerLayer);
        mapView.addLayer(tapLyer);
        mapView.setOnStatusChangedListener((OnStatusChangedListener) (o, status) -> {
            if (status == OnStatusChangedListener.STATUS.INITIALIZED || status == OnStatusChangedListener.STATUS.LAYER_LOADED) {
                loadMap();
//                doLocation();
                LocationDisplayManager locationManager = mapView.getLocationDisplayManager();
                locationManager.setAllowNetworkLocation(true);
                locationManager.setAutoPanMode(LocationDisplayManager.AutoPanMode.OFF);
                locationManager.start();
            }
        });
        mapView.setOnSingleTapListener((OnSingleTapListener) (x, y) -> {
//            llAddPoint.setVisibility(View.VISIBLE);
            tapPoint = mapView.toMapPoint(x, y);
            addTapMarker(tapPoint);
        });
    }

    private void addTapMarker(Point point) {
        SpatialReference sr4326 = SpatialReference.create(4490);
        Point mappt = (Point) GeometryEngine.project(point, sr4326, mapView.getSpatialReference());
        tapLyer.removeAll();
        PictureMarkerSymbol tapSymbol = new PictureMarkerSymbol(ContextCompat.getDrawable(activity, R.mipmap.tap_marker));
        tapSymbol.setOffsetX(0f);
        tapSymbol.setOffsetY(8f);
        tapLyer.addGraphic(new Graphic(mappt, tapSymbol));
    }

    //添加标记
    private void addmarker(Point point) {
        SpatialReference sr4326 = SpatialReference.create(4490);
        Point mappt = (Point) GeometryEngine.project(point, sr4326, mapView.getSpatialReference());
        markerLayer.addGraphic(new Graphic(mappt, picSymbol));
    }

    //添加范围
    private void addRange(Point point) {
        Polygon polygon = new Polygon();
        getCircle(point, polygon);
        FillSymbol fillSymbol = new SimpleFillSymbol(Color.BLUE);
        fillSymbol.setAlpha(1);
        SimpleLineSymbol lineSymbol = new SimpleLineSymbol(Color.RED, 0.2f, SimpleLineSymbol.STYLE.SOLID);
        fillSymbol.setOutline(lineSymbol);
        polygonLayer.addGraphic(new Graphic(polygon, fillSymbol));
    }

    private void getCircle(Point center, Polygon circle) {
        circle.setEmpty();
        Point[] points = getPoints(center);
        circle.startPath(points[0]);
        for (int i = 1; i < points.length; i++) {
            circle.lineTo(points[i]);
        }
    }

    private static Point[] getPoints(Point center) {
        Point[] points = new Point[80];
        double sin;
        double cos;
        double x;
        double y;
        for (double i = 0; i < 80; i++) {
            sin = Math.sin(Math.PI * 2 * i / 80);
            cos = Math.cos(Math.PI * 2 * i / 80);
            x = center.getX() + 0.0027 * sin;
            y = center.getY() + 0.0027 * cos;
            points[(int) i] = new Point(x, y);
        }
        return points;
    }

    @Override
    public void onBackPressed() {
        hide();
        mListener.onHide();
    }

}