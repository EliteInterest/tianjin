package com.zx.tjmarketmobile.util;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by zhouzq on 2017/2/7.
 */
@SuppressWarnings("deprecation")
public class DatePickDialogUtil implements DatePicker.OnDateChangedListener {
    private DatePicker datePicker;
    private AlertDialog ad;
    private String dateTime;
    private String initDateTime;
    private Context context;

    public DatePickDialogUtil(Context context, String initDateTime) {
        this.context = context;
        this.initDateTime = initDateTime;

    }

    public void init(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        if (!(null == initDateTime || "".equals(initDateTime))) {
            calendar = this.getCalendarByInintData(initDateTime);
        } else {
            initDateTime = calendar.get(Calendar.YEAR) + "-"
                    + calendar.get(Calendar.MONTH) + "-"
                    + calendar.get(Calendar.DAY_OF_MONTH) + "- ";
        }

        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this);
    }

    /**
     * 弹出日期时间选择框方法
     * @return
     */
    @SuppressLint("ResourceAsColor")
    public AlertDialog dateTimePicKDialog(final TextView textView, final String mType) {
        GridLayout dateTimeLayout = (GridLayout) LayoutInflater.from(context).
                inflate(R.layout.popup_attribute_spinner_date_picker, null);
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.dateSpinnerPicker);
        init(datePicker);
        ad = new AlertDialog.Builder(context)
                .setTitle(initDateTime)
                .setView(dateTimeLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (mType.equals("year")){
                            textView.setText(dateTime.subSequence(0,4));
                        }else{
                            textView.setText(dateTime.subSequence(0,7).toString());
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).show();
        datePicker.getCalendarView().setFirstDayOfWeek(1);
        onDateChanged(null, 0, 0, 0);
        return ad;
    }


    public void onDateChanged(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
        // 获得日历实例
        Calendar calendar = Calendar.getInstance();

        calendar.set(datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        dateTime = sdf.format(calendar.getTime());
        ad.setTitle(dateTime);
    }


    private Calendar getCalendarByInintData(String initDateTime) {
        Calendar calendar = Calendar.getInstance();

        String yearStr = spliteString(initDateTime, "-", "index", "front"); // 年份
        String monthAndDay = spliteString(initDateTime, "-", "index", "back"); // 月日

        int currentYear = Integer.valueOf(yearStr.trim()).intValue();
        int currentMonth = Integer.valueOf(monthAndDay.trim()).intValue() - 1;
        int currentDay = Integer.valueOf(1).intValue();
        calendar.set(currentYear, currentMonth, currentDay);
        return calendar;
    }


    public static String spliteString(String srcStr, String pattern,
                                      String indexOrLast, String frontOrBack) {
        String result = "";
        int loc = -1;
        if (indexOrLast.equalsIgnoreCase("index")) {
            loc = srcStr.indexOf(pattern); // 取得字符串第一次出现的位置
        } else {
            loc = srcStr.lastIndexOf(pattern); // 最后一个匹配串的位置
        }
        if (frontOrBack.equalsIgnoreCase("front")) {
            if (loc != -1)
                result = srcStr.substring(0, loc); // 截取子串
        } else {
            if (loc != -1)
                result = srcStr.substring(loc + 1, srcStr.length()); // 截取子串
        }
        return result;
    }
}
