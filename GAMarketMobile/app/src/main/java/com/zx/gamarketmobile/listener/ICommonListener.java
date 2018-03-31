package com.zx.gamarketmobile.listener;

import com.esri.core.tasks.ags.find.FindResult;
import com.zx.gamarketmobile.entity.SelectInfo;
import com.zx.gamarketmobile.entity.SelectPopDataList;
import com.zx.gamarketmobile.entity.ZFYTHassigneeEntity;

import java.util.List;

public class ICommonListener {

    public interface IOnSingleSelectListener {
        public void onSelect(String flag, String name);
    }

    public interface IOnInfoSelectListener {
        public void onSelect(int pos, SelectPopDataList info, int index);
    }

    public interface IOnAssigneeSelectListener {
        public void onSelect(int pos, ZFYTHassigneeEntity info);
    }

    public interface IOnMultipleSelectListener {
        public void onSelect(String flag, List<SelectInfo> list);
    }

    public interface IOnMapSearchListener {
        public void onSelect(List<FindResult> result);
    }
}
