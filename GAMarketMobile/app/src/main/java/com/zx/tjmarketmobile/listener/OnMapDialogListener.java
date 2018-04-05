package com.zx.tjmarketmobile.listener;

import com.esri.core.geometry.Point;

/**
 * Created by Xiangb on 2017/12/20.
 * 功能：
 */

public interface OnMapDialogListener {
    void selectPoint(Point selectPoint);

    void onHide();
}
