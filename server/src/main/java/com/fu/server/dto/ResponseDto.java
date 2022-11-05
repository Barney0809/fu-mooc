package com.fu.server.dto;

public class ResponseDto<T> {
    /**
     * 返回码
     */
    private String code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回泛型数据，自定义类型
     */
    private T data;

    public ResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseDto(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseDto<T> success(){
        return new ResponseDto<>("0","成功");
    }

    public static <T> ResponseDto<T> success(T data){
        return new ResponseDto<>("0","成功",data);
    }

    public static <T> ResponseDto<T> error(){
        return new ResponseDto<>("-1","服务端错误");
    }

    public static <T> ResponseDto<T> error(String message){
        return new ResponseDto<>("-1",message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
