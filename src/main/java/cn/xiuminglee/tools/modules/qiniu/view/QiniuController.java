package cn.xiuminglee.tools.modules.qiniu.view;

import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import cn.xiuminglee.tools.modules.common.AlertComponent;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;

/**
 * @author Xiuming Lee
 * @description
 */
@FXMLView(fxmlPath = "fxml/qiniu/qiniu.fxml")
public class QiniuController extends FXMLController {

    public VBox qiniuBox;
    public ImageView imageView;
    public TextField textField;
    public Button delButton;


    /** 删除图片方法 */
    public void handleButtonAction(ActionEvent event) {
        AlertComponent.errorAlert("错误提示！");
        System.out.println("点击了删除事件");
    }

    public void setShortcuts(){

        // Ctrl + Shift + v
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.V,KeyCombination.CONTROL_DOWN,KeyCombination.SHIFT_DOWN);
        stage.getScene().getAccelerators().put(keyCombination,()->{
            System.out.println("七牛快捷键触发！！");
        });
    }
}
