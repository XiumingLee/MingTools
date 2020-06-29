package cn.xiuminglee.tools.modules.file.biz;

import cn.xiuminglee.tools.modules.common.AlertComponent;
import cn.xiuminglee.tools.modules.common.State;
import cn.xiuminglee.tools.modules.file.biz.task.FileChangeNameServiceTask;
import cn.xiuminglee.tools.modules.file.view.FileController;
import cn.xiuminglee.tools.util.OSUtils;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * @author Xiuming Lee
 * @description 修改文件名称
 */
@Service
@Slf4j
public class FileChangeNameService {

    @Autowired
    private FileController fileController;

    private FileChangeNameServiceTask changeNameServiceTask;



    /** 执行重命名逻辑  */
    public void handleFileChangeNameButtonAction(ActionEvent event) {
        if (fileController.changeNameState.equals(State.RUNNING)){
            AlertComponent.warningAlert("其他操作正在进行，请稍后再试！");
            return;
        }
        if (changeNameServiceTask.file == null){
            AlertComponent.warningAlert("请选择要操作的文件夹！");
            return;
        }
        if (changeNameServiceTask.flag == FileChangeNameServiceTask.Operation.REMOVE && StringUtils.isEmpty(changeNameServiceTask.removeStr)){
            AlertComponent.warningAlert("请输入要删除的字符串！");
            return;
        }
        fileController.changLabelState(fileController.changeNameStateLabel,fileController.changeNameState, State.RUNNING);
        changeNameServiceTask.start();
    }

    public void init(){
        initFileChangeNameServiceTask();
        initDirectoryChooser();
        initRadioButton();
        initChangeNameRemoveStrTextField();
    }


    /** 为要去掉的字符串TextField添加监听事件 */
    private void initChangeNameRemoveStrTextField(){
        fileController.changeNameRemoveStr.textProperty().addListener((observable, oldValue, newValue) -> {
            changeNameServiceTask.removeStr = newValue;
        });
    }

    /** 初始化选择文件相关 */
    private void initDirectoryChooser(){
        DirectoryChooser fileChooser = new DirectoryChooser();
        fileController.fileChooserBtn.setOnAction(e -> {
            File selectedFile = fileChooser.showDialog(new Stage());
            if (selectedFile != null){
                this.changeNameServiceTask.file = selectedFile;
                fileController.selectedFileName.setText(selectedFile.getAbsolutePath());
            }
        });
    }

    /** 初始化单选按钮相关 */
    private void initRadioButton(){
        fileController.radioToggleGroup.selectToggle(fileController.radioButtonRemove);

        // 点击`去掉文件中的指定字符串`按钮时
        fileController.radioButtonRemove.setOnAction(event -> {
            changeNameServiceTask.flag = FileChangeNameServiceTask.Operation.REMOVE;
            // 显示输入要去掉字符串的输入框
            fileController.changeNameOtherBox.setVisible(true);
        });

        // 点击`将文件名转成汉语拼音`按钮时
        fileController.radioButtonPinyin.setOnAction(event -> {
            changeNameServiceTask.flag = FileChangeNameServiceTask.Operation.PIN_YIN;
            // 隐藏输入要去掉字符串的输入框
            fileController.changeNameOtherBox.setVisible(false);
            fileController.changeNameRemoveStr.setText(null);
        });
    }

    /** 初始化FileChangeNameServiceTask任务相关 */
    private void initFileChangeNameServiceTask() {
        this.changeNameServiceTask = new FileChangeNameServiceTask();

        // region FileChangeNameServiceTask监听事件 --------------------------------------------------------------
        /** 成功 */
        changeNameServiceTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fileController.changLabelState(fileController.changeNameStateLabel,fileController.changeNameState,State.SUCCEEDED);
                // 打开文件夹
                OSUtils.openFolder(changeNameServiceTask.file.getAbsolutePath());
            }
        });
        changeNameServiceTask.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(Worker.State.SUCCEEDED)){
                // 重置
                changeNameServiceTask.reset();
            }
            if (newValue.equals(Worker.State.FAILED)){
                fileController.changLabelState(fileController.changeNameStateLabel,fileController.changeNameState,State.FAILED);
                AlertComponent.errorAlert("文件重命名失败！");
                changeNameServiceTask.reset();
            }
        });

        changeNameServiceTask.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fileController.changLabelState(fileController.changeNameStateLabel,fileController.changeNameState,State.FAILED);
                log.error("文件重命名操作出现错误：{}",newValue.getMessage(),newValue);
                AlertComponent.errorAlert(newValue.getMessage());
            }
        });
        // endregion FileChangeNameServiceTask监听事件 --------------------------------------------------------------
    }
}
