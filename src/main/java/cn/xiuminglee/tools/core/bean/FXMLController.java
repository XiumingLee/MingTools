package cn.xiuminglee.tools.core.bean;

import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Xiuming Lee
 * @description 用于初始化FXMLController，并加入到Spring容器中，非单例模式
 *
 * @see FXMLView
 * @see FXMLViewInspector
 * @see FXMLControllerRegistrar
 */
public abstract class FXMLController implements ApplicationContextAware {

    /** 该FXMLController控制的那个Window窗口
     * 需要使用时，通过set注入即可。
     */
    protected Stage stage;

    protected ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 代替JavaFx中FXMLController的initialize()方法。
     * 最一些初始化操作
     */
     protected void initController(){};

    public void setWindow(Stage stage) {
        this.stage = stage;
    }
}
