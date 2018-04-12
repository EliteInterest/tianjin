package com.zx.tjmarketmobile.entity;

/**
 * Created by Xiangb on 2018/4/11.
 * 功能：
 */

public class LegalEntity {

    /**
     * id : 1
     * name : 公共法律法规
     * parentId : null
     * url : null
     * icon : null
     * orders : null
     * departmentId : null
     * userId : null
     * fileId : null
     * childMenus : [{"id":4,"name":"修改测试","parentId":3,"url":null,"icon":null,"orders":null,"departmentId":null,"userId":null,"fileId":null,"childMenus":[{"id":5,"name":"个人独资企业登记管理办法","parentId":4,"url":null,"icon":null,"orders":1,"departmentId":"00","userId":"f9aea146c8ea11e79fcc000c2934879e","fileId":"FB3901126C4A40A1B8CAE675B8FD3499","childMenus":null,"childMenuTotal":0},{"id":12,"name":"徐杨航的王法","parentId":4,"url":null,"icon":null,"orders":2,"departmentId":null,"userId":null,"fileId":"FB624B854E9F41A0B5549E42CEE1E895","childMenus":null,"childMenuTotal":0}],"childMenuTotal":2},{"id":8,"name":"徐杨航的王法","parentId":2,"url":null,"icon":null,"orders":1,"departmentId":null,"userId":null,"fileId":"66F6B6C396BB4E0FA43762F04F5180E1","childMenus":null,"childMenuTotal":0},{"id":13,"name":"徐杨航","parentId":1,"url":null,"icon":null,"orders":1,"departmentId":null,"userId":null,"fileId":null,"childMenus":null,"childMenuTotal":0},{"id":7,"name":"公共法律法规测试","parentId":3,"url":null,"icon":null,"orders":2,"departmentId":null,"userId":null,"fileId":null,"childMenus":null,"childMenuTotal":0},{"id":9,"name":"徐杨航的王法","parentId":2,"url":null,"icon":null,"orders":2,"departmentId":null,"userId":null,"fileId":"CC35A95A556548B291FD3DA157DBC092","childMenus":null,"childMenuTotal":0}]
     * childMenuTotal : 0
     */

    private int id;
    private String name;
    private String parentId;
    private String url;
    private String icon;
    private String orders;
    private String departmentId;
    private String userId;
    private String fileId;
    private int childMenuTotal;
    private int selecteRadio = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public int getChildMenuTotal() {
        return childMenuTotal;
    }

    public void setChildMenuTotal(int childMenuTotal) {
        this.childMenuTotal = childMenuTotal;
    }

    public int getSelecteRadio() {
        return selecteRadio;
    }

    public void setSelecteRadio(int selecteRadio) {
        this.selecteRadio = selecteRadio;
    }
}
