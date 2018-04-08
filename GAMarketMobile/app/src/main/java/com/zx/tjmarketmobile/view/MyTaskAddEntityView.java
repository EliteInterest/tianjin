package com.zx.tjmarketmobile.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.esri.core.geometry.Point;
import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.supervise.SuperviseMyTaskAddEntityAdapter;
import com.zx.tjmarketmobile.entity.HttpLoginEntity;
import com.zx.tjmarketmobile.entity.supervise.MyTaskInfoEntity;
import com.zx.tjmarketmobile.entity.supervise.MyTaskListEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.http.BaseRequestData;
import com.zx.tjmarketmobile.listener.OnMapDialogListener;
import com.zx.tjmarketmobile.manager.UserManager;
import com.zx.tjmarketmobile.ui.supervise.mytask.SuperviseMyTaskCheckFragment;
import com.zx.tjmarketmobile.util.Util;
import com.zx.tjmarketmobile.util.ZXDialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2017/11/1.
 * 功能：
 */

public class MyTaskAddEntityView implements BaseRequestData.OnHttpLoadingListener<BaseHttpResult> {
    private int addPageSize = 10;
    public int addPageNo = 1;
    public int addTotalNo = 0;
    private SwipeRefreshLayout srlAdd;
    private RecyclerView rvEntity;
    private ImageButton ibSearch;
    private EditText etSearch;
    private ScrollView svAddNew;
    private EditText etNewName, etNewAddress;
    private TextView tvMap, tvLongitude, tvLatitude;
    private Button btnAddNew, btnCancelAdd;
    private SuperviseMyTaskAddEntityAdapter mAddAdapter;
    private List<MyTaskInfoEntity.RowsBean> mTaskList = new ArrayList<>();
    public Dialog dialog;
    private Spinner spinner;
    private List<String> departments = new ArrayList<>();
    private MapViewDialog mapViewDialog;

    private SuperviseMyTaskCheckFragment mFragment;
    private MyTaskListEntity mEntity;
    private HttpLoginEntity userInfo;
    private String userStation = "";

    private ApiData ztsearchData = new ApiData(ApiData.HTTP_ID_superviseQueryEntityByCondition);
    private ApiData addNewEntity = new ApiData(ApiData.HTTP_ID_superviseAppendEntity);

    public MyTaskAddEntityView(SuperviseMyTaskCheckFragment context, MyTaskListEntity mEntity) {
        this.mFragment = context;
        this.mEntity = mEntity;
        UserManager userManager = new UserManager();
        userInfo = userManager.getUser(context.getActivity());
    }

    public void show() {
        if (dialog != null) {
            etNewName.setText("");
            etNewAddress.setText("");
            spinner.setSelection(0);
            tvLatitude.setText("纬度");
            tvLongitude.setText("经度");
            dialog.show();
            return;
        }
        ztsearchData.setLoadingListener(this);
        addNewEntity.setLoadingListener(this);
        View view = LayoutInflater.from(mFragment.getActivity()).inflate(R.layout.layout_mytask_addentity, null, false);
        etSearch = view.findViewById(R.id.et_addentity);
        ibSearch = view.findViewById(R.id.ibtn_add_search);
        rvEntity = view.findViewById(R.id.rv_normal_view);
        srlAdd = view.findViewById(R.id.srl_normal_layout);
        svAddNew = view.findViewById(R.id.sv_add_new);
        etNewName = view.findViewById(R.id.et_new_entityName);
        etNewAddress = view.findViewById(R.id.et_new_entityAddress);
        btnAddNew = view.findViewById(R.id.btn_addNew);
        btnCancelAdd = view.findViewById(R.id.btn_cancleAdd);
        spinner = view.findViewById(R.id.sp_new_entityDepartment);
        tvLatitude = view.findViewById(R.id.et_addNew_latitude);
        tvLongitude = view.findViewById(R.id.et_addNew_longitude);
        tvMap = view.findViewById(R.id.tv_addNew_map);

        userStation = userInfo.getDepartmentAlias();
        if ("湖潮乡".equals(userStation) ||
                "高峰镇".equals(userStation) ||
                "党武镇".equals(userStation) ||
                "马场镇".equals(userStation)) {
            spinner.setVisibility(View.GONE);
        } else {
            userStation = "";
            spinner.setVisibility(View.VISIBLE);
        }

        departments.add("请选择所属乡镇");
        departments.add("湖潮乡");
        departments.add("高峰镇");
        departments.add("党武镇");
        departments.add("马场镇");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mFragment.getActivity(), R.layout.spinner_item_layout, departments);
        spinner.setAdapter(adapter);

        mAddAdapter = new SuperviseMyTaskAddEntityAdapter(mTaskList);
        rvEntity.setAdapter(mAddAdapter);
        rvEntity.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
        srlAdd.setOnRefreshListener(() -> {
            if (addPageNo > 1) {
                addPageNo--;
            }
            searchEntity();
        });
        //加载更多
        mAddAdapter.setOnLoadMoreListener(() -> {
            if (addPageNo * addPageSize < addTotalNo) {
                addPageNo++;
                mAddAdapter.setStatus(1, addPageNo, addTotalNo);
                searchEntity();
            }
        });
        //item点击事件
        mAddAdapter.setOnItemClickListener((view1, position) -> showYesNo(position));
        //搜索
        ibSearch.setOnClickListener(view12 -> searchEntity());
        //软键盘搜索按钮
        etSearch.setOnEditorActionListener((arg0, arg1, arg2) -> {
            if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                searchEntity();
            }
            return false;
        });
        //打开搜索
        dialog = ZXDialogUtil.showCustomViewDialog(mFragment.getActivity(), "添加主体", view, (dialogInterface, i) -> {
            if (svAddNew.isShown()) {
                svAddNew.setVisibility(View.GONE);
                etSearch.setEnabled(true);
                ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE).setText("手动录入");
            } else {
                svAddNew.setVisibility(View.VISIBLE);
                etSearch.setEnabled(false);
                etSearch.setText("");
                spinner.setSelection(0);
                searchEntity();
                ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE).setText("主体搜索");
            }
        }, "手动录入", (dialogInterface, i) -> {
            mFragment.loadData();
            dialog.dismiss();
        }, "取消", false);
        dialog.getWindow().setDimAmount(0);
        //取消录入
        btnCancelAdd.setOnClickListener(view13 -> {
            svAddNew.setVisibility(View.GONE);
            etSearch.setEnabled(true);
            ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE).setText("手动录入");
        });
        //确定录入
        btnAddNew.setOnClickListener(view14 -> {
            boolean readyAdd = true;
            if (etNewName.getText().toString().trim().length() == 0) {
                mFragment.showToast("名称不能为空");
                readyAdd = false;
            }
            if (etNewAddress.getText().toString().trim().length() == 0) {
                mFragment.showToast("地址不能为空");
                readyAdd = false;
            }
            if (spinner.getVisibility() == View.VISIBLE && spinner.getSelectedItemPosition() == 0) {
                mFragment.showToast("请选择所属乡镇");
                readyAdd = false;
            }
            if (tvLongitude.getText().toString().length() == 0 || "经度".equals(tvLongitude.getText().toString())) {//判别经纬度提示文字
                mFragment.showToast("请填入坐标经度");
                readyAdd = false;
            }
            if (tvLatitude.getText().toString().length() == 0 || "纬度".equals(tvLatitude.getText().toString())) {//判别经纬度提示文字
                mFragment.showToast("请填入坐标纬度");
                readyAdd = false;
            }
            if (readyAdd) {
                showYesNo(-1);
            }
        });
        //地图选点
        tvMap.setOnClickListener((View v) -> {
            if (mapViewDialog == null) {
                mapViewDialog = new MapViewDialog(mFragment.getActivity(), MapViewDialog.MapType.selectPoint, new OnMapDialogListener() {
                    @Override
                    public void selectPoint(Point selectPoint) {
                        tvLongitude.setText(selectPoint.getX() + "");
                        tvLatitude.setText(selectPoint.getY() + "");
                    }

                    @Override
                    public void onHide() {
                        dialog.show();
                    }
                });
            }
            dialog.hide();
            mapViewDialog.show();
        });
        searchEntity();
    }

    private void showYesNo(final int position) {
        ZXDialogUtil.showYesNoDialog(mFragment.getActivity(), "提示", "是否添加该主体", (dialogInterface, i) -> {
            if (position < 0) {
                String department = userInfo.getDepartmentAlias();
                if (spinner.getVisibility() == View.VISIBLE) {
                    department = departments.get(spinner.getSelectedItemPosition());
                }
                String latitude = tvLatitude.getText().toString().length() > 17 ? tvLatitude.getText().toString().substring(0, 17) : tvLatitude.getText().toString();
                String longitude = tvLongitude.getText().toString().length() > 17 ? tvLongitude.getText().toString().substring(0, 17) : tvLongitude.getText().toString();
//                addNewEntity.loadData(mEntity.getFTaskId(), etNewName.getText().toString().trim(), mEntity.getF_GUID(),
                addNewEntity.loadData(mEntity.getId(), etNewName.getText().toString().trim(), mEntity.getUserId(),
                        etNewAddress.getText().toString().trim(), department, "", userInfo.getId(), latitude, longitude);
            } else {
//                addNewEntity.loadData(mEntity.getFTaskId(), mTaskList.get(position).getFEntityName(), mEntity.getF_GUID(), "", "", mTaskList.get(position).getFEntityGuid(), userInfo.getId(), "", "");?
                addNewEntity.loadData(mEntity.getId(), mTaskList.get(position).getFEntityName(), mEntity.getUserId(), "", "", mTaskList.get(position).getFEntityGuid(), userInfo.getId(), "", "");
            }
        });
    }

    private void searchEntity() {
        Util.closeKeybord(etSearch, mFragment.getActivity());
        ztsearchData.loadData(addPageNo, "10", 0, etSearch.getText().toString(), userStation);
    }

    @Override
    public void onLoadStart(int id) {
        if (id != ApiData.HTTP_ID_superviseQueryEntityByCondition) {
            mFragment.showProgressDialog("正在加载中，请稍后");
        }
    }

    @Override
    public void onLoadError(int id, boolean isAPIError, String error_message) {
        mFragment.dismissProgressDialog();
        mFragment.showToast(error_message);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        mFragment.dismissProgressDialog();
        switch (id) {
            case ApiData.HTTP_ID_superviseQueryEntityByCondition:
                srlAdd.setRefreshing(false);
                MyTaskInfoEntity mSearchZtEntity = (MyTaskInfoEntity) b.getEntry();
                addTotalNo = mSearchZtEntity.getTotal();
                mTaskList.clear();
                mTaskList.addAll(mSearchZtEntity.getRows());
                mAddAdapter.notifyDataSetChanged();
                rvEntity.smoothScrollToPosition(0);
                mAddAdapter.setStatus(0, addPageNo, addTotalNo);
                break;
            case ApiData.HTTP_ID_superviseAppendEntity:
                searchEntity();
                mFragment.showToast(b.getMessage());
                svAddNew.setVisibility(View.GONE);
                ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE).setText("手动录入");
                etNewName.setText("");
                etNewAddress.setText("");
                tvLatitude.setText("纬度");
                tvLongitude.setText("经度");
                break;
            default:
                break;
        }

    }

    @Override
    public void onLoadPregress(int id, int progress) {

    }
}
