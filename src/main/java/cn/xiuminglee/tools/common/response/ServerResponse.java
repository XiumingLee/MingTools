package cn.xiuminglee.tools.common.response;

/**
 * @Author: Xiuming Lee
 * @Describe: 返回工具类，方便返回
 */
public class ServerResponse<T> {

    private final boolean flag;
    private final int code;
    private String message;
    private T data;

    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<T>(true, ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
        return new ServerResponse<T>(true, ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T> createBySuccessData(T data) {
        return new ServerResponse<T>(true, ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(true, ResponseCode.SUCCESS.getCode(), msg, data);
    }


    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>(false, ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage) {
        return new ServerResponse<T>(false, ResponseCode.ERROR.getCode(), errorMessage);
    }

    public static <T> ServerResponse<T> createByErrorData(T data) {
        return new ServerResponse<T>(false, ResponseCode.ERROR.getCode(), data);
    }

    public static <T> ServerResponse<T> createByError(String errorMessage, T data) {
        return new ServerResponse<T>(false, ResponseCode.ERROR.getCode(), errorMessage, data);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage(ResponseCode code, String errorMessage) {
        return new ServerResponse<T>(false, code.getCode(), errorMessage);
    }

    public static <T> ServerResponse<T> createByErrorCodeData(ResponseCode code, T data) {
        return new ServerResponse<T>(false, code.getCode(), data);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessageData(ResponseCode code, String errorMessage, T data) {
        return new ServerResponse<T>(false, code.getCode(), errorMessage, data);
    }

    private ServerResponse(boolean flag, int code) {
        this.flag = flag;
        this.code = code;
    }

    private ServerResponse(boolean flag, int code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    private ServerResponse(boolean flag, int code, T data) {
        this.flag = flag;
        this.code = code;
        this.data = data;
    }

    private ServerResponse(boolean flag, int code, String message, T data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean getFlag() {
        return flag;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
