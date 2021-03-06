package cn.xiuminglee.tools.core.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Xiuming Lee
 * @description 配置总类
 */
@ConfigurationProperties(prefix = "ming.tools")
@Getter
@Setter
public class MingToolsProperties {
    private QiniuProperties qiniu = new QiniuProperties();
    private BaiduProperties baidu = new BaiduProperties();




    /** 七牛配置相关 */
    @Getter
    @Setter
    public class QiniuProperties {
        /** accessKey*/
        private String accessKey;
        /** secretKey*/
        private String secretKey;
        /** 要上传的空间*/
        private  String bucketName;
        /** 文件地址前缀，cdn地址*/
        private String filePathPrefix;
    }

    /** 百度配置相关 */
    @Getter
    @Setter
    public class BaiduProperties {
        /** OCR 的accessKey*/
        private String accessOcrKey;
        /** OCR 的secretKey*/
        private String secretOcrKey;

        /** Trans 的accessKey*/
        private String accessTransKey;
        /** Trans 的secretKey*/
        private String secretTransKey;
    }
}
