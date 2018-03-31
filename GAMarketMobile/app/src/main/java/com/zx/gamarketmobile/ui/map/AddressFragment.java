package com.zx.gamarketmobile.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.entity.KeyEntityInfo;
import com.zx.gamarketmobile.ui.base.BaseFragment;

/**
 * Create By Stanny On 2016/9/22
 * 功能：地址主体信息
 */
public class AddressFragment extends BaseFragment {

    private KeyEntityInfo mEntityInfo;

    public static AddressFragment newInstance(int index, KeyEntityInfo entity) {
        AddressFragment details = new AddressFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        details.setArguments(args);
        details.mEntityInfo = entity;
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dz, container, false);
        TextView tvEntityName = (TextView) view.findViewById(R.id.tvFmDz_entityname);
        TextView tvregcode = (TextView) view.findViewById(R.id.tvFmDz_regcode);
        TextView tvLicNum = (TextView) view.findViewById(R.id.tvFmDz_bizlicnum);
        TextView tvPersion = (TextView) view.findViewById(R.id.tvFmDz_persion);
        TextView tvEntityAddress = (TextView) view.findViewById(R.id.tvFmDz_entityaddress);
        TextView tvRegPhone = (TextView) view.findViewById(R.id.tvFmDz_regphone);
        EditText mEdtCancelType = (EditText) view.findViewById(R.id.edtFmDz_canceltype);
        EditText mEdtCancelCause = (EditText) view.findViewById(R.id.edtFmDz_cancelcause);
        EditText mEdtCancelDate = (EditText) view.findViewById(R.id.edtFmDz_canceldate);
        if (null != mEntityInfo) {
            tvEntityName.setText(mEntityInfo.fEntityName);
            tvregcode.setText(mEntityInfo.fOrgCode);
            tvLicNum.setText(mEntityInfo.fUniscid);
            tvPersion.setText(mEntityInfo.fLegalPerson);
            tvEntityAddress.setText(mEntityInfo.fAddress);
            tvRegPhone.setText(mEntityInfo.fContactInfo);
            mEdtCancelType.setText(mEntityInfo.fCancelType);
            mEdtCancelCause.setText(mEntityInfo.fCancelReason);
            mEdtCancelDate.setText(mEntityInfo.fCancelDate);
        }
        return view;
    }

}
