package com.zx.tjmarketmobile.ui.caselegal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.CaseInfoEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.ui.base.BaseFragment;

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
    private String imgUrl;

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
        imgUrl = ApiData.baseUrl + "/TJCase/caseFlow/caseflowImg.do?processId=" + caseInfo.getProcessId();

        ivChart = (ImageView) view.findViewById(R.id.iv_case_chart);
        ivChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgUrl != null&&imgUrl.length()>0) {
                    ArrayList<String> paths = new ArrayList<>();
                    paths.add(imgUrl);
                    PhotoPreview.builder()
                            .setPhotos(paths)
                            .setAction(MultiPickResultView.ACTION_ONLY_SHOW)
                            .start(getActivity());
                }
            }
        });

        Glide.with(getActivity())
                .load(imgUrl)
                .into(ivChart);
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
