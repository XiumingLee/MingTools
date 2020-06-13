package cn.xiuminglee.tools.modules.test;

import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * @author Xiuming Lee
 * @description
 */
@FXMLView(fxmlPath = "fxml/test/test2.fxml")
public class Test1 extends FXMLController {
    public Label label;
    public AnchorPane test1Pane;

    public void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!我是test1");
    }

}
