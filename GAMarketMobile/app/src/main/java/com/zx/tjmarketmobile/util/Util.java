package com.zx.tjmarketmobile.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.ChoiceListAdapter;
import com.zx.tjmarketmobile.listener.ICheckedChangeListener;
import com.zx.tjmarketmobile.ui.mainbase.PhotoPickerActivity;
import com.zx.tjmarketmobile.ui.mainbase.SpaceImageDetailActivity;
import com.zx.tjmarketmobile.zoom.NativeImageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;


/**
 * Create By Stanny On 2016/9/22
 * 功能：工具类
 */
@SuppressWarnings("deprecation")
@SuppressLint("SimpleDateFormat")
public class Util {
    public static final int RESULT_CAPTURE_IMAGE = 1;// 照相的requestCode
    public static final int RESULT_PICKER_IMAGE_PHONE = 2;// 相册的requestCode
    public static final int RESULT_CUSTOM_PICKER_IMAGE_PHONE = 3;// 自定义选择照片的requestCode
    public static final String Basepath = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/TJMarket/";
    public static final String Myphotopath = Basepath + "PIC/";
    public static final String Myaudiopath = Basepath + "AUDIO/";
    public static final String Myvideopath = Basepath + "VIDEO/";
    public static final int VIDEO_MAX_TIME = 30;
    public static final String SAVE_PATH = Basepath + "VIDEO/save.mp4";

    private static HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<>();

    private static Animation shakeAction = new TranslateAnimation(-3, 3, 0, 0);//设置动画左右跳动


    /**
     * Activity 跳转
     *
     * @param activity
     */
    public static void activity_In(Activity activity) {
        activity.overridePendingTransition(R.anim.push_left_in_a, R.anim.push_right_out_a);
    }

    /**
     * Activity 退出
     *
     * @param activity
     */
    public static void activity_Out(Activity activity) {
        activity.overridePendingTransition(R.anim.push_right_in_a, R.anim.push_left_out);
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    // 隐藏软键盘
    public static void hideKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏输入法
     *
     * @param activity
     */
    public static void hideInputMethod(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = activity.getWindow().peekDecorView();
        if (v != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 关闭软键盘
     *
     * @parammEditText输入框
     * @parammContext上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    public static int[] getScreenSize(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        Activity act = (Activity) context;
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        return new int[]{width, height};
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        // final float scale =
        // context.getResources().getDisplayMetrics().density;
        // return (int) (dpValue * scale + 0.5f);
        return (int) (dpValue * (context.getResources().getDisplayMetrics().densityDpi / 160));
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static LayoutInflater inflater;
    public static Dialog dialog;
//    public static AlertDialog.Builder builder;

    /**
     * 弹出框
     *
     * @param context
     * @param titleStr      标题
     * @param cancleBtnName 取消按钮
     * @param listener      确认按钮监听
     * @parammessageStr内容
     * @paramPositiveBtnName 确认按钮
     */
    public static void showDeleteDialog(Context context, String titleStr, String messageStr, String positiveBtnName,
                                        String cancleBtnName, OnClickListener listener, OnClickListener cancelListener) {
        dialog = null;
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        LinearLayout dialogView = (LinearLayout) inflater.inflate(R.layout.hintdialog_delete, null);
        int[] size = getScreenSize(context);
        if (size[0] > 1000) {
            dialogView.setMinimumWidth(dip2px(context, 400));
        } else {
            dialogView.setMinimumWidth(size[0] - 100);
        }
        dialog = new Dialog(context, R.style.fullDialog);
        dialog.getWindow().setContentView(dialogView);
        TextView titleView = (TextView) dialog.findViewById(R.id.hintDialogdelete_hint);
        TextView messageView = (TextView) dialog.findViewById(R.id.hintDialogdelete_hint_message);
        Button btnDelete = (Button) dialog.findViewById(R.id.hintbtnDialogdelete_delete);
        Button btnCancel = (Button) dialog.findViewById(R.id.hintbtnDialogdelete_close);
        if (messageStr != null) {
            messageView.setVisibility(View.VISIBLE);
            messageView.setText(messageStr);
        }
        titleView.setText(titleStr);
        if (positiveBtnName != null) {
            btnDelete.setText(positiveBtnName);
        }
        if (cancleBtnName != null) {
            btnCancel.setText(cancleBtnName);
        }
        btnDelete.setOnClickListener(listener);
        if (cancelListener != null) {
            btnCancel.setOnClickListener(cancelListener);
        } else {
            btnCancel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
        }
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取系统时间
     *
     * @return
     */
    public static String getDate() {
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);           // 获取年份
        int month = ca.get(Calendar.MONTH);         // 获取月份
        int day = ca.get(Calendar.DATE);            // 获取日
        int minute = ca.get(Calendar.MINUTE);       // 分
        int hour = ca.get(Calendar.HOUR);           // 小时
        int second = ca.get(Calendar.SECOND);       // 秒

        String date = "" + year + (month + 1) + day + hour + minute + second;
        return date;
    }

    /**
     * 获取SD path
     *
     * @return
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
            return sdDir.toString();
        }

        return null;
    }


    private static ProgressDialog progressDialog;

    public static void closeProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showProgressDialog(Context context, int progress, String text) {
        try {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage(text);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCancelable(true);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setProgress(progress);
                progressDialog.show();
            } else {
                progressDialog.setMessage(text);
                progressDialog.setProgress(progress);
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    public static int getTabsHeight(Context context) {
        return (int) context.getResources().getDimension(R.dimen.tabsHeight);
    }

    public static Dialog getInstance(Context context) {
        if (dialog == null) {
            synchronized (Dialog.class) {
                if (dialog == null)
                    dialog = new Dialog(context, R.style.fullDialog);
            }
        }
        return dialog;
    }

    /**
     * 给dialog设置动画 以及全屏
     *
     * @param view
     * @param dialog
     */
    public static void dialog_window(View view, Dialog dialog) {
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setWindowAnimations(R.style.animationDialog);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }


    /**
     * 弹出框
     *
     * @param context
     * @param titleStr      标题
     * @param cancleBtnName 取消按钮
     * @param listener      确认按钮监听
     * @parammessageStr内容
     * @paramPositiveBtnName 确认按钮
     */
    public static void showYesOrNoDialog(Context context, String titleStr, String messageStr, String positiveBtnName,
                                         String cancleBtnName, OnClickListener listener, OnClickListener cancelListener) {
        dialog = null;
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        LinearLayout dialogView = (LinearLayout) inflater.inflate(R.layout.hintdialog_yesorno, null);
        int[] size = getScreenSize(context);
        if (size[0] > 1000) {
            dialogView.setMinimumWidth(dip2px(context, 400));
        } else {
            dialogView.setMinimumWidth(size[0] - 100);
        }
        dialog = getInstance(context);
        dialog.getWindow().setContentView(dialogView);
        TextView titleView = (TextView) dialog.findViewById(R.id.hintDialogyesorno_hint);
        TextView messageView = (TextView) dialog.findViewById(R.id.hintDialogyesorno_hint_message);
        Button btnCancle = (Button) dialog.findViewById(R.id.hintbtnDialogyesorno_cancle);
        Button btnOk = (Button) dialog.findViewById(R.id.hintbtnDialogyesorno_yes);
        if (messageStr != null) {
            messageView.setVisibility(View.VISIBLE);
            messageView.setText(messageStr);
        }
        titleView.setText(titleStr);
        if (positiveBtnName != null) {
            btnOk.setText(positiveBtnName);
        }
        if (cancleBtnName != null) {
            btnCancle.setText(cancleBtnName);
        }
        btnOk.setOnClickListener(listener);
        if (cancelListener != null) {
            btnCancle.setOnClickListener(cancelListener);
        } else {
            btnCancle.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
        }
        dialog.show();
    }

    public static void showEditDialog(Context context, String titleStr, String messageStr, String positiveBtnName,
                                      String cancleBtnName, DialogEditListener listener, OnClickListener cancelListener) {
        dialog = null;
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        LinearLayout dialogView = (LinearLayout) inflater.inflate(R.layout.layout_add_entity_point, null);
        int[] size = getScreenSize(context);
        if (size[0] > 1000) {
            dialogView.setMinimumWidth(dip2px(context, 400));
        } else {
            dialogView.setMinimumWidth(size[0] - 100);
        }
        dialog = getInstance(context);
        dialog.getWindow().setContentView(dialogView);
        TextView titleView = (TextView) dialog.findViewById(R.id.hintDialogedit_hint);
        TextView messageView = (TextView) dialog.findViewById(R.id.hintDialogedit_hint_message);
        Button btnCancle = (Button) dialog.findViewById(R.id.hintbtnDialogedit_cancle);
        Button btnOk = (Button) dialog.findViewById(R.id.hintbtnDialogedit_yes);
        EditText etAddress = dialog.findViewById(R.id.hintDialogedit_hint_address);
        if (messageStr != null) {
            messageView.setVisibility(View.VISIBLE);
            messageView.setText(messageStr);
        }
        titleView.setText(titleStr);
        if (positiveBtnName != null) {
            btnOk.setText(positiveBtnName);
        }
        if (cancleBtnName != null) {
            btnCancle.setText(cancleBtnName);
        }
        btnOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etAddress.getWindowToken(), 0);
                listener.onOKBtnClick(etAddress.getText().toString());
            }
        });
        if (cancelListener != null) {
            btnCancle.setOnClickListener(cancelListener);
        } else {
            btnCancle.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
        }
        dialog.show();
    }

    public interface DialogEditListener {
        void onOKBtnClick(String editString);
    }

    //三个选择
    public static void showThreeActionDialog(Context context, String titleStr, String messageStr, String otherBtnName, String positiveBtnName,
                                             String cancleBtnName, OnClickListener ohterListener, OnClickListener listener, OnClickListener cancelListener) {
        dialog = null;
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        LinearLayout dialogView = (LinearLayout) inflater.inflate(R.layout.hintdialog_yesorno, null);
        int[] size = getScreenSize(context);
        if (size[0] > 1000) {
            dialogView.setMinimumWidth(dip2px(context, 400));
        } else {
            dialogView.setMinimumWidth(size[0] - 100);
        }
        dialog = getInstance(context);
        dialog.getWindow().setContentView(dialogView);
        TextView titleView = (TextView) dialog.findViewById(R.id.hintDialogyesorno_hint);
        TextView messageView = (TextView) dialog.findViewById(R.id.hintDialogyesorno_hint_message);
        Button btnCancle = (Button) dialog.findViewById(R.id.hintbtnDialogyesorno_cancle);
        Button btnOk = (Button) dialog.findViewById(R.id.hintbtnDialogyesorno_yes);
        Button btnOhter = (Button) dialog.findViewById(R.id.hintbtnDialogyesorno_other);
        btnOhter.setVisibility(View.VISIBLE);
        if (messageStr != null) {
            messageView.setVisibility(View.VISIBLE);
            messageView.setText(messageStr);
        }
        titleView.setText(titleStr);
        if (positiveBtnName != null) {
            btnOk.setText(positiveBtnName);
        }
        if (cancleBtnName != null) {
            btnCancle.setText(cancleBtnName);
        }
        if (otherBtnName != null) {
            btnOhter.setText(otherBtnName);
        }
        btnOk.setOnClickListener(listener);
        btnOhter.setOnClickListener(ohterListener);
        if (cancelListener != null) {
            btnCancle.setOnClickListener(cancelListener);
        } else {
            btnCancle.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
        }
        dialog.show();
    }

    //三个选择
    public static void showThreeActionListDialog(Context context, String titleStr, String messageStr, String otherBtnName, String positiveBtnName,
                                                 String cancleBtnName, String[] strings, OnClickListener ohterListener,
                                                 OnClickListener listener, OnClickListener cancelListener, ICheckedChangeListener checkedChangeListener) {
        dialog = null;
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        LinearLayout dialogView = (LinearLayout) inflater.inflate(R.layout.hintdialog_list_yesorno, null);
        int[] size = getScreenSize(context);
        if (size[0] > 1000) {
            dialogView.setMinimumWidth(dip2px(context, 400));
        } else {
            dialogView.setMinimumWidth(size[0] - 100);
        }
        dialog = getInstance(context);
        dialog.getWindow().setContentView(dialogView);

        TextView titleView = (TextView) dialog.findViewById(R.id.hintDialogyesorno_hint);
        TextView messageView = (TextView) dialog.findViewById(R.id.hintDialogyesorno_hint_message);
        ListView choiceList = (ListView) dialog.findViewById(R.id.choice_list);
        ChoiceListAdapter choiceListAdapter = new ChoiceListAdapter(context, strings);
        choiceList.setAdapter(choiceListAdapter);
        choiceListAdapter.setOnCheckedChangeListener(checkedChangeListener);
        Button btnCancle = (Button) dialog.findViewById(R.id.hintbtnDialogyesorno_cancle);
        Button btnOk = (Button) dialog.findViewById(R.id.hintbtnDialogyesorno_yes);
        Button btnOhter = (Button) dialog.findViewById(R.id.hintbtnDialogyesorno_other);
        btnOhter.setVisibility(View.VISIBLE);
        if (messageStr != null) {
            messageView.setVisibility(View.VISIBLE);
            messageView.setText(messageStr);
        }
        titleView.setText(titleStr);
        if (positiveBtnName != null) {
            btnOk.setText(positiveBtnName);
        }
        if (cancleBtnName != null) {
            btnCancle.setText(cancleBtnName);
        }
        if (otherBtnName != null) {
            btnOhter.setText(otherBtnName);
        }
        btnOk.setOnClickListener(listener);
        btnOhter.setOnClickListener(ohterListener);
        if (cancelListener != null) {
            btnCancle.setOnClickListener(cancelListener);
        } else {
            btnCancle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
        }
        dialog.show();
    }


    /**
     * 信息提示弹出框
     *
     * @param context
     * @param titleStr 标题
     * @parammessageStr内容
     */
    public static void showInfoDialog(Context context, String titleStr, String messageStr) {
        android.support.v7.app.AlertDialog.Builder buider = new android.support.v7.app.AlertDialog.Builder(context);
        buider.setTitle(titleStr);
        buider.setMessage(messageStr);
        buider.setPositiveButton("确定", null);
        dialog = buider.show();
        dialog.setCanceledOnTouchOutside(true);
//        dialog = null;
//        if (inflater == null) {
//            inflater = LayoutInflater.from(context);
//        }
//        LinearLayout dialogView = (LinearLayout) inflater.inflate(R.layout.hintdialog_ok, null);
//        int[] size = getScreenSize(context);
//        if (size[0] > 1000) {
//            dialogView.setMinimumWidth(dip2px(context, 400));
//        } else {
//            dialogView.setMinimumWidth(size[0] - 100);
//        }
//        dialog = new Dialog(context, R.style.fullDialog);
//        dialog.getWindow().setContentView(dialogView);
//        TextView titleView = (TextView) dialog.findViewById(R.id.hintDialogok_hint);
//        if (titleStr != null && !titleStr.isEmpty()) {
//            titleView.setText(titleStr);
//        } else {
//            titleView.setVisibility(View.GONE);
//        }
//        TextView messageView = (TextView) dialog.findViewById(R.id.hintDialogok_hint_message);
//        messageView.setMovementMethod(ScrollingMovementMethod.getInstance());
//        Button btnOk = (Button) dialog.findViewById(R.id.hintbtnDialogok_ok);
//        if (messageStr != null) {
//            messageView.setVisibility(View.VISIBLE);
//            messageView.setText(messageStr);
//        }
//
//        btnOk.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                dialog.cancel();
//            }
//        });

//        dialog.showMap();
    }

    /**
     * 详细信息弹出框
     *
     * @param context
     * @param titleStr 标题
     * @parammessageStr内容
     */
    public static void showDetailInfoDialog(Context context, String titleStr, String messageStr) {
        dialog = null;
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        LinearLayout dialogView = (LinearLayout) inflater.inflate(R.layout.hintdialog_detailinfo, null);
        int[] size = getScreenSize(context);
        if (size[0] > 1000) {
            dialogView.setMinimumWidth(dip2px(context, 400));
        } else {
            dialogView.setMinimumWidth(size[0] - 100);
        }
        dialog = new Dialog(context, R.style.fullDialog);
        dialog.getWindow().setContentView(dialogView);
        TextView titleView = (TextView) dialog.findViewById(R.id.hintDialogdetailinfo_hint);
        if (titleStr != null && !titleStr.isEmpty()) {
            titleView.setText(titleStr);
        } else {
            titleView.setVisibility(View.GONE);
        }
        TextView messageView = (TextView) dialog.findViewById(R.id.hintDialogdetailinfo_hint_message);
        messageView.setMovementMethod(ScrollingMovementMethod.getInstance());
        Button btnOk = (Button) dialog.findViewById(R.id.hintbtnDialogdetailinfo_ok);
        if (messageStr != null) {
            messageView.setVisibility(View.VISIBLE);
            messageView.setText(messageStr);
        }

        btnOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    public static void showDetailInfoDialog(Context context, String titleStr, String messageStr,
                                            OnClickListener oklistener, int autolinkmask) {
        dialog = null;
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        LinearLayout dialogView = (LinearLayout) inflater.inflate(R.layout.hintdialog_detailinfo, null);
        int[] size = getScreenSize(context);
        if (size[0] > 1000) {
            dialogView.setMinimumWidth(dip2px(context, 400));
        } else {
            dialogView.setMinimumWidth(size[0] - 100);
        }
        dialog = new Dialog(context, R.style.fullDialog);
        dialog.getWindow().setContentView(dialogView);
        TextView titleView = (TextView) dialog.findViewById(R.id.hintDialogdetailinfo_hint);
        if (titleStr != null && !titleStr.isEmpty()) {
            titleView.setText(titleStr);
        } else {
            titleView.setVisibility(View.GONE);
        }
        TextView messageView = (TextView) dialog.findViewById(R.id.hintDialogdetailinfo_hint_message);
        messageView.setMovementMethod(ScrollingMovementMethod.getInstance());
        messageView.setAutoLinkMask(autolinkmask);
        Button btnOk = (Button) dialog.findViewById(R.id.hintbtnDialogdetailinfo_ok);
        if (messageStr != null) {
            messageView.setVisibility(View.VISIBLE);
            messageView.setText(messageStr);
        }

        if (oklistener != null) {
            btnOk.setOnClickListener(oklistener);
        } else {
            btnOk.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
        }

        dialog.show();
    }

    public static boolean checkLocEnabled(final Context context, LocationManager locManager) {

        /*
         * 判断定位服务是否开启，没有则引导用户设置
         */
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!network) {
            boolean gps = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!gps) {
                // 初始化一个AlertDialog对象
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();

                // //为dialog设置图标
                // alertDialog.setIcon(getResources().getDrawable(R.drawable.?));

                // 为dialog设置标题
                alertDialog.setTitle("设置");

                // 为dialog设置主体内容
                alertDialog.setMessage("尚未打开无线网络定位或GPS定位，是否设置?");

                // 为dialog设置一个按钮
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                });

                // 为dialog设置另一个按钮
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ((Activity) context).startActivityForResult(intent, 0);
                    }
                });
                // 显示dialog
                alertDialog.show();
                return false;
            }
        }
        return true;
    }

    /**
     * 根据图片uri 获取图片绝对路径
     *
     * @param context
     * @param uri
     * @return 图片绝对路径
     */
    public static String getFilePathByContentResolver(Context context, Uri uri) {
        if (null == uri) {
            return null;
        }
        Cursor c = context.getContentResolver().query(uri, null, null, null, null);
        String filePath = null;
        if (null == c) {
            throw new IllegalArgumentException("Query on " + uri + " returns null result.");
        }
        try {
            if ((c.getCount() != 1) || !c.moveToFirst()) {
            } else {
                filePath = c.getString(c.getColumnIndexOrThrow(MediaColumns.DATA));
            }
        } finally {
            c.close();
        }
        return filePath;
    }

    /**
     * 根据图片路径发送扫描广播更新相册 结合getFilePathByContentResolver(Context context, Uri
     * uri)使用
     *
     * @param path    图片路径
     * @param context 上下文
     */
    public static void sendBroadCast(String path, Context context) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(path));
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    /**
     * @param context
     * @param picPath     图片原始路径
     * @param name        设置的名称
     * @param description 描述
     */
    public static String savePicToDCIM(Context context, String picPath, String name, String description) {
        try {
            // 将图片存入系统相册，并设置名称
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), picPath, name, description);
            // 得到图片URI
            Uri uri = Uri.parse(path);
            // 根据URI得到图片在相册绝对地址
            String absolutePath = getFilePathByContentResolver(context, uri);
//            // 发送扫描广播，只对当前路径图片进行扫描
            sendBroadCast(absolutePath, context);
            // Log.e(TAG, "get Iamge form file,  path = " + path);
            return getSmallBitmapPath(picPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getSmallBitmapPath(String oldPathath) {
        if (oldPathath.startsWith("http")) {
            return oldPathath;
        }
        String name = oldPathath.substring(oldPathath.lastIndexOf("/") + 1, oldPathath.length());
        File newPathFile = new File(ConstStrings.INI_PATH + ConstStrings.INI_SUBMIT_FILE_PATH);
        if (!newPathFile.exists()) {
            newPathFile.mkdirs();
        }
        String newPath = ConstStrings.INI_PATH + ConstStrings.INI_SUBMIT_FILE_PATH + name;
        File file = new File(newPath);
        if (file.exists()) {//判断本地是否有缓存图片
            return newPath;
        } else {
            if (FileSizeUtil.getFileOrFilesSize(oldPathath, 2) > 200) {//判断文件是否大于200KB
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(oldPathath, options);
                options.inSampleSize = calculateInSampleSize(options, 480, 800);
                options.inJustDecodeBounds = false;
                Bitmap bm = BitmapFactory.decodeFile(oldPathath, options);
                if (bm == null) {
                    return null;
                }
                int degree = readPictureDegree(oldPathath);
                bm = rotateBitmap(bm, degree);
                try {
                    FileOutputStream outpicstream = new FileOutputStream(newPath);
                    bm.compress(Bitmap.CompressFormat.JPEG, 80, outpicstream);// 把数据写入文件
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return newPath;
            } else {
                return oldPathath;
            }
        }
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        return inSampleSize;
    }

    private static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        if (bitmap == null)
            return null;

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        // Setting post rotate to 90
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    /**
     * 照相功能
     *
     * @param activity
     * @param nameStr  名称
     * @return 图片绝对地址
     */
    public static String cameraMethod(Activity activity, String nameStr) {
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String strImgPath = "";
        String fileName = "";
        strImgPath = Myphotopath;// 存放照片的文件夹
        if (nameStr == null || "".equals(nameStr.trim())) {
            fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";// 照片命名
        } else {
            fileName = nameStr + ".jpg";// 照片命名
        }
        File out = new File(strImgPath);
        if (!out.exists()) {
            out.mkdirs();
        }
        out = new File(strImgPath, fileName);
        strImgPath = strImgPath + fileName;// 该照片的绝对路径
        Uri uri = Uri.fromFile(out);
//        imageCaptureIntent.putExtra(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.5);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1024);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, "portrait");
        activity.startActivityForResult(imageCaptureIntent, RESULT_CAPTURE_IMAGE);
        return strImgPath;
    }


//    public static void showImage(Activity activity, final String path, final View view, Handler handler) {
//        view.setVisibility(View.VISIBLE);
//        // 初始化
//        Animation scaleAnimation = new ScaleAnimation(0f, 1.0f, 0f, 1.0f, 0.5f, 0.5f);
//        scaleAnimation.setFillEnabled(true);
//
//        // 设置动画时间
//        scaleAnimation.setDuration(500);
//        view.setAnimation(scaleAnimation);
//        view.findViewById(R.id.ibtn_zoom_iv_header_back).setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                view.setVisibility(View.GONE);
//                Animation scaleAnimation = new ScaleAnimation(1f, 0.0f, 1f, 0.0f, 0.5f, 0.5f);
//                scaleAnimation.setAnimationListener(new AnimationListener() {
//
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        // view.setVisibility(View.GONE);
//                    }
//                });
//                // 设置动画时间
//                scaleAnimation.setDuration(500);
//                view.setAnimation(scaleAnimation);
//            }
//        });
//        final ImageView image = (ImageView) view.findViewById(R.id.common_image_imageview);
//        if (imageCache.containsKey(path)) {
//            SoftReference<Bitmap> soft = imageCache.get(path);
//            Bitmap bitmap = soft.get();
//            image.setImageBitmap(bitmap);
//        } else {
//            Bitmap bitmap1 = setBitmapSize(path, 1080);
//            imageCache.put(path, new SoftReference<Bitmap>(bitmap1));
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    image.setImageBitmap(imageCache.get(path).get());
//                }
//            });
//        }
//    }

    public static void showImage(final Activity activity, final String path, final View view, Handler handler) {
        FrameLayout layout = (FrameLayout) view;
        final ImageView image = (ImageView) layout.getChildAt(0);
        if (path.contains("http")) {
            String name = path.substring(path.lastIndexOf("/") + 1, path.length());
            String storagePath = SYSUtil.CACHE_DIR_NAME + name;
            Bitmap bitmap = NativeImageLoader.getBitmapFromMemCache(storagePath);
            if (bitmap != null) {
                image.setTag(path);
                animateShowImage(image, activity);
            } else {
                image.setTag(path);
                Picasso.with(activity).load(path).into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bmp = ((BitmapDrawable) image.getDrawable()).getBitmap();
                        if (bmp != null) {
                            String path = (String) image.getTag();
                            String name = path.substring(path.lastIndexOf("/") + 1, path.length());
                            String storagePath = SYSUtil.CACHE_DIR_NAME + name;
                            NativeImageLoader.mMemoryCache.put(storagePath, bmp);
                            animateShowImage(image, activity);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        } else {
            if (NativeImageLoader.getBitmapFromMemCache(path) != null) {
                image.setTag(path);
                animateShowImage(image, activity);
            } else {
                Bitmap bitmap1 = setBitmapSize(path, 1080);
                NativeImageLoader.mMemoryCache.put(path, bitmap1);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        image.setTag(path);
                        animateShowImage(image, activity);
                    }
                });
            }
        }
    }

    private static void animateShowImage(ImageView image, final Activity activity) {

        Intent intent = new Intent(activity, SpaceImageDetailActivity.class);
        int[] location = new int[2];
        image.getLocationOnScreen(location);
        intent.putExtra("locationX", location[0]);
        intent.putExtra("locationY", location[1]);
        intent.putExtra("path", image.getTag().toString());
        intent.putExtra("width", image.getWidth());
        intent.putExtra("height", image.getHeight());
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }


    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;

        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            // MmsLog.e(ISMS_TAG, "getExifOrientation():", ex);
        }

        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            if (orientation != -1) {
                // We only recognize a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                    default:
                        break;
                }
            }
        }
        return degree;
    }


    /**
     * @return 图片
     * @parambit
     */
    public static Bitmap setBitmapSize(String path, int size) {
        Bitmap bitmap = null;
        int scale = 1;
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, o);
        if (size != 0)
            while (o.outWidth / scale / 2 > size && o.outHeight / scale / 2 > size) {
                scale *= 2;
            }
        o.inSampleSize = scale;
        o.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, o);
        return bitmap;
    }

    public static void vibrate(Context context, long duration) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(duration);
    }

    /*
     * 判断是否为浮点数，包括double和float
     *
     * @param str 传入的字符串
     *
     * @return 是浮点数返回true,否则返回false
     */
    public static boolean isDouble(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-+]?[0-9]*\\.?[0-9]+$");
        return pattern.matcher(str).matches();
    }

    /**
     * IP地址是否合法
     *
     * @param context
     * @param ip1
     * @param ip2
     * @param ip3
     * @param ip4
     * @return
     */
    public static boolean isIpValid(Context context, String ip1, String ip2, String ip3, String ip4) {
        if (ip1.equals("") || ip2.equals("") || ip3.equals("") || ip4.equals("")) {
            Util.showToast(context, "IP地址填写不完整");
            return false;
        }
        if (Integer.valueOf(ip1) < 0 || Integer.valueOf(ip1) > 255) {
            Util.showToast(context, "IP地址段只可输入0~255之间的数");
            return false;
        } else if (Integer.valueOf(ip2) < 0 || Integer.valueOf(ip2) > 255) {
            Util.showToast(context, "IP地址段只可输入0~255之间的数");
            return false;
        } else if (Integer.valueOf(ip3) < 0 || Integer.valueOf(ip3) > 255) {
            Util.showToast(context, "IP地址段只可输入0~255之间的数");
            return false;
        } else if (Integer.valueOf(ip4) < 0 || Integer.valueOf(ip4) > 255) {
            Util.showToast(context, "IP地址段只可输入0~255之间的数");
            return false;
        }
        return true;
    }

    public static void setDateIntoText(Context context, View v) {
        setDateIntoText(context, v, "year");
    }

    /**
     * EditText的时间选择器弹框
     *
     * @param context
     * @param v       EditText
     */
    public static void setDateIntoText(Context context, View v, String type) {
        if (v != null && v instanceof TextView) {
            //隐藏软件盘
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            TextView textView = (TextView) v;
            Calendar c = Calendar.getInstance();
            SimpleDateFormat formatter = null;
            if ("-".equals(type)) {
                formatter = new SimpleDateFormat("yyyy-MM-dd");
            } else {
                formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            }
            formatter.setCalendar(c);
            Button button = null;
            try {
                // parse to a double
                c.setTime(formatter.parse(textView.getText().toString()));
            } catch (ParseException e) {
                // do nothing as should parse
            }
            Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
            String currentdatestr = formatter.format(curDate);
            DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                    context, currentdatestr);
            dateTimePicKDialog.dateTimePicKDialog(textView, "-");
            //获取焦点时不弹出软键盘
            textView.setInputType(InputType.TYPE_NULL);
        }
    }

    /**
     * 选择年月
     *
     * @param context
     * @param v
     * @param mType
     */
    public static void setDateYearIntoText(Context context, View v, String mType) {
        if (v != null && v instanceof TextView) {
            //隐藏软件盘
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            TextView textView = (TextView) v;
            String currentdatestr = "";
            if (mType.equals("year")) {
                currentdatestr = textView.getText().toString() + "-01";
            } else {
                currentdatestr = textView.getText().toString();
            }
            DatePickDialogUtil datePicKDialog = new DatePickDialogUtil(
                    context, currentdatestr);
            datePicKDialog.dateTimePicKDialog(textView, mType);
            //获取焦点时不弹出软键盘
            textView.setInputType(InputType.TYPE_NULL);
        }
    }


    /**
     * 判断输入框内容长度是否超限
     *
     * @param editTextArr
     * @return
     * @paramactivitiy
     * @paramnum
     */
    public static boolean checkContentLengthIsOverRun(EditText[] editTextArr, int minNum, int maxNum) {
        boolean isOverRun = false;
        if (editTextArr != null && editTextArr.length > 0) {
            for (int i = 0; i < editTextArr.length; i++) {
                if (editTextArr[i] != null) {
                    if (editTextArr[i].getText() != null) {
                        String contentStr = editTextArr[i].getText().toString();
                        if (!TextUtils.isEmpty(contentStr)) {
                            if (contentStr.length() > maxNum) {
                                setRequired(editTextArr[i], "密码长度不能大于" + maxNum + "个字符！");
                                isOverRun = true;
                                break;
                            } else {
                                if (contentStr.length() < minNum) {
                                    setRequired(editTextArr[i], "密码长度不能小于" + minNum + "个字符！");
                                    isOverRun = true;
                                    break;
                                } else {
                                    if (containsEmoji(contentStr)) {
                                        setRequired(editTextArr[i], "密码格式有误！");
                                        isOverRun = true;
                                        break;
                                    }
                                }
                            }
                        } else {
                            if (editTextArr[i].getTag() != null) {
                                String string = editTextArr[i].getTag().toString();
                                if (!TextUtils.isEmpty(string)) {
                                    setRequired(editTextArr[i], "请输入" + string + "！");
                                    isOverRun = true;
                                    break;
                                }
                            }
                        }
                    } else {
                        if (editTextArr[i].getTag() != null) {
                            String string = editTextArr[i].getTag().toString();
                            if (!TextUtils.isEmpty(string)) {
                                setRequired(editTextArr[i], "请输入" + string + "！");
                                isOverRun = true;
                                break;
                            }
                        }
                    }
                }
            }

        }
        return isOverRun;
    }


    public static void setRequired(View view, String... error) {
        shakeAction.setDuration(100);
        shakeAction.setRepeatCount(5);//动画执行5次
        view.startAnimation(shakeAction);
        view.setFocusable(true);
        view.requestFocus();//设置光标为选择状态
        view.setFocusableInTouchMode(true);
        if (view instanceof EditText) {
            ((EditText) view).setError(error[0]);
        }
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }


    /**
     * 计算TabLayout宽度，动态设置TabLayout的模式
     *
     * @param tabLayout
     */
    public static void dynamicSetTabLayoutMode(TabLayout tabLayout) {
        int tabWidth = calculateTabWidth(tabLayout);
        int screenWidth = getScreenWith();

        if (tabWidth <= screenWidth) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    private static int calculateTabWidth(TabLayout tabLayout) {
        int tabWidth = 0;
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            final View view = tabLayout.getChildAt(i);
            view.measure(0, 0); // 通知父view测量，以便于能够保证获取到宽高
            tabWidth += view.getMeasuredWidth();
        }
        return tabWidth;
    }

    public static int getScreenWith() {
        return MyApplication.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    public static boolean compareTime(String startTime, String endTime) {
        String formatType = "yyyy年MM月dd日";
        Long st = null;
        Long et = null;
        try {
            st = stringToLong(startTime, formatType);
            et = stringToLong(endTime, formatType);
            if (st < et) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }


    /**
     * 图片选取
     *
     * @paramflag
     */
    public static void imagePicker(Activity activity) {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        activity.startActivityForResult(intent, RESULT_PICKER_IMAGE_PHONE);

        Intent intent = new Intent(activity, PhotoPickerActivity.class);
        activity.startActivityForResult(intent, RESULT_CUSTOM_PICKER_IMAGE_PHONE);

    }

    /**
     * 把毫秒转换成：1：20：30这样的形式
     *
     * @param timeMs
     * @return
     */
    public static String stringForTime(int timeMs) {
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%02d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    //文件拷贝
    //要复制的目录下的所有非子目录(文件夹)文件拷贝
    public static int CopySdcardFile(String fromFile, String toFile) {
        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return 0;

        } catch (Exception ex) {
            return -1;
        }
    }
}
