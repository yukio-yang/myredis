package com.yukio.abc.utils.easyexcel.base;


import java.io.Serializable;
import java.util.List;

public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String message;
    private Long currentTimeMillis = System.currentTimeMillis();
    private List<RError> messages;
    private T data;

    /**
     * 请求成功返回0
     * @return
     **/
    public static R ok(){
        return new R(Type.SUCCESS);
    }

    /**
     * 请求成功返回并携带数据
     * @param data
     * @return
     **/
    public static R ok(Object data){
        R r = new R(Type.SUCCESS);
        r.setData(data);
        return r;
    }

    /**
     *  创建自定义
     * @param code
     * @param msg
     * @return
     **/
    public static R build(int code, String msg){
        R<Object> r = new R<>();
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }

    public static R build(Type t, List<RError> messages){
        return new R(t, messages);
    }
    

    public static R build(int code, String message, List<RError> messages){
        return new R(code, message, messages);
    }

    public static R build(Type t){
        return new R(t);
    }

    public static R build(int code, String msg, Object data){
        R<Object> r = new R<>();
        r.setCode(code);
        r.setMessage(msg);
        r.setData(data);
        return r;
    }

    public static R build(Type t, Object data){
        R<Object> r = new R<>();
        r.setCode(t.getCode());
        r.setMessage(t.getMessage());
        r.setData(data);
        return r;
    }


    public R() {
        this(Type.SUCCESS);
    }


    public R(Type type) {
        this.code = type.getCode();
        this.message = type.getMessage();
    }

    public R(T data) {
        this(Type.SUCCESS);
        this.setData(data);
    }

    public R(Type type, List<RError> messages) {
        this(type);
        this.messages = messages;
    }

    public R(Type type, List<RError> messages, T data) {
        this(type, messages);
        this.data = data;
    }

    public R(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public R(Integer code, String message, List<RError> messages) {
        this.code = code;
        this.message = message;
        this.messages = messages;
    }

    public R(Integer code, String message, List<RError> messages, T data) {
        this.code = code;
        this.message = message;
        this.messages = messages;
        this.data = data;
    }

    public R set(Type type) {
        this.code = type.getCode();
        this.message = type.getMessage();
        return this;
    }

    public R set(Type type, List<RError> messages) {
        this.set(type);
        this.setMessages(messages);
        return this;
    }

    public R set(Type type, T data) {
        this.set(type);
        this.setData(data);
        return this;
    }

    public R set(Type type, T data, List<RError> messages) {
        this.set(type);
        this.setData(data);
        this.setMessages(messages);
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public R setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public R setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<RError> getMessages() {
        return messages;
    }

    public R setMessages(List<RError> messages) {
        this.messages = messages;
        return this;
    }

    public T getData() {
        return data;
    }

    public R setData(T data) {
        this.data = data;
        return this;
    }

    public Long getCurrentTimeMillis() {
        return currentTimeMillis;
    }

    public R setCurrentTimeMillis(Long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
        return this;
    }
}
