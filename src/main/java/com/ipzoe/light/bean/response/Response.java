package com.ipzoe.light.bean.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

/**
 * 接口返回值格式定义.
 * <p>
 * 接口返回均为JSON格式, 形式为: {code: XXX, message:, YYY, data: ZZZ}
 * 其中code为错误码, message为简单的错误描述, data为请求成功时返回的所需数据.
 * 注意: message不能作为前端显示错误的凭据, 该值仅为开发调试使用.
 * <p>
 * Created by xingfinal on 15/11/27.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    @ApiModelProperty(value = "error code", required = true)
    private long code;
    @ApiModelProperty(value = "error message", required = true)
    private String message;
    @ApiModelProperty(value = "data", required = true)
    private T data;

    public static <T> Response<T> ok() {
        return ok(Code.SUCCESS);
    }

    public static <T> Response<T> ok(Code code) {
        return ok(code, null);
    }

    public static <T> Response<T> ok(T data) {
        return ok(Code.SUCCESS, data);
    }

    public static <T> Response<T> ok(Code code, T data) {
        return new Response<>(code, data);
    }

    public Response(T data) {
        this(Code.SUCCESS, data);
    }

    public Response(Code code) {
        this(code.getCode(), code.getMessage(), null);
    }

    public Response(Code code, T data) {
        this(code.getCode(), code.getMessage(), data);
    }

    public Response(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
