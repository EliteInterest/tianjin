package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.PicEntity;
import com.zx.tjmarketmobile.listener.PicListener;
import com.zx.tjmarketmobile.util.Util;
import com.zx.tjmarketmobile.zoom.NativeImageLoader;

import java.util.List;

/**
 * Create By Xiangb On 2016/9/22
 * 功能：图片适配器
 */
public class PicAdapter extends BaseAdapter {

    private Context context;
    private List<PicEntity> datas;
    private PicListener listener;
    private boolean deleteFlag;

    public PicAdapter(Context context, List<PicEntity> datas, PicListener listener) {
        this.context = context;
        this.datas = datas;
        this.listener = listener;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int arg0) {
        return datas.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, View arg1, ViewGroup arg2) {
        final ViewHolder holder;
        arg1 = View.inflate(context, R.layout.item_add_pic_list, null);
        holder = new ViewHolder();
        holder.iv = (ImageView) arg1.findViewById(R.id.item_info_pic_img);
        holder.add = (ImageView) arg1.findViewById(R.id.item_info_pic_add);
        holder.del = (ImageButton) arg1.findViewById(R.id.item_info_pic_del);
        final String path = (String) datas.get(position).getPath();
        if (!"default".equals(path)) {
            holder.add.setVisibility(View.GONE);
            holder.iv.setVisibility(View.VISIBLE);
            holder.iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.__picker_ic_broken_image_black_48dp));
            NativeImageLoader.getInstance().loadNativeImage(context, path, holder.iv);
            if (deleteFlag && position < getCount()) {
                holder.del.setVisibility(View.VISIBLE);
            } else {
                holder.del.setVisibility(View.GONE);
            }
        } else {

            holder.add.setVisibility(View.VISIBLE);
            holder.del.setVisibility(View.GONE);
            holder.iv.setVisibility(View.GONE);
        }
        holder.del.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Util.showDeleteDialog(context, "是否去掉选择图片?", null, "确定", null, new OnClickListener() {

                    @Override
                    public void onClick(View v) {
//						if (context instanceof EntityDetailActivity){
//							EntityDetailActivity mActivity = (EntityDetailActivity)context;
//							if (mActivity.entityFileFragment!=null){
//								HashMap<String,String>  hashMap = mActivity.entityFileFragment.recordFileInfo;
//								mActivity.deleteFileInfoData.loadData(hashMap.get(datas.get(position).getPath()));
//							}
//						}
                        datas.remove(position);
                        notifyDataSetChanged();
                        Util.dialog.cancel();


                    }
                }, null);
            }
        });
        holder.add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.typeListener();
            }
        });
        return arg1;
    }


    class ViewHolder {
        ImageView iv;
        ImageView add;
        ImageButton del;
    }

    public void deleteButton(boolean flag) {
        if (deleteFlag == flag) {
            return;
        }
        deleteFlag = flag;
        notifyDataSetChanged();
    }
}
