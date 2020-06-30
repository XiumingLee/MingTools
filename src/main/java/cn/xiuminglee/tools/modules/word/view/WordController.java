package cn.xiuminglee.tools.modules.word.view;

import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import cn.xiuminglee.tools.modules.common.AlertComponent;
import cn.xiuminglee.tools.modules.common.State;
import cn.xiuminglee.tools.modules.word.biz.OcrService;
import cn.xiuminglee.tools.modules.word.biz.TransService;
import cn.xiuminglee.tools.modules.word.biz.element.LanguageType;
import javafx.event.ActionEvent;
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
 * 百度翻译：https://fanyi-api.baidu.com/doc/21
 */
@Slf4j
@FXMLView(fxmlPath = "fxml/word/word.fxml")
public class WordController extends FXMLController {



    // region 其他服务模块 --------------------------------------------------------------
    /** 百度相关 */
    @Autowired
    private OcrService ocrService;
    @Autowired
    private TransService transService;
    // endregion 其他服务模块 --------------------------------------------------------------


    // region 页面中的Node元素 --------------------------------------------------------------
    /** 最外层的Node */
    public VBox wordBox;
    /** 用于存放OCR的结果和待翻译的文字 */
    public TextArea startTextArea;
    /** 存放翻译好的文字 */
    public TextArea resultTextArea;
    /** OCR状态 */
    public Label ocrStateLabel;
    public State ocrState = State.READY;
    /** ocr的语言选择框 */
    public ChoiceBox<LanguageType> ocrChoiceBox;
    /** 翻译状态 */
    public Label translateStateLabel;
    public State translateState  = State.READY;
    /** 翻译的源语言选择框 */
    public ChoiceBox<LanguageType> transFromChoiceBox;
    /** 翻译的目标语言选择框 */
    public ChoiceBox<LanguageType> transToChoiceBox;
    // endregion  页面中的Node元素 --------------------------------------------------------------



    @Override
    protected void initController() {
        ocrService.initOcrService(this);
        transService.initTransService(this);
    }

    /**
     * 设置快捷键。
     */
    public void setShortcuts(){
        // 设置OCR的快捷键。
        ocrService.setOcrShortcuts();
    }

    /**
     * 点击翻译按钮时触发
     * @param event
     */
    public void handleTransAction(ActionEvent event) {
        if (translateState.equals(State.RUNNING) || ocrState.equals(State.RUNNING)) {
            AlertComponent.warningAlert("其他操作正在进行，请稍后再试！");
            return;
        }
        transService.transServiceTask.queryStr = startTextArea.getText();
        transService.transServiceTask.start();
        changLabelState(translateStateLabel,translateState,State.RUNNING);
    }


    /**
     * 修改OCR或翻译Label的状态
     * @param label OCR或翻译
     * @param value State值
     */
    public void changLabelState(Label label,State state, State value){
        state = value;
        label.setText(value.getStateMessage());
        label.setTextFill(Paint.valueOf(value.getColor()));
    }

}
