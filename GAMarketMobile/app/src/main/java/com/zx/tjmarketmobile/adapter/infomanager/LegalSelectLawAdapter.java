package com.zx.tjmarketmobile.adapter.infomanager;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.MyRecycleAdapter;
import com.zx.tjmarketmobile.entity.KeyValueInfo;
import com.zx.tjmarketmobile.ui.infomanager.LegalSelectLawFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Stanny On 2017/3/13
 * 功能：案件详情适配器
 */
public class LegalSelectLawAdapter extends MyRecycleAdapter {
    private static final String TAG = "InfoManagerStandardAdapter";
    private List<KeyValueInfo> mItemList = null;
    private Context mContext;
    private boolean showOverdue = false;
    public Holder myHolder;

    public LegalSelectLawAdapter(Context mContext, List<KeyValueInfo> itemList, boolean showOverdue) {
        mItemList = itemList;
        this.mContext = mContext;
        this.showOverdue = showOverdue;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.infomanager_list_law, parent, false);
            return new Holder(view);
        } else {//footer
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_foot_view, parent, false);
            return new FooterViewHolder(view);
        }
    }

    private List<Integer> getChild(String parent, String child) {
        List<Integer> rets = new ArrayList<>();
        int count = 0;
        int index = 0;
        while ((index = parent.indexOf(child, index)) != -1) {
            rets.add(index);
            index = index + child.length();
            count++;
        }
        Log.i("wangwansheng", "匹配个数为" + count);
        return rets;
    }

    //从holder中获取控件并设置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Holder) {
            myHolder = (Holder) holder;
            KeyValueInfo entity = mItemList.get(position);

            myHolder.lawName.setText(entity.key);
            myHolder.lawContent.setText(Html.fromHtml(entity.value));
            String content = myHolder.lawContent.getText().toString();
            SpannableString sStr = new SpannableString(content);
            List<Integer> indexs = getChild(content, LegalSelectLawFragment.searchText);
            for (int i = 0; i < indexs.size(); i++) {
                int index = indexs.get(i).intValue();
                sStr.setSpan(new ForegroundColorSpan(Color.RED), index, index + LegalSelectLawFragment.searchText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            myHolder.lawContent.setText(sStr);
        } else {
            footerViewHolder = (FooterViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView lawName, lawContent;

        public Holder(View parent) {
            super(parent);
            lawName = parent.findViewById(R.id.law_name);
            lawContent = parent.findViewById(R.id.law_content);
            parent.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

}