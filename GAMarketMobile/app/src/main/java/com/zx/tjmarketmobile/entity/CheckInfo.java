package com.zx.tjmarketmobile.entity;

import java.io.Serializable;
import java.util.List;

public class CheckInfo implements Serializable {


	/**
	 * departmentName : 消保科
	 * updateDate : null
	 * max : null
	 * departmentId : 0
	 * orderNum : null
	 * pId : null
	 * remark : null
	 * type : null
	 * checkResult : null
	 * itemName : 食品生产日常监督检查要点表
	 * min : null
	 * children : [{"departmentName":"消保科","updateDate":null,"max":null,"departmentId":"0","orderNum":null,"pId":"1","remark":null,"type":null,"checkResult":null,"itemName":"进货查验结果\r\n注：①检查原辅料仓库；②原辅料品种随机抽查，不足2种的全部检查。","min":null,"children":[],"id":"1","remark1":null,"updatePerson":null,"remark2":null}]
	 */

	private String departmentName;
	private Object updateDate;
	private String max;
	private String departmentId;
	private Object orderNum;
	private Object pId;
	private Object remark;
	private Object type;
	private String checkResult;
	private String itemName;
	private String min;
	private String id;
	private List<CheckInfo> children;
	private int index = 0;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Object getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Object updateDate) {
		this.updateDate = updateDate;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public Object getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Object orderNum) {
		this.orderNum = orderNum;
	}

	public Object getPId() {
		return pId;
	}

	public void setPId(Object pId) {
		this.pId = pId;
	}

	public Object getRemark() {
		return remark;
	}

	public void setRemark(Object remark) {
		this.remark = remark;
	}

	public Object getType() {
		return type;
	}

	public void setType(Object type) {
		this.type = type;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public List<CheckInfo> getChildren() {
		return children;
	}

	public void setChildren(List<CheckInfo> children) {
		this.children = children;
	}
}