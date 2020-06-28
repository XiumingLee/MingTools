package cn.xiuminglee.tools.modules.file.biz.task;

import cn.xiuminglee.tools.util.FileUtils;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * @author Xiuming Lee
 * @description
 */
@Slf4j
public class FileChangeNameServiceTask extends Service<Boolean> {

    public File file;
    public Operation flag = Operation.REMOVE;
    /** 要删除的字符串 */
    public String  removeStr;

    @Override
    protected Task<Boolean> createTask() {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                List<File> files = FileUtils.buildFileList(file);
                if (flag.equals(Operation.REMOVE)){
                    FileUtils.fileListNameRemoveChar(files,removeStr);
                } else {
                    FileUtils.fileListNameChangToPinyin(files);
                }
                return true;
            }
        };
    }

    public enum Operation{
        /** 删除指定字符串 */
        REMOVE,
        /** 改为拼音 */
        PIN_YIN
    }
}


