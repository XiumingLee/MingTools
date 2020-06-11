package cn.xiuminglee.tools;

import cn.xiuminglee.tools.modules.home.view.HomeController;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
    private HomeController homeController;

    @Override
    public void init() throws Exception {
        // 将JavaFX相关的类注册到Spring应用中去
        ApplicationContextInitializer<GenericApplicationContext> initializer = genericApplicationContext -> {
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
        primaryStage.setTitle("MingTools工具包");
        primaryStage.getIcons().add(new Image("assets/img/app/tools.png"));
        Scene scene = new Scene(homeController.anchorPane);
        primaryStage.setScene(scene);
        //HomeViewInit home = new HomeViewInit();
        //home.initView();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        this.context.close();
        Platform.exit();
    }
}
