package com.zx.tjmarketmobile.manager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.http.BaseRequestData.OnHttpLoadingListener;
import com.zx.tjmarketmobile.listener.IOnDownLoadListener;
import com.zx.tjmarketmobile.util.FileDownLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class DownLoadService extends Service implements OnHttpLoadingListener<BaseHttpResult> {

	private FileDownLoader mFileDownLoader = new FileDownLoader();
	private ApiData updateData = new ApiData(ApiData.HTTP_ID_map_update);
	private List<String> mFileList;
	private static final String MAP_BASE_PATH = "/mnt/sdcard/GAMarket/map/";

	@Override
	public void onDestroy() {
		mFileDownLoader.cancelTask();
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, final int startId) {
		updateData.setLoadingListener(this);
		updateData.loadData(getVersionByMap("vector"), getVersionByMap("image"), getVersionByMap("render"));
		return super.onStartCommand(intent, flags, startId);
	}

	private int getVersionByMap(String dirPath) {
		int version = 0;
		try {
			InputStream input = getInputFromFile(MAP_BASE_PATH + dirPath + "/version.xml");
			version = ParserXml.parser(input, new BaseParser());
		} catch (Exception e) {
		}
		return version;
	}

	private InputStream getInputFromFile(String path) throws FileNotFoundException {
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		return new FileInputStream(file);
	}

	@Override
	public void onLoadStart(int id) {
	}

	@Override
	public void onLoadError(int id, boolean isAPIError, String error_message) {
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onLoadComplete(int id, BaseHttpResult b) {
		if (b.isSuccess()) {
			mFileList = (List<String>) b.getEntry();
			new Thread() {
				public void run() {
					for (String path : mFileList) {
						path = path.replace("\\", "/");
						String url = ApiData.baseUrl + path;
						String filePath = MAP_BASE_PATH + path;
						mFileDownLoader.syncDownloadFile(onDownLoadListener, url, filePath);
					}
				};
			}.start();

		}
	}

	@Override
	public void onLoadPregress(int id, int progress) {

	}

	IOnDownLoadListener onDownLoadListener = new IOnDownLoadListener() {
		@Override
		public void onfailed(String msg) {
		}

		@Override
		public void onProgress(int progress) {
			if (progress == 100) {
				for (String path : mFileList) {
					path = path.replace("\\", "/");
					File file = new File(MAP_BASE_PATH + path);
					if (!file.exists()) {
						return;
					}
				}
				// 重命名文件夹
			}
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
