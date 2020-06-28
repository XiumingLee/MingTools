package cn.xiuminglee.tools.modules.file.biz;

import cn.xiuminglee.tools.modules.file.biz.task.FileChangeNameServiceTask;
import cn.xiuminglee.tools.modules.file.view.FileController;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @author Xiuming Lee
 * @description 修改文件名称
 */
@Service
public class FileChangeNameService {

    @Autowired
    private FileController fileController;
    private FileChangeNameServiceTask changeNameServiceTask;



    /** 执行重命名逻辑  */
    public void handleFileChangeNameButtonAction(ActionEvent event) {
    }

    @PostConstruct
    public void init(){
        initFileChangeNameServiceTask();
        initDirectoryChooser();
        initRadioButton();
    }

    /** 初始化FileChangeNameServiceTask任务相关 */
    private void initFileChangeNameServiceTask() {
        this.changeNameServiceTask = new FileChangeNameServiceTask();
    }

    /** 初始化选择文件相关 */
    private void initDirectoryChooser(){
        DirectoryChooser fileChooser = new DirectoryChooser();
        fileController.fileChooserBtn.setOnAction(e -> {
            File selectedFile = fileChooser.showDialog(new Stage());
            if (selectedFile != null){
                this.changeNameServiceTask.file = selectedFile;
                fileController.selectedFileName.setText(selectedFile.getName());
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
}
