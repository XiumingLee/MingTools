package cn.xiuminglee.mingtools;

import cn.xiuminglee.mingtools.qiniu.Test;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author Xiuming Lee
 * @description
 */
public class MingToolsFXApplication extends Application {

    private ConfigurableApplicationContext context;

    @Autowired
    private Test test;

    @Override
    public void init() throws Exception {
        // 将JavaFX相关的类注册到Spring应用中去
        ApplicationContextInitializer<GenericApplicationContext> initializer = genericApplicationContext-> {
            genericApplicationContext.registerBean(Application.class,()->MingToolsFXApplication.this);
            genericApplicationContext.registerBean(Parameters.class, this::getParameters);
            genericApplicationContext.registerBean(HostServices.class,this::getHostServices);
        };

        // 构建并启动Spring应用
        this.context = new SpringApplicationBuilder()
                .sources(MingToolsApplication.class)
                .initializers(initializer)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("我是标题");
        test.testEnv();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        this.context.close();
        Platform.exit();
    }
}
