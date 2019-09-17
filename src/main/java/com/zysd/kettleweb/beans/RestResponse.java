package com.zysd.kettleweb.beans;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class RestResponse<T> implements Serializable {
    public static final long serialVersionUID = 42L;
    private Integer code;
    private String msg;
    private T data;

    public RestResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> RestResponse<T> success(T data){
        return new RestResponse<>(HttpStatus.OK.value(), "", data);
    }
    public static RestResponse<String> fail(String message){
        return new RestResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }

    public static RestResponse<String> fail(Integer code,String message){
        return new RestResponse<>(code, message, null);
    }
}
