package com.zx.tjmarketmobile.adapter.supervise;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.MyRecycleAdapter;
import com.zx.tjmarketmobile.entity.CheckInfo;
import com.zx.tjmarketmobile.util.Util;

import java.util.List;

/**
 * Created by zhouzq on 2017/4/6.
 */

public class SuperviseMyTaskCheckResultAdapter extends MyRecycleAdapter {

    private List<CheckInfo> checkItemInfoBeanList;
    private Holder mHolder;
    private int index = 0;
    private boolean isExcute = false;
    private Context context;
    private int status;

    public SuperviseMyTaskCheckResultAdapter(List<CheckInfo> dataList, int mindex, boolean isExcute, int status) {
        this.checkItemInfoBeanList = dataList;
        this.index = mindex;
        this.isExcute = isExcute;
        this.status = status;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
//        if (viewType == ITEM_TYPE_NORMAL) {
        context = parent.getContext();
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
            final CheckInfo checkItemInfoBean = checkItemInfoBeanList.get(position);
            if (!TextUtils.isEmpty(checkItemInfoBean.getItemName())) {
                mHolder.tvName.setText(checkItemInfoBean.getItemName());
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.width = Util.dip2px(context, 30) * checkItemInfoBean.getIndex();
            if (checkItemInfoBean.getIndex() == 0) {
                mHolder.viewLine.setVisibility(View.VISIBLE);
            } else {
                mHolder.viewLine.setVisibility(View.GONE);
            }
            mHolder.index.setLayoutParams(layoutParams);
            if (checkItemInfoBean.getChildren() != null && checkItemInfoBean.getChildren().size() > 0) {
                mHolder.valueTypeZeroLayout.setVisibility(View.GONE);
                mHolder.valueTypeFirstLayout.setVisibility(View.GONE);
                mHolder.valueTypeSecondLayout.setVisibility(View.GONE);
            } else if ("1".equals(checkItemInfoBean.getType())) {//判断
                mHolder.valueTypeZeroLayout.setVisibility(View.VISIBLE);
                mHolder.valueTypeFirstLayout.setVisibility(View.GONE);
                mHolder.valueTypeSecondLayout.setVisibility(View.GONE);
                if ("是".equals(checkItemInfoBean.getCheckResult())) {
                    mHolder.radioButtonZero.setChecked(true);
                    mHolder.radioButtonFirst.setChecked(false);
                } else if ("否".equals(checkItemInfoBean.getCheckResult())) {
                    mHolder.radioButtonZero.setChecked(false);
                    mHolder.radioButtonFirst.setChecked(true);
                } else {
                    mHolder.radioButtonZero.setChecked(false);
                    mHolder.radioButtonFirst.setChecked(false);
                }
                mHolder.radioButtonZero.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            RadioButton radioButton = (RadioButton) buttonView.getTag();
                            radioButton.setChecked(false);
                            checkItemInfoBean.setCheckResult("是");
                        }
                    }
                });
                mHolder.radioButtonFirst.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            RadioButton radioButton = (RadioButton) buttonView.getTag();
                            radioButton.setChecked(false);
                            checkItemInfoBean.setCheckResult("否");
                        }
                    }
                });
            } else if ("2".equals(checkItemInfoBean.getType())) {//打分
                mHolder.valueTypeZeroLayout.setVisibility(View.GONE);
                mHolder.valueTypeFirstLayout.setVisibility(View.VISIBLE);
                mHolder.valueTypeSecondLayout.setVisibility(View.GONE);
                mHolder.etValue.setText(checkItemInfoBean.getCheckResult());
                checkItemInfoBean.setCheckResult("1");
                mHolder.etValue.setHint("请输入数值(" + (checkItemInfoBean.getMin().length() == 0 ? "0" : checkItemInfoBean.getMin()) + "-" + (checkItemInfoBean.getMax().length() == 0 ? "0" : checkItemInfoBean.getMax()) + ")");
                mHolder.etValue.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        checkItemInfoBean.setCheckResult(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            } else if ("3".equals(checkItemInfoBean.getType())) {//填写
                mHolder.valueTypeZeroLayout.setVisibility(View.GONE);
                mHolder.valueTypeFirstLayout.setVisibility(View.GONE);
                mHolder.valueTypeSecondLayout.setVisibility(View.VISIBLE);
                mHolder.etText.setText(checkItemInfoBean.getCheckResult());
                checkItemInfoBean.setCheckResult("1");
                mHolder.etText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        checkItemInfoBean.setCheckResult(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
            // || status == 1
            if (!isExcute) {
                mHolder.radioButtonFirst.setClickable(false);
                mHolder.radioButtonZero.setClickable(false);
                mHolder.etText.setEnabled(false);
                mHolder.etValue.setEnabled(false);
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
        private EditText etText;
        private View index, viewLine;
        private LinearLayout valueTypeZeroLayout, valueTypeFirstLayout, valueTypeSecondLayout;
        private RadioButton radioButtonZero, radioButtonFirst;

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
            etValue = (EditText) parent.findViewById(R.id.et_value_num);
            etValue.setInputType(InputType.TYPE_CLASS_PHONE);
            etText = (EditText) parent.findViewById(R.id.et_num_text);
            index = parent.findViewById(R.id.view_super_index);
            viewLine = parent.findViewById(R.id.view_item_line);
        }

    }
}