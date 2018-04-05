package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

public class SuperviseCountInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -980675153806933590L;
	private String grid;
	private String gridAlias;//别名
	private String realName;
	private String userid;
	private String total;
	private String count1;
	private String count2;
	private String count3;
	private String count4;
	private boolean isZiban = false;//是否为自办监管任务
	private boolean isSelected = false;
	private String fGroupName;
	private String forDisposal;
	private String F_GROUP_NAME;
	private String entityNum;
	private String forSecondTrial;
	private String forFinal;
	private String finish;

	public String getForDisposal() {
		return forDisposal;
	}

	public void setForDisposal(String forDisposal) {
		this.forDisposal = forDisposal;
	}

	public String getF_GROUP_NAME() {
		return F_GROUP_NAME;
	}

	public void setF_GROUP_NAME(String f_GROUP_NAME) {
		F_GROUP_NAME = f_GROUP_NAME;
	}

	public String getEntityNum() {
		return entityNum;
	}

	public void setEntityNum(String entityNum) {
		this.entityNum = entityNum;
	}

	public String getForSecondTrial() {
		return forSecondTrial;
	}

	public void setForSecondTrial(String forSecondTrial) {
		this.forSecondTrial = forSecondTrial;
	}

	public String getForFinal() {
		return forFinal;
	}

	public void setForFinal(String forFinal) {
		this.forFinal = forFinal;
	}

	public String getFinish() {
		return finish;
	}

	public void setFinish(String finish) {
		this.finish = finish;
	}

	public String getForFirstTrial() {
		return forFirstTrial;
	}

	public void setForFirstTrial(String forFirstTrial) {
		this.forFirstTrial = forFirstTrial;
	}

	private String forFirstTrial;

	public String getfGroupName() {
		return fGroupName;
	}

	public void setfGroupName(String fGroupName) {
		this.fGroupName = fGroupName;
	}

	public String getF_GROUP_ID() {
		return F_GROUP_ID;
	}

	public void setF_GROUP_ID(String f_GROUP_ID) {
		F_GROUP_ID = f_GROUP_ID;
	}

	private String F_GROUP_ID;
	
	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public String getGridAlias() {
		return gridAlias;
	}

	public void setGridAlias(String gridAlias) {
		this.gridAlias = gridAlias;
	}
	
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	
	public String getCount1() {
		return count1;
	}

	public void setCount1(String count1) {
		this.count1 = count1;
	}

	public String getCount2() {
		return count2;
	}

	public void setCount2(String count2) {
		this.count2 = count2;
	}

	public String getCount3() {
		return count3;
	}

	public void setCount3(String count3) {
		this.count3 = count3;
	}
	
	public String getCount4() {
		return count4;
	}

	public void setCount4(String count4) {
		this.count4 = count4;
	}

	public boolean isZiban() {
		return isZiban;
	}

	public void setZiban(boolean isZiban) {
		this.isZiban = isZiban;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	@Override
	public String toString() {
		return "SuperviseCountInfo [grid=" + grid + ", realName=" + realName + ", userid=" + userid + ", count1="
				+ count1 + ", count2=" + count2 + ", count3=" + count3 + ", isZiban=" + isZiban + "]";
	}
	 
}
