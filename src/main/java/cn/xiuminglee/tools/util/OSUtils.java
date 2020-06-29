package cn.xiuminglee.tools.util;

import cn.hutool.core.util.RuntimeUtil;

/**
 * @author Xiuming Lee
 * @description 操作系统工具类
 */
public class OSUtils {
    private static String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isLinux() {
        return OS.contains("linux");
    }

    public static boolean isMac() {
        return OS.contains("mac");
    }

    public static boolean isWindows() {
        return OS.contains("windows");
    }

    /** 打开文件夹 */
    public static void openFolder(String folderPath){
        String cmds = "";
        if (isMac()){
            cmds = "open " + folderPath;

        } else if (isWindows()){
            cmds = "explorer " + folderPath;
        }
        RuntimeUtil.exec(cmds);
    }

    //public static void main(String[] args) {
    //    RuntimeUtil.exec("open /Users/xiuming/Desktop/test/change");
    //}
}
