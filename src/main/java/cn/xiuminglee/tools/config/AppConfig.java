package cn.xiuminglee.tools.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;

/**
 * @author Xiuming Lee
 * @date 2022/2/15 10:40
 * @desc
 */
@Configuration
public class AppConfig implements WebFluxConfigurer {

    /**
     * 页面重定向
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> homePage(){
        return RouterFunctions.route()
                .GET("/",serverRequest -> ServerResponse.permanentRedirect(URI.create("/index.html")).build())
                .build();
    }


}
