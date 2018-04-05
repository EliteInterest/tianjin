package com.zx.tjmarketmobile.util;

public class ConstStrings {
    public static String INI_PATH = "";
    public static String APPNAME = "GAMarketMobile";
    public static final String INI_CRASH_PATH = "/" + APPNAME + "/CRASH/";
    public static String Map_Base = "http://42.123.116.156:6080";
    public static String Map_ImgBase = "http://42.123.116.156:6080";
    public static final String INI_SUBMIT_FILE_PATH = "/" + APPNAME + "/SUBMITFILE/";
    public static final String INI_CAMERA_FILE_PATH = "/" + APPNAME + "/CAMERA/";
    public static String VECTOR_DATA_NAME = "GAMarket";
    public static String Vec = "/arcgis/rest/services/GASCJG/GAVector/MapServer";
    public static String Img = "/arcgis/rest/services/GASCJG/GAImage/MapServer";
    public static String VecLabel = "/arcgis/rest/services/GASCJG/GAVector_Label/MapServer";
    public static String ImgLabel = "/arcgis/rest/services/GASCJG/GAImage_Label/MapServer";

    public static String Vec_Local = "/arcgis/rest/services/gascjg/GAVector/Layers";
    public static String VecLabel_Local = "/arcgis/rest/services/gascjg/GAVector_Label/Layers";
    public static String Img_Local = "/arcgis/rest/services/ljxyjg/CQImage/MapServer/Layers";
    public static String ImgLabel_Local = "/arcgis/rest/services/gascjg/GAVector_Label/Layers";

    public static String Map_Vec = "";
    public static String Map_Img = "";
    public static String Map_ImgLabel = "";
    public static String Map_VecLabel = "";

    public static String Map_Vec_Public = "http://www.digitalcq.com/RemoteRest/services/CQMap_VEC/MapServer";
    public static String Map_Img_Public = "http://www.digitalcq.com/RemoteRest/services/CQMap_IMG/MapServer";
    public static String Map_ImgLabel_public = "http://www.digitalcq.com/RemoteRest/services/CQMap_IMG_LABEL/MapServer";
    public static String MAP_TOPIC = "http://42.123.116.156:6080/arcgis/rest/services/GASCJG/gascjg/MapServer";
//	public static String MAP_TOPIC = "http://192.168.110.238:6080/arcgis/rest/services/sgs/MapServer";
//	public static String Map_Vec = "http://172.30.4.15:6080/arcgis/rest/services/LJ_Vector/MapServer";
//	public static String Map_Img = "http://172.30.4.15:6080/arcgis/rest/services/LJ_Image/MapServer";
//	public static String Map_ImgLabel = "http://172.30.4.15:6080/arcgis/rest/services/LJ_Image_Label/MapServer";

    public static void setMapUrl(String url, int maptype) {
        if (maptype == 1) {
            Map_Base = url;
            Map_Vec = Map_Base + Vec_Local;
            Map_VecLabel = Map_Base + VecLabel_Local;
            Map_Img = Map_Base + Img;
            Map_ImgLabel = Map_Base + ImgLabel;
        } else {
            Map_Base = url;
            Map_Vec = Map_Base + Vec;
            Map_VecLabel = Map_Base + VecLabel;
            Map_Img = Map_Base + Img;
            Map_ImgLabel = Map_Base + ImgLabel;
        }
    }

    public static String getDownloadPath() {
        String path = "";
        if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {
            path = ConstStrings.INI_PATH + "/" + ConstStrings.APPNAME + "/";
        } else {
            path = "/data/data/GAMarketMobile/";
        }
        return path;
    }

    public static void setLocalImgUrl(String url) {
        Map_ImgBase = url;
        Map_Img = Map_ImgBase + Img;
        Map_ImgLabel = Map_ImgLabel + ImgLabel;

    }


    //地图默认中心点
    public static double Longitude = 106.510745;
    public static double Latitude = 26.495940;
    //定位时地图比例尺
//	public static double LocationScale = 72142.9670553589;// 5025.584284067166
    public static double LocationScale = 10025.584284067166;// 10025.584284067166

    //两点之间距离容差（在此距离内可视为同一点）
    public static float TolDistance = 1.0f;

    public final static String[] SUPERVISESTATES = new String[]{"全部","已下发","已办结","已指派","待审核","审核未通过","待下发","待指派"};

    //调用地图窗体类型
    public static final int MapType_Main = 0;//从主页面点击打开
    public static final int MapType_SearchZt = 1;//从主体结果列表打开
    public static final int MapType_Task = 2;//从任务结果列表打开
    public static final int MapType_ZtDetail = 3;//从主体（单个）详情打开
    public static final int MapType_TaskDetail = 4;//从任务（单个）详情打开
    public static final int MapType_CaseDetail = 5;//从案件详情打开
    public static final int MapType_CompDetail = 6;//从投诉详情打开

    //任务执行
    public static final String Task_Status_ForExecute = "待处置";
    public static final String Task_Status_ForCommit = "待提交";
    public static final String Status_IsTrue = "经查属实:";
    public static final String Status_IsNotTrue = "经查不实:";
    public static final String Status_Complaint_Revert = "撤销:";
    public static final String Status_Complaint_Other_CODITION = "其他情况:";

    //消息类型
    public static final String Msg_Type_Event = "启动";
    public static final String Msg_Type_Event_Zl = "指令下发";
    public static final String Msg_Type_Complaint = "投诉举报任务指派";

    public static final int Request_Success = 1001;
    public static final int Request_Saved = 1002;

    public final static String ip1 = "42";
    public final static String ip2 = "123";
    public final static String ip3 = "116";
    public final static String ip4 = "156";
    public final static String ipport = "80";
    public final static String mapport = "6080";


    public final static String VECTOR_DATA_TYPE = "tpk";

}
