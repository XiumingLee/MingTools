package cn.xiuminglee.tools.modules.qiniu.view;

import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import cn.xiuminglee.tools.modules.Constant;
import cn.xiuminglee.tools.modules.common.AlertComponent;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Xiuming Lee
 * @description
 */
@Slf4j
@FXMLView(fxmlPath = "fxml/qiniu/qiniu.fxml")
public class QiniuController extends FXMLController {

    public VBox qiniuBox;
    public ImageView imageView;
    public TextField textField;
    public Button delButton;

    @Override
    protected void initController() {
        imageView.setImage(new Image(Constant.Qiniu.QINIU_IMAGE_AREA));
    }

    /** 删除图片方法 */
    public void handleButtonAction(ActionEvent event) {
        AlertComponent.errorAlert("错误提示！");
        log.info("点击了删除事件");
    }

    public void setShortcuts(){
        // Ctrl + Shift + v
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.V,KeyCombination.CONTROL_DOWN,KeyCombination.SHIFT_DOWN);
        stage.getScene().getAccelerators().put(keyCombination,()->{
            log.info("七牛快捷键触发！！");
        });
    }
}
