package com.zysd.kettleweb.config;

import com.zysd.kettleweb.beans.BaseEnum;
import com.zysd.kettleweb.beans.RestResponse;
import com.zysd.kettleweb.beans.ZYException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理
 *  @author cartman
 *  2019/0/05 上午11:33
 */
@ControllerAdvice
public class GloablExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestResponse<String> handleException(Exception e) {
        // 记录错误信息
        String msg = e.getMessage();
        if (msg == null || msg.equals(BaseEnum.NULL_STRING)) {
            msg = "服务器发生未知错误，请联系管理员";
        }
        return  RestResponse.fail(msg);
    }

    @ExceptionHandler(ZYException.class)
    @ResponseBody
    public RestResponse<String> handleException(ZYException e) {
        return  RestResponse.fail(e.getErrCode(),e.getMessage());
    }

}
