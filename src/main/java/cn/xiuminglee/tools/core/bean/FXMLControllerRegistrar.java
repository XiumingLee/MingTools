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
 * 当Bean是`@Scope("prototype")`时，Spring启动时如果没有使用Bean，不会创建，再使用时才会创建，在创建原型Bean时，还是会
 * 调用BeanPostProcessor的方法。
 *
 */
@Slf4j
@Component
public class FXMLControllerRegistrar implements BeanPostProcessor {

    /**
     * 移花接木注册FXMLController
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Before：" + beanName);
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(FXMLView.class)) {
            beanFXMLControllerVerifier(bean,beanName);
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

    /**
     * 执行FXMLController的initController方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("After：" + beanName);
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(FXMLView.class)) {
            FXMLController controller = (FXMLController)bean;
            controller.initController();
        }
        return bean;
    }

    /**
     * 对FXMLController校验，校验是否符合规则。
     */
    private void beanFXMLControllerVerifier(Object bean, String beanName){
        if (!(bean instanceof FXMLController)){
            log.error("FXMLView注解必须注解在FXMLController的实现类上，{}：{}不是FXMLController的实现类！",beanName,bean.getClass().getName());
            throw new RuntimeException("FXMLView注解必须注解在FXMLController的实现类上，" + beanName + "：" + bean.getClass().getName() + "不是FXMLController的实现类！");
        }
    }
}
