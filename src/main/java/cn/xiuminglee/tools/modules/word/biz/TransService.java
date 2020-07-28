package cn.xiuminglee.tools.modules.word.biz;

import cn.xiuminglee.tools.modules.common.AlertComponent;
import cn.xiuminglee.tools.modules.common.State;
import cn.xiuminglee.tools.modules.word.biz.element.LanguageType;
import cn.xiuminglee.tools.modules.word.biz.task.TransServiceTask;
import cn.xiuminglee.tools.modules.word.view.WordController;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Xiuming Lee
 * @description 翻译相关,分担WordController的业务职能
 */
@Slf4j
public class TransService {
    private WordController wordController;
    /** 百度相关 */
    private BaiduService baiduService;

    // region 属性 --------------------------------------------------------------

    public TransServiceTask transServiceTask = null;
    // endregion 属性 --------------------------------------------------------------


    public TransService(WordController wordController, BaiduService baiduService) {
        this.wordController = wordController;
        this.baiduService = baiduService;
        initChoiceBoxes();
    }

    private void initChoiceBoxes() {
        ObservableList<LanguageType> languageTypes = LanguageType.buildBaiduTransLanguageType();
        wordController.transFromChoiceBox.setItems(languageTypes);
        wordController.transFromChoiceBox.setValue(languageTypes.get(0));
        wordController.transToChoiceBox.setItems(languageTypes);
        wordController.transToChoiceBox.setValue(languageTypes.get(0));
        // 选择框显示的内容
        wordController.transFromChoiceBox.setConverter(new LanguageType.LanguageTypeStringConverter());
        wordController.transToChoiceBox.setConverter(new LanguageType.LanguageTypeStringConverter());

        // 选择的内容改变时
        wordController.transFromChoiceBox.getSelectionModel().selectedItemProperty().addListener((ChangeListener<LanguageType>) (observable, oldValue, newValue) -> {
            /** 这里可以根据选项进行相关处理 */
            transServiceTask.fromLanguage = newValue;
        });
        wordController.transToChoiceBox.getSelectionModel().selectedItemProperty().addListener((ChangeListener<LanguageType>) (observable, oldValue, newValue) -> {
            /** 这里可以根据选项进行相关处理 */
            transServiceTask.toLanguage = newValue;
        });

        /** TransServiceTask 任务相关的一些监听 */
        transServiceTask = new TransServiceTask(baiduService);
        transServiceTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                wordController.changLabelState(wordController.translateStateLabel,wordController.translateState, State.SUCCEEDED);
                wordController.resultTextArea.setText(newValue);
            }
        });
        transServiceTask.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(Worker.State.SUCCEEDED)){
                // 重置
                transServiceTask.reset();
            }
            if (newValue.equals(Worker.State.FAILED)){
                wordController.changLabelState(wordController.translateStateLabel,wordController.translateState,State.FAILED);
                transServiceTask.reset();
            }
        });

        transServiceTask.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                wordController.changLabelState(wordController.translateStateLabel,wordController.translateState,State.FAILED);
                log.error("百度翻译出现错误：{}",newValue.getMessage(),newValue);
                AlertComponent.errorAlert(newValue.getMessage());
            }
        });
    }
}
