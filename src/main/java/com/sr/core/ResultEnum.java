package com.sr.core;

/**
 * Created by KingZS on 2015/5/26.
 */
public enum ResultEnum {

    FAIL("1000","失败"),
    SUCCESS("1001","成功"),
    PRAM_NOT_NULL("1002","参数不能为空"),
    USERNANE_EXIST("2001","用户名已存在"),
    USERNANE_NOTEXIST("2004","用户名不存在"),
    PASSWORD_ERROR("2003","密码错误，请重新输入"),
    UPFILE_FAILURE("9001","上传失败"),
    FILE_NOT_NULL("8001","上传文件为空"),
    FILE_BEYOND_LIMITSIZE("8002","上传文件超出大小限制10KB"),
    LOGIN_FIRST("2000","请先登录");




    private String code;
    private String msg;

    private ResultEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
