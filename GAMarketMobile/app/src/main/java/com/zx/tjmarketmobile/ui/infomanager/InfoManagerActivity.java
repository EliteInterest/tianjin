package com.zx.tjmarketmobile.ui.infomanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.GuideFunctionEntity;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.zoom.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class InfoManagerActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private InfoManageAdapter mAdapter;
    private List<GuideFunctionEntity> guideFunctionEntityList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_guild);

        addToolBar(true);
        hideRightImg();
        setMidText("信息管理");

        guideFunctionEntityList = new ArrayList<>();
        String[] names = new String[]{"标准信息", "特种设备", "许可证书", "计量器具"
                , "法律法规"
        };
        int[] ids = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
        int[] imgs = new int[]{R.mipmap.guide_search,
                R.mipmap.guide_analysis,
                R.mipmap.guide_sudden,
                R.mipmap.guide_supervise,
                R.mipmap.guide_case};

        for (int i = 0; i < names.length; i++) {
            GuideFunctionEntity guideFunctionEntity = new GuideFunctionEntity();
            guideFunctionEntity.setName(names[i]);
            guideFunctionEntity.setId(ids[i]);
            guideFunctionEntity.setImg(imgs[i]);
            guideFunctionEntityList.add(guideFunctionEntity);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.info_recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecyclerView.setAdapter(mAdapter = new InfoManageAdapter());
    }


    private class InfoManageAdapter extends RecyclerView.Adapter<InfoManageAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(
                    LayoutInflater.from(InfoManagerActivity.this).inflate(R.layout.activity_guide_item_home, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.tvName.setText(guideFunctionEntityList.get(position).getName());
            holder.imgBg.setBackground(getDrawable(guideFunctionEntityList.get(position).getImg()));
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    int id = guideFunctionEntityList.get(pos).getId();
                    switch (id) {
                        case 0:
                            toHome(0);
                            break;
                        case 1:
                            toHome(1);
                            break;
                        case 2:
                            toHome(2);
                            break;
                        case 3:
                            toHome(3);
                            break;
                        case 4:
                            toHome(4);
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return guideFunctionEntityList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tvName;
            ImageView imgBg;
            LinearLayout linearLayout;

            public MyViewHolder(View view) {
                super(view);
                tvName = (TextView) view.findViewById(R.id.tv_name);
                imgBg = (ImageView) view.findViewById(R.id.img_bg);
                linearLayout = (LinearLayout) view.findViewById(R.id.ll_item_layout);
            }
        }
    }


//    @Override
//    public void onBackPressed() {
//        MyApplication.getInstance().remove(GuideActivity.class);
//        super.onBackPressed();
//    }

    private void toHome(int item) {
        Intent intent = new Intent(InfoManagerActivity.this, InfoHomeActivity.class);
//        Intent intent = new Intent(InfoManagerActivity.this, HomeActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

}
