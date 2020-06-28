package cn.xiuminglee.tools.util;

import cn.xiuminglee.tools.core.exception.MingToolsException;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * @author Xiuming Lee
 * @description Markdown相关
 */
public class MarkdownUtils {

    /**
     * @param imageUrl http://123.com/1.png
     * @return ![](http://123.com/1.png)
     */
    public static String buildMDImageUrl(String imageUrl) {
        var md = "![](" + imageUrl + ")";
        return md;
    }

    /**
     * 获取MD格式的真实路径
     * @param mdImageUrl ![](http://123.com/1.png)
     * @return http://123.com/1.png
     */
    public static String getImageUrl(String mdImageUrl) {
        var imageUrl = "";
        // 截取视频信息
        var pattern = Pattern.compile("(?<=!\\[]\\().*?(?=\\))");
        var matcher = pattern.matcher(mdImageUrl);
        if (matcher.find()) {
            imageUrl = matcher.group();
        }
        return imageUrl;
    }

    /**
     * @param mdImageUrl ![](http://123.com/1.png)
     * @return 1.png
     * @throws MingToolsException
     */
    public static String getQiniuImageKey(String mdImageUrl) throws MingToolsException {
        var imageUrl = getImageUrl(mdImageUrl);
        if (StringUtils.isEmpty(imageUrl)){
            throw new MingToolsException("请检查七牛云图片的地址是否正确！");
        }
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    }
}
