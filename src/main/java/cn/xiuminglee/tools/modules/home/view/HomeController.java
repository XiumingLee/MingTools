package cn.xiuminglee.tools.modules.home.view;

import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Xiuming Lee
 * @description
 */
@FXMLView(fxmlPath = "fxml/home/home.fxml")
public class HomeController extends FXMLController {

    @Autowired
    private HomeSidebarController homeSidebarController;

    public Pane homePane;
    public Pane homeSidebar;
    public Pane homeContent;

    @Override
    protected void initController() {
        VBox homeSidebarPane = homeSidebarController.homeSidebarPane;
        homeSidebar.getChildren().add(homeSidebarPane);
    }
}
