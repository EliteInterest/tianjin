package com.zx.tjmarketmobile.ui.mainbase;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.view.SmoothImageView;
import com.zx.tjmarketmobile.util.SYSUtil;
import com.zx.tjmarketmobile.zoom.NativeImageLoader;

import java.util.ArrayList;


public class SpaceImageDetailActivity extends Activity {

	private ArrayList<String> mDatas;
	private int mPosition;
	private int mLocationX;
	private int mLocationY;
	private int mWidth;
	private int mHeight;
	private String path;
	SmoothImageView imageView = null;
	Bitmap bitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mLocationX = getIntent().getIntExtra("locationX", 0);
		mLocationY = getIntent().getIntExtra("locationY", 0);
		mWidth = getIntent().getIntExtra("width", 0);
		mHeight = getIntent().getIntExtra("height", 0);
		path = getIntent().getStringExtra("path");
		if (path.contains("http")){
			String name = path.substring(path.lastIndexOf("/")+1,path.length());
			String storagePath = SYSUtil.CACHE_DIR_NAME+name;
			bitmap = NativeImageLoader.getBitmapFromMemCache(storagePath);
		}else{
			bitmap = NativeImageLoader.getBitmapFromMemCache(path);
		}
		imageView = new SmoothImageView(this);
		imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
		imageView.transformIn();
		imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
		imageView.setScaleType(ScaleType.FIT_CENTER);
		setContentView(imageView);
		if (bitmap!=null){
			imageView.setImageBitmap(bitmap);
		}else{
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pictures_no);
			imageView.setImageBitmap(bitmap);
		}

	}

	@Override
	public void onBackPressed() {
		imageView.setOnTransformListener(new SmoothImageView.TransformListener() {
			@Override
			public void onTransformComplete(int mode) {
				if (mode == 2) {
					finish();
				}
			}
		});
		imageView.transformOut();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			overridePendingTransition(0, 0);
		}
	}

}
