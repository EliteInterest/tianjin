package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

public class EntitySimpleInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 697555978264856532L;
	
	private String F_GUID;
	private String F_ENTITY_GUID;
	private String fCredtieLevel;
	private String F_ENTITY_NAME;
	
	public String getF_Guid() {
		return F_GUID;
	}

	public void setF_Guid(String f_Guid) {
		this.F_GUID = f_Guid;
	}

	public String getF_Entity_Guid() {
		return F_ENTITY_GUID;
	}

	public void setF_Entity_Guid(String f_Entity_Guid) {
		this.F_ENTITY_GUID = f_Entity_Guid;
	}

	public String getfCredtieLevel() {
		return fCredtieLevel;
	}

	public void setfCredtieLevel(String fCredtieLevel) {
		this.fCredtieLevel = fCredtieLevel;
	}

	public String getF_Entity_Name() {
		return F_ENTITY_NAME;
	}

	public void setF_Entity_Name(String f_Entity_Name) {
		this.F_ENTITY_NAME = f_Entity_Name;
	}
	
	@Override
	public String toString() {
		return "EntitySimpleInfo [fGuid=" + F_GUID + ", fEntityGuid=" + F_ENTITY_GUID + ", fCredtieLevel=" + fCredtieLevel
				+ ", fEntityName=" + F_ENTITY_NAME + "]";
	}
	
}
