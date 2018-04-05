package com.zx.tjmarketmobile.ui.supervise;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.supervise.SuperviseClaimedAdapter;
import com.zx.tjmarketmobile.entity.EntityDetail;
import com.zx.tjmarketmobile.entity.HttpSearchZtEntity;
import com.zx.tjmarketmobile.entity.HttpZtEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.ICheckedChangeListener;
import com.zx.tjmarketmobile.listener.LoadMoreListener;
import com.zx.tjmarketmobile.listener.MyItemClickListener;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.ui.map.EntityDetailActivity;
import com.zx.tjmarketmobile.util.GpsTool;
import com.zx.tjmarketmobile.util.Util;

import java.util.ArrayList;
import java.util.List;


/**
 * Create By Xiangb On 2016/9/22
 * 功能：监管待认领列表
 */
public class SuperviseClaimedFragment extends BaseFragment implements MyItemClickListener, LoadMoreListener, View.OnClickListener {

    private SuperviseClaimedAdapter mAdapter;
    private List<HttpZtEntity> mTaskList = new ArrayList<>();
    private List<String> checkedTaskIds = new ArrayList<>();
    private EditText etClaimed;
    private ImageButton ibClaimed;
    private int type = 0;//0坐标纠正 1主体认领
    private String sfrl = "", sfjz = "", station = "";
    private SwipeRefreshLayout SRL_supercise;
    private TextView tvClaimedCheck_num;
    private RecyclerView RV_supercise;
    private int mPageSize = 10;
    public int mPageNo = 1;
    public int mTotalNo = 0;
    private String currentStr = "";
    private int currentIndex = -1;
    private Location location = null;
    private HttpSearchZtEntity mSearchZtEntity = null;
    private String mKeyword = "";
    private ApiData ztsearchData = new ApiData(ApiData.HTTP_ID_searchall);
    private ApiData ztSearchJyfwData = new ApiData(ApiData.HTTP_ID_searchzt_Jyfw);
    private ApiData taskData = new ApiData(ApiData.HTTP_ID_entity_detail);
    private ApiData entityClaimed = new ApiData(ApiData.HTTP_ID_doClaimed);
    private ApiData mChangeposData = new ApiData(ApiData.HTTP_ID_change_pos);

    public static SuperviseClaimedFragment newInstance(int index) {
        SuperviseClaimedFragment details = new SuperviseClaimedFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        details.setArguments(args);

        return details;
    }

    public void loadData() {
        loadData(false);
    }

    public void loadData(boolean isFirst) {
        if (isFirst) {
            mPageNo = 1;
        }
        if (!"湖潮乡".equals(station) &&
                !"高峰镇".equals(station) &&
                !"党武镇".equals(station) &&
                !"马场镇".equals(station)) {
            station = "";
        }
        ztsearchData.loadData(mPageNo, "10", mKeyword, sfrl, sfjz, station);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_claimedlist, container, false);
        type = getArguments().getInt("index");
        if (type == 0) {
            sfjz = "1";
            station = userInfo.getDepartmentAlias();
        } else {
            sfrl = "1";
        }
        ztsearchData.setLoadingListener(this);
        ztSearchJyfwData.setLoadingListener(this);
        entityClaimed.setLoadingListener(this);
        mChangeposData.setLoadingListener(this);
        taskData.setLoadingListener(this);
        etClaimed = (EditText) view.findViewById(R.id.et_claimed);
        ibClaimed = (ImageButton) view.findViewById(R.id.ibtn_claimed);
        SRL_supercise = (SwipeRefreshLayout) view.findViewById(R.id.srl_normal_layout);
        RV_supercise = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        tvClaimedCheck_num = (TextView) view.findViewById(R.id.tvClaimedCheck_num);
        mAdapter = new SuperviseClaimedAdapter(getActivity(), mTaskList, type);
        RV_supercise.setLayoutManager(mLinearLayoutManager);
        RV_supercise.setAdapter(mAdapter);
        checkedTaskIds.clear();
        tvClaimedCheck_num.setOnClickListener(this);
        ibClaimed.setOnClickListener(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this);
        SRL_supercise.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mPageNo > 1) {
                    mPageNo--;
                }
                loadData();
            }
        });
        etClaimed.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    Util.closeKeybord(etClaimed, getActivity());
                    mKeyword = etClaimed.getText().toString().trim();
                    mPageNo = 1;
                    ztsearchData.loadData(mPageNo, mPageSize, mKeyword, sfrl, sfjz, station);
                }
                return false;
            }
        });
//        mAdapter.setOnCheckedChangeListener(new ICheckedChangeListener() {
//            @Override
//            public void CheckedChange(int position, boolean checked) {
//                if (checked) {
//                    if (!checkedTaskIds.contains(mTaskList.get(position).getGuid())) {
//                        checkedTaskIds.add(mTaskList.get(position).getGuid());
//                    }
//                } else {
//                    checkedTaskIds.remove(mTaskList.get(position).getGuid());
//                }
//                setSelectStatus(mSearchZtEntity.getZtList());
//                if (checkedTaskIds.size() > 0) {
//                    tvClaimedCheck_num.setVisibility(View.VISIBLE);
//                    tvClaimedCheck_num.setText(checkedTaskIds.size() + "");
//                } else {
//                    tvClaimedCheck_num.setVisibility(View.GONE);
//                }
//            }
//        });

        return view;
    }


    @Override
    public void LoadMore() {
        if (mPageNo * mPageSize < mTotalNo) {
            mPageNo++;
            mAdapter.setStatus(1, mPageNo, mTotalNo);
            loadData();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        currentIndex = position;
        HttpZtEntity zt = mTaskList.get(position);
//        loadZtJyfwData(zt.getGuid());
//        taskData.loadData(userInfo.getId(), zt.getGuid(), zt.getCreditLevel(), zt.getfType());
    }

    public void loadZtJyfwData(String guid) {
        ztSearchJyfwData.loadData(guid);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        SRL_supercise.setRefreshing(false);
        if (b.isSuccess()) {
            switch (id) {
                case ApiData.HTTP_ID_searchall:
                    mSearchZtEntity = (HttpSearchZtEntity) b.getEntry();
                    mTotalNo = mSearchZtEntity.getTotal();
                    mTaskList.clear();
                    mTaskList.addAll(setSelectStatus(mSearchZtEntity.getZtList()));
                    mAdapter.notifyDataSetChanged();
                    RV_supercise.smoothScrollToPosition(0);
                    mAdapter.setStatus(0, mPageNo, mTotalNo);
                    break;
//                case ApiData.HTTP_ID_searchzt_Jyfw:
//                    String strJyfw = b.getEntry().toString();
//                    HttpZtEntity zt = mTaskList.get(currentIndex);
//                    zt.setJyfw(strJyfw);
//                    Intent intent = new Intent(getActivity(), EntityDetailActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("type", type);
//                    bundle.putSerializable("entity", zt);
//                    intent.putExtras(bundle);
//                    if(zt!=null){
//                        String fEntityType = zt.getfType();
//                        intent.putExtra("fEntityType", fEntityType);
//                    }
//                    startActivity(intent);
//                    Util.activity_In(getActivity());
//                    break;
                case ApiData.HTTP_ID_entity_detail:
                    HttpZtEntity zt = mTaskList.get(currentIndex);
                    EntityDetail mEntityDetail = (EntityDetail) b.getEntry();
                    Intent intent = new Intent(getActivity(), EntityDetailActivity.class);
                    intent.putExtra("type", 2);
                    intent.putExtra("entity", mEntityDetail);
                    intent.putExtra("ztEntity", zt);
                    if (zt != null) {
//                        String fEntityType = zt.getfType();
//                        intent.putExtra("fEntityType", fEntityType);
                    }
                    startActivity(intent);
                    Util.activity_In(getActivity());
                    break;
                case ApiData.HTTP_ID_doClaimed:
                    showToast("主体认领成功");
                    Util.dialog.dismiss();
                    checkedTaskIds.clear();
                    tvClaimedCheck_num.setVisibility(View.GONE);
                    loadData(true);
                    break;
                case ApiData.HTTP_ID_change_pos:
                    showToast("纠正成功");
                    checkedTaskIds.clear();
                    tvClaimedCheck_num.setVisibility(View.GONE);
                    mTaskList.clear();
                    mTaskList.addAll(setSelectStatus(mSearchZtEntity.getZtList()));
                    Util.dialog.dismiss();
                    loadData();
                    break;
                default:
                    break;
            }
        }
    }

    //设置选中属性
    private List<HttpZtEntity> setSelectStatus(List<HttpZtEntity> ztList) {
//        for (int i = 0; i < ztList.size(); i++) {
//            ztList.get(i).setSelected(false);
//            for (int j = 0; j < checkedTaskIds.size(); j++) {
//                if (ztList.get(i).getGuid().equals(checkedTaskIds.get(j))) {
//                    ztList.get(i).setSelected(true);
//                    break;
//                }
//            }
//        }
        return ztList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_claimed:
                Util.closeKeybord(etClaimed, getActivity());
                mKeyword = etClaimed.getText().toString().trim();
                mPageNo = 1;
                ztsearchData.loadData(mPageNo, mPageSize, mKeyword, sfrl, sfjz, station);
                break;
            case R.id.tvClaimedCheck_num:
                if (type == 1) {
                    String depatrMent = userInfo.getDepartmentAlias();
                    if ("湖潮乡".equals(depatrMent) ||
                            "高峰镇".equals(depatrMent) ||
                            "党武镇".equals(depatrMent) ||
                            "马场镇".equals(depatrMent)) {
                        Util.showThreeActionDialog(getActivity(), "提示！", "是否认领所选主体？", "清空", "认领", "取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {//清空
                                checkedTaskIds.clear();
                                tvClaimedCheck_num.setVisibility(View.GONE);
                                mTaskList.clear();
                                mTaskList.addAll(setSelectStatus(mSearchZtEntity.getZtList()));
                                mAdapter.notifyDataSetChanged();
                                Util.dialog.dismiss();
                            }
                        }, new View.OnClickListener() {//认领
                            @Override
                            public void onClick(View v) {
                                String guid = "";
                                for (int i = 0; i < checkedTaskIds.size(); i++) {
                                    guid = guid + checkedTaskIds.get(i) + ",";
                                }
                                if (guid.length() > 0) {
                                    guid = guid.substring(0, guid.length() - 1);
                                }
                                entityClaimed.loadData(guid, userInfo.getId(), userInfo.getDepartment());
                            }
                        }, null);
                    } else {
                        final String[] areaArray = new String[]{"湖潮分局", "高峰分局", "党武分局", "马场分局"};
                        currentStr = "";
                        Util.showThreeActionListDialog(getActivity(), "提示！", "是否认领所选主体？", "清空", "认领", "取消",
                                areaArray, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {//清空
                                        checkedTaskIds.clear();
                                        tvClaimedCheck_num.setVisibility(View.GONE);
                                        mTaskList.clear();
                                        mTaskList.addAll(setSelectStatus(mSearchZtEntity.getZtList()));
                                        mAdapter.notifyDataSetChanged();
                                        Util.dialog.dismiss();
                                    }
                                }, new View.OnClickListener() {//认领
                                    @Override
                                    public void onClick(View v) {
                                        String guid = "";
                                        for (int i = 0; i < checkedTaskIds.size(); i++) {
                                            guid = guid + checkedTaskIds.get(i) + ",";
                                        }
                                        if (guid.length() > 0) {
                                            guid = guid.substring(0, guid.length() - 1);
                                        }
                                        if (!TextUtils.isEmpty(currentStr)) {
                                            entityClaimed.loadData(guid, userInfo.getId(), currentStr);
                                        } else {
                                            showToast("请先选择认领区域！");
                                        }

                                    }
                                },
                                null,
                                new ICheckedChangeListener() {
                                    @Override
                                    public void CheckedChange(int position, boolean checked) {
                                        if (checked) {
                                            currentStr = areaArray[position];
                                        }
                                    }
                                });
                    }

                } else {
                    if (!GpsTool.isOpen(getActivity())) {
                        GpsTool.openGPS(getActivity());
                    } else {
                        location = GpsTool.getGpsLocation(getActivity());
                        final StringBuilder builder = new StringBuilder();
                        if (location != null) {
                            if (mTaskList.size() > 0) {
                                for (int i = 0; i < mTaskList.size(); i++) {
//                                    if (mTaskList.get(i).getGuid() != null) {
//                                        String guid = mTaskList.get(i).getGuid();
//                                        builder.append(guid).append(",");
//                                    }
                                }
                            }
                            Util.showYesOrNoDialog(getActivity(), "提示！", "是否将这些主体的位置纠正为当前坐标：\n" + location.getLongitude() + "," + location.getLatitude(), "确认", "取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mChangeposData.loadData(builder.toString(), userInfo.getId(), location.getLongitude(), location.getLatitude(), null);
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    checkedTaskIds.clear();
                                    tvClaimedCheck_num.setVisibility(View.GONE);
                                    mTaskList.clear();
                                    mTaskList.addAll(setSelectStatus(mSearchZtEntity.getZtList()));
                                    mAdapter.notifyDataSetChanged();
                                    Util.dialog.dismiss();
                                }
                            });
                        } else {
                            showToast("当前坐标定位失败，请重试");
                        }
                    }
                    break;
                }

                break;
            default:
                break;
        }
    }
}
