package cn.xiuminglee.tools.modules.word.view;

import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import cn.xiuminglee.tools.modules.word.biz.BaiduService;
import cn.xiuminglee.tools.modules.word.biz.OcrService;
import cn.xiuminglee.tools.modules.word.biz.State;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Xiuming Lee
 * @description  文件处理页面控制器
 * 百度OCR：https://cloud.baidu.com/doc/OCR/s/zk3h7xz52
 */
@Slf4j
@FXMLView(fxmlPath = "fxml/word/word.fxml")
public class WordController extends FXMLController {


    // region 其他服务模块 --------------------------------------------------------------
    /** 百度相关 */
    @Autowired
    private BaiduService baiduService;
    @Autowired
    private OcrService ocrService;
    // endregion 其他服务模块 --------------------------------------------------------------


    // region 页面中的Node元素 --------------------------------------------------------------
    /** 最外层的Node */
    public VBox wordBox;
    /** 用于存放OCR的结果和待翻译的文字 */
    public TextArea startTextArea;
    /** 存放翻译好的文字 */
    public TextArea resultTextArea;
    /** OCR状态 */
    public Label ocrState;
    /** 翻译状态 */
    public Label translateState;
    /** ocr的语言选择框 */
    public ChoiceBox ocrChoiceBox;
    // endregion  页面中的Node元素 --------------------------------------------------------------



    @Override
    protected void initController() {
        ocrService.initOcrService();
    }

    /**
     * 设置快捷键。
     */
    public void setShortcuts(){
        // 设置OCR的快捷键。
        ocrService.setOcrShortcuts();
    }


    /**
     * 修改OCR或翻译Label的状态
     * @param label OCR或翻译
     * @param value State值
     */
    public void changLabelState(Label label, State value){
        label.setText(value.getStateMessage());
        label.setTextFill(Paint.valueOf(value.getColor()));
    }

}
