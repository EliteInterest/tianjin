package com.zx.gamarketmobile.entity;

/**
 * Created by Xiangb on 2017/3/20.
 * 功能：
 */
public class FileInfoEntity {
    private String fileName;
    private String filePath;
    private String fileId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
