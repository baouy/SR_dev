package com.sr.core;


/**
 * 返回结果类
 * Created by KingZS on 2018/5/26
 */
public class Result<T> {
    private String msg = ResultEnum.FAIL.getMsg(); //返回消息提示

    private String code = ResultEnum.FAIL.getCode();  //返回消息编码

    private boolean success = false;  //业务是否成功

    private T model;     //数据对象

    private BaseQuery query; //查询对象

    private T data;//BJUI数据存储对象

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public void defaultSuccess(){
        this.success = true;
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = ResultEnum.SUCCESS.getMsg();
    }

    public void defaultFailure(){
        this.success = false;
        this.code = ResultEnum.FAIL.getCode();
        this.msg = ResultEnum.FAIL.getMsg();
    }

    public void setCodeAndMsg(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public BaseQuery getQuery() {
        return query;
    }

    public void setQuery(BaseQuery query) {
        this.query = query;
    }


}
