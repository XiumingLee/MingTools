package cn.xiuminglee.tools.core.bean.annotation;

import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;


/**
 * @author Xiuming Lee
 * @description 需要标在AbstractController的实现类上，用于初始化FXMLController，并加入到Spring容器中，非单例模式
 *  注意：在FXMLController的实现类标注上FXMLView注解后，对应的fxml文件就不需要用{@code fx:controller="xx.xx"}来标注控制类了，否则会报错
 *       可以根据自己对Bean的要求，在对应的class上通过{@link Scope}设置其作用域
 *
 * @see cn.xiuminglee.tools.core.bean.FXMLController
 * @see cn.xiuminglee.tools.core.bean.FXMLViewInspector
 * @see cn.xiuminglee.tools.core.bean.FXMLControllerRegistrar
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface FXMLView {

    @AliasFor(annotation = Component.class)
    String value() default "";
    /**
     * 标注FXMLController对应的fxml文件的路径
     * @return the relative file path of a views fxml file.
     */
    String fxmlPath();
}
