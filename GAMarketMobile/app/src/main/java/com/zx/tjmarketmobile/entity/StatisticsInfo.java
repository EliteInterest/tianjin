package com.zx.tjmarketmobile.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StatisticsInfo implements Serializable {

	public String labelName;
	public List<StatisticsItemInfo> itemList = new ArrayList<>();

}
