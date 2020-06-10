package cn.xiuminglee.mingtools.qiniu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author Xiuming Lee
 * @description
 */
@Component
@Slf4j
public class Test {

    private Environment environment;

    public Test(Environment environment) {
        this.environment = environment;
    }

    public void testEnv(){
        log.info("日志测试");
        System.out.println(environment.getProperty("ming.test"));
        System.out.println(environment.getProperty("ming.test2"));
    }
}
