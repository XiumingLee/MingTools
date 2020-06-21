package cn.xiuminglee.tools.modules.home.view;

import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import cn.xiuminglee.tools.modules.common.PageEnum;
import cn.xiuminglee.tools.modules.file.view.FileController;
import cn.xiuminglee.tools.modules.qiniu.view.QiniuController;
import cn.xiuminglee.tools.modules.word.view.WordController;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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

    // region 其他组件Controller --------------------------------------------------------------

    @Autowired
    private HomeController homeController;
    @Autowired
    private QiniuController qiniuController;
    @Autowired
    private FileController fileController;
    @Autowired
    private WordController wordController;

    // endregion 其他组件Controller --------------------------------------------------------------

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
        Pane homeContent = getHomeContent();
        homeContent.getChildren().add(qiniuController.qiniuBox);
        /** 绑定快捷键事件 */
        qiniuController.setWindow(homeController.getWindow());
        qiniuController.setShortcuts();
        log.info("打开七牛页面");
    }

    /** 去文件处理页面 */
    public void toFilePage(MouseEvent mouseEvent){
        if (pageEnum == PageEnum.FILE){
            return;
        }
        this.pageEnum = PageEnum.FILE;
        Pane homeContent = getHomeContent();
        homeContent.getChildren().add(fileController.fileBox);
        log.info("打开文件处理页面");
    }

    /** 去ORC和翻译页面 */
    public void toWordPage(MouseEvent mouseEvent){
        if (pageEnum == PageEnum.WORD){
            return;
        }
        this.pageEnum = PageEnum.WORD;
        Pane homeContent = getHomeContent();
        homeContent.getChildren().add(wordController.wordBox);
        /** 绑定快捷键事件 */
        wordController.setWindow(homeController.getWindow());
        wordController.setShortcuts();
        log.info("打开ORC和翻译页面");
    }

    /**
     * 获取home页面的homeContent
     * 移除之前页面的快捷键，并将之前的页面从homeContent拿掉。
     * @return homeContent
     */
    private Pane getHomeContent() {
        removeShortcuts(homeController.getWindow());
        Pane homeContent = homeController.homeContent;
        homeContent.getChildren().clear();
        return homeContent;
    }

    /** 移除快捷键 */
    private void removeShortcuts(Stage stage){
        stage.getScene().getAccelerators().clear();
    }

}
