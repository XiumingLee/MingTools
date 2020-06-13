package cn.xiuminglee.tools.modules.home.view;

import cn.xiuminglee.tools.modules.AbstractUiObject;
import cn.xiuminglee.tools.util.SpringContextHolder;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Xiuming Lee
 * @description
 */
@Slf4j
public class HomeViewInit extends AbstractUiObject {

    public HomeViewInit() {
        HomeController homeController = SpringContextHolder.getBean(HomeController.class);
        //homeController.setWindow(this);
        setTitle("MingTools工具包");
        //getIcons().add(new Image(Constant.System.SYSTEM_ICON));
        setWidth(1100);
        setHeight(800);
        //initStyle(StageStyle.TRANSPARENT);
        // 设置不可最大化，不可拉伸
        setResizable(false);
        root = homeController.homePane;
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        setScene(scene);
    }

    @Override
    public void initView() {
        show();
        initEventDefine();
    }

    @Override
    public void initEventDefine() {
        move();
    }
}
