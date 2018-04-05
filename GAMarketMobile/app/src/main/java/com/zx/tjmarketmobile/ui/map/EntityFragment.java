package com.zx.tjmarketmobile.ui.map;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.EntityDetail;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseFragment;

/**
 * Create By Stanny On 2016/9/22
 * 功能：主体信息
 */
public class EntityFragment extends BaseFragment {

    private EntityDetail.BaseInfoBean mEntity;
    private boolean mIsSubmit = false;
    private Button mBtnSubmit;
    private Button mBtnCancel;
    private EditText mEdtLinkName;
    private EditText mEdtLinkPhone;
    private EditText mEdtLinkAddress;
    private ApiData taskData = new ApiData(ApiData.HTTP_ID_entity_modifycontactinfo);

    public static EntityFragment newInstance(int index, EntityDetail.BaseInfoBean entity) {
        EntityFragment details = new EntityFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        args.putSerializable("entity", entity);
        details.setArguments(args);
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        mEntity = (EntityDetail.BaseInfoBean) getArguments().getSerializable("entity");
        view = inflater.inflate(R.layout.fragment_entity, container, false);
        taskData.setLoadingListener(this);
        TextView tvEntityName = (TextView) view.findViewById(R.id.tvFmEntity_entityname);
        TextView tvEntityType = (TextView) view.findViewById(R.id.tvFmEntity_entitytype);
//        TextView tvregcode = (TextView) view.findViewById(R.id.tvFmEntity_regcode);
        TextView tvLicNum = (TextView) view.findViewById(R.id.tvFmEntity_bizlicnum);
//        TextView tvLicenses = (TextView) view.findViewById(R.id.tvFmEntity_licenses);
        TextView tvPersion = (TextView) view.findViewById(R.id.tvFmEntity_persion);
        TextView tvEntityAddress = (TextView) view.findViewById(R.id.tvFmEntity_entityaddress);
        TextView tvRegPhone = (TextView) view.findViewById(R.id.tvFmEntity_regphone);
        mBtnSubmit = (Button) view.findViewById(R.id.btnFmEntity_confirm);
        mBtnCancel = (Button) view.findViewById(R.id.btnFmEntity_cancel);
        if (null != mEntity) {
            tvEntityName.setText(mEntity.getEnterpriseName());
            tvEntityType.setText(mEntity.getEnterpriseRegType());
//            tvregcode.setText(mEntity.getBizlicNum());
            tvLicNum.setText(mEntity.getBizlicNum());
            tvPersion.setText(mEntity.getLegalPerson());
            tvEntityAddress.setText(mEntity.getAddress());
            tvRegPhone.setText(mEntity.getContactInfo());
//            if ("D".equals(mEntityInfo.fCreditLevel) || "Z".equals(mEntityInfo.fCreditLevel)) {
//                mEdtLinkName.setVisibility(View.GONE);
//                mEdtLinkPhone.setVisibility(View.GONE);
//                mEdtLinkAddress.setVisibility(View.GONE);
//            }
                mBtnSubmit.setVisibility(View.GONE);
        }
        mBtnSubmit.setOnClickListener(confirmListener);
        mBtnCancel.setOnClickListener(confirmListener);
        return view;
    }

    OnClickListener confirmListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnFmEntity_confirm:
//                    if (!mIsSubmit) {
//                        changeViewStatusToSubmit();
//                    } else {
//                        String contactAddr = mEdtLinkAddress.getText().toString();
//                        String contactPhone = mEdtLinkPhone.getText().toString();
//                        String contactPeople = mEdtLinkName.getText().toString();
//                        taskData.loadData(mEntityInfo.fEntityGuid, contactAddr, contactPhone, contactPeople);
//                    }
                    break;
                case R.id.btnFmEntity_cancel:
//                    changeViewStatusToCancel();
//                    mEdtLinkName.setText(mEntityInfo.fContactPeople);
//                    mEdtLinkPhone.setText(mEntityInfo.fContactPhone);
//                    mEdtLinkAddress.setText(mEntityInfo.fContactAddress);
//                    mIsSubmit = false;
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
//        if (b.isSuccess()) {
//            mEntityInfo.fContactAddress = mEdtLinkAddress.getText().toString();
//            mEntityInfo.fContactPhone = mEdtLinkPhone.getText().toString();
//            mEntityInfo.fContactPeople = mEdtLinkName.getText().toString();
//            changeViewStatusToCancel();
//            mIsSubmit = false;
//            showToast("修改成功");
//        } else {
//            showToast("系统异常，请稍后再试");
//        }
    }
}
