package cn.xiuminglee.tools.modules.word.biz.element;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Xiuming Lee
 * @description OCR图片文字类型
 * 百度：
 * 识别语言类型，默认为CHN_ENG
 * 可选值包括：
 * - key ：value
 * - CHN_ENG：中英文混合
 * - ENG：英文
 * - JAP：日语
 * - KOR：韩语
 * - FRE：法语
 * - SPA：西班牙语
 * - POR：葡萄牙语
 * - GER：德语
 * - ITA：意大利语
 * - RUS：俄语
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageType {
    private String key;
    private String value;



    public static ObservableList<LanguageType> buildBaiduLanguageType(){
        return FXCollections.observableArrayList(
                new LanguageType("CHN_ENG","中英文混合"),
                new LanguageType("ENG","英文"),
                new LanguageType("JAP","日语"),
                new LanguageType("KOR","韩语"),
                new LanguageType("FRE","法语"),
                new LanguageType("SPA","西班牙语"),
                new LanguageType("POR","葡萄牙语"),
                new LanguageType("GER","德语"),
                new LanguageType("ITA","意大利语"),
                new LanguageType("RUS","俄语")
        );
    }
    public static ObservableList<LanguageType> buildBaiduTransLanguageType(){
        return FXCollections.observableArrayList(
                new LanguageType("auto","自动检测"),
                new LanguageType("zh","中文"),
                new LanguageType("en","英文"),
                new LanguageType("yue","粤语"),
                new LanguageType("wyw","文言文"),
                new LanguageType("jp","日语"),
                new LanguageType("kor","韩语"),
                new LanguageType("de","德语"),
                new LanguageType("it","意大利语"),
                new LanguageType("ru","俄语")
        );
    }

    public static class LanguageTypeStringConverter extends StringConverter<LanguageType> {
        @Override
        public String toString(LanguageType languageType) {
            return languageType.getValue();  // 这里将想要 看到的内容显示出来
        }
        @Override
        public LanguageType fromString(String s) {
            return null;
        }
    }
}
