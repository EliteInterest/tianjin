package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.listener.ICheckedChangeListener;

import java.util.HashMap;

/**
 * Created by zhouzq on 2017/1/22.
 */

public class ChoiceListAdapter extends BaseAdapter{

    private String[] mStrings;
    private Context mContext;
    private HashMap<String,Object> objectsHashMap = new HashMap<>();
    private boolean isOnChecked = false;
    private ICheckedChangeListener checkedChangeListener;

    public ChoiceListAdapter(Context context, String[] strings) {
        this.mContext = context;
        this.mStrings = strings;
        objectsHashMap.clear();
        isOnChecked = false;
    }

    public void setOnCheckedChangeListener(ICheckedChangeListener listener) {
        this.checkedChangeListener = listener;
    }

    @Override
    public int getCount() {
        return mStrings.length;
    }

    @Override
    public String getItem(int position) {
        return mStrings[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(mContext).inflate(R.layout.choice_list_item,parent,false);
        TextView name = (TextView)convertView.findViewById(R.id.name);
        name.setText(getItem(position));
        final CheckBox box = (CheckBox)convertView.findViewById(R.id.check_radio);
        if (!objectsHashMap.containsKey(getItem(position))){
            if (!isOnChecked)
            objectsHashMap.put(name.getText().toString(),box);
        }else{
            if (isOnChecked){
                box.setChecked(true);
            }
        }
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    for (int i = 0; i < mStrings.length; i++) {
                        if (!getItem(position).equals(mStrings[i])){
                            if (objectsHashMap.get(mStrings[i])!=null){
                                CheckBox checkbox = (CheckBox)objectsHashMap.get(mStrings[i]);
                                checkbox.setChecked(false);
                                objectsHashMap.remove(mStrings[i]);
                            }
                        }else{
                            objectsHashMap.put(getItem(position).toString(),box);
                        }
                    }
                    isOnChecked = true;
                    notifyDataSetChanged();
                    if (checkedChangeListener != null) {
                        checkedChangeListener.CheckedChange(position, isChecked);
                    }
                }else{
                    isOnChecked = false;
                }
            }
        });

        return convertView;
    }


}
