package cn.xiuminglee.tools.modules.home.view;

import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;


/**
 * @author Xiuming Lee
 * @description
 */
@Slf4j
@FXMLView(fxmlPath = "fxml/home/home.fxml")
public class HomeController extends FXMLController {

    @Resource
    private Application application;

    public Pane homePane;
    public Pane homeSidebar;
    public Pane homeContent;


    @Override
    public void initController() {
        //HomeSidebarController homeSidebarController = applicationContext.getBean(HomeSidebarController.class);
        //homeSidebar.getChildren().add(homeSidebarController.homeSidebarPane);
        ///** 属性绑定，用于自适应 */
        //homeSidebarController.homeSidebarPane.prefWidthProperty().bind(homeSidebar.widthProperty());
        //homeSidebarController.homeSidebarPane.prefHeightProperty().bind(homeSidebar.heightProperty());
    }

    /** 最小化窗口。 */
    public void minWindow(MouseEvent mouseEvent) throws Exception {
        System.out.println("最小化");
        Stage stageEvent = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stageEvent.setIconified(true);
    }

    /** 关闭窗口，退出程序。 */
    public void closeWindow(MouseEvent mouseEvent) throws Exception {
        application.stop();
    }
}
