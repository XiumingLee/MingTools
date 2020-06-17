package cn.xiuminglee.tools.modules.home.view;

import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import cn.xiuminglee.tools.modules.common.PageEnum;
import cn.xiuminglee.tools.modules.file.view.FileController;
import cn.xiuminglee.tools.modules.qiniu.view.QiniuController;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Xiuming Lee
 * @description
 */
@Slf4j
@FXMLView(fxmlPath = "fxml/home/home_sidebar.fxml")
public class HomeSidebarController extends FXMLController {

    private PageEnum pageEnum = PageEnum.HOME;

    @Autowired
    private HomeController homeController;
    @Autowired
    private QiniuController qiniuController;
    @Autowired
    private FileController fileController;

    public Pane homeSidebarPane;
    public ListView<Pane> toolList;


    @Override
    protected void initController() {

    }

    /** 去七牛云页面 */
    public void toQiniuPage(MouseEvent mouseEvent) {
        if (pageEnum == PageEnum.QINIU){
            return;
        }
        this.pageEnum = PageEnum.QINIU;
        /** 切换页面 */
        Pane homeContent = homeController.homeContent;
        homeContent.getChildren().clear();
        homeContent.getChildren().add(qiniuController.qiniuBox);
        /** 绑定快捷键事件 */
        System.out.println("打开七牛页面");
    }

    /** 去文件处理页面 */
    public void toFilePage(MouseEvent mouseEvent){
        if (pageEnum == PageEnum.FILE){
            return;
        }
        this.pageEnum = PageEnum.FILE;
        Pane homeContent = homeController.homeContent;
        homeContent.getChildren().clear();
        homeContent.getChildren().add(fileController.fileBox);
        System.out.println("打开文件处理页面");
    }
}
