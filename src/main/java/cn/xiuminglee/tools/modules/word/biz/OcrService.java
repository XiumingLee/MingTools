package cn.xiuminglee.tools.modules.word.biz;

import cn.xiuminglee.tools.modules.word.biz.element.LanguageType;
import cn.xiuminglee.tools.modules.word.view.WordController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Xiuming Lee
 * @description OCR相关的方法，承接WordController的业务操作。
 */
@Service
public class OcrService {
    @Autowired
    private WordController wordController;

    public void initOcrService(){
        wordController.ocrChoiceBox.setItems(LanguageType.buildBaiduLanguageType());
    }

    /** 设置OCR的快捷键  */
    public void setOcrShortcuts(){

    }
}
