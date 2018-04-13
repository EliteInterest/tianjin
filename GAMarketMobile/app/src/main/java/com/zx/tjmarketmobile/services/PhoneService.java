package com.zx.tjmarketmobile.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.zx.tjmarketmobile.util.Util;

import java.io.File;

public class PhoneService extends Service {
    TelephonyManager telephonyManager;
    PhoneListener phoneListener;

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
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        return START_STICKY;
    }

    private final class PhoneListener extends PhoneStateListener {
        private MediaRecorder mediaRecorder;
        private File file;
        private Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            try {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING: //来电
                        Log.i("wangwansheng", "来电");
                        break;

                    case TelephonyManager.CALL_STATE_OFFHOOK: //接通电话
                        Log.i("wangwansheng", "接通电话");
                        File outAudioDir = new File(Util.Myaudiopath);
                        if (!outAudioDir.exists()) {
                            outAudioDir.mkdirs();
                        }

                        try {
                            String path = Util.getSDPath();
                            if (path != null) {
                                File dir = new File(Util.Myaudiopath);
                                if (!dir.exists()) {
                                    dir.mkdirs();
                                }
                                path = dir + "/" + Util.getDate() + ".3gp";
                                Log.i("wangwansheng", "incomingNumber is " + incomingNumber);
                                mediaRecorder = new MediaRecorder();
                                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                                Log.i("wangwansheng", "file is " + path);
//                                mediaRecorder.setOutputFile(file.getAbsolutePath());
                                mediaRecorder.setOutputFile(path);
                                mediaRecorder.prepare();
                                mediaRecorder.start();
                                Toast.makeText(getApplicationContext(), "电话已接通，开始录音。", Toast.LENGTH_SHORT).show();
                                //震动一下
                                vibrator.vibrate(100);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE: //挂断电话
                        Log.i("wangwansheng", "挂断电话");
                        if (mediaRecorder != null) {
                            mediaRecorder.stop();
                            mediaRecorder.release();
                            mediaRecorder = null;
                            Toast.makeText(getApplicationContext(), "电话已挂断，录音停止。", Toast.LENGTH_SHORT).show();
                            //震动一下
                            vibrator.vibrate(100);
                            //cancel listen phone state
                            telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_NONE);
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
