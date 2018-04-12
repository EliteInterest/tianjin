package com.zx.tjmarketmobile.ui.infomanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.infomanager.LegalSelectAdapter;
import com.zx.tjmarketmobile.entity.LegalEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.LoadMoreListener;
import com.zx.tjmarketmobile.listener.MyItemClickListener;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.view.ZXExpandBean;
import com.zx.tjmarketmobile.view.ZXExpandItemClickListener;
import com.zx.tjmarketmobile.view.ZXExpandRecyclerHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzq on 2017/3/23.
 */

public class LegalSelectFragment extends BaseFragment implements LoadMoreListener, MyItemClickListener {
    private static final String TAG = "StandardMessageSelectFragment";
    private RecyclerView rvTodo;
    private LegalSelectAdapter mAdapter;
    //    private List<InfoManagerLegalSelect> dataList = new ArrayList<>();
    private List<ZXExpandBean> dataList = new ArrayList<>();
    private int mPageSize = 10;
    private String msg;
    private static String userId;
    private static String userDepartmentCode;
    public int mPageNo = 1;
    public int mTotalNo = 0;
    private ApiData getInfoStandar = new ApiData(ApiData.HTTP_ID_info_manager_legal_query);

    public static LegalSelectFragment newInstance(String userID, String userDepartmentCODE) {
        userId = userID;
        userDepartmentCode = userDepartmentCODE;
        LegalSelectFragment fragment = new LegalSelectFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_to_do_list, container, false);
        rvTodo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        rvTodo.setLayoutManager(mLinearLayoutManager);
        getInfoStandar.setLoadingListener(this);

        return view;
    }

    //加载更多
    @Override
    public void LoadMore() {
        if (mPageNo * mPageSize < mTotalNo) {
            mPageNo++;
            mAdapter.setStatus(1, mPageNo, mTotalNo);
            loadData();
        }
    }

    //item点击事件
    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    //数据加载
    @SuppressLint("LongLogTag")
    private void loadData() {
        Log.i("wangwansheng", "userDepartmentCode is " + userDepartmentCode + "userId is " + userId);
        getInfoStandar.loadData(userDepartmentCode, userId);
    }

    public void load(final String msg) {
        this.msg = msg;
        getInfoStandar.loadData(mPageNo, mPageSize, msg);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_info_manager_legal_query:
                List<ZXExpandBean> myTaskListEntity = (List<ZXExpandBean>) b.getEntry();
                ZXExpandRecyclerHelper.getInstance(getActivity())
                        .withRecycler(rvTodo)
                        .setData(myTaskListEntity)
                        .multiSelected(false)
                        .setItemClickListener(new ZXExpandItemClickListener() {
                            @Override
                            public void onItemClick(ZXExpandBean expandBean) {
                                ((InfoHomeActivity) getActivity()).setLegalRadioStatus(0, ((LegalEntity) expandBean.getCustomData()));
                            }

                            @Override
                            public void onMenuClick(ZXExpandBean expandBean) {
                                if (expandBean.getIndex() == 0) {
                                    ((InfoHomeActivity) getActivity()).setLegalRadioStatus(1, ((LegalEntity) expandBean.getCustomData()));
                                } else {
                                    ((InfoHomeActivity) getActivity()).setLegalRadioStatus(2, ((LegalEntity) expandBean.getCustomData()));
                                }
                            }
                        }).build();
                break;

            default:
                break;
        }
    }
}
