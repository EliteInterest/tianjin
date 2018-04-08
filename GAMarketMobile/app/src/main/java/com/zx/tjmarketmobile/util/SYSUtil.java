package com.zx.tjmarketmobile.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.telephony.TelephonyManager;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("deprecation")
public class SYSUtil {

	public static State wifiState = null;
	public static State mobileState = null;
	public static PackageManager packageManager = null;
	public static PackageInfo packageInfo = null;
	public static ApplicationInfo applicationInfo = null;
	public static TelephonyManager mTelephonyManager = null;
	public static String CACHE_DIR_NAME = "";
	/**
	 * @author zrspring 网络状态枚举
	 */
	public enum NetWorkState {
		WIFI, MOBILE, NONE;
	}

	/**
	 * 功能:获取PackageManager对象
	 * 
	 * @param context
	 * @return
	 */
	private static PackageManager getPackageManager(Context context) {
		if (packageManager == null) {
			packageManager = context.getApplicationContext().getPackageManager();
		}
		return packageManager;
	}

	/**
	 * 功能:获取PackageInfo对象
	 * 
	 * @param context
	 * @return
	 */
	public static PackageInfo getPackageInfo(Context context) {
		if (packageInfo == null) {
			try {
				packageInfo = getPackageManager(context)
						.getPackageInfo(context.getPackageName(), 0);
			} catch (PackageManager.NameNotFoundException e) {
				packageInfo = null;
			}
		}
		return packageInfo;
	}

	/**
	 * 功能:获取ApplicationInfo对象
	 * 
	 * @param context
	 * @return mTelephonyManager
	 */
	private static ApplicationInfo getApplicationInfo(Context context) {
		if (applicationInfo == null) {
			try {
				applicationInfo = getPackageManager(context).getApplicationInfo(
						context.getPackageName(), 0);
			} catch (PackageManager.NameNotFoundException e) {
				applicationInfo = null;
			}
		}
		return applicationInfo;
	}

	private static TelephonyManager getTelephonyManager(Context context) {
		if (mTelephonyManager == null) {
			try {
				mTelephonyManager = (TelephonyManager) context
						.getSystemService(Context.TELEPHONY_SERVICE);
			} catch (Exception e) {
				mTelephonyManager = null;
			}
		}
		return mTelephonyManager;
	}

	/**
	 * 功能:获取App程序名
	 * 
	 * @param context
	 * @return
	 */
	public static String getAppName(Context context) {
		String applicationName = getPackageManager(context).getApplicationLabel(
				getApplicationInfo(context)).toString();
		return applicationName;
	}

	/**
	 * 功能:获取App包名
	 * 
	 * @param context
	 * @return
	 */
	public static String getPackageName(Context context) {
		String packageName = getPackageInfo(context).packageName;
		return packageName;
	}

	/**
	 * 功能:获取版本名字
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		String versionName = getPackageInfo(context).versionName;
		return versionName;
	}

	/**
	 * 功能:获取程序版本
	 * 
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		int versionCode = getPackageInfo(context).versionCode;
		return versionCode;
	}

	/**
	 * 功能:获取App程序图标
	 * 
	 * @param context
	 * @return
	 */
	public static Drawable getAppIcon(Context context) {
		Drawable appIcon = getApplicationInfo(context).loadIcon(getPackageManager(context));
		return appIcon;
	}

	/**
	 * 功能:获取程序的签名
	 * 
	 * @param context
	 * @return
	 */
	public static String getAppSignature(Context context) {
		String appSignature = getPackageInfo(context).signatures[0].toCharsString();
		return appSignature;
	}

	/**
	 * 功能:判断当前系统是否插入SD卡
	 * 
	 * @return true：存在SD卡 false：不存在SD卡
	 */
	public static boolean isSdcardExist() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * 功能：获取SD卡的路径
	 * 
	 * @return SD卡的绝对路径
	 */
	public static String getSDPath() {
		File sdDir = null;
		if (isSdcardExist()) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		return sdDir.toString();
	}

	/**
	 * 获取当前的网络状态
	 * 
	 * @return
	 */
	public static NetWorkState getConnectState(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager.getActiveNetworkInfo() != null) { // connected to the internet
			if (manager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI) {
				wifiState = manager.getActiveNetworkInfo().getState();
			} else if (manager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE) {
				mobileState = manager.getActiveNetworkInfo().getState();
			}
		}
//		wifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
//		mobileState = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		if (wifiState != null && mobileState != null && State.CONNECTED != wifiState
				&& State.CONNECTED == mobileState) {
			return NetWorkState.MOBILE;
		} else if (wifiState != null && mobileState != null && State.CONNECTED != wifiState
				&& State.CONNECTED != mobileState) {
			return NetWorkState.NONE;
		} else if (wifiState != null && State.CONNECTED == wifiState) {
			return NetWorkState.WIFI;
		}
		return NetWorkState.NONE;
	}

	/**
	 * 获取设备IMEI号码
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		return getTelephonyManager(context).getDeviceId();
	}

	public static void loadImageCache(Context context) {
		CACHE_DIR_NAME = ConstStrings.INI_PATH+"/"+ConstStrings.APPNAME+"/CACHE/";
		File file = new File(CACHE_DIR_NAME);
		if (!file.exists()){
			file.mkdirs();
		}
		Picasso picasso = new Picasso.Builder(context).downloader(new OkHttpDownloader(file)).build();
		Picasso.setSingletonInstance(picasso);
	}
	/*
	*
	* 初始化工作目录
	*
	* */
	public static void initWorkSpaceDir(Context mContext){
//		boolean sdhave = isHasSdcard();
		boolean sdhave = false;
		sdhave = false;//默认为内置存储卡，外置存储卡权限获取目前没有合适的方法
		Double currentapiVersion = Double.valueOf(android.os.Build.VERSION.RELEASE.substring(0, 3));
		if (currentapiVersion>=6) {
			if(sdhave){
				ConstStrings.INI_PATH = getStoragePath(mContext, true);//返回true时，为外置内存卡路径  /storage/emulated/65537
				if (ConstStrings.INI_PATH==null){
					ConstStrings.INI_PATH = getStoragePath(mContext, false);
				}
			}else{
				ConstStrings.INI_PATH = getStoragePath(mContext, false);
			}
		}else {
//			if (sdhave) {
//				ConstStrings.INI_PATH = "/mnt/sdcard";
//			} else {
//				ConstStrings.INI_PATH = System.getenv("EXTERNAL_STORAGE");
//			}
			ConstStrings.INI_PATH = Environment.getExternalStorageDirectory().toString();
		}
		try {
			String mainPath = ConstStrings.INI_PATH+"/"+ConstStrings.APPNAME;
			File fileMain = new File(mainPath);
			if (!fileMain.exists()){
				fileMain.mkdirs();
			}
			loadImageCache(mContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断SD卡是否已装载
	 */
	public static boolean isHasSdcard()
	{
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	private static String getStoragePath(Context mContext, boolean is_removale) {

		StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
		Class<?> storageVolumeClazz = null;
		try {
			storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
			Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
			Method getPath = storageVolumeClazz.getMethod("getPath");
			Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
			Object result = getVolumeList.invoke(mStorageManager);
			final int length = Array.getLength(result);
			for (int i = 0; i < length; i++) {
				Object storageVolumeElement = Array.get(result, i);
				String path = (String) getPath.invoke(storageVolumeElement);
				boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
				if (is_removale == removable) {
					return path;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static Bitmap imageZoom(Bitmap bitMap) {
		Bitmap bm = null;
		//图片允许最大空间   单位：KB
		double maxSize = 400.00;
		//将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		//将字节换成KB
		double mid = b.length/1024;
		//判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
		if (mid > maxSize) {
			//获取bitmap大小 是允许最大大小的多少倍
			double i = mid / maxSize;
			//开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
			bm = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i), bitMap.getHeight() / Math.sqrt(i));
		}else{
			bm = bitMap;
		}

//		for (int i = 0; i < 10; i++) {
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//			byte[] b = baos.toByteArray();
//			//将字节换成KB
//			double mid = b.length/1024;
//			//判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
//			if (mid > maxSize) {
//				//获取bitmap大小 是允许最大大小的多少倍
//				double n = mid / maxSize;
//				//开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
//				bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(n), bitMap.getHeight() / Math.sqrt(n));
//			}else{
//				bm = bitMap;
//				break;
//			}
//
//		}
		return bm;
	}

	/***
	 * 图片的缩放方法
	 *
	 * @param bgimage
	 *            ：源图片资源
	 * @param newWidth
	 *            ：缩放后宽度
	 * @param newHeight
	 *            ：缩放后高度
	 * @return
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth,double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,(int) height, matrix, true);

		return bitmap;
	}

}
