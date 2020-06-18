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

    public interface Baidu {
        /** 授权地址 */
        String AUTH_HOST = "https://aip.baidubce.com/oauth/2.0/token";
        /** 百度ORC 通用文字识别 */
        String OCR_GENERAL_BASIC = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
    }
}
