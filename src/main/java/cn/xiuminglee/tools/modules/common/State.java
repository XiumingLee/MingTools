package cn.xiuminglee.tools.modules.common;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Xiuming Lee
 * @description OCR或翻译的状态值
 */
public enum State {
    /** 准备阶段 */
    READY("#808080",new SimpleStringProperty("准备")),
    RUNNING("#05d4f9",new SimpleStringProperty("进行中...")),
    SUCCEEDED("#1afb03",new SimpleStringProperty("已完成")),
    FAILED("#c60121",new SimpleStringProperty("失败"));


    private final String color;
    private final StringProperty stateMessage;

    State(String color, StringProperty stateMessage) {
        this.color = color;
        this.stateMessage = stateMessage;
    }

    public String getColor() {
        return color;
    }

    public String getStateMessage() {
        return stateMessage.get();
    }

    public StringProperty stateMessageProperty() {
        return stateMessage;
    }
}
