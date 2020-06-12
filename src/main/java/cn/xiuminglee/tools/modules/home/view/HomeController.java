package cn.xiuminglee.tools.modules.home.view;

import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;


/**
 * @author Xiuming Lee
 * @description
 */
@Slf4j
@FXMLView(fxmlPath = "fxml/home/home.fxml")
public class HomeController extends FXMLController {

    public Pane homePane;
    public Pane homeSidebar;
    public Pane homeContent;


    @Override
    public void initController() {
        HomeSidebarController homeSidebarController = applicationContext.getBean(HomeSidebarController.class);
        homeSidebar.getChildren().add(homeSidebarController.homeSidebarPane);
        /** 属性绑定，用于自适应 */
        homeSidebarController.homeSidebarPane.prefWidthProperty().bind(homeSidebar.widthProperty());
        homeSidebarController.homeSidebarPane.prefHeightProperty().bind(homeSidebar.heightProperty());
    }
}
