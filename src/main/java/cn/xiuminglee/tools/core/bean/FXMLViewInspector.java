package cn.xiuminglee.tools.core.bean;

import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author Xiuming Lee
 * @description 用于检查FXMLView注解标注的是否正确
 * @see FXMLView
 * @see AbstractController
 */
@Slf4j
@Component
public class FXMLViewInspector implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

    }

    /**
     * 用于检测FXMLView注解标记的是否正确，FXMLView注解必须注解再AbstractController的实现类上
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanNames = beanFactory.getBeanNamesForAnnotation(FXMLView.class);
        if (beanNames!=null && beanNames.length>0) {
            for (String beanName : beanNames) {
                //Class<?> type = beanFactory.getType(beanName);
                Object bean = beanFactory.getBean(beanName);
                if (!(bean instanceof AbstractController)){
                    log.error("FXMLView注解必须注解在AbstractController的实现类上，{}：{}不是AbstractController的实现类！",beanName,bean.getClass().getName());
                    throw new RuntimeException("FXMLView注解必须注解在AbstractController的实现类上，" + beanName + "：" + bean.getClass().getName() + "不是AbstractController的实现类！");
                }
            }
        }
    }
}
