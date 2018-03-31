package com.zx.gamarketmobile.adapter.supervise;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.adapter.MyRecycleAdapter;
import com.zx.gamarketmobile.entity.supervise.MyTaskCheckResultEntity;

import java.util.List;

/**
 * Created by zhouzq on 2017/4/6.
 */

public class SuperviseMyTaskCheckResultAdapter extends MyRecycleAdapter {

    private List<MyTaskCheckResultEntity.DataBean> checkItemInfoBeanList;
    private Holder mHolder;
    private int index = 0;
    private boolean isExcute = false;

    public SuperviseMyTaskCheckResultAdapter(List<MyTaskCheckResultEntity.DataBean> dataList, int mindex, boolean isExcute) {
        this.checkItemInfoBeanList = dataList;
        this.index = mindex;
        this.isExcute = isExcute;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
//        if (viewType == ITEM_TYPE_NORMAL) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervise_mytask_check_result_item, parent, false);
        return new Holder(view);
//        } else {//footer
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_foot_view, parent, false);
//            return new FooterViewHolder(view);
//        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof Holder) {
            mHolder = (Holder) holder;
            final MyTaskCheckResultEntity.DataBean checkItemInfoBean = checkItemInfoBeanList.get(position);
            if (!TextUtils.isEmpty(checkItemInfoBean.getFItemName())) {
                mHolder.tvName.setText(checkItemInfoBean.getFItemName());
            }
            if (checkItemInfoBean.getFValueType() == 0) {//勾选
                mHolder.valueTypeZeroLayout.setVisibility(View.VISIBLE);
                mHolder.valueTypeFirstLayout.setVisibility(View.GONE);
                mHolder.valueTypeSecondLayout.setVisibility(View.GONE);
                if ("1".equals(checkItemInfoBean.getFCheckResult())) {
                    mHolder.radioButtonZero.setChecked(true);
                    mHolder.radioButtonFirst.setChecked(false);
                } else {
                    mHolder.radioButtonZero.setChecked(false);
                    mHolder.radioButtonFirst.setChecked(true);
                }
                mHolder.radioButtonZero.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            RadioButton radioButton = (RadioButton) buttonView.getTag();
                            radioButton.setChecked(false);
                            checkItemInfoBean.setFCheckResult(1 + "");
                        }
                    }
                });
                mHolder.radioButtonFirst.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            RadioButton radioButton = (RadioButton) buttonView.getTag();
                            radioButton.setChecked(false);
                            checkItemInfoBean.setFCheckResult(0 + "");
                        }
                    }
                });
            } else if (checkItemInfoBean.getFValueType() == 1) {//打分
                mHolder.valueTypeZeroLayout.setVisibility(View.GONE);
                mHolder.valueTypeFirstLayout.setVisibility(View.VISIBLE);
                mHolder.valueTypeSecondLayout.setVisibility(View.GONE);
                mHolder.etValue.setText(checkItemInfoBean.getFCheckResult());
                checkItemInfoBean.setFCheckResult("1");
                mHolder.etValue.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        checkItemInfoBean.setFCheckResult(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            } else if (checkItemInfoBean.getFValueType() == 2) {//纠正坐标
                mHolder.valueTypeZeroLayout.setVisibility(View.GONE);
                mHolder.valueTypeFirstLayout.setVisibility(View.GONE);
                mHolder.valueTypeSecondLayout.setVisibility(View.GONE);
                checkItemInfoBean.setFCheckResult("1");
                mHolder.corCoorBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            if (!isExcute) {
                mHolder.radioButtonFirst.setClickable(false);
                mHolder.radioButtonZero.setClickable(false);
            }
        } else {
            footerViewHolder = (FooterViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return checkItemInfoBeanList == null ? 0 : checkItemInfoBeanList.size();
    }

    class Holder extends ViewHolder {
        private TextView tvName;
        private EditText etValue;
        private LinearLayout valueTypeZeroLayout, valueTypeFirstLayout, valueTypeSecondLayout;
        private RadioButton radioButtonZero, radioButtonFirst;
        private Button corCoorBtn;

        public Holder(View parent) {
            super(parent);
            tvName = (TextView) parent.findViewById(R.id.item_name);
            valueTypeZeroLayout = (LinearLayout) parent.findViewById(R.id.layout_value_type_0);
            valueTypeFirstLayout = (LinearLayout) parent.findViewById(R.id.layout_value_type_1);
            valueTypeSecondLayout = (LinearLayout) parent.findViewById(R.id.layout_value_type_2);
            radioButtonZero = (RadioButton) parent.findViewById(R.id.radio_0);
            radioButtonFirst = (RadioButton) parent.findViewById(R.id.radio_1);
            radioButtonZero.setTag(radioButtonFirst);
            radioButtonFirst.setTag(radioButtonZero);
            etValue = (EditText) parent.findViewById(R.id.et_value);
            etValue.setInputType(InputType.TYPE_CLASS_PHONE);
            corCoorBtn = (Button) parent.findViewById(R.id.btn_value);
        }

    }
}
