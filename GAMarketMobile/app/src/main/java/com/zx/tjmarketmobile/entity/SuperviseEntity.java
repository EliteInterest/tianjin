package com.zx.tjmarketmobile.entity;

import java.io.Serializable;
import java.util.List;

public class SuperviseEntity implements Serializable {
	public int currPageNo;
	public int pageSize;// 每页查询数
	public int pageTotal;// 总页数
	public int total;// 总条数
	public List<SuperviseInfo> superviseInfoList;// 查询结果
}
