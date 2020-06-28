package cn.xiuminglee.tools.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Xiuming Lee
 * @description
 */
public class FileUtils {

    public static void fileListNameRemoveChar(List<File> fileList, String character) {
        fileList.parallelStream().forEach(fil -> {
            removeChar(fil,character);
        });
    }

    public static void fileListNameChangToPinyin(List<File> fileList) {
        fileList.parallelStream().forEach(fil -> {
            // 将汉字名称改为拼音名称
            if (fil.isFile()){
                String filName = fil.getName();
                String oldName = filName.substring(0, filName.lastIndexOf("."));
                String newName = HanyuPinyinUtils.toHanyuPinyin(oldName);
                changToChar(fil,newName);
            }
        });
    }

    /**
     * 文件重命名，删除文件命中的指定字符串
     *
     * @param file
     * @param character
     * @return
     */
    public static boolean removeChar(File file, String character) {
        // 父级文件绝对路径
        String parentAbsolutePath = file.getParentFile().getAbsolutePath();
        String fileName = file.getName();
        if (!fileName.contains(character)) {
            return false;
        }
        fileName = fileName.replace(character, "");
        return file.renameTo(new File(parentAbsolutePath + File.separator + fileName));
    }

    /**
     * 将文件名修改为str
     *
     * @param file
     * @param str  新的文件名
     * @return
     */
    public static void changToChar(File file, String str) {
        if (file.isFile()){
            String parentAbsolutePath = file.getParentFile().getAbsolutePath();
            String fileName = file.getName();
            String suffix = fileName.substring(fileName.lastIndexOf(".")); // 带点. eg:  .txt
            file.renameTo(new File(parentAbsolutePath + File.separator + str + suffix));
        }
    }

    public static List<File> buildFileList(File file) {
        ArrayList<File> fileList = new ArrayList<>();
        buildFileList(file,fileList);
        return fileList;
    }

    private static void buildFileList(File file, List<File> list) {
        if (file.isDirectory()) {
            list.add(file);
            File[] files = file.listFiles();
            assert files != null;
            for (File file1 : files) {
                buildFileList(file1, list);
            }
        } else {
            list.add(file);
        }
    }
}
