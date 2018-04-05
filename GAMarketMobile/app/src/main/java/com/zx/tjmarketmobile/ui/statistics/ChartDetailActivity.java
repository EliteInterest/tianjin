package com.zx.tjmarketmobile.ui.statistics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.ChartDetailInfoAdapter;
import com.zx.tjmarketmobile.entity.KeyValueInfo;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2017/5/19
 * 功能：统计详情
 */
public class ChartDetailActivity extends BaseActivity {

    private RecyclerView rvChart;
    private ChartDetailInfoAdapter mAdapter;
    private static String mItemName = "";
    private static JSONObject charJson = null;
    private List<KeyValueInfo> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_swipe_recycler_view);
        addToolBar(true);
        hideRightImg();
        setMidText("详情");
        rvChart = (RecyclerView) findViewById(R.id.rv_normal_view);
        findViewById(R.id.srl_normal_layout).setEnabled(false);
        rvChart.setLayoutManager(mLinearLayoutManager);
        mAdapter = new ChartDetailInfoAdapter(this, dataList);
        rvChart.setAdapter(mAdapter);
        setDataList();
    }

    public static void starAction(Context context, String itemName, JSONObject dataInfo) {
        mItemName = itemName;
        charJson = dataInfo;
        Intent intent = new Intent(context, ChartDetailActivity.class);
        context.startActivity(intent);
    }

    /**
     * 设置参数
     */
    private void setDataList() {
        if (TextUtils.isEmpty(mItemName) || charJson == null) {
            showToast("暂无具体数据！");
            finish();
            return;
        }
        switch (mItemName) {
            case "食品经营":
                addValue("主体名称", "fEntityName");
                addValue("许可证号", "fLicNum");
                addValue("法定代表人", "fLegalPerson");
                addValue("社会信用代码", "fUniscid");
                addValue("移动电话", "fMobilePhone");
                addValue("经营场所", "fAddress");
                addValue("经营项目", "fManageItem");
                addValue("主体业态", "fEntityOperateType");
                addValue("餐馆规模", "fScale");
                addValue("发证机关", "fOrg");
                addValue("发证日期", "fRegDate");
                addValue("有效期至", "fExpireDate");
                addValue("许可状态", "fStatus");
                break;
            case "食品生产":
                addValue("主体名称", "fEntityName");
                addValue("许可证号", "fLicNum");
                addValue("发证日期", "fRegDate");
                addValue("有效期至", "fExpireDate");
                addValue("静态风险等级", "fRiskLevel");
                addValue("区域", "F_STATION");
                addValue("生产地址", "fAddress");
                addValue("静态风险分值", "fRiskScore");
                addValue("产品名称", "fProduct");
                addValue("联系电话", "fTel");
                addValue("联系人", "fContact");
                break;
            case "区级检查":
                addValue("主体名称", "fEntityName");
                addValue("生产日期", "fSamplingDate");
                addValue("规格", "fSpecifications");
                addValue("生产日期", "fProductionDate");
                addValue("抽样日期", "fSampleName");
                addValue("综合判定", "fResult");
                addValue("监测场所", "fCheckPlace");
                addValue("所属区域", "fStation");
                addValue("委托登记号", "fRegistNum");
                addValue("标称商标", "fNominalMark");
                addValue("标称生产企业", "fNominalEntity");
                addValue("监测地点", "fCheckSite");
                break;
            case "药品生产":
            case "药品经营":
                addValue("主体名称", "fEntityName");
                addValue("许可证号", "fLicNum");
                addValue("法定代表人", "fLegalPerson");
                addValue("发证日期", "fRegDate");
                addValue("有效期至", "fExpireDate");
                addValue("质量负责人", "fQualityManager");
                addValue("生产类型", "fProductType");
                addValue("所属区域", "fStation");
                addValue("仓房地址", "fWarehouseAddr");
                addValue("认证编号", "fGmpNum");
                addValue("认证日期", "fGmpRegDate");
                addValue("认证时间", "fGmpEffectiveTime");
                addValue("企业负责人", "fManager");
                break;
            case "器械经营":
                addValue("主体名称", "fEntityName");
                addValue("法定代表人", "fLegalPerson");
                addValue("企业负责人", "fManager");
                addValue("经营范围", "fScope");
                addValue("有效期", "fExpireDate");
                addValue("统一社会信用代码", "fUniscid");
                addValue("所属区域", "fStation");
                break;
            case "器械生产":
                addValue("主体名称", "fEntityName");
                addValue("许可证号", "fLicNum");
                addValue("法定代表人", "fLegalPerson");
                addValue("发证日期", "fRegDate");
                addValue("有效期至", "fExpireDate");
                addValue("生产类型", "fProductType");
                addValue("注册地址", "fRegAddr");
                addValue("管理地址", "fManageAddr");
                addValue("主要生产类型", "fMainProductType");
                addValue("硬件仓库地址", "fWarehouseAddr");
                addValue("企业负责人", "fManager");
                break;
            case "药品检查":
                addValue("被抽样单位", "fEntityName");
                addValue("样品类别", "fSampleCategory");
                addValue("抽样地点", "fSamplingSite");
                addValue("抽样日期", "fSamplingDate");
                addValue("生产日期", "fProduceDate");
                addValue("样品名称", "fSampleName");
                addValue("检验结论", "fResult");
                addValue("生产批号", "fBatchNum");
                addValue("抽样单位", "fSamplingUnit");
                addValue("检验单位", "fCheckUnit");
                addValue("所属区域", "fStation");
                addValue("批准文号", "fApprovalNum");
                addValue("抽样数量", "fSamplingCount");
                break;
            case "化妆品":
                addValue("主体名称", "fEntityName");
                addValue("负责人", "fEntityPerson");
                addValue("美容/美发", "fType");
                addValue("主体地址", "fAddress");
                addValue("联系电话", "fContactPhone");
                addValue("所属区域", "fStation");
                break;
            case "设备结构":
            case "压力容器":
            case "电梯数量":
            case "锅炉":
            case "起重机械":
                addValue("主体名称", "fEntityName");
                addValue("设备种类", "fCategory");
                addValue("设备类别", "fType");
                addValue("设备型号", "fDeviceModel");
                addValue("登记时间", "F_REG_DATE");
                addValue("下次检查日期", "F_NEXT_CHECK_DATE");
                addValue("设备状态", "fStatus");
                break;
            case "工业产品许可":
                addValue("主体名称", "fEntityName");
                addValue("法定代表人", "fLegalPerson");
                addValue("产品类型", "fProduceType");
                addValue("类型", "fType");
                addValue("登记", "fSupervisionLevel");
                addValue("执照号", "fLicenseNum");
                addValue("所属区域", "fStation");
                addValue("地址", "fProAddress");
                addValue("产品名称", "fProduceName");
                addValue("联系电话", "fTel");
                break;
            case "工业产品检查":
                addValue("主体名称", "fEntityName");
                break;
            case "强制性认证":
                addValue("主体名称", "fEntityName");
                addValue("许可证号", "fLicNum");
                addValue("产品类型", "fType");
                addValue("发证日期", "fRegDate");
                addValue("到期日期", "fMaturityDate");
                addValue("地址", "fAddress");
                addValue("所属辖区", "fStation");
                break;
            case "分类监督":
                addValue("主体名称", "fEntityName");
                addValue("法人名称", "fLegalPerson");
                addValue("区域", "fStation");
                addValue("产品名称", "fProduceName");
                addValue("规格型号", "fSpecifications");
                addValue("工业产品生产许可证证号", "fLicenseNum");
                addValue("联系电话", "fLegalPhone");
                addValue("生产地址", "fProAddress");
                break;
            case "自愿性认证":
                addValue("主体名称", "fEntityName");
                addValue("许可证号", "fLicNum");
                addValue("认证类型", "fType");
                addValue("证书项目", "fProject");
                addValue("发证日期", "fRegDate");
                addValue("到期日期", "fMaturityDate");
                addValue("证书使用的认可标识", "fMark");
                addValue("证书状态", "fStatus");
                addValue("所属辖区", "fStation");
                break;
            case "计量器具":
                addValue("主体名称", "fEntityName");
                addValue("许可证号", "fLicNum");
                addValue("使用名称", "fUseName");
                addValue("所属区域", "fStation");
                addValue("用途", "fPurpose");
                addValue("状态", "fStatus");
                addValue("规格", "fSpecifications");
                addValue("出厂编号", "fRegCode");
                addValue("购置日期", "fDate");
                addValue("制造单位", "fCreateDepartment");
                addValue("检定单位", "fVerification");
                addValue("检定日期", "fVerDate");
                addValue("下次检定日期", "fNextVerDate");
                addValue("检定结论", "fResult");
                break;
            case "名牌":
                addValue("主体名称", "fEntityName");
                addValue("颁奖单位", "fOrg");
                addValue("所属区域", "fStation");
                addValue("获评年度", "F_ASSESMENT_YEAR");
                addValue("规格", "fSpecifications");
                addValue("名牌类型", "fBrandName");
                addValue("奖励", "fAwards");
                break;
            case "农资":
                addValue("主体名称", "fEntityName");
                addValue("主体类别", "fEntityType");
                addValue("统一社会信用代码", "fUniscid");
                addValue("营业执照注册号", "fBizlicNum");
                addValue("许可证", "fLicenses");
                addValue("法定代表人(负责人)", "fLegalPerson");
                addValue("注册地址", "fAddress");
                addValue("联系人姓名", "fContactPeople");
                addValue("联系人电话", "fContactPhone");
                addValue("联系人地址", "fContactAddress");
                break;
            default:
                break;
        }
    }

    /**
     * 添加list
     *
     * @param key   key
     * @param value value
     */
    private void addValue(String key, String value) {
        value = getStringValue(value);
        if (value.matches("[0-9]+") && value.length() > 11) {
            value = DateUtil.getDateFromMillis(Long.parseLong(value));
        }
        dataList.add(new KeyValueInfo(key, value));
    }

    // 获取String类型参数
    private String getStringValue(String name) {
        try {
            if (charJson.getString(name).equals("null")) {
                return "";
            } else {
                return charJson.getString(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
