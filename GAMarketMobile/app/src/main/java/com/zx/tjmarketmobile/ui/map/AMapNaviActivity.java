//package com.zx.tjmarketmobile.ui.map;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Window;
//import android.widget.Toast;
//
//import com.amap.api.maps.CameraUpdateFactory;
//import com.amap.api.maps.model.LatLng;
//import com.amap.api.navi.AMapNavi;
//import com.amap.api.navi.AMapNaviListener;
//import com.amap.api.navi.AMapNaviView;
//import com.amap.api.navi.AMapNaviViewListener;
//import com.amap.api.navi.enums.NaviType;
//import com.amap.api.navi.enums.PathPlanningStrategy;
//import com.amap.api.navi.model.AMapLaneInfo;
//import com.amap.api.navi.model.AMapModelCross;
//import com.amap.api.navi.model.AMapNaviCameraInfo;
//import com.amap.api.navi.model.AMapNaviCross;
//import com.amap.api.navi.model.AMapNaviInfo;
//import com.amap.api.navi.model.AMapNaviLocation;
//import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
//import com.amap.api.navi.model.AMapServiceAreaInfo;
//import com.amap.api.navi.model.AimLessModeCongestionInfo;
//import com.amap.api.navi.model.AimLessModeStat;
//import com.amap.api.navi.model.NaviInfo;
//import com.amap.api.navi.model.NaviLatLng;
//import com.autonavi.tbt.TrafficFacilityInfo;
//import com.zx.tjmarketmobile.R;
//import com.zx.tjmarketmobile.entity.CarLatLngBean;
//import com.zx.tjmarketmobile.util.TTSController;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 导航界面
// */
//@SuppressWarnings("deprecation")
//public class AMapNaviActivity extends Activity implements AMapNaviListener, AMapNaviViewListener {
//
//    private NaviLatLng mStartLatlng;
//    private NaviLatLng mEndLatlng;
//    private AMapNaviView mAMapNaviView;
//    private AMapNavi mAMapNavi;
//    private TTSController mTtsManager;//语音
//    private List<NaviLatLng> mStartList = new ArrayList<>();
//    private List<NaviLatLng> mEndList = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_amap_navi);
//        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
//        mAMapNaviView.onCreate(savedInstanceState);
//        mAMapNaviView.setAMapNaviViewListener(this);
//        mAMapNaviView.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.407363, 106.441222), 20));
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        double startLat = bundle.getDouble("startLatitude");
//        double startLag = bundle.getDouble("startLongitude");
//        double endLat = bundle.getDouble("endLatitude");
//        double endLag = bundle.getDouble("endLongitude");
//        mStartLatlng = new NaviLatLng(startLat, startLag);
//        mEndLatlng = new NaviLatLng(endLat, endLag);
//        Log.e(">>>>>>.", "" + mStartLatlng);
//        Log.e("mEndLatlng", "" + mEndLatlng);
//
//        //为了尽最大可能避免内存泄露问题，建议这么写
//        mTtsManager = TTSController.getInstance(getApplicationContext());
//        mTtsManager.init();
//
//        //为了尽最大可能避免内存泄露问题，建议这么写
//        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
//        Log.e("mAMapNavi", "" + mAMapNavi);
//        mAMapNavi.addAMapNaviListener(this);
//        mAMapNavi.addAMapNaviListener(mTtsManager);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mAMapNaviView.onResume();
//        mStartList.add(mStartLatlng);
//        mEndList.add(mEndLatlng);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mAMapNaviView.onPause();
//
////        仅仅是停止你当前在说的这句话，一会到新的路口还是会再说的
//        mTtsManager.stopSpeaking();
////
////        停止导航之后，会触及底层stop，然后就不会再有回调了，但是讯飞当前还是没有说完的半句话还是会说完
////        mAMapNavi.stopNavi();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mAMapNaviView.onDestroy();
//        //since 1.6.0 不再在naviview destroy的时候自动执行AMapNavi.stopNavi();
//        //请自行执行
//        mAMapNavi.stopNavi();
//        mAMapNavi.destroy();
//        mTtsManager.destroy();
//    }
//
//    @Override
//    public void onInitNaviFailure() {
//        Toast.makeText(this, "init navi Failed", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onInitNaviSuccess() {
//        mAMapNavi.calculateDriveRoute(mStartList, mEndList, PathPlanningStrategy.DRIVING_DEFAULT);
//    }
//
//    @Override
//    public void onStartNavi(int type) {
//
//    }
//
//    @Override
//    public void onTrafficStatusUpdate() {
//
//    }
//
//    @Override
//    public void onLocationChange(AMapNaviLocation location) {
//
//    }
//
//    @Override
//    public void onGetNavigationText(int type, String text) {
//
//    }
//
//    @Override
//    public void onGetNavigationText(String s) {
//
//    }
//
//    @Override
//    public void onEndEmulatorNavi() {
//    }
//
//    @Override
//    public void onArriveDestination() {
//    }
//
//    @Override
//    public void onCalculateRouteFailure(int errorInfo) {
//        Toast.makeText(this, "errorInfo" + errorInfo, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onReCalculateRouteForYaw() {
//
//    }
//
//    @Override
//    public void onReCalculateRouteForTrafficJam() {
//
//    }
//
//    @Override
//    public void onArrivedWayPoint(int wayID) {
//
//    }
//
//    @Override
//    public void onGpsOpenStatus(boolean enabled) {
//    }
//
//    @Override
//    public void onNaviSetting() {
//    }
//
//    @Override
//    public void onNaviMapMode(int isLock) {
//
//    }
//
//    @Override
//    public void onNaviCancel() {
//        finish();
//    }
//
//
//    @Override
//    public void onNaviTurnClick() {
//
//    }
//
//    @Override
//    public void onNextRoadClick() {
//
//    }
//
//
//    @Override
//    public void onScanViewButtonClick() {
//    }
//
//    @Override
//    public void onNaviInfoUpdated(AMapNaviInfo naviInfo) {
//
//    }
//
//    @Override
//    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {
//
//    }
//
//    @Override
//    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {
//
//    }
//
//    @Override
//    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {
//
//    }
//
//    private ArrayList<CarLatLngBean> list = new ArrayList<>();
//
//    @Override
//    public void onNaviInfoUpdate(NaviInfo naviinfo) {
//        CarLatLngBean bean = new CarLatLngBean();
//        bean.setLatitude(naviinfo.getCoord().getLatitude());
//        bean.setLongitude(naviinfo.getCoord().getLongitude());
//        list.add(bean);
////        MainActivity.instance.initRoadData(list);
////        if (list.size() > 2) {
////            MainActivity.instance.moveLooper();
////        } else {
////            return;
////        }
//    }
//
//    @Override
//    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {
//
//    }
//
//    @Override
//    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
//
//    }
//
//    @Override
//    public void showCross(AMapNaviCross aMapNaviCross) {
//    }
//
//    @Override
//    public void hideCross() {
//    }
//
//    @Override
//    public void showModeCross(AMapModelCross aMapModelCross) {
//
//    }
//
//    @Override
//    public void hideModeCross() {
//
//    }
//
//    @Override
//    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo, byte[] laneRecommendedInfo) {
//
//    }
//
//    @Override
//    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {
//
//    }
//
//    @Override
//    public void hideLaneInfo() {
//
//    }
//
//    @Override
//    public void onCalculateRouteSuccess(int[] ints) {
//        mAMapNavi.startNavi(NaviType.GPS);
//    }
//
//    @Override
//    public void notifyParallelRoad(int i) {
//
//    }
//
//    @Override
//    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
//
//    }
//
//    @Override
//    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
//
//    }
//
//    @Override
//    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
//
//    }
//
//    @Override
//    public void onPlayRing(int i) {
//
//    }
//
//    @Override
//    public void onLockMap(boolean isLock) {
//    }
//
//    @Override
//    public void onNaviViewLoaded() {
//
//    }
//
//    @Override
//    public boolean onNaviBackClick() {
//        return false;
//    }
//}
