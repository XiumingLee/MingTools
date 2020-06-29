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

    public static void warningAlert(String errMsg){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.titleProperty().set("警告");
        alert.headerTextProperty().set("警告");
        alert.setContentText(errMsg);
        alert.showAndWait();
    }

    /** 确认弹窗 */
    public static Alert confirmAlert(String headerText,String contentText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.titleProperty().set("确认消息");
        alert.headerTextProperty().set(headerText);
        alert.setContentText(contentText);
        return alert;
    }
}
