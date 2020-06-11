package cn.xiuminglee.tools.core.bean.annotation;

import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.FXMLControllerRegistrar;
import cn.xiuminglee.tools.core.bean.FXMLViewInspector;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author Xiuming Lee
 * @description 需要标在AbstractController的实现类上，用于初始化FXMLController，并加入到Spring容器中，非单例模式
 *  注意：在FXMLController的实现类标注上FXMLView注解后，对应的fxml文件就不需要用{@code fx:controller="xx.xx"}来标注控制类了，否则会报错
 * @see FXMLController
 * @see FXMLViewInspector
 * @see FXMLControllerRegistrar
 */
@Component
@Scope("prototype")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface FXMLView {

    String value() default "";
    /**
     * 标注FXMLController对应的fxml文件的路径
     * @return the relative file path of a views fxml file.
     */
    String fxmlPath();
}
