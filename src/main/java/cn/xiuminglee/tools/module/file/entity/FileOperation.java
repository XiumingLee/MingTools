package cn.xiuminglee.tools.module.file.entity;

import lombok.Data;

/**
 * @author Xiuming Lee
 * @date 2022/2/25 11:37
 * @desc
 */
@Data
public class FileOperation {
    private String fileName;
    private Boolean IncludeFolder;
    private FileOperator fileOperator;
    private String condition;
}
