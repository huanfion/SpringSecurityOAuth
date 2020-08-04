package com.huanfion.wms.common.exception;

/**
 * @author huanfion
 * @version 1.0
 * @date 2019/8/30 9:54
 */
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(Throwable throwable) {
        super(throwable);
    }

    public CustomException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
