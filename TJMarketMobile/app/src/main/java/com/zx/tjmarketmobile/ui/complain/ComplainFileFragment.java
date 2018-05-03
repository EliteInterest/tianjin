package com.zx.tjmarketmobile.ui.complain;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.CaseCompDetailInfoAdapter;
import com.zx.tjmarketmobile.adapter.CompFileListAdapter;
import com.zx.tjmarketmobile.entity.ComplainInfoDetailsBean;
import com.zx.tjmarketmobile.entity.ComplainInfoDetailsBean.BaseInfoBean.FFileNameBean;
import com.zx.tjmarketmobile.entity.KeyValueInfo;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.FileUtil;
import com.zx.tjmarketmobile.util.PhotoRecordUtil;
import com.zx.tjmarketmobile.util.Util;
import com.zx.tjmarketmobile.util.ZXItemClickSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.widget.MultiPickResultView;

public class ComplainFileFragment extends BaseFragment {
    private CaseCompDetailInfoAdapter mCompAdapter;
    private RecyclerView mRvInfo;
    private MultiPickResultView mprvImage;
    private RecyclerView rvFile;
    private String filePath = "";
    private int type = 0;
    private ComplainInfoDetailsBean detailsBean;
    private List<KeyValueInfo> dataInfoList = new ArrayList<>();
    private CompFileListAdapter compFileListAdapter;

    private ApiData fileDownload = new ApiData(ApiData.FILE_DOWNLOAD);
    private ArrayList<String> imgPath = new ArrayList<>();
    private List<ComplainInfoDetailsBean.BaseInfoBean.FFileNameBean> fileList = new ArrayList<>();

    private ApiData updateFile = new ApiData(ApiData.FILE_UPLOAD);

    public static ComplainFileFragment newInstance(ComplainInfoDetailsBean detailsBean, int type) {
        ComplainFileFragment details = new ComplainFileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", detailsBean);
        bundle.putInt("type", type);
        details.setArguments(bundle);
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complain_file, container, false);
        mRvInfo = view.findViewById(R.id.rv_normal_view);
        rvFile = view.findViewById(R.id.rv_video);
        view.findViewById(R.id.srl_normal_layout).setEnabled(false);
        mprvImage = view.findViewById(R.id.mprv_complain);
        detailsBean = (ComplainInfoDetailsBean) getArguments().getSerializable("bean");

        fileDownload.setLoadingListener(this);

        List<FFileNameBean> fileNameBeans = detailsBean.getBaseInfo().getFFilename();
        for (FFileNameBean fileNameBean : fileNameBeans) {
            if (fileNameBean.getRealName().endsWith("png") || fileNameBean.getRealName().endsWith("jpg")) {
                imgPath.add(ApiData.baseUrl + "/" + fileNameBean.getSavePath() + "/" + fileNameBean.getSaveName());
            } else {
                fileList.add(fileNameBean);
            }
        }

        mprvImage.init(getActivity(), MultiPickResultView.ACTION_ONLY_SHOW, imgPath);
        rvFile.setLayoutManager(new LinearLayoutManager(getActivity()));
        compFileListAdapter = new CompFileListAdapter(getActivity(), fileList);
        rvFile.setAdapter(compFileListAdapter);

        ZXItemClickSupport.addTo(rvFile)
                .setOnItemClickListener(new ZXItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View view) {
//                        showToast("该文件不允许在移动端下载，请前往PC端查看");
                        Util.showYesOrNoDialog(getActivity(), "提示", "是否进行下载", "下载", "取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String downloadUrl = ApiData.baseUrl + "/" + fileList.get(position).getSavePath() + "/" + fileList.get(position).getSaveName();
                                filePath = "file/" + fileList.get(position).getRealName();
                                fileDownload.loadData(downloadUrl, filePath, false);
                                Util.dialog.dismiss();
                            }
                        }, null);
                    }
                });

        updateFile.setLoadingListener(this);
        mRvInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        getDataList();
        mCompAdapter = new CaseCompDetailInfoAdapter(getActivity(), dataInfoList);
        ZXItemClickSupport.addTo(mRvInfo)
                .setOnItemClickListener(new ZXItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                        if (dataInfoList.get(position).key.contains("电话") && dataInfoList.get(position).value.length() > 0) {
                            PhotoRecordUtil.doPhotoRecord(getActivity(), dataInfoList.get(position).value, new PhotoRecordUtil.PhotoRecordListener() {
                                @Override
                                public void onPhotoRecord(File file) {
                                    Map<String, String> map = new HashMap<>();
                                    map.put("itemId", detailsBean.getBaseInfo().getFGuid());
                                    map.put("tableName", "tComplaintsInfo");
                                    map.put("field", "fFileName");
                                    updateFile.loadData(0, new String[]{file.getPath()}, "/TJComplaint/file/uploadFiles.do", map);
                                }
                            });
                        }
                    }
                });
        mRvInfo.setAdapter(mCompAdapter);
        return view;
    }

    private void getDataList() {
        ComplainInfoDetailsBean.BaseInfoBean baseInfoBean = detailsBean.getBaseInfo();
        dataInfoList.clear();
        KeyValueInfo info = new KeyValueInfo("反馈内容: ", baseInfoBean.getFFeedbackContent());
        dataInfoList.add(info);
        info = new KeyValueInfo("答复内容: ", baseInfoBean.getfReplyContent());
        dataInfoList.add(info);
        info = new KeyValueInfo("回访结果: ", baseInfoBean.getfReviewResult());
        dataInfoList.add(info);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.FILE_UPLOAD:
                showToast("录音文件上传成功");
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
}
