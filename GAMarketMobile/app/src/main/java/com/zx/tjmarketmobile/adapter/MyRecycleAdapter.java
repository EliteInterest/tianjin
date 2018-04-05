package com.zx.tjmarketmobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.listener.LoadMoreListener;
import com.zx.tjmarketmobile.listener.MyItemClickListener;

/**
 * Created by Xiangb on 2016/9/21.
 * 功能：封装的Recycler适配器。用于待FooterView的情况
 */
public class MyRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public LoadMoreListener mLoadMoreListener;
    public MyItemClickListener mListener;
    public FooterViewHolder footerViewHolder;
    //全局变量
    public static final int ITEM_TYPE_NORMAL = 1;
    public static final int ITEM_TYPE_FOOTER = 2;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public void setStatus(int status, int mPageNum, int mTotalNum) {
        if (footerViewHolder != null) {
            footerViewHolder.setStatus(status, mPageNum, mTotalNum, 10);
        }
    }

    public void setStatus(int status, int mPageNum, int mTotalNum, int pageSize) {
        if (footerViewHolder != null) {
            footerViewHolder.setStatus(status, mPageNum, mTotalNum, pageSize);
        }
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mListener = listener;
    }

    public void setOnLoadMoreListener(LoadMoreListener listener) {
        this.mLoadMoreListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return ITEM_TYPE_FOOTER;
        } else {
            return ITEM_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView loadText;
        private ProgressBar loadProgress;

        public FooterViewHolder(View parent) {
            super(parent);
            loadText = (TextView) parent.findViewById(R.id.load_tv);
            loadProgress = (ProgressBar) parent.findViewById(R.id.load_progress);
            itemView.setOnClickListener(this);
        }

        public void setStatus(int status, int mPageNum, int mTotalNum, int pageSize) {
            if (status == -1) {
                loadProgress.setVisibility(View.GONE);
                loadText.setVisibility(View.GONE);
            } else {
                if (status == 1) {
                    loadProgress.setVisibility(View.VISIBLE);
                    loadText.setText("正在加载中。。");
                } else if (mTotalNum == 0) {
                    loadProgress.setVisibility(View.GONE);
                    loadText.setText("没有数据");
                } else if (mPageNum * pageSize < mTotalNum) {
                    loadProgress.setVisibility(View.GONE);
                    loadText.setText("点击加载更多，第" + mPageNum + "页，共" + (mTotalNum / pageSize + 1) + "页");
                } else {
                    loadProgress.setVisibility(View.GONE);
                    loadText.setText("已加载完");
                }
            }
        }

        @Override
        public void onClick(View v) {
            if (mLoadMoreListener != null) {
                mLoadMoreListener.LoadMore();
            }
        }
    }
}
