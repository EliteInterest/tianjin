package com.zx.tjmarketmobile.http;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.zx.tjmarketmobile.entity.CaseDetailEntity;
import com.zx.tjmarketmobile.entity.CaseFlowEntity;
import com.zx.tjmarketmobile.entity.CaseInfoEntity;
import com.zx.tjmarketmobile.entity.CaseRefeEntity;
import com.zx.tjmarketmobile.entity.CheckInfo;
import com.zx.tjmarketmobile.entity.CheckItemInfoEntity;
import com.zx.tjmarketmobile.entity.CompFlowEntity;
import com.zx.tjmarketmobile.entity.ComplainInfoDetailsBean;
import com.zx.tjmarketmobile.entity.ComplainInfoEntity;
import com.zx.tjmarketmobile.entity.DeviceEmergencyDetialEntity;
import com.zx.tjmarketmobile.entity.DeviceSecurityRiskEntity;
import com.zx.tjmarketmobile.entity.EmergencyInfo;
import com.zx.tjmarketmobile.entity.EmergencyListInfo;
import com.zx.tjmarketmobile.entity.EntityDetail;
import com.zx.tjmarketmobile.entity.EntityListInfo;
import com.zx.tjmarketmobile.entity.EntityPictureBean;
import com.zx.tjmarketmobile.entity.EntityPointBean;
import com.zx.tjmarketmobile.entity.EntitySimpleInfo;
import com.zx.tjmarketmobile.entity.EquipmentInfoEntity;
import com.zx.tjmarketmobile.entity.HttpLoginEntity;
import com.zx.tjmarketmobile.entity.HttpMonitor;
import com.zx.tjmarketmobile.entity.HttpMonitorEntityList;
import com.zx.tjmarketmobile.entity.HttpMonitorTaskList;
import com.zx.tjmarketmobile.entity.HttpMsgListEntity;
import com.zx.tjmarketmobile.entity.HttpSearchZtEntity;
import com.zx.tjmarketmobile.entity.HttpTaskCountEntity;
import com.zx.tjmarketmobile.entity.HttpTaskEntity;
import com.zx.tjmarketmobile.entity.HttpTaskListEntity;
import com.zx.tjmarketmobile.entity.HttpUpdateEntity;
import com.zx.tjmarketmobile.entity.HttpZtEntity;
import com.zx.tjmarketmobile.entity.ImageEntity;
import com.zx.tjmarketmobile.entity.KeyValueInfo;
import com.zx.tjmarketmobile.entity.LegalEntity;
import com.zx.tjmarketmobile.entity.LocationEntity;
import com.zx.tjmarketmobile.entity.MsgEntity;
import com.zx.tjmarketmobile.entity.NormalListEntity;
import com.zx.tjmarketmobile.entity.PermitEmergencyDetialEntity;
import com.zx.tjmarketmobile.entity.QualitySampleEntity;
import com.zx.tjmarketmobile.entity.SecurityflawEntity;
import com.zx.tjmarketmobile.entity.SelectPopDataList;
import com.zx.tjmarketmobile.entity.StatisticsNum;
import com.zx.tjmarketmobile.entity.SuperviseCountInfo;
import com.zx.tjmarketmobile.entity.SuperviseDetailInfo;
import com.zx.tjmarketmobile.entity.SuperviseEntity;
import com.zx.tjmarketmobile.entity.SuperviseInfo;
import com.zx.tjmarketmobile.entity.SynergyDTInfoEntity;
import com.zx.tjmarketmobile.entity.SynergyDetailEntity;
import com.zx.tjmarketmobile.entity.SynergyInfoBean;
import com.zx.tjmarketmobile.entity.TaskCountInfo;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerBiaozhun;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerBiaozhunDetail;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerDevice;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerDeviceDetail;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerLicense;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerLicenseDetail;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerLicenseFood;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerMeasureDetail;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerMeasureLiebiao;
import com.zx.tjmarketmobile.entity.supervise.MonitorPrecessCountEntity;
import com.zx.tjmarketmobile.entity.supervise.MyTaskCheckEntity;
import com.zx.tjmarketmobile.entity.supervise.MyTaskCheckResultEntity;
import com.zx.tjmarketmobile.entity.supervise.MyTaskCheckResultInfoEntity;
import com.zx.tjmarketmobile.entity.supervise.MyTaskFlow;
import com.zx.tjmarketmobile.entity.supervise.MyTaskInfoEntity;
import com.zx.tjmarketmobile.entity.supervise.MyTaskListEntity;
import com.zx.tjmarketmobile.entity.supervise.MyTaskPageEntity;
import com.zx.tjmarketmobile.entity.supervise.MyTaskProcessEntity;
import com.zx.tjmarketmobile.entity.supervise.SuperviseEquimentEntity;
import com.zx.tjmarketmobile.http.BaseHttpParams.HTTP_MOTHOD;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.LogUtil;
import com.zx.tjmarketmobile.util.MD5Util;
import com.zx.tjmarketmobile.view.ZXExpandBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网络请求统一处理
 *
 * @author zx-wt
 */
public class ApiData extends BaseRequestData<Object, Object, BaseHttpResult> {
    public static final boolean ISRELEASE = false;//是否正式环境
    public static final String LOCAL_HOST = "wenzhe.nat300.top";
    public static final String LOCAL_HOST_TAG = "TJSSO";
    public static final String LOCAL_HOST_CASE = "TJCase";
    /**
     * 登录页接口 [无参数 ]
     */
    public static final int HTTP_ID_getcover = 1;
    /**
     * 欢迎页接口 [0]用户名 [1]密码
     */
    public static final int HTTP_ID_login = 2;
    public static final int HTTP_ID_searchzt = 3;// 主体查询
    public static final int HTTP_ID_searchzt_Jyfw = 4;// 主体经营范围查询
    public static final int HTTP_ID_search_taskcount = 5;// 任务数量查询
    public static final int HTTP_ID_search_task_bypage = 6;// 任务列表查询
    public static final int HTTP_ID_searchtask_detail = 7;// 任务详情查询
    public static final int HTTP_ID_task_execute = 8;// 任务执行
    public static final int HTTP_ID_task_commit = 9;// 任务(监管、投诉)提交
    public static final int HTTP_ID_task_check_Save = 10;// 监管任务保存
    public static final int HTTP_ID_msg_notread_count = 11;// 获取未读消息数
    public static final int HTTP_ID_msg_center = 12;// 消息中心记录查询
    public static final int HTTP_ID_searchevent_detail = 13;// 预警任务详情查询
    public static final int HTTP_ID_searchzt_detail = 14;// 主体详情查询
    public static final int HTTP_ID_event_response = 15;// 应急响应
    public static final int HTTP_ID_event_checkin = 16;// 应急签到
    public static final int HTTP_ID_event_report = 17;// 应急上报
    public static final int HTTP_ID_set_msg_read = 18;// 设置消息为已读
    public static final int HTTP_ID_change_pos = 19;// 纠正主体位置
    public static final int HTTP_ID_complain_taskcount = 20;
    public static final int HTTP_ID_message_send = 30;
    public static final int HTTP_ID_version_update = 31;// 版本更新
    public static final int HTTP_ID_user_station = 32;// 根据部门查询人员
    public static final int HTTP_ID_supervisetask_list = 34;// 监管任务-任务列表
    public static final int HTTP_ID_supervisetask_detail = 35;// 监管任务-任务详情
    public static final int HTTP_ID_entity_detail = 36;// 主体详情查询
    public static final int HTTP_ID_entity_modifycontactinfo = 37;// 修改主体联系方式
    public static final int HTTP_ID_supervisetask_searchcheck = 38;// 监管任务-查询检查指标
    public static final int HTTP_ID_supervisetask_save = 39;// 监管任务-保存处置结果
    public static final int HTTP_ID_supervisetask_submit = 40;// 监管任务-提交处置结果
    public static final int HTTP_ID_supervisetasktype_list = 41;// 监管任务-任务类别列表
    public static final int HTTP_ID_supervisetasktype_count = 42;// 监管任务-任务类别数量查询
    public static final int HTTP_ID_entity_list = 43;// 监管任务-查看主体列表
    public static final int HTTP_ID_superviseoperate_detail = 44;// 监管任务-所长操作任务详情
    public static final int HTTP_ID_superviseoperate_dispose = 45;// 监管任务-退回或移交支队
    public static final int HTTP_ID_supervisetasktype_end = 46;// 监管任务-办结
    public static final int HTTP_ID_supervisetask_statusupdate = 47;// 监管任务-更新任务状态（待初审,待核审,待终审）
    public static final int HTTP_ID_supervisetask_queryuserentity = 48;// 监管任务-按照任务状态获取用户任务主体列表
    public static final int HTTP_ID_supervisetask_queryallentity = 49;// 监管任务-根据指定任务和任务状态查询所有的主体信息
    public static final int HTTP_ID_supervisetasktype_reviewlist = 50;// 监管任务-根据登录用户获取核审、终审任务列表
    public static final int HTTP_ID_supervisetask_monitorlist = 51;// 监管任务-任务监控查询不同状态下主体数量
    public static final int HTTP_ID_supervisetask_querystatusentity = 52;// 监管任务-任务监控查询不同状态下主体信息
    public static final int HTTP_ID_loginOut = 53;
    public static final int HTTP_ID_getCheckInfos = 55;// 预警任务-任务列表
    public static final int HTTP_ID_statistics_single_parameter = 56;// 统计分析-获取统计数量（返回一个参数)
    public static final int HTTP_ID_statistics_four_parameter = 57;// 统计分析-获取统计参数)数量（返回四个
    public static final int HTTP_ID_security_flaws_parameter = 58;
    public static final int HTTP_ID_drug_sample_parameter = 59;
    public static final int HTTP_ID_industrial_product_parameter = 60;
    public static final int HTTP_ID_food_sample_parameter = 61;
    public static final int HTTP_ID_food_fast_sample_parameter = 62;
    public static final int HTTP_ID_getDeviceEmergencyListInfos = 63;
    public static final int HTTP_ID_getMaintenanceContractDataListInfos = 64;
    public static final int HTTP_ID_getSafeFileListInfos = 65;
    public static final int HTTP_ID_getEmergency_detail = 66;
    public static final int HTTP_ID_getLicYjEmergencyListInfos = 67;
    public static final int HTTP_ID_getUsersByDept = 68;// 根据科室名
    public static final int HTTP_ID_getUserNameByArea = 69;// 根据所名查询负责人名称
    public static final int HTTP_ID_getPermit_Emergency_detail = 70;
    public static final int HTTP_ID_submitLocation = 71;//提交坐标
    public static final int HTTP_ID_countEntityType = 72;//主体类别查询
    public static final int HTTP_ID_psw_update = 73;//密码修改
    public static final int HTTP_ID_doClaimed = 74;//主体认领
    public static final int HTTP_ID_searchall = 75;//搜索全部
    public static final int HTTP_ID_device_security_risk_parameter = 76;//特种设备隐患
    public static final int HTTP_ID_deleteFileInfo = 77;
    public static final int HTTP_ID_savaOfficialPicture = 78;
    public static final int HTTP_ID_queryLocationData = 79;
    public static final int HTTP_ID_casePageAyxx = 80;//案件执法-信息查询
    public static final int HTTP_ID_caseTaskPage = 81;//案件执法-待办查询
    public static final int HTTP_ID_caseGetAyxxDetailById = 82;//案件执法-查询详细信息
    public static final int HTTP_ID_caseGetAyLcgjPageInfo = 83;//案件执法-流程轨迹
    public static final int HTTP_ID_caseGetNextPerson = 84;//案件执法-获取流程处理人
    public static final int HTTP_ID_caseDoExcute = 85;//案件执法-处理
    public static final int HTTP_ID_caseTaskHisPage = 86;//案件执法-已办查询
    public static final int HTTP_ID_caseDoDelay = 87;//案件延期-处理
    public static final int HTTP_ID_caseDoDestory = 88;//案件销案-处理
    public static final int HTTP_ID_caseDoTransfer = 89;//案件移送-处理
    public static final int HTTP_ID_caseDoForce = 90;//强制措施-处理
    public static final int HTTP_ID_caseGetMonitor = 91;//案件执法-监控
    public static final int HTTP_ID_casePageLcjk = 92;//案件列表-流程监控
    public static final int HTTP_ID_caseSaveAyfj = 93;//保存图片信息
    public static final int HTTP_ID_compMonitor = 95;//投诉举报-流程监控
    public static final int HTTP_ID_compPageQuery = 96;//投诉举报-查询
    public static final int HTTP_ID_compInfoById = 97;//投诉举报-详情
    public static final int HTTP_ID_compLcgj = 98;//投诉举报-处置动态
    public static final int HTTP_ID_compTaskPage = 99;//投诉举报-待办已办
    public static final int HTTP_ID_getAllUserDept = 100;//获取科室名称
    public static final int HTTP_ID_compFlowhandle = 101;//投诉举报-处理
    public static final int HTTP_ID_compLcjk = 102;//投诉举报流程监控

    public static final int HTTP_ID_SuperviseTaskHisPage = 103;//协同监管-我的任务-我的已办
    public static final int HTTP_ID_superviseTaskBaseInfo = 104;//协同监管-我的任务-基本信息
    public static final int HTTP_ID_superviseGetAyLcgjPageInfo = 105;//协同监管-流程轨迹
    public static final int HTTP_ID_SuperviseTaskCheckEntity = 106;//协同监管-检查主体
    public static final int HTTP_ID_Supervise_MyTask_sendTask = 107;//协同监管-任务处理-待下发
    public static final int HTTP_ID_Supervise_MyTask_getProcessingPeople = 108;//协同监管-任务处理-待下发-获取当前用户的所在部门的下属
    public static final int HTTP_ID_Supervise_MyTask_getSubDirector = 109;//协同监管-任务处理-待下发-获取分局长
    public static final int HTTP_ID_Supervise_MyTask_examineTaskInfo = 110;//协同监管-任务处理-审核任务
    public static final int HTTP_ID_Supervise_MyTask_assignTaskInfo = 111;//协同监管-任务处理-任务指派
    public static final int HTTP_ID_Supervise_MyTask_reviewTask = 112;//协同监管-任务处理-待核审
    public static final int HTTP_ID_Supervise_MyTask_executeMyTask = 113;//协同监管-任务处理-检查主体-获取初始处置信息
    public static final int HTTP_ID_Supervise_MyTask_disposalTask = 114;//协同监管-任务处理-检查主体-提交处置结果
    public static final int HTTP_ID_Supervise_getThisTaskPackageTask = 115;//协同监管-任务处理-打包任务
    public static final int HTTP_ID_supervisetask_getMonitorTask = 116;//协同监管-监管流程-信息列表
    public static final int HTTP_ID_supervise_countMonitorTask = 117;//协同监管-监管流程-分类数量
    public static final int HTTP_ID_Supervise_MyTask_feedbackTask = 118;//协同监管-任务处理-反馈任务
    public static final int HTTP_ID_SuperviseTaskPage = 119;//协同监管-我的任务-我的待办

    public static final int HTTP_ID_Case_Delete_Img = 120;//删除图片
    public static final int HTTP_ID_statistics_hzp = 121;//化妆品统计
    public static final int HTTP_ID_getStatisticsList = 122;//统计钻取

    public static final int HTTP_ID_Supervise_MyTask_getCheckResult = 123;//获取提交的意见和结论
    public static final int HTTP_ID_SuperviseMyTaskCheckEntity = 124;//我的待办跳转到检查主体后调用的接口
    public static final int HTTP_ID_superviseIsCanFinishInfo = 125;//监管任务-我的待办-判断任务是否可以退回的接口
    public static final int HTTP_ID_superviseFinishTask = 126;//监管任务-我的待办-
    public static final int HTTP_ID_superviseGetTaskImg = 128;//获取图片
    public static final int HTTP_ID_superviseAppendEntity = 129;//添加主体
    public static final int HTTP_ID_superviseQueryEntityByCondition = 130;//根据区县查询主体
    public static final int HTTP_ID_caseSelectDiscretionStandard = 131;//案件-依据查询
    public static final int HTTP_ID_superviseGetEntityPoint = 132;//获取任务坐标点
    public static final int HTTP_ID_superviseGetDTByCondition = 133;//获取特种设备列表
    public static final int HTTP_ID_superviseUpdateDTPosition = 134;//更新特种设备坐标
    public static final int HTTP_ID_superviceAppendEntityPoint = 135;//追加主体任务点
    public static final int HTTP_ID_synergyCountCheckInfo = 136;//统计协同监管审核和未审核/已阅和未阅
    public static final int HTTP_ID_synergyGetCountCheckInfoItem = 137;//获取审核与未审核/已阅和未阅的主体名称和条数
    public static final int HTTP_ID_synergyGetCheckInfoItems = 138;//获取审核和未审核/已阅与未阅某个主体具体的几条
    public static final int HTTP_ID_synergyGetDetailInfo = 139;//获取普通类型具体的信息
    public static final int HTTP_ID_synergyGetDTDetailInfo = 140;//获取电梯具体的信息
    public static final int HTTP_ID_synergyGetYJDetailInfo = 141;//获取预警具体的信息
    public static final int HTTP_ID_synergyGetEquipment1 = 142;//获取预警设备信息
    public static final int HTTP_ID_equipSearchList = 143;//特种设备获取查询列表
    public static final int HTTP_ID_getPicByEntityId = 144;//特种设备获取查询列表

    public static final int HTTP_ID_statistics_case_queryDep = 201;//统计-案件-根据部门统计
    public static final int HTTP_ID_statistics_case_queryType = 202;//统计-案件-来源统计
    public static final int HTTP_ID_statistics_case_queryIsCase = 203;//统计-案件-立案统计
    public static final int HTTP_ID_statistics_case_queryClosedCount = 204;//统计-案件-结案统计
    public static final int HTTP_ID_statistics_case_queryRecordCount = 205;//统计-案件-案件记录趋势统计
    public static final int HTTP_ID_statistics_case_queryPunishCount = 206;//统计-案件-案件处罚趋势统计
    public static final int HTTP_ID_statistics_comp_countDepartment = 207;//统计-投诉-部门统计
    public static final int HTTP_ID_statistics_comp_countType = 208;//统计-投诉-投诉类别
    public static final int HTTP_ID_statistics_comp_countInfo = 209;//统计-投诉-信息来源
    public static final int HTTP_ID_statistics_comp_countBussiniss = 210;//统计-投诉-业务来源

    public static final int HTTP_ID_statistics_entity_enterpriseType = 211;//统计-主体-企业类型
    public static final int HTTP_ID_statistics_entity_enterpriseIndustry = 212;//统计-主体-行业结构
    public static final int HTTP_ID_statistics_entity_equipmentType = 213;//统计-主体-特种设备
    public static final int HTTP_ID_statistics_entity_enterpriseComplain = 214;//统计-主体-消保维权
    public static final int HTTP_ID_statistics_entity_enterpriseDev = 215;//统计-主体-主体发展
    public static final int HTTP_ID_statistics_entity_enterpriseAnn = 216;//统计-主体-年报情况
    public static final int HTTP_ID_statistics_entity_enterpriseWarning = 217;//统计-主体-许可证预警

    public static final int HTTP_ID_statistics_super_countTask = 218;//任务主体
    public static final int HTTP_ID_statistics_super_countType = 219;//任务类型
    public static final int HTTP_ID_statistics_super_countEnterprise = 220;//检查主体
    public static final int HTTP_ID_statistics_super_countDoTask = 221;//统计-监管-执行任务数

    public static final int HTTP_ID_supervise_saceItemResult = 222;//监管处置
    public static final int HTTP_ID_supervise_finishItem = 223;//监管完成
    public static final int HTTP_ID_supervise_getTaskFiles = 224;//监管任务-图片集合
    public static final int HTTP_ID_supervise_deleteFile = 225;//删除图片

    public static final int HTTP_ID_case_01 = 226;//案件-线索核查
    public static final int HTTP_ID_case_Y01 = 227;//案件-立案申请
    public static final int HTTP_ID_case_Y02 = 228;//案件-立案审批
    public static final int HTTP_ID_case_Y03 = 229;//案件-调查取证
    public static final int HTTP_ID_case_Y05 = 231;//案件-终结报告
    public static final int HTTP_ID_case_Y06 = 232;//案件-初审
    public static final int HTTP_ID_case_Y07 = 233;//案件-委员会集体审理
    public static final int HTTP_ID_case_Y08 = 234;//案件-行政处罚告知
    public static final int HTTP_ID_case_Y10 = 235;//案件-听证
    public static final int HTTP_ID_case_Y11 = 236;//案件-处罚决定
    public static final int HTTP_ID_case_Y12 = 237;//案件-处罚决定审核
    public static final int HTTP_ID_case_Y13 = 238;//案件-送达当事人
    public static final int HTTP_ID_case_Y14 = 239;//案件-行政处罚的执行
    public static final int HTTP_ID_case_02 = 241;//案件-结案

    public static final int HTTP_ID_info_manager_biaozhun = 301;//标准信息查询
    public static final int HTTP_ID_info_manager_device_liebiao = 302;//特种设备-特种设备列表查询
    public static final int HTTP_ID_info_manager_device_detail = 303;//特种设备-特种设备详情接口
    public static final int HTTP_ID_info_manager_license_food = 304;//许可证-食品企业列表
    public static final int HTTP_ID_info_manager_license_drugs = 305;//许可证-药品企业列表
    public static final int HTTP_ID_info_manager_license_cosmetics = 306;//许可证-化妆品企业列表
    public static final int HTTP_ID_info_manager_license_instrument = 307;//许可证-医疗器械企业列表
    public static final int HTTP_ID_info_manager_license_detail = 308;//许可证-许可证详情
    public static final int HTTP_ID_info_manager_measuring_instruments_custom = 310;//计量器具-自定义表信息接口
    public static final int HTTP_ID_info_manager_measuring_instruments_liebiao = 311;//计量器具-计量器具列表接口
    public static final int HTTP_ID_info_manager_measuring_instruments_detail = 312;//计量器具-计量器具详情
    public static final int HTTP_ID_info_manager_legal_query = 313;//法律法规-查询菜单接口
    public static final int HTTP_ID_info_manager_legal_search = 314;//法律法规-法律法规搜索接口
    public static final int HTTP_ID_info_manager_biaozhun_detail = 315;//标准信息展示
    //TODO
    public static String UUID = "";

    public static String getmIp() {
        return mIp;
    }

    public static void setmIp(String mIp) {
        ApiData.mIp = mIp;
    }

    public static String mIp = "";
    public static String sessionId = "";
    public static final int HTTP_ID_map_update = 200;// 离线地图更新

    private HttpParam params = new HttpParam();
    public static String baseUrl = null;
//            "http://42.123.116.156:80/GAMarketSupervise/";
    /**
     * 文件上传 [0]客户id [1]文件存放路径[2]接受者id[3] 发送类型 参考 HTTP_ID_message_send 2图片 3语音,
     * String type]
     */
    public static final int FILE_UPLOAD = 999;
    public static final int FILE_DOWNLOAD = 1000;

    public ApiData(int id) {
        super(id);
    }

    public static void setBaseUrl(String ip) {
        baseUrl = "http://" + ip;
        setmIp(ip);
    }

    public void setPort() {

    }

    public void loadData(final Object... objects) {
        if (getId() == HTTP_ID_message_send) {
            super.queueLoadData(objects);
        } else if (getId() == FILE_UPLOAD) {
            final String sqlId = objects[0].toString();
            HTTPEXCUTORS.execute(new Runnable() {
                String errorMessage = null;

                @Override
                public void run() {
                    if (listener != null) {
                        try {
                            pHandler.post(new Runnable() {

                                @Override
                                public void run() {
                                    listener.onLoadStart(FILE_UPLOAD);
                                }
                            });
                            fileUpload((String[]) objects[1], (String) objects[2], (Map<String, String>) objects[3]);
                        } catch (final CustomException e) {
                            e.printStackTrace();
                            errorMessage = e.getMessage();
                        } catch (IOException | URISyntaxException e) {
                            e.printStackTrace();
                            errorMessage = "网络连接失败";
                        } catch (JSONException e) {
                            e.printStackTrace();
                            errorMessage = "解析失败";
                        }
                        if (errorMessage != null) {
                            pHandler.post(new Runnable() {

                                @Override
                                public void run() {
                                    listener.onLoadError(FILE_UPLOAD, false, sqlId);
                                }
                            });
                        }
                    }

                }
            });
        } else if (getId() == FILE_DOWNLOAD) {
            HTTPEXCUTORS.execute(new Runnable() {
                @Override
                public void run() {
                    if (listener != null) {
                        pHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                listener.onLoadStart(FILE_DOWNLOAD);
                            }
                        });
                        if (objects.length < 2) {
                            listener.onLoadError(FILE_DOWNLOAD, false, "上传失败");
                            Log.e("上传失败", "参数不齐，请检查");
                        } else {
                            fileDownload((String) objects[0], (String) objects[1], (boolean) objects[2]);
                        }
                    }

                }
            });
        } else {
            super._loadData(objects);
        }
    }

    @Override
    protected BaseHttpParams<Object> getHttpParams(int id, Object... objects) {
        try {
            params.clear();
            switch (id) {
                case HTTP_ID_login:
                    params.setApiUrl(baseUrl + "/TJSSO/appLogin.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("userName", objects[0]);
                    params.putParams("password", MD5Util.encoderPassword(objects[1].toString()));
                    break;
                case HTTP_ID_searchzt:
                    params.setApiUrl(baseUrl + "/TJsupervise/enterprise/getEnterprisePage.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("enterpriseName", objects[2]);
                    break;
                case HTTP_ID_searchall:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "queryAllEntityByInfo");
                    params.putParams("pageno", objects[0]);
                    params.putParams("pagesize", objects[1]);
                    params.putParams("name", objects[2]);
                    params.putParams("fSfrl", objects[3]);
                    params.putParams("fSfjz", objects[4]);
                    params.putParams("fStation", objects[5]);
                    break;
                case HTTP_ID_searchzt_Jyfw:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "queryBaseMarketDetail");
                    params.putParams("guid", objects[0]);
                    params.putParams("type", objects[1]);
                    break;
                case HTTP_ID_search_taskcount:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getTaskCounts");
                    params.putParams("userid", objects[0]);
                    break;
                case HTTP_ID_search_task_bypage:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "queryTaskPage");
                    params.putParams("pageno", objects[0]);
                    params.putParams("pagesize", objects[1]);
                    params.putParams("timetype", objects[2]);
                    params.putParams("status", objects[3]);
                    params.putParams("userid", objects[4]);
                    break;
                case HTTP_ID_searchtask_detail:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getTaskDetailInfo");
                    params.putParams("guid", objects[0]);
                    params.putParams("type", objects[1]);
                    break;
                case HTTP_ID_task_execute:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "executeTask");
                    params.putParams("guid", objects[0]);
                    params.putParams("type", objects[1]);
                    params.putParams("userid", objects[2]);
                    if (objects[1].toString().equals("1")) {// 投诉举报任务 0为监管监管任务
                        params.putParams("remark", objects[3]);
                        params.putParams("contactBool", objects[4]);
                        params.putParams("contactAvenue", objects[5]);
                    }
                    break;
                case HTTP_ID_task_commit:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "submitTask");
                    params.putParams("guid", objects[0]);
                    params.putParams("type", objects[1]);
                    params.putParams("remark", objects[2]);
                    params.putParams("userid", objects[3]);
                    params.putParams("qualify", objects[4]);
                    params.putParams("fRecoupedeconomyLost", objects[5]);
                    params.putParams("licnum", objects[6]);
                    break;
                case HTTP_ID_task_check_Save:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "saveTaskItem");
                    params.putParams("taskitemjson", objects[0]);
                    params.putParams("userid", objects[1]);
                    break;
                case HTTP_ID_msg_notread_count:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getNotReadMsgCount");
                    params.putParams("userid", objects[0]);
                    break;
                case HTTP_ID_msg_center:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getMessageInfos");
                    params.putParams("userid", objects[0]);
                    break;
                case HTTP_ID_searchevent_detail:
                    params.setApiUrl(baseUrl + "AppCheckInfoController.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getCheckInfo");
                    params.putParams("fCheckGuid", objects[0]);
                    params.putParams("type", objects[1]);
                    break;
                case HTTP_ID_searchzt_detail:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getEntityDetailInfo");
                    params.putParams("guid", objects[0]);
                    // params.putParams("guid", "6FA2A1BD36B945568A7962460DA596DC");
                    break;
                case HTTP_ID_event_response:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "responseEvent");
                    params.putParams("eventid", objects[0]);
                    params.putParams("userid", objects[1]);
                    break;
                case HTTP_ID_event_checkin:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "checkinEvent");
                    params.putParams("eventid", objects[0]);
                    params.putParams("userid", objects[1]);
                    params.putParams("x", objects[2]);
                    params.putParams("y", objects[3]);
                    break;
                case HTTP_ID_event_report:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "reportEvent");
                    params.putParams("eventid", objects[0]);
                    params.putParams("userid", objects[1]);
                    params.putParams("desc", objects[2]);
                    params.putParams("picPaths", objects[3]);
                    break;
                case HTTP_ID_set_msg_read:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "setMsgRead");
                    params.putParams("msgid", objects[0]);
                    params.putParams("userid", objects[1]);
                    break;
                case HTTP_ID_change_pos:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "changeEntitypos");
                    params.putParams("entityid", objects[0]);
                    params.putParams("userid", objects[1]);
                    params.putParams("x", objects[2]);
                    params.putParams("y", objects[3]);
                    params.putParams("guid", objects[4]);
                    break;

                case HTTP_ID_message_send:
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("action", "message_send");
                    params.putParams("id", objects[0]);
                    params.putParams("fileinfo", objects[1]);
                    break;
                case HTTP_ID_version_update:
                    params.setApiUrl(baseUrl + "/TJSSO/queryVersion.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("versionCode", objects[0]);
                    break;
                case HTTP_ID_user_station:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getPersionByStation");
                    params.putParams("station", objects[0]);
                    break;
                case HTTP_ID_supervisetask_list:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getSuperviseList");
                    params.putParams("userid", objects[0]);
                    params.putParams("pageNo", objects[1]);
                    params.putParams("pageSize", objects[2]);
                    params.putParams("type", objects[3]);
                    params.putParams("department", objects[4]);
                    params.putParams("status", objects[5]);
                    params.putParams("duty", objects[6]);
                    break;
                case HTTP_ID_supervisetask_detail:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getSuperviseDetail");
                    params.putParams("taskId", objects[0]);
                    params.putParams("guid", objects[1]);
                    params.putParams("userid", objects[2]);
                    break;
                case HTTP_ID_entity_detail:
                    params.setApiUrl(baseUrl + "/TJsupervise/enterprise/getEnterpriseInfo.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("id", objects[0]);
                    break;
                case HTTP_ID_entity_modifycontactinfo:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "modifyEntityContactInfo");
                    params.putParams("entityGuid", objects[0]);
                    params.putParams("contactAddr", objects[1]);
                    params.putParams("contactPhone", objects[2]);
                    params.putParams("contactPeople", objects[3]);
                    break;
                case HTTP_ID_supervisetask_searchcheck:
                    params.setApiUrl(baseUrl + "/TJsupervise/taskDo/queryItemResult.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("id", objects[0]);
                    break;
                case HTTP_ID_supervisetask_save:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "saveMyTask");
                    params.putParams("userid", objects[0]);
                    params.putParams("taskitemjson", objects[1]);
                    params.putParams("guid", objects[2]);
                    break;
                case HTTP_ID_supervisetask_submit:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "submitSuperviseTask");
                    params.putParams("userid", objects[0]);
                    params.putParams("guid", objects[1]);
                    params.putParams("remark", objects[2]);
                    params.putParams("qualify", objects[3]);
                    params.putParams("taskitemjson", objects[4]);
                    break;
                case HTTP_ID_supervisetasktype_list:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getTaskList");
                    if (objects.length > 0) {
                        params.putParams("year", objects[0]);
                    }
                    break;
                case HTTP_ID_supervisetasktype_count:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "queryCountTaskInfoByStatus");
                    params.putParams("taskid", objects[0]);
                    params.putParams("status", objects[1]);
                    params.putParams("userid", objects[2]);
                    break;
                case HTTP_ID_entity_list:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "queryEntityInfoOfTaskCountByStatus");
                    params.putParams("pageno", objects[0]);
                    params.putParams("pagesize", objects[1]);
                    params.putParams("f_task_id", objects[2]);
                    params.putParams("f_status", objects[3]);
                    params.putParams("f_type", objects[4]);
                    params.putParams("f_value", objects[5]);
                    params.putParams("userid", objects[6]);
                    break;
                case HTTP_ID_superviseoperate_detail:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getSuperviseOperateDetail");
                    params.putParams("guid", objects[0]);
                    break;
                case HTTP_ID_superviseoperate_dispose:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "optEntityTask");
                    params.putParams("userid", objects[0]);
                    params.putParams("guid", objects[1]);
                    params.putParams("opt", objects[2]);
                    break;
                case HTTP_ID_supervisetasktype_end:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "finishTask");
                    params.putParams("userid", objects[0]);
                    params.putParams("taskId", objects[1]);
                    params.putParams("station", objects[2]);
                    params.putParams("grid", objects[3]);
                    break;
                case HTTP_ID_supervisetask_statusupdate:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "updateTaskEntityStatusByEntity");
                    params.putParams("operation", objects[0]);
                    params.putParams("guid", objects[1]);
                    params.putParams("status", objects[2]);
                    params.putParams("remark", objects[3]);
                    params.putParams("userid", objects[4]);
                    break;
                case HTTP_ID_supervisetask_queryuserentity:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "queryUserTaskEntityOfStatus");
                    params.putParams("userid", objects[0]);
                    params.putParams("status", objects[1]);
                    params.putParams("taskid", objects[2]);
                    if (objects.length >= 5) {
                        params.putParams("pageno", objects[3]);
                        params.putParams("pagesize", objects[4]);
                    }
                    break;
                case HTTP_ID_supervisetask_queryallentity:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "queryAllEntityInfoByTaskStatus");
                    params.putParams("userid", objects[0]);
                    params.putParams("status", objects[1]);
                    params.putParams("taskid", objects[2]);
                    params.putParams("pageno", objects[3]);
                    params.putParams("pagesize", objects[4]);
                    break;
                case HTTP_ID_supervisetasktype_reviewlist:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getTaskListForReview");
                    params.putParams("type", objects[0]);
                    params.putParams("userid", objects[1]);
                    break;
                case HTTP_ID_supervisetask_monitorlist:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getTaskMonitoringDetail");
                    params.putParams("userid", objects[0]);
                    params.putParams("taskId", objects[1]);
                    params.putParams("pageNo", objects[2]);
                    params.putParams("pageSize", objects[3]);
                    params.putParams("type", objects[4]);
                    break;
                case HTTP_ID_supervisetask_querystatusentity:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getEntityByTaskStatus");
                    params.putParams("userid", objects[0]);
                    params.putParams("pageno", objects[1]);
                    params.putParams("pagesize", objects[2]);
                    params.putParams("fTaskId", objects[3]);
                    params.putParams("fStatus", objects[4]);
                    params.putParams("type", objects[5]);
                    params.putParams("fRow", objects[6]);
                    break;
                case HTTP_ID_statistics_single_parameter:
                case HTTP_ID_statistics_hzp:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "countStatisticsWithSingleResult");
                    params.putParams("key", objects[0]);
                    if (objects.length > 1) {
                        params.putParams("param0", objects[1]);
                    }
                    if (objects.length > 2) {
                        params.putParams("param1", objects[2]);
                    }
                    break;
                case HTTP_ID_device_security_risk_parameter:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getEquitHiddendangerNumByStation");
                    params.putParams("key", objects[0]);
                    params.putParams("param0", objects[1]);
                    params.putParams("param1", objects[2]);
                    params.putParams("queryByType", objects[3]);
                    break;
                case HTTP_ID_security_flaws_parameter:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getHiddendangerNumByStation");
                    break;
                case HTTP_ID_food_sample_parameter:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "countFoodRandCheckByStation");
                    params.putParams("time", objects[0]);
                    break;
                case HTTP_ID_food_fast_sample_parameter:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "countFoodFastCheckByStation");
                    params.putParams("time", objects[0]);
                    break;
                case HTTP_ID_drug_sample_parameter:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "countMedicRandCheckByStation");
                    params.putParams("time", objects[0]);
                    break;
                case HTTP_ID_industrial_product_parameter:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "countInProRandCheckByStation");
                    params.putParams("time", objects[0]);
                    break;
                case HTTP_ID_statistics_four_parameter:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "countStatisticsWithFourResult");
                    params.putParams("name", objects[0]);
                    break;
                case HTTP_ID_map_update:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "updateMap");
                    params.putParams("vectorCode", objects[0]);
                    params.putParams("imageCode", objects[1]);
                    params.putParams("renderCode", objects[2]);
                    break;
                case HTTP_ID_loginOut:
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.setApiUrl(baseUrl + "/TJSSO/appLogout.do");
                    break;
                case HTTP_ID_getUsersByDept:
                    params.setApiUrl(baseUrl + "/TJComplaint/user/getUserPage.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("pageNo", 1);
                    params.putParams("pageSize", 999);
//                    params.putParams("userName", objects[0]);
//                    params.putParams("realName", objects[1]);
//                    params.putParams("department", objects[2]);
                    params.putParams("departmentCode", objects[0]);
//                    params.putParams("status", objects[4]);
                    params.putParams("roles", objects[1]);
                    break;
                case HTTP_ID_getUserNameByArea:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getUserNameByArea");
                    params.putParams("area", objects[0]);
                    params.putParams("areaname", objects[1]);
                    break;
                case HTTP_ID_getCheckInfos:
                    params.setApiUrl(baseUrl + "AppCheckInfoController.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getCheckInfos");
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("fetchNum", objects[2]);
                    params.putParams("type", objects[3]);
                    params.putParams("fCheckType", objects[4]);
                    break;
                case HTTP_ID_getDeviceEmergencyListInfos:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getElevatorPageByInfo");
                    params.putParams("pageno", objects[0]);
                    params.putParams("pagesize", objects[1]);
                    params.putParams("fetchNum", objects[2]);
                    break;
                case HTTP_ID_getLicYjEmergencyListInfos:
                    params.setApiUrl(baseUrl + "AppCheckInfoController.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getLicYjInfo");
                    params.putParams("pageno", objects[0]);
                    params.putParams("pagesize", objects[1]);
                    params.putParams("lictype", objects[2]);
                    break;
                case HTTP_ID_getMaintenanceContractDataListInfos:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getMaintenancePageByInfo");
                    params.putParams("pageno", objects[0]);
                    params.putParams("pagesize", objects[1]);
                    params.putParams("fetchNum", objects[2]);
                    break;
                case HTTP_ID_getSafeFileListInfos:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getSaftyAccByInfo");
                    params.putParams("pageno", objects[0]);
                    params.putParams("pagesize", objects[1]);
                    params.putParams("fetchNum", objects[2]);
                    break;
                case HTTP_ID_getEmergency_detail:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getEquipmentByGuid");
                    params.putParams("fGuid", objects[0]);
                    break;
                case HTTP_ID_getPermit_Emergency_detail:
                    params.setApiUrl(baseUrl + "AppCheckInfoController.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getLicInfoByLicNum");
                    params.putParams("type", objects[0]);
                    params.putParams("fGuid", objects[1]);
                    break;
                case HTTP_ID_submitLocation:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "submitLocation");
                    params.putParams("fEntityGuid", objects[0]);
                    params.putParams("fLongitude", objects[1]);
                    params.putParams("fLatitude", objects[2]);
                    break;
                case HTTP_ID_countEntityType:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "countEntityType");
                    break;
                case HTTP_ID_psw_update:
                    params.setApiUrl(baseUrl + "/TJSSO/updatePass.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("oldPassword", MD5Util.encoderPassword(objects[0].toString()));
                    params.putParams("newPassword", MD5Util.encoderPassword(objects[1].toString()));
                    break;
                case HTTP_ID_doClaimed:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "entityClaim");
                    params.putParams("fEntityGuid", objects[0]);
                    params.putParams("fUserId", objects[1]);
                    params.putParams("fStation", objects[2]);
                    break;
                case HTTP_ID_deleteFileInfo:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "deleteOfficialPictureById");
                    params.putParams("fGuid", objects[0]);
                    break;
                case HTTP_ID_savaOfficialPicture:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "savaOfficialPicture");
                    params.putParams("fEntityGuid", objects[0]);
                    params.putParams("fileNames", objects[1]);
                    params.putParams("realNames", objects[2]);
                    params.putParams("fUploadPerson", objects[3]);
                    break;
                case HTTP_ID_queryLocationData:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getRefreshLocaltion");
                    params.putParams("guid", objects[0]);
                    params.putParams("fEntityType", objects[1]);
                    break;
                case HTTP_ID_complain_taskcount:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getTaskCount");
                    params.putParams("userid", objects[0]);
                    params.putParams("authority", objects[1]);
                    params.putParams("dpt", objects[2]);
                    break;
                case HTTP_ID_casePageAyxx:
                    params.setApiUrl(baseUrl + "/TJCase/case/queryList.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("isOverdue", objects[2]);
                    params.putParams("caseName", objects[3]);
                    params.putParams("caseNum", objects[4]);
                    params.putParams("status", objects[5]);
                    break;
                case HTTP_ID_caseTaskPage:
                    params.setApiUrl(baseUrl + "/TJCase/case/queryToDo.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("caseName", objects[2]);
                    params.putParams("foundDateMin", objects[3]);
                    params.putParams("foundDateMax", objects[4]);
                    params.putParams("typeCode", objects[5]);
                    params.putParams("provideName", objects[6]);
                    break;
                case HTTP_ID_caseTaskHisPage:
                    params.setApiUrl(baseUrl + "/TJCase/case/queryAlready.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("caseName", objects[2]);
                    params.putParams("foundDateMin", objects[3]);
                    params.putParams("foundDateMax", objects[4]);
                    params.putParams("typeCode", objects[5]);
                    params.putParams("provideName", objects[6]);
                    break;
                case HTTP_ID_caseGetAyxxDetailById:
                    params.setApiUrl(baseUrl + "/TJCase/case/queryOne.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("id", objects[0]);
                    break;
                case HTTP_ID_caseGetAyLcgjPageInfo:
                    params.setApiUrl(baseUrl + "/TJCase/caseFlow/flowLog.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("caseId", objects[0]);
                    break;
                case HTTP_ID_caseGetNextPerson:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getTaskAssigneeTree");
                    params.putParams("fId", objects[0]);
                    params.putParams("isPass", objects[1]);
                    params.putParams("taskId", objects[2]);
                    break;
                case HTTP_ID_caseDoExcute:
                    params.setApiUrl(baseUrl + "GaZhzfService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", objects[0]);
                    params.putParams("fId", objects[1]);
                    params.putParams("isPass", objects[2]);
                    params.putParams("assignee", objects[3]);
                    params.putParams("fSlr", objects[4]);
                    params.putParams("fUserId", objects[5]);
                    params.putParams("description", objects[6]);
                    params.putParams("taskId", objects[7]);
                    if (objects.length > 8) {
                        for (int i = 8; i < objects.length; i++) {
                            params.putParams(objects[i] + "", objects[++i]);
                        }
                    }
                    break;
                case HTTP_ID_caseDoDelay:
                case HTTP_ID_caseDoDestory:
                    params.setApiUrl(baseUrl + "GaZhzfService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", objects[0]);
                    params.putParams("fId", objects[1]);
                    params.putParams("isPass", objects[2]);
                    params.putParams("fUserId", objects[3]);
                    params.putParams("description", objects[4]);
                    params.putParams("taskId", objects[5]);
                    for (int i = 6; i < objects.length; i++) {
                        params.putParams(objects[i] + "", objects[++i]);
                    }
                    break;
                case HTTP_ID_caseDoTransfer:
                    params.setApiUrl(baseUrl + "GaZhzfService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "doAjysLcSpPro");
                    params.putParams("fId", objects[0]);
                    params.putParams("fUserId", objects[1]);
                    params.putParams("fYsr", objects[2]);
                    params.putParams("fYsdw", objects[3]);
                    params.putParams("fYsSj", objects[4]);
                    break;
                case HTTP_ID_caseDoForce:
                    params.setApiUrl(baseUrl + "GaZhzfService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "doAjQzcsPro");
                    params.putParams("fId", objects[0]);
                    params.putParams("fUserId", objects[1]);
                    params.putParams("fZxsm", objects[2]);
                    params.putParams("fZxr", objects[3]);
                    params.putParams("fZxsj", objects[4]);
                    break;
                case HTTP_ID_caseGetMonitor:
                    params.setApiUrl(baseUrl + "/TJCase/caseMonitor/getMonitorCount.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    break;
                case HTTP_ID_casePageLcjk:
                    params.setApiUrl(baseUrl + "/TJCase/caseMonitor/getMonitorCount.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("pageNum", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("type", objects[2]);
                    params.putParams("yqtype", objects[3]);
                    break;
                case HTTP_ID_caseSaveAyfj:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "saveAyfj");
                    params.putParams("fId", objects[0]);
                    params.putParams("fUserId", objects[1]);
                    params.putParams("realNames", objects[2]);
                    params.putParams("fileNames", objects[3]);
                    params.setRetry(false);//是否在网络不好时，请求第二次，默认为true
                    break;
                case HTTP_ID_compMonitor:
                    params.setApiUrl(baseUrl + "/TJComplaint/complaint/countComplaintByStatus.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    break;
                case HTTP_ID_compPageQuery:
                    params.setApiUrl(baseUrl + "/TJComplaint/complaint/getComplaintPage.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("fCondition", objects[4]);
                    break;
                case HTTP_ID_compInfoById:
                    params.setApiUrl(baseUrl + "/TJComplaint/complaint/getComplaintInfo.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("fGuid", objects[0]);
                    break;
                case HTTP_ID_compLcgj:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getTrajectoryByfRegId");
                    params.putParams("fRegId", objects[0]);
                    break;
                case HTTP_ID_compTaskPage:
                    params.setApiUrl(baseUrl + "/TJComplaint/complaint/getComplaintByStatus.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("fStatus", objects[2]);
                    params.putParams("sort", objects[3]);
                    break;
                case HTTP_ID_getAllUserDept:
                    params.setApiUrl(baseUrl + "/TJsupervise/department/getDepartmentList.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    break;
                case HTTP_ID_compFlowhandle:
                    params.setApiUrl(baseUrl + "/TJComplaint/complaint/optComplaint.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("fGuid", objects[0]);
                    params.putParams("fDispose", objects[1]);
                    params.putParams("fDisposeRemark", objects[2]);
                    params.putParams("fShunt", objects[3]);
                    params.putParams("fDisposeUser", objects[4]);
                    params.putParams("fReviewResult", objects[5]);
                    params.putParams("fReplyContent", objects[6]);
                    params.putParams("fFeedbackContent", objects[7]);
                    break;
                case HTTP_ID_compLcjk:
                    params.setApiUrl(baseUrl + "/TJComplaint/complaint/getComplaintByStatus.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("fStatus", objects[2]);
                    params.putParams("sort", "desc");
                    break;
                case HTTP_ID_SuperviseTaskPage:
                    params.setApiUrl(baseUrl + "/TJsupervise/formulation/queryTaskList.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("status", objects[2]);
//                    params.putParams("fetchNum", objects[2]);
//                    params.putParams("fUserId", objects[3]);
                    break;
                case HTTP_ID_SuperviseTaskHisPage:
//                    params.setApiUrl(baseUrl + "GaClientService.do");
//                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.setApiUrl(baseUrl + "/TJsupervise/formulation/queryTaskList.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
//                    params.putParams("method", "getMyDone");
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("status", objects[2]);
//                    params.putParams("fetchNum", objects[2]);
//                    params.putParams("fUserId", objects[3]);
                    break;
                case HTTP_ID_supervisetask_getMonitorTask:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getMonitorTask");
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("fetchNum", objects[2]);
                    params.putParams("fUserId", objects[3]);
                    params.putParams("fIsOverFlag", objects[4]);
                    params.putParams("fTaskStatus", objects[5]);
                    break;
                case HTTP_ID_supervise_countMonitorTask:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "countMonitorTask");
                    params.putParams("fUserId", objects[0]);
                    break;
                case HTTP_ID_superviseTaskBaseInfo:
                    params.setApiUrl(baseUrl + "/TJsupervise/formulation/queryTaskOne.do");
//                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("id", objects[0]);
//                    params.putParams("method", "getTaskShunt");
//                    params.putParams("fGuid", objects[0]);
//                    params.putParams("fTaskStatus", objects[1]);
//                    params.putParams("fTaskId", objects[2]);
//                    params.putParams("fUserId", objects[3]);
                    break;
                case HTTP_ID_superviseGetAyLcgjPageInfo:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getTaskHistory");
                    params.putParams("fTaskId", objects[0]);
                    params.putParams("fUserId", objects[1]);
                    break;
                case HTTP_ID_Supervise_getThisTaskPackageTask:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getThisTaskPackageTask");
                    params.putParams("fTaskId", objects[0]);
                    params.putParams("fGuid", objects[1]);
                    break;
                case HTTP_ID_SuperviseTaskCheckEntity:
//                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setApiUrl(baseUrl + "/TJsupervise/formulation/queryEnterprise.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("taskId", objects[2]);
                    break;
                case HTTP_ID_SuperviseMyTaskCheckEntity:
//                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setApiUrl(baseUrl + "/TJsupervise/taskDo/queryItemResult.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("id", objects[0]);
//                    params.setRequestMothod(HTTP_MOTHOD.POST);
//                    params.putParams("method", "getMyDisposal");
//                    params.putParams("fTaskId", objects[0]);
//                    params.putParams("fUserId", objects[1]);
                    break;
                case HTTP_ID_Supervise_MyTask_sendTask:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "sendTask");
                    params.putParams("fTaskId", objects[0]);
                    params.putParams("fDepartment", objects[1]);
                    params.putParams("fHandleUserId", objects[2]);
                    params.putParams("fUserId", objects[3]);
                    params.putParams("xbUserIds", objects[4]);
                    params.putParams("xbUserNames", objects[5]);
                    break;
                case HTTP_ID_Supervise_MyTask_getProcessingPeople:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getProcessingPeople");
                    params.putParams("fUserId", objects[0]);
                    break;
                case HTTP_ID_Supervise_MyTask_getSubDirector:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getSubDirector");
                    params.putParams("fUserId", objects[0]);
                    break;
                case HTTP_ID_Supervise_MyTask_examineTaskInfo:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "examineTaskInfo");
                    params.putParams("fIsOk", objects[0]);
                    params.putParams("fTaskId", objects[1]);
                    params.putParams("fExamineOptinion", objects[2]);
                    params.putParams("fUserId", objects[3]);
                    break;
                case HTTP_ID_Supervise_MyTask_assignTaskInfo:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "assignTaskInfo");
                    params.putParams("fHandleUserId", objects[0]);
                    params.putParams("fTaskId", objects[1]);
                    params.putParams("fUserId", objects[2]);
                    params.putParams("xbUserIds", objects[3]);
                    params.putParams("xbUserNames", objects[4]);
                    break;
                case HTTP_ID_Supervise_MyTask_reviewTask:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "reviewTask");
                    params.putParams("fGuid", objects[0]);
                    params.putParams("fIsOk", objects[1]);
                    params.putParams("fReviewMessage", objects[2]);
                    params.putParams("fUserId", objects[3]);
                    break;
                case HTTP_ID_Supervise_MyTask_feedbackTask:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "feedbackTask");
                    params.putParams("fGuid", objects[0]);
                    params.putParams("fTaskId", objects[1]);
                    params.putParams("fFeedbackMessage", objects[2]);
                    params.putParams("fUserId", objects[3]);
                    break;
                case HTTP_ID_Supervise_MyTask_executeMyTask:
//                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setApiUrl(baseUrl + "/TJsupervise/taskDo/saveItemResult.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
//                    params.putParams("method", "executeMyTask");
                    params.putParams("id", objects[0]);
                    break;
                case HTTP_ID_Supervise_MyTask_getCheckResult:
                    params.setApiUrl(baseUrl + "/TJsupervise/taskDo/finishTask.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
//                    params.putParams("method", "executeMyTask");
                    params.putParams("id", objects[0]);
//                    params.setApiUrl(baseUrl + "GaClientService.do");
//                    params.setRequestMothod(HTTP_MOTHOD.GET);
//                    params.putParams("method", "getStatusBaseInfo");
//                    params.putParams("fGuid", objects[0]);
                    break;
                case HTTP_ID_Supervise_MyTask_disposalTask:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "disposalTask");
                    params.putParams("fUserId", objects[0]);
                    params.putParams("fGuid", objects[1]);
                    params.putParams("guid", objects[2]);
                    params.putParams("remark", objects[3]);
                    params.putParams("qualify", objects[4]);
                    params.putParams("taskitemjson", objects[5]);
                    params.putParams("fImgs", objects[6]);
                    params.putParams("fTaskStatuGuid", objects[7]);
                    params.putParams("fTypes", objects[8]);
                    params.putParams("fLat", objects[9]);
                    params.putParams("fLon", objects[10]);
                    break;
                case HTTP_ID_Case_Delete_Img:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "deleteAyfjById");
                    params.putParams("fIds", objects[0]);
                    break;
                case HTTP_ID_getStatisticsList:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getAppPageInfoByCondition");
                    params.putParams("pagesize", objects[0]);
                    params.putParams("pageno", objects[1]);
                    params.putParams("key", objects[2]);
                    params.putParams("line", objects[3]);
                    params.putParams("column", objects[4]);
                    break;
                case HTTP_ID_superviseIsCanFinishInfo:
                    params.setApiUrl(baseUrl + "/TJsupervise/taskDo/countUntreated.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("taskId", objects[0]);
                    break;
                case HTTP_ID_superviseFinishTask:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "goBackAssignTask");
                    params.putParams("fGuid", objects[0]);
                    params.putParams("fTaskId", objects[1]);
                    params.putParams("fUserId", objects[2]);
                    break;
                case HTTP_ID_superviseGetTaskImg:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("method", "getTaskImg");
                    params.putParams("fGuid", objects[0]);
                    params.putParams("fTaskStatuGuid", objects[1]);
                    break;
                case HTTP_ID_superviseAppendEntity:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "appendEntity");
                    params.putParams("fTaskId", objects[0]);
                    params.putParams("fEntityName", objects[1]);
                    params.putParams("fGuid", objects[2]);
                    params.putParams("fAddress", objects[3]);
                    params.putParams("fStation", objects[4]);
                    params.putParams("fEntityGuid", objects[5]);
                    params.putParams("fUserId", objects[6]);
                    params.putParams("fLat", objects[7]);
                    params.putParams("fLon", objects[8]);
                    break;
                case HTTP_ID_superviseQueryEntityByCondition:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("method", "queryEntityByCondition");
                    params.putParams("pageno", objects[0]);
                    params.putParams("pagesize", objects[1]);
                    params.putParams("fetchNum", objects[2]);
                    params.putParams("fEntityName", objects[3]);
                    params.putParams("fStation", objects[4]);
                    break;
                case HTTP_ID_caseSelectDiscretionStandard:
                    params.setApiUrl(baseUrl + "AppCheckInfoController.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("method", "selectDiscretionStandard");
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("fetchNum", objects[2]);
                    params.putParams("fType", objects[3]);
                    params.putParams("fIllegalAct", objects[4]);
                    params.putParams("fViolateLaw", objects[5]);
                    params.putParams("fPunishLaw", objects[6]);
                    params.putParams("condition", objects[7]);
                    break;
                case HTTP_ID_superviseGetEntityPoint:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getEntityPoint");
                    params.putParams("fEntityGuid", objects[0]);
                    break;
                case HTTP_ID_superviseGetDTByCondition:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "getDTByCondition");
                    params.putParams("fEntityGuid", objects[0]);
                    params.putParams("pagesize", objects[1]);
                    params.putParams("pageno", objects[2]);
                    break;
                case HTTP_ID_superviseUpdateDTPosition:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "updateDTPosition");
                    params.putParams("fLat", objects[0]);
                    params.putParams("fLon", objects[1]);
                    params.putParams("fId", objects[2]);
                    break;
                case HTTP_ID_superviceAppendEntityPoint:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("method", "appendEntityPoint");
                    params.putParams("fGuid", objects[0]);
                    params.putParams("fEntityGuid", objects[1]);
                    params.putParams("fLat", objects[2]);
                    params.putParams("fLon", objects[3]);
                    params.putParams("fAddress", objects[4]);
                    break;
                case HTTP_ID_synergyCountCheckInfo:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    if (((boolean) objects[0])) {
                        params.putParams("method", "countCheckInfoSee");
                    } else {
                        params.putParams("method", "countCheckInfo");
                    }
                    params.putParams("fUserId", objects[1]);
                    params.putParams("status", objects[2]);
                    break;
                case HTTP_ID_synergyGetCountCheckInfoItem:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    if (((boolean) objects[0])) {
                        params.putParams("method", "getCountSeeCheckInfoItems");
                    } else {
                        params.putParams("method", "getCountCheckInfoItems");
                    }
                    params.putParams("fUserId", objects[1]);
                    params.putParams("type", objects[2]);
                    params.putParams("status", objects[3]);
                    break;
                case HTTP_ID_synergyGetCheckInfoItems:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    if (((boolean) objects[0])) {
                        params.putParams("method", "getSeeCheckInfoItems");
                    } else {
                        params.putParams("method", "getCheckInfoItems");
                    }
                    params.putParams("fUserId", objects[1]);
                    params.putParams("type", objects[2]);
                    params.putParams("status", objects[3]);
                    params.putParams("fEntityGuid", objects[4]);
                    params.putParams("pageNo", 1);
                    params.putParams("pageSize", 999);
                    break;
                case HTTP_ID_synergyGetDetailInfo:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("method", "getCheckInfo");
                    params.putParams("type", objects[0]);
                    params.putParams("fUserId", objects[1]);
                    params.putParams("status", objects[2]);
                    params.putParams("fCheckGuid", objects[3]);
                    break;
                case HTTP_ID_synergyGetDTDetailInfo:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("method", "getEquipmentCheckByGuid");
                    params.putParams("type", objects[0]);
                    params.putParams("fUserId", objects[1]);
                    params.putParams("status", objects[2]);
                    params.putParams("fGuid", objects[3]);
                    break;
                case HTTP_ID_synergyGetYJDetailInfo:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("method", "getHiddenDangerInfoByGuid");
                    params.putParams("type", objects[0]);
                    params.putParams("fUserId", objects[1]);
                    params.putParams("status", objects[2]);
                    params.putParams("fGuid", objects[3]);
                    break;
                case HTTP_ID_synergyGetEquipment1:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("method", "getEquipmentByGuid1");
                    params.putParams("fGuid", objects[0]);
                    break;
                case HTTP_ID_equipSearchList:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("method", "getDTByCondition");
                    params.putParams("pageno", objects[0]);
                    params.putParams("pagesize", objects[1]);
                    params.putParams("fetchNum", "1");

                    params.putParams("fEntityName", objects[2]);//使用单位
                    params.putParams("fCategory", objects[3]);//设备类别
                    params.putParams("fCode", objects[4]);//出厂编号
                    params.putParams("fStatus", objects[5]);//使用状态
                    params.putParams("fEquipmentAddress", objects[6]);//使用地址
                    params.putParams("startDate", objects[7]);//起始日期
                    params.putParams("endDate", objects[8]);//截止日期
                    params.putParams("fStation", objects[9]);//区域
                    params.putParams("fMaintenanceName", objects[10]);//维保单位
                    break;
                case HTTP_ID_getPicByEntityId:
                    params.setApiUrl(baseUrl + "GaClientService.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("method", "getPictureByEntityGuid");
                    params.putParams("fEntityGuid", objects[0]);
                    break;
                case HTTP_ID_statistics_case_queryDep:
                    params.setApiUrl(baseUrl + "/TJCase/statistics/queryDep.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("regDateMin", objects[0]);
                    params.putParams("regDateMax", objects[1]);
                    break;
                case HTTP_ID_statistics_case_queryType:
                    params.setApiUrl(baseUrl + "/TJCase/statistics/queryType.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("regDateMin", objects[0]);
                    params.putParams("regDateMax", objects[1]);
                    break;
                case HTTP_ID_statistics_case_queryIsCase:
                    params.setApiUrl(baseUrl + "/TJCase/statistics/queryIsCase.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("regDateMin", objects[0]);
                    params.putParams("regDateMax", objects[1]);
                    break;
                case HTTP_ID_statistics_case_queryClosedCount:
                    params.setApiUrl(baseUrl + "/TJCase/statistics/getClosedCount.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("year", objects[0]);
                    break;
                case HTTP_ID_statistics_case_queryRecordCount:
                    params.setApiUrl(baseUrl + "/TJCase/statistics/getRecordCount.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("year", objects[0]);
                    break;
                case HTTP_ID_statistics_case_queryPunishCount:
                    params.setApiUrl(baseUrl + "/TJCase/statistics/getPunishCount.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("year", objects[0]);
                    break;
                case HTTP_ID_statistics_comp_countDepartment:
                    params.setApiUrl(baseUrl + "/TJComplaint/complaintStat/countByDepartment.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("fRegTimeStart", objects[0]);
                    params.putParams("fRegTimeEnd", objects[1]);
                    break;
                case HTTP_ID_statistics_comp_countType:
                    params.setApiUrl(baseUrl + "/TJComplaint/complaintStat/countByType.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("fRegTimeStart", objects[0]);
                    params.putParams("fRegTimeEnd", objects[1]);
                    break;
                case HTTP_ID_statistics_comp_countInfo:
                    params.setApiUrl(baseUrl + "/TJComplaint/complaintStat/countBySource.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("fRegTimeStart", objects[0]);
                    params.putParams("fRegTimeEnd", objects[1]);
                    break;
                case HTTP_ID_statistics_comp_countBussiniss:
                    params.setApiUrl(baseUrl + "/TJComplaint/complaintStat/countByBusinessSource.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("fRegTimeStart", objects[0]);
                    params.putParams("fRegTimeEnd", objects[1]);
                    break;
                case HTTP_ID_statistics_entity_enterpriseType:
                    params.setApiUrl(baseUrl + "/TJsupervise/homePage/countEnterpriseByType.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    break;
                case HTTP_ID_statistics_entity_enterpriseIndustry:
                    params.setApiUrl(baseUrl + "/TJsupervise/homePage/countEnterpriseByIndustry.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    break;
                case HTTP_ID_statistics_entity_equipmentType:
                    params.setApiUrl(baseUrl + "/TJsupervise/statistics/equipmentType.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    break;
                case HTTP_ID_statistics_entity_enterpriseComplain:
                    params.setApiUrl(baseUrl + "/TJsupervise/homePage/countEnterpriseByComplaint.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    break;
                case HTTP_ID_statistics_entity_enterpriseDev:
                    params.setApiUrl(baseUrl + "/TJsupervise/homePage/countEnterpriseByDev.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("year", objects[0]);
                    break;
                case HTTP_ID_statistics_entity_enterpriseAnn:
                    params.setApiUrl(baseUrl + "/TJsupervise/homePage/countEnterpriseByAnn.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("year", objects[0]);
                    break;
                case HTTP_ID_statistics_entity_enterpriseWarning:
                    params.setApiUrl(baseUrl + "/TJsupervise/homePage/countEnterpriseByWarning.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    break;
                case HTTP_ID_statistics_super_countTask:
                    params.setApiUrl(baseUrl + "/TJsupervise/taskDo/countTaskEnterprise.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("taskId", objects[0]);
                    break;
                case HTTP_ID_statistics_super_countType:
                    params.setApiUrl(baseUrl + "/TJsupervise/taskDo/countType.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("year", objects[0]);
                    break;
                case HTTP_ID_statistics_super_countEnterprise:
                    params.setApiUrl(baseUrl + "/TJsupervise/taskDo/countEnterprise.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("year", objects[0]);
                    break;
                case HTTP_ID_statistics_super_countDoTask:
                    params.setApiUrl(baseUrl + "/TJsupervise/taskDo/countDoTask.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("year", objects[0]);
                    break;
                case HTTP_ID_info_manager_biaozhun:
                    params.setApiUrl(baseUrl + "/TJsupervise/standard/select.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("enterpriseName", ((String) objects[0]).toString());
                    params.putParams("pageNo", objects[1]);
                    params.putParams("pageSize", objects[2]);
//                    params.putParams("fUserId", objects[4]);
                    break;

                case HTTP_ID_info_manager_biaozhun_detail:
                    params.setApiUrl(baseUrl + "/TJsupervise/standard/selectById.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("id", objects[0]);
                    break;
                case HTTP_ID_info_manager_device_liebiao:
                    params.setApiUrl(baseUrl + "/TJsupervise/equipment/getEquipmentPage.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("enterpriseName", objects[2]);
//                    params.putParams("registerCode", objects[3]);
//                    params.putParams("typeName", objects[4]);
//                    params.putParams("categoryName", objects[5]);
//                    params.putParams("status", objects[6]);
//                    params.putParams("originNum", objects[7]);
//                    params.putParams("code", objects[8]);
//                    params.putParams("level", objects[9]);
//                    params.putParams("model", objects[10]);
//                    params.putParams("address", objects[11]);
//                    params.putParams("useNum", objects[7]);
//                    params.putParams("ordered", objects[8]);
//                    params.putParams("registerDateStart", objects[9]);
//                    params.putParams("registerDateEnd", objects[10]);
//                    params.putParams("makeDateStart", objects[11]);
//                    params.putParams("makeDateEnd", objects[12]);
//                    params.putParams("radius", objects[13]);
//                    params.putParams("positionList", objects[14]);
                    break;
                case HTTP_ID_info_manager_device_detail:
                    params.setApiUrl(baseUrl + "/TJsupervise/equipment/getEquipmentInfo.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("id", objects[0]);
                    break;
                case HTTP_ID_info_manager_license_food:
                    params.setApiUrl(baseUrl + "/TJsupervise/license/getFoodEnterprisePage.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("enterpriseName", objects[2]);
//                    params.putParams("licNum", objects[3]);

                    break;
                case HTTP_ID_info_manager_license_drugs:
                    params.setApiUrl(baseUrl + "/TJsupervise/license/getDurgEnterprisePage.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("tableName", objects[2]);
                    params.putParams("enterpriseName", objects[3]);
//                    params.putParams("licNum", objects[4]);
                    break;
                case HTTP_ID_info_manager_license_cosmetics:
                    params.setApiUrl(baseUrl + "/TJsupervise/license/getCosmeticEnterprisePage.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("enterpriseName", objects[2]);
//                    params.putParams("licNum", objects[3]);
                    break;
                case HTTP_ID_info_manager_license_instrument://许可证-医疗器械企业列表
                    params.setApiUrl(baseUrl + "/TJsupervise/license/getEquipmentEnterprisePage.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("tableName", objects[2]);
                    params.putParams("enterpriseName", objects[3]);
//                    params.putParams("licNum", objects[4]);
                    break;
                case HTTP_ID_info_manager_license_detail://许可证-许可证详情
                    params.setApiUrl(baseUrl + "/TJsupervise/enterprise/getLicensesDetail.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("id", objects[0]);
                    break;
                case HTTP_ID_info_manager_measuring_instruments_custom://计量器具-自定义表信息接口
                    params.setApiUrl(baseUrl + "/TJsupervise/defined/getDefinedTableInfo.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("name", objects[0]);
                    break;
                case HTTP_ID_info_manager_measuring_instruments_liebiao://计量器具-计量器具列表接口
                    params.setApiUrl(baseUrl + "/TJsupervise/defined/getDefinedList.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("pageNo", objects[0]);
                    params.putParams("pageSize", objects[1]);
                    params.putParams("tableName", objects[2]);
                    params.putParams("enterpriseName", objects[3]);
                    break;
                case HTTP_ID_info_manager_measuring_instruments_detail:
                    params.setApiUrl(baseUrl + "/TJsupervise/defined/getDefinedDetails.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("id", objects[0]);
                    params.putParams("tableName", objects[1]);
                    break;
                case HTTP_ID_info_manager_legal_query:
                    params.setApiUrl(baseUrl + "/TJsupervise/Law/select.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("departmentCode", objects[0]);
                    params.putParams("id", objects[1]);
                    break;
                case HTTP_ID_info_manager_legal_search:
                    params.setApiUrl(baseUrl + "/TJsupervise/Law/selectLaw.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("content", objects[0]);
                    params.putParams("lawId", objects[1]);
                    params.putParams("directoryId", objects[2]);
                    params.putParams("lawTotalFlag", objects[3]);
                    params.putParams("id", objects[4]);
                    params.putParams("departmentCode", objects[5]);
                    break;
                case HTTP_ID_supervise_saceItemResult:
                    params.setApiUrl(baseUrl + "/TJsupervise/taskDo/saveItemResult.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("id", objects[0]);
                    params.putParams("resultList", objects[1]);
                    params.putParams("illegal", objects[2]);
                    break;
                case HTTP_ID_supervise_finishItem:
                    params.setApiUrl(baseUrl + "/TJsupervise/taskDo/finishTask.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("id", objects[0]);
                    break;
                case HTTP_ID_supervise_getTaskFiles:
                    params.setApiUrl(baseUrl + "/TJsupervise/taskDo/fileList.do");
                    params.setRequestMothod(HTTP_MOTHOD.GET);
                    params.putParams("taskId", objects[0]);
                    break;
                case HTTP_ID_supervise_deleteFile:
                    params.setApiUrl(baseUrl + "/TJsupervise/taskDo/deleteFile.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("id", objects[0]);
                    break;
                case HTTP_ID_case_01:
                    params.setApiUrl(baseUrl + "/TJCase/caseFlow/isCase.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("id", objects[0]);
                    params.putParams("remark", objects[1]);
                    params.putParams("isCase", objects[2]);
                    params.putParams("taskId", objects[3]);
                    break;
                case HTTP_ID_case_Y01:
                    params.setApiUrl(baseUrl + "/TJCase/caseFlow/apply.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("id", objects[0]);
                    params.putParams("remark", objects[1]);
                    params.putParams("taskId", objects[2]);
                    break;
                case HTTP_ID_case_Y02:
                    params.setApiUrl(baseUrl + "/TJCase/caseFlow/auditing.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("id", objects[0]);
                    params.putParams("remark", objects[1]);
                    params.putParams("taskId", objects[2]);
                    params.putParams("isPass", objects[3]);
                    break;
                case HTTP_ID_case_Y03:
                    params.setApiUrl(baseUrl + "/TJCase/caseFlow/examine.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("id", objects[0]);
                    params.putParams("remark", objects[1]);
                    params.putParams("taskId", objects[2]);
                    params.putParams("isPass", objects[3]);
                    break;
                case HTTP_ID_case_Y05:
                    params.setApiUrl(baseUrl + "/TJCase/caseFlow/report.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("id", objects[0]);
                    params.putParams("remark", objects[1]);
                    params.putParams("taskId", objects[2]);
                    break;
                case HTTP_ID_case_Y06:
                    params.setApiUrl(baseUrl + "/TJCase/caseFlow/firstTrial.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("id", objects[0]);
                    params.putParams("remark", objects[1]);
                    params.putParams("taskId", objects[2]);
                    params.putParams("isPass", objects[3]);
                    break;
                case HTTP_ID_case_Y07:
                    params.setApiUrl(baseUrl + "/TJCase/caseFlow/secondTrial.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("id", objects[0]);
                    params.putParams("remark", objects[1]);
                    params.putParams("taskId", objects[2]);
                    params.putParams("isPass", objects[3]);
                    break;
                case HTTP_ID_case_Y08:
                    params.setApiUrl(baseUrl + "/TJCase/caseFlow/notice.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("id", objects[0]);
                    params.putParams("remark", objects[1]);
                    params.putParams("taskId", objects[2]);
                    params.putParams("isPass", objects[3]);
                    break;
                case HTTP_ID_case_Y10:
                    params.setApiUrl(baseUrl + "/TJCase/caseFlow/hearing.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("id", objects[0]);
                    params.putParams("remark", objects[1]);
                    params.putParams("taskId", objects[2]);
                    break;
                case HTTP_ID_case_Y11:
                    params.setApiUrl(baseUrl + "/TJCase/caseFlow/decide.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("id", objects[0]);
                    params.putParams("remark", objects[1]);
                    params.putParams("taskId", objects[2]);
                    break;
                case HTTP_ID_case_Y12:
                    params.setApiUrl(baseUrl + "/TJCase/caseFlow/decideAuditing.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("id", objects[0]);
                    params.putParams("remark", objects[1]);
                    params.putParams("taskId", objects[2]);
                    params.putParams("isPass", objects[3]);
                    break;
                case HTTP_ID_case_Y13:
                    params.setApiUrl(baseUrl + "/TJCase/caseFlow/execute.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("id", objects[0]);
                    params.putParams("remark", objects[1]);
                    params.putParams("taskId", objects[2]);
                    break;
                case HTTP_ID_case_Y14:
                    params.setApiUrl(baseUrl + "/TJCase/caseFlow/closedCase.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("id", objects[0]);
                    params.putParams("remark", objects[1]);
                    params.putParams("taskId", objects[2]);
                    break;
                case HTTP_ID_case_02:
                    params.setApiUrl(baseUrl + "/TJCase/caseFlow/endCase.do");
                    params.setRequestMothod(HTTP_MOTHOD.POST);
                    params.putParams("id", objects[0]);
                    params.putParams("remark", objects[1]);
                    params.putParams("taskId", objects[2]);
                    break;
                default:
                    if (LogUtil.DEBUG) {
                        LogUtil.e(this, "ApiData 请求被遗漏 id:" + id);
                    }
                    break;
                //TODO
            }
            if (id != HTTP_ID_login) {
                params.putParams("tokenId", UUID);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            if (LogUtil.DEBUG) {
                LogUtil.e(this, "请求参数错误 请检查loadData()是否未带参数");
            }
        }
        return params;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected BaseHttpResult parseStr(int id, String currentDownloadText, BaseHttpResult lastData) throws Exception {
        JSONObject list;
        JSONObject jsonObject;
        JSONArray jsonArray;
        Gson gson = new Gson();
        BaseHttpResult result = new BaseHttpResult();
        switch (id) {
            /** api */
            default:
                try {
                    jsonObject = new JSONObject(currentDownloadText);
                } catch (Exception e) {
                    result.setSuccess(false);
                    return result;
                }
                String msg;
                // if (jsonObject.has("InfoMessage")) {
                // Message message = new Message();
                // message.what = 1;
                // BaseActivity activity = new BaseActivity();
                // activity.handler2.sendMessage(message);
                // return null;
                // }
                String code = getStringValue(jsonObject, "code");
                msg = getStringValue(jsonObject, "message");
                if (getStringValue(jsonObject, "InfoMessage").length() > 0) {
                    msg = getStringValue(jsonObject, "InfoMessage");
                }
                result.setMessage(msg);
                result.setSuccess("0".equals(code) || "20000".equals(code) || "1".equals(code));
                if (result.isSuccess()) {
                    switch (id) {
                        case HTTP_ID_login: {
                            list = getJSONObject(jsonObject, "data");
                            HttpLoginEntity loginEntity = gson.fromJson(list.toString(), HttpLoginEntity.class);
                            UUID = loginEntity.getTokenId();
                            result.setEntry(loginEntity);
                        }
                        break;
                        case HTTP_ID_searchzt: {
                            list = getJSONObject(jsonObject, "data");
                            HttpSearchZtEntity searchZtEntity = new HttpSearchZtEntity();
                            searchZtEntity.setCurrPageNo(getIntValue(list, "pageNo"));
                            searchZtEntity.setPageSize(getIntValue(list, "pageSize"));
                            searchZtEntity.setPageTotal(getIntValue(list, "pages"));
                            searchZtEntity.setTotal(getIntValue(list, "total"));
                            JSONArray dataArray = getJSONArray(list, "list");
                            List<HttpZtEntity> ztlist = gson.fromJson(dataArray.toString(), new TypeToken<List<HttpZtEntity>>() {
                            }.getType());
                            searchZtEntity.setZtList(ztlist);
                            result.setEntry(searchZtEntity);
                        }
                        break;
                        case HTTP_ID_searchall: {
                            list = getJSONObject(jsonObject, "data");
                            HttpSearchZtEntity searchZtEntity = new HttpSearchZtEntity();
                            searchZtEntity.setCurrPageNo(getIntValue(list, "currPageNo"));
                            searchZtEntity.setPageSize(getIntValue(list, "pageSize"));
                            searchZtEntity.setPageTotal(getIntValue(list, "pageTotal"));
                            searchZtEntity.setTotal(getIntValue(list, "total"));
                            JSONArray dataArray = getJSONArray(list, "rows");
                            List<HttpZtEntity> ztlist = new ArrayList<>();
//                            for (int i = 0; i < dataArray.length(); i++) {
//                                JSONObject jObj = (JSONObject) dataArray.get(i);
//                                HttpZtEntity zt = new HttpZtEntity();
//                                zt.setGuid(getStringValue(jObj, "fEntityGuid"));
//                                zt.setfEntityType(getStringValue(jObj, "fEntityType"));
//                                zt.setEntityName(getStringValue(jObj, "fEntityName"));
//                                zt.setCreditLevel(getStringValue(jObj, "fCreditLevel"));
//                                zt.setContactInfo(getStringValue(jObj, "fContactInfo"));
//                                zt.setfContactPhone(getStringValue(jObj, "fContactPhone"));
//                                zt.setLegalPerson(getStringValue(jObj, "fLegalPerson"));
//                                zt.setOrgCode(getStringValue(jObj, "fOrgCode"));
//                                zt.setBizlicNum(getStringValue(jObj, "fBizlicNum"));
//                                zt.setLicenses(getStringValue(jObj, "fLicenses").equals("null") ? "" : getStringValue(jObj, "fLicenses"));
//                                zt.setAddress(getStringValue(jObj, "fAddress"));
//                                zt.setfType(getStringValue(jObj, "fType"));
//                                zt.fTags = getStringValue(jObj, "fTags");
//                                JSONObject jObjsInfo = getJSONObject(jObj, "sInfo");
//                                zt.setWkid(getIntValue(jObjsInfo, "spatialReference"));
//                                zt.setLongitude(getStringValue(jObjsInfo, "x"));
//                                zt.setLatitude(getStringValue(jObjsInfo, "y"));
//                                ztlist.add(zt);
//                            }
                            searchZtEntity.setZtList(ztlist);
                            result.setEntry(searchZtEntity);
                        }
                        break;
                        case HTTP_ID_searchzt_Jyfw: {
                            String strData = getStringValue(jsonObject, "data");
                            String strJyfw = "";
                            if (strData != null && !strData.isEmpty()) {
                                JSONArray dataArray = new JSONArray(strData);
                                if (dataArray.length() > 0) {
                                    JSONObject jObj = (JSONObject) dataArray.get(0);
                                    strJyfw = getStringValue(jObj, "value");
                                }
                            }
                            result.setEntry(strJyfw);
                        }
                        break;
                        case HTTP_ID_search_taskcount: {
                            list = getJSONObject(jsonObject, "data");
                            HttpTaskCountEntity taskCountEntity = new HttpTaskCountEntity();

                            JSONObject joProcessing = getJSONObject(list, "processingTask");
                            taskCountEntity.setProcessingAll(getIntValue(joProcessing, "totalCount"));
                            taskCountEntity.setProcessingJjdq(getIntValue(joProcessing, "expiringCount"));
                            taskCountEntity.setProcessingYq(getIntValue(joProcessing, "expiredCount"));

                            JSONObject joPending = getJSONObject(list, "pendingTask");
                            taskCountEntity.setPendingAll(getIntValue(joPending, "totalCount"));
                            taskCountEntity.setPendingJjdq(getIntValue(joPending, "expiringCount"));
                            taskCountEntity.setPendingYq(getIntValue(joPending, "expiredCount"));

                            JSONObject joMsg = getJSONObject(list, "msgCount");
                            taskCountEntity.setMsgTotal(getIntValue(joMsg, "total"));
                            taskCountEntity.setMsgNotread(getIntValue(joMsg, "notread"));

                            result.setEntry(taskCountEntity);
                        }
                        break;
                        case HTTP_ID_search_task_bypage: {
                            list = getJSONObject(jsonObject, "data");
                            HttpTaskListEntity taskListEntity = new HttpTaskListEntity();
                            taskListEntity.setCurrPageNo(getIntValue(list, "currPageNo"));
                            taskListEntity.setPageSize(getIntValue(list, "pageSize"));
                            taskListEntity.setPageTotal(getIntValue(list, "pageTotal"));
                            taskListEntity.setTotal(getIntValue(list, "total"));
                            JSONArray dataArray = getJSONArray(list, "rows");
                            List<HttpTaskEntity> tasklist = new ArrayList<>();
                            if (dataArray != null) {
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject jObj = (JSONObject) dataArray.get(i);
                                    HttpTaskEntity task = new HttpTaskEntity();
                                    task.setGuid(getStringValue(jObj, "fGuid"));
                                    task.setTaskName(getStringValue(jObj, "fTaskName"));
                                    task.setEntityGuid(getStringValue(jObj, "fEntityGuid"));
                                    task.setEntityName(getStringValue(jObj, "fEntityName"));
                                    task.setTaskTime(getStringValue(jObj, "dateTime"));
                                    task.setAddress(getStringValue(jObj, "fAddress"));
                                    task.setTaskType(getIntValue(jObj, "tasktype"));
                                    // task.setTimeType(getIntValue(jObj, "timeType"));

                                    JSONObject jObjsInfo = getJSONObject(jObj, "sInfo");
                                    task.setWkid(getIntValue(jObjsInfo, "spatialReference"));
                                    task.setLongitude(getDoubleValue(jObjsInfo, "x"));
                                    task.setLatitude(getDoubleValue(jObjsInfo, "y"));
                                    tasklist.add(task);
                                }
                            }
                            taskListEntity.setTaskList(tasklist);
                            result.setEntry(taskListEntity);
                        }
                        break;
                        case HTTP_ID_searchtask_detail: {
                            list = getJSONObject(jsonObject, "data");
                            HttpTaskEntity taskEntity = new HttpTaskEntity();
                            // 任务信息
                            JSONObject jsonobjTaskinfo = getJSONObject(list, "taskInfo");
                            if (jsonobjTaskinfo != null) {
                                taskEntity.setTaskId(getStringValue(jsonobjTaskinfo, "fTaskId"));
                                taskEntity.setSource(getStringValue(jsonobjTaskinfo, "fSource"));
                                taskEntity.setRemark(getStringValue(jsonobjTaskinfo, "fRemark"));
                                taskEntity.setEntityGuid(getStringValue(jsonobjTaskinfo, "fEntityGuid"));
                                taskEntity.setSubmitRemark(getStringValue(jsonobjTaskinfo, "fSubmitRemark"));
                                taskEntity.setDeadline(getStringValue(jsonobjTaskinfo, "fDeadline"));
                                taskEntity.setTaskTime(getStringValue(jsonobjTaskinfo, "fDispatchTime"));
                                JSONObject jObjsInfo = getJSONObject(jsonobjTaskinfo, "sInfo");
                                taskEntity.setWkid(getIntValue(jObjsInfo, "spatialReference"));
                                taskEntity.setLongitude(getDoubleValue(jObjsInfo, "x"));
                                taskEntity.setLatitude(getDoubleValue(jObjsInfo, "y"));

                                taskEntity.setStatus(getStringValue(jsonobjTaskinfo, "fStatus"));
                            }
                            // 相关主体信息
                            HttpZtEntity zt = new HttpZtEntity();
                            if (!list.isNull("entityInfo")) {
//                                JSONObject jsonobjZtinfo = getJSONObject(list, "entityInfo");
//                                if (jsonobjZtinfo != null) {
//                                    zt.setJyfw(getStringValue(jsonobjZtinfo, "fScope"));
//                                    zt.setEntityName(getStringValue(jsonobjZtinfo, "fEntityName"));
//                                    taskEntity.setEntityName(getStringValue(jsonobjZtinfo, "fEntityName"));
//                                    zt.setCreditLevel(getStringValue(jsonobjZtinfo, "fCreditLevel"));
//                                    zt.setContactInfo(getStringValue(jsonobjZtinfo, "fContactInfo"));
//                                    zt.setLegalPerson(getStringValue(jsonobjZtinfo, "fLegalPerson"));
//                                    zt.setOrgCode(getStringValue(jsonobjZtinfo, "fOrgCode"));
//                                    zt.setBizlicNum(getStringValue(jsonobjZtinfo, "fBizlicNum"));
//                                    zt.setLicenses(getStringValue(jsonobjZtinfo, "fLicenses"));
//                                    zt.setAddress(getStringValue(jsonobjZtinfo, "fAddress"));
//                                    taskEntity.setAddress(getStringValue(jsonobjZtinfo, "fAddress"));
//                                }
                                taskEntity.setZtEntity(zt);
                            }
                            // 任务操作信息（投诉信息或检查指标）
                            if (!list.isNull("complaintInfo")) {
//                                JSONObject jsonobjComplaint = getJSONObject(list, "complaintInfo");
//                                if (jsonobjComplaint != null) {
//                                    ComplaintInfoEntity complaint = new Gson().fromJson(jsonobjComplaint.toString(), ComplaintInfoEntity.class);
//                                    taskEntity.setComplaintInfo(complaint);
//                                }
                            } else if (list.has("checkItemInfo")) {
                                List<CheckItemInfoEntity> checkitemList = new ArrayList<>();
                                JSONArray jsonArrayChecks = getJSONArray(list, "checkItemInfo");
                                if (jsonArrayChecks != null) {
                                    for (int i = 0; i < jsonArrayChecks.length(); i++) {
                                        JSONObject jObj = (JSONObject) jsonArrayChecks.get(i);
                                        if (jObj != null) {
                                            CheckItemInfoEntity check = new CheckItemInfoEntity();
                                            check.setItemId(getStringValue(jObj, "fItemId"));
                                            check.setItemName(getStringValue(jObj, "fItemName"));
                                            check.setCheckResult(getDoubleValue(jObj, "fCheckResult"));
                                            check.setValueType(getIntValue(jObj, "fValueType"));
                                            check.setValueMin(getDoubleValue(jObj, "fValueMin"));
                                            check.setValueMax(getDoubleValue(jObj, "fValueMax"));
                                            checkitemList.add(check);
                                        }

                                    }
                                }
                                taskEntity.setCheckItemList(checkitemList);
                            }
                            result.setEntry(taskEntity);
                        }
                        break;
                        case HTTP_ID_task_execute:
                            break;
                        case HTTP_ID_task_commit:
                            break;
                        case HTTP_ID_task_check_Save:
                            break;
                        case HTTP_ID_msg_notread_count: {
                            list = getJSONObject(jsonObject, "data");
                            int notread = getIntValue(list, "notread");
                            result.setEntry(notread);
                        }
                        break;
                        case HTTP_ID_msg_center: {
                            list = getJSONObject(jsonObject, "data");
                            HttpMsgListEntity msgListEntity = new HttpMsgListEntity();
                            msgListEntity.setTotal(getIntValue(list, "total"));
                            msgListEntity.setNoread(getIntValue(list, "notread"));
                            JSONArray dataArray = getJSONArray(list, "rows");
                            List<MsgEntity> msgList = new ArrayList<>();
                            if (dataArray != null) {
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject jObj = (JSONObject) dataArray.get(i);
                                    if (jObj != null) {
                                        MsgEntity msgEntity = new MsgEntity();
                                        msgEntity.setGuid(getStringValue(jObj, "msgId"));
                                        msgEntity.setName(getStringValue(jObj, "msgName"));
                                        msgEntity.setType(getStringValue(jObj, "msgType"));
                                        msgEntity.setTime(getStringValue(jObj, "msgTime"));
                                        msgEntity.setContent(getStringValue(jObj, "msgContent"));
                                        msgEntity.setEventId(jObj.getLong("msgEventId"));
                                        msgEntity.setStatus(getIntValue(jObj, "msgStutas"));
                                        msgEntity.setMsgUserid(getStringValue(jObj, "msgUserId"));

                                        msgList.add(msgEntity);
                                    }
                                }
                            }
                            msgListEntity.setMsgList(msgList);

                            result.setEntry(msgListEntity);
                        }
                        break;
                        case HTTP_ID_searchevent_detail: {
                            jsonObject = getJSONObject(jsonObject, "data");
                            EmergencyListInfo entityInfo = new EmergencyListInfo();
                            entityInfo.setfGuid(getStringValue(jsonObject, "fGuid"));
                            entityInfo.setfEntityGuid(getStringValue(jsonObject, "fEntityGuid"));
                            entityInfo.setfContent(getStringValue(jsonObject, "fContent"));
                            entityInfo.setfCategory(getStringValue(jsonObject, "fCategory"));
                            entityInfo.setfType(getStringValue(jsonObject, "fType"));
                            entityInfo.setfStatus(getStringValue(jsonObject, "fStatus"));
                            entityInfo.setfEquipmentGuid(getStringValue(jsonObject, "fEquipmentGuid"));
                            entityInfo.setfEntityName(getStringValue(jsonObject, "fEntityName"));
                            entityInfo.setfAddress(getStringValue(jsonObject, "fAddress"));
                            entityInfo.setfInsertDate(getStringValue(jsonObject, "fInsertDate"));
                            entityInfo.setfLatitude(getDoubleValue(jsonObject, "fLatitude"));
                            entityInfo.setfLongitude(getDoubleValue(jsonObject, "fLongitude"));
                            entityInfo.setfEntityId(getStringValue(jsonObject, "fEntityId"));
                            entityInfo.setfLegalPerson(getStringValue(jsonObject, "fLegalPerson"));
                            entityInfo.setImg(getStringValue(jsonObject, "img"));
                            entityInfo.setfCreditInfo(getStringValue(jsonObject, "fCreditInfo"));
                            entityInfo.setfCheckResult(getStringValue(jsonObject, "fCheckResult"));
                            entityInfo.setfUniscid(getStringValue(jsonObject, "fUniscid"));
                            entityInfo.setfItemGuid(getStringValue(jsonObject, "fItemGuid"));
                            entityInfo.setfPhoneModify(getStringValue(jsonObject, "fPhoneModify"));
                            entityInfo.setfCreditLevel(getStringValue(jsonObject, "fCreditLevel"));
                            entityInfo.setfEntityType(getStringValue(jsonObject, "fEntityType"));
                            entityInfo.setfBizlicNum(getStringValue(jsonObject, "fBizlicNum"));
                            entityInfo.setfContactPeople(getStringValue(jsonObject, "fContactPeople"));
                            entityInfo.setfEnttypename(getStringValue(jsonObject, "fEnttypename"));
                            entityInfo.setfIndustry(getStringValue(jsonObject, "fIndustry"));
                            entityInfo.setfTags(getStringValue(jsonObject, "fTags"));
                            entityInfo.setfContactAddress(getStringValue(jsonObject, "fContactAddress"));
                            entityInfo.setfFoundDate(getStringValue(jsonObject, "fFoundDate"));
                            entityInfo.setfContactPhone(getStringValue(jsonObject, "fContactPhone"));
                            entityInfo.setfLicenses(getStringValue(jsonObject, "fLicenses"));
                            entityInfo.setfNbxh(getStringValue(jsonObject, "fNbxh"));
                            entityInfo.setfBizTypes(getStringValue(jsonObject, "fBizTypes"));
                            entityInfo.setfRemark(getStringValue(jsonObject, "fRemark"));
                            entityInfo.setfStation(getStringValue(jsonObject, "fStation"));
                            entityInfo.setfGrid(getStringValue(jsonObject, "fGrid"));
                            entityInfo.setfOrgCode(getStringValue(jsonObject, "fOrgCode"));
                            entityInfo.setfContactInfo(getStringValue(jsonObject, "fContactInfo"));
                            result.setEntry(entityInfo);
                        }
                        break;
                        case HTTP_ID_searchzt_detail:

                            break;
                        case HTTP_ID_event_response:
                            break;
                        case HTTP_ID_event_checkin:
                            break;
                        case HTTP_ID_event_report:
                            break;
                        case HTTP_ID_set_msg_read:
                            break;
                        case HTTP_ID_change_pos:
                            break;

                        case HTTP_ID_version_update:
                            list = getJSONObject(jsonObject, "data");
                            HttpUpdateEntity updateEntity = gson.fromJson(list.toString(), HttpUpdateEntity.class);
//                            new HttpUpdateEntity();
//                            resEntity.fCommitDate = getStringValue(list, "fCommitDate");
//                            resEntity.fGuid = getStringValue(list, "fGuid");
//                            resEntity.fRemark = getStringValue(list, "fRemark");
//                            resEntity.fUrl = getStringValue(list, "fUrl");
//                            resEntity.fVersionName = getStringValue(list, "fVersionName");
//                            resEntity.fVersionCode = getIntValue(list, "fVersionCode");
//                            resEntity.fContent = getStringValue(list, "fContent");
                            result.setEntry(updateEntity);
                            break;

                        case HTTP_ID_user_station:
                            result.setEntry(getStringValue(jsonObject, "data").replace("[", "").replace("]", "").replace("\"", ""));
                            break;
                        case HTTP_ID_supervisetask_list:
                            JSONObject superviseJson = getJSONObject(jsonObject, "data");
                            SuperviseEntity superviseEntity = new SuperviseEntity();
                            superviseEntity.currPageNo = getIntValue(superviseJson, "currPageNo");
                            superviseEntity.pageSize = getIntValue(superviseJson, "pageSize");
                            superviseEntity.pageTotal = getIntValue(superviseJson, "pageTotal");
                            superviseEntity.total = getIntValue(superviseJson, "total");
                            JSONArray superviseArray = getJSONArray(superviseJson, "rows");
                            List<SuperviseInfo> superviseList = new ArrayList<>();
                            for (int i = 0; i < superviseArray.length(); i++) {
                                SuperviseInfo superviseInfo = new Gson().fromJson(superviseArray.getString(i), SuperviseInfo.class);
                                superviseList.add(superviseInfo);
                            }
                            superviseEntity.superviseInfoList = superviseList;
                            result.setEntry(superviseEntity);
                            break;
                        case HTTP_ID_supervisetask_detail:
                            String strlist = getStringValue(jsonObject, "data");
                            SuperviseDetailInfo superviseInfo = new Gson().fromJson(strlist, SuperviseDetailInfo.class);
                            result.setEntry(superviseInfo);
                            break;
                        case HTTP_ID_entity_detail:
                            String strEntity = getStringValue(jsonObject, "data");
                            EntityDetail entityDetail = new Gson().fromJson(strEntity, EntityDetail.class);
                            result.setEntry(entityDetail);
                            break;
                        case HTTP_ID_supervisetask_searchcheck:
                            JSONArray array = getJSONArray(jsonObject, "data");
                            List<CheckInfo> checkList = gson.fromJson(array.toString(), new TypeToken<List<CheckInfo>>() {
                            }.getType());
                            result.setEntry(checkList);
                            break;
                        case HTTP_ID_supervisetasktype_list:
                            List<SelectPopDataList> supervisekList = new ArrayList<>();
                            JSONArray superviseArray2 = getJSONArray(jsonObject, "data");
                            for (int i = 0; i < superviseArray2.length(); i++) {
                                String strSuperviseTask = superviseArray2.getString(i);
                                SelectPopDataList superviseTaskDetail = new Gson().fromJson(strSuperviseTask, SelectPopDataList.class);
                                supervisekList.add(superviseTaskDetail);
                            }
                            result.setEntry(supervisekList);
                            break;
                        case HTTP_ID_supervisetasktype_count:
                            List<SuperviseCountInfo> superviseCountkList = new ArrayList<>();
                            JSONArray superviseCountArray = getJSONArray(jsonObject, "data");
                            for (int i = 0; i < superviseCountArray.length(); i++) {
                                JSONObject superviseCountJson = superviseCountArray.getJSONObject(i);
                                SuperviseCountInfo superviseCount = new SuperviseCountInfo();
                                if (superviseCountJson.has("dcz")) {// 待处置
                                    if (superviseCountJson.has("f_real_name")) {// 自办
                                        superviseCount.setZiban(true);
                                        superviseCount.setRealName(getStringValue(superviseCountJson, "f_real_name"));
                                        superviseCount.setUserid(getStringValue(superviseCountJson, "f_user_id"));
                                        superviseCount.setGridAlias(getStringValue(superviseCountJson, "fDepartmentAlias"));
                                    } else {// 非自办
                                        superviseCount.setZiban(false);
                                    }
                                    superviseCount.setfGroupName(getStringValue(superviseCountJson, "fGroupName"));
                                    superviseCount.setF_GROUP_ID(getStringValue(superviseCountJson, "F_GROUP_ID"));
                                    superviseCount.setGrid(getStringValue(superviseCountJson, "f_grid"));
                                    superviseCount.setTotal(getStringValue(superviseCountJson, "total"));
                                    superviseCount.setCount1(getStringValue(superviseCountJson, "dcz"));
                                    superviseCount.setCount2(getStringValue(superviseCountJson, "ycz"));
                                } else if (superviseCountJson.has("dcs")) {// 待初审
                                    if (superviseCountJson.has("f_real_name")) {// 自办
                                        superviseCount.setZiban(true);
                                        superviseCount.setRealName(getStringValue(superviseCountJson, "f_real_name"));
                                        superviseCount.setUserid(getStringValue(superviseCountJson, "f_user_id"));
                                        superviseCount.setGridAlias(getStringValue(superviseCountJson, "fDepartmentAlias"));
                                    } else {// 非自办
                                        superviseCount.setZiban(false);
                                    }
                                    superviseCount.setfGroupName(getStringValue(superviseCountJson, "fGroupName"));
                                    superviseCount.setF_GROUP_ID(getStringValue(superviseCountJson, "F_GROUP_ID"));
                                    superviseCount.setGrid(getStringValue(superviseCountJson, "f_grid"));
                                    superviseCount.setTotal(getStringValue(superviseCountJson, "total"));
                                    superviseCount.setCount1(getStringValue(superviseCountJson, "blz"));
                                    superviseCount.setCount2(getStringValue(superviseCountJson, "dcs"));
                                    superviseCount.setCount3(getStringValue(superviseCountJson, "ycs"));
                                } else if (superviseCountJson.has("dhs")) {// 待核审
                                    superviseCount.setZiban(false);// 待核审默认设置为非自办，为UI判定时用
                                    superviseCount.setfGroupName(getStringValue(superviseCountJson, "fGroupName"));
                                    superviseCount.setF_GROUP_ID(getStringValue(superviseCountJson, "F_GROUP_ID"));
                                    superviseCount.setGrid(getStringValue(superviseCountJson, "f_department"));
                                    superviseCount.setGridAlias(getStringValue(superviseCountJson, "fDepartmentAlias"));
                                    superviseCount.setTotal(getStringValue(superviseCountJson, "total"));
                                    superviseCount.setCount1(getStringValue(superviseCountJson, "blz"));
                                    superviseCount.setCount2(getStringValue(superviseCountJson, "dhs"));
                                    superviseCount.setCount3(getStringValue(superviseCountJson, "yhs"));
                                } else if (superviseCountJson.has("dzs")) {// 待终审
                                    superviseCount.setZiban(false);// 待终审默认设置为非自办，为UI判定时用
                                    superviseCount.setfGroupName(getStringValue(superviseCountJson, "fGroupName"));
                                    superviseCount.setF_GROUP_ID(getStringValue(superviseCountJson, "F_GROUP_ID"));
                                    superviseCount.setGrid(getStringValue(superviseCountJson, "f_department"));
                                    superviseCount.setGridAlias(getStringValue(superviseCountJson, "fDepartmentAlias"));
                                    superviseCount.setTotal(getStringValue(superviseCountJson, "total"));
                                    superviseCount.setCount1(getStringValue(superviseCountJson, "blz"));
                                    superviseCount.setCount2(getStringValue(superviseCountJson, "dzs"));
                                    superviseCount.setCount3(getStringValue(superviseCountJson, "yzs"));
                                }
                                superviseCountkList.add(superviseCount);
                            }
                            result.setEntry(superviseCountkList);
                            break;
                        case HTTP_ID_entity_list:
                            JSONObject entityJson = getJSONObject(jsonObject, "data");
                            EntityListInfo entityLisiInfo = new EntityListInfo();
                            entityLisiInfo.setCurrPageNo(getIntValue(entityJson, "currPageNo"));
                            entityLisiInfo.setPageSize(getIntValue(entityJson, "pageSize"));
                            entityLisiInfo.setPageTotal(getIntValue(entityJson, "pageTotal"));
                            entityLisiInfo.setTotal(getIntValue(entityJson, "total"));
                            List<EntitySimpleInfo> entityList = new ArrayList<>();
                            JSONArray entityArray = getJSONArray(entityJson, "rows");
                            for (int i = 0; i < entityArray.length(); i++) {
                                String entityStr = entityArray.getString(i);
                                EntitySimpleInfo entityInfo = new Gson().fromJson(entityStr, EntitySimpleInfo.class);
                                entityList.add(entityInfo);
                            }
                            entityLisiInfo.setEntityList(entityList);
                            result.setEntry(entityLisiInfo);
                            break;
                        case HTTP_ID_superviseoperate_detail:
//                            JSONObject superviseOperateJson = getJSONObject(jsonObject, "data");
//                            SuperviseDetailInfo operateInfo = new SuperviseDetailInfo();
//                            List<CheckInfo> checInfokList = new ArrayList<>();
//                            JSONArray operateArray = getJSONArray(superviseOperateJson, "checkitem");
//                            for (int i = 0; i < operateArray.length(); i++) {
//                                JSONObject checkJson = operateArray.getJSONObject(i);
//                                CheckInfo check = new CheckInfo();
//                                check.fCheckResult = getStringValue(checkJson, "fCheckResult");
//                                check.fItemName = getStringValue(checkJson, "fItemName");
//                                check.fValueType = getStringValue(checkJson, "fValueType");
//                                check.fValueMax = getStringValue(checkJson, "fValueMax");
//                                check.fValueMin = getStringValue(checkJson, "fValueMin");
//                                checInfokList.add(check);
//                            }
//                            operateInfo.setCheckList(checInfokList);
//                            JSONObject operateJson = getJSONObject(superviseOperateJson, "task");
//                            operateInfo.setfLeaderUserId(getStringValue(operateJson, "fLeaderUserId"));
//                            operateInfo.setfReviewUserId(getStringValue(operateJson, "fReviewUserId"));
//                            operateInfo.setTaskCode(getStringValue(operateJson, "taskId"));
//                            operateInfo.setTaskName(getStringValue(operateJson, "taskName"));
//                            operateInfo.setTaskResource(getStringValue(operateJson, "source"));
//                            operateInfo.setTaskDispatchTime(getStringValue(operateJson, "startTime"));
//                            operateInfo.setTaskDeadlineTime(getStringValue(operateJson, "endTime"));
//                            operateInfo.setTaskProgress(getStringValue(operateJson, "status"));
//                            operateInfo.setTaskArea(getStringValue(operateJson, "grid"));
//                            operateInfo.setTaskRemark(getStringValue(operateJson, "reamrk"));
//                            operateInfo.setResult(getStringValue(operateJson, "result"));
//                            operateInfo.setLegalPerson(getStringValue(getJSONObject(superviseOperateJson, "entity"), "legalPerson"));
//                            result.setEntry(operateInfo);
                            break;
                        case HTTP_ID_supervisetasktype_end:
                            break;
                        case HTTP_ID_supervisetask_statusupdate:
                            break;
                        case HTTP_ID_supervisetask_queryuserentity:
                            JSONObject userentityJson = getJSONObject(jsonObject, "data");
                            EntityListInfo userentityLisiInfo = new EntityListInfo();
                            userentityLisiInfo.setCurrPageNo(getIntValue(userentityJson, "currPageNo"));
                            userentityLisiInfo.setPageSize(getIntValue(userentityJson, "pageSize"));
                            userentityLisiInfo.setPageTotal(getIntValue(userentityJson, "pageTotal"));
                            userentityLisiInfo.setTotal(getIntValue(userentityJson, "total"));
                            List<EntitySimpleInfo> userentityList = new ArrayList<>();
                            JSONArray userentityArray = getJSONArray(userentityJson, "rows");
                            for (int i = 0; i < userentityArray.length(); i++) {
                                String entityStr = userentityArray.getString(i);
                                EntitySimpleInfo entityInfo = new Gson().fromJson(entityStr, EntitySimpleInfo.class);
                                userentityList.add(entityInfo);
                            }
                            userentityLisiInfo.setEntityList(userentityList);
                            result.setEntry(userentityLisiInfo);
                            break;
                        case HTTP_ID_supervisetask_queryallentity:
                            JSONObject allentityJson = getJSONObject(jsonObject, "data");
                            EntityListInfo allentityLisiInfo = new EntityListInfo();
                            allentityLisiInfo.setCurrPageNo(getIntValue(allentityJson, "currPageNo"));
                            allentityLisiInfo.setPageSize(getIntValue(allentityJson, "pageSize"));
                            allentityLisiInfo.setPageTotal(getIntValue(allentityJson, "pageTotal"));
                            allentityLisiInfo.setTotal(getIntValue(allentityJson, "total"));
                            List<EntitySimpleInfo> allentityList = new ArrayList<>();
                            JSONArray allentityArray = getJSONArray(allentityJson, "rows");
                            for (int i = 0; i < allentityArray.length(); i++) {
                                String entityStr = allentityArray.getString(i);
                                EntitySimpleInfo entityInfo = new Gson().fromJson(entityStr, EntitySimpleInfo.class);
                                allentityList.add(entityInfo);
                            }
                            allentityLisiInfo.setEntityList(allentityList);
                            result.setEntry(allentityLisiInfo);
                            break;
                        case HTTP_ID_supervisetasktype_reviewlist:
                            List<SelectPopDataList> supervisekReviewList = new ArrayList<>();
                            JSONArray superviseArray3 = getJSONArray(jsonObject, "data");
                            for (int i = 0; i < superviseArray3.length(); i++) {
                                String strSuperviseTask = superviseArray3.getString(i);
                                SelectPopDataList superviseTaskDetail = new Gson().fromJson(strSuperviseTask, SelectPopDataList.class);
                                supervisekReviewList.add(superviseTaskDetail);
                            }
                            result.setEntry(supervisekReviewList);
                            break;
                        case HTTP_ID_supervisetask_monitorlist:
                            HttpMonitor monitor = new HttpMonitor();
                            JSONObject monitorJson = getJSONObject(jsonObject, "data");
                            // 数量信息
                            JSONObject numJson = getJSONObject(monitorJson, "number");
                            monitor.setTotal(getIntValue(numJson, "total"));
                            monitor.setTheOverdue(getIntValue(numJson, "theOverdue"));
                            monitor.setOverdue(getIntValue(numJson, "overdue"));
                            // 主体或者任务信息
                            if (monitorJson.has("entitys")) {
                                monitor.setInfoType(0);
                                HttpMonitorEntityList monitorEntityList = new HttpMonitorEntityList();
                                JSONObject entitysJson = getJSONObject(monitorJson, "entitys");
                                monitorEntityList.setTotal(getIntValue(entitysJson, "total"));
                                monitorEntityList.setPageTotal(getIntValue(entitysJson, "pageTotal"));
                                monitorEntityList.setPageNo(getIntValue(entitysJson, "pageNo"));
                                monitorEntityList.setPageSize(getIntValue(entitysJson, "pageSize"));
                                List<SuperviseInfo> entitys = new ArrayList<>();
                                JSONArray entitysArray = getJSONArray(entitysJson, "rows");
                                for (int i = 0; i < entitysArray.length(); i++) {
                                    SuperviseInfo entity = new SuperviseInfo();
                                    JSONObject entityObject = entitysArray.getJSONObject(i);
                                    entity.taskId = getStringValue(entityObject, "fTaskId");
                                    entity.taskName = getStringValue(entityObject, "fTaskName");
                                    entity.timeType = getIntValue(entityObject, "timeType");
                                    entity.dispatchTime = getStringValue(entityObject, "fDispatchTime");
                                    if (entityObject.has("fAddress")) {
                                        entity.address = getStringValue(entityObject, "fAddress");
                                    }
                                    entity.entityName = getStringValue(entityObject, "fEntityName");
                                    entity.guid = getStringValue(entityObject, "fGuid");
                                    entity.entityGuid = getStringValue(entityObject, "fEntityGuid");
                                    entity.fCreditLevel = getStringValue(entityObject, "fCredtieLevel");
                                    JSONObject sInfoObjsInfo = getJSONObject(entityObject, "sInfo");
                                    entity.longitude = getDoubleValue(sInfoObjsInfo, "x");
                                    entity.latitude = getDoubleValue(sInfoObjsInfo, "y");

                                    entitys.add(entity);
                                }
                                monitorEntityList.setEntityList(entitys);
                                monitor.setMonitorEntityList(monitorEntityList);

                            }
                            if (monitorJson.has("taskInfo")) {
                                monitor.setInfoType(1);
                                HttpMonitorTaskList monitorTaskList = new HttpMonitorTaskList();
                                JSONArray tasksArray = getJSONArray(monitorJson, "taskInfo");
                                List<SuperviseCountInfo> tasks = new ArrayList<>();
                                for (int i = 0; i < tasksArray.length(); i++) {
                                    SuperviseCountInfo task = new SuperviseCountInfo();
                                    JSONObject taskObject = tasksArray.getJSONObject(i);
                                    // fDepartment: 第二所"
                                    task.setForDisposal(getStringValue(taskObject, "forDisposal"));// 此处为部门名
                                    task.setfGroupName(getStringValue(taskObject, "F_GROUP_NAME"));// 此处为部门别名
                                    task.setF_GROUP_ID(getStringValue(taskObject, "F_GROUP_ID"));
                                    task.setTotal(getStringValue(taskObject, "forDisposal"));
                                    task.setCount1(getStringValue(taskObject, "forFirstTrial"));
                                    task.setCount2(getStringValue(taskObject, "forSecondTrial"));
                                    task.setCount3(getStringValue(taskObject, "forFinal"));
                                    task.setCount4(getStringValue(taskObject, "finish"));

                                    tasks.add(task);
                                }
                                monitorTaskList.setTaskList(tasks);
                                monitor.setMonitorTaskList(monitorTaskList);
                            }
                            result.setEntry(monitor);
                            break;
                        case HTTP_ID_supervisetask_querystatusentity:
                            JSONObject sEntityJson = getJSONObject(getJSONObject(jsonObject, "data"), "entitys");
                            EntityListInfo sEntityLisiInfo = new EntityListInfo();
                            sEntityLisiInfo.setCurrPageNo(getIntValue(sEntityJson, "pageNo"));
                            sEntityLisiInfo.setPageSize(getIntValue(sEntityJson, "pageSize"));
                            sEntityLisiInfo.setPageTotal(getIntValue(sEntityJson, "pageTotal"));
                            sEntityLisiInfo.setTotal(getIntValue(sEntityJson, "total"));
                            List<EntitySimpleInfo> sEntityList = new ArrayList<>();
                            JSONArray sEntityArray = getJSONArray(sEntityJson, "rows");
                            for (int i = 0; i < sEntityArray.length(); i++) {
                                JSONObject entityStr = sEntityArray.getJSONObject(i);
                                EntitySimpleInfo entityInfo = new EntitySimpleInfo();
                                entityInfo.setF_Guid(getStringValue(entityStr, "fGuid"));
                                entityInfo.setF_Entity_Guid(getStringValue(entityStr, "fEntityGuid"));
                                entityInfo.setF_Entity_Name(getStringValue(entityStr, "fEntityName"));
                                entityInfo.setfCredtieLevel(getStringValue(entityStr, "fCredtieLevel"));
                                sEntityList.add(entityInfo);
                            }
                            sEntityLisiInfo.setEntityList(sEntityList);
                            result.setEntry(sEntityLisiInfo);
                            break;
                        case HTTP_ID_getCheckInfos:
                            setEmergencyListInfo(jsonObject, result);
                            break;
                        case HTTP_ID_countEntityType:
                        case HTTP_ID_statistics_single_parameter:
                            JSONArray statisticsJson = getJSONArray(jsonObject, "data");
                            List<KeyValueInfo> keyList = new ArrayList<>();
                            double countNum = 0;
                            boolean hasCount = false;
                            for (int i = 0; i < statisticsJson.length(); i++) {
                                KeyValueInfo keyValue = new KeyValueInfo();
                                keyValue.key = getStringValue(statisticsJson.getJSONObject(i), "name");
                                double dNum = getDoubleValue(statisticsJson.getJSONObject(i), "num");
                                if ("总计".equals(keyValue.key) || "总数".equals(keyValue.key) || "合计".equals(keyValue.key)) {
                                    hasCount = true;
                                } else {
                                    countNum += dNum;
                                }
                                if (dNum % 1.0 == 0) {
                                    keyValue.value = String.valueOf((long) dNum);
                                } else {
                                    Double d = getDoubleValue(statisticsJson.getJSONObject(i), "num");
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    keyValue.value = df.format(d);
                                }
                                keyList.add(keyValue);
                                if (statisticsJson.getJSONObject(i).has("code")) {
                                    keyValue.code = getStringValue(statisticsJson.getJSONObject(i), "code");
                                }
                            }
                            if (!hasCount) {
                                KeyValueInfo keyValue = new KeyValueInfo();
                                keyValue.key = "总计";
                                if (countNum % 1.0 == 0) {
                                    keyValue.value = String.valueOf((long) countNum);
                                } else {
                                    Double d = countNum;
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    keyValue.value = df.format(d);
                                }
                                keyList.add(keyValue);
                            }

                            result.setEntry(keyList);
                            break;
                        case HTTP_ID_statistics_hzp:
                            JSONArray hzpArray = getJSONArray(jsonObject, "data");
                            List<KeyValueInfo> hzpList = new ArrayList<>();
                            double countNumhzp1 = 0;
                            double countNumhzp2 = 0;
                            for (int i = 0; i < hzpArray.length(); i++) {
                                KeyValueInfo keyValue = new KeyValueInfo();
                                keyValue.key = getStringValue(hzpArray.getJSONObject(i), "NAME");
                                double dNum1 = getDoubleValue(hzpArray.getJSONObject(i), "美容");
                                double dNum2 = getDoubleValue(hzpArray.getJSONObject(i), "美发");
                                countNumhzp1 += dNum1;
                                countNumhzp2 += dNum2;
                                if (dNum1 % 1.0 == 0) {
                                    keyValue.value = String.valueOf((long) dNum1);
                                } else {
                                    Double d = getDoubleValue(hzpArray.getJSONObject(i), "num");
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    keyValue.value = df.format(d);
                                }
                                if (dNum2 % 1.0 == 0) {
                                    keyValue.value1 = String.valueOf((long) dNum2);
                                } else {
                                    Double d = getDoubleValue(hzpArray.getJSONObject(i), "num");
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    keyValue.value1 = df.format(d);
                                }
                                hzpList.add(keyValue);
                            }
                            KeyValueInfo keyValue = new KeyValueInfo();
                            keyValue.key = "总计";
                            if (countNumhzp1 % 1.0 == 0) {
                                keyValue.value = String.valueOf((long) countNumhzp1);
                            } else {
                                Double d = countNumhzp1;
                                DecimalFormat df = new DecimalFormat("0.00");
                                keyValue.value = df.format(d);
                            }
                            if (countNumhzp2 % 1.0 == 0) {
                                keyValue.value1 = String.valueOf((long) countNumhzp2);
                            } else {
                                Double d = countNumhzp2;
                                DecimalFormat df = new DecimalFormat("0.00");
                                keyValue.value1 = df.format(d);
                            }
                            hzpList.add(keyValue);

                            result.setEntry(hzpList);
                            break;
                        case HTTP_ID_device_security_risk_parameter:
                            JSONArray statisticsSecurityRiskArray = getJSONArray(jsonObject, "data");
                            List<DeviceSecurityRiskEntity> deviceSecurityRiskEntityList = new ArrayList<>();
                            for (int i = 0; i < statisticsSecurityRiskArray.length(); i++) {
                                DeviceSecurityRiskEntity deviceSecurityRiskEntity = new DeviceSecurityRiskEntity();
                                deviceSecurityRiskEntity.setType(getStringValue(statisticsSecurityRiskArray.getJSONObject(i), "type"));
                                if (statisticsSecurityRiskArray.getJSONObject(i).has("安全附件")) {
                                    deviceSecurityRiskEntity.setSecurityFile(getStringValue(statisticsSecurityRiskArray.getJSONObject(i), "安全附件"));
                                }
                                if (statisticsSecurityRiskArray.getJSONObject(i).has("安保合同")) {
                                    deviceSecurityRiskEntity.setSecurityContract(getStringValue(statisticsSecurityRiskArray.getJSONObject(i), "安保合同"));
                                }
                                if (statisticsSecurityRiskArray.getJSONObject(i).has("下次检修")) {
                                    deviceSecurityRiskEntity.setNextOverhaul(getStringValue(statisticsSecurityRiskArray.getJSONObject(i), "下次检修"));
                                }
                                deviceSecurityRiskEntityList.add(deviceSecurityRiskEntity);
                            }
                            result.setEntry(deviceSecurityRiskEntityList);
                            break;
                        case HTTP_ID_security_flaws_parameter:
                            JSONArray securityJson = getJSONArray(jsonObject, "data");
                            List<SecurityflawEntity> securityflawEntityList = new ArrayList<>();
                            if (securityJson != null && securityJson.length() > 0)
                                for (int i = 0; i < securityJson.length(); i++) {
                                    SecurityflawEntity securityflawEntity = new Gson().fromJson(securityJson.getString(i), SecurityflawEntity.class);
                                    securityflawEntityList.add(securityflawEntity);
                                }
                            result.setEntry(securityflawEntityList);
                            break;
                        case HTTP_ID_food_sample_parameter:
                            JSONArray foodSampleJson = getJSONArray(jsonObject, "data");
                            List<QualitySampleEntity> qualityFoodSampleEntityList = new ArrayList<>();
                            if (foodSampleJson != null && foodSampleJson.length() > 0)
                                for (int i = 0; i < foodSampleJson.length(); i++) {
                                    QualitySampleEntity qualitySampleEntity = new Gson().fromJson(foodSampleJson.getString(i), QualitySampleEntity.class);
                                    qualityFoodSampleEntityList.add(qualitySampleEntity);
                                }
                            result.setEntry(qualityFoodSampleEntityList);
                            break;
                        case HTTP_ID_food_fast_sample_parameter:
                            JSONArray foodFastSampleJson = getJSONArray(jsonObject, "data");
                            List<QualitySampleEntity> qualityFoodFastSampleEntityList = new ArrayList<>();
                            if (foodFastSampleJson != null && foodFastSampleJson.length() > 0)
                                for (int i = 0; i < foodFastSampleJson.length(); i++) {
                                    QualitySampleEntity qualitySampleEntity = new Gson().fromJson(foodFastSampleJson.getString(i), QualitySampleEntity.class);
                                    qualityFoodFastSampleEntityList.add(qualitySampleEntity);
                                }
                            result.setEntry(qualityFoodFastSampleEntityList);
                            break;
                        case HTTP_ID_drug_sample_parameter:
                            JSONArray drugJson = getJSONArray(jsonObject, "data");
                            List<QualitySampleEntity> qualitySampleEntityList = new ArrayList<>();
                            if (drugJson != null && drugJson.length() > 0)
                                for (int i = 0; i < drugJson.length(); i++) {
                                    QualitySampleEntity qualitySampleEntity = new Gson().fromJson(drugJson.getString(i), QualitySampleEntity.class);
                                    qualitySampleEntityList.add(qualitySampleEntity);
                                }
                            result.setEntry(qualitySampleEntityList);
                            break;
                        case HTTP_ID_industrial_product_parameter:
                            JSONArray productJson = getJSONArray(jsonObject, "data");
                            List<QualitySampleEntity> industrialQualitySampleEntityList = new ArrayList<>();
                            if (productJson != null && productJson.length() > 0)
                                for (int i = 0; i < productJson.length(); i++) {
                                    QualitySampleEntity qualitySampleEntity = new Gson().fromJson(productJson.getString(i), QualitySampleEntity.class);
                                    industrialQualitySampleEntityList.add(qualitySampleEntity);
                                }
                            result.setEntry(industrialQualitySampleEntityList);
                            break;
                        case HTTP_ID_statistics_four_parameter:
                            JSONArray statisticsArray = getJSONArray(jsonObject, "data");
                            List<StatisticsNum> numList = new ArrayList<>();
                            for (int i = 0; i < statisticsArray.length(); i++) {
                                StatisticsNum num = new Gson().fromJson(statisticsArray.getString(i), StatisticsNum.class);
                                numList.add(num);
                            }
                            result.setEntry(numList);
                            break;
                        case HTTP_ID_getUsersByDept:
                            array = getJSONArray(getJSONObject(jsonObject, "data"), "list");
                            List<KeyValueInfo> userList = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                jsonObject = array.getJSONObject(i);
                                userList.add(new KeyValueInfo(getStringValue(jsonObject, "realName"), getStringValue(jsonObject, "id")));
                            }
                            result.setEntry(userList);
                            break;
                        case HTTP_ID_getUserNameByArea:
                            result.setEntry(getStringValue(jsonObject, "data"));
                            break;
                        case HTTP_ID_map_update:
                            String filePaths = getStringValue(jsonObject, "data");
                            Type type = new TypeToken<ArrayList<String>>() {
                            }.getType();
                            List<String> fileList = new Gson().fromJson(filePaths, type);
                            result.setEntry(fileList);
                            break;
                        case HTTP_ID_loginOut:
                            result.setCode(0);
                            break;
                        case HTTP_ID_getDeviceEmergencyListInfos:
                            setEmergencyListInfo(jsonObject, result);
                            break;
                        case HTTP_ID_getLicYjEmergencyListInfos:
                            setEmergencyListInfo(jsonObject, result);
                            break;
                        case HTTP_ID_getMaintenanceContractDataListInfos:
                            setEmergencyListInfo(jsonObject, result);
                            break;
                        case HTTP_ID_getSafeFileListInfos:
                            setEmergencyListInfo(jsonObject, result);
                            break;
                        case HTTP_ID_getEmergency_detail:
                            JSONObject deviceEmergencyJson = getJSONObject(jsonObject, "data");
                            DeviceEmergencyDetialEntity deviceEmergencyDetialEntity = new Gson().fromJson(deviceEmergencyJson.toString(), DeviceEmergencyDetialEntity.class);
                            result.setEntry(deviceEmergencyDetialEntity);
                            break;
                        case HTTP_ID_getPermit_Emergency_detail:
                            JSONObject permitEmergencyJson = getJSONObject(jsonObject, "data");
                            PermitEmergencyDetialEntity permitEmergencyDetialEntity = new Gson().fromJson(permitEmergencyJson.toString(), PermitEmergencyDetialEntity.class);
                            result.setEntry(permitEmergencyDetialEntity);
                            break;
                        case HTTP_ID_submitLocation:

                            break;
                        case HTTP_ID_doClaimed:

                            break;
                        case HTTP_ID_savaOfficialPicture:
                            result.setEntry(getStringValue(jsonObject, "data"));
                            break;
                        case HTTP_ID_queryLocationData:
                            JSONObject locationInfoJson = getJSONObject(jsonObject, "data");
                            LocationEntity locationEntity = new LocationEntity();
                            locationEntity.setfLatitude(getStringValue(locationInfoJson, "fLatitude"));
                            locationEntity.setfLongitude(getStringValue(locationInfoJson, "fLongitude"));
                            result.setEntry(locationEntity);
                            break;
                        case HTTP_ID_deleteFileInfo:
                            result.setEntry(getStringValue(jsonObject, "data"));
                            break;
                        case HTTP_ID_complain_taskcount:
                            HashMap<String, List<TaskCountInfo>> map = new HashMap<>();
                            list = getJSONObject(jsonObject, "data");
                            if (list.has("投诉举报")) {
                                map.put("投诉举报", parseCount(list, "投诉举报"));
                            }
                            if (list.has("监管任务")) {
                                map.put("监管任务", parseCount(list, "监管任务"));
                            }
                            if (list.has("预警信息")) {

                                map.put("预警信息", parseCount(list, "预警信息"));
                            }
                            result.setEntry(map);
                            break;
                        case HTTP_ID_casePageAyxx:
                        case HTTP_ID_caseTaskPage:
                        case HTTP_ID_caseTaskHisPage:
                        case HTTP_ID_casePageLcjk:
                            jsonObject = getJSONObject(jsonObject, "data");
                            NormalListEntity normalListEntity = new NormalListEntity();
                            normalListEntity.setCurrPageNo(getIntValue(jsonObject, "pageNo"));
                            normalListEntity.setPageSize(getIntValue(jsonObject, "pageSize"));
                            normalListEntity.setPageTotal(getIntValue(jsonObject, "pages"));
                            normalListEntity.setTotal(getIntValue(jsonObject, "total"));
                            jsonArray = getJSONArray(jsonObject, "list");
                            List<CaseInfoEntity> caseInfoEntities = gson.fromJson(jsonArray.toString(), new TypeToken<List<CaseInfoEntity>>() {
                            }.getType());
//                            List<CaseInfoEntity> caseInfoEntities = new ArrayList<>();
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                CaseInfoEntity caseInfo = new CaseInfoEntity();
//                                jsonObject = jsonArray.getJSONObject(i);
//                                caseInfo.setfAyAjly(getStringValue(jsonObject, "fAyAjly"));
//                                caseInfo.setfAyAymc(getStringValue(jsonObject, "fAyAymc"));
//                                caseInfo.setfDsrMc(getStringValue(jsonObject, "fDsrMc"));
//                                caseInfo.setfId(getStringValue(jsonObject, "fId"));
//                                caseInfo.setfHjBm(getStringValue(jsonObject, "fHjBm"));
//                                caseInfo.setfHjMc(getStringValue(jsonObject, "fHjMc"));
//                                caseInfo.setfSjCjsj(getStringValue(jsonObject, "fSjCjsj"));
//                                caseInfo.setfSfyq(getStringValue(jsonObject, "fSfyq"));
//                                caseInfo.setfSfxa("1".equals(getStringValue(jsonObject, "fSfxa")));
//                                caseInfo.setfSfys("1".equals(getStringValue(jsonObject, "fSfys")));
//                                caseInfo.setfSfqzcs("1".equals(getStringValue(jsonObject, "fSfqzcs")));
//                                caseInfo.setfTaskId(getStringValue(jsonObject, "TASK_ID"));
//                                caseInfo.setfTaskName(getStringValue(jsonObject, "TASK_NAME_"));
//                                caseInfo.setPROC_DEF_ID_(getStringValue(jsonObject, "PROC_DEF_ID_"));
//                                caseInfo.setfYqzt("已逾期".equals(getStringValue(jsonObject, "fYqzt")));
//                                caseInfo.setLatitude(getStringValue(jsonObject, "fDsrWd"));
//                                caseInfo.setLongitude(getStringValue(jsonObject, "fDsrJd"));
//                                caseInfo.setfDcqzZxrId(getStringValue(jsonObject, "fDcqzZxrId"));
//                                caseInfo.setfDcqzXzr(getStringValue(jsonObject, "fDcqzXzr"));
//                                caseInfoEntities.add(caseInfo);
//                            }
                            normalListEntity.setCaseInfoEntityList(caseInfoEntities);
                            result.setEntry(normalListEntity);
                            break;
                        case HTTP_ID_caseGetAyxxDetailById:
                            jsonObject = getJSONObject(jsonObject, "data");
                            CaseDetailEntity caseDetailEntity = gson.fromJson(jsonObject.toString(), CaseDetailEntity.class);
                            result.setEntry(caseDetailEntity);
                            break;
                        case HTTP_ID_caseGetAyLcgjPageInfo:
                            jsonArray = getJSONArray(jsonObject, "data");
                            List<CaseFlowEntity> flowList = gson.fromJson(jsonArray.toString(), new TypeToken<List<CaseFlowEntity>>() {
                            }.getType());
                            result.setEntry(flowList);
                            break;
                        case HTTP_ID_caseGetNextPerson:
                            jsonArray = getJSONArray(jsonObject, "data").getJSONArray(0);
                            List<SelectPopDataList> personList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                SelectPopDataList popDataList = new SelectPopDataList();
                                jsonObject = jsonArray.getJSONObject(i);
                                popDataList.fTaskName = getStringValue(jsonObject, "name");
                                popDataList.fTaskId = getStringValue(jsonObject, "id");
                                personList.add(popDataList);
                            }
                            result.setEntry(personList);
                            break;
                        case HTTP_ID_caseDoExcute:
                            break;
                        case HTTP_ID_caseDoDelay:
                            break;
                        case HTTP_ID_caseDoDestory:
                            break;
                        case HTTP_ID_caseGetMonitor:
                            jsonObject = getJSONObject(jsonObject, "data");
                            List<TaskCountInfo> taskCountInfos = new ArrayList<>();
                            //从1开始，因为要排序，因为行政处罚需要排到第三个位置。。。。。
                            TaskCountInfo caseMonitorInfo = new TaskCountInfo();
                            caseMonitorInfo.status = "01";
                            caseMonitorInfo.soonCount = getIntValue(getJSONObject(jsonObject, "01"), "soon");
                            caseMonitorInfo.expireCount = getIntValue(getJSONObject(jsonObject, "01"), "yes");
                            caseMonitorInfo.allCount = getIntValue(getJSONObject(jsonObject, "01"), "no");
                            taskCountInfos.add(caseMonitorInfo);
                            caseMonitorInfo = new TaskCountInfo();
                            caseMonitorInfo.status = "N01";
                            caseMonitorInfo.soonCount = getIntValue(getJSONObject(jsonObject, "N01"), "soon");
                            caseMonitorInfo.expireCount = getIntValue(getJSONObject(jsonObject, "N01"), "yes");
                            caseMonitorInfo.allCount = getIntValue(getJSONObject(jsonObject, "N01"), "no");
                            taskCountInfos.add(caseMonitorInfo);
                            caseMonitorInfo = new TaskCountInfo();
                            caseMonitorInfo.status = "Y06";
                            caseMonitorInfo.soonCount = getIntValue(getJSONObject(jsonObject, "Y06"), "soon");
                            caseMonitorInfo.expireCount = getIntValue(getJSONObject(jsonObject, "Y06"), "yes");
                            caseMonitorInfo.allCount = getIntValue(getJSONObject(jsonObject, "Y06"), "no");
                            taskCountInfos.add(caseMonitorInfo);
                            caseMonitorInfo = new TaskCountInfo();
                            caseMonitorInfo.status = "Y10";
                            caseMonitorInfo.soonCount = getIntValue(getJSONObject(jsonObject, "Y10"), "soon");
                            caseMonitorInfo.expireCount = getIntValue(getJSONObject(jsonObject, "Y10"), "yes");
                            caseMonitorInfo.allCount = getIntValue(getJSONObject(jsonObject, "Y10"), "no");
                            taskCountInfos.add(caseMonitorInfo);
                            caseMonitorInfo = new TaskCountInfo();
                            caseMonitorInfo.status = "Y14";
                            caseMonitorInfo.soonCount = getIntValue(getJSONObject(jsonObject, "Y14"), "soon");
                            caseMonitorInfo.expireCount = getIntValue(getJSONObject(jsonObject, "Y14"), "yes");
                            caseMonitorInfo.allCount = getIntValue(getJSONObject(jsonObject, "Y14"), "no");
                            taskCountInfos.add(caseMonitorInfo);
                            caseMonitorInfo = new TaskCountInfo();
                            caseMonitorInfo.status = "02";
                            caseMonitorInfo.soonCount = getIntValue(getJSONObject(jsonObject, "02"), "soon");
                            caseMonitorInfo.expireCount = getIntValue(getJSONObject(jsonObject, "02"), "yes");
                            caseMonitorInfo.allCount = getIntValue(getJSONObject(jsonObject, "02"), "no");
                            taskCountInfos.add(caseMonitorInfo);
                            result.setEntry(taskCountInfos);
                            break;
                        case HTTP_ID_caseSaveAyfj:
                            break;
                        case HTTP_ID_compMonitor:
                            jsonObject = getJSONObject(jsonObject, "data");
                            List<TaskCountInfo> taskCountInfo = new ArrayList<>();
                            TaskCountInfo countInfo = new TaskCountInfo();
                            countInfo.status = "0";
                            countInfo.allCount = getIntValue(jsonObject, "0");
                            taskCountInfo.add(countInfo);
                            countInfo = new TaskCountInfo();
                            countInfo.status = "10";
                            countInfo.allCount = getIntValue(jsonObject, "10");
                            taskCountInfo.add(countInfo);
                            countInfo = new TaskCountInfo();
                            countInfo.status = "20";
                            countInfo.allCount = getIntValue(jsonObject, "20");
                            taskCountInfo.add(countInfo);
                            countInfo = new TaskCountInfo();
                            countInfo.status = "30";
                            countInfo.allCount = getIntValue(jsonObject, "30");
                            taskCountInfo.add(countInfo);
                            countInfo = new TaskCountInfo();
                            countInfo.status = "40";
                            countInfo.allCount = getIntValue(jsonObject, "40");
                            taskCountInfo.add(countInfo);
                            countInfo = new TaskCountInfo();
                            countInfo.status = "50";
                            countInfo.allCount = getIntValue(jsonObject, "50");
                            taskCountInfo.add(countInfo);
                            countInfo = new TaskCountInfo();
                            countInfo.status = "60";
                            countInfo.allCount = getIntValue(jsonObject, "60");
                            taskCountInfo.add(countInfo);
                            countInfo = new TaskCountInfo();
                            countInfo.status = "70";
                            countInfo.allCount = getIntValue(jsonObject, "70");
                            taskCountInfo.add(countInfo);
                            countInfo = new TaskCountInfo();
                            countInfo.status = "80";
                            countInfo.allCount = getIntValue(jsonObject, "80");
                            taskCountInfo.add(countInfo);
                            result.setEntry(taskCountInfo);
                            break;
                        case HTTP_ID_compPageQuery:
                        case HTTP_ID_compTaskPage:
                        case HTTP_ID_compLcjk:
                            jsonObject = getJSONObject(jsonObject, "data");
                            NormalListEntity complainEntity = new NormalListEntity();
                            complainEntity.setCurrPageNo(getIntValue(jsonObject, "currPageNo"));
                            complainEntity.setPageSize(getIntValue(jsonObject, "pageSize"));
                            complainEntity.setPageTotal(getIntValue(jsonObject, "pageTotal"));
                            complainEntity.setTotal(getIntValue(jsonObject, "total"));
                            jsonArray = getJSONArray(jsonObject, "list");
                            List<ComplainInfoEntity> compInfoEntities = gson.fromJson(jsonArray.toString(), new TypeToken<List<ComplainInfoEntity>>() {
                            }.getType());
                            complainEntity.setComplainInfoList(compInfoEntities);
                            result.setEntry(complainEntity);
                            break;
                        case HTTP_ID_compInfoById:
                            jsonObject = getJSONObject(jsonObject, "data");
                            ComplainInfoDetailsBean compInfo = gson.fromJson(jsonObject.toString(), ComplainInfoDetailsBean.class);
                            result.setEntry(compInfo);
                            break;
                        case HTTP_ID_compLcgj:
                            jsonArray = getJSONArray(jsonObject, "data");
                            List<CompFlowEntity> compFlowEntities = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                CompFlowEntity compFlowEntity = new CompFlowEntity();
                                jsonObject = jsonArray.getJSONObject(i);
                                compFlowEntity.setfGuid(getStringValue(jsonObject, "fGuid"));
                                compFlowEntity.setfRegId(getStringValue(jsonObject, "fRegId"));
                                compFlowEntity.setfStatus(getStringValue(jsonObject, "fStatus"));
                                compFlowEntity.setfHandleDate(getStringValue(jsonObject, "fHandleDate"));
                                compFlowEntity.setfHandleAdvice(getStringValue(jsonObject, "fHandleAdvice"));
                                compFlowEntity.setfHandleUser(getStringValue(jsonObject, "fHandleUser"));
                                compFlowEntity.setfSlr(getStringValue(jsonObject, "fSlr"));
                                compFlowEntity.setfRemark(getStringValue(jsonObject, "fRemark"));
                                compFlowEntities.add(compFlowEntity);
                            }
                            result.setEntry(compFlowEntities);
                            break;
                        case HTTP_ID_getAllUserDept:
                            jsonArray = getJSONArray(jsonObject, "data");
                            List<SelectPopDataList> selectPopDataLists = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                selectPopDataLists.add(new SelectPopDataList(getStringValue(jsonArray.getJSONObject(i), "name"), getStringValue(jsonArray.getJSONObject(i), "id")));
                            }
                            result.setEntry(selectPopDataLists);
                            break;
                        case HTTP_ID_compFlowhandle:
                            break;
                        case HTTP_ID_SuperviseTaskPage:
                        case HTTP_ID_SuperviseTaskHisPage:
                        case HTTP_ID_supervisetask_getMonitorTask:
                            jsonObject = getJSONObject(jsonObject, "data");
                            NormalListEntity superMyTask = new NormalListEntity();
                            superMyTask.setCurrPageNo(getIntValue(jsonObject, "currPageNo"));
                            superMyTask.setPageSize(getIntValue(jsonObject, "pageSize"));
                            superMyTask.setPageTotal(getIntValue(jsonObject, "pageTotal"));
                            superMyTask.setTotal(getIntValue(jsonObject, "total"));
                            jsonArray = getJSONArray(jsonObject, "list");
                            List<MyTaskListEntity> myTaskListEntities = gson.fromJson(jsonArray.toString(), new TypeToken<List<MyTaskListEntity>>() {
                            }.getType());
                            superMyTask.setMyTaskListEntities(myTaskListEntities);
                            result.setEntry(superMyTask);
                            break;
                        case HTTP_ID_supervise_countMonitorTask:
                            jsonObject = getJSONObject(jsonObject, "data");
                            MonitorPrecessCountEntity monitorPrecessCountEntity =
                                    new Gson().fromJson(jsonObject.toString(), MonitorPrecessCountEntity.class);
                            result.setEntry(monitorPrecessCountEntity);
                            break;
                        case HTTP_ID_superviseTaskBaseInfo:
                            jsonObject = getJSONObject(jsonObject, "data");
                            MyTaskListEntity myTaskBaseInfo = new Gson().fromJson(jsonObject.toString(), MyTaskListEntity.class);
                            result.setEntry(myTaskBaseInfo);
                            break;
                        case HTTP_ID_superviseGetAyLcgjPageInfo:
                            MyTaskFlow myTaskFlow = new Gson().fromJson(jsonObject.toString(), MyTaskFlow.class);
                            result.setEntry(myTaskFlow.getData());
                            break;
                        case HTTP_ID_SuperviseTaskCheckEntity:
                            jsonObject = getJSONObject(jsonObject, "data");
                            NormalListEntity superNormalList = new NormalListEntity();
                            superNormalList.setCurrPageNo(getIntValue(jsonObject, "currPageNo"));
                            superNormalList.setPageSize(getIntValue(jsonObject, "pageSize"));
                            superNormalList.setPageTotal(getIntValue(jsonObject, "pageTotal"));
                            superNormalList.setTotal(getIntValue(jsonObject, "total"));
                            jsonArray = getJSONArray(jsonObject, "list");
                            List<MyTaskCheckEntity> myTaskCheckEntity = gson.fromJson(jsonArray.toString(), new TypeToken<List<MyTaskCheckEntity>>() {
                            }.getType());
                            superNormalList.setTaskCheckEntities(myTaskCheckEntity);
                            result.setEntry(superNormalList);
                            break;
                        case HTTP_ID_Supervise_getThisTaskPackageTask:
                            MyTaskPageEntity myTaskPageEntity = new Gson().fromJson(jsonObject.toString(), MyTaskPageEntity.class);
                            result.setEntry(myTaskPageEntity);
                            break;
                        case HTTP_ID_Supervise_MyTask_getProcessingPeople:
                        case HTTP_ID_Supervise_MyTask_getSubDirector:
                            MyTaskProcessEntity myTaskProcessEntity = new Gson().fromJson(jsonObject.toString(), MyTaskProcessEntity.class);
                            result.setEntry(myTaskProcessEntity);
                            break;
                        case HTTP_ID_Supervise_MyTask_examineTaskInfo:
                            break;
                        case HTTP_ID_Supervise_MyTask_assignTaskInfo:
                            break;
                        case HTTP_ID_Supervise_MyTask_reviewTask:
                            break;
                        case HTTP_ID_Supervise_MyTask_feedbackTask:
                            break;
                        case HTTP_ID_Supervise_MyTask_executeMyTask:
                            MyTaskCheckResultEntity myTaskCheckResultEntity = new Gson().fromJson(jsonObject.toString(), MyTaskCheckResultEntity.class);
                            result.setEntry(myTaskCheckResultEntity);
                            break;
                        case HTTP_ID_Supervise_MyTask_getCheckResult:
                            jsonObject = getJSONObject(jsonObject, "data");
                            MyTaskCheckResultInfoEntity checkResultInfoEntity = new MyTaskCheckResultInfoEntity();
                            checkResultInfoEntity.setfQualify(getStringValue(jsonObject, "fQualify"));
                            checkResultInfoEntity.setfResult(getStringValue(jsonObject, "fResult"));
                            result.setEntry(checkResultInfoEntity);
                            break;
                        case HTTP_ID_Supervise_MyTask_disposalTask:
                            break;
                        case HTTP_ID_Supervise_MyTask_sendTask:
                            break;
                        case HTTP_ID_Case_Delete_Img:
                            break;
                        case HTTP_ID_getStatisticsList:
                            jsonObject = getJSONObject(jsonObject, "data");
                            NormalListEntity statisticsEntity = new NormalListEntity();
                            statisticsEntity.setCurrPageNo(getIntValue(jsonObject, "currPageNo"));
                            statisticsEntity.setPageSize(getIntValue(jsonObject, "pageSize"));
                            statisticsEntity.setPageTotal(getIntValue(jsonObject, "pageTotal"));
                            statisticsEntity.setTotal(getIntValue(jsonObject, "total"));
                            jsonArray = getJSONArray(jsonObject, "rows");
//                            List<ChartInfoEntity> chartList = new ArrayList<>();
                            List<JSONObject> chartList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
//                                ChartInfoEntity chartInfoEntity = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), ChartInfoEntity.class);
//                                chartList.add(chartInfoEntity);
                                JSONObject json = jsonArray.getJSONObject(i);
                                chartList.add(json);
                            }
                            statisticsEntity.setChartList(chartList);
                            result.setEntry(statisticsEntity);
                            break;
                        case HTTP_ID_superviseIsCanFinishInfo:
                            int num = getIntValue(jsonObject, "data");
                            result.setEntry(num);
                            break;
                        case HTTP_ID_superviseFinishTask:
                            if ("20000".equals(code)) {
                                result.setSuccess(true);
                            } else {
                                result.setSuccess(false);
                            }
                            break;
                        case HTTP_ID_superviseGetTaskImg:
                            jsonObject = getJSONObject(jsonObject, "data");
                            String url = getStringValue(jsonObject, "imgurl");
                            String types = getStringValue(jsonObject, "imgType");
                            String[] urlArray = url.split(",");
                            String[] typeArray = types.split(",");
                            Map<String, List<String>> urlMap = new HashMap<>();
                            List<String> url1 = new ArrayList<>();
                            List<String> url2 = new ArrayList<>();
                            List<String> url3 = new ArrayList<>();
                            List<String> url4 = new ArrayList<>();
                            List<String> url5 = new ArrayList<>();
                            List<String> url6 = new ArrayList<>();
                            if ((typeArray.length == 1 && typeArray[0].length() == 0) || typeArray.length == 0) {
                                url3.addAll(Arrays.asList(urlArray));
                            } else {
                                for (int i = 0; i < typeArray.length; i++) {
                                    if ("1".equals(typeArray[i])) {
                                        url1.add(urlArray[i]);
                                    } else if ("2".equals(typeArray[i])) {
                                        url2.add(urlArray[i]);
                                    } else if ("3".equals(typeArray[i])) {
                                        url3.add(urlArray[i]);
                                    } else if ("4".equals(typeArray[i])) {
                                        url4.add(urlArray[i]);
                                    } else if ("5".equals(typeArray[i])) {
                                        url5.add(urlArray[i]);
                                    } else if ("6".equals(typeArray[i])) {
                                        url6.add(urlArray[i]);
                                    }
                                }
                            }
                            urlMap.put("1", url1);
                            urlMap.put("2", url2);
                            urlMap.put("3", url3);
                            urlMap.put("4", url4);
                            urlMap.put("5", url5);
                            urlMap.put("6", url6);
                            result.setEntry(urlMap);
                            break;
                        case HTTP_ID_superviseAppendEntity:
                            break;
                        case HTTP_ID_superviseQueryEntityByCondition:
                            list = getJSONObject(jsonObject, "data");
                            MyTaskInfoEntity taskInfoEntity = new MyTaskInfoEntity();
                            taskInfoEntity.setCurrPageNo(getIntValue(list, "currPageNo"));
                            taskInfoEntity.setPageSize(getIntValue(list, "pageSize"));
                            taskInfoEntity.setPageTotal(getIntValue(list, "pageTotal"));
                            taskInfoEntity.setTotal(getIntValue(list, "total"));
                            JSONArray dataArray = getJSONArray(list, "rows");
                            List<MyTaskInfoEntity.RowsBean> ztlist = new ArrayList<>();
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject jObj = (JSONObject) dataArray.get(i);
                                MyTaskInfoEntity.RowsBean rowsBean = new MyTaskInfoEntity.RowsBean();
                                rowsBean.setF_ADDRESS(getStringValue(jObj, "F_ADDRESS"));
                                rowsBean.setFEntityGuid(getStringValue(jObj, "fEntityGuid"));
                                rowsBean.setF_STATION(getStringValue(jObj, "F_STATION"));
                                rowsBean.setFEntityName(getStringValue(jObj, "fEntityName"));
                                rowsBean.setFEntityType(getStringValue(jObj, "fEntityType"));
                                rowsBean.setF_MARK(getStringValue(jObj, "F_MARK"));
                                ztlist.add(rowsBean);
                            }
                            taskInfoEntity.setRows(ztlist);
                            result.setEntry(taskInfoEntity);
                            break;
                        case HTTP_ID_caseSelectDiscretionStandard:
                            list = getJSONObject(jsonObject, "data");
                            CaseRefeEntity mEntity = gson.fromJson(list.toString(), CaseRefeEntity.class);
                            result.setEntry(mEntity);
                            break;
                        case HTTP_ID_superviseGetEntityPoint:
                            jsonArray = getJSONArray(jsonObject, "data");
                            List<EntityPointBean> pointBeans = gson.fromJson(jsonArray.toString(), new TypeToken<List<EntityPointBean>>() {
                            }.getType());
                            result.setEntry(pointBeans);
                            break;
                        case HTTP_ID_superviseGetDTByCondition:
                        case HTTP_ID_equipSearchList:
                            jsonObject = getJSONObject(jsonObject, "data");
                            NormalListEntity equipmentBean = new NormalListEntity();
                            equipmentBean.setCurrPageNo(getIntValue(jsonObject, "currPageNo"));
                            equipmentBean.setTotal(getIntValue(jsonObject, "total"));
                            equipmentBean.setPageTotal(getIntValue(jsonObject, "pageTotal"));
                            equipmentBean.setPageSize(getIntValue(jsonObject, "pageSize"));
                            jsonArray = getJSONArray(jsonObject, "rows");
                            List<SuperviseEquimentEntity> equimentEntities = gson.fromJson(jsonArray.toString(), new TypeToken<List<SuperviseEquimentEntity>>() {
                            }.getType());
                            equipmentBean.setEquimentEntityList(equimentEntities);
                            result.setEntry(equipmentBean);
                            break;
                        case HTTP_ID_superviseUpdateDTPosition:
                            break;
                        case HTTP_ID_superviceAppendEntityPoint:
                            break;
                        case HTTP_ID_synergyCountCheckInfo:
                            jsonObject = getJSONObject(jsonObject, "data");
                            result.setEntry(jsonObject);
                            break;
                        case HTTP_ID_synergyGetCountCheckInfoItem:
                            jsonArray = getJSONArray(jsonObject, "data");
                            List<SynergyInfoBean> synergyParent = gson.fromJson(jsonArray.toString(), new TypeToken<List<SynergyInfoBean>>() {
                            }.getType());
                            result.setEntry(synergyParent);
                            break;
                        case HTTP_ID_synergyGetCheckInfoItems:
                            jsonObject = getJSONObject(jsonObject, "data");
                            jsonArray = getJSONArray(jsonObject, "rows");
                            List<SynergyInfoBean> synergyChild = gson.fromJson(jsonArray.toString(), new TypeToken<List<SynergyInfoBean>>() {
                            }.getType());
                            result.setEntry(synergyChild);
                            break;
                        case HTTP_ID_synergyGetDetailInfo:
                            jsonObject = getJSONObject(jsonObject, "data");
                            SynergyDetailEntity detailEntity = gson.fromJson(jsonObject.toString(), SynergyDetailEntity.class);
                            result.setEntry(detailEntity);
                            break;
                        case HTTP_ID_synergyGetDTDetailInfo:
                            jsonObject = getJSONObject(jsonObject, "data");
                            SynergyDTInfoEntity dtInfoEntity = gson.fromJson(jsonObject.toString(), SynergyDTInfoEntity.class);
                            result.setEntry(dtInfoEntity);
                            break;
                        case HTTP_ID_synergyGetYJDetailInfo:
                            jsonObject = getJSONObject(jsonObject, "data");
                            SynergyDetailEntity yjEntity = gson.fromJson(jsonObject.toString(), SynergyDetailEntity.class);
                            result.setEntry(yjEntity);
                            break;
                        case HTTP_ID_synergyGetEquipment1:
                            jsonObject = getJSONObject(jsonObject, "data");
                            EquipmentInfoEntity equipmentInfoEntity = gson.fromJson(jsonObject.toString(), EquipmentInfoEntity.class);
                            result.setEntry(equipmentInfoEntity);
                            break;
                        case HTTP_ID_getPicByEntityId:
                            jsonArray = getJSONArray(jsonObject, "data");
                            List<EntityPictureBean> pictureBeans = gson.fromJson(jsonArray.toString(), new TypeToken<List<EntityPictureBean>>() {
                            }.getType());
                            result.setEntry(pictureBeans);
                            break;
                        case HTTP_ID_statistics_case_queryDep:
                        case HTTP_ID_statistics_case_queryType:
                        case HTTP_ID_statistics_case_queryIsCase:
                        case HTTP_ID_statistics_case_queryClosedCount:
                        case HTTP_ID_statistics_entity_equipmentType:
                        case HTTP_ID_statistics_super_countTask:
                        case HTTP_ID_statistics_super_countType:
                        case HTTP_ID_statistics_super_countDoTask:
                        case HTTP_ID_statistics_super_countEnterprise:
                            jsonArray = getJSONArray(jsonObject, "data");
                            List<KeyValueInfo> closeCounts = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject closeCountObject = jsonArray.getJSONObject(i);
                                KeyValueInfo closeCountInfo = new KeyValueInfo();
                                closeCountInfo.key = getStringValue(closeCountObject, "name");
                                closeCountInfo.value = getStringValue(closeCountObject, "num");
                                closeCounts.add(closeCountInfo);
                            }
                            result.setEntry(closeCounts);
                            break;
                        case HTTP_ID_statistics_case_queryRecordCount:
                            jsonArray = getJSONArray(jsonObject, "data");
                            List<KeyValueInfo> recordCount = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject recordObject = jsonArray.getJSONObject(i);
                                KeyValueInfo recordInfo = new KeyValueInfo();
                                recordInfo.key = getStringValue(recordObject, "name");
                                recordInfo.value = getStringValue(recordObject, "num");
                                recordInfo.value1 = getStringValue(recordObject, "code");
                                recordCount.add(recordInfo);
                            }
                            result.setEntry(recordCount);
                            break;
                        case HTTP_ID_statistics_case_queryPunishCount:
                            jsonArray = getJSONArray(jsonObject, "data");
                            List<KeyValueInfo> punishCount = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject punishObject = jsonArray.getJSONObject(i);
                                KeyValueInfo punishInfo = new KeyValueInfo();
                                punishInfo.key = getStringValue(punishObject, "name");
                                punishInfo.value = getStringValue(punishObject, "punish");
                                punishInfo.value1 = getStringValue(punishObject, "noPunish");
                                punishInfo.value2 = getStringValue(punishObject, "nowPush");
                                punishCount.add(punishInfo);
                            }
                            result.setEntry(punishCount);
                            break;
                        case HTTP_ID_statistics_comp_countType:
                        case HTTP_ID_statistics_entity_enterpriseComplain:
                            jsonArray = getJSONArray(jsonObject, "data");
                            List<KeyValueInfo> typeInfo = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject typeObject = jsonArray.getJSONObject(i);
                                KeyValueInfo typeKV = new KeyValueInfo();
                                typeKV.key = getStringValue(typeObject, "name");
                                typeKV.value = getStringValue(typeObject, "1");
//                                typeKV.value1 = getStringValue(typeObject, "0");
                                typeInfo.add(typeKV);
                            }
                            result.setEntry(typeInfo);
                            break;
                        case HTTP_ID_statistics_comp_countDepartment:
                        case HTTP_ID_statistics_comp_countInfo:
                        case HTTP_ID_statistics_comp_countBussiniss:
                        case HTTP_ID_statistics_entity_enterpriseType:
                        case HTTP_ID_statistics_entity_enterpriseDev:
                        case HTTP_ID_statistics_entity_enterpriseIndustry:
                        case HTTP_ID_statistics_entity_enterpriseAnn:
                            jsonArray = getJSONArray(jsonObject, "data");
                            List<KeyValueInfo> compInfos = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject compObject = jsonArray.getJSONObject(i);
                                KeyValueInfo compKV = new KeyValueInfo();
                                compKV.key = getStringValue(compObject, "name");
                                compKV.value = getStringValue(compObject, "value");
                                compInfos.add(compKV);
                            }
                            result.setEntry(compInfos);
                            break;
                        case HTTP_ID_statistics_entity_enterpriseWarning:
                            jsonArray = getJSONArray(jsonObject, "data");
                            List<KeyValueInfo> warnings = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject compObject = jsonArray.getJSONObject(i);
                                KeyValueInfo compKV = new KeyValueInfo();
                                compKV.key = getStringValue(compObject, "type");
                                compKV.value = getStringValue(compObject, "overdue");
                                warnings.add(compKV);
                            }
                            result.setEntry(warnings);
                            break;
                        case HTTP_ID_info_manager_biaozhun:
                            jsonObject = getJSONObject(jsonObject, "data");
                            InfoManagerBiaozhun myIncomanagerStandarEntity = new Gson().fromJson(jsonObject.toString(), InfoManagerBiaozhun.class);
                            result.setEntry(myIncomanagerStandarEntity);
                            break;
                        case HTTP_ID_info_manager_biaozhun_detail:
                            jsonObject = getJSONObject(jsonObject, "data");
                            InfoManagerBiaozhunDetail myStandarEntity = new Gson().fromJson(jsonObject.toString(), InfoManagerBiaozhunDetail.class);
                            result.setEntry(myStandarEntity);
                            break;
                        case HTTP_ID_info_manager_device_liebiao:
                            jsonObject = getJSONObject(jsonObject, "data");
                            InfoManagerDevice myIncomanagerDeviceEntity = new Gson().fromJson(jsonObject.toString(), InfoManagerDevice.class);
                            result.setEntry(myIncomanagerDeviceEntity);
                            break;
                        case HTTP_ID_info_manager_device_detail:
                            jsonObject = getJSONObject(jsonObject, "data");
                            InfoManagerDeviceDetail myDeviceDtailInfo = new Gson().fromJson(jsonObject.toString(), InfoManagerDeviceDetail.class);
                            result.setEntry(myDeviceDtailInfo);
                            break;
                        case HTTP_ID_info_manager_license_food:
                            jsonObject = getJSONObject(jsonObject, "data");
                            InfoManagerLicenseFood myLisenceFood = new Gson().fromJson(jsonObject.toString(), InfoManagerLicenseFood.class);
                            result.setEntry(myLisenceFood);
                            break;
                        case HTTP_ID_info_manager_license_drugs:
                            jsonObject = getJSONObject(jsonObject, "data");
                            InfoManagerLicense myLisence = new Gson().fromJson(jsonObject.toString(), InfoManagerLicense.class);
                            result.setEntry(myLisence);
                            break;
                        case HTTP_ID_info_manager_license_cosmetics:
                            jsonObject = getJSONObject(jsonObject, "data");
                            myLisence = new Gson().fromJson(jsonObject.toString(), InfoManagerLicense.class);
                            result.setEntry(myLisence);
                            break;
                        case HTTP_ID_info_manager_license_instrument://许可证-医疗器械企业列表
                            jsonObject = getJSONObject(jsonObject, "data");
                            myLisence = new Gson().fromJson(jsonObject.toString(), InfoManagerLicense.class);
                            result.setEntry(myLisence);
                            break;
                        case HTTP_ID_info_manager_license_detail://许可证-许可证详情
//                            jsonObject = getJSONObject(jsonObject, "data");
//                            InfoManagerLicenseDetail myLisenceDetail = new Gson().fromJson(jsonObject.toString(), InfoManagerLicenseDetail.class);
//                            result.setEntry(myLisenceDetail);

                            jsonArray = getJSONArray(jsonObject, "data");
                            List<InfoManagerLicenseDetail> myLicenseDetails = new ArrayList<>();
                            int length = jsonArray.length();
                            for (int i = 0; i < length; i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                InfoManagerLicenseDetail myLisenceDetail = new Gson().fromJson(jsonObject.toString(), InfoManagerLicenseDetail.class);
                                myLicenseDetails.add(myLisenceDetail);
                            }

                            result.setEntry(myLicenseDetails);
                            break;
                        case HTTP_ID_info_manager_measuring_instruments_custom://计量器具-自定义表信息接口
                            jsonArray = getJSONArray(jsonObject, "data");
                            List<KeyValueInfo> myMeasureCustom = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject compObject = jsonArray.getJSONObject(i);
                                Log.i("wangwansheng", "jsonObject is " + compObject.toString());
                                KeyValueInfo compKV = new KeyValueInfo();
                                compKV.key = getStringValue(compObject, "name");
                                compKV.value = String.valueOf(getIntValue(compObject, "type"));
                                compKV.value1 = getStringValue(compObject, "col");
                                myMeasureCustom.add(compKV);
                            }
                            result.setEntry(myMeasureCustom);
                            break;
                        case HTTP_ID_info_manager_measuring_instruments_liebiao://计量器具-计量器具列表接口
                            jsonObject = getJSONObject(jsonObject, "data");
                            InfoManagerMeasureLiebiao myMeasureLiebiao = new Gson().fromJson(jsonObject.toString(), InfoManagerMeasureLiebiao.class);
                            result.setEntry(myMeasureLiebiao);
                            break;
                        case HTTP_ID_info_manager_measuring_instruments_detail:
                            jsonObject = getJSONObject(jsonObject, "data");
                            InfoManagerMeasureDetail myMeasureDetail = new Gson().fromJson(jsonObject.toString(), InfoManagerMeasureDetail.class);
                            result.setEntry(myMeasureDetail);
                            break;
                        case HTTP_ID_info_manager_legal_query:
                            jsonArray = getJSONArray(jsonObject, "data");
                            List<ZXExpandBean> myLegalSelect = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject compObject = jsonArray.getJSONObject(i);
                                LegalEntity legalEntity = new Gson().fromJson(compObject.toString(), LegalEntity.class);
                                String name = legalEntity.getName();
                                ZXExpandBean bean = new ZXExpandBean(legalEntity, name);
                                List<ZXExpandBean> dataList = listBeans(compObject);
                                if (dataList != null && dataList.size() != 0)
                                    bean.setChildList(dataList);
                                myLegalSelect.add(bean);
                            }

                            result.setEntry(myLegalSelect);
                            break;
                        case HTTP_ID_info_manager_legal_search:
                            jsonObject = getJSONObject(jsonObject, "data");
                            NormalListEntity legalSearch = new NormalListEntity();
                            legalSearch.setCurrPageNo(getIntValue(jsonObject, "currPageNo"));
                            legalSearch.setTotal(getIntValue(jsonObject, "total"));
                            legalSearch.setPageTotal(getIntValue(jsonObject, "pageTotal"));
                            legalSearch.setPageSize(getIntValue(jsonObject, "pageSize"));
                            jsonArray = getJSONArray(jsonObject, "list");
                            List<KeyValueInfo> myLegalSelectLaw = new ArrayList<>();
//                            Log.i("wangwansheng", "jsonArray.length is " + jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                KeyValueInfo compKV = new KeyValueInfo();
                                compKV.key = getStringValue(jsonObject1, "lawName");
                                JSONArray jsonArray1 = jsonObject1.getJSONArray("law");
//                                JSONObject jsonObject2 = jsonObject1.getJSONObject("law");
                                compKV.value = jsonArray1.getString(0);
                                myLegalSelectLaw.add(compKV);
                            }
                            legalSearch.setKeyValueInfoList(myLegalSelectLaw);
                            result.setEntry(legalSearch);
                            break;
                        case HTTP_ID_supervise_saceItemResult:
                        case HTTP_ID_supervise_finishItem:
                        case HTTP_ID_supervise_deleteFile:

                            break;
                        case HTTP_ID_supervise_getTaskFiles:
                            jsonArray = getJSONArray(jsonObject, "data");
                            List<ImageEntity> imageEntities = gson.fromJson(jsonArray.toString(), new TypeToken<List<ImageEntity>>() {
                            }.getType());
                            result.setEntry(imageEntities);
                            break;
                        case HTTP_ID_case_01:
                        case HTTP_ID_case_Y01:
                        case HTTP_ID_case_Y02:
                        case HTTP_ID_case_Y03:
                        case HTTP_ID_case_Y05:
                        case HTTP_ID_case_Y06:
                        case HTTP_ID_case_Y07:
                        case HTTP_ID_case_Y08:
                        case HTTP_ID_case_Y10:
                        case HTTP_ID_case_Y11:
                        case HTTP_ID_case_Y12:
                        case HTTP_ID_case_Y13:
                        case HTTP_ID_case_Y14:
                        case HTTP_ID_case_02:
                            break;
                        case HTTP_ID_psw_update:

                            break;
                        default:
                            break;
                    }
                }
                break;
            //TODO
        }
        return result;
    }

    private List<ZXExpandBean> listBeans(JSONObject jsonObject) {
        try {
            List<ZXExpandBean> beanList = new ArrayList<>();
            if (jsonObject.get("childMenus") == null)
                return null;
            JSONArray jsonArray = getJSONArray(jsonObject, "childMenus");
//            Log.i("wangwansheng","jsonArray length is " +jsonArray.length());
            if (jsonArray != null && jsonArray.length() != 0) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    LegalEntity legalEntity = new Gson().fromJson(jsonObject1.toString(), LegalEntity.class);
                    String name = legalEntity.getName();
//                    Log.i("wangwansheng","jsonObject1  is " +jsonObject1.toString());
                    ZXExpandBean bean1 = new ZXExpandBean(legalEntity, name);
                    beanList.add(bean1);
                    List<ZXExpandBean> dataList = listBeans(jsonObject1);
                    if (dataList != null && dataList.size() != 0) {
                        bean1.setChildList(dataList);
                    }
                }
            } else {
                Log.e("wangwansheng", "not children!!");
            }
            return beanList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setEmergencyListInfo(JSONObject jsonObject, BaseHttpResult result) throws JSONException {
        JSONObject deviceEmergencyJson = getJSONObject(jsonObject, "data");
        EmergencyInfo deviceEmergencyInfo = new EmergencyInfo();
        deviceEmergencyInfo.currPageNo = getIntValue(deviceEmergencyJson, "currPageNo");
        deviceEmergencyInfo.pageSize = getIntValue(deviceEmergencyJson, "pageSize");
        deviceEmergencyInfo.pageTotal = getIntValue(deviceEmergencyJson, "pageTotal");
        deviceEmergencyInfo.total = getIntValue(deviceEmergencyJson, "total");
        List<EmergencyListInfo> deviceEmergencyList = new ArrayList<>();
        JSONArray deviceEmergencyArray = getJSONArray(deviceEmergencyJson, "rows");
        for (int i = 0; i < deviceEmergencyArray.length(); i++) {
            jsonObject = deviceEmergencyArray.getJSONObject(i);
            EmergencyListInfo entityInfo = new EmergencyListInfo();
            entityInfo.setfGuid(getStringValue(jsonObject, "fGuid"));
            entityInfo.setfEntityGuid(getStringValue(jsonObject, "fEntityGuid"));
            entityInfo.setfContent(getStringValue(jsonObject, "fContent"));
            entityInfo.setfCategory(getStringValue(jsonObject, "fCategory"));
            entityInfo.setfType(getStringValue(jsonObject, "fType"));
            entityInfo.setfStatus(getStringValue(jsonObject, "fStatus"));
            entityInfo.setfEquipmentGuid(getStringValue(jsonObject, "fEquipmentGuid"));
            entityInfo.setfEntityName(getStringValue(jsonObject, "fEntityName"));
            entityInfo.setfAddress(getStringValue(jsonObject, "fAddress"));
            entityInfo.setfInsertDate(getStringValue(jsonObject, "fInsertDate"));
            entityInfo.setfExpireDate(getStringValue(jsonObject, "fExpireDate"));
            entityInfo.setfLatitude(getDoubleValue(jsonObject, "fLatitude"));
            entityInfo.setfLongitude(getDoubleValue(jsonObject, "fLongitude"));
            JSONObject sInfo = getJSONObject(jsonObject, "sInfo");
            entityInfo.setfLatitude(getDoubleValue(sInfo, "y"));
            entityInfo.setfLongitude(getDoubleValue(sInfo, "x"));
            deviceEmergencyList.add(entityInfo);
        }
        deviceEmergencyInfo.emergencyList = deviceEmergencyList;
        result.setEntry(deviceEmergencyInfo);
    }


    // 获取JSONObject类型
    public JSONObject getJSONObject(JSONObject jsonObject, String name) {
        try {
            return jsonObject.getJSONObject(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    // 获取JSONArray类型
    public JSONArray getJSONArray(JSONObject jsonObject, String name) {
        try {
            return jsonObject.getJSONArray(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    // 获取String类型参数
    public String getStringValue(JSONObject jsonObject, String name) {
        try {
            if (jsonObject.getString(name).equals("null")) {
                return "";
            } else {
                return jsonObject.getString(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    // 获取Int类型参数
    public int getIntValue(JSONObject jsonObject, String name) {
        try {
            return jsonObject.getInt(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 获取double类型参数
    public double getDoubleValue(JSONObject jsonObject, String name) {
        try {
            return jsonObject.getDouble(name);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 文件下载
     *
     * @param downloadUrl
     * @param filePath        例如/apk/market.apk
     * @param deleteExistFile 是否删除已存在文件
     */
    private void fileDownload(String downloadUrl, String filePath, boolean deleteExistFile) {
        HttpUtils http = new HttpUtils();
        File file = new File(ConstStrings.getDownloadPath() + filePath);
        if (deleteExistFile) {
            file.delete();
        } else if (file.exists()) {
            listener.onLoadComplete(FILE_DOWNLOAD, null);
            return;
        }
        HttpHandler handler = http.download(downloadUrl, //资源地址
                ConstStrings.getDownloadPath() + filePath, //保存文件地址
                true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                new RequestCallBack<File>() {

                    //下载过程中，实时调用
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        int progress = (int) ((current * 100) / total);
                        listener.onLoadPregress(FILE_DOWNLOAD, progress);
                    }

                    //下载成功后调用
                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        pHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onLoadComplete(FILE_DOWNLOAD, null);
                            }
                        });
                    }

                    //下载失败后调用
                    @Override
                    public void onFailure(HttpException error, String msg) {
                        listener.onLoadError(FILE_DOWNLOAD, false, msg);
                    }
                });
    }

    /**
     * 文件上传
     *
     * @throws IOException
     * @throws URISyntaxException
     * @throws CustomException
     * @throws JSONException
     */
    private void fileUpload(final String[] paths, String type, Map<String, String> param) throws IOException, URISyntaxException, CustomException, JSONException {
        //实例化HttpUtils对象， 参数设置链接超时
        HttpUtils HTTP_UTILS = new HttpUtils(60 * 1000);
        //实例化RequestParams对象
        RequestParams requestParams = new RequestParams();
        final BaseHttpResult result = new BaseHttpResult();
        for (int i = 0; i < paths.length; i++) {
            String path = paths[i];
            File file = new File(path);
            if (!file.exists()) {
                continue;
            }
            if (file.exists()) {
                String name = path.substring(path.lastIndexOf("/"), path.length()) + i;
                requestParams.addBodyParameter(name, file);
            }
        }
        for (String key : param.keySet()) {
            requestParams.addBodyParameter(key, param.get(key));
        }
        String photoUrl = baseUrl + type;
        //通过HTTP_UTILS来发送post请求， 并书写回调函数
        LogUtil.e(this, "开始上传");
        if (sessionId.length() > 0) {
            requestParams.addHeader("Cookie", sessionId);
        }
        HTTP_UTILS.send(HttpMethod.POST, photoUrl, requestParams, new com.lidroid.xutils.http.callback.RequestCallBack<String>() {
            @Override
            public void onFailure(HttpException httpException, String arg1) {
                listener.onLoadError(FILE_UPLOAD, false, "上报失败");
                try {
                    throw new CustomException("上传失败:" + arg1);
                } catch (CustomException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                int progress = (int) (current * 100 / total);
                if (progress > 100) {
                    progress = 100;
                } else if (progress < 0) {
                    progress = 0;
                }
                listener.onLoadPregress(FILE_UPLOAD, progress);
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                LogUtil.e(this, "上传完成 HttpCode:" + responseInfo.result);
                JSONObject json;
                try {
                    json = new JSONObject(responseInfo.result);
                    result.setSuccess("0".equals(getStringValue(json, "code")));
                    result.setMessage(getStringValue(json, "message"));
                    result.setEntry(getStringValue(json, "data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onLoadComplete(FILE_UPLOAD, result);
                    }
                });
            }
        });
    }

    private List<TaskCountInfo> parseCount(JSONObject list, String name) {
        List<TaskCountInfo> taskList = new ArrayList<>();
        JSONArray dataList = getJSONArray(list, name);
        try {
            if (name.equals("预警信息")) {
                for (int i = 0; i < dataList.length(); i++) {
                    JSONObject jsonObject = dataList.getJSONObject(i);
                    if (jsonObject.has("type")) {
                        TaskCountInfo taskEntity = new TaskCountInfo();
                        taskEntity.setEmergencyName(getStringValue(jsonObject, "type"));
                        taskEntity.setEmergencyNum(getIntValue(jsonObject, "num"));
                        taskList.add(taskEntity);
                    }
                }
            } else {
                for (int i = 0; i < dataList.length(); i++) {
                    TaskCountInfo taskEntity = new Gson().fromJson(dataList.getString(i), TaskCountInfo.class);
                    taskList.add(taskEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskList;
    }
}
