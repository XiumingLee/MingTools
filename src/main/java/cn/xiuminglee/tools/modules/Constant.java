package cn.xiuminglee.tools.modules;

import javafx.scene.input.Clipboard;

/**
 * @author Xiuming Lee
 * @description 使用的常量
 */
public class Constant {

    public interface System {
        /** 应用图标 */
        String SYSTEM_ICON = "assets/img/app/tools.png";
        /** 全局样式 bootstrapfx */
        //String GLOBAL_STYLESHEET = "org/kordamp/bootstrapfx/bootstrapfx.css";
        /** 系统粘贴板 */
        Clipboard CLIPBOARD = Clipboard.getSystemClipboard();
    }

    public interface Qiniu {
        /** 七牛工具页面背景图片 */
        String QINIU_IMAGE_AREA = "fxml/qiniu/img/area.jpg";
    }

    public interface Baidu {
        /** 百度ORC 授权地址 */
        String OCR_AUTH_HOST = "https://aip.baidubce.com/oauth/2.0/token";
        /** 百度ORC 通用文字识别 */
        String OCR_GENERAL_BASIC = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
        /** 百度翻译相关 */
        String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    }
}
