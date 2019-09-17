package com.zysd.kettleweb.beans;


import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ZYException extends RuntimeException {

    private Integer errCode = 0;

    public ZYException(Integer errCode, String msg){
        super(msg);
        this.errCode = errCode;
    }

    public ZYException(String msg) {
        super(msg);
        errCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }


    public static ZYException fail(String msg) {
        return new ZYException(msg);
    }
}
