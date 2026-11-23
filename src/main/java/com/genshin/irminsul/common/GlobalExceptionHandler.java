package com.genshin.irminsul.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理：业务异常按原样返回，其余统一兜底为 500
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 业务异常记 warn，未预期异常记 error 并输出完整堆栈
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理业务异常，按其携带的错误码原样返回
     *
     * @param e 业务异常
     * @return 统一错误响应，data 为 null
     */
    @ExceptionHandler(BizException.class)
    public Result<Void> handleBiz(BizException e) {
        log.warn("业务异常: {}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 兜底处理未预期异常，避免堆栈直接暴露给客户端
     *
     * @param e 未捕获异常
     * @return 错误码 500 的统一响应
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("未捕获异常", e);
        return Result.error(500, "服务器内部错误");
    }
}
