package com.zx.tjmarketmobile.entity;

import java.io.Serializable;
import java.util.List;

public class HttpEventEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8084045499497129445L;
    public int eventId;
    public String eventName;
    public String eventTime;
    public String startTime;
    public String eventSource;
    public String eventType;
    public String eventLevel;
    public String entityId;
    public String entityName;
    public String eventAddr;
    public String eventDesc;
    private String eventResult;
    private String eventStatus;
    public double longitude;
    public double latitude;
    public int wkid;//投影坐标系ID
    public List<EventUserEntity> userList;//应急人员列表
    public List<EventContactEntity> contactList;//相关单位列表
    public List<EventResourceEntity> resList;//应急资源列表

    @Override
    public String toString() {
        return "HttpEventEntity [eventId=" + eventId + ", eventName=" + eventName + ", eventTime=" + eventTime
                + ", startTime=" + startTime + ", eventSource=" + eventSource + ", eventType=" + eventType
                + ", eventLevel=" + eventLevel + ", entityId=" + entityId + ", entityName=" + entityName
                + ", eventAddr=" + eventAddr + ", eventDesc=" + eventDesc + ", eventResult=" + eventResult
                + ", eventStatus=" + eventStatus + ", longitude=" + longitude + ", latitude=" + latitude + ", wkid=" + wkid + ", userList=" + userList
                + ", contactList=" + contactList + ", resList=" + resList + "]";
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(String eventLevel) {
        this.eventLevel = eventLevel;
    }

    public String getEventResult() {
        return eventResult;
    }

    public void setEventResult(String eventResult) {
        this.eventResult = eventResult;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventAddr() {
        return eventAddr;
    }

    public void setEventAddr(String eventAddr) {
        this.eventAddr = eventAddr;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getWkid() {
        return wkid;
    }

    public void setWkid(int wkid) {
        this.wkid = wkid;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public List<EventUserEntity> getUserList() {
        return this.userList;
    }

    public void setUserList(List<EventUserEntity> userList) {
        this.userList = userList;
    }

    public List<EventContactEntity> getContactList() {
        return this.contactList;
    }

    public void setContactList(List<EventContactEntity> contactList) {
        this.contactList = contactList;
    }

    public List<EventResourceEntity> getResourceList() {
        return this.resList;
    }

    public void setResourceList(List<EventResourceEntity> resList) {
        this.resList = resList;
    }

}
