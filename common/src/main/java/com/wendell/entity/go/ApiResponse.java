package com.wendell.entity.go;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author ZhangWenhao
 * @date 2026/1/5 16:59
 */

public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "Asia/Shanghai")
    private ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));

    public ApiResponse() {}

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, "OK", data);
    }

    public static <T> ApiResponse<T> success(T data, String msg) {
        return new ApiResponse<>(200, msg, data);
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    // getters & setters
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public ZonedDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(ZonedDateTime timestamp) { this.timestamp = timestamp; }
}

