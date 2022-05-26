package cn.xiuminglee.tool.module.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * @author Xiuming Lee
 */
@ShellComponent
public class TestCommands {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestCommands.class);

    @ShellMethod(" Add two integers together. ")
    public int add(int a,int b) {
        int sum = a + b;
        LOGGER.info("结果为：" + sum);
        return sum;
    }
}
