package cn.xiuminglee.tools.common.util;

import cn.hutool.core.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Xiuming Lee
 * @date 2022/2/25 8:50
 * @desc
 */
public class FileUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);


    /**
     * 遍历文件夹并对文件进行相应的操作。
     * @param file 文件夹
     * @param fileOperator 对文件进行的操作,需考虑文件夹和文件的不同操作。
     */
    public static void traverseTheFolder(File file, Consumer<File> fileOperator) {
        if (!file.exists()) {
            LOGGER.error("  File does not exist !");
            throw new RuntimeException("  File does not exist !");
        } else if (file.isFile()) {
            fileOperator.accept(file);
        } else {
            fileOperator.accept(file);
            for (File listFile : file.listFiles()) {
                traverseTheFolder(listFile,fileOperator);
            }
        }
    }











    public static void main(String[] args) {
        File file = FileUtil.file("E:\\qiandu_netdisk_download\\206-陈天 · Rust 编程第一课");
        List<File> fileList = new ArrayList<>();
        traverseTheFolder(file,file1 -> {
            fileList.add(file1);
            System.out.println(file1.getName());
        });
        System.out.println(fileList.size());
    }

}
