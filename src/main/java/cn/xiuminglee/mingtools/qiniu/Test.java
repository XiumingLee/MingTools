package cn.xiuminglee.mingtools.qiniu;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author Xiuming Lee
 * @description
 */
@Component
public class Test {

    private Environment environment;

    public Test(Environment environment) {
        this.environment = environment;
    }

    public void testEnv(){
        System.out.println(environment.getProperty("ming.test"));
        System.out.println(environment.getProperty("ming.test2"));
    }
}
