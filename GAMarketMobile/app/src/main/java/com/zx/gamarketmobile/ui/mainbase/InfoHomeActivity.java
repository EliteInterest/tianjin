package com.zx.gamarketmobile.ui.mainbase;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.adapter.infomanager.InfoManagerDeviceAdapter;
import com.zx.gamarketmobile.adapter.infomanager.InfoManagerStandardAdapter;
import com.zx.gamarketmobile.adapter.MyRecycleAdapter;
import com.zx.gamarketmobile.entity.infomanager.InfoManagerBiaozhun;
import com.zx.gamarketmobile.entity.infomanager.InfoManagerDevice;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.http.BaseHttpResult;
import com.zx.gamarketmobile.listener.LoadMoreListener;
import com.zx.gamarketmobile.listener.MyItemClickListener;
import com.zx.gamarketmobile.ui.base.BaseActivity;
import com.zx.gamarketmobile.util.Util;

import java.util.ArrayList;
import java.util.List;

import static com.zx.gamarketmobile.http.ApiData.HTTP_ID_info_manager_biaozhun;
import static com.zx.gamarketmobile.http.ApiData.HTTP_ID_info_manager_device_detail;
import static com.zx.gamarketmobile.http.ApiData.HTTP_ID_info_manager_device_liebiao;
import static com.zx.gamarketmobile.http.ApiData.HTTP_ID_info_manager_legal_query;
import static com.zx.gamarketmobile.http.ApiData.HTTP_ID_info_manager_legal_search;
import static com.zx.gamarketmobile.http.ApiData.HTTP_ID_info_manager_license_cosmetics;
import static com.zx.gamarketmobile.http.ApiData.HTTP_ID_info_manager_license_detail;
import static com.zx.gamarketmobile.http.ApiData.HTTP_ID_info_manager_license_drugs;
import static com.zx.gamarketmobile.http.ApiData.HTTP_ID_info_manager_license_food;
import static com.zx.gamarketmobile.http.ApiData.HTTP_ID_info_manager_license_instrument;
import static com.zx.gamarketmobile.http.ApiData.HTTP_ID_info_manager_measuring_instruments_custom;
import static com.zx.gamarketmobile.http.ApiData.HTTP_ID_info_manager_measuring_instruments_detail;
import static com.zx.gamarketmobile.http.ApiData.HTTP_ID_info_manager_measuring_instruments_liebiao;

/**
 * Create By Xiangb On 2016/9/19
 * 功能：主界面
 */
public class InfoHomeActivity extends BaseActivity implements View.OnClickListener, LoadMoreListener, MyItemClickListener {
    private static String TAG = "HomeActivity";
    private RecyclerView mInfoManagerRecyclerView;
    private SwipeRefreshLayout srlTodo;
    private MyRecycleAdapter mAdapter;
    private List<InfoManagerBiaozhun.RowsBean> dataList = new ArrayList<>();
    private List<InfoManagerDevice.RowsBean> dataListDevice = new ArrayList<>();

    private int mPageSize = 2;
    public int mPageNo = 1;
    public int mTotalNo = 0;

    private int index;
    private SharedPreferences mSharePreferences;
    private EditText infoSearchEdit;
    private Spinner infoSpinner;

    public LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);

    private ApiData infoStandard = new ApiData(ApiData.HTTP_ID_info_manager_biaozhun);
    private ApiData infoDeviceLiebiao = new ApiData(ApiData.HTTP_ID_info_manager_device_liebiao);
    private ApiData infoDeviceDetail = new ApiData(HTTP_ID_info_manager_device_detail);
    private ApiData infoLicenseFood = new ApiData(HTTP_ID_info_manager_license_food);
    private ApiData infoLicenseDrug = new ApiData(HTTP_ID_info_manager_license_drugs);
    private ApiData infoLicenseCosmetics = new ApiData(HTTP_ID_info_manager_license_cosmetics);
    private ApiData infoLicenseInstrument = new ApiData(HTTP_ID_info_manager_license_instrument);
    private ApiData infoLicenseDetail = new ApiData(HTTP_ID_info_manager_license_detail);
    private ApiData infoLicenseMeasureInstrumentCustom = new ApiData(HTTP_ID_info_manager_measuring_instruments_custom);
    private ApiData infoLicenseMeasureInstrumentLiebiao = new ApiData(HTTP_ID_info_manager_measuring_instruments_liebiao);
    private ApiData infoLicenseMeasureInstrumentDetail = new ApiData(ApiData.HTTP_ID_info_manager_measuring_instruments_detail);
    private ApiData infoLegalQuery = new ApiData(HTTP_ID_info_manager_legal_query);
    private ApiData infoLegalSearch = new ApiData(ApiData.HTTP_ID_info_manager_legal_search);


//    public static final int HTTP_ID_info_manager_biaozhun = 301;//标准信息查询
//    public static final int HTTP_ID_info_manager_device_liebiao = 302;//特种设备-特种设备列表查询
//    public static final int HTTP_ID_info_manager_device_detail = 303;//特种设备-特种设备详情接口
//    public static final int HTTP_ID_info_manager_license_food = 304;//许可证-食品企业列表
//    public static final int HTTP_ID_info_manager_license_drugs = 305;//许可证-药品企业列表
//    public static final int HTTP_ID_info_manager_license_cosmetics = 306;//许可证-化妆品企业列表
//    public static final int HTTP_ID_info_manager_license_instrument = 307;//许可证-医疗器械企业列表
//    public static final int HTTP_ID_info_manager_license_detail = 308;//许可证-许可证详情
//    public static final int HTTP_ID_info_manager_measuring_instruments_custom = 310;//计量器具-自定义表信息接口
//    public static final int HTTP_ID_info_manager_measuring_instruments_liebiao = 311;//计量器具-计量器具列表接口
//    public static final int HTTP_ID_info_manager_measuring_instruments_detail = 312;//计量器具-计量器具详情
//    public static final int HTTP_ID_info_manager_legal_query = 313;//法律法规-查询菜单接口
//    public static final int HTTP_ID_info_manager_legal_search = 314;//法律法规-法律法规搜索接口


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        addToolBar(true);
        showMidPic();

        getRightImg().setOnClickListener(this);

        index = getIntent().getIntExtra("item", 0);
        initialViewPager();
        findViewById(R.id.info_case_search).setOnClickListener(this);
        infoSpinner = findViewById(R.id.info_spinner);
        infoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });
        infoSearchEdit = findViewById(R.id.llInfoHome_bottom_edit);
        findViewById(R.id.info_case_search).setOnClickListener(this);

        mInfoManagerRecyclerView = (RecyclerView) findViewById(R.id.rv_normal_view);
        mInfoManagerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initialViewPager();
        srlTodo = (SwipeRefreshLayout) findViewById(R.id.srl_normal_layout);
        srlTodo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mPageNo > 1) {
                    mPageNo--;
                }
                loadData(index);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int ii = getIntent().getIntExtra("item", 0);
        if (index != ii)
            initialViewPager();
        else {

        }
        if (index == 1)
            loadData(index);
    }

    public void loadData(int index, final Object... objects) {
        switch (index) {
            case 0:
                infoStandard.loadData(objects);
                break;
            case 1:
                infoDeviceLiebiao.loadData(1, 2);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;


        }
    }

    public void initialViewPager() {
        switch (index) {
            case 0://标准信息
                setMidText("标准信息");
                mAdapter = new InfoManagerStandardAdapter(this, dataList, true);
                mAdapter.setOnLoadMoreListener(this);
                mInfoManagerRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(this);
                break;
            case 1://法律法规
                setMidText("法律法规");
                break;
            case 2://许可认证
                break;
            case 3://计量器件

                break;

            case 4://特种设备
                mAdapter = new InfoManagerDeviceAdapter(this, dataListDevice, true);
                mAdapter.setOnLoadMoreListener(this);
                mInfoManagerRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(this);
                break;

            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_case_search:
                loadData(index, infoSearchEdit.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    private void toActivity(Class<? extends Activity> c) {
        Intent intent = new Intent(InfoHomeActivity.this, c);
        startActivity(intent);
        Util.activity_In(this);
    }

    //加载更多
    @Override
    public void LoadMore() {
        if (mPageNo * mPageSize < mTotalNo) {
            mPageNo++;
            mAdapter.setStatus(1, mPageNo, mTotalNo);
            loadData(index);
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        srlTodo.setRefreshing(false);
        switch (id) {
            case HTTP_ID_info_manager_biaozhun:
                InfoManagerBiaozhun myTaskListEntity = (InfoManagerBiaozhun) b.getEntry();
                mTotalNo = myTaskListEntity.getTotal();
                mAdapter.setStatus(0, mPageNo, mTotalNo);
                List<InfoManagerBiaozhun.RowsBean> entityList = myTaskListEntity.getList();
                dataList.clear();
                if (entityList != null) {
                    dataList.addAll(entityList);
                }
                mAdapter.notifyDataSetChanged();

                InfoManagerBiaozhun.RowsBean bean = null;
                for (int j = 0; j < dataList.size(); j++) {
                    bean = dataList.get(j);
                    Log.i(TAG, "bean is " + bean.toString());
                }
                break;

            case HTTP_ID_info_manager_device_liebiao:
                break;

            case HTTP_ID_info_manager_device_detail:
                break;

            case HTTP_ID_info_manager_license_food:
                break;

            case HTTP_ID_info_manager_license_drugs:
                break;
            case HTTP_ID_info_manager_license_cosmetics:
                break;
            case HTTP_ID_info_manager_license_instrument:
                break;
            case HTTP_ID_info_manager_license_detail:
                break;
            case HTTP_ID_info_manager_measuring_instruments_custom:
                break;
            case HTTP_ID_info_manager_measuring_instruments_liebiao:
                break;
            case HTTP_ID_info_manager_measuring_instruments_detail:
                break;
            case HTTP_ID_info_manager_legal_query:
                break;
            case HTTP_ID_info_manager_legal_search:
                break;


            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (index) {
            case 1:
//                Intent intent = new Intent(this, SuperviseMyTaskDetailActivity.class);
//                intent.putExtra("entity", dataList.get(position));
//                intent.putExtra("index", index);
//                intent.putExtra("type", 0);
//                startActivity(intent);
                break;
        }

    }
}

