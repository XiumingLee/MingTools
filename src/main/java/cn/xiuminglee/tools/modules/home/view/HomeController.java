package cn.xiuminglee.tools.modules.home.view;

import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import javafx.scene.layout.Pane;
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
    public Pane nav;

    @Override
    protected void initController() {
        Pane homeSidebarPane = homeSidebarController.homeSidebarPane;
        homeSidebar.getChildren().add(homeSidebarPane);
    }
}
