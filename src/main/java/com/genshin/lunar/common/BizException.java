package com.genshin.lunar.common;

/**
 * 业务异常，由 GlobalExceptionHandler 统一转换为 Result.error。
 */
public class BizException extends RuntimeException {
    private final int code;

    public BizException(String message) {
        this(400, message);
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
