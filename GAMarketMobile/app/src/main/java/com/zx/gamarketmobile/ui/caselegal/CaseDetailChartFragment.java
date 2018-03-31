package com.zx.gamarketmobile.ui.caselegal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.entity.CaseInfoEntity;
import com.zx.gamarketmobile.ui.base.BaseFragment;

import java.io.InputStream;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPreview;
import me.iwf.photopicker.widget.MultiPickResultView;

/**
 * Create By Stanny On 2017/3/20
 * 功能：流程图Fragment
 */
public class CaseDetailChartFragment extends BaseFragment {

    private ImageView ivChart;
    private String fHjbm;
    private String filename;
    private Bitmap image;

    public static CaseDetailChartFragment newInstance(CaseInfoEntity caseInfo, boolean showExcute) {
        CaseDetailChartFragment details = new CaseDetailChartFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("caseInfo", caseInfo);
        bundle.putBoolean("showEx", showExcute);
        details.setArguments(bundle);
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_chart, container, false);


        CaseInfoEntity caseInfo = (CaseInfoEntity) getArguments().getSerializable("caseInfo");
        boolean showExcute = getArguments().getBoolean("showEx");
        fHjbm = caseInfo.getfHjBm();
        if (showExcute && caseInfo.getPROC_DEF_ID_().indexOf("AJYQLC") != -1) {//延期流程
            if ("延期申请".equals(caseInfo.getfTaskName())) {
                filename = "delay_yqsq.png";
            } else if ("延期审查".equals(caseInfo.getfTaskName())) {
                filename = "delay_yqsc.png";
            } else if ("延期审批".equals(caseInfo.getfTaskName())) {
                filename = "delay_yqsp.png";
            }
        } else if (showExcute && caseInfo.getPROC_DEF_ID_().indexOf("AJXALC") != -1) {//销案流程
            if ("销案申请".equals(caseInfo.getfTaskName())) {
                filename = "destory_xasq.png";
            } else if ("销案审查".equals(caseInfo.getfTaskName())) {
                filename = "destory_xasc.png";
            } else if ("销案审批".equals(caseInfo.getfTaskName())) {
                filename = "destory_xasp.png";
            }
        }
        if (filename == null) {
            filename = "case_" + fHjbm + ".png";
        }

        ivChart = (ImageView) view.findViewById(R.id.iv_case_chart);
        setImgBg();
        ivChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image != null) {
                    String path = "file:///android_asset/" + filename;
                    ArrayList<String> paths = new ArrayList<>();
                    paths.add(path);
                    PhotoPreview.builder()
                            .setPhotos(paths)
                            .setAction(MultiPickResultView.ACTION_ONLY_SHOW)
                            .start(getActivity());
                }
            }
        });
        return view;
    }

    private void setImgBg() {
        try {
            InputStream is = getActivity().getResources().getAssets().open(filename);
            image = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ivChart.setImageBitmap(image);
    }

}
