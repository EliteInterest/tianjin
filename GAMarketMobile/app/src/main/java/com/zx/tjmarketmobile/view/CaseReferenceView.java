package com.zx.tjmarketmobile.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.CaseRefeAdapter;
import com.zx.tjmarketmobile.entity.CaseRefeEntity;
import com.zx.tjmarketmobile.entity.SelectPopDataList;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.http.BaseRequestData;
import com.zx.tjmarketmobile.listener.ICheckedChangeListener;
import com.zx.tjmarketmobile.listener.ICommonListener;
import com.zx.tjmarketmobile.listener.LoadMoreListener;
import com.zx.tjmarketmobile.listener.MyItemClickListener;
import com.zx.tjmarketmobile.ui.caselegal.CaseExcuteFragment;
import com.zx.tjmarketmobile.util.ZXDialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2017/11/1.
 * 功能：
 */
@SuppressWarnings("deprecation")
public class CaseReferenceView implements BaseRequestData.OnHttpLoadingListener<BaseHttpResult> {
    private CaseExcuteFragment mFragment;
    private Dialog dialog;
    private RelativeLayout rlRefe1, rlRefe2;
    private TextView tvRefe1, tvRefe2;
    private EditText etRefe;
    private Button btnRefe;
    private TextView tvRefeTotal;
    private SwipeRefreshLayout srlRefe;
    private RecyclerView rvRefe;
    private RelativeLayout rlDetail;
    private TextView tvDetail;
    private ImageView ivCancel;
    private SuperviseSelectPopuView mPopRefe1, mPopRefe2;
    private List<SelectPopDataList> refe1List = new ArrayList<>();
    private List<SelectPopDataList> refe2List = new ArrayList<>();
    private String searchType = "";
    private CaseRefeAdapter mAdapter;

    private int mPageSize = 10;
    private int mPageNo = 1;
    private int mTotalNo = 0;
    private String fType = "", fIllegalAct = "", fViolateLaw = "", fPunishLaw = "", fCondition = "";
    private List<CaseRefeEntity.RowsBean> mTaskList = new ArrayList<>();
    public List<CaseRefeEntity.RowsBean> mCheckedList = new ArrayList<>();
    private CaseRefeEntity.RowsBean mEntity;

    private ApiData selectDisData = new ApiData(ApiData.HTTP_ID_caseSelectDiscretionStandard);

    public CaseReferenceView(CaseExcuteFragment fragment) {
        mFragment = fragment;
    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
            return;
        }
        View view = LayoutInflater.from(mFragment.getActivity()).inflate(R.layout.layout_case_reference, null, false);
        rlRefe1 = (RelativeLayout) view.findViewById(R.id.rl_case_refeType1);
        rlRefe2 = (RelativeLayout) view.findViewById(R.id.rl_case_refeType2);
        tvRefe1 = (TextView) view.findViewById(R.id.tv_case_refeType1);
        tvRefe2 = (TextView) view.findViewById(R.id.tv_case_refeType2);
        etRefe = (EditText) view.findViewById(R.id.et_case_refe_search);
        btnRefe = (Button) view.findViewById(R.id.btn_case_refe_foSearch);
        srlRefe = (SwipeRefreshLayout) view.findViewById(R.id.srl_normal_layout);
        rvRefe = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        tvRefeTotal = (TextView) view.findViewById(R.id.tv_case_refeTotal);
        rlDetail = (RelativeLayout) view.findViewById(R.id.rl_case_refe_detail);
        tvDetail = (TextView) view.findViewById(R.id.tv_case_refe_detail);
        ivCancel = (ImageView) view.findViewById(R.id.iv_case_refe_cancel);
        selectDisData.setLoadingListener(this);

        tvDetail.setMovementMethod(new ScrollingMovementMethod());
        refe1List.add(new SelectPopDataList("全部类型", "0"));
        refe1List.add(new SelectPopDataList("工商", "1"));
        refe1List.add(new SelectPopDataList("质监", "2"));
        refe1List.add(new SelectPopDataList("食药监", "3"));
        refe2List.add(new SelectPopDataList("全部类型", "0"));
        refe2List.add(new SelectPopDataList("违法行为", "1"));
        refe2List.add(new SelectPopDataList("定性依据", "2"));
        refe2List.add(new SelectPopDataList("处罚依据", "3"));
        rlRefe1.setOnClickListener(new View.OnClickListener() {//选择领域
            @Override
            public void onClick(View view) {
                if (mPopRefe1 == null) {
                    mPopRefe1 = new SuperviseSelectPopuView(mFragment.getActivity());
                    mPopRefe1.setDataSelectListener(mListener);
                }
                mPopRefe1.show(rlRefe1, view.getWidth(), refe1List, 1);
            }
        });
        rlRefe2.setOnClickListener(new View.OnClickListener() {//选择类型
            @Override
            public void onClick(View view) {
                if (mPopRefe2 == null) {
                    mPopRefe2 = new SuperviseSelectPopuView(mFragment.getActivity());
                    mPopRefe2.setDataSelectListener(mListener);
                }
                mPopRefe2.show(rlRefe2, view.getWidth(), refe2List, 2);
            }
        });
        btnRefe.setOnClickListener(new View.OnClickListener() {//搜索
            @Override
            public void onClick(View view) {
                fIllegalAct = "";
                fPunishLaw = "";
                fViolateLaw = "";
                fCondition = "";
                if ("1".equals(searchType)) {
                    fIllegalAct = etRefe.getText().toString();
                } else if ("2".equals(searchType)) {
                    fViolateLaw = etRefe.getText().toString();
                } else if ("3".equals(searchType)) {
                    fPunishLaw = etRefe.getText().toString();
                } else {
                    fCondition = etRefe.getText().toString();
                }
                loadData();
            }
        });
        dialog = ZXDialogUtil.showCustomViewDialog(mFragment.getActivity(), "参考", view, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                mFragment.setReferenceInfo(mCheckedList);
                dialog.dismiss();
                //TODO
            }
        }, "提交", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        }, "取消", false);
        mAdapter = new CaseRefeAdapter(mTaskList);
        rvRefe.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
        rvRefe.setAdapter(mAdapter);
        srlRefe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//刷新
            @Override
            public void onRefresh() {
                if (mPageNo > 1) {
                    mPageNo--;
                }
                loadData();
            }
        });
        mAdapter.setOnItemClickListener(new MyItemClickListener() {//item点击
            @Override
            public void onItemClick(View view, int position) {
                rlDetail.setVisibility(View.VISIBLE);
                mEntity = mTaskList.get(position);
                tvDetail.setText(Html.fromHtml(mEntity.getFDiscretionStandard()).toString().replace("\n\n", "\n"));
            }
        });
        mAdapter.setOnLoadMoreListener(new LoadMoreListener() {//加载更多
            @Override
            public void LoadMore() {
                if (mPageNo * mPageSize < mTotalNo) {
                    mPageNo++;
                    mAdapter.setStatus(1, mPageNo, mTotalNo);
                    loadData();
                }
            }
        });
        mAdapter.setOnCheckedChangeListener(new ICheckedChangeListener() {
            @Override
            public void CheckedChange(int position, boolean checked) {
                if (checked) {
                    mCheckedList.add(mTaskList.get(position));
                } else {
                    for (int i = 0; i < mCheckedList.size(); i++) {
                        for (int j = 0; j < mTaskList.size(); j++) {
                            if (mCheckedList.get(i).getFGuid().equals(mTaskList.get(j).getFGuid())) {
                                mCheckedList.remove(i);
                                return;
                            }
                        }
                    }
                }
            }
        });
        ivCancel.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View view) {
                rlDetail.setVisibility(View.GONE);
            }
        });
        loadData();
    }

    private void loadData() {
        selectDisData.loadData(mPageNo, mPageSize, 1, fType, fIllegalAct, fViolateLaw, fPunishLaw, fCondition);
    }

    @Override
    public void onLoadStart(int id) {

    }

    @Override
    public void onLoadError(int id, boolean isAPIError, String error_message) {
        mFragment.showToast(error_message);
        srlRefe.setRefreshing(false);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        srlRefe.setRefreshing(false);
        CaseRefeEntity mEntity = (CaseRefeEntity) b.getEntry();
        mTotalNo = mEntity.getTotal();
        tvRefeTotal.setText("共" + mTotalNo + "条,已选" + mCheckedList.size() + "条,点击条目查看裁量标准");
        mTaskList.clear();
        mTaskList.addAll(mEntity.getRows());
        for (int i = 0; i < mCheckedList.size(); i++) {
            for (int j = 0; j < mTaskList.size(); j++) {
                if (mCheckedList.get(i).getFGuid().equals(mTaskList.get(j).getFGuid())) {
                    mTaskList.get(j).setHasChecked(true);
                    break;
                }
            }
        }
        mAdapter.setStatus(0, mPageNo, mTotalNo);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadPregress(int id, int progress) {

    }

    private ICommonListener.IOnInfoSelectListener mListener = new ICommonListener.IOnInfoSelectListener() {
        @Override
        public void onSelect(int pos, SelectPopDataList info, int index) {
            if (index == 1) {
                tvRefe1.setText(info.getfTaskName());
                fType = "0".equals(info.getfTaskId()) ? "" : info.getfTaskName();
            } else {
                tvRefe2.setText(info.getfTaskName());
                searchType = info.getfTaskId();
            }
        }
    };
}
