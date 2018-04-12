package com.zx.tjmarketmobile.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.ui.system.SettingsMapActivity;
import com.zx.tjmarketmobile.entity.HttpUpdateEntity;
import com.zx.tjmarketmobile.listener.IOnDownLoadListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Xiangb on 2017/3/1.
 * 功能：
 */
public class MapDownLoadUtil {

    private boolean mIsDownLoading = false;
    private Context context;
    private HttpUpdateEntity entity;

    public MapDownLoadUtil(Context context, HttpUpdateEntity entity) {
        this.context = context;
        this.entity = entity;
    }

    public void downloadMap() {
        Util.showProgressDialog(context, 0, "正在下载矢量地图包");
        new Thread() {
            public void run() {
                if (mIsDownLoading == false) {
                    mIsDownLoading = true;
                    SharedPreferences mSharePreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    //检查是否为正式环境
                    String ip = "";
                    if (ApiData.ISRELEASE) {
                        ip = "http://" + ConstStrings.ip1 + "." + ConstStrings.ip2 + "." + ConstStrings.ip3 + "." + ConstStrings.ip4 + ":" + ConstStrings.ipport;
                    } else {
                        ip = "http://" + mSharePreferences.getString("ip1", ConstStrings.ip1) + "."
                                + mSharePreferences.getString("ip2", ConstStrings.ip2) + "."
                                + mSharePreferences.getString("ip3", ConstStrings.ip3) + "."
                                + mSharePreferences.getString("ip4", ConstStrings.ip4) + ":"
                                + mSharePreferences.getString("ipport", ConstStrings.ipport);
                    }
                    downLoadFile(ip + getDownLoadUrl(entity.url), downLoadListener);
                }
            }
        }.start();
    }


    private String getDownLoadUrl(String url) {
        return url + "." + ConstStrings.VECTOR_DATA_TYPE;
    }

    private String getDownLoadFileName() {
        return ConstStrings.VECTOR_DATA_NAME + "." + ConstStrings.VECTOR_DATA_TYPE;
    }

    private void downLoadFile(String downloadUrl, IOnDownLoadListener listener) {
        String msg = "";
        File rarFile = null;
        File updateDir = null;
        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection hc = (HttpURLConnection) url.openConnection();
            InputStream in = null;
            try {
                in = hc.getInputStream();
            } catch (IOException e1) {
                e1.printStackTrace();
                if (listener != null) {
                    listener.onfailed("文件不存在");
                }
                return;
            }
            hc.setConnectTimeout(15000);
            updateDir = new File(CQDigitalUtil.getDataPath() + ConstStrings.VECTOR_DATA_NAME);
            if (!updateDir.exists()) {
                updateDir.mkdirs();
            }
            rarFile = new File(updateDir.getPath(), getDownLoadFileName());
            if (rarFile.exists()) {
                rarFile.delete();
            }
            rarFile.createNewFile();
            try {
                Runtime.getRuntime().exec("chmod 705 " + updateDir.getPath());
                Runtime.getRuntime().exec("chmod 604 " + rarFile.getPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            FileOutputStream out = new FileOutputStream(rarFile);
            byte[] bytes = new byte[1024];
            int c;
            long lastProgress = 0;
            long currentNum = 0;
            long total = hc.getContentLength();
            while ((c = in.read(bytes)) != -1) {
                out.write(bytes, 0, c);
                currentNum += c;
                int progress = (int) ((currentNum * 100) / total);
                if (progress - lastProgress > 1) {
                    if (listener != null) {
                        listener.onProgress(progress);
                    }
                    lastProgress = progress;
                }
            }
            in.close();
            out.close();
            return;
        } catch (MalformedURLException e) {
            msg = "网络异常,文件下载失败";
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            msg = "文件不存在";
            e.printStackTrace();
        } catch (IOException e) {
            msg = "网络连接断开,文件下载失败";
            e.printStackTrace();
        }
        if (rarFile != null) {
            rarFile.delete();
            rarFile = null;
        }
        if (listener != null) {
            listener.onfailed(msg);
        }
    }

    IOnDownLoadListener downLoadListener = new IOnDownLoadListener() {
        @Override
        public void onfailed(String str) {
            Message msg = handler.obtainMessage();
            msg.what = -1;
            msg.obj = str;
            handler.sendMessage(msg);
        }

        @Override
        public void onProgress(int progress) {
            Message msg = handler.obtainMessage();
            msg.arg1 = progress;
            if (progress == 100) {
                msg.what = 2;//完成
            } else {
                msg.what = 1;
            }
            handler.sendMessage(msg);
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler unRarHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UnRarOrZip.start:
                    Util.showProgressDialog(context, 0, "正在解压矢量地图");
                    break;
                case UnRarOrZip.end:
                    //写入配置文件
                    boolean a = TxtUtil.writeTxt(context, CQDigitalUtil.getDataPath() + ConstStrings.VECTOR_DATA_NAME, "mapVersion", entity.versionName + ":" + entity.versionCode);
                    Util.closeProgressDialog();
                    Toast.makeText(context, "解压完成!", Toast.LENGTH_SHORT).show();
                    ((SettingsMapActivity) context).tvMapName.setText("当前版本号:" + entity.versionName + ":" + entity.versionCode);
                    File file = new File(CQDigitalUtil.getDataPath()
                            + ConstStrings.VECTOR_DATA_NAME
                            + "/"
                            + ConstStrings.VECTOR_DATA_NAME
                            + "." + ConstStrings.VECTOR_DATA_TYPE);
                    if (file.exists()) {
                        file.delete();
                    }
                    break;
                case UnRarOrZip.percent:
                    double percent = msg.getData().getDouble("percent");
                    Util.showProgressDialog(context, (int) percent, "正在解压矢量地图");
                    break;
                default:
                    break;
            }
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int result = msg.arg1;
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Util.showProgressDialog(context, result, "正在下载矢量地图包");
                    break;
                case 2://完成
//                    try {
//                        UnRarOrZip.deCompress(unRarHander,
//                                CQDigitalUtil.getDataPath() + ConstStrings.VECTOR_DATA_NAME + "/"+ConstStrings.VECTOR_DATA_NAME,
//                                CQDigitalUtil.getDataPath() + ConstStrings.VECTOR_DATA_NAME + "/",ConstStrings.VECTOR_DATA_TYPE);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    boolean a = TxtUtil.writeTxt(context, CQDigitalUtil.getDataPath() + ConstStrings.VECTOR_DATA_NAME, "mapVersion", entity.versionName + ":" + entity.versionCode);
                    Util.closeProgressDialog();
                    Toast.makeText(context, "下载完成!", Toast.LENGTH_SHORT).show();
                    ((SettingsMapActivity) context).tvMapName.setText("当前版本号:" + entity.versionName + ":" + entity.versionCode);
//                    File file = new File(CQDigitalUtil.getDataPath()
//                            + ConstStrings.VECTOR_DATA_NAME
//                            +"."+ConstStrings.VECTOR_DATA_TYPE);
//                    if (file.exists()) {
//                        file.delete();
//                    }
                    break;
                case -1://失败
                    Util.closeProgressDialog();
                    Toast.makeText(context, msg.obj + "", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
}
