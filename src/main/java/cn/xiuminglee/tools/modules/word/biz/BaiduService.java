package cn.xiuminglee.tools.modules.word.biz;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.xiuminglee.tools.core.config.properties.MingToolsProperties;
import cn.xiuminglee.tools.modules.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Xiuming Lee
 * @description 调用百度相关的接口
 */
@Component
public class BaiduService {

    @Autowired
    private MingToolsProperties mingToolsProperties;

    /**
     * 获取权限token
     *
     * @return 返回示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public String getAuth() {
        // 官网获取的 API Key 更新为你注册的
        String clientId = mingToolsProperties.getBaidu().getAccessKey();
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = mingToolsProperties.getBaidu().getSecretKey();
        return getAuth(clientId, clientSecret);
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     *
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = Constant.Baidu.AUTH_HOST;
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "?grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        String result = HttpUtil.get(getAccessTokenUrl);
        JSONObject resultObject = new JSONObject(result);
        String accessToken = resultObject.getStr("access_token");
        return accessToken;

    }

    /**
     * OCR 百度ORC 通用文字识别
     * @param imageBytes 图片文件的字节数组。
     * @return
     */
    public String generalBasic(byte[] imageBytes) {
        // 请求url
        String url = Constant.Baidu.OCR_GENERAL_BASIC + "?access_token=" + getAuth();
        try {
            String imageBase64 = Base64.encode(imageBytes);
            String imageResult = HttpRequest.post(url)
                    .form("image", imageBase64)
                    .execute()
                    .body();
            return resultStringBuilder(imageResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对百度OCR返回的结果进行解析。
     * @param baiduResultJSON eg:
     *                        {
     *                          "log_id": 2471272194,
     *                          "words_result_num": 2,
     *                          "words_result":
     *                          [
     *                            {"words": " TSINGTAO"},
     *                            {"words": "青島睥酒"}
     *                          ]
     *                        }
     * @return
     */
    private String resultStringBuilder(String baiduResultJSON) {
        StringBuilder resultBuilder = new StringBuilder();
        JSONObject jsonObject = new JSONObject(baiduResultJSON);
        JSONArray jsonArray = jsonObject.getJSONArray("words_result");
        jsonArray.forEach(e -> {
            JSONObject wordObject = (JSONObject) e;
            resultBuilder.append(wordObject.getStr("words") + "\n");
        });

        return resultBuilder.toString();
    }
}
