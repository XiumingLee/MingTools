package cn.xiuminglee.tools.modules.qiniu.biz;

import cn.xiuminglee.tools.core.config.properties.MingToolsProperties;
import cn.xiuminglee.tools.modules.Constant;
import cn.xiuminglee.tools.modules.common.AlertComponent;
import cn.xiuminglee.tools.modules.common.ClipboardUtil;
import cn.xiuminglee.tools.modules.common.State;
import cn.xiuminglee.tools.modules.qiniu.biz.task.QiniuServiceTask;
import cn.xiuminglee.tools.modules.qiniu.view.QiniuController;
import cn.xiuminglee.tools.util.MarkdownUtils;
import javafx.concurrent.Worker;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author Xiuming Lee
 * @description 分担QiniuController的业务职能
 */
@Slf4j
@Service
public class QiniuService {
    @Autowired
    private QiniuController qiniuController;
    @Autowired
    private QiniuAPI qiniuAPI;
    @Autowired
    private MingToolsProperties mingToolsProperties;

    public QiniuServiceTask qiniuServiceTask;


    /**
     * 设置七牛云上传快捷键
     */
    public void setQiniuShortcuts(){
        qiniuServiceTask = new QiniuServiceTask(qiniuAPI);
        // Ctrl + Shift + v
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.V,KeyCombination.CONTROL_DOWN,KeyCombination.SHIFT_DOWN);
        qiniuController.getWindow().getScene().getAccelerators().put(keyCombination,()->{
            log.info("七牛快捷键触发！！");
            if (qiniuController.qiniuState.equals(State.RUNNING)){
                AlertComponent.warningAlert("其他操作正在进行，请稍后再试！");
                return;
            }
            qiniuController.changLabelState(qiniuController.qiniuStateLabel,qiniuController.qiniuState, State.RUNNING);
            qiniuServiceTask.flag = QiniuServiceTask.Operation.ADD;
            try {
                qiniuServiceTask.imageBytes = ClipboardUtil.getImageBytes();
            } catch (IOException e) {
                log.error("获取粘贴板图片失败！",e);
                AlertComponent.errorAlert("获取粘贴板图片失败：" + e.getMessage());
                return;
            }
            qiniuServiceTask.start();
        });

        // region QiniuServiceTask监听事件 --------------------------------------------------------------
        /** 成功 */
        qiniuServiceTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (qiniuServiceTask.flag.equals(QiniuServiceTask.Operation.ADD)){
                    // 上传成功
                    log.info("七牛云上传后的返回值：{}",newValue);
                    // 1、设置到显示区
                    qiniuController.imageView.setImage(new Image(newValue));
                    // 2、设置到文本域和粘贴板
                    String mdImageUrl = MarkdownUtils.buildMDImageUrl(newValue);
                    qiniuController.textField.setText(mdImageUrl);
                    ClipboardUtil.setStringToClipboard(mdImageUrl);
                } else {
                    // 删除成功
                    qiniuController.imageView.setImage(new Image(Constant.Qiniu.QINIU_IMAGE_AREA));
                }
                qiniuController.changLabelState(qiniuController.qiniuStateLabel,qiniuController.qiniuState, State.SUCCEEDED);
            }
        });
        qiniuServiceTask.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(Worker.State.SUCCEEDED)){
                // 重置
                qiniuServiceTask.reset();
            }
            if (newValue.equals(Worker.State.FAILED)){
                qiniuController.changLabelState(qiniuController.qiniuStateLabel,qiniuController.qiniuState,State.FAILED);
                AlertComponent.errorAlert("检查网络是否畅通或者已经正确配置了七牛云的AccessKey和SecretKey。");
                qiniuServiceTask.reset();
            }
        });

        qiniuServiceTask.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                qiniuController.changLabelState(qiniuController.qiniuStateLabel,qiniuController.qiniuState,State.FAILED);
                log.error("七牛云操作出现错误：{}",newValue.getMessage(),newValue);
                AlertComponent.errorAlert(newValue.getMessage());
            }
        });
        // endregion QiniuServiceTask监听事件 --------------------------------------------------------------
    }


    /**
     * 检查能否删除图片；
     * @return
     */
    public boolean checkDelImagePath(){
        boolean result = false;
        String fieldText = qiniuController.textField.getText();
        if (!StringUtils.isEmpty(fieldText) && fieldText.contains(mingToolsProperties.getQiniu().getFilePathPrefix())){
            result = true;
        }
        return result;
    }
}
