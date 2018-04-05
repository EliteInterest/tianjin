package com.zx.tjmarketmobile.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Environment;
import android.text.TextUtils;

public class ImageUtils {
	
	public static final String FILEPATH = Environment.getExternalStorageDirectory().getPath() + "/androuter/";
	public static final String PACKAGEPATH = "/data/data/com.chinamobile.iot.smarthome/images/";

	// 获取图片目录
	public static String getBitmapPath() {
		String path = "";
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			path = FILEPATH + "images/";
		} else {
			path = PACKAGEPATH;
		}
		return path;
	}

	/**
	 * @param imageUrl
	 *            图片url或者路径.用于获取图片名称
	 * @param tag
	 *            图片目录
	 * @return
	 */
	public static Bitmap getBitmap(String imageUrl, String tag) {
		String name = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
		String path = getBitmapPath() + tag;
		Bitmap bitmap = ImageUtils.getSimplifyBitmap(path + name);
		return bitmap;
	}

	/**
	 * 旋转图片为正方向
	 * 
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap, int angle) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	// 压缩图片，默认尺寸为720x1280
	public static Bitmap getSimplifyBitmap(String path) {
		getSimplifyBitmap(path, 720, 1280);
		return getSimplifyBitmap(path, 720, 1280);
	}

	// 根据指定尺寸压缩图片
	public static Bitmap getSimplifyBitmap(String path, int newWidth, int newHeight) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path, newOpts);

		// 设置想要的大小
		int width = newOpts.outWidth;
		int height = newOpts.outHeight;

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		float scaleSize = Math.min(scaleWidth, scaleHeight);
		if (scaleSize > 1)
			scaleSize = 1;
		newOpts.inSampleSize = (int) (1 / scaleSize);
		newOpts.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(path, newOpts);
		if (null == bitmap) {
			return null;
		}
		// 重新计算压缩比例
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		scaleWidth = ((float) newWidth) / width;
		scaleHeight = ((float) newHeight) / height;
		scaleSize = Math.min(scaleWidth, scaleHeight);
		if (scaleSize > 1) {
			scaleSize = 1;
		}
		Matrix matrix = new Matrix();
		matrix.postScale(scaleSize, scaleSize);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		return bitmap;
	}

	/**
	 * 功能：压缩图片
	 * 
	 * @param originalFile
	 *            图片源文件
	 * @param path
	 *            压缩之后图片的存储路径
	 * @param name
	 *            压缩之后图片的名称
	 * @param size
	 *            压缩之后图片的大小,如果size不为0则图片尺寸为160 x 160
	 * @return
	 */
	public static Bitmap bitmapCompress(File originalFile, String path, String name, int size) {
		String originalPath = originalFile.getParent();
		if (null != originalPath) {
			originalPath += File.separator;
		} else {
			originalPath = "";
		}
		Bitmap bitmap = null;
		if (size != 0) {
			bitmap = getSimplifyBitmap(originalPath + originalFile.getName(), 160, 160);
		} else {
			bitmap = getSimplifyBitmap(originalPath + originalFile.getName());
		}
		if (null == bitmap) {
			return null;
		}
		int degree = readPictureDegree(path + name);
		bitmap = rotateBitmap(bitmap, degree);
		bitmap = bitmapOutput(bitmap, path, name, size);
		return bitmap;
	}

	public static Bitmap bitmapOutput(Bitmap bitmap, String path, String name, int size) {
		if (TextUtils.isEmpty(path)) {
			path = ImageUtils.getBitmapPath() + "thumbnail/";
		}
		if (TextUtils.isEmpty(name)) {
			name = bitmap.toString();
		}
		try {
			File pathFile = new File(path);
			File file = new File(path, name);
			if (file.exists()) {
				file.delete();
			}
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			file.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(file);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			int options = 100;
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, os);
			byte[] bytes = os.toByteArray();
			if (size != 0) {
				while (os.toByteArray().length / 1024 > size) {
					os.reset();
					options -= 10;
					bitmap.compress(Bitmap.CompressFormat.JPEG, options, os);
					bytes = os.toByteArray();
				}
			}
			fileOut.write(bytes);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public static Bitmap drawableToBitmap(Drawable d) {
		int w = d.getIntrinsicWidth();
		int h = d.getIntrinsicWidth();
		Config config = d.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		Canvas c = new Canvas(bitmap);
		d.setBounds(0, 0, w, h);
		d.draw(c);
		return bitmap;
	}

	/**
	 * 下载图片操作
	 * 
	 * @param context
	 * @param imageUrl
	 *            －－ 图片url地址
	 */
	/*
	 * public static void downloadImage(Context context, Bitmap bitmap, String
	 * imageUrl) { String path = getCameraPath(imageUrl); File file = new
	 * File(path); if (!file.exists()) { if (bitmap != null) { try {
	 * bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new
	 * FileOutputStream(file)); } catch (FileNotFoundException e) {
	 * e.printStackTrace(); Toast.makeText(context,
	 * R.string.uc_image_download_failure, Toast.LENGTH_SHORT).showMap(); return; }
	 * // 发送数据装载广播让保存的图片显示到相册 context.sendBroadcast(new
	 * Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" +
	 * Environment.getExternalStorageDirectory()))); } else {
	 * Toast.makeText(context, R.string.uc_image_not_exist,
	 * Toast.LENGTH_SHORT).showMap(); return; } } Toast.makeText(context,
	 * R.string.uc_image_download_success, Toast.LENGTH_SHORT).showMap(); }
	 */

	// public static void downloadImage(final Context context, Bitmap bitmap,
	// String imageUrl) {
	//
	// if(!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
	// {
	// Toast.makeText(context, "无SDcard", Toast.LENGTH_SHORT).show();
	// return;
	// }
	//
	// String path = getCameraPath(imageUrl);
	// final File file = new File(path);
	//
	// if(!file.exists()) {
	// DisplayImageOptions options = new DisplayImageOptions.Builder()
	// .cacheInMemory()
	// .cacheOnDisc()
	// .build();
	// ImageLoader.getInstance().loadImage(imageUrl, options, new
	// SimpleImageLoadingListener(){
	//
	// public void onLoadingComplete(String imageUri, View view, Bitmap
	// loadedImage) {
	// if(loadedImage != null) {
	// try {
	// loadedImage.compress(Bitmap.CompressFormat.JPEG, 100, new
	// FileOutputStream(file));
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// Toast.makeText(context, R.string.uc_image_download_failure,
	// Toast.LENGTH_SHORT).show();
	// return;
	// }
	// // 发送数据装载广播让保存的图片显示到相册
	// context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
	// Uri.parse("file://" + Environment.getExternalStorageDirectory())));
	// Toast.makeText(context, R.string.uc_image_download_success,
	// Toast.LENGTH_SHORT).show();
	// } else {
	// Toast.makeText(context, R.string.uc_image_download_failure,
	// Toast.LENGTH_SHORT).show();
	// }
	// }
	//
	//
	// public void onLoadingFailed(String imageUri, View view, FailReason
	// failReason) {
	// Toast.makeText(context, R.string.uc_image_download_failure,
	// Toast.LENGTH_SHORT).show();
	// }
	// });
	//
	// } else {
	// Toast.makeText(context, R.string.uc_image_download_success,
	// Toast.LENGTH_SHORT).show();
	// }
	//
	//
	// }

	/**
	 * 获取有效显示路径
	 * 
	 * @param photo
	 * @return
	 */
	/*
	 * public static String getValidlyPath(CampusStylePhotoObject photo) { if
	 * (!TextUtils.isEmpty(photo.image1Path)) { return photo.image1Path; } else
	 * if (!TextUtils.isEmpty(photo.image2Path)) { return photo.image2Path; }
	 * else if (!TextUtils.isEmpty(photo.image3Path)) { return photo.image3Path;
	 * } else if (!TextUtils.isEmpty(photo.image4Path)) { return
	 * photo.image4Path; } else if (!TextUtils.isEmpty(photo.image5Path)) {
	 * return photo.image5Path; } return ""; }
	 */
	/**
	 * 图片规格 -100*
	 */
	public static final int TYPE_IMAGE_100XX = 1;
	/**
	 * 图片规格 -300*
	 */
	public static final int TYPE_IMAGE_300XX = 2;
	/**
	 * 图片规格 -720*
	 */
	public static final int TYPE_IMAGE_720XX = 3;
	/**
	 * 图片规格-原图
	 */
	public static final int TYPE_IMAGE_ORIGINAL = 4;

	/**
	 * 组装Image路径
	 * 
	 * @param imageUrl
	 * @param type
	 * @return
	 */
	public static String componentImageUrl(String imageUrl, int type) {

		if (TextUtils.isEmpty(imageUrl)) {
			return imageUrl;
		}

		int lastPositionSp = imageUrl.lastIndexOf("/") + 1;
		String name = imageUrl.substring(lastPositionSp);
		imageUrl = imageUrl.substring(0, lastPositionSp);
		switch (type) {
		case TYPE_IMAGE_100XX:
			imageUrl += "100xx" + File.separator;
			break;
		case TYPE_IMAGE_300XX:
			imageUrl += "300xx" + File.separator;
			break;
		case TYPE_IMAGE_720XX:
			imageUrl += "720xx" + File.separator;
			break;
		case TYPE_IMAGE_ORIGINAL:
			imageUrl += "original" + File.separator;
			break;
		}
		imageUrl += name;
		return imageUrl;
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	public static String getDirectPath() {
		return "/wificity/images/";
	}

	public static String getFileName(String imageUrl) {
		return String.valueOf(imageUrl.hashCode()) + ".png";
	}

	public static String getFilePath(String imageUrl) {
		return getDirectPath() + getFileName(imageUrl);
	}

	public static String getCameraPath(String imageUrl) {
		StringBuffer sb = new StringBuffer();
		sb.append(Environment.getExternalStorageDirectory().getPath());
		sb.append("/DCIM/Camera/");
		File file = new File(sb.toString());
		if (!file.exists()) {
			file.mkdirs();
		}
		sb.append(getFileName(imageUrl));
		return sb.toString();
	}

	/**
	 * 设置图片圆角
	 * 
	 * @param bitmap
	 *            数据源
	 * @param innerCorner
	 *            圆角显示的位置 8个圆角半径值 分别对就左上、右上、右下、左下四个点<br/>
	 *            float inner[] = new float[] {20, 20, //左上<br/>
	 *            0, 0,//右上<br/>
	 *            20,20,//右下<br/>
	 *            0,0 //左下<br/>
	 *            };<br/>
	 * @return
	 */
	public static Bitmap drawCorner(Bitmap bitmap, float[] innerCorner) {
		if (bitmap == null) {
			return null;
		}
		Bitmap output = null;
		try {
			output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		} catch (OutOfMemoryError e) {
			if (output != null && !output.isRecycled()) {
				output.recycle();
				output = null;
			}
			return null;
		}
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		Path path = new Path();
		path.addRoundRect(rectF, innerCorner, Direction.CW);
		canvas.drawPath(path, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}
}