package cn.xiuminglee.tools.modules.qiniu.biz;

import cn.xiuminglee.tools.core.config.properties.MingToolsProperties;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @author Xiuming Lee
 * @description 七牛云操作相关的API
 */
@Service
public class QiniuAPI {
    private final MingToolsProperties mingToolsProperties;

    private String pathPreFix;
    private String bucketName;

    private Auth auth;
    private Configuration cfg;
    private UploadManager uploadManager;

    public QiniuAPI(MingToolsProperties mingToolsProperties) {
        System.out.println("QiniuAPI construct");
        this.mingToolsProperties = mingToolsProperties;
    }

    /**
     * 普通上传
     *
     * @param bytes   文件字节组
     *  extName 扩展名，不带点.  jpg  png  gif
     * @return
     */
    public String upload(byte[] bytes) throws QiniuException {
        // TODO:这里将图片的扩展名写死了。
        String extName = "png";
        String upToken = auth.uploadToken(bucketName);
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_" + Math.abs(new Random().nextInt(999)) + "." + extName;
        Response response = uploadManager.put(bytes, fileName, upToken);
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        String key = putRet.key;
        return pathPreFix + key;
    }

    /**
     * 删除文件
     * @param key
     * @return
     */
    public void deleteByKey(String key) throws QiniuException {
        BucketManager bucketManager = new BucketManager(auth, cfg);
        bucketManager.delete(bucketName, key);
    }


    /** 初始化相关变量属性 */
    @PostConstruct
    public void init(){
        String accessKey = mingToolsProperties.getQiniu().getAccessKey();
        String secretKey = mingToolsProperties.getQiniu().getSecretKey();
        pathPreFix = mingToolsProperties.getQiniu().getFilePathPrefix();
        bucketName = mingToolsProperties.getQiniu().getBucketName();

        auth = Auth.create(accessKey, secretKey);
        cfg = new Configuration(Region.region1());
        uploadManager = new UploadManager(cfg);
    }
}
