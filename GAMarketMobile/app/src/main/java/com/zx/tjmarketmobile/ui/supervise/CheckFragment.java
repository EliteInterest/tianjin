package com.zx.tjmarketmobile.ui.supervise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.CheckInfo;
import com.zx.tjmarketmobile.entity.CheckItemFirst;
import com.zx.tjmarketmobile.entity.CheckItemSecond;
import com.zx.tjmarketmobile.ui.base.BaseFragment;

import java.util.List;

/**
 *
 * Create By Stanny On 2016/9/22
 * 功能：检查Fragment
 *
 */
public class CheckFragment extends BaseFragment {

	private List<CheckItemFirst> mCheckList;
	private LinearLayout mLlParent;

	public static CheckFragment newInstance(int index, List<CheckItemFirst> checkList) {
		CheckFragment details = new CheckFragment();
		Bundle args = new Bundle();
		args.putInt("index", index);
		details.setArguments(args);
		details.mCheckList = checkList;
		return details;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.fragment_check, null);
		mLlParent = (LinearLayout) view.findViewById(R.id.llFmCheck_parent);
		if (null != mCheckList) {
			if (mCheckList.size() == 1) {
				if (mCheckList.get(0).getSecondList().size() == 1) {
					CheckItemSecond item = mCheckList.get(0).getSecondList().get(0);
					for (int i = 0; i < item.threeList.size(); i++) {
						CheckInfo threeItem = item.threeList.get(i);
						View secondMenu = View.inflate(getActivity(), R.layout.item_menu_first, null);
						TextView tvName = (TextView) secondMenu.findViewById(R.id.tvItemMenuFirst_name);
						tvName.setText((i + 1) + "." + threeItem.getItemName());
						mLlParent.addView(secondMenu);
					}
				} else {
					addFirstView();
				}
			} else {
				addFirstView();
			}
		}
		return view;
	}

	private void addFirstView() {
		for (final CheckItemFirst item : mCheckList) {
			View firstMenu = View.inflate(getActivity(), R.layout.item_menu_first, null);
			TextView tvName = (TextView) firstMenu.findViewById(R.id.tvItemMenuFirst_name);
			final LinearLayout llContent = (LinearLayout) firstMenu.findViewById(R.id.llItemMenuFirst_content);
			tvName.setText(item.getName());
			tvName.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					View child = mLlParent.findViewWithTag(item);
					if (null == child) {
						child = createSecondView(item);
						llContent.addView(child);
					}
					if (item.isExpand()) {
						child.setVisibility(View.GONE);
					} else {
						child.setVisibility(View.VISIBLE);
					}
					item.setExpand(!item.isExpand());
				}
			});
			mLlParent.addView(firstMenu);
		}
	}

	private View createSecondView(CheckItemFirst item) {
		LinearLayout llSecond = new LinearLayout(getActivity());
		llSecond.setTag(item);
		llSecond.setOrientation(LinearLayout.VERTICAL);
		if (null != item.getSecondList()) {
			for (final CheckItemSecond secondItem : item.getSecondList()) {
				View secondMenu = View.inflate(getActivity(), R.layout.item_menu_first, null);
				TextView tvName = (TextView) secondMenu.findViewById(R.id.tvItemMenuFirst_name);
				final LinearLayout llContent = (LinearLayout) secondMenu.findViewById(R.id.llItemMenuFirst_content);
				tvName.setText(secondItem.name);
				tvName.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						View child = mLlParent.findViewWithTag(secondItem);
						if (null == child) {
							child = createThreeView(secondItem);
							llContent.addView(child);
						}
						if (secondItem.isExpand) {
							child.setVisibility(View.GONE);
						} else {
							child.setVisibility(View.VISIBLE);
						}
						secondItem.isExpand = !secondItem.isExpand;
					}
				});
				llSecond.addView(secondMenu);
			}
		}
		return llSecond;
	}

	private View createThreeView(CheckItemSecond item) {
		LinearLayout llSecond = new LinearLayout(getActivity());
		llSecond.setTag(item);
		llSecond.setOrientation(LinearLayout.VERTICAL);
		for (int i = 0; i < item.threeList.size(); i++) {
			CheckInfo threeItem = item.threeList.get(i);
			View secondMenu = View.inflate(getActivity(), R.layout.item_menu_first, null);
			TextView tvName = (TextView) secondMenu.findViewById(R.id.tvItemMenuFirst_name);
			final LinearLayout llContent = (LinearLayout) secondMenu.findViewById(R.id.llItemMenuFirst_content);
			tvName.setText((i + 1) + "." + threeItem.getItemName());
			llContent.setVisibility(View.GONE);
			llSecond.addView(secondMenu);
		}
		return llSecond;
	}

}