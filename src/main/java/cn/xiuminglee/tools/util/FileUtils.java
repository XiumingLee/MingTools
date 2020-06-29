package cn.xiuminglee.tools.util;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Xiuming Lee
 * @description
 */
@Slf4j
public class FileUtils {

    public static void fileListNameRemoveChar(Stack<File> fileStack, String character) {
        // 不能使用parallelStream并行流多线程，
        // 1、由于当在修改文件夹名称A时，如果其他线程在修改该文件夹下的其他文件B，那么B会修改失败
        // 2、当修改了父级的文件夹时，那么再修改子级的文件就会找不到相应的文件
        // 3、将原本的ArrayList换成了Stack,利用栈先进后出的原则，从最底层开始修改，就不会产生找不到路径的问题了。
        // 4、Stack继承自Vector，方法都是加锁的，多线程也就没有了意义。
        while (!fileStack.isEmpty()){
            removeChar(fileStack.pop(),character);
        }
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
    public static void removeChar(File file, String character) {
        // 父级文件绝对路径
        String parentAbsolutePath = file.getParentFile().getAbsolutePath();
        String fileName = file.getName();
        if (!fileName.contains(character)) {
            return;
        }
        fileName = fileName.replace(character, "");
        FileUtil.rename(file,fileName,false,true);
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

    public static Stack<File> buildFileStack(File file) {
        Stack<File> fileStack = new Stack<>();
        buildFileList(file,fileStack);
        return fileStack;
    }

    private static void buildFileStack(File file, Stack<File> stack) {
        if (file.isDirectory()) {
            stack.add(file);
            File[] files = file.listFiles();
            assert files != null;
            for (File file1 : files) {
                buildFileStack(file1, stack);
            }
        } else {
            stack.add(file);
        }
    }
}
