package cn.xiuminglee.tools.modules.common;

import javafx.scene.control.Alert;

/**
 * @author Xiuming Lee
 * @description
 */
public class AlertComponent {
    public static void errorAlert(String errMsg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.titleProperty().set("错误信息");
        alert.headerTextProperty().set("错误提示");
        alert.setContentText(errMsg);
        alert.showAndWait();
    }
}
