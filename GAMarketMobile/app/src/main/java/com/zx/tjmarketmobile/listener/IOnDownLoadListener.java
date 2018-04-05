package com.zx.tjmarketmobile.listener;


public interface IOnDownLoadListener {
	public void onProgress(int progress);

	public void onfailed(String msg);
}