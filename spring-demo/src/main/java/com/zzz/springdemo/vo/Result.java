package com.zzz.springdemo.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result<T> {

    Integer code;

    String msg;

    T data;

    public static <T> Result success(String msg, T data) {
        return Result.builder().code(200).msg(msg).data(data).build();
    }

    public static <T> Result success(T data) {
        return success("success", data);
    }

    public static Result error(String msg) {
        return Result.builder().code(500).msg(msg).build();
    }

}
