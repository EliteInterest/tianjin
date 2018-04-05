package com.zx.tjmarketmobile.ui.caselegal;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.ComplainFileListAdapter;
import com.zx.tjmarketmobile.entity.CaseDetailEntity;
import com.zx.tjmarketmobile.entity.FileInfoEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.MyItemClickListener;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.FileUtil;
import com.zx.tjmarketmobile.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.widget.MultiPickResultView;

/**
 * Create By Stanny On 2017/3/20
 * 功能：案件资料Fragment
 */
public class CaseDetailFileFragment extends BaseFragment {

    private String fId = "";
    private ArrayList<String> photoPaths;
    private MultiPickResultView mprvPhoto;
    private RecyclerView rvDoc;
    private ComplainFileListAdapter mAdapter;
    private String filePath = "";
    private TextView tvMorePic;
    private boolean showAll = false;
    private List<FileInfoEntity> picList = new ArrayList<>();
    private List<FileInfoEntity> docList = new ArrayList<>();
    private ApiData getFileById = new ApiData(ApiData.HTTP_ID_caseGetAyxxDetailById);
    private ApiData fileDownload = new ApiData(ApiData.FILE_DOWNLOAD);

    public static CaseDetailFileFragment newInstance(String fId) {
        CaseDetailFileFragment details = new CaseDetailFileFragment();
        details.fId = fId;
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_file, container, false);
        mprvPhoto = (MultiPickResultView) view.findViewById(R.id.mprv_file);
        tvMorePic = (TextView) view.findViewById(R.id.tv_case_morePic);
        rvDoc = (RecyclerView) view.findViewById(R.id.rv_docList);
        rvDoc.setLayoutManager(mLinearLayoutManager);
        mAdapter = new ComplainFileListAdapter(getActivity(), docList);
        rvDoc.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                showToast("该文件不允许在移动端下载，请前往PC端查看");
//                Util.showYesOrNoDialog(getActivity(), "提示", "是否进行下载", "下载", "取消", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String downloadUrl = "http://" + ApiData.mIp + "/" + docList.get(position).getFilePath();
//                        filePath = "file/" + docList.get(position).getFileName();
//                        fileDownload.loadData(downloadUrl, filePath, false);
//                        Util.dialog.dismiss();
//                    }
//                }, null);
            }
        });
        tvMorePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAll = true;
                tvMorePic.setVisibility(View.GONE);
//                initImgView();
            }
        });
        getFileById.setLoadingListener(this);
        fileDownload.setLoadingListener(this);

        getFileById.loadData(fId);
        return view;
    }

    //当界面可见之后再进行数据加载（懒加载）
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
//            getFileById.loadData(fId);
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_caseGetAyxxDetailById:
                CaseDetailEntity caseDetailEntity = (CaseDetailEntity) b.getEntry();
                docList = caseDetailEntity.getFiles();
                docList.addAll(0, picList);
//                initImgView();//加载图片视图，屏蔽
                mAdapter.notifyDataSetChanged();
                break;
            case ApiData.FILE_DOWNLOAD:
                Util.closeProgressDialog();
                if (filePath.length() != 0) {
                    File file = new File(ConstStrings.getDownloadPath() + filePath);
                    FileUtil.openFile(getActivity(), file);
                }
                break;
            default:
                break;
        }
    }

    //初始化图片视图
    private void initImgView() {
//        if (picList.size() > 0) {
//            if (photoPaths == null) {
//                photoPaths = new ArrayList<>();
//            }
//            photoPaths.clear();
//            int size = picList.size();
//            tvMorePic.setVisibility(View.GONE);
//            if (!showAll && size > 6) {
//                size = 6;
//                tvMorePic.setVisibility(View.VISIBLE);
//            }
//            for (int i = 0; i < size; i++) {
//                String filePath = ConstStrings.INI_PATH + ConstStrings.INI_SUBMIT_FILE_PATH + picList.get(i).getFileName();
//                File file = new File(filePath);
//                if (file.exists()) {
//                    photoPaths.add(filePath);
//                } else {
//                    photoPaths.add("http://" + ApiData.mIp + "/" + picList.get(i).getFilePath());
////                    photoPaths.add(picList.get(i).getFilePath());
//                }
//            }
//            mprvPhoto.init(getActivity(), MultiPickResultView.ACTION_ONLY_SHOW, photoPaths);
//        }
    }

}
