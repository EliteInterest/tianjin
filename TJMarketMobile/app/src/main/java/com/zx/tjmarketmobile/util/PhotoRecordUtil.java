package com.zx.tjmarketmobile.util;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import java.io.File;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by Xiangb on 2018/4/17.
 * 功能：
 */

public class PhotoRecordUtil {
    private static boolean readyCallback = false;

    public static void doPhotoRecord(Context context, String tel, PhotoRecordUtil.PhotoRecordListener photoRecordListener) {
        readyCallback = false;
        File dir = new File(Util.Myaudiopath);
        ZXFileUtil.deleteFiles(dir);
        ZXDialogUtil.showYesNoDialog(context, "提示", "是否开启电话录音？(通话请打开扬声器以保证录音质量)", "开启", "不开启", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Intent broadCase = new Intent("com.zx.tjmarketmobile.broadcast.phonestate.start");
//                context.sendBroadcast(broadCase);
                Intent photoService = new Intent("com.zx.tjmarketmobile.services.PhoneService");
                photoService.setPackage(context.getPackageName());
                context.startService(photoService);
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.zx.tjmarketmobile.broadcast.phonevideo.path");
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String path = intent.getStringExtra("audioPath");
                File file = new File(path);
                if (file.exists() && photoRecordListener != null && readyCallback == false) {
                    readyCallback = true;
                    //获取ActivityManager
                    ActivityManager mAm = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
                    //获得当前运行的task
                    List<ActivityManager.RunningTaskInfo> taskList = mAm.getRunningTasks(100);
                    for (ActivityManager.RunningTaskInfo rti : taskList) {
                        //找到当前应用的task，并启动task的栈顶activity，达到程序切换到前台
                        if (rti.topActivity.getPackageName().equals(context.getPackageName())) {
                            mAm.moveTaskToFront(rti.id, 0);
                            break;
                        }
                    }
                    ZXDialogUtil.showYesNoDialog(context, "提示", "获取到通话录音，是否进行上传？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            photoRecordListener.onPhotoRecord(file);
                        }
                    });
                }
            }
        }, filter);
    }

    public interface PhotoRecordListener {
        void onPhotoRecord(File file);
    }
}
