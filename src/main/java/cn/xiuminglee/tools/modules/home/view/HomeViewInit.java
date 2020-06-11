package cn.xiuminglee.tools.modules.home.view;

import cn.xiuminglee.tools.modules.AbstractUiObject;
import cn.xiuminglee.tools.util.SpringContextHolder;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Xiuming Lee
 * @description
 */
@Slf4j
public class HomeViewInit extends AbstractUiObject {
    private static final String RESOURCE_NAME = "fxml/home/home.fxml";


    public HomeViewInit() {
        //try {
        //    root = FXMLLoader.load(getClass().getClassLoader().getResource(RESOURCE_NAME));
        //} catch (IOException e) {
        //    log.error("加载登录页fxml文件失败！",e);
        //}
        HomeController homeController = SpringContextHolder.getBean(HomeController.class);
        Scene scene = new Scene(homeController.anchorPane);
        setScene(scene);
        setTitle("MingTools工具包");
        getIcons().add(new Image("assets/img/app/tools.png"));
    }

    @Override
    public void initView() {
        show();
    }

    @Override
    public void initEventDefine() {

    }
}
