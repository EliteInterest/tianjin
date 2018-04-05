package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

/**
 * @author Administrator
 * 图片实体
 */
public class PicEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5181170277531167691L;
	
	private String path = "default";
	private String soundTime = "0";
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getSoundTime() {
		return soundTime;
	}
	public void setSoundTime(String soundTime) {
		this.soundTime = soundTime;
	}
	@Override
	public String toString() {
		return "PicEntity [path=" + path + ", soundTime=" + soundTime + "]";
	}
	
}
