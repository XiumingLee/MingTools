package cn.xiuminglee.tools.modules.word.biz.task;

import cn.xiuminglee.tools.modules.word.biz.BaiduService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Xiuming Lee
 * @description
 * 注意：Service中不能使用粘贴板？调用Clipboard的方法后，一直没有返回，任务结束不了。
 */
@Slf4j
public class OcrServiceTask extends Service<String> {
    private BaiduService baiduService;
    private byte[] imageBytes;
    public OcrServiceTask(BaiduService baiduService) {
        this.baiduService = baiduService;
    }

    @Override
    protected Task<String> createTask() {
        Task<String> png = new Task<>() {
            @Override
            protected String call() throws Exception {
                String resultText = null;
                resultText = baiduService.generalBasic(imageBytes);
                return resultText;
            }
        };
        return png;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}
