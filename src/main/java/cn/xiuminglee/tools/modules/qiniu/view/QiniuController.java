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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

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
    public Button delButton;
    public Label qiniuStateLabel;
    public State qiniuState = State.READY;

    @Override
    protected void initController() {
        imageView.setImage(new Image(Constant.Qiniu.QINIU_IMAGE_AREA));
    }

    /** 删除图片方法 */
    public void handleButtonAction(ActionEvent event) {
        log.info("点击了删除事件");
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
     * @param label OCR或翻译
     * @param value State值
     */
    public void changLabelState(Label label, State state, State value){
        state = value;
        label.setText(value.getStateMessage());
        label.setTextFill(Paint.valueOf(value.getColor()));
    }
}
