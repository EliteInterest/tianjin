package com.zx.tjmarketmobile.ui.camera;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.SGLog;
import com.zx.tjmarketmobile.util.ToastUtil;
import com.zx.tjmarketmobile.util.Util;
import com.zx.tjmarketmobile.util.Worker;
import com.zx.tjmarketmobile.video.VideoCompressor;

import java.io.File;
import java.io.IOException;

@SuppressLint("Registered")
public class CameraActivity extends BaseActivity implements SurfaceHolder.Callback, View.OnClickListener {
    private static final String TAG = "CameraActivity";
    private Context mContext = null;
    private SurfaceView mSurfaceview;
    private Button mBtnStartStop;
    private Button mBtnPlay;
    private Button mBtnSelect;
    private boolean mStartedFlg = false;//是否正在录像
    private boolean mIsPlay = false;//是否正在播放录像
    private MediaRecorder mRecorder;
    private SurfaceHolder mSurfaceHolder;
    private ImageView mImageView;
    private Camera camera;
    private MediaPlayer mediaPlayer;
    private String path;
    private TextView textView;
    private int text = 0;
    private int fromCode;

    private android.os.Handler handler = new android.os.Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            text++;
//            textView.setText(Util.stringForTime(text));
            handler.postDelayed(this, 1000);
            if (text >= Util.VIDEO_MAX_TIME) {
                if (mStartedFlg) {
                    try {
                        handler.removeCallbacks(runnable);
                        mRecorder.stop();
                        mRecorder.reset();
                        mRecorder.release();
                        mRecorder = null;
                        mBtnStartStop.setText("录像");
                        if (camera != null) {
                            camera.release();
                            camera = null;
                        }

                        compressVideo(path);
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
        mSurfaceview = (SurfaceView) findViewById(R.id.surfaceview);
        mImageView = (ImageView) findViewById(R.id.imageview);
        mBtnStartStop = (Button) findViewById(R.id.btnStartStop);
        mBtnPlay = (Button) findViewById(R.id.btnPlayVideo);
        mBtnSelect = (Button) findViewById(R.id.btnSelectVideo);
        textView = (TextView) findViewById(R.id.text);
        mBtnStartStop.setOnClickListener(this);
        mBtnPlay.setOnClickListener(this);
        mBtnSelect.setOnClickListener(this);

//        findViewById(R.id.btnDial).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "526"));//跳转到拨号界面，同时传递电话号码
//                startActivity(dialIntent);
//
//                Intent intent = new Intent("com.zx.tjmarketmobile.broadcast.phonestate.start");
//                mContext.sendBroadcast(intent);
//            }
//        });
//        ZXDialogUtil.showYesNoDialog(this, "视频选择", "", "拍摄", "本地文件", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        }, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        }).show();

        SurfaceHolder holder = mSurfaceview.getHolder();
        holder.addCallback(this);
        // setType必须设置，要不出错.
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("wangwansheng", "onResume...");
        if (!mStartedFlg) {
            mImageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 是否触发按键为back键
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            // 实例化 Bundle，设置需要传递的参数
            Intent intent = this.getIntent();
            intent.putExtra("path", path);
            setResult(RESULT_OK, intent);
            this.finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.btnStartStop: {
                if (mIsPlay) {
                    if (mediaPlayer != null) {
                        mIsPlay = false;
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
                if (!mStartedFlg) {
                    text = 0;
                    handler.postDelayed(runnable, 1000);
                    mImageView.setVisibility(View.GONE);
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
                        mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                        mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

                        // Set output file format
                        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

                        // 这两项需要放在setOutputFormat之后
                        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);

                        mRecorder.setVideoSize(640, 480);
                        mRecorder.setVideoFrameRate(30);
                        mRecorder.setVideoEncodingBitRate(3 * 1024 * 1024);
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
                            Log.i("wangwansheng", "path is " + path);
                            mRecorder.setOutputFile(path);
                            mRecorder.prepare();
                            mRecorder.start();
                            mStartedFlg = true;
                            mBtnStartStop.setText("停止");
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
                            mBtnStartStop.setText("录像");
                            if (camera != null) {
                                camera.release();
                                camera = null;
                            }

                            compressVideo(path);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mStartedFlg = false;
                }
            }
            break;
            case R.id.btnPlayVideo: {
                mIsPlay = true;
                mImageView.setVisibility(View.GONE);
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                }
                mediaPlayer.reset();
                if (path == null || path.length() == 0) {
                    ToastUtil.getLongToastByString(CameraActivity.this, "no file is found!");
                }

                Log.i("wangwansheng", "path is " + path);

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
            break;
            case R.id.btnSelectVideo: {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");//设置类型，选择视频 （mp4 是android支持的视频格式）
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String resultPath = "";
        Log.i("wangwansheng", "resultvode is " + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Toast.makeText(this, uri.getPath(), Toast.LENGTH_SHORT).show();
            Log.i("wangwansheng", "1resultvode is " + uri.getPath());
//            if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
//                Toast.makeText(this, uri.getPath(), Toast.LENGTH_SHORT).show();
//                Log.i("wangwansheng", "1resultvode is " + requestCode);
//                resultPath = uri.getPath();
//            } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
//                Log.i("wangwansheng", "22resultvode is " + requestCode);
//                resultPath = getPath(this, uri);
//                Toast.makeText(this, path.toString(), Toast.LENGTH_SHORT).show();
//            } else {//4.4一下系统调用方法
//                Toast.makeText(CameraActivity.this, getRealPathFromURI(uri), Toast.LENGTH_SHORT).show();
//                Log.i("wangwansheng", "333resultvode is " + requestCode);
//                resultPath = getRealPathFromURI(uri);
//            }
        }
//        if (checkVedioTime(resultPath)) {
//            //should clip to 30S
//            ClipUtil clipUtil = new ClipUtil(this);
//            clipUtil.setFilePath(resultPath);
//            clipUtil.setWorkingPath(Util.Myvideopath);
//            clipUtil.setOutName("clip.mp4");
//            clipUtil.setStartTime(0);
//            clipUtil.setEndTime(30.0);
//            clipUtil.clip();
//        }
    }

    private boolean checkVedioTime(String path) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int duration = mediaPlayer.getDuration();
        Log.i("wangwansheng", "duration is " + duration);
        mediaPlayer.release();

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

    private void compressVideo(String mInputStr) {
        //对视频进行压缩
        ToastUtil.getLongToastByString(mContext, "开始压缩视频");
        VideoCompressor.compress(this, mInputStr, new com.czm.videocompress.video.VideoCompressListener() {
            @Override
            public void onSuccess(final String outputFile, String filename, long duration) {
                Worker.postMain(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(mContext, "video compress success:" + outputFile, Toast.LENGTH_SHORT).show();
                        ToastUtil.getLongToastByString(mContext, "压缩完成");
                        SGLog.e("video compress success:" + outputFile);
                    }
                });
            }

            @Override
            public void onFail(final String reason) {
                Worker.postMain(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(mContext, "video compress failed:" + reason, Toast.LENGTH_SHORT).show();
                        SGLog.e("video compress failed:" + reason);
                        ToastUtil.getLongToastByString(mContext, "压缩失败");
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
