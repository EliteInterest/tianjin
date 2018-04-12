package com.zx.tjmarketmobile.ui.mainbase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.R.id;
import com.zx.tjmarketmobile.entity.GuideFunctionEntity;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.ui.infomanager.InfoManagerActivity;
import com.zx.tjmarketmobile.ui.map.WorkInMapShowActivity;
import com.zx.tjmarketmobile.ui.system.SettingsActivity;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.MyApplication;
import com.zx.tjmarketmobile.zoom.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private List<GuideFunctionEntity> guideFunctionEntityList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        addToolBar(false);

        guideFunctionEntityList = new ArrayList<>();
        String[] names = new String[]{"主体查询", "监管任务", "投诉举报", "案件执法", "信息管理", "统计分析", "设置"};
        int[] ids = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
        int[] imgs = new int[]{R.mipmap.guide_search,
                R.mipmap.guide_supervise,
                R.mipmap.guide_complain,
                R.mipmap.guide_case,
                R.mipmap.guide_info,
                R.mipmap.guide_analysis,
//                R.mipmap.guide_synegy,
//                R.mipmap.guid_equip,
//                R.mipmap.guide_msg,
                R.mipmap.guide_setting};

        for (int i = 0; i < names.length; i++) {
            GuideFunctionEntity guideFunctionEntity = new GuideFunctionEntity();
            guideFunctionEntity.setName(names[i]);
            guideFunctionEntity.setId(ids[i]);
            guideFunctionEntity.setImg(imgs[i]);
            guideFunctionEntityList.add(guideFunctionEntity);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());


    }


    private class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(
                    LayoutInflater.from(GuideActivity.this).inflate(R.layout.activity_guide_item_home, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.tvName.setText(guideFunctionEntityList.get(position).getName());
            holder.imgBg.setBackground(getDrawable(guideFunctionEntityList.get(position).getImg()));
            holder.linearLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    int id = guideFunctionEntityList.get(pos).getId();
                    switch (id) {
                        case 0://主体查询
                            Intent mapIntent = new Intent(GuideActivity.this, WorkInMapShowActivity.class);
                            mapIntent.putExtra("type", ConstStrings.MapType_Main);
                            startActivity(mapIntent);
                            break;
                        case 1://监管任务
                            toHome(0);
                            break;
                        case 2://投诉举报
                            toHome(1);
                            break;
                        case 3://案件执法
                            toHome(2);
                            break;
                        case 4://信息管理
//                            toHome(3);
                            startActivity(new Intent(GuideActivity.this, InfoManagerActivity.class));
                            break;
                        case 5://统计分析
                            toHome(4);
                            break;
                        case 6://帮助
                            startActivity(new Intent(GuideActivity.this, SettingsActivity.class));
//                            startActivity(new Intent(GuideActivity.this, SynergyActivity.class));
                            break;
                        case 7: //设置
//                            startActivity(new Intent(GuideActivity.this, EquipSearchActivity.class));
                            break;
//                        case 7:
//                            startActivity(new Intent(GuideActivity.this, MessageCenterActivity.class));
//                            break;
//                        case 8:
//                            startActivity(new Intent(GuideActivity.this, SettingsActivity.class));
//                            break;
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

        class MyViewHolder extends ViewHolder {

            TextView tvName;
            ImageView imgBg;
            LinearLayout linearLayout;

            public MyViewHolder(View view) {
                super(view);
                tvName = (TextView) view.findViewById(id.tv_name);
                imgBg = (ImageView) view.findViewById(id.img_bg);
                linearLayout = (LinearLayout) view.findViewById(id.ll_item_layout);
            }
        }
    }


    @Override
    public void onBackPressed() {
        MyApplication.getInstance().remove(LoginActivity.class);
        super.onBackPressed();
    }

    private void toHome(int item) {
        Intent intent = new Intent(GuideActivity.this, HomeActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

}
