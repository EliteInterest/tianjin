package com.zx.tjmarketmobile.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.zx.tjmarketmobile.listener.IOnDownLoadListener;

public class FileDownLoader {

	private ExecutorService mImageThreadPool = null;
	private List<Future> mFutureList = new ArrayList<Future>(); // 线程列表
	private HashMap<String, String> mURLCache = new HashMap<String, String>();
	private static final String SUFFIX_FILE_NAME = ".temp"; // 文件后缀

	public ExecutorService getThreadPool() {
		synchronized (ExecutorService.class) {
			if (mImageThreadPool == null) {
				mImageThreadPool = Executors.newFixedThreadPool(5);
			}
		}
		return mImageThreadPool;
	}

	public void syncDownloadFile(final IOnDownLoadListener onDownLoadListener, final String url, final String path) {
		File file = new File(path);
		if (!file.exists()) {
			if (!mURLCache.containsKey(url)) {
				mURLCache.put(url, path);
				Future future = getThreadPool().submit(new Runnable() {
					public void run() {
						downloadFile(onDownLoadListener, url, path);
					}
				});
				mFutureList.add(future);
			}
		} else {
			if(null != onDownLoadListener) {
				onDownLoadListener.onProgress(100);
			}
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 *            下载地址
	 * @param path
	 *            文件保存路径
	 * @param isZIP
	 *            是否为压缩文件
	 */
	private void downloadFile(IOnDownLoadListener onDownLoadListener, String url, String path) {
		final String name = path.substring(path.lastIndexOf("/") + 1);
		final String filePath = path.substring(0, path.lastIndexOf("/") + 1);
		File resultFile = null;
		try {
			resultFile = writeFile(onDownLoadListener, filePath, name, url);
		} catch (Exception e) {
			if (null != resultFile && resultFile.exists()) {
				resultFile.delete();
			}
			e.printStackTrace();
		}
		mURLCache.remove(url);
	}

	public static File writeFile(IOnDownLoadListener onDownLoadListener, String path, String name, String httpUrl) {
		InputStream is = null;
		File resultFile = null;
		FileOutputStream outStream = null;
		File file = new File(path + "/" + name + SUFFIX_FILE_NAME);
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection hc = (HttpURLConnection) url.openConnection();
			hc.setConnectTimeout(15000);
			is = hc.getInputStream();
			long totalSize = hc.getContentLength();
			if (is == null) {
				return null;
			}
			byte[] data = new byte[1024];
			if (file.exists()) {
				file.delete();
			}
			file.getParentFile().mkdirs();
			file.createNewFile();
			outStream = new FileOutputStream(file);
			int c = 0;
			long count = 0;
			while ((c = is.read(data)) != -1) {
				Thread.sleep(0);
				outStream.write(data, 0, c);
				count += c;
				if (null != onDownLoadListener) {
					onDownLoadListener.onProgress((int) ((count * 100) / totalSize));
				}
			}
			resultFile = new File(path, name);
			file.renameTo(resultFile);
			outStream.write(data);
		} catch (Exception e) {
			if (null != onDownLoadListener) {
				onDownLoadListener.onfailed("");
			}
			if (null != file && file.exists()) {
				file.delete();
			}
		}
		try {
			outStream.close();
			is.close();
		} catch (Exception e) {
		}
		return resultFile;
	}

	// 取消正在下载的任务
	public synchronized void cancelTask() {
		for (Future future : mFutureList) {
			future.cancel(true);
		}
		if (mImageThreadPool != null) {
			mImageThreadPool.shutdownNow();
			mImageThreadPool = null;
		}
	}

}
