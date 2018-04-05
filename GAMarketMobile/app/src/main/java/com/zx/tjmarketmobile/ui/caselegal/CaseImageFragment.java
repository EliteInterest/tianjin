package com.zx.tjmarketmobile.ui.caselegal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.CaseDetailEntity;
import com.zx.tjmarketmobile.entity.CaseInfoEntity;
import com.zx.tjmarketmobile.entity.FileInfoEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.iwf.photopicker.listener.OnDeleteListener;
import me.iwf.photopicker.widget.MultiPickResultView;

/**
 * Created by Xiangb on 2017/4/14.
 * 功能：
 */

public class CaseImageFragment extends BaseFragment {
    private CaseExcuteActivity activity;
    public MultiPickResultView mprvPhoto;//图片上传界面
    private ArrayList<String> photoPaths;
    private Button btnSubmit;
    private CaseInfoEntity mEntity;
    public HashMap<String, String> recordFileInfo = new HashMap<>();
    private List<FileInfoEntity> picList = new ArrayList<>();
    private ApiData picUpload = new ApiData(ApiData.FILE_UPLOAD);
    private ApiData caseSave = new ApiData(ApiData.HTTP_ID_caseSaveAyfj);
    private ApiData getFileById = new ApiData(ApiData.HTTP_ID_caseGetAyxxDetailById);
    private ApiData deleteFile = new ApiData(ApiData.HTTP_ID_Case_Delete_Img);

    public static CaseImageFragment newInstance(CaseExcuteActivity activity, CaseInfoEntity mEntity) {
        CaseImageFragment details = new CaseImageFragment();
        details.mEntity = mEntity;
        details.activity = activity;
        return details;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit_image, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mprvPhoto = (MultiPickResultView) view.findViewById(R.id.mprv_photo);
        btnSubmit = (Button) view.findViewById(R.id.btn_case_imagesubmit);
        picUpload.setLoadingListener(this);
        caseSave.setLoadingListener(this);
        getFileById.setLoadingListener(this);
        deleteFile.setLoadingListener(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.showYesOrNoDialog(getActivity(), "提示", "是否提交？", "提交", "取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Util.dialog.dismiss();
                        //图片上传
                        List<String> localPath = new ArrayList<>();
                        for (int i = 0; i < photoPaths.size(); i++) {
                            if (!photoPaths.get(i).startsWith("http")) {
                                localPath.add(Util.getSmallBitmapPath(photoPaths.get(i)));
                            }
                        }
                        if (localPath.size() > 0) {
                            if (localPath.size() > 0) {
                                String[] paths = new String[localPath.size()];
                                for (int i = 0; i < localPath.size(); i++) {
                                    paths[i] = localPath.get(i);
                                }
                                picUpload.loadData(0, paths, "case");
                            }
                        } else {
                            showToast("未添加新图片");
                        }
                    }
                }, null);
            }
        });
        getFileById.loadData(mEntity.getId());
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        switch (id) {
            case ApiData.FILE_UPLOAD:
                Util.closeProgressDialog();
                String realNameListStr = b.getEntry().toString();
                StringBuffer realNameBuffer = new StringBuffer();
                String fileNameListStr;
                if (photoPaths != null && photoPaths.size() > 0) {
                    for (int i = 0; i < photoPaths.size(); i++) {
                        String path = photoPaths.get(i);
                        if (!TextUtils.isEmpty(path)) {
                            String name = path.substring(path.lastIndexOf("/") + 1, path.length());
                            realNameBuffer.append(",").append(name);
                        }
                    }
                    fileNameListStr = realNameBuffer.toString();
                    fileNameListStr = fileNameListStr.substring(1);
                    if (!TextUtils.isEmpty(realNameListStr)) {
                        String fUploadPerson = userInfo.getUserName();
                        if (!TextUtils.isEmpty(mEntity.getId()) && !TextUtils.isEmpty(fUploadPerson)) {
                            caseSave.loadData(mEntity.getId(), userInfo.getId(), realNameListStr, fileNameListStr);
                        }
                    }
                }
                break;
            case ApiData.HTTP_ID_caseGetAyxxDetailById:
                CaseDetailEntity fileEntity = (CaseDetailEntity) b.getEntry();
                picList = fileEntity.getFiles();
                initImgView();
                dismissProgressDialog();
                break;
            case ApiData.HTTP_ID_caseSaveAyfj:
                showToast("上传成功");
                getFileById.loadData(mEntity.getId());
                break;
            case ApiData.HTTP_ID_Case_Delete_Img:
                if (b.isSuccess()) {
                    Toast.makeText(getActivity(), "图片删除成功！", Toast.LENGTH_SHORT).show();
                    mprvPhoto.notifyDataSetChanged();
                    dismissProgressDialog();
                }
                break;
            default:
                break;
        }
    }

    //初始化图片视图
    private void initImgView() {
        if (photoPaths == null) {
            photoPaths = new ArrayList<>();
        }
        if (picList.size() > 0) {
            photoPaths.clear();
            int size = picList.size();
            for (int i = 0; i < size; i++) {
                String path = "http://" + ApiData.mIp + "/" + picList.get(i).getUrl();
                photoPaths.add(path);
                recordFileInfo.put(path, picList.get(i).getId());
            }
        }
        if (mprvPhoto.photoAdapter == null) {
            mprvPhoto.setMaxPicNum(30)
                    .init(getActivity(), MultiPickResultView.ACTION_SELECT, photoPaths, new OnDeleteListener() {
                        @Override
                        public void OnDetele(int positon) {
                            final int finalI = positon;
                            if (mprvPhoto.photoAdapter.photoPaths.get(positon).startsWith("http")) {
                                Util.showYesOrNoDialog(getActivity(), "提示", "是否删除该图片？", "删除", "取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Util.dialog.dismiss();
                                        deleteFile.loadData(recordFileInfo.get(photoPaths.get(finalI)));
                                        photoPaths.remove(finalI);
                                    }
                                }, null);
                            } else {
                                photoPaths.remove(positon);
                                mprvPhoto.notifyDataSetChanged();
                            }
                        }
                    });
        } else {
            mprvPhoto.notifyDataSetChanged();
        }
    }

}
