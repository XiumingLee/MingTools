package cn.xiuminglee.tools.core.bean.annotation;

import cn.xiuminglee.tools.core.bean.AbstractController;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Xiuming Lee
 * @description 需要标在AbstractController的实现类上，用于初始化FXMLController，并加入到Spring容器中，非单例模式
 * @see AbstractController
 * @see cn.xiuminglee.tools.core.bean.FXMLViewInspector
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
public @interface FXMLView {

    String value() default "";
    /**
     * 标注FXMLController对应的fxml文件的路径
     * @return the relative file path of a views fxml file.
     */
    String fxmlPath();
}
