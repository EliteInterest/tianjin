package com.zx.tjmarketmobile.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.zx.tjmarketmobile.util.Util;

import java.io.File;

public class PhoneService extends Service {
    PhoneListener phoneListener;
    TelephonyManager telephonyManager;
    private MediaRecorder mediaRecorder = null;
    private File file;
    private boolean isRecording = false;
    String path;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneListener = new PhoneListener();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("wangwansheng","start service...");
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        return START_STICKY;
    }

    private final class PhoneListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            try {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING: //来电
                        Log.i("wangwansheng", "来电");
                        break;

                    case TelephonyManager.CALL_STATE_OFFHOOK: //接通电话
                        Log.i("wangwansheng", "接通电话");
                        try {
                            path = Util.getSDPath();
                            if (path != null) {
                                File dir = new File(Util.Myaudiopath);
                                if (!dir.exists()) {
                                    dir.mkdirs();
                                }
                                path = dir + "/" + Util.getDate() + ".mp3";
                                Log.i("wangwansheng", "path is " + path);
                                startAudio(path);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE: //挂断电话
                        if (mediaRecorder != null && isRecording) {
                            Log.i("wangwansheng", "stop dial");
                            mediaRecorder.stop();
                            mediaRecorder.release();
                            mediaRecorder = null;
                            //cancel listen phone state
                            telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_NONE);
                            Intent intent = new Intent("com.zx.tjmarketmobile.broadcast.phonevideo.path");
                            intent.putExtra("audioPath", path);
                            sendBroadcast(intent);
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

            PackageManager pkm = getPackageManager();
            boolean has_permission = (PackageManager.PERMISSION_GRANTED
                    == pkm.checkPermission("android.permission.RECORD_AUDIO", "com.zx.tjmarketmobile"));
            if (has_permission) {
                Log.i("wangwansheng", "start recoding...");
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mediaRecorder.setOutputFile(filePath);
                mediaRecorder.prepare();
                mediaRecorder.start();
                isRecording = true;
            } else {
                Log.i("wangwansheng", "has not pemission..");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
