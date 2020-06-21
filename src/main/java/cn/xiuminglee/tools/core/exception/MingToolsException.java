package cn.xiuminglee.tools.core.exception;

/**
 * @author Xiuming Lee
 * @description
 */
public class MingToolsException extends Exception {
    public MingToolsException() {
        super();
    }

    public MingToolsException(String message) {
        super(message);
    }

    public MingToolsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MingToolsException(Throwable cause) {
        super(cause);
    }

}
