package com.zx.gamarketmobile.ui.synergy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.entity.EquipmentInfoEntity;
import com.zx.gamarketmobile.entity.KeyValueInfo;
import com.zx.gamarketmobile.entity.SynergyDTInfoEntity;
import com.zx.gamarketmobile.entity.SynergyDetailEntity;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.http.BaseHttpResult;
import com.zx.gamarketmobile.ui.base.BaseActivity;
import com.zx.gamarketmobile.util.Util;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.widget.MultiPickResultView;

public class SynergyDetailActivity extends BaseActivity {

    private String type;
    private boolean isRead = false;
    private String checkGuid;

    private LinearLayout llNormal;//简单表格
    private LinearLayout llCheck;//强制检定申请表格
    private LinearLayout llDt;//电梯表格
    private TextView tvDtTile;//电梯title
    private TextView tvDwbdwqk;//对维保单位情况
    private TextView tvGzjclqk;//故障及处理情况
    private ImageView ivDTImg;//电梯照片
    private LinearLayout llJfjqsb;//电梯机房及其设备
    private LinearLayout llJxjcj;//轿厢及层级
    private MultiPickResultView mprvSynergy;

    private ApiData getDetailInfo = new ApiData(ApiData.HTTP_ID_synergyGetDetailInfo);
    private ApiData getDTDetailInfo = new ApiData(ApiData.HTTP_ID_synergyGetDTDetailInfo);
    private ApiData getYJDetailInfo = new ApiData(ApiData.HTTP_ID_synergyGetYJDetailInfo);
    private ApiData getEquipmentDetail = new ApiData(ApiData.HTTP_ID_synergyGetEquipment1);

    private List<KeyValueInfo> normalList = new ArrayList<>();
    private ArrayList<String> photoPath = new ArrayList<>();

    public static void startAction(Context context, String type, boolean isRead, String checkGuid) {
        Intent starter = new Intent(context, SynergyDetailActivity.class);
        starter.putExtra("type", type);
        starter.putExtra("isRead", isRead);
        starter.putExtra("checkGuid", checkGuid);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synergy_detail);

        addToolBar(true);
        hideRightImg();
        setMidText("基本信息");

        type = getIntent().getStringExtra("type");
        isRead = getIntent().getBooleanExtra("isRead", false);
        checkGuid = getIntent().getStringExtra("checkGuid");

        llNormal = findViewById(R.id.ll_synergy_normal);
        llCheck = findViewById(R.id.ll_synergy_check);
        llDt = findViewById(R.id.ll_synergy_dt);
        tvDtTile = findViewById(R.id.tv_dt_title);
        tvDwbdwqk = findViewById(R.id.tv_dt_dwbdwqk);
        tvGzjclqk = findViewById(R.id.tv_dt_gzjclqk);
        ivDTImg = findViewById(R.id.iv_dt_img);
        llJfjqsb = findViewById(R.id.ll_dt_jfjqsb);
        llJxjcj = findViewById(R.id.ll_dt_jxjcz);
        mprvSynergy = findViewById(R.id.mprv_synergy);
        mprvSynergy.init(this, MultiPickResultView.ACTION_ONLY_SHOW, photoPath);

        getDetailInfo.setLoadingListener(this);
        getDTDetailInfo.setLoadingListener(this);
        getYJDetailInfo.setLoadingListener(this);
        getEquipmentDetail.setLoadingListener(this);

        //根据type选择加载数据
        if ("elevator".equals(type)) {
            getDTDetailInfo.loadData(type, userInfo.getId(), isRead ? "1" : "0", checkGuid);
        } else if ("hiddenDanger".equals(type)) {
            getYJDetailInfo.loadData(type, userInfo.getId(), isRead ? "1" : "0", checkGuid);
        } else {
            getDetailInfo.loadData(type, userInfo.getId(), isRead ? "1" : "0", checkGuid);
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult baseHttpResult) {
        super.onLoadComplete(id, baseHttpResult);
        switch (id) {
            case ApiData.HTTP_ID_synergyGetDetailInfo:
                SynergyDetailEntity mentity = (SynergyDetailEntity) baseHttpResult.getEntry();
                createNormalTable(mentity);
                break;
            case ApiData.HTTP_ID_synergyGetDTDetailInfo:
                SynergyDTInfoEntity dtEntity = (SynergyDTInfoEntity) baseHttpResult.getEntry();
                createDTTable(dtEntity);
                break;
            case ApiData.HTTP_ID_synergyGetYJDetailInfo:
                SynergyDetailEntity yjEntity = (SynergyDetailEntity) baseHttpResult.getEntry();
                createNormalTable(yjEntity);
                String itemGuid = yjEntity.getFItemGuid();
                getEquipmentDetail.loadData(itemGuid);
                break;
            case ApiData.HTTP_ID_synergyGetEquipment1:
                EquipmentInfoEntity equipEntity = (EquipmentInfoEntity) baseHttpResult.getEntry();
                if (equipEntity != null) {
                    createYJTable(equipEntity);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 预警信息
     *
     * @param mEntity
     */
    private void createYJTable(EquipmentInfoEntity mEntity) {
        int index = 0;
        normalList.add(index++, new KeyValueInfo("使用单位", mEntity.getEuser()));
        normalList.add(index++, new KeyValueInfo("设备种类", mEntity.getEtype()));
        normalList.add(index++, new KeyValueInfo("设备名称", mEntity.getElet()));
        normalList.add(index++, new KeyValueInfo("设备型号", mEntity.getEusemodel()));
        normalList.add(index++, new KeyValueInfo("使用地点", mEntity.getEaddress()));
        normalList.add(index++, new KeyValueInfo("检验日期", mEntity.getChkdate()));
        normalList.add(index++, new KeyValueInfo("下次检验日期", mEntity.getNchkdate()));
        normalList.add(index++, new KeyValueInfo("使用状态", mEntity.getUsestate()));
        normalList.add(index++, new KeyValueInfo("出厂编号", mEntity.getEnums()));
        normalList.add(index++, new KeyValueInfo("注册代码", mEntity.getInsreportno()));
        normalList.add(index++, new KeyValueInfo("注册登记日期", mEntity.getEbuilddate()));
        normalList.add(index++, new KeyValueInfo("行政区域", mEntity.getGcom()));
        normalList.add(index++, new KeyValueInfo("联系人", mEntity.getUseowner()));
        normalList.add(index++, new KeyValueInfo("联系人电话", mEntity.getUseownertel()));
        llNormal.removeAllViews();
        for (int i = 0; i < normalList.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_synergy_normal, llNormal, false);
            ((TextView) view.findViewById(R.id.tv_synergy_name)).setText(normalList.get(i).key);
            ((TextView) view.findViewById(R.id.tv_synergy_info)).setText(normalList.get(i).value);
            llNormal.addView(view);
        }
    }

    /**
     * 电梯列表
     *
     * @param dtEntity
     */
    private void createDTTable(SynergyDTInfoEntity dtEntity) {
        initImg(dtEntity.getImg(), null);

        llDt.setVisibility(View.VISIBLE);
        tvDtTile.setText("使用单位：" + dtEntity.getInfo().getFEntityName() + "    检查人：" + dtEntity.getInfo().getFHandleUser() +
                "\n电梯编号：" + dtEntity.getInfo().getFElevatoeNumber() + "    时间：" + dtEntity.getInfo().getFInsertTime());
        tvDwbdwqk.setText("维保单位开展维保时间：" + dtEntity.getInfo().getFMaintenanceDate() + "\n"
                + "维保质量：" + dtEntity.getInfo().getFMaintenanceQuality());
        tvGzjclqk.setText(dtEntity.getInfo().getFRemark());
        if (dtEntity.getImg() != null && dtEntity.getImg().length() > 0) {
            ivDTImg.setVisibility(View.VISIBLE);
            Glide.with(this).load(ApiData.baseUrl + dtEntity.getImg()).error(R.mipmap.pic_load_error).into(ivDTImg);
        }
        for (int i = 0; i < dtEntity.getCheckResult().size(); i++) {
            SynergyDTInfoEntity.CheckResultBean checkResultBean = dtEntity.getCheckResult().get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.item_synergy_dt_smalltype, llJfjqsb, false);
            TextView tvContent = view.findViewById(R.id.tv_dt_small_content);
            RadioGroup radioGroup = view.findViewById(R.id.rg_synergy_dt);
            TextView tvRemark = view.findViewById(R.id.tv_dt_small_remark);
            tvContent.setText(checkResultBean.getFItemName());
            tvRemark.setText("null".equals(checkResultBean.getFRemark()) ? "" : checkResultBean.getFRemark());
            if ("正常".equals(checkResultBean.getFCheckResult())) {
                radioGroup.check(R.id.rb_synergy_dt1);
            } else if ("异常".equals(checkResultBean.getFCheckResult())) {
                radioGroup.check(R.id.rb_synergy_dt2);
            } else {
                radioGroup.check(R.id.rb_synergy_dt3);
            }
            if ("机房及其设备".equals(checkResultBean.getFCateGory())) {
                llJfjqsb.addView(view);
            } else if ("轿厢及层站".equals(checkResultBean.getFCateGory())) {
                llJxjcj.addView(view);
            }
        }

    }

    /**
     * 根据返回结果和状态添加普通列表
     *
     * @param mentity
     */
    private void createNormalTable(SynergyDetailEntity mentity) {
        initImg(mentity.getImgurl(), mentity.getImg());

        normalList.clear();
        llNormal.setVisibility(View.VISIBLE);
        llNormal.removeAllViews();
        switch (type) {
            case "specialdrugStore":
                normalList.add(new KeyValueInfo("主体名称", mentity.getFEntityName()));
                normalList.add(new KeyValueInfo("药品名称", mentity.getFDrugName()));
                normalList.add(new KeyValueInfo("批号", mentity.getFBatchNumber()));
                normalList.add(new KeyValueInfo("药品类型", mentity.getFDrugType()));
                normalList.add(new KeyValueInfo("生产日期", mentity.getFProduceDate()));
                normalList.add(new KeyValueInfo("购进时间", mentity.getFBuyDate()));
                normalList.add(new KeyValueInfo("购进数量", mentity.getFBuyCount()));
                normalList.add(new KeyValueInfo("剂型", mentity.getFDosageForm()));
                normalList.add(new KeyValueInfo("库存数量", mentity.getFStock()));
                normalList.add(new KeyValueInfo("使用数量", mentity.getFUseCount()));
                normalList.add(new KeyValueInfo("状态", mentity.getFStatusDes()));
                normalList.add(new KeyValueInfo("备注", mentity.getFRemark()));
                break;
            case "specialdrugUsing":
                normalList.add(new KeyValueInfo("主体名称", mentity.getFEntityName()));
                normalList.add(new KeyValueInfo("患者姓名", mentity.getFPatientName()));
                normalList.add(new KeyValueInfo("使用人数", mentity.getFUserCount()));
                normalList.add(new KeyValueInfo("使用医务人员", mentity.getFUsemedcialPerson()));
                normalList.add(new KeyValueInfo("使用日期", mentity.getFUseDate()));
                normalList.add(new KeyValueInfo("是否出现不良反应", mentity.getFAdverseReactions()));
                normalList.add(new KeyValueInfo("是否开具处方", mentity.getFIsPrescribe()));
                normalList.add(new KeyValueInfo("处方调配人", mentity.getFPrescribePerson()));
                normalList.add(new KeyValueInfo("处方核对人", mentity.getFPrescribeChecker()));
                normalList.add(new KeyValueInfo("状态", mentity.getFStatusDes()));
                normalList.add(new KeyValueInfo("备注", mentity.getFRemark()));
                break;
            case "medUsing":
                normalList.add(new KeyValueInfo("主体名称", mentity.getFEntityName()));
                normalList.add(new KeyValueInfo("医疗器械名称", mentity.getFMedEquipmentName()));
                normalList.add(new KeyValueInfo("编号", mentity.getFEqNumber()));
                normalList.add(new KeyValueInfo("分类名称", mentity.getFTypeName()));
                normalList.add(new KeyValueInfo("生产企业", mentity.getFProduceEntity()));
                normalList.add(new KeyValueInfo("上游购进企业", mentity.getFUpstreamEntity()));
                normalList.add(new KeyValueInfo("批号", mentity.getFBatchNumber()));
                normalList.add(new KeyValueInfo("购买时间", mentity.getFBuyDate()));
                normalList.add(new KeyValueInfo("运行情况", mentity.getFOperationCon()));
                normalList.add(new KeyValueInfo("状态", mentity.getFStatusDes()));
                normalList.add(new KeyValueInfo("是否即时进行检验", mentity.getFIsCheck()));
                normalList.add(new KeyValueInfo("备注", mentity.getFRemark()));
                break;
            case "medCheck":
                normalList.add(new KeyValueInfo("主体名称", mentity.getFEntityName()));
                normalList.add(new KeyValueInfo("管理类别", mentity.getFManageType()));
                normalList.add(new KeyValueInfo("编号", mentity.getFNumber()));
                normalList.add(new KeyValueInfo("检验周期", mentity.getFCheckCircle()));
                normalList.add(new KeyValueInfo("上次检验时间", mentity.getFLastCheckDate()));
                normalList.add(new KeyValueInfo("此次检验时间", mentity.getFCheckDate()));
                normalList.add(new KeyValueInfo("检测机构", mentity.getFCheckOrg()));
                normalList.add(new KeyValueInfo("检测结果", mentity.getFResult()));
                normalList.add(new KeyValueInfo("预警", mentity.getFWarming()));
                normalList.add(new KeyValueInfo("状态", mentity.getFStatusDes()));
                normalList.add(new KeyValueInfo("备注", mentity.getFRemark()));
                break;
            case "vacUsing":
                normalList.add(new KeyValueInfo("主体名称", mentity.getFEntityName()));
                normalList.add(new KeyValueInfo("药品名称", mentity.getFDrugName()));
                normalList.add(new KeyValueInfo("批号", mentity.getFBatchNumber()));
                normalList.add(new KeyValueInfo("购买时间", mentity.getFBuyDate()));
                normalList.add(new KeyValueInfo("购买数量", mentity.getFBuyCount()));
                normalList.add(new KeyValueInfo("生产时间", mentity.getFProduceDate()));
                normalList.add(new KeyValueInfo("接收疫苗温度", mentity.getFTemperHumidity()));
                normalList.add(new KeyValueInfo("剂型", mentity.getFDosageForm()));
                normalList.add(new KeyValueInfo("库存数量", mentity.getFStock()));
                normalList.add(new KeyValueInfo("接种数量", mentity.getFUseCount()));
                normalList.add(new KeyValueInfo("状态", mentity.getFStatusDes()));
                normalList.add(new KeyValueInfo("备注", mentity.getFRemark()));
                break;
            case "bizCheckStatement":
                normalList.add(new KeyValueInfo("企业名称", mentity.getFEntityName()));
                normalList.add(new KeyValueInfo("社会信用代码/组织机构代码", mentity.getFCreditCode()));
                normalList.add(new KeyValueInfo("产品名称", mentity.getFProduce()));
                normalList.add(new KeyValueInfo("标准类型", mentity.getFType()));
                normalList.add(new KeyValueInfo("标准名称", mentity.getFName()));
                normalList.add(new KeyValueInfo("标准号", mentity.getFNumber()));
                normalList.add(new KeyValueInfo("标准运行情况", mentity.getFSituation()));
                normalList.add(new KeyValueInfo("标准公开时间", mentity.getFDate()));
                break;
            case "bizCheckSpot":
                normalList.add(new KeyValueInfo("企业名称", mentity.getFEntityName()));
                normalList.add(new KeyValueInfo("产品名称", mentity.getFProduce()));
                normalList.add(new KeyValueInfo("不合格项目名称", mentity.getFProject()));
                normalList.add(new KeyValueInfo("整改复查情况", mentity.getFResult()));
                normalList.add(new KeyValueInfo("联系人", mentity.getFContacts()));
                normalList.add(new KeyValueInfo("联系人电话", mentity.getFTel()));
                normalList.add(new KeyValueInfo("企业地址", mentity.getFAddress()));
                break;
            case "bizCheckCompulsory":
                normalList.add(new KeyValueInfo("企业名称", mentity.getFEntityName()));
                normalList.add(new KeyValueInfo("联系人", mentity.getFContacts()));
                normalList.add(new KeyValueInfo("联系人电话", mentity.getFTel()));
                break;
            case "zdyyh":
                normalList.add(new KeyValueInfo("企业名称", mentity.getFEntityName()));
                normalList.add(new KeyValueInfo("企业地址", mentity.getFAddress()));
                normalList.add(new KeyValueInfo("隐患内容", mentity.getFContent()));
                normalList.add(new KeyValueInfo("录入时间", mentity.getFHandleDate() + ""));
                normalList.add(new KeyValueInfo("录入人员", mentity.getFHandleUser()));
                normalList.add(new KeyValueInfo("联系电话", mentity.getFTel()));
                normalList.add(new KeyValueInfo("备注", mentity.getFRemark()));
                break;
            case "hiddenDanger":
                normalList.add(new KeyValueInfo("隐患类型", mentity.getFWarnType()));
                normalList.add(new KeyValueInfo("新增时间", mentity.getFInsertDate()));
                normalList.add(new KeyValueInfo("处理结果", mentity.getFHandleResult()));
                break;
            default:
                break;
        }
        for (int i = 0; i < normalList.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_synergy_normal, llNormal, false);
            ((TextView) view.findViewById(R.id.tv_synergy_name)).setText(normalList.get(i).key);
            ((TextView) view.findViewById(R.id.tv_synergy_info)).setText(normalList.get(i).value);
            llNormal.addView(view);
        }
        if ("bizCheckCompulsory".equals(type))
            createCheckTable(mentity);
    }

    private void initImg(String imgurl, List<String> imgs) {
        photoPath.clear();
        if (imgs != null && imgs.size() > 0) {
            for (int i = 0; i < imgs.size(); i++) {
                photoPath.add("http://" + ApiData.mIp + "/" + imgs.get(i));
            }
        }
        if (imgurl != null) {
            String[] imgArray = imgurl.split(",");
            if (imgArray != null && imgArray.length > 0) {
                for (int i = 0; i < imgArray.length; i++) {
                    photoPath.add("http://" + ApiData.mIp + "/" + imgArray[i]);
                }
            }
        }
        mprvSynergy.notifyDataSetChanged();
    }

    /**
     * 添加强制检定申请表格
     *
     * @param mentity
     */
    private void createCheckTable(SynergyDetailEntity mentity) {
        llCheck.setVisibility(View.VISIBLE);
        llCheck.removeAllViews();
        List<SynergyDetailEntity.ItemsBean> itemsBeans = mentity.getItems();
        if (itemsBeans == null) {
            itemsBeans = new ArrayList<>();
        }
        itemsBeans.add(0, new SynergyDetailEntity.ItemsBean());
        for (int i = 0; i < itemsBeans.size(); i++) {
            SynergyDetailEntity.ItemsBean itemsBean = itemsBeans.get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.item_synergy_table, llCheck, false);
            TextView tvJlqjmc = view.findViewById(R.id.tv_synergy_jlqjmc);
            TextView tvXz = view.findViewById(R.id.tv_synergy_xz);
            TextView tvYt = view.findViewById(R.id.tv_synergy_yt);
            TextView tvXq = view.findViewById(R.id.tv_synergy_xq);
            if (i == 0) {
                tvJlqjmc.setBackgroundColor(ContextCompat.getColor(this, R.color.tableBg));
                tvXz.setBackgroundColor(ContextCompat.getColor(this, R.color.tableBg));
                tvYt.setBackgroundColor(ContextCompat.getColor(this, R.color.tableBg));
                tvXq.setBackgroundColor(ContextCompat.getColor(this, R.color.tableBg));
            } else {
                tvXq.setTextColor(ContextCompat.getColor(this, R.color.tableBg));
                tvXz.setText(itemsBean.getFStation());
                tvYt.setText(itemsBean.getFPurpose());
                tvXq.setTag(itemsBean);
                tvXq.setOnClickListener(v -> {
                    SynergyDetailEntity.ItemsBean mBean = (SynergyDetailEntity.ItemsBean) v.getTag();
                    String msg = "计量器具名称：" + mBean.getFName() + "\n"
                            + "规格：" + mBean.getFSpecification() + "\n"
                            + "出厂编号：" + mBean.getFSerialNum() + "\n"
                            + "数量：" + mBean.getFNumber() + "\n"
                            + "强检类型：" + mBean.getFType() + "\n"
                            + "乡镇：" + mBean.getFStation() + "\n"
                            + "用途：" + mBean.getFPurpose() + "\n"
                            + "备注：" + mBean.getFRemark();
                    Util.showInfoDialog(SynergyDetailActivity.this, "详情", msg);
                });
            }
            llCheck.addView(view);
        }
    }
}
