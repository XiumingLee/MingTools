package cn.xiuminglee.tools.core.bean;

import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import javafx.fxml.FXMLLoader;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author Xiuming Lee
 * @description 用于初始化FXMLController，并加入到Spring容器中，非单例模式
 *
 * @error 存在的问题：这样将其添加到容器中，可以被其他Spring Bean依赖注入。却不可依赖注入其他的Spring Bean。
 *        因为该类Bean是通过FXMLLoader实例化完成，直接放入到容器中的。
 * @see FXMLView
 * @see FXMLViewInspector
 */
public abstract class AbstractController<T extends AbstractController> implements FactoryBean<T> {
    @Override
    public boolean isSingleton() {
        return false;
    }

    /**
     * 使用FXMLLoader初始化FXMLController，使用FXMLView注解的fxml路径加载fxml
     * @return
     * @throws Exception
     */
    @Override
    public T getObject() throws Exception {
        Class<? extends AbstractController> aClass = getClass();
        FXMLView annotation = aClass.getAnnotation(FXMLView.class);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(annotation.fxmlPath()));
        loader.load();
        return loader.getController();
    }

    @Override
    public Class<?> getObjectType() {
        return getClass();
    }

}
