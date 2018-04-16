package com.zx.tjmarketmobile.ui.camera;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.SGLog;
import com.zx.tjmarketmobile.util.ToastUtil;
import com.zx.tjmarketmobile.util.Util;
import com.zx.tjmarketmobile.util.Worker;
import com.zx.tjmarketmobile.util.ZXDialogUtil;
import com.zx.tjmarketmobile.video.ClipUtil;
import com.zx.tjmarketmobile.video.VideoCompressListener;
import com.zx.tjmarketmobile.video.VideoCompressor;
import com.zx.tjmarketmobile.video.VideoSetttingsListener;
import com.zx.tjmarketmobile.view.VideoSettingsPopuView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("Registered")
public class CameraActivity extends BaseActivity implements SurfaceHolder.Callback, View.OnClickListener {
    private static final String TAG = "CameraActivity";
    private Context mContext = null;
    private SurfaceView mSurfaceview;
    private boolean mStartedFlg = false;//是否正在录像
    private boolean mIsPlay = false;//是否正在播放录像
    private MediaRecorder mRecorder;
    private SurfaceHolder mSurfaceHolder;
    private SharedPreferences mSharedPreferences;
    private Camera camera;
    private MediaPlayer mediaPlayer;
    private String path;
    private TextView textView;
    private int timeSencond = 0;
    private int videoClarity = 0;
    private ImageView mPlayImage;
    private ImageView mPlaySelectImage;
    private ImageView mPlayunSelectImage;
    private ImageView mPlayunImageSettings;

    private VideoSettingsPopuView mVideoSettings;
    String resultPath = "";


    private android.os.Handler handler = new android.os.Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 0:
                    ToastUtil.getLongToastByString(mContext, "开始压缩视频");
                    showProgressDialog("正在处理中，请稍后...");
                    break;

                case 1:
                    ToastUtil.getLongToastByString(mContext, "压缩完成");
                    dismissProgressDialog();
                    break;
                case 2:
                    dismissProgressDialog();
                    ToastUtil.getLongToastByString(mContext, "压缩失败");

                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;


            }

        }

    };


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            timeSencond++;
            textView.setText(Util.stringForTime(timeSencond * 1000));
            handler.postDelayed(this, 1000);
            if (timeSencond >= Util.VIDEO_MAX_TIME) {
                if (mStartedFlg) {
                    try {
                        handler.removeCallbacks(runnable);
                        mRecorder.stop();
                        mRecorder.reset();
                        mRecorder.release();
                        mRecorder = null;
//                        mBtnStartStop.setText("录像");
                        mPlayImage.setImageResource(R.mipmap.play);
                        mPlayImage.setVisibility(View.GONE);
                        mPlayunImageSettings.setVisibility(View.GONE);
                        findViewById(R.id.videoImageLayout).setVisibility(View.VISIBLE);
                        if (camera != null) {
                            camera.release();
                            camera = null;
                        }
                        playVideo(path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                mStartedFlg = false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_camera);
        addToolBar(false);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        videoClarity = mSharedPreferences.getInt("videoClarity", 0);
        mSurfaceview = (SurfaceView) findViewById(R.id.surfaceview);
        mPlayImage = (ImageView) findViewById(R.id.videoImage);
        mPlaySelectImage = (ImageView) findViewById(R.id.videoImageSelect);
        mPlayunSelectImage = (ImageView) findViewById(R.id.videoImageCancel);
        mPlayunImageSettings = (ImageView) findViewById(R.id.videoImageSetting);
        textView = (TextView) findViewById(R.id.text);
        mPlayunImageSettings.setOnClickListener(this);
        mPlaySelectImage.setOnClickListener(this);
        mPlayunSelectImage.setOnClickListener(this);
        mPlayImage.setOnClickListener(this);

        Dialog dialog = ZXDialogUtil.showYesNoDialog(this, "视频选择", "", "本地文件", "拍摄", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");//设置类型，选择视频 （mp4 是android支持的视频格式）
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPlayImage.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                mPlayunImageSettings.setVisibility(View.VISIBLE);
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialog.dismiss();
                CameraActivity.this.finish();
            }
        });
        dialog.show();

        SurfaceHolder holder = mSurfaceview.getHolder();
        holder.addCallback(this);
        // setType必须设置，要不出错.
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (resultPath != null && resultPath.length() != 0) {
            final String mInputStr = resultPath;
            compressVideo(mInputStr);
        } else {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 是否触发按键为back键
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.videoImage:
                if (!mStartedFlg) {
                    timeSencond = 0;
                    handler.postDelayed(runnable, 1000);
                    findViewById(R.id.defaultImageLayout).setVisibility(View.GONE);
                    if (mRecorder == null) {
                        mRecorder = new MediaRecorder();
                    }
                    camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                    if (camera != null) {
                        camera.setDisplayOrientation(90);
                        camera.unlock();
                        mRecorder.setCamera(camera);
                    }
                    try {
                        // 这两项需要放在setOutputFormat之前
//                        mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
//                        mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//                        // Set output file format
//                        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//                        // 这两项需要放在setOutputFormat之后
//                        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//                        mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);

                        switch (videoClarity) {
                            case 0:
                                CamcorderProfile camcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
                                mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                                mRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
                                mRecorder.setProfile(camcorderProfile);
                                mRecorder.setVideoSize(640, 480);
                                mRecorder.setVideoFrameRate(30);
                                mRecorder.setVideoEncodingBitRate(3 * 1024 * 1024);


//                                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置声源
//                                mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);//设置视频源
//                                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);//设置音频输出格式为3gp  DEFAULT THREE_GPP
//                                mRecorder.setVideoFrameRate(100);//每秒3帧
//                                mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);//录制视频编码 264
//                                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);//设置音频编码为amr_nb   AMR_NB DEFAULT AAC
//                                mRecorder.setVideoSize(640, 480);//设置录制视频尺寸     mWidth   mHeight
//                                mRecorder.setVideoEncodingBitRate(5 * 1024 * 1024 );

                                break;
                            case 1:
                                camcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_720P);
                                mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                                mRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
                                mRecorder.setProfile(camcorderProfile);
                                mRecorder.setVideoSize(1280, 720);
                                mRecorder.setVideoFrameRate(30);
                                mRecorder.setVideoEncodingBitRate(4 * 1024 * 1024);
//
//                                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置声源
//                                mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);//设置视频源
//                                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);//设置音频输出格式为3gp  DEFAULT THREE_GPP
//                                mRecorder.setVideoFrameRate(30);//每秒3帧
//                                mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);//录制视频编码 264
//                                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);//设置音频编码为amr_nb   AMR_NB DEFAULT AAC
//                                mRecorder.setVideoSize(1280, 702);//设置录制视频尺寸     mWidth   mHeight
//                                mRecorder.setVideoEncodingBitRate(4 * 1024 * 1024 );

                                break;
                            case 2:
                                camcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_1080P);
                                mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                                mRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
                                mRecorder.setProfile(camcorderProfile);
                                mRecorder.setVideoSize(1920, 1080);
                                mRecorder.setVideoFrameRate(30);
                                mRecorder.setVideoEncodingBitRate(5 * 1024 * 1024);

                                break;
                        }

                        mRecorder.setOrientationHint(90);
                        //设置记录会话的最大持续时间（毫秒）
                        mRecorder.setMaxDuration(30 * 1000);
                        mRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
                        path = Util.getSDPath();
                        if (path != null) {
                            File dir = new File(Util.Myvideopath);
//                            File dir = new File(path + "/recordtest");
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }
                            path = dir + "/" + Util.getDate() + ".mp4";
                            mRecorder.setOutputFile(path);
                            mRecorder.prepare();
                            mRecorder.start();
                            mStartedFlg = true;
                            mPlayImage.setImageResource(R.mipmap.pause);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //stop
                    if (mStartedFlg) {
                        try {
                            handler.removeCallbacks(runnable);
                            mRecorder.stop();
                            mRecorder.reset();
                            mRecorder.release();
                            mRecorder = null;
                            mPlayImage.setImageResource(R.mipmap.play);
                            mPlayImage.setVisibility(View.GONE);
                            mPlayunImageSettings.setVisibility(View.GONE);
                            findViewById(R.id.videoImageLayout).setVisibility(View.VISIBLE);
                            if (camera != null) {
                                camera.release();
                                camera = null;
                            }
                            playVideo(path);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mStartedFlg = false;
                }
                break;
            case R.id.videoImageSelect:
                compressVideo(path);
                break;

            case R.id.videoImageCancel:
                mPlayImage.setVisibility(View.VISIBLE);
                mPlayunImageSettings.setVisibility(View.VISIBLE);
                findViewById(R.id.videoImageLayout).setVisibility(View.GONE);
                if (mIsPlay) {
                    mIsPlay = false;
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                }

                break;

            case R.id.videoImageSetting:
                List<String> list = new ArrayList<>();
                list.add("普清");
                list.add("高清");
                list.add("超清");

                showSelectWindow(findViewById(R.id.defaultImageLayout), list);
                break;
        }
    }

    //打开选择框
    private boolean showSelectWindow(View view, List<String> list) {
        if (mVideoSettings == null) {
            mVideoSettings = new VideoSettingsPopuView(this);
            mVideoSettings.setDataSelectListener(selectListener);
        }
        return mVideoSettings.show(view, view.getWidth(), list, 0);

    }

    VideoSetttingsListener.IOnInfoSelectListener selectListener = new VideoSetttingsListener.IOnInfoSelectListener() {
        @Override
        public void onSelect(int pos, View view, int index) {
            if (index == 0) {
                if (pos != videoClarity) {
                    videoClarity = pos;
                    SharedPreferences.Editor edit = mSharedPreferences.edit();
                    edit.putInt("videoClarity", pos);
                    edit.commit();
                }
            }
        }
    };

    private void playVideo(String path) {
        mIsPlay = true;
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.reset();
        if (path == null || path.length() == 0) {
            ToastUtil.getLongToastByString(CameraActivity.this, "no file is found!");
        }

        Uri uri = Uri.parse(path);
        mediaPlayer = MediaPlayer.create(CameraActivity.this, uri);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDisplay(mSurfaceHolder);
        try {
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if (uri == null) {
                finish();
                return;
            }
            resultPath = uri.getPath();
            if (resultPath == null || resultPath.length() == 0) {
                finish();
                return;
            }

            if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                Toast.makeText(this, uri.getPath(), Toast.LENGTH_SHORT).show();
                resultPath = uri.getPath();
            } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                resultPath = getPath(this, uri);
                Toast.makeText(this, resultPath, Toast.LENGTH_SHORT).show();
            } else {//4.4一下系统调用方法
                Toast.makeText(CameraActivity.this, getRealPathFromURI(uri), Toast.LENGTH_SHORT).show();
                resultPath = getRealPathFromURI(uri);
            }

            if (checkVedioTime(resultPath)) {
                //should clip to 30S
                String outFileName = Util.getDate() + "_clip.mp4";
                ClipUtil clipUtil = new ClipUtil(this);
                clipUtil.setFilePath(resultPath);
                clipUtil.setWorkingPath(Util.Myvideopath);
                clipUtil.setOutName(outFileName);
                clipUtil.setStartTime(0);
                clipUtil.setEndTime(30.0 * 1000);
                clipUtil.clip();
                resultPath = Util.Myvideopath + outFileName;
            } else {
            }
        } else {
            finish();
        }
    }

    public static String getRingDuring(String mUri) {
        String duration = null;
        android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();

        try {
            if (mUri != null) {
//                HashMap<String, String> headers = null;
//                if (headers == null) {
//                    headers = new HashMap<String, String>();
//                    headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
//                }
//                mmr.setDataSource(mUri, headers);
                mmr.setDataSource(mUri);
            }

            duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);
        } catch (Exception ex) {
        } finally {
            mmr.release();
        }
        return duration;
    }

    private boolean checkVedioTime(String path) {
        int duration = Integer.valueOf(getRingDuring(path)) / 1000;

        return duration > Util.VIDEO_MAX_TIME ? true : false;
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            ;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    private void compressVideo(final String mInputStr) {
        new Thread() {
            @Override
            public void run() {//对视频进行压缩
                handler.sendEmptyMessage(0);
                VideoCompressor.compress(CameraActivity.this, mInputStr, new VideoCompressListener() {
                    @Override
                    public void onSuccess(final String outputFile, String filename, long duration) {
                        Worker.postMain(new Runnable() {
                            @Override
                            public void run() {
                                handler.sendEmptyMessage(1);
                                SGLog.e("video compress success:" + outputFile);

                                int ret = Util.CopySdcardFile(outputFile, mInputStr);
                                if (ret == 0) {
                                    File file = new File(outputFile);
                                    file.delete();
                                    Intent intent = CameraActivity.this.getIntent();
                                    intent.putExtra("path", mInputStr);
                                    setResult(RESULT_OK, intent);
                                    CameraActivity.this.finish();
                                } else {
                                    SGLog.e("copy file is fail:");
                                }
                            }
                        });
                    }

                    @Override
                    public void onFail(final String reason) {
                        Worker.postMain(new Runnable() {
                            @Override
                            public void run() {
                                handler.sendEmptyMessage(2);
                                SGLog.e("video compress failed:" + reason);
                                Intent intent = CameraActivity.this.getIntent();
                                intent.putExtra("path", "");
                                setResult(RESULT_OK, intent);
                                CameraActivity.this.finish();
                            }
                        });
                    }

                    @Override
                    public void onProgress(final int progress) {
                        Worker.postMain(new Runnable() {
                            @Override
                            public void run() {
                                SGLog.e("video compress progress:" + progress);
                            }
                        });
                    }
                });

            }
        }.start();
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mSurfaceHolder = surfaceHolder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        // 将holder，这个holder为开始在onCreate里面取得的holder，将它赋给mSurfaceHolder
        mSurfaceHolder = surfaceHolder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mSurfaceview = null;
        mSurfaceHolder = null;
        handler.removeCallbacks(runnable);
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
            Log.d(TAG, "surfaceDestroyed release mRecorder");
        }
        if (camera != null) {
            camera.release();
            camera = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
