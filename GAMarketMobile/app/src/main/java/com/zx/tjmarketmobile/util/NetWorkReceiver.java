package com.zx.tjmarketmobile.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.zx.tjmarketmobile.manager.DownLoadService;

public class NetWorkReceiver extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			changeServiceStatus(context, true);
		} else {
			changeServiceStatus(context, false);
		}
	}

	@SuppressWarnings("deprecation")
	public static void changeServiceStatus(Context context, boolean isStart) {
		boolean isServiceRunning = false;
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			String className = "com.zx.tjmarketmobile.manager.DownLoadService";
			if (className.equals(service.service.getClassName())) {
				isServiceRunning = true;
				break;
			}
		}
		Intent intent = new Intent(context, DownLoadService.class);
		if (!isServiceRunning) {
			if (isStart) {
				context.startService(intent);
			}
		} else {
			if (!isStart) {
				context.stopService(intent);
			}
		}
	}
}
