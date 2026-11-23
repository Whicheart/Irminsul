package com.genshin.irminsul.common;

/**
 * 业务异常，由 GlobalExceptionHandler 统一转换为 Result.error
 */
public class BizException extends RuntimeException {
    // 业务错误码，原样写入 Result.code
    private final int code;

    /**
     * 使用默认错误码 400 创建业务异常
     *
     * @param message 面向调用方的错误提示
     */
    public BizException(String message) {
        this(400, message);
    }

    /**
     * 使用指定错误码创建业务异常
     *
     * @param code    业务错误码，对应 Result.code
     * @param message 面向调用方的错误提示
     */
    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 返回业务错误码
     *
     * @return 业务错误码
     */
    public int getCode() {
        return code;
    }
}
