package com.zx.tjmarketmobile.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.rarfile.FileHeader;

/**
 * Created by Xiangb on 2017/2/24.
 * 功能：
 */
public class UnRarOrZip {
    public static final int start = 0;
    public static final int percent = 1;
    public static final int end = 2;
    public static final int error = -1;

    public static void unRar(final Handler handler, final String sourceRar, final String destDir,String filetype) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    handler.sendEmptyMessage(start);
                    Archive a = null;
                    FileOutputStream fos = null;
                    a = new Archive(new File(sourceRar));
                    for (int i = 0; i < a.getFileHeaders().size(); i++) {
                        FileHeader e = a.getFileHeaders().get(i);
                        String compressFileName;
                        String destFileName;
                        Log.i("解压" + i, "文件：" + e.getFileNameString().trim());
                        if (e.getFileNameString().trim().toString().indexOf(".") == -1) {
                            compressFileName = e.getFileNameString().trim();
                            destFileName = "";
                            compressFileName = compressFileName.replaceAll("\\\\", "/");
                            destFileName = destDir + "/" + compressFileName;
                            File destDirName = new File(destFileName);
                            if (!destDirName.exists() || !destDirName.isDirectory()) {
                                destDirName.mkdirs();
                            }
                        }

                        if (!e.isDirectory()) {
                            compressFileName = e.getFileNameString().trim();
                            destFileName = "";
                            String destDirName1 = "";
                            compressFileName = compressFileName.replaceAll("\\\\", "/");
                            destFileName = destDir + "/" + compressFileName;
                            destDirName1 = destFileName.substring(0, destFileName.lastIndexOf("/"));
                            File dir = new File(destDirName1);
                            if (!dir.exists() || !dir.isDirectory()) {
                                dir.mkdirs();
                            }

                            fos = new FileOutputStream(new File(destFileName));
                            a.extractFile(e, fos);
                            fos.close();
                            fos = null;
                        }
                        Bundle bundle = new Bundle();
                        double percentNum = ((double) i / (double) a.getFileHeaders().size()) * 100.0;
                        bundle.putDouble("percent", percentNum);
                        Message msg = new Message();
                        msg.setData(bundle);
                        msg.what = percent;
                        handler.sendMessage(msg);
                    }
                    handler.sendEmptyMessage(end);
                    a.close();
                    a = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public static void unZip(Handler handler, String zipFile, String targetDir) {
        short BUFFER = 4096;

        try {
            BufferedOutputStream cwj = null;
            FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));

            while (true) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    String strEntry;
                    if (("" + entry).lastIndexOf("/") != ("" + entry).length() - 1) {
                        try {
                            byte[] data = new byte[BUFFER];
                            strEntry = entry.getName();
                            File entryFile = new File(targetDir + "/" + strEntry);
                            File entryDir = new File(entryFile.getParent());
                            if (!entryDir.exists()) {
                                entryDir.mkdirs();
                            }

                            FileOutputStream fos = new FileOutputStream(entryFile);
                            cwj = new BufferedOutputStream(fos, BUFFER);

                            int ex1;
                            while ((ex1 = zis.read(data, 0, BUFFER)) != -1) {
                                cwj.write(data, 0, ex1);
                            }

                            cwj.flush();
                            cwj.close();
                        } catch (Exception var15) {
                            var15.printStackTrace();
                        }
                    } else {
                        try {
                            strEntry = entry.getName();
                            File ex = new File(targetDir + "/" + strEntry);
                            if (!ex.exists()) {
                                ex.mkdirs();
                            }
                        } catch (Exception var14) {
                            var14.printStackTrace();
                        }
                    }
                }

                zis.close();
                break;
            }
        } catch (Exception var16) {
            var16.printStackTrace();
        }
    }



    /**
     * 解压缩文件
     *
     * @param unRarHander
     * @param filepath 压缩文件绝对路径
     * @param despath 解压目录，解压后的文件将会放入此目录中，请使用绝对路径
     * 只支持zip和rar格式的压缩包！
     * @throws Exception
     */
    public static void deCompress(Handler unRarHander, String filepath, String despath, String filetype)
            throws Exception {
        if (filetype.toLowerCase().equals("zip")) {
            unzip(unRarHander,filepath, despath);
        } else if (filetype.toLowerCase().equals("rar")) {
            unrar(unRarHander,filepath, despath,filetype);
        } else {
            throw new Exception("只支持zip和rar格式的压缩包！");
        }
    }

    /**
     * 解压zip格式压缩包
     * 对应的是ant.jar
     */
    private static void unzip(Handler unRarHander, final String sourceZip, final String destDir){
        try {
            Project p = new Project();
            final Expand e = new Expand();
            e.setProject(p);
            e.setSrc(new File(sourceZip));
            e.setOverwrite(false);
            e.setDest(new File(destDir));
				/*
				 * ant下的zip工具默认压缩编码为UTF-8编码， 而winRAR软件压缩是用的windows默认的GBK或者GB2312编码
				 * 所以解压缩时要制定编码格式
				 */
            e.setEncoding("gbk");
            e.execute();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 解压rar格式压缩包。
     * 对应的是java-unrar-0.3.jar，但是java-unrar-0.3.jar又会用到commons-logging-1.1.1.jar
     */
    public static void unrar(final Handler handler, final String sourceRar,
                             final String destDir,final String filetype){
        new Thread(){
            @Override
            public void run() {
                handler.sendEmptyMessage(start);
                Archive a = null;
                FileOutputStream fos = null;
                try {
                    a = new Archive(new File(sourceRar+"."+filetype));
                    FileHeader fh = a.nextFileHeader();
                    int i = 0;
                    while (fh != null) {
                        if (!fh.isDirectory()) {
                            // 1 根据不同的操作系统拿到相应的 destDirName 和 destFileName
//					        String compressFileName = fh.getFileNameString().trim();
                            String compressFileName = fh.getFileNameW().trim();
                            if(!existZH(compressFileName)){
                                compressFileName = fh.getFileNameString().trim();
                            }
                            String destFileName = "";
                            String destDirName = "";
                            // 非windows系统
                            if (File.separator.equals("/")) {
                                destFileName = destDir
                                        + compressFileName.replaceAll("\\\\", "/");
                                destDirName = destFileName.substring(0,
                                        destFileName.lastIndexOf("/"));
                                // windows系统
                            } else {
                                destFileName = destDir
                                        + compressFileName.replaceAll("/", "\\\\");
                                destDirName = destFileName.substring(0,
                                        destFileName.lastIndexOf("\\"));
                            }
                            // 2创建文件夹
                            File dir = new File(destDirName);
                            if (!dir.exists() || !dir.isDirectory()) {
                                dir.mkdirs();
                            }
                            // 3解压缩文件
                            fos = new FileOutputStream(new File(destFileName));
                            a.extractFile(fh, fos);
                            fos.close();
                            fos = null;
                            Bundle bundle = new Bundle();
                            double percentNum = ((double) i / (double) a.getFileHeaders().size()) * 100.0;
                            bundle.putDouble("percent", percentNum);
                            Message msg = new Message();
                            msg.setData(bundle);
                            msg.what = percent;
                            handler.sendMessage(msg);
                        }
                        fh = a.nextFileHeader();
                        i++;
                    }
                    a.close();
                    a = null;
                    handler.sendEmptyMessage(end);
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(error);
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                            fos = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (a != null) {
                        try {
                            a.close();
                            a = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }

    /**
     * 判断是否为中文
     * @param str
     * @return
     */
    public static boolean existZH(String str) {
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        while (m.find()) {
            return true;
        }
        return false;
    }


}
