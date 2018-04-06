package com.zx.tjmarketmobile.ui.infomanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.CaseCompDetailInfoAdapter;
import com.zx.tjmarketmobile.entity.KeyValueInfo;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerBiaozhun;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerDevice;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerDeviceDetail;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.manager.UserManager;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzq on 2017/3/23.
 * 特种设备详情
 */

public class DeviceListBaseInfoFragment extends BaseFragment {

    private CaseCompDetailInfoAdapter mCaseAdapter;
    private String fId = "";
    private InfoManagerDevice.RowsBean mEntity;
    private RecyclerView rvBaseInfo;
    private ApiData getTaskBaseInfo = new ApiData(ApiData.HTTP_ID_info_manager_device_detail);

    private List<KeyValueInfo> dataInfoList = new ArrayList<>();

    public static DeviceListBaseInfoFragment newInstance(Context context, InfoManagerDevice.RowsBean mEntity) {
        DeviceListBaseInfoFragment details = new DeviceListBaseInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mEntity", mEntity);
        details.mEntity = mEntity;
        UserManager userManager = new UserManager();
        details.userInfo = userManager.getUser(context);
        details.setArguments(bundle);
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_mytask_baseinfo, container, false);
        mEntity = (InfoManagerDevice.RowsBean) getArguments().getSerializable("mEntity");

        rvBaseInfo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        rvBaseInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        getTaskBaseInfo.setLoadingListener(this);
        mCaseAdapter = new CaseCompDetailInfoAdapter(getActivity(), dataInfoList);
        rvBaseInfo.setAdapter(mCaseAdapter);
        return view;
    }

    //当界面可见之后再进行数据加载（懒加载）
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mEntity != null) {
            if (isVisibleToUser && dataInfoList.size() == 0) {
//                fId = mEntity.getFTaskId();
                fId = mEntity.getId();
//                getTaskBaseInfo.loadData(mEntity.getF_GUID(), mEntity.getFTaskStatus(), fId, userInfo.getId());
                getTaskBaseInfo.loadData(fId);
            }
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_info_manager_device_detail:
                InfoManagerDeviceDetail myTaskBaseInfo = (InfoManagerDeviceDetail) b.getEntry();
                getDataList(myTaskBaseInfo);
                mCaseAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private void getDataList(InfoManagerDeviceDetail myTaskBaseInfo) {
        dataInfoList.clear();
        KeyValueInfo info = new KeyValueInfo("设备编号: ", myTaskBaseInfo.getId());
        dataInfoList.add(info);
        info = new KeyValueInfo("使用企业id: ", myTaskBaseInfo.getEnterpriseId());
        dataInfoList.add(info);
        info = new KeyValueInfo("使用企业名称: ", myTaskBaseInfo.getEnterpriseName());
        dataInfoList.add(info);
        info = new KeyValueInfo("设备注册代码: ", myTaskBaseInfo.getRegisterCode());
        dataInfoList.add(info);
        info = new KeyValueInfo("维护日期: ", DateUtil.getDateTimeFromMillis(myTaskBaseInfo.getMaintenanceDate()));
        dataInfoList.add(info);
        info = new KeyValueInfo("换证日期: ", DateUtil.getDateTimeFromMillis(myTaskBaseInfo.getReplaceDate()));
        dataInfoList.add(info);
        info = new KeyValueInfo("注册日期: ", DateUtil.getDateTimeFromMillis(myTaskBaseInfo.getRegisterDate()));
        dataInfoList.add(info);
        info = new KeyValueInfo("设备类别: ", myTaskBaseInfo.getName());
        dataInfoList.add(info);
        info = new KeyValueInfo("设备类别名称: ", myTaskBaseInfo.getTypeId());
        dataInfoList.add(info);
        info = new KeyValueInfo("设备品种名称: ", myTaskBaseInfo.getCategoryName());
        dataInfoList.add(info);
        info = new KeyValueInfo("设备品种: ", myTaskBaseInfo.getCategoryId());
        dataInfoList.add(info);
        info = new KeyValueInfo("设备使用状态: ", myTaskBaseInfo.getStatus());
        dataInfoList.add(info);
        info = new KeyValueInfo("出厂编号: ", myTaskBaseInfo.getOriginNum());
        dataInfoList.add(info);
        info = new KeyValueInfo("设备代码: ", myTaskBaseInfo.getCode());
        dataInfoList.add(info);
        info = new KeyValueInfo("设备级别: ", myTaskBaseInfo.getLevel());
        dataInfoList.add(info);
        info = new KeyValueInfo("设备型号: ", myTaskBaseInfo.getModel());
        dataInfoList.add(info);
        info = new KeyValueInfo("单位内部编号: ", myTaskBaseInfo.getUnitNum());
        dataInfoList.add(info);
        info = new KeyValueInfo("制造单位: ", myTaskBaseInfo.getMakeCompany());
        dataInfoList.add(info);
        info = new KeyValueInfo("设备所在地点: ", myTaskBaseInfo.getAddress());
        dataInfoList.add(info);
        info = new KeyValueInfo("安全管理人员: ", myTaskBaseInfo.getManagePerson());
        dataInfoList.add(info);
        info = new KeyValueInfo("安全管理员联系电话: ", myTaskBaseInfo.getMaintenancePhone());
        dataInfoList.add(info);
        info = new KeyValueInfo("使用证编号: ", myTaskBaseInfo.getUseNum());
        dataInfoList.add(info);
        info = new KeyValueInfo("注册登记机构: ", myTaskBaseInfo.getRegisterCompany());
        dataInfoList.add(info);
        info = new KeyValueInfo("检验单位: ", myTaskBaseInfo.getCheckCompany());
        dataInfoList.add(info);
        info = new KeyValueInfo("检验类别: ", myTaskBaseInfo.getCheckType());
        dataInfoList.add(info);
        info = new KeyValueInfo("检验结论: ", myTaskBaseInfo.getCheckResult());
        dataInfoList.add(info);
        info = new KeyValueInfo("检验报告书编号: ", myTaskBaseInfo.getCheckResultNum());
        dataInfoList.add(info);
        info = new KeyValueInfo("注册状态: ", myTaskBaseInfo.getRegisterStatus());
        dataInfoList.add(info);
        info = new KeyValueInfo("检验员姓名: ", myTaskBaseInfo.getCheckUser());
        dataInfoList.add(info);
        info = new KeyValueInfo("检验日期: ", DateUtil.getDateTimeFromMillis(myTaskBaseInfo.getCheckDate()));
        dataInfoList.add(info);
        info = new KeyValueInfo("下次检验日期: ", DateUtil.getDateTimeFromMillis(myTaskBaseInfo.getNextCheckDate()));
        dataInfoList.add(info);
        info = new KeyValueInfo("检制造日期: ", DateUtil.getDateTimeFromMillis(myTaskBaseInfo.getMakeDate()));
        dataInfoList.add(info);
        info = new KeyValueInfo("报停日期: ", DateUtil.getDateTimeFromMillis(myTaskBaseInfo.getStopDate()));
        dataInfoList.add(info);
        info = new KeyValueInfo("主管负责人: ", myTaskBaseInfo.getDirectorPerson());
        dataInfoList.add(info);
        info = new KeyValueInfo("主管负责人电话: ", myTaskBaseInfo.getDirectorPhone());
        dataInfoList.add(info);
        info = new KeyValueInfo("部门负责人: ", myTaskBaseInfo.getDepartmentPerson());
        dataInfoList.add(info);
        info = new KeyValueInfo("部门负责人电话: ", myTaskBaseInfo.getDepartmentPhone());
        dataInfoList.add(info);
        info = new KeyValueInfo("维修保养单位: ", myTaskBaseInfo.getMaintenanceCompany());
        dataInfoList.add(info);
        info = new KeyValueInfo("维保电话: ", myTaskBaseInfo.getMaintenancePhone());
        dataInfoList.add(info);
        info = new KeyValueInfo("经度: ", String.valueOf(myTaskBaseInfo.getLongitude()));
        dataInfoList.add(info);
        info = new KeyValueInfo("维度: ", String.valueOf(myTaskBaseInfo.getLatitude()));
        dataInfoList.add(info);
        info = new KeyValueInfo("预约: ", String.valueOf(myTaskBaseInfo.getOrdered()));
        dataInfoList.add(info);
        info = new KeyValueInfo("newEquipmentDate: ", DateUtil.getDateTimeFromMillis(myTaskBaseInfo.getNewEquipmentDate()));
        dataInfoList.add(info);
        info = new KeyValueInfo("是否是重点: ",String.valueOf( myTaskBaseInfo.getIsMajor()));
        dataInfoList.add(info);
    }


}
