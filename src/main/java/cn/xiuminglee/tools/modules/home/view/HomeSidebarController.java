package cn.xiuminglee.tools.modules.home.view;

import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import cn.xiuminglee.tools.modules.home.element.ElementTool;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Xiuming Lee
 * @description
 */
@Slf4j
@FXMLView(fxmlPath = "fxml/home/home_sidebar.fxml")
public class HomeSidebarController extends FXMLController {
    public Pane homeSidebarPane;
    public ListView<Pane> toolList;


    @Override
    protected void initController() {
        ElementTool elementTool = new ElementTool();
        toolList.getItems().add(elementTool.getElement());
    }
}
