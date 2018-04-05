package com.zx.tjmarketmobile.ui.mainbase;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.MessageAdapter;
import com.zx.tjmarketmobile.entity.HttpEventEntity;
import com.zx.tjmarketmobile.entity.HttpMsgListEntity;
import com.zx.tjmarketmobile.entity.HttpTaskEntity;
import com.zx.tjmarketmobile.entity.MsgEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.MyItemClickListener;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.Util;

/**
 * Create By Xiangb On 2016/9/19
 * 功能：消息中心
 */
public class MessageCenterActivity extends BaseActivity implements MyItemClickListener {
    private TextView mTvTotal, mTvNotread;
    private SwipeRefreshLayout mMessageSRl;
    private RecyclerView mMessageRV;
    private MessageAdapter mMessageAdapter;

    private HttpMsgListEntity mMsgListEntity = null;

    private ApiData mLoadMsgData = new ApiData(ApiData.HTTP_ID_msg_center);
    private ApiData mLoadTaskDetailData = new ApiData(ApiData.HTTP_ID_searchtask_detail);
    private ApiData mLoadEventDetail = new ApiData(ApiData.HTTP_ID_searchevent_detail);
    private ApiData mLoadSetMsgRead = new ApiData(ApiData.HTTP_ID_set_msg_read);

    private MsgEntity mCurrentMsg;
//	private ApiData mLoadMsg

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagecenter);

        addToolBar(true);
        hideRightImg();
        setMidText("消息中心");

        mLoadMsgData.setLoadingListener(this);
        mLoadTaskDetailData.setLoadingListener(this);
        mLoadEventDetail.setLoadingListener(this);
        mLoadSetMsgRead.setLoadingListener(this);

        mTvTotal = (TextView) findViewById(R.id.tv_msgcenter_total_count);
        mTvNotread = (TextView) findViewById(R.id.tv_msgcenter_notread_count);
        mMessageRV = (RecyclerView) findViewById(R.id.rv_normal_view);
        mMessageSRl = (SwipeRefreshLayout) findViewById(R.id.srl_normal_layout);
        mMessageSRl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mLoadMsgData.loadData(userInfo.getId());
            }
        });
        mMessageRV.setLayoutManager(new LinearLayoutManager(this));
//		mLoadMsgData.loadData(userInfo.getId());
    }

    protected void getMsgDetail(MsgEntity msg) {
        if (msg == null) {
            return;
        }
        String type = msg.getType();
        if ("启动".equals(type)) {//打开应急任务详情
            mLoadEventDetail.loadData(msg.getEventId(), userInfo.getId());

        } else if ("指令下发".equals(type)) {//打开指令内容
            Util.showDetailInfoDialog(this, "应急指令", msg.getContent(),
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (Util.dialog != null && Util.dialog.isShowing()) {
                                Util.dialog.cancel();
                            }
                            mLoadMsgData.loadData(userInfo.getId());
                        }
                    }, Linkify.PHONE_NUMBERS);
            mLoadSetMsgRead.loadData(msg.getGuid(), userInfo.getId());

        } else if ("投诉举报任务指派".equals(type)) {//打开投诉举报任务详情
            mLoadTaskDetailData.loadData(msg.getContent(), 1);
            mLoadSetMsgRead.loadData(msg.getGuid(), userInfo.getId());
        }
    }

    private void initData() {
        if (mMsgListEntity != null) {
            mTvTotal.setText(mMsgListEntity.getTotal() + "");
            mTvNotread.setText(mMsgListEntity.getNoread() + "");
            mMessageAdapter = new MessageAdapter(this, mMsgListEntity.getMsgList());
            mMessageRV.setAdapter(mMessageAdapter);
            mMessageAdapter.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(View view, int position) {

        mCurrentMsg = mMsgListEntity.getMsgList().get(position);
        getMsgDetail(mCurrentMsg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoadMsgData.loadData(userInfo.getId());
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            Intent intent = new Intent(this, HomeActivity.class);
//            startActivity(intent);
//            this.finish();
//            Util.activity_Out(this);
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        mMessageSRl.setRefreshing(false);
        switch (id) {
            case ApiData.HTTP_ID_msg_center: {
                if (b.isSuccess()) {
                    mMsgListEntity = (HttpMsgListEntity) b.getEntry();
                    initData();
                }
            }
            break;
            case ApiData.HTTP_ID_searchtask_detail: {
                if (mCurrentMsg != null) {
                    HttpTaskEntity task = (HttpTaskEntity) b.getEntry();
                    task.setTaskType(1);
                    task.setGuid(mCurrentMsg.getContent());

//                    Intent intent = new Intent(this, ComplainDetailActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("entity", task);
//                    bundle.putSerializable("procedure", task.getStatus());
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                    Util.activity_In(this);
                }

            }
            break;
            case ApiData.HTTP_ID_searchevent_detail: {
                HttpEventEntity event = (HttpEventEntity) b.getEntry();
//                Intent intent = new Intent(this, EventEntityActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("entity", event);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                Util.activity_In(this);
            }
            break;

            default:
                break;
        }
    }

}
