package cn.xiuminglee.tools.modules.qiniu.view;

import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
        System.out.println("点击了删除事件");
    }
}
