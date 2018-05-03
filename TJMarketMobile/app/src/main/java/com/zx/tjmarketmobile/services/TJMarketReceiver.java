package com.zx.tjmarketmobile.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.zx.tjmarketmobile.util.Util;

import java.io.File;
import java.util.logging.Handler;

public class TJMarketReceiver extends BroadcastReceiver {
    private Handler mHandler;
    private Context mContext;

    TelephonyManager telephonyManager;
    PhoneListener phoneListener;
    private MediaRecorder mediaRecorder = null;
    private File file;
    private boolean isRecording = false;
    String path;

    @Override
    public void onReceive(Context var1, Intent var2) {
        if (var2.getAction().equals("com.zx.tjmarketmobile.broadcast.phonestate.start")) {
            //start service for monitor audio of phone
            Log.e("wangwansheng", "start service for monitor dial state...");
            mContext = var1;

            telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            phoneListener = new PhoneListener();
            telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        }
    }


    private final class PhoneListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            try {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING: //来电
                        Log.e("wangwansheng", "来电");
                        break;

                    case TelephonyManager.CALL_STATE_OFFHOOK: //接通电话
                        Log.e("wangwansheng", "接通电话");
                        try {
                            path = Util.getSDPath();
                            if (path != null) {
                                File dir = new File(Util.Myaudiopath);
                                if (!dir.exists()) {
                                    dir.mkdirs();
                                }
//                                else {
//                                    ZXFileUtil.deleteFiles(dir);
//                                }
                                path = dir + "/" + Util.getDate() + ".mp3";
                                Log.e("wangwansheng", "path is " + path);
                                startAudio(path);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE: //挂断电话
                        if (mediaRecorder != null && isRecording) {
                            Log.e("wangwansheng", "stop dial");
                            mediaRecorder.stop();
                            mediaRecorder.release();
                            mediaRecorder = null;
                            //cancel listen phone state
//                            telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_NONE);
                            Intent intent = new Intent("com.zx.tjmarketmobile.broadcast.phonevideo.path");
                            intent.putExtra("audioPath", path);
                            mContext.sendBroadcast(intent);
                            isRecording = false;
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startAudio(String filePath) {
        try {

            PackageManager pkm = mContext.getPackageManager();
            boolean has_permission = (PackageManager.PERMISSION_GRANTED
                    == pkm.checkPermission("android.permission.RECORD_AUDIO", "com.zx.tjmarketmobile"));
            if (has_permission) {
                Log.e("wangwansheng", "start recoding...");
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mediaRecorder.setOutputFile(filePath);
                mediaRecorder.prepare();
                mediaRecorder.start();
                isRecording = true;
            } else {
                Log.e("wangwansheng", "has not pemission..");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
