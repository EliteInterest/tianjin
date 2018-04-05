package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

public class CheckItemInfoEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6652445318680991616L;
	private double checkResult;//1：是 0：否
	private String itemId;
	private String itemName;//内容
	private int valueType;//指标类型0：是否选项 1：评分项
	private double valueMin;//最小值 valueType为1时有效
	private double valueMax;//最大值 valueType为1时有效
	
	public double getCheckResult() {
		return this.checkResult;
	}
	
	public void setCheckResult(double checkResult) {
		this.checkResult = checkResult;
	}
	
	public String getItemId() {
		return this.itemId;
	}
	
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public String getItemName() {
		return this.itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public int getValueType() {
		return this.valueType;
	}
	
	public void setValueType(int valueType) {
		this.valueType = valueType;
	}
	
	public double getValueMin() {
		return this.valueMin;
	}
	
	public void setValueMin(double valueMin) {
		this.valueMin = valueMin;
	}
	
	public double getValueMax() {
		return this.valueMax;
	}
	
	public void setValueMax(double valueMax) {
		this.valueMax = valueMax;
	}
	
}
