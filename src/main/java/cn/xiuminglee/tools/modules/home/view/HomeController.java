package cn.xiuminglee.tools.modules.home.view;

import cn.xiuminglee.tools.core.bean.AbstractController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import cn.xiuminglee.tools.modules.test.Test1;
import cn.xiuminglee.tools.modules.test.Test2;
import cn.xiuminglee.tools.util.SpringContextHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;


/**
 * @author Xiuming Lee
 * @description
 */
@Slf4j
@FXMLView(fxmlPath = "fxml/home/home.fxml")
public class HomeController extends AbstractController<HomeController> {

    //@Autowired
    //private Test1 test1;


    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Label label;

    public Pane testPane;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Test1 test1 = SpringContextHolder.getBean(Test1.class);
        testPane.getChildren().clear();
        testPane.getChildren().add(test1.test1Pane);
    }

    public void initialize() {
        // TODO 初始化相关
    }

    public void handleButtonAction2(ActionEvent event) {
        Test2 test2 = SpringContextHolder.getBean(Test2.class);
        testPane.getChildren().clear();
        testPane.getChildren().add(test2.test2Pane);
    }

}
