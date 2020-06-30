package cn.xiuminglee.tools.modules.word.biz;

import cn.xiuminglee.tools.modules.common.AlertComponent;
import cn.xiuminglee.tools.modules.common.ClipboardUtil;
import cn.xiuminglee.tools.modules.common.State;
import cn.xiuminglee.tools.modules.word.biz.element.LanguageType;
import cn.xiuminglee.tools.modules.word.biz.task.OcrServiceTask;
import cn.xiuminglee.tools.modules.word.view.WordController;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author Xiuming Lee
 * @description OCR相关的方法，承接WordController的业务操作。
 */
@Service
@Slf4j
public class OcrService {

    // region 其他服务模块 --------------------------------------------------------------
    /** 百度相关 */
    @Autowired
    private BaiduService baiduService;
    // endregion 其他服务模块 --------------------------------------------------------------

    @Autowired
    private WordController wordController;

    // region 属性 --------------------------------------------------------------
    private OcrServiceTask ocrFXService = null;
    // endregion 属性 --------------------------------------------------------------

    public void initOcrService(WordController wordController){
        this.wordController = wordController;
        initOcrChoiceBox();
    }

    /** 设置OCR的快捷键
     *
     * 利用OcrFXService任务，异步请求，完成。
     */
    public void setOcrShortcuts(){
        ocrFXService = new OcrServiceTask(baiduService);
        ocrFXService.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                wordController.changLabelState(wordController.ocrStateLabel,wordController.ocrState, State.SUCCEEDED);
                wordController.startTextArea.setText(newValue);
            }
        });
        ocrFXService.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(Worker.State.SUCCEEDED)){
                // 重置
                ocrFXService.reset();
            }
            if (newValue.equals(Worker.State.FAILED)){
                wordController.changLabelState(wordController.ocrStateLabel,wordController.ocrState,State.FAILED);
                AlertComponent.errorAlert("检查网络是否畅通或者已经正确配置了百度OCR的AccessKey和SecretKey。");
                ocrFXService.reset();
            }
        });

        ocrFXService.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                wordController.changLabelState(wordController.ocrStateLabel,wordController.ocrState,State.FAILED);
                log.error("百度OCR出现错误：{}",newValue.getMessage(),newValue);
                AlertComponent.errorAlert(newValue.getMessage());
            }
        });
        // Ctrl + Shift + T
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.T,KeyCombination.CONTROL_DOWN,KeyCombination.SHIFT_DOWN);
        wordController.getWindow().getScene().getAccelerators().put(keyCombination,()->{
            if (wordController.translateState.equals(State.RUNNING) || wordController.ocrState.equals(State.RUNNING)) {
                AlertComponent.warningAlert("其他操作正在进行，请稍后再试！");
                return;
            }
            wordController.changLabelState(wordController.ocrStateLabel,wordController.ocrState,State.RUNNING);
            byte[] imageBytes = new byte[0];
            try {
                imageBytes = ClipboardUtil.getImageBytes();
            } catch (IOException e) {
                log.error("图片转换错误！",e);
                wordController.changLabelState(wordController.ocrStateLabel,wordController.ocrState,State.FAILED);
                AlertComponent.errorAlert("粘贴板中的图片转换错误：" + e.getMessage());
            }
            ocrFXService.setImageBytes(imageBytes);
            ocrFXService.start();
        });
    }

    private void initOcrChoiceBox(){
        ObservableList<LanguageType> languageTypes = LanguageType.buildBaiduLanguageType();
        wordController.ocrChoiceBox.setItems(languageTypes);
        wordController.ocrChoiceBox.setValue(languageTypes.get(0));

        // 选择框显示的内容
        wordController.ocrChoiceBox.setConverter(new LanguageType.LanguageTypeStringConverter());
        // 选择的内容改变时
        wordController.ocrChoiceBox.getSelectionModel().selectedItemProperty().addListener((ChangeListener<LanguageType>) (observable, oldValue, newValue) -> {
            /** 这里可以根据选项进行相关处理 */
            ocrFXService.setLanguageType(newValue);
        });
    }
}
