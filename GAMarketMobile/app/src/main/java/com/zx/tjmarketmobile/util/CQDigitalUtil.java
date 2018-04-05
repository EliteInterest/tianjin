package com.zx.tjmarketmobile.util;

public class CQDigitalUtil {
    private static String DataPath = "";

    public static String getDataPath() {

        return ConstStrings.INI_PATH + "/"+ConstStrings.APPNAME+"/";
    }

//	 /**
//     * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
//     *
//     * @return
//     */
//    private static ArrayList<String> getDevMountList() {
//        String[] toSearch = FileUtils.readFile("/etc/vold.fstab").split(" ");
//        ArrayList<String> out = new ArrayList<String>();
//        for (int i = 0; i < toSearch.length; i++) {
//            if (toSearch[i].contains("dev_mount")) {
//                if (new File(toSearch[i + 2]).exists()) {
//                    out.add(toSearch[i + 2]);
//                }
//            }
//        }
//        return out;
//    }

//    private static String getDataPathByRuntime() {
//        String path = "";
//        try {
//            Runtime runtime = Runtime.getRuntime();
//            Process proc = runtime.exec("mount");
//            InputStream is = proc.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);
//            String line;
//            String mount = new String();
//            BufferedReader br = new BufferedReader(isr);
//            while ((line = br.readLine()) != null) {
//                Log.v("DataPath", line);
//                if (line.contains("secure"))
//                    continue;
//                if (line.contains("asec"))
//                    continue;
//                if (line.contains("fat")) {
//                    String columns[] = line.split(" ");
//                    if (columns != null && columns.length > 1) {
//                        mount = mount.concat(columns[1]);
//                    }
//                } else if (line.contains("fuse")) {
//                    String columns[] = line.split(" ");
//                    if (columns != null && columns.length > 1) {
//                        mount = columns[1];
//                    }
//                }
//                if (!mount.isEmpty() && line.startsWith(mount)) {
//                    String columns[] = line.split(" ");
//                    if (columns != null && columns.length > 1) {
//                        mount = columns[1];
//                    }
//                }
//            }
//            path = mount;
//            //path = "storage/extSdCard";
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated
//        } catch (IOException e) {
//            // TODO Auto-generated
//        }
//
//        return path;
//    }
}
