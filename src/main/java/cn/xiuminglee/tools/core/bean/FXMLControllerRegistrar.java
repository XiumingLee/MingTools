package cn.xiuminglee.tools.core.bean;

import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import javafx.fxml.FXMLLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Xiuming Lee
 *
 * @description 这里利用Bean的后置处理器，将Spring容器中的FXMLController使用FXMLLoader实例化好的进行替换
 *
 * 这种移花接木的方式注册Bean，解决了`使用FactoryBean方式注册FXMLController到Spring容器` 的问题
 *
 * 注意点：FXMLController之间最好不要使用Spring的自动注入，因为我们可能希望，每次打开某个页面都是重新加载的，而不是保留了
 * 上一次的编辑内容。如果使用FXMLController之间使用Spring的自动注入，可能会造成，你关闭了一个页面，重新打开时，发现还保留着
 * 上次的编辑内容，如果有这种需求可能使用。
 * 推荐的做法：使用 {@link cn.xiuminglee.tools.util.SpringContextHolder#getBean(Class)}，从容器中获取一个
 * 最原始的FXMLController。
 *
 */
@Component
@Slf4j
public class FXMLControllerRegistrar implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(FXMLView.class)) {
            try {
                FXMLView annotation = beanClass.getAnnotation(FXMLView.class);
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(annotation.fxmlPath()));
                loader.setController(bean);
                loader.load();
                // 移花接木：修改了Spring容器中的对象
                return loader.getController();
            } catch (IOException e) {
                log.error("FXMLLoader加载异常！{}：{}",beanName,beanClass.getName(),e);
                throw new RuntimeException("FXMLLoader加载异常！" + beanName + "：" + beanClass.getName(),e);
            }
        } else {
            return bean;
        }
    }
}
