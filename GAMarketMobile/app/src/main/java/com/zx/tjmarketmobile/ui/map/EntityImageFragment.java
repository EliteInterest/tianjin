package com.zx.tjmarketmobile.ui.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.EntityDetail;
import com.zx.tjmarketmobile.entity.HttpZtEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.iwf.photopicker.listener.OnDeleteListener;
import me.iwf.photopicker.widget.MultiPickResultView;

import static com.zx.tjmarketmobile.http.ApiData.HTTP_ID_deleteFileInfo;
import static com.zx.tjmarketmobile.http.ApiData.HTTP_ID_savaOfficialPicture;

/**
 * Created by Xiangb on 2017/4/14.
 * 功能：
 */

public class EntityImageFragment extends BaseFragment {
    private EntityDetailActivity activity;
    public MultiPickResultView mprvPhoto;//图片上传界面
    private ArrayList<String> photoPaths = new ArrayList<>();
    private Button btnSubmit;
    private EntityDetail mEntity;
    private HttpZtEntity ztEntity;
    public HashMap<String, String> recordFileInfo = new HashMap<>();
    private ApiData deleteFileInfoData = new ApiData(HTTP_ID_deleteFileInfo);
    private ApiData picUpload = new ApiData(ApiData.FILE_UPLOAD);
    private ApiData savaOfficialPictureInfoData = new ApiData(HTTP_ID_savaOfficialPicture);
    private ApiData taskData = new ApiData(ApiData.HTTP_ID_entity_detail);

    public static EntityImageFragment newInstance(EntityDetailActivity activity, EntityDetail mEntity) {
        EntityImageFragment details = new EntityImageFragment();
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

    private void initData() {
        photoPaths.clear();
        recordFileInfo.clear();
//        for (int i = 0; i < mEntity.getImg().size(); i++) {
//            if (!TextUtils.isEmpty(mEntity.getImg().get(i).getfSaveName())) {
//                String id = mEntity.getImg().get(i).getfGuid();
//                String path = "http://" + ApiData.getmIp() + "/" + mEntity.getImg().get(i).getfSaveName();
//                photoPaths.add(path);
//                recordFileInfo.put(path, id);
//            }
//        }
        mprvPhoto.notifyDataSetChanged();
    }

    private void initView(View view) {
        mprvPhoto = (MultiPickResultView) view.findViewById(R.id.mprv_photo);
        btnSubmit = (Button) view.findViewById(R.id.btn_case_imagesubmit);
        picUpload.setLoadingListener(this);
        savaOfficialPictureInfoData.setLoadingListener(this);
        deleteFileInfoData.setLoadingListener(this);
        taskData.setLoadingListener(this);

        ztEntity = (HttpZtEntity) getActivity().getIntent().getSerializableExtra("ztEntity");

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
                                picUpload.loadData(0, paths, "OfficialPicture");
                            }
                        } else {
                            showToast("未添加新图片");
                        }
                    }
                }, null);
            }
        });
        mprvPhoto.setMaxPicNum(30)
                .init(getActivity(), MultiPickResultView.ACTION_SELECT, photoPaths, new OnDeleteListener() {
                    @Override
                    public void OnDetele(final int positon) {
                        if (mprvPhoto.photoAdapter.photoPaths.get(positon).startsWith("http")) {
                            Util.showYesOrNoDialog(getActivity(), "提示", "是否删除该图片？", "删除", "取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Util.dialog.dismiss();
                                    deleteFileInfoData.loadData(recordFileInfo.get(photoPaths.get(positon)));
                                    photoPaths.remove(positon);
                                }
                            }, null);
                        } else {
                            photoPaths.remove(positon);
                            mprvPhoto.notifyDataSetChanged();
                        }
                    }
                });
        initData();
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
                        if (!path.startsWith("http") && !TextUtils.isEmpty(path)) {
                            String name = path.substring(path.lastIndexOf("/") + 1, path.length());
                            realNameBuffer.append(",").append(name);
                        }
                    }
                    fileNameListStr = realNameBuffer.toString();
                    fileNameListStr = fileNameListStr.substring(1);
//                    if (!TextUtils.isEmpty(realNameListStr)) {
//                        String fEntityGuid = mEntity.EntityInfo.fEntityGuid;
//                        String fUploadPerson = userInfo.getUserName();
//                        if (!TextUtils.isEmpty(fEntityGuid) && !TextUtils.isEmpty(fUploadPerson)) {
//                            savaOfficialPictureInfoData.loadData(fEntityGuid, realNameListStr, fileNameListStr, userInfo.getUserName());
//                        }
//                    }
                }
                break;
            case ApiData.HTTP_ID_savaOfficialPicture:
                showToast("上传成功");
//                taskData.loadData(userInfo.getId(), ztEntity.getGuid(), ztEntity.getCreditLevel(), ztEntity.getfEntityType());
                break;
            case ApiData.HTTP_ID_entity_detail:
                dismissProgressDialog();
                mEntity = (EntityDetail) b.getEntry();
                initData();
                break;
            case ApiData.HTTP_ID_deleteFileInfo:
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

}
