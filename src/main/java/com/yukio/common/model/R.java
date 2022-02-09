package com.yukio.common.model;

import org.springframework.http.HttpStatus;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yukio
 * @create 2020-06-15 9:53
 * 业务相应对象
 */
public class R  extends ConcurrentHashMap<String,Object> {
    public R(){
        this.put("code", HttpStatus.OK.value());//相当于默认返回这个，如果有覆盖返回覆盖的
        this.put("msg", "success!!!");
    }

    public static R ok() { return new R(); }

    public static R ok(String msg) {
        R r = R.ok();
        r.put("msg", msg);
        return r;//put返回的是value的类型String
    }


    public static R ok(Object data) {
        R r = R.ok();
        r.put("data", data);
        return r;//put返回的是value的类型String
    }

    public static R ok(String msg, Object data) {
        R r = R.ok();
        r.put("msg", msg);
        r.put("data", data);
        return r;//put返回的是value的类型String
    }

    public static R error(HttpStatus status, String error) {
        R r = R.ok();
        r.put("code", status);
        r.put("error", error);
        r.put("msg", "");
        return r;
//                R.ok().put("code", status)
//                .put("error", error)
//                .put("msg", "");
    }

    public static R error(HttpStatus status, String error, String msg) {
        R r = R.ok();
        r.put("code", status);
        r.put("error", error);
        r.put("msg", msg);
        return r;
//        return R.ok()
//                .put("code", status)
//                .put("error", error)
//                .put("msg", msg);
    }



}
