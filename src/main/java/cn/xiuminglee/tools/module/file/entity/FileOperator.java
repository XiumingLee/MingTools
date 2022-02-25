package cn.xiuminglee.tools.module.file.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

/**
 * @author Xiuming Lee
 * @date 2022/2/25 13:07
 * @desc
 */
public enum FileOperator {
    /** 删除操作 */
    DELETE("delete"),
    /** 重命名操作 */
    RENAME("rename");

    private final String value;

    FileOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /** 用于Jackson反序列化时通过字符串反序列化过来 */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static FileOperator create(Object value) {
        final String stringValue = value.toString();
        for (FileOperator item : values()) {
            if (Objects.equals(stringValue, item.name())) {
                return item;
            } else if (Objects.equals(item.getValue(), stringValue)) {
                return item;
            }
        }
        return null;
    }


}
