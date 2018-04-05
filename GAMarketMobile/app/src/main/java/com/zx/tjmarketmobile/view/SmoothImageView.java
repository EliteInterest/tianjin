package com.zx.tjmarketmobile.view;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;


/**
 * 2d平滑变化的显示图片的ImageView
 * 仅限于用于:从一个ScaleType==CENTER_CROP的ImageView，切换到另一个ScaleType=
 * FIT_CENTER的ImageView，或者反之 (当然，得使用同样的图片最好)
 * 
 * @author Dean Tao
 * 
 */
public class SmoothImageView extends ImageView {

	private static final int STATE_NORMAL = 0;
	private static final int STATE_TRANSFORM_IN = 1;
	private static final int STATE_TRANSFORM_OUT = 2;
	private int mOriginalWidth;
	private int mOriginalHeight;
	private int mOriginalLocationX;
	private int mOriginalLocationY;
	private int mState = STATE_NORMAL;
	private Matrix mSmoothMatrix;
	private Bitmap mBitmap;
	private boolean mTransformStart = false;
	private Transfrom mTransfrom;
	private final int mBgColor = 0xFF000000;
	private int mBgAlpha = 0;
	private Paint mPaint;
	private GestureDetector mGestureDetector;
	/**  模板Matrix，用以初始化 */
	private Matrix mMatrix=new Matrix();
	/**  图片长度*/
	private float mImageWidth;
	/**  图片高度 */
	private float mImageHeight;
	
	public SmoothImageView(Context context) {
		super(context);
		init();
	}

	public SmoothImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SmoothImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {

		MatrixTouchListener mListener=new MatrixTouchListener();
		setOnTouchListener(mListener);
		mGestureDetector=new GestureDetector(getContext(), new GestureListener(mListener));
		mSmoothMatrix = new Matrix();

		mPaint=new Paint();
		mPaint.setColor(mBgColor);
		mPaint.setStyle(Style.FILL);
	}

	public void setOriginalInfo(int width, int height, int locationX, int locationY) {
		mOriginalWidth = width;
		mOriginalHeight = height;
		mOriginalLocationX = locationX;
		mOriginalLocationY = locationY;
		// 因为是屏幕坐标，所以要转换为该视图内的坐标，因为我所用的该视图是MATCH_PARENT，所以不用定位该视图的位置,
		// 如果不是的话，还需要定位视图的位置，然后计算mOriginalLocationX和mOriginalLocationY
		mOriginalLocationY = mOriginalLocationY - getStatusBarHeight(getContext());
	}

	/**
	 * 获取状态栏高度
	 * 
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		java.lang.reflect.Field field = null;
		int x = 0;
		int statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
			return statusBarHeight;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusBarHeight;
	}

	/**
	 * 用于开始进入的方法。 调用此方前，需已经调用过setOriginalInfo
	 */
	public void transformIn() {
		mState = STATE_TRANSFORM_IN;
		mTransformStart = true;
		invalidate();
	}

	/**
	 * 用于开始退出的方法。 调用此方前，需已经调用过setOriginalInfo
	 */
	public void transformOut() {
		mState = STATE_TRANSFORM_OUT;
		mTransformStart = true;
		invalidate();
	}

	private class Transfrom {
		float startScale;// 图片开始的缩放值
		float endScale;// 图片结束的缩放值
		float scale;// 属性ValueAnimator计算出来的值
		LocationSizeF startRect;// 开始的区域
		LocationSizeF endRect;// 结束的区域
		LocationSizeF rect;// 属性ValueAnimator计算出来的值

		void initStartIn() {
			scale = startScale;
			try {
				rect = (LocationSizeF) startRect.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}

		void initStartOut() {
			scale = endScale;
			try {
				rect = (LocationSizeF) endRect.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * 初始化进入的变量信息
	 */
	private void initTransform() {
		if (getDrawable() == null) {
			return;
		}
		if (mBitmap == null || mBitmap.isRecycled()) {
			mBitmap = ((BitmapDrawable) getDrawable()).getBitmap();
		}
		//防止mTransfrom重复的做同样的初始化
		if (mTransfrom != null) {
			return;
		}
		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}
		mTransfrom = new Transfrom();

		/** 下面为缩放的计算 */
		/* 计算初始的缩放值，初始值因为是CENTR_CROP效果，所以要保证图片的宽和高至少1个能匹配原始的宽和高，另1个大于 */
		float xSScale = mOriginalWidth / ((float) mBitmap.getWidth());
		float ySScale = mOriginalHeight / ((float) mBitmap.getHeight());
		float startScale = xSScale > ySScale ? xSScale : ySScale;
		mTransfrom.startScale = startScale;
		/* 计算结束时候的缩放值，结束值因为要达到FIT_CENTER效果，所以要保证图片的宽和高至少1个能匹配原始的宽和高，另1个小于 */
		float xEScale = getWidth() / ((float) mBitmap.getWidth());
		float yEScale = getHeight() / ((float) mBitmap.getHeight());
		float endScale = xEScale < yEScale ? xEScale : yEScale;
		mTransfrom.endScale = endScale;

		/**
		 * 下面计算Canvas Clip的范围，也就是图片的显示的范围，因为图片是慢慢变大，并且是等比例的，所以这个效果还需要裁减图片显示的区域
		 * ，而显示区域的变化范围是在原始CENTER_CROP效果的范围区域
		 * ，到最终的FIT_CENTER的范围之间的，区域我用LocationSizeF更好计算
		 * ，他就包括左上顶点坐标，和宽高，最后转为Canvas裁减的Rect.
		 */
		/* 开始区域 */
		mTransfrom.startRect = new LocationSizeF();
		mTransfrom.startRect.left = mOriginalLocationX;
		mTransfrom.startRect.top = mOriginalLocationY;
		mTransfrom.startRect.width = mOriginalWidth;
		mTransfrom.startRect.height = mOriginalHeight;
		/* 结束区域 */
		mTransfrom.endRect = new LocationSizeF();
		float bitmapEndWidth = mBitmap.getWidth() * mTransfrom.endScale;// 图片最终的宽度
		float bitmapEndHeight = mBitmap.getHeight() * mTransfrom.endScale;// 图片最终的宽度
		mTransfrom.endRect.left = (getWidth() - bitmapEndWidth) / 2;
		mTransfrom.endRect.top = (getHeight() - bitmapEndHeight) / 2;
		mTransfrom.endRect.width = bitmapEndWidth;
		mTransfrom.endRect.height = bitmapEndHeight;

		mTransfrom.rect = new LocationSizeF();
	}

	private class LocationSizeF implements Cloneable {
		float left;
		float top;
		float width;
		float height;
		@Override
		public String toString() {
			return "[left:"+left+" top:"+top+" width:"+width+" height:"+height+"]";
		}
		
		@Override
		public Object clone() throws CloneNotSupportedException {
			// TODO Auto-generated method stub
			return super.clone();
		}
		
	}

	/* 下面实现了CENTER_CROP的功能 的Matrix，在优化的过程中，已经不用了 */
	private void getCenterCropMatrix() {
		if (getDrawable() == null) {
			return;
		}
		if (mBitmap == null || mBitmap.isRecycled()) {
			mBitmap = ((BitmapDrawable) getDrawable()).getBitmap();
		}
		/* 下面实现了CENTER_CROP的功能 */
		float xScale = mOriginalWidth / ((float) mBitmap.getWidth());
		float yScale = mOriginalHeight / ((float) mBitmap.getHeight());
		float scale = xScale > yScale ? xScale : yScale;
		mSmoothMatrix.reset();
		mSmoothMatrix.setScale(scale, scale);
		mSmoothMatrix.postTranslate(-(scale * mBitmap.getWidth() / 2 - mOriginalWidth / 2), -(scale * mBitmap.getHeight() / 2 - mOriginalHeight / 2));
	}

	private void getBmpMatrix() {
		if (getDrawable() == null) {
			return;
		}
		if (mTransfrom == null) {
			return;
		}
		if (mBitmap == null || mBitmap.isRecycled()) {
			mBitmap = ((BitmapDrawable) getDrawable()).getBitmap();
		}
		/* 下面实现了CENTER_CROP的功能 */
		mSmoothMatrix.setScale(mTransfrom.scale, mTransfrom.scale);
		mSmoothMatrix.postTranslate(-(mTransfrom.scale * mBitmap.getWidth() / 2 - mTransfrom.rect.width / 2),
				-(mTransfrom.scale * mBitmap.getHeight() / 2 - mTransfrom.rect.height / 2));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (getDrawable() == null) {
			return; // couldn't resolve the URI
		}

		if (mState == STATE_TRANSFORM_IN || mState == STATE_TRANSFORM_OUT) {
			if (mTransformStart) {
				initTransform();
			}
			if (mTransfrom == null) {
				super.onDraw(canvas);
				return;
			}

			if (mTransformStart) {
				if (mState == STATE_TRANSFORM_IN) {
					mTransfrom.initStartIn();
				} else {
					mTransfrom.initStartOut();
				}
			}

			if(mTransformStart){
				Log.d("Dean", "mTransfrom.startScale:"+mTransfrom.startScale);
				Log.d("Dean", "mTransfrom.startScale:"+mTransfrom.endScale);
				Log.d("Dean", "mTransfrom.scale:"+mTransfrom.scale);
				Log.d("Dean", "mTransfrom.startRect:"+mTransfrom.startRect.toString());
				Log.d("Dean", "mTransfrom.endRect:"+mTransfrom.endRect.toString());
				Log.d("Dean", "mTransfrom.rect:"+mTransfrom.rect.toString());
			}
			
			mPaint.setAlpha(mBgAlpha);
			canvas.drawPaint(mPaint);
			
			int saveCount = canvas.getSaveCount();
			canvas.save();
			// 先得到图片在此刻的图像Matrix矩阵
			getBmpMatrix();
			canvas.translate(mTransfrom.rect.left, mTransfrom.rect.top);
			canvas.clipRect(0, 0, mTransfrom.rect.width, mTransfrom.rect.height);
			canvas.concat(mSmoothMatrix);
			getDrawable().draw(canvas);
			canvas.restoreToCount(saveCount);
			if (mTransformStart) {
				mTransformStart=false;
				startTransform(mState);
			} 
		} else {
			//当Transform In变化完成后，把背景改为黑色，使得Activity不透明
			mPaint.setAlpha(255);
			canvas.drawPaint(mPaint);
			super.onDraw(canvas);
		}
	}

	private void startTransform(final int state) {
		if (mTransfrom == null) {
			return;
		}
		ValueAnimator valueAnimator = new ValueAnimator();
		valueAnimator.setDuration(300);
		valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
		if (state == STATE_TRANSFORM_IN) {
			PropertyValuesHolder scaleHolder = PropertyValuesHolder.ofFloat("scale", mTransfrom.startScale, mTransfrom.endScale);
			PropertyValuesHolder leftHolder = PropertyValuesHolder.ofFloat("left", mTransfrom.startRect.left, mTransfrom.endRect.left);
			PropertyValuesHolder topHolder = PropertyValuesHolder.ofFloat("top", mTransfrom.startRect.top, mTransfrom.endRect.top);
			PropertyValuesHolder widthHolder = PropertyValuesHolder.ofFloat("width", mTransfrom.startRect.width, mTransfrom.endRect.width);
			PropertyValuesHolder heightHolder = PropertyValuesHolder.ofFloat("height", mTransfrom.startRect.height, mTransfrom.endRect.height);
			PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofInt("alpha", 0, 255);
			valueAnimator.setValues(scaleHolder, leftHolder, topHolder, widthHolder, heightHolder, alphaHolder);
		} else {
			PropertyValuesHolder scaleHolder = PropertyValuesHolder.ofFloat("scale", mTransfrom.endScale, mTransfrom.startScale);
			PropertyValuesHolder leftHolder = PropertyValuesHolder.ofFloat("left", mTransfrom.endRect.left, mTransfrom.startRect.left);
			PropertyValuesHolder topHolder = PropertyValuesHolder.ofFloat("top", mTransfrom.endRect.top, mTransfrom.startRect.top);
			PropertyValuesHolder widthHolder = PropertyValuesHolder.ofFloat("width", mTransfrom.endRect.width, mTransfrom.startRect.width);
			PropertyValuesHolder heightHolder = PropertyValuesHolder.ofFloat("height", mTransfrom.endRect.height, mTransfrom.startRect.height);
			PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofInt("alpha", 255, 0);
			valueAnimator.setValues(scaleHolder, leftHolder, topHolder, widthHolder, heightHolder, alphaHolder);
		}

		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public synchronized void onAnimationUpdate(ValueAnimator animation) {
				mTransfrom.scale = (Float) animation.getAnimatedValue("scale");
				mTransfrom.rect.left = (Float) animation.getAnimatedValue("left");
				mTransfrom.rect.top = (Float) animation.getAnimatedValue("top");
				mTransfrom.rect.width = (Float) animation.getAnimatedValue("width");
				mTransfrom.rect.height = (Float) animation.getAnimatedValue("height");
				mBgAlpha = (Integer) animation.getAnimatedValue("alpha");
				invalidate();
				((Activity)getContext()).getWindow().getDecorView().invalidate();
			}
		});
		valueAnimator.addListener(new ValueAnimator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				/*
				 * 如果是进入的话，当然是希望最后停留在center_crop的区域。但是如果是out的话，就不应该是center_crop的位置了
				 * ， 而应该是最后变化的位置，因为当out的时候结束时，不回复视图是Normal，要不然会有一个突然闪动回去的bug
				 */
				// TODO 这个可以根据实际需求来修改
				if (state == STATE_TRANSFORM_IN) {
					mState = STATE_NORMAL;
				}
				if (mTransformListener != null) {
					mTransformListener.onTransformComplete(state);
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});
		valueAnimator.start();
	}

	public void setOnTransformListener(TransformListener listener) {
		mTransformListener = listener;
	}

	private TransformListener mTransformListener;

	public static interface TransformListener {
		/**
		 * 
		 * @param mode
		 *            STATE_TRANSFORM_IN 1 ,STATE_TRANSFORM_OUT 2
		 */
		void onTransformComplete(int mode);// mode 1
	}




	public class MatrixTouchListener implements OnTouchListener {
		/** 拖拉照片模式 */
		private static final int MODE_DRAG = 1;
		/** 放大缩小照片模式 */
		private static final int MODE_ZOOM = 2;
		/**  不支持Matrix */
		private static final int MODE_UNABLE=3;
		/**   最大缩放级别*/
		float mMaxScale=6;
		/**   最小缩放级别 */
		float mMinScale = (float) 0.16;
		/**   双击时的缩放级别*/
		float mDobleClickScale=2;
		private int mMode = 0;//
		/**  缩放开始时的手指间距 */
		private float mStartDis;
		/**   当前Matrix*/
		private Matrix mCurrentMatrix = new Matrix();

		/** 用于记录开始时候的坐标位置 */
		private PointF startPoint = new PointF();
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getActionMasked()) {
				case MotionEvent.ACTION_DOWN:
					//设置拖动模式
					mMode=MODE_DRAG;
					startPoint.set(event.getX(), event.getY());
					isMatrixEnable();
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
//                reSetMatrix();
					break;
				case MotionEvent.ACTION_MOVE:
					if (mMode == MODE_ZOOM) {
						setZoomMatrix(event);
					}else if (mMode==MODE_DRAG) {
						setDragMatrix(event);
					}
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					if(mMode==MODE_UNABLE) return true;
					mMode=MODE_ZOOM;
					mStartDis = distance(event);
					break;
				default:
					break;
			}

			return mGestureDetector.onTouchEvent(event);
		}

		public void setDragMatrix(MotionEvent event) {
			if(isZoomChanged()){
				float dx = event.getX() - startPoint.x; // 得到x轴的移动距离
				float dy = event.getY() - startPoint.y; // 得到x轴的移动距离
				//避免和双击冲突,大于10f才算是拖动
				if(Math.sqrt(dx*dx+dy*dy)>10f){
					startPoint.set(event.getX(), event.getY());
					//在当前基础上移动
					mCurrentMatrix.set(getImageMatrix());
					mCurrentMatrix.postTranslate(dx, dy);
					setImageMatrix(mCurrentMatrix);
				}
			}
		}

		/**
		 *  判断缩放级别是否是改变过
		 *  @return   true表示非初始值,false表示初始值
		 */
		private boolean isZoomChanged() {
			float[] values=new float[9];
			getImageMatrix().getValues(values);
			//获取当前X轴缩放级别
			float scale=values[Matrix.MSCALE_X];
			//获取模板的X轴缩放级别，两者做比较
			mMatrix.getValues(values);
			return scale!=values[Matrix.MSCALE_X];
		}

		/**
		 *  和当前矩阵对比，检验dy，使图像移动后不会超出ImageView边界
		 *  @param values
		 *  @param dy
		 *  @return
		 */
		private float checkDyBound(float[] values, float dy) {
			float height=getHeight();
			if(mImageHeight*values[Matrix.MSCALE_Y]<height)
				return 0;
			if(values[Matrix.MTRANS_Y]+dy>0)
				dy=-values[Matrix.MTRANS_Y];
			else if(values[Matrix.MTRANS_Y]+dy<-(mImageHeight*values[Matrix.MSCALE_Y]-height))
				dy=-(mImageHeight*values[Matrix.MSCALE_Y]-height)-values[Matrix.MTRANS_Y];
			return dy;
		}

		/**
		 *和当前矩阵对比，检验dx，使图像移动后不会超出ImageView边界
		 *  @param values
		 *  @param dx
		 *  @return
		 */
		private float checkDxBound(float[] values,float dx) {
			float width=getWidth();
			if(mImageWidth*values[Matrix.MSCALE_X]<width)
				return 0;
			if(values[Matrix.MTRANS_X]+dx>0)
				dx=-values[Matrix.MTRANS_X];
			else if(values[Matrix.MTRANS_X]+dx<-(mImageWidth*values[Matrix.MSCALE_X]-width))
				dx=-(mImageWidth*values[Matrix.MSCALE_X]-width)-values[Matrix.MTRANS_X];
			return dx;
		}

		/**
		 *  设置缩放Matrix
		 *  @param event
		 */
		private void setZoomMatrix(MotionEvent event) {
			//只有同时触屏两个点的时候才执行
			if(event.getPointerCount()<2) return;
			float endDis = distance(event);// 结束距离
			if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10
				float scale = endDis / mStartDis;// 得到缩放倍数
				mStartDis=endDis;//重置距离
				mCurrentMatrix.set(getImageMatrix());//初始化Matrix
				float[] values=new float[9];
				mCurrentMatrix.getValues(values);

				scale = checkMaxOrMinScale(scale, values);
				setImageMatrix(mCurrentMatrix);
			}
		}

		/**
		 *  检验scale，使图像缩放后不会超出最大倍数
		 *  @param scale
		 *  @param values
		 *  @return
		 */
		private float checkMaxOrMinScale(float scale, float[] values) {
			if(scale*values[Matrix.MSCALE_X]>mMaxScale){
				scale=mMaxScale/values[Matrix.MSCALE_X];
				mCurrentMatrix.postScale(scale, scale,getWidth()/2,getHeight()/2);
			}else if(scale*values[Matrix.MSCALE_X]<=mMinScale){
				scale=mMinScale/values[Matrix.MSCALE_X];
				if(scale<1){
					mCurrentMatrix.postScale(scale, scale,getWidth()/2,getHeight()/2);
				}else{
					mCurrentMatrix.postScale(1, 1,getWidth()/2,getHeight()/2);
				}
			}else{
				mCurrentMatrix.postScale(scale, scale,getWidth()/2,getHeight()/2);
			}
			return scale;
		}

		/**
		 *   重置Matrix
		 */
		private void reSetMatrix() {
			if(checkRest()){
				mCurrentMatrix.set(mMatrix);
				setImageMatrix(mCurrentMatrix);
			}
		}

		/**
		 *  判断是否需要重置
		 *  @return  当前缩放级别小于模板缩放级别时，重置
		 */
		private boolean checkRest() {
			// TODO Auto-generated method stub
			float[] values=new float[9];
			getImageMatrix().getValues(values);
			//获取当前X轴缩放级别
			float scale=values[Matrix.MSCALE_X];
			//获取模板的X轴缩放级别，两者做比较
			mMatrix.getValues(values);
			return scale<values[Matrix.MSCALE_X];
		}

		/**
		 *  判断是否支持Matrix
		 */
		private void isMatrixEnable() {
			//当加载出错时，不可缩放
			if(getScaleType()!= ScaleType.CENTER){
				setScaleType(ScaleType.MATRIX);
			}else {
				mMode=MODE_UNABLE;//设置为不支持手势
			}
		}

		/**
		 *  计算两个手指间的距离
		 *  @param event
		 *  @return
		 */
		private float distance(MotionEvent event) {
			float dx = event.getX(1) - event.getX(0);
			float dy = event.getY(1) - event.getY(0);
			/** 使用勾股定理返回两点之间的距离 */
			return (float) Math.sqrt(dx * dx + dy * dy);
		}

		/**
		 *   双击时触发
		 */
		public void onDoubleClick(){
			float scale=isZoomChanged()?1:mDobleClickScale;
			mCurrentMatrix.set(mMatrix);//初始化Matrix
			mCurrentMatrix.postScale(scale, scale,getWidth()/2,getHeight()/2);
			setImageMatrix(mCurrentMatrix);
		}
	}



	private class  GestureListener extends GestureDetector.SimpleOnGestureListener {
		private final MatrixTouchListener listener;
		public GestureListener(MatrixTouchListener listener) {
			this.listener=listener;
		}
		@Override
		public boolean onDown(MotionEvent e) {
			//捕获Down事件
			return true;
		}
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			//触发双击事件
			listener.onDoubleClick();
			return true;
		}
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return super.onSingleTapUp(e);
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			super.onLongPress(e);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
			// TODO Auto-generated method stub

			return super.onFling(e1, e2, velocityX, velocityY);
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			super.onShowPress(e);
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			// TODO Auto-generated method stub
			return super.onDoubleTapEvent(e);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			// TODO Auto-generated method stub
			return super.onSingleTapConfirmed(e);
		}

	}

}
