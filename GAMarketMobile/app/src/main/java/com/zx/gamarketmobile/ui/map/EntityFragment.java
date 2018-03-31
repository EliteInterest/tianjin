package com.zx.gamarketmobile.ui.map;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.entity.KeyEntityInfo;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.http.BaseHttpResult;
import com.zx.gamarketmobile.ui.base.BaseFragment;

/**
 * Create By Stanny On 2016/9/22
 * 功能：主体信息
 */
public class EntityFragment extends BaseFragment {

    private KeyEntityInfo mEntityInfo;
    private boolean mIsSubmit = false;
    private Button mBtnSubmit;
    private Button mBtnCancel;
    private EditText mEdtLinkName;
    private EditText mEdtLinkPhone;
    private EditText mEdtLinkAddress;
    private int update = 0;//0纠正  1认领
    private String mEntityType;
    private ApiData taskData = new ApiData(ApiData.HTTP_ID_entity_modifycontactinfo);

    public static EntityFragment newInstance(int index, KeyEntityInfo entity,int update, String fEntityType) {
        EntityFragment details = new EntityFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        details.setArguments(args);
        details.update = update;
        details.mEntityInfo = entity;
        details.mEntityType = fEntityType;
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (mEntityType!=null){
            if (mEntityType.equals("individual")){
                view = inflater.inflate(R.layout.fragment_entity_individal, container, false);
                TextView tvEntityName = (TextView) view.findViewById(R.id.tvFmEntity_entityname);
                TextView tvOperatorAddress = (TextView) view.findViewById(R.id.tvFmEntity_operator_address);
                TextView tvOperatorName = (TextView) view.findViewById(R.id.tvFmEntity_operator_name);
                TextView tvOperatorMode = (TextView) view.findViewById(R.id.tvFmEntity_operation_mode);
                TextView tvOperatorScope = (TextView) view.findViewById(R.id.tvFmEntity_business_scope);
                TextView tvLicenseNumber = (TextView) view.findViewById(R.id.tvFmEntity_license);
                TextView tvRegulatoryBranch = (TextView) view.findViewById(R.id.tvFmEntity_regulatory_branch);
                TextView tvCertificateNumber = (TextView) view.findViewById(R.id.tvFmEntity_certificate_number);
                if (mEntityInfo!=null){
                    if (mEntityInfo.fZhmc!=null)
                        tvEntityName.setText(mEntityInfo.fZhmc);
                    if (mEntityInfo.fZs!=null)
                        tvOperatorAddress.setText(mEntityInfo.fZs);
                    if (mEntityInfo.fJyzm!=null)
                        tvOperatorName.setText(mEntityInfo.fJyzm);
                    if (mEntityInfo.fJyfs!=null)
                        tvOperatorMode.setText(mEntityInfo.fJyfs);
                    if (mEntityInfo.fYbjyfw!=null)
                        tvOperatorScope.setText(mEntityInfo.fYbjyfw);
                    if (mEntityInfo.fXkz!=null)
                        tvLicenseNumber.setText(mEntityInfo.fXkz);
                    if (mEntityInfo.fSdjgs!=null)
                        tvRegulatoryBranch.setText(mEntityInfo.fSdjgs);
                    if (mEntityInfo.fSwdjz!=null)
                        tvCertificateNumber.setText(mEntityInfo.fSwdjz);
                }
            }else if (mEntityType.equals("enterprise")) {
                view = inflater.inflate(R.layout.fragment_entity, container, false);
                taskData.setLoadingListener(this);
                TextView tvEntityName = (TextView) view.findViewById(R.id.tvFmEntity_entityname);
                TextView tvEntityType = (TextView) view.findViewById(R.id.tvFmEntity_entitytype);
                TextView tvregcode = (TextView) view.findViewById(R.id.tvFmEntity_regcode);
                TextView tvLicNum = (TextView) view.findViewById(R.id.tvFmEntity_bizlicnum);
                TextView tvLicenses = (TextView) view.findViewById(R.id.tvFmEntity_licenses);
                TextView tvPersion = (TextView) view.findViewById(R.id.tvFmEntity_persion);
                TextView tvEntityAddress = (TextView) view.findViewById(R.id.tvFmEntity_entityaddress);
                TextView tvRegPhone = (TextView) view.findViewById(R.id.tvFmEntity_regphone);
                mEdtLinkName = (EditText) view.findViewById(R.id.edtFmEntity_linkname);
                mEdtLinkPhone = (EditText) view.findViewById(R.id.edtFmEntity_linkphone);
                mEdtLinkAddress = (EditText) view.findViewById(R.id.edtFmEntity_linkaddress);
                mBtnSubmit = (Button) view.findViewById(R.id.btnFmEntity_confirm);
                mBtnCancel = (Button) view.findViewById(R.id.btnFmEntity_cancel);
                if (null != mEntityInfo) {
                    tvEntityName.setText(mEntityInfo.fEntityName);
                    tvEntityType.setText(mEntityInfo.fEntityType);
                    tvregcode.setText(mEntityInfo.fUniscid);
                    tvLicNum.setText(mEntityInfo.fBizlicNum);
                    tvLicenses.setText(mEntityInfo.fLicenses);
                    tvPersion.setText(mEntityInfo.fLegalPerson);
                    tvEntityAddress.setText(mEntityInfo.fAddress);
                    tvRegPhone.setText(mEntityInfo.fContactInfo);
                    mEdtLinkName.setText(mEntityInfo.fContactPeople);
                    mEdtLinkPhone.setText(mEntityInfo.fContactPhone);
                    mEdtLinkAddress.setText(mEntityInfo.fContactAddress);
                    if ("D".equals(mEntityInfo.fCreditLevel) || "Z".equals(mEntityInfo.fCreditLevel)) {
                        mEdtLinkName.setVisibility(View.GONE);
                        mEdtLinkPhone.setVisibility(View.GONE);
                        mEdtLinkAddress.setVisibility(View.GONE);
                        mBtnSubmit.setVisibility(View.GONE);
                    }
                }
                if (update < 2) {
                    mBtnSubmit.setVisibility(View.GONE);
                }
                mBtnSubmit.setOnClickListener(confirmListener);
                mBtnCancel.setOnClickListener(confirmListener);
            }else if (mEntityType.equals("YLJG")){
                view = inflater.inflate(R.layout.fragment_entity_yljg, container, false);
                TextView tvEntityName = (TextView) view.findViewById(R.id.tvFmEntity_entityname);
                TextView tvType = (TextView) view.findViewById(R.id.tvFmEntity_yljg_type);
                TextView tvNature = (TextView) view.findViewById(R.id.tvFmEntity_yljg_nature);
                TextView tvScale = (TextView) view.findViewById(R.id.tvFmEntity_yljg_scale);
                TextView tvAddress = (TextView) view.findViewById(R.id.tvFmEntity_yljg_address);
                TextView tvContact = (TextView) view.findViewById(R.id.tvFmEntity_yljg_contact);
                if (mEntityInfo!=null) {
                    if (mEntityInfo.fEntityName != null)
                        tvEntityName.setText(mEntityInfo.fEntityName);
                    if (mEntityInfo.fEntityType != null)
                        tvType.setText(mEntityInfo.fEntityType);
                    if (mEntityInfo.fStation != null)
                        tvAddress.setText(mEntityInfo.fStation);
                    if (mEntityInfo.fScale != null)
                        tvScale.setText(mEntityInfo.fScale);
                    if (mEntityInfo.fNature != null)
                        tvNature.setText(mEntityInfo.fNature);
                    if (mEntityInfo.fContact != null)
                        tvContact.setText(mEntityInfo.fContact);
                }
            }else if (mEntityType.equals("JYJG")){
                view = inflater.inflate(R.layout.fragment_entity_jyjg, container, false);
                TextView tvEntityName = (TextView) view.findViewById(R.id.tvFmEntity_entityname);
                TextView tvType = (TextView) view.findViewById(R.id.tvFmEntity_type);
                TextView tvAddress = (TextView) view.findViewById(R.id.tvFmEntity_address);
                if (mEntityInfo!=null) {
                    if (mEntityInfo.fEntityName != null)
                        tvEntityName.setText(mEntityInfo.fEntityName);
                    if (mEntityInfo.fEntityType != null)
                        tvType.setText(mEntityInfo.fEntityType);
                    if (mEntityInfo.fStation != null)
                        tvAddress.setText(mEntityInfo.fStation);
                }
            }
        }

        return view;
    }

    OnClickListener confirmListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnFmEntity_confirm:
                    if (!mIsSubmit) {
                        changeViewStatusToSubmit();
                    } else {
                        String contactAddr = mEdtLinkAddress.getText().toString();
                        String contactPhone = mEdtLinkPhone.getText().toString();
                        String contactPeople = mEdtLinkName.getText().toString();
                        taskData.loadData(mEntityInfo.fEntityGuid, contactAddr, contactPhone, contactPeople);
                    }
                    break;
                case R.id.btnFmEntity_cancel:
                    changeViewStatusToCancel();
                    mEdtLinkName.setText(mEntityInfo.fContactPeople);
                    mEdtLinkPhone.setText(mEntityInfo.fContactPhone);
                    mEdtLinkAddress.setText(mEntityInfo.fContactAddress);
                    mIsSubmit = false;
                    break;

                default:
                    break;
            }

        }
    };

    private void changeViewStatusToSubmit() {
        int px5 = (int) getResources().getDimension(R.dimen.px5);
        mBtnSubmit.setText("提交");
        mEdtLinkName.setEnabled(true);
        mEdtLinkName.setBackgroundResource(R.drawable.home_ll_bg);
        mEdtLinkName.setPadding(px5, px5, px5, px5);
        mEdtLinkPhone.setEnabled(true);
        mEdtLinkPhone.setBackgroundResource(R.drawable.home_ll_bg);
        mEdtLinkPhone.setPadding(px5, px5, px5, px5);
        mEdtLinkAddress.setEnabled(true);
        mEdtLinkAddress.setBackgroundResource(R.drawable.home_ll_bg);
        mEdtLinkAddress.setPadding(px5, px5, px5, px5);
        mBtnCancel.setVisibility(View.VISIBLE);
        mIsSubmit = true;
    }

    private void changeViewStatusToCancel() {
        int color = ContextCompat.getColor(getActivity(), R.color.transparent);
        mBtnSubmit.setText("修改");
        mEdtLinkName.setEnabled(false);
        mEdtLinkName.setBackgroundColor(color);
        mEdtLinkPhone.setEnabled(false);
        mEdtLinkPhone.setBackgroundColor(color);
        mEdtLinkAddress.setEnabled(false);
        mEdtLinkAddress.setBackgroundColor(color);
        mBtnCancel.setVisibility(View.GONE);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        if (b.isSuccess()) {
            mEntityInfo.fContactAddress = mEdtLinkAddress.getText().toString();
            mEntityInfo.fContactPhone = mEdtLinkPhone.getText().toString();
            mEntityInfo.fContactPeople = mEdtLinkName.getText().toString();
            changeViewStatusToCancel();
            mIsSubmit = false;
            showToast("修改成功");
        } else {
            showToast("系统异常，请稍后再试");
        }
    }
}
