package cn.xiuminglee.tools.modules.qiniu.view;

import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import cn.xiuminglee.tools.core.exception.MingToolsException;
import cn.xiuminglee.tools.modules.Constant;
import cn.xiuminglee.tools.modules.common.AlertComponent;
import cn.xiuminglee.tools.modules.common.State;
import cn.xiuminglee.tools.modules.qiniu.biz.QiniuService;
import cn.xiuminglee.tools.modules.qiniu.biz.task.QiniuServiceTask;
import cn.xiuminglee.tools.util.MarkdownUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @author Xiuming Lee
 * @description
 */
@Slf4j
@FXMLView(fxmlPath = "fxml/qiniu/qiniu.fxml")
public class QiniuController extends FXMLController {

    @Autowired
    private QiniuService qiniuService;


    public VBox qiniuBox;
    public ImageView imageView;
    public TextField textField;
    public Label qiniuStateLabel;
    public State qiniuState = State.READY;

    @Override
    protected void initController() {
        imageView.setImage(new Image(Constant.Qiniu.QINIU_IMAGE_AREA));
    }

    /** 删除图片方法 */
    public void handleButtonAction(ActionEvent event) {
        log.info("点击了七牛云删除事件");
        if (!qiniuService.checkDelImagePath()){
            AlertComponent.warningAlert("请检查删除图片地址的正确性！");
            return;
        }
        Alert confirmAlert = AlertComponent.confirmAlert("您确定删除此图片吗", "图片地址为：" + MarkdownUtils.getImageUrl(textField.getText()));
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.get() != ButtonType.OK){
            // 不删除
            return;
        }
        if (qiniuState.equals(State.RUNNING)){
            AlertComponent.warningAlert("其他操作正在进行，请稍后再试！");
            return;
        }
        changLabelState(qiniuStateLabel,qiniuState, State.RUNNING);
        qiniuService.qiniuServiceTask.flag = QiniuServiceTask.Operation.DEL;
        String qiniuImageKey = null;
        try {
            qiniuImageKey = MarkdownUtils.getQiniuImageKey(textField.getText());
        } catch (MingToolsException e) {
            log.error("删除七牛云图片时出现错误：{}",e.getMessage(),e);
            AlertComponent.errorAlert("删除七牛云图片时出现错误：" + e.getMessage());
            changLabelState(qiniuStateLabel,qiniuState,State.FAILED);
            return;
        }
        qiniuService.qiniuServiceTask.deleteKey = qiniuImageKey;
        qiniuService.qiniuServiceTask.start();
    }

    public void setShortcuts(){
        qiniuService.setQiniuShortcuts();
    }

    /**
     * 修改状态
     * @param label
     * @param value State值
     */
    public void changLabelState(Label label, State state, State value){
        state = value;
        label.setText(value.getStateMessage());
        label.setTextFill(Paint.valueOf(value.getColor()));
    }
}
