package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

public class SelectInfo implements Serializable {
	public SelectInfo(String name) {
		this.name = name;
	}

	public String name;
	public boolean isSelected = false;
}
