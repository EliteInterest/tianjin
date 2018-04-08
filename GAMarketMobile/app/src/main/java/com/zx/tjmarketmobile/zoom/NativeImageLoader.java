package com.zx.tjmarketmobile.zoom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.util.SYSUtil;
import com.zx.tjmarketmobile.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 本地图片加载器,采用的是异步解析本地图片，单例模式利用getInstance()获取NativeImageLoader实例
 * 调用loadNativeImage()方法加载本地图片，此类可作为一个加载本地图片的工具类
 */
public class NativeImageLoader {
    private static final String TAG = NativeImageLoader.class.getSimpleName();
    private static NativeImageLoader mInstance = new NativeImageLoader();
    public static LruCache<String, Bitmap> mMemoryCache;
    //	private static HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<>();
    private ExecutorService mImageThreadPool = Executors.newFixedThreadPool(1);
    private Bitmap bitmap = null;
    private int DEFAULT_SIZE = 100;

    private NativeImageLoader() {
        // 获取应用程序的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory());

        // 用最大内存的1/8来存储图片
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {

            // 获取每张图片的bytes
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }

        };
    }

    /**
     * 通过此方法来获取NativeImageLoader的实例
     *
     * @return
     */
    public static NativeImageLoader getInstance() {
        return mInstance;
    }

    /**
     * 加载本地图片，对图片不进行裁剪
     *
     * @param path
     * @param mCallBack
     * @return
     */
    public Bitmap loadNativeImage(final String path, final NativeImageCallBack mCallBack) {
        return this.loadNativeImage(path, null, mCallBack);
    }

    /**
     * 此方法来加载本地图片，这里的mPoint是用来封装ImageView的宽和高，我们会根据ImageView控件的大小来裁剪Bitmap
     * 如果你不想裁剪图片，调用loadNativeImage(final String path, final NativeImageCallBack
     * mCallBack)来加载
     *
     * @param path
     * @param mPoint
     * @param mCallBack
     * @return
     */
    public Bitmap loadNativeImage(final String path, final Point mPoint, final NativeImageCallBack mCallBack) {
        // 先获取内存中的Bitmap
        Bitmap bitmap = getBitmapFromMemCache(path);
        final Handler mHander = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mCallBack.onImageLoader((Bitmap) msg.obj, path);
            }

        };

        // 若该Bitmap不在内存缓存中，则启用线程去加载本地的图片，并将Bitmap加入到mMemoryCache中
        if (bitmap == null) {
            mImageThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    // 先获取图片的缩略图
                    Bitmap mBitmap = null;
                    try {
                        mBitmap = decodeThumbBitmapForFile(path, mPoint == null ? DEFAULT_SIZE : mPoint.x, mPoint == null ? DEFAULT_SIZE : mPoint.y);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Message msg = mHander.obtainMessage();
                    msg.obj = mBitmap;
                    mHander.sendMessage(msg);
                    // 将图片加入到内存缓存
                    addBitmapToMemoryCache(path, mBitmap);
                }
            });
        }
        return bitmap;

    }

    /**
     * 此方法来加载本地图片，我们会根据ImageView控件的大小来裁剪Bitmap
     *
     * @param path
     * @return
     */
    public Bitmap loadNativeImage(final Context context, final String path, final ImageView view) {
        final Handler mHander = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                view.setImageBitmap(mMemoryCache.get(path));
            }

        };
        if (path.contains("http")) {
            String name = path.substring(path.lastIndexOf("/") + 1, path.length());
            final String storagePath = SYSUtil.CACHE_DIR_NAME + name;
            File cacheFile = new File(SYSUtil.CACHE_DIR_NAME);
            if (!cacheFile.exists()) {
                cacheFile.mkdirs();
            }
            bitmap = getBitmapFromMemCache(storagePath);
            // 若该Bitmap不在内存缓存中，则启用线程去加载本地的图片，并将Bitmap加入到mMemoryCache中
            if (bitmap != null) {
                view.setImageBitmap(bitmap);
                return bitmap;
            }
            if (bitmap == null) {
                view.setTag(storagePath);
                Picasso.with(context).load(path).into(view, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bmp = ((BitmapDrawable) view.getDrawable()).getBitmap();
                        if (bmp != null) {
                            bitmap = bmp;
                            Bitmap zoomBitmap = SYSUtil.imageZoom(bmp);
                            addBitmapToMemoryCache((String) view.getTag(), zoomBitmap);
                        } else {
                            bmp = ((BitmapDrawable) ContextCompat.getDrawable(context, R.mipmap.pictures_no)).getBitmap();
                            bitmap = bmp;
                        }
                    }

                    @Override
                    public void onError() {
                        bitmap = ((BitmapDrawable) ContextCompat.getDrawable(context,R.mipmap.pictures_no)).getBitmap();
                    }
                });
            }
        } else {
            // 先获取内存中的Bitmap
            bitmap = getBitmapFromMemCache(path);
            // 若该Bitmap不在内存缓存中，则启用线程去加载本地的图片，并将Bitmap加入到mMemoryCache中
            if (bitmap != null) {
                view.setImageBitmap(bitmap);
                return bitmap;
            }
            if (bitmap == null) {
                mImageThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        // 先获取图片的缩略图
                        Bitmap mBitmap = null;
                        try {
                            mBitmap = decodeThumbBitmapForFile(path, DEFAULT_SIZE, DEFAULT_SIZE);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        if (mBitmap != null) {
                            // 将图片加入到内存缓存
                            addBitmapToMemoryCache(path, mBitmap);
                            Message msg = mHander.obtainMessage();
                            msg.obj = mBitmap;
                            mHander.sendMessage(msg);
                        } else {
                            bitmap = ((BitmapDrawable) ContextCompat.getDrawable(context,R.mipmap.pictures_no)).getBitmap();
                        }
                    }
                });
            }
        }
        return bitmap;

    }

    /**
     * 往内存缓存中添加Bitmap
     *
     * @param key
     * @param bitmap
     */
    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null && bitmap != null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * 根据key来获取内存中的图片
     *
     * @param key
     * @return
     */
    public static Bitmap getBitmapFromMemCache(String key) {
        Bitmap bitmap = null;
        if (mMemoryCache != null) {
            bitmap = mMemoryCache.get(key);
            if (bitmap != null) {
                // Log.i(TAG, "get image for LRUCache , path = " + key);
            }
        }
        return bitmap;
    }

    /**
     * 清除LruCache中的bitmap
     */
    public void trimMemCache() {
        mMemoryCache.evictAll();
    }

    /**
     * 根据View(主要是ImageView)的宽和高来获取图片的缩略图
     *
     * @param path
     * @param viewWidth
     * @param viewHeight
     * @return
     */
    private Bitmap decodeThumbBitmapForFile(String path, int viewWidth, int viewHeight) throws FileNotFoundException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 设置为true,表示解析Bitmap对象，该对象不占内存
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        // 设置缩放比例
        options.inSampleSize = computeScale(options, viewWidth, viewHeight);
        // 设置为false,解析Bitmap对象加入到内存中
        options.inJustDecodeBounds = false;

        return transformeFile(path, options);
    }


    public static Bitmap transformeFile(String path, BitmapFactory.Options options) {
        Bitmap transformed = null;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        int rotation = Util.getExifOrientation(path);
        Matrix m = new Matrix();
        m.setRotate(rotation);
        //转换图片方向
        if (bitmap != null) {
            transformed = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
        }
        return transformed;
    }


    /**
     * 根据View(主要是ImageView)的宽和高来计算Bitmap缩放比例。默认不缩放
     *
     * @param options
     * @paramwidth
     * @paramheight
     */
    private int computeScale(BitmapFactory.Options options, int viewWidth, int viewHeight) {
        int inSampleSize = 1;
        if (viewWidth == 0 || viewWidth == 0) {
            return inSampleSize;
        }
        int bitmapWidth = options.outWidth;
        int bitmapHeight = options.outHeight;

        // 假如Bitmap的宽度或高度大于我们设定图片的View的宽高，则计算缩放比例
        if (bitmapWidth > viewWidth || bitmapHeight > viewWidth) {
            int widthScale = Math.round((float) bitmapWidth / (float) viewWidth);
            int heightScale = Math.round((float) bitmapHeight / (float) viewWidth);

            // 为了保证图片不缩放变形，我们取宽高比例最小的那个
            inSampleSize = widthScale < heightScale ? widthScale : heightScale;
        }
        return inSampleSize;
    }

    /**
     * 加载本地图片的回调接口
     */
    public interface NativeImageCallBack {
        /**
         * 当子线程加载完了本地的图片，将Bitmap和图片路径回调在此方法中
         *
         * @param bitmap
         * @param path
         */
        public void onImageLoader(Bitmap bitmap, String path);
    }

    public void setDefaultSize(int size) {
        DEFAULT_SIZE = size;
    }

    /**
     * 根据key来获取内存中的图片
     *
     * @param key
     * @return
     */
    public void deleteBitmapFromMemCache(String key) {
        Bitmap bitmap = mMemoryCache.get(key);

        if (bitmap != null) {
            mMemoryCache.remove(key);
        }
    }
}
