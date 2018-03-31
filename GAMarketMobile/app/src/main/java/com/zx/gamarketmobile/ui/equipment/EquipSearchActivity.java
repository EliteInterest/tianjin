package com.zx.gamarketmobile.ui.equipment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.adapter.EquipListAdapter;
import com.zx.gamarketmobile.entity.NormalListEntity;
import com.zx.gamarketmobile.entity.supervise.SuperviseEquimentEntity;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.http.BaseHttpResult;
import com.zx.gamarketmobile.listener.LoadMoreListener;
import com.zx.gamarketmobile.listener.MyItemClickListener;
import com.zx.gamarketmobile.ui.base.BaseActivity;
import com.zx.gamarketmobile.ui.supervise.mytask.SuperviseEquipmentDetailActivity;
import com.zx.gamarketmobile.util.Util;

import java.util.ArrayList;
import java.util.List;

public class EquipSearchActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, MyItemClickListener, LoadMoreListener {

    private LinearLayout llSearchView;//搜索界面
    private LinearLayout llSwitch;//开关
    private SwipeRefreshLayout srlLayout;//刷新界面
    private RecyclerView rvContent;//列表
    private EditText etSydw, etCcbh, etSbdz, etWbdw;
    private Spinner spSblb, spSyzt, spQy;
    private TextView tvQsrq, tvJzrq;
    private Button btnSearch;//查询
    private ImageView ivSearch;//开关图片
    private TextView tvSearch;//开关文字

    private int pageSize = 10;
    private int pageNo = 1;
    private int totalNo = 0;

    private EquipListAdapter mAdapter;

    private List<SuperviseEquimentEntity> dataList = new ArrayList<>();
    private List<String> sblxList = new ArrayList<>();
    private List<String> qyList = new ArrayList<>();
    private List<String> syztList = new ArrayList<>();

    private ApiData getEquipList = new ApiData(ApiData.HTTP_ID_equipSearchList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equip_search);

        addToolBar(true);
        showMidPic();
        hideRightImg();
        setMidText("特种设备");

        initView();

    }

    private void initView() {
        llSearchView = findViewById(R.id.ll_equip_searchview);
        llSwitch = findViewById(R.id.ll_equip_switch);
        srlLayout = findViewById(R.id.srl_equip);
        rvContent = findViewById(R.id.rv_equip);
        etSydw = findViewById(R.id.et_equip_sydw);
        etCcbh = findViewById(R.id.et_equip_ccbh);
        etSbdz = findViewById(R.id.et_equip_sbdz);
        etWbdw = findViewById(R.id.et_equip_wbdw);
        spSblb = findViewById(R.id.sp_equip_sblb);
        spSyzt = findViewById(R.id.sp_equip_syzt);
        spQy = findViewById(R.id.sp_equip_qy);
        tvQsrq = findViewById(R.id.tv_equip_qsrq);
        tvJzrq = findViewById(R.id.tv_equip_jzrq);
        btnSearch = findViewById(R.id.btn_equip_search);
        ivSearch = findViewById(R.id.iv_equip_switch_img);
        tvSearch = findViewById(R.id.tv_equip_switch_text);

        btnSearch.setOnClickListener(this);
        llSwitch.setOnClickListener(this);
        tvJzrq.setOnClickListener(this);
        tvQsrq.setOnClickListener(this);
        getEquipList.setLoadingListener(this);

        sblxList.add("全部");
        sblxList.add("电梯");
        sblxList.add("锅炉");
        sblxList.add("客运索道");
        sblxList.add("厂内车辆");
        sblxList.add("压力容器");
        sblxList.add("起重器械");
        sblxList.add("压力管道");
        sblxList.add("游乐设施");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.spinner_item_layout, sblxList);
        spSblb.setAdapter(adapter1);

        qyList.add("全部");
        qyList.add("湖潮乡");
        qyList.add("高峰镇");
        qyList.add("党武镇");
        qyList.add("马场镇");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_item_layout, qyList);
        spQy.setAdapter(adapter2);

        syztList.add("全部");
        syztList.add("节能淘汰");
        syztList.add("在用");
        syztList.add("停用");
        syztList.add("停用整改");
        syztList.add("拆除");
        syztList.add("安装告知");
        syztList.add("改造");
        syztList.add("报废");
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.spinner_item_layout, syztList);
        spSyzt.setAdapter(adapter3);

        srlLayout.setOnRefreshListener(this);
        mAdapter = new EquipListAdapter(this, dataList);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_equip_search://查询
                pageNo = 1;
                loadData();
                doAnim();
                break;
            case R.id.ll_equip_switch://开关
                doAnim();
                break;
            case R.id.tv_equip_qsrq://起始日期
                Util.setDateIntoText(this, tvQsrq, "-");
                break;
            case R.id.tv_equip_jzrq://截止日期
                Util.setDateIntoText(this, tvJzrq, "-");
                break;

            default:
                break;
        }
    }

    private void loadData() {
        String sydw = etSydw.getText().toString().trim();
        String sblb = spSblb.getSelectedItemPosition() == 0 ? "" : sblxList.get(spSblb.getSelectedItemPosition());
        String ccbh = etCcbh.getText().toString().trim();
        String syzt = spSyzt.getSelectedItemPosition() == 0 ? "" : syztList.get(spSyzt.getSelectedItemPosition());
        String sbdz = etSbdz.getText().toString().trim();
        String qsrq = tvQsrq.getText().toString().trim();
        String jzrq = tvJzrq.getText().toString().trim();
        String qy = spQy.getSelectedItemPosition() == 0 ? "" : qyList.get(spQy.getSelectedItemPosition());
        String wbdw = etWbdw.getText().toString().trim();
        getEquipList.loadData(pageNo, pageSize, sydw, sblb, ccbh, syzt, sbdz, qsrq, jzrq, qy, wbdw);
    }

    //执行动画
    private void doAnim() {
        if (llSearchView.getVisibility() == View.VISIBLE) {
            llSearchView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.search_close));
            llSearchView.setVisibility(View.GONE);
            srlLayout.setVisibility(View.VISIBLE);
            srlLayout.startAnimation(AnimationUtils.loadAnimation(this, R.anim.search_open));
            ivSearch.setBackground(ContextCompat.getDrawable(this, R.mipmap.equip_open));
            tvSearch.setText("展开搜索栏");
        } else {
            srlLayout.startAnimation(AnimationUtils.loadAnimation(this, R.anim.search_close));
            srlLayout.setVisibility(View.GONE);
            llSearchView.setVisibility(View.VISIBLE);
            llSearchView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.search_open));
            ivSearch.setBackground(ContextCompat.getDrawable(this, R.mipmap.equip_close));
            tvSearch.setText("收起搜索栏");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //及时清理动画资源
        llSearchView.clearAnimation();
        srlLayout.clearAnimation();
    }

    @Override
    public void onRefresh() {
        if (pageNo > 1) {
            pageNo--;
        }
        loadData();
    }

    @Override
    public void LoadMore() {
        if (pageNo * pageSize < totalNo) {
            pageNo++;
            mAdapter.setStatus(1, pageNo, totalNo);
            loadData();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        SuperviseEquipmentDetailActivity.startAction(this, dataList.get(position));
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult baseHttpResult) {
        super.onLoadComplete(id, baseHttpResult);
        switch (id) {
            case ApiData.HTTP_ID_equipSearchList:
                srlLayout.setRefreshing(false);
                NormalListEntity normalListEntity = (NormalListEntity) baseHttpResult.getEntry();
                totalNo = normalListEntity.getTotal();
                mAdapter.setStatus(0, pageNo, totalNo);
                List<SuperviseEquimentEntity> list = normalListEntity.getEquimentEntityList();
                dataList.clear();
                dataList.addAll(list);
                mAdapter.notifyDataSetChanged();
                rvContent.scrollToPosition(0);
                break;

            default:
                break;
        }
    }

}
