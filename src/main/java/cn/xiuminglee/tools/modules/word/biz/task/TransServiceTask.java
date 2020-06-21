package cn.xiuminglee.tools.modules.word.biz.task;

import cn.xiuminglee.tools.core.exception.MingToolsException;
import cn.xiuminglee.tools.modules.word.biz.BaiduService;
import cn.xiuminglee.tools.modules.word.biz.element.LanguageType;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.springframework.util.StringUtils;

/**
 * @author Xiuming Lee
 * @description
 */
public class TransServiceTask extends Service<String> {
    private BaiduService baiduService;

    public String queryStr;

    public LanguageType fromLanguage = new LanguageType("auto","自动检测");
    public LanguageType toLanguage = new LanguageType("auto","自动检测");

    public TransServiceTask(BaiduService baiduService) {
        this.baiduService = baiduService;
    }

    @Override
    protected Task<String> createTask() {
        Task<String> resultTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                if (StringUtils.isEmpty(queryStr)){
                    throw new MingToolsException("请输入待翻译的内容文字！");
                }
                String resultText = null;
                resultText = baiduService.getTransResult(queryStr,fromLanguage.getKey(),toLanguage.getKey());
                return resultText;
            }
        };
        return resultTask;
    }
}
