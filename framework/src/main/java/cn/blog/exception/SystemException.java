package cn.blog.exception;

import cn.blog.enums.AppHttpCodeEnum;


/**
 * @author KaelvihN
 */
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

    public SystemException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
