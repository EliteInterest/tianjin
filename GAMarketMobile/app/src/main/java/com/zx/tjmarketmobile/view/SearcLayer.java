package com.zx.tjmarketmobile.view;

import android.os.AsyncTask;

import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.core.tasks.ags.find.FindParameters;
import com.esri.core.tasks.ags.find.FindResult;
import com.esri.core.tasks.ags.find.FindTask;
import com.zx.tjmarketmobile.listener.ICommonListener.IOnMapSearchListener;

import java.util.List;

/**
 *
 * Create By Stanny On 2016/9/22
 * 功能：查询控件MapLayer
 *
 */
public class SearcLayer extends ArcGISDynamicMapServiceLayer {

	private String mUrl;
	private IOnMapSearchListener mOnMapSearchListener;

	public SearcLayer(String url) {
		super(url);
		this.mUrl = url;
	}

	public void onSelect(IOnMapSearchListener mapSearchListener) {
		this.mOnMapSearchListener = mapSearchListener;
		MyQueryTask task = new MyQueryTask();
		FindParameters findParameters = new FindParameters();
		findParameters.setLayerIds(new int[] { 1 });
		findParameters.setReturnGeometry(true);
		findParameters.setSearchText("片区");
		task.execute(findParameters);
	}

	class MyQueryTask extends AsyncTask<FindParameters, Void, List<FindResult>> {

		private FindTask task;

		protected List<FindResult> doInBackground(FindParameters... params) {
			FindParameters query = params[0];
			List<FindResult> res = null;
			try {
				res = task.execute(query);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return res;
		}

		protected void onPreExecute() {
			task = new FindTask(mUrl);
		}

		protected void onPostExecute(List<FindResult> result) {
			if (result != null && mOnMapSearchListener != null) {
				mOnMapSearchListener.onSelect(result);
			}
		}
	}

}
