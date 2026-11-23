package com.genshin.irminsul.common;

import lombok.Data;

/**
 * 统一响应信封：code 200 表示成功，其余为业务/系统错误
 */
@Data
public class Result<T> {
    // 响应码：200 成功，其余为业务/系统错误
    private int code;
    // 提示信息，成功时固定为 "ok"
    private String msg;
    // 业务数据，错误时为 null
    private T data;

    // 供 Jackson 反序列化使用
    public Result() {}

    /**
     * 构造统一响应
     *
     * @param code 响应码
     * @param msg  提示信息
     * @param data 业务数据
     */
    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 构造携带数据的成功响应
     *
     * @param data 业务数据，可为 null
     * @param <T>  数据类型
     * @return code 为 200 的响应
     */
    public static <T> Result<T> ok(T data) {
        return new Result<>(200, "ok", data);
    }

    /**
     * 构造不含数据的成功响应
     *
     * @param <T> 数据类型
     * @return code 为 200 的响应
     */
    public static <T> Result<T> ok() {
        return new Result<>(200, "ok", null);
    }

    /**
     * 构造错误响应
     *
     * @param code 错误码
     * @param msg  错误提示
     * @param <T>  数据类型
     * @return 不含业务数据的错误响应
     */
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }
}
