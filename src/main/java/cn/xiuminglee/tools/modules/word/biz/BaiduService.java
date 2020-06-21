package cn.xiuminglee.tools.modules.word.biz;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.xiuminglee.tools.core.config.properties.MingToolsProperties;
import cn.xiuminglee.tools.core.exception.MingToolsException;
import cn.xiuminglee.tools.modules.Constant;
import cn.xiuminglee.tools.modules.word.biz.element.LanguageType;
import cn.xiuminglee.tools.modules.word.biz.translate.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Xiuming Lee
 * @description 调用百度相关的接口
 */
@Component
public class BaiduService {

    @Autowired
    private MingToolsProperties mingToolsProperties;

    private String ocrToken;


    // region OCR相关 --------------------------------------------------------------
    public String getOcrAuth(){
        String ak = mingToolsProperties.getBaidu().getAccessOcrKey();
        String sk = mingToolsProperties.getBaidu().getSecretOcrKey();
        String authHost = Constant.Baidu.OCR_AUTH_HOST;
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
    public String generalOcrBasic(byte[] imageBytes, LanguageType languageType) throws MingToolsException {
        // 请求url
        String url = Constant.Baidu.OCR_GENERAL_BASIC + "?access_token=" + ocrToken;
        String ocrBuilder = null;
        try {
            String imageBase64 = Base64.encode(imageBytes);
            String imageResult = HttpRequest.post(url)
                    .form("image", imageBase64)
                    .form("language_type", languageType.getKey())
                    .execute()
                    .body();
            ocrBuilder = resultStringOcrBuilder(imageResult);
        } catch (Exception e) {
            throw new MingToolsException("检查网络是否畅通或者已经正确配置了百度OCR的AccessKey和SecretKey！",e);
        }
        return ocrBuilder;
    }

    /**
     * 对百度OCR返回的结果进行解析。
     * @param baiduResultJSON eg:
     *                        {
     *                        "log_id": 2471272194,
     *                        "words_result_num": 2,
     *                        "words_result":
     *                        [
     *                        {"words": " TSINGTAO"},
     *                        {"words": "青島睥酒"}
     *                        ]
     *                        }
     * @return
     */
    private String resultStringOcrBuilder(String baiduResultJSON) throws MingToolsException {
        StringBuilder resultBuilder = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(baiduResultJSON);
            JSONArray jsonArray = jsonObject.getJSONArray("words_result");
            jsonArray.forEach(e -> {
                JSONObject wordObject = (JSONObject) e;
                resultBuilder.append(wordObject.getStr("words") + "\n");
            });
        } catch (Exception e) {
            throw new MingToolsException("请检查是否配置正确的百度OCR的AccessKey和SecretKey！",e);
        }

        return resultBuilder.toString();
    }
    // endregion OCR相关 --------------------------------------------------------------


    // region 百度翻译相关 --------------------------------------------------------------

    /**
     *
     * @param query 待翻译的文字
     * @param from 源语言
     * @param to 目标语言
     * @return
     */
    public String getTransResult(String query, String from, String to) throws Exception {
        String transBuilder = null;
        try {
            Map<String, String> params = buildTransParams(query, from, to);
            var resultStr = HttpGet.get(Constant.Baidu.TRANS_API_HOST, params);
            transBuilder = resultStringTransBuilder(resultStr);
        } catch (Exception e) {
            throw new MingToolsException("请检查是否配置正确的百度翻译的AccessKey和SecretKey！",e);
        }
        return transBuilder;
    }

    private Map<String, String> buildTransParams(String query, String from, String to) {
        String appid = mingToolsProperties.getBaidu().getAccessTransKey();
        String securityKey = mingToolsProperties.getBaidu().getSecretTransKey();
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        //params.put("sign", MD5.md5(src));
        params.put("sign", SecureUtil.md5().digestHex(src));
        return params;
    }

    /**
     * 对百度翻译返回的结果进行解析。
     * @param resultStr eg:
     *               {"from":"en","to":"zh","trans_result":[{"src":"apple","dst":"苹果"}]}
     * @return
     */
    private String resultStringTransBuilder(String resultStr) {
        StringBuilder resultBuilder = new StringBuilder();
        JSONObject jsonObject = new JSONObject(resultStr);
        JSONArray jsonArray = jsonObject.getJSONArray("trans_result");
        jsonArray.forEach(e -> {
            JSONObject wordObject = (JSONObject) e;
            resultBuilder.append(wordObject.getStr("dst") + "\n");
        });
        return resultBuilder.toString();
    }
    // endregion 百度翻译相关 --------------------------------------------------------------

    @PostConstruct
    public void init(){
        ocrToken = getOcrAuth();
    }
}
