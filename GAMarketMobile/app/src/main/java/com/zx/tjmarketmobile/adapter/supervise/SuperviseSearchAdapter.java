package com.zx.tjmarketmobile.adapter.supervise;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.ui.supervise.EntityListActivity;
import com.zx.tjmarketmobile.entity.SuperviseCountInfo;

import java.util.List;

/**
 * Create By Xiangb On 2016/9/22
 * 功能：监管任务适配器
 */
public class SuperviseSearchAdapter extends BaseAdapter {

    private List<SuperviseCountInfo> mDataList;
    private Context mContext;
    private int mTaskType;
    private String mTaskId;
    private String mStatus;
    private String mType = "全部";//即将逾期、逾期

    public void setType(String type) {
        this.mType = type;
    }

    public SuperviseSearchAdapter(Context c, int taskType, String status, List<SuperviseCountInfo> checkList) {
        this.mDataList = checkList;
        this.mContext = c;
        this.mTaskType = taskType;
        this.mStatus = status;
    }

    public void setTaskId(String taskId) {
        this.mTaskId = taskId;
    }

    public String getTaskId() {
        return this.mTaskId;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder;
        final SuperviseCountInfo superviseInfo = mDataList.get(position);
        if (view == null) {
            holder = new Holder();
            view = View.inflate(mContext, R.layout.item_supervisesearch, null);
            holder.tvGrid = (TextView) view.findViewById(R.id.tvItemSupervisesearch_station);
            holder.tvTotal = (TextView) view.findViewById(R.id.tvItemSupervisesearch_total);
            holder.tvCount1 = (TextView) view.findViewById(R.id.tvItemSupervisesearch_notok);
            holder.tvCount2 = (TextView) view.findViewById(R.id.tvItemSupervisesearch_ok);
            holder.tvCount3 = (TextView) view.findViewById(R.id.tvItemSupervisesearch_turn);
            holder.tvStatus = (TextView) view.findViewById(R.id.tvItemSupervisesearch_status);
            holder.llContent = (LinearLayout) view.findViewById(R.id.llItemSupervisesearch_content);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.tvGrid.setText(superviseInfo.getfGroupName());
        holder.tvTotal.setText(superviseInfo.getTotal());
        holder.tvCount1.setText(superviseInfo.getCount1());
        holder.tvCount2.setText(superviseInfo.getCount2());
        holder.tvCount3.setText(superviseInfo.getCount3());
        holder.tvStatus.setText(superviseInfo.getCount4());
        if (superviseInfo.isSelected()) {
            int supervise_select = ContextCompat.getColor(mContext, R.color.supervise_bg);
            holder.llContent.setBackgroundColor(supervise_select);
        } else {
            int supervise_bg = ContextCompat.getColor(mContext, R.color.transparent);
            holder.llContent.setBackgroundColor(supervise_bg);
        }
        holder.tvTotal.setOnClickListener(createListener(superviseInfo, 0));
        holder.tvCount1.setOnClickListener(createListener(superviseInfo, 1));
        holder.tvCount2.setOnClickListener(createListener(superviseInfo, 2));
        if ("待处置".equals(mStatus)) {
            holder.tvCount3.setVisibility(View.GONE);
        } else {
            holder.tvCount3.setOnClickListener(createListener(superviseInfo, 3));
            holder.tvCount3.setVisibility(View.VISIBLE);
        }
        if (mTaskType == 1) {//mTaskType==1：任务监控
            holder.tvStatus.setOnClickListener(createListener(superviseInfo, 4));
            holder.tvStatus.setVisibility(View.VISIBLE);
        } else {
            holder.tvStatus.setVisibility(View.GONE);
        }
        return view;
    }

    private OnClickListener createListener(final SuperviseCountInfo superviseInfo, final int status) {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchType = "";
                switch (status) {
                    case 0:
                        if ("0".equals(superviseInfo.getTotal())) {
                            Toast.makeText(mContext, "没有相关主体", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            if ("任务监控".equals(mStatus)) {
                                searchType = "dcz";
                            }
                        }
                        break;
                    case 1:
                        if ("0".equals(superviseInfo.getCount1())) {
                            Toast.makeText(mContext, "没有相关主体", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            if ("待处置".equals(mStatus)) {
                                searchType = "dcz";
                            } else if ("待初审".equals(mStatus)) {
                                searchType = "dcs";
                            } else if ("待核审".equals(mStatus)) {
                                searchType = "dhs";
                            } else if ("待终审".equals(mStatus)) {
                                searchType = "dzs";
                            } else if ("任务监控".equals(mStatus)) {
                                searchType = "dcs";
                            }
                        }
                        break;
                    case 2:
                        if ("0".equals(superviseInfo.getCount2())) {
                            Toast.makeText(mContext, "没有相关主体", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            if ("待处置".equals(mStatus)) {
                                searchType = "ycz";
                            } else if ("待初审".equals(mStatus)) {
                                searchType = "dcs";
                            } else if ("待核审".equals(mStatus)) {
                                searchType = "dhs";
                            } else if ("待终审".equals(mStatus)) {
                                searchType = "dzs";
                            } else if ("任务监控".equals(mStatus)) {
                                searchType = "dhs";
                            }
                        }
                        break;
                    case 3:
                        if ("0".equals(superviseInfo.getCount3())) {
                            Toast.makeText(mContext, "没有相关主体", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            if ("待初审".equals(mStatus)) {
                                searchType = "ycs";
                            } else if ("待核审".equals(mStatus)) {
                                searchType = "yhs";
                            } else if ("待终审".equals(mStatus)) {
                                searchType = "yzs";
                            } else if ("任务监控".equals(mStatus)) {
                                searchType = "dzs";
                            }
                        }
                        break;
                    case 4:
                        if ("0".equals(superviseInfo.getCount4())) {
                            Toast.makeText(mContext, "没有相关主体", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            if ("任务监控".equals(mStatus)) {
                                searchType = "finish";
                            }
                        }
                        break;
                    default:
                        break;
                }

                if (status == 1 && "0".equals(superviseInfo.getCount1())) {
                    Toast.makeText(mContext, "没有相关主体", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (status == 2 && "0".equals(superviseInfo.getCount2())) {
                    Toast.makeText(mContext, "没有相关主体", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (status == 3 && "0".equals(superviseInfo.getCount3())) {
                    Toast.makeText(mContext, "没有相关主体", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (status == 4 && "0".equals(superviseInfo.getCount4())) {
                    Toast.makeText(mContext, "没有相关主体", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(mContext, EntityListActivity.class);
                intent.putExtra("superviseInfo", superviseInfo);
                intent.putExtra("status", mStatus);
                intent.putExtra("taskId", mTaskId);
                intent.putExtra("searchType", searchType);
                mContext.startActivity(intent);
            }
        };
        return listener;
    }

    private class Holder {
        TextView tvGrid;
        TextView tvTotal;
        TextView tvCount1;
        TextView tvCount2;
        TextView tvCount3;
        TextView tvStatus;
        LinearLayout llContent;
    }
}
