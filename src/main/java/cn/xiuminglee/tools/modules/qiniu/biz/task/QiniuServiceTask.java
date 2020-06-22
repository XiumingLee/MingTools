package cn.xiuminglee.tools.modules.qiniu.biz.task;

import cn.xiuminglee.tools.modules.qiniu.biz.QiniuAPI;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Xiuming Lee
 * @description
 */
@Slf4j
public class QiniuServiceTask extends Service<String> {

    public Operation flag = Operation.ADD;
    private QiniuAPI qiniuAPI;

    public byte[] imageBytes;

    public String deleteKey;

    public QiniuServiceTask(QiniuAPI qiniuAPI) {
        this.qiniuAPI = qiniuAPI;
    }

    @Override
    protected Task<String> createTask() {
        Task<String> resultTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                String resultText = null;
                if (flag.equals(Operation.ADD)){
                    resultText = qiniuAPI.upload(imageBytes);
                } else {
                    qiniuAPI.deleteByKey(deleteKey);
                    resultText = "删除成功！";
                }
                return resultText;
            }
        };
        return resultTask;
    }


    public enum Operation{
        /** 添加操作 */
        ADD,
        /** 删除操作 */
        DEL
    }
}
