package com.zx.tjmarketmobile.view;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.core.geometry.SpatialReference;
import com.zx.tjmarketmobile.helper.GADBHelper;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Map;

import static com.iflytek.cloud.VerifierResult.TAG;

/**
 * Created by xb on 2016/8/3.
 * 本地地图缓存
 */
public class ArcGISLocalDataLayer extends ArcGISTiledMapServiceLayer {

    private String mUrl = null;
    private Context mContext;
    private SQLiteDatabase mDb;
    private int dpi = 96;
    private int tileWidth = 256;
    private int tileHeight = 256;
    private SpatialReference mSpatialReference;

    public ArcGISLocalDataLayer(Context mContext, String url) {
        super(url);
        this.mContext = mContext;
        this.mUrl = url;
        this.init();
    }

    private static final double[] SCALES = {2.958293554545656E8,
            1.479146777272828E8, 7.39573388636414E7, 3.69786694318207E7,
            1.848933471591035E7, 9244667.357955175, 4622333.678977588,
            2311166.839488794, 1155583.419744397, 577791.7098721985,
            288895.85493609926, 144447.92746804963, 72223.96373402482,
            36111.98186701241, 18055.990933506204, 9027.995466753102,
            4513.997733376551, 2256.998866688275};


    private static final double[] RESOLUTIONS_2000 = {0.7031249999891485,
            0.35156249999999994, 0.17578124999999997, 0.08789062500000014,
            0.04394531250000007, 0.021972656250000007, 0.01098632812500002,
            0.00549316406250001, 0.0027465820312500017, 0.0013732910156250009,
            0.000686645507812499, 0.0003433227539062495,
            0.00017166137695312503, 0.00008583068847656251,
            0.000042915344238281406, 0.000021457672119140645,
            0.000010728836059570307, 0.000005364418029785169};

    private void init() {
        GADBHelper gadbHelper = new GADBHelper(mContext, GADBHelper.TABLE_NAME);
        mDb = gadbHelper.getWritableDatabase();
        setDefaultSpatialReference(SpatialReference.create(4326));
        //设置mapservice
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArcGISLocalDataLayer.this.initLayer();
            }
        }).start();
    }

    @Override
    protected byte[] getTile(int level, int col, int row) throws Exception {
        byte[] result = null;
        String tileIndex = "tile_" + level + "_" + row + "_" + col;
        URL url = new URL(mUrl + "/" + tileIndex.replaceAll("_", "/"));
        String sql = "select * from " + GADBHelper.TABLE_NAME + " where TILEINDEX = '" + tileIndex + "'";
        Cursor mCursor = mDb.rawQuery(sql, null);
        Map<String, String> map = null;
        boolean hasData = false;
        while (mCursor.moveToNext()) {//判断是否存在数据
            hasData = true;
        }
        boolean isWifi = isWifi();
        if (hasData && !isWifi) {//数据库中有数据
            try {
                if (mCursor.moveToFirst()) {
                    result = mCursor.getBlob(mCursor.getColumnIndex("TILEDATA"));
                }
                mCursor.close();
            } catch (Exception e) {
                e.printStackTrace();
                result = com.esri.core.internal.io.handler.a.a(url.toString(), map);
            }
        } else {//数据库中没有数据
            result = com.esri.core.internal.io.handler.a.a(url.toString(), map);
        }
        return result;
    }


    @Override
    protected void requestTile(int level, int col, int row) {
        super.requestTile(level, col, row);
        String mTileIndex = "tile_" + level + "_" + row + "_" + col;
        String sql = "select * from " + GADBHelper.TABLE_NAME + " where TILEINDEX = '" + mTileIndex + "'";
        Cursor mCursor = mDb.rawQuery(sql, null);
        boolean hasData = false;
        while (mCursor.moveToNext()) {//判断是否存在数据
            hasData = true;
        }
        if (!hasData) {//数据库中有数据
            downloadData(mTileIndex);
        } else {
            setTileInfo(new com.esri.android.map.TiledServiceLayer.TileInfo(null, SCALES, RESOLUTIONS_2000, level, dpi, tileWidth, tileHeight));
        }

    }


    //在线加载
    private byte[] downloadData(String tileIndex) {
        byte[] result = null;
        try {
            URL url = new URL(mUrl + "/" + tileIndex.replaceAll("_", "/"));
            byte[] buf = new byte[1024];
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setConnectTimeout(3000);
            httpConnection.setReadTimeout(3000);
            // 设置是否使用缓存  默认是true
            httpConnection.setUseCaches(true);
            // 设置为Post请求
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();
            if (httpConnection.getResponseCode() == 200) {
                // 获取返回的数据
                BufferedInputStream is = new BufferedInputStream(httpConnection.getInputStream());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int temp = -1;
                while ((temp = is.read(buf)) > 0) {
                    bos.write(buf, 0, temp);
                }
                result = bos.toByteArray();
                is.close();
                insertOrUpdateToDb(tileIndex, result);
                Log.e(TAG, "Get方式请求成功，result--->" + result);
            } else {
                Log.e(TAG, "Get方式请求失败");
            }
            httpConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean isWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    //添加数据
    private void insertOrUpdateToDb(String tileIndex, byte[] result) {
        ContentValues values = new ContentValues();
        values.put("TILEINDEX", tileIndex);
        values.put("TILEDATA", result);
        values.put("TILETIME", System.currentTimeMillis() + "");

        //首选判断数据库表中文件大小时候超过四百兆，如果超过，就进行一次删除
        File file = new File("/data/data/com.zx.gamarketmobile/databases/" + GADBHelper.TABLE_NAME);
        Cursor cs = mDb.rawQuery("select * from " + GADBHelper.TABLE_NAME, null);
        int count = cs.getCount();
        cs.close();
        if (getFileSize(file) > 300) {//当缓存大于300兆时,删除数据库中前百分之三十的数据
            int deleteLimit = (int) (count * 0.3);
            String sqlDelete = "delete from " + GADBHelper.TABLE_NAME + " where TILEINDEX in ( select TILEINDEX from " + GADBHelper.TABLE_NAME + " order by TILETIME desc limit 0," + deleteLimit + " )";
            mDb.execSQL(sqlDelete);
        }

        String sql = "select * from " + GADBHelper.TABLE_NAME + " where TILEINDEX = '" + tileIndex + "'";
        Cursor cursor = mDb.rawQuery(sql, null);
        boolean hasData = false;
        while (cursor.moveToNext()) {
            hasData = true;
        }
        cursor.close();
        if (hasData) {
            mDb.update(GADBHelper.TABLE_NAME, values, "TILEINDEX = ?", new String[]{tileIndex});
        } else {
            mDb.insert(GADBHelper.TABLE_NAME, "value", values);
        }
    }

    //获取文件大小
    private double getFileSize(File file) {
        DecimalFormat df = new DecimalFormat("#.00");
        double size = 0;
        try {
            if (file.exists()) {
                FileInputStream fIs = new FileInputStream(file);
                size = Double.valueOf(df.format((double) fIs.available() / 1048576));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
}
