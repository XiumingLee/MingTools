package cn.xiuminglee.mingtools.config;

import cn.xiuminglee.mingtools.config.properties.MingToolsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Xiuming Lee
 * @description
 */
@Configuration
@EnableConfigurationProperties(MingToolsProperties.class)
public class MingToolsConfig {
}
