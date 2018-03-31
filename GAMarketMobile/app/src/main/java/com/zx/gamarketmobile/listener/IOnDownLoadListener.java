package com.zx.gamarketmobile.listener;


public interface IOnDownLoadListener {
	public void onProgress(int progress);

	public void onfailed(String msg);
}