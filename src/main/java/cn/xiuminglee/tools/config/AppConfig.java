package cn.xiuminglee.tools.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

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
    public RouterFunction<ServerResponse> homePage(@Value("classpath:/static/index.html") final Resource indexHtml){
        return RouterFunctions.route()
                .GET("/",serverRequest -> ok().contentType(MediaType.TEXT_HTML).bodyValue(indexHtml))
                .build();
    }


}
