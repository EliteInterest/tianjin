package com.zx.tjmarketmobile.entity;

import com.zx.tjmarketmobile.entity.supervise.MyTaskCheckEntity;
import com.zx.tjmarketmobile.entity.supervise.MyTaskListEntity;
import com.zx.tjmarketmobile.entity.supervise.SuperviseEquimentEntity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2017/3/13.
 * 功能：通用列表实体类
 */
public class NormalListEntity {

    private int currPageNo;//当前页数
    private int pageSize;// 每页查询数
    private int pageTotal;// 总页数
    private int total;// 总条数
    private List<CaseInfoEntity> caseInfoEntityList;//案件信息列表
    private List<ComplainInfoEntity> complainInfoList;//投诉举报列表
    private List<JSONObject> chartList;
    private List<MyTaskCheckEntity> taskCheckEntities;
    private List<MyTaskListEntity> myTaskListEntities;
    private List<KeyValueInfo> keyValueInfoList;
    private List<SuperviseEquimentEntity> equimentEntityList;//特种设备列表

    public List<KeyValueInfo> getKeyValueInfoList() {
        return keyValueInfoList;
    }

    public void setKeyValueInfoList(List<KeyValueInfo> keyValueInfoList) {
        this.keyValueInfoList = keyValueInfoList;
    }

    public List<MyTaskListEntity> getMyTaskListEntities() {
        return myTaskListEntities;
    }

    public void setMyTaskListEntities(List<MyTaskListEntity> myTaskListEntities) {
        this.myTaskListEntities = myTaskListEntities;
    }

    public List<MyTaskCheckEntity> getTaskCheckEntities() {
        return taskCheckEntities;
    }

    public void setTaskCheckEntities(List<MyTaskCheckEntity> taskCheckEntities) {
        this.taskCheckEntities = taskCheckEntities;
    }

    public List<SuperviseEquimentEntity> getEquimentEntityList() {
        return equimentEntityList;
    }

    public void setEquimentEntityList(List<SuperviseEquimentEntity> equimentEntityList) {
        this.equimentEntityList = equimentEntityList;
    }

    public List<JSONObject> getChartList() {
        return chartList;
    }

    public void setChartList(List<JSONObject> chartList) {
        this.chartList = chartList;
    }

    public List<ComplainInfoEntity> getComplainInfoList() {
        return complainInfoList;
    }

    public void setComplainInfoList(List<ComplainInfoEntity> complainInfoList) {
        this.complainInfoList = complainInfoList;
    }

    public int getCurrPageNo() {
        return currPageNo;
    }

    public void setCurrPageNo(int currPageNo) {
        this.currPageNo = currPageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CaseInfoEntity> getCaseInfoEntityList() {
        if (caseInfoEntityList == null) {
            return new ArrayList<>();
        }
        return caseInfoEntityList;
    }

    public void setCaseInfoEntityList(List<CaseInfoEntity> caseInfoEntityList) {
        this.caseInfoEntityList = caseInfoEntityList;
    }
}
