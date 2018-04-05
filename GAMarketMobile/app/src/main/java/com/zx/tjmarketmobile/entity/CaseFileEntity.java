package com.zx.tjmarketmobile.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2017/3/20.
 * 功能：案件文件实体类
 */
public class CaseFileEntity {

    private List<FileInfoEntity> picList = new ArrayList<>();
    private List<FileInfoEntity> docList = new ArrayList<>();

    public List<FileInfoEntity> getPicList() {
        return picList;
    }

    public void setPicList(List<FileInfoEntity> picList) {
        this.picList = picList;
    }

    public List<FileInfoEntity> getDocList() {
        return docList;
    }

    public void setDocList(List<FileInfoEntity> docList) {
        this.docList = docList;
    }
}
