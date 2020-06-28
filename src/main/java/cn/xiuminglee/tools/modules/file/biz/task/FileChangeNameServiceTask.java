package cn.xiuminglee.tools.modules.file.biz.task;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author Xiuming Lee
 * @description
 */
@Slf4j
public class FileChangeNameServiceTask extends Service<String> {

    public File file;
    public Operation flag;
    /** 要删除的字符串 */
    public String  removeStr;

    @Override
    protected Task<String> createTask() {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                String resultText = null;
                if (flag.equals(Operation.REMOVE)){
                    resultText = "删除字符串";
                } else {
                    resultText = "转换为拼音";
                }
                return resultText;
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


