package com.diary.calendar.exception;

/**
 * 业务逻辑异常
 */
public class ServiceException extends RuntimeException {

    private final int code;

    // 构造器：仅消息（默认 code = 400）
    public ServiceException(String message) {
        super(message);
        this.code = 400;
    }

    // 构造器：指定错误码和消息
    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    // 构造器：带 cause（用于包装其他异常）
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
    }

    // Getter
    public int getCode() {
        return code;
    }
}