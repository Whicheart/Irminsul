package com.genshin.lunar.common;

import lombok.Data;

/**
 * 统一响应信封：code 200 表示成功，其余为业务/系统错误。
 */
@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(200, "ok", data);
    }

    public static <T> Result<T> ok() {
        return new Result<>(200, "ok", null);
    }

    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }
}
