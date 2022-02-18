package com.yukio.abc.utils.easyexcel.base;

/**
 * 信息类
 * 
 * @author ry
 * @date 2018/9/25
 */

public enum Type {
    SUCCESS(0, "成功"),

    CUSTOM_EXCEPTION(10000, "自定义异常"), 
    PARAM_VALIDATE_FAIL(400001, "参数验证失败"), 
    EXIST_ERROR(400002, "信息已存在"),
    POWER_VALIDATE_FAIL(400003, "登录已失效,请重新登录"), 
    DATA_DEL_ERROR(400004, "信息已删除"), 
    NOT_FOUND_ERROR(400005, "信息未找到"),
    PARAM_LENGTH_ERROR(400006, "参数太长"),
    Content_TYPE_ERROR(400007, "header信息中的Content-Type不正确,请确认该参数和API文档中的一致"),

    PERMISSIONS_VALIDATE_FAIL(401000, "没有权限访问此接口"), 
    ACCESS_TOKEN_VERFIY_FAIL(401005, "安全签名验证失败"),
    ACCESS_IP_VERFIY_FAIL(401006, "访问IP没有权限访问此接口"),

    PAGE_NOT_FOUND_ERROR(404000, "页面未找到"), 
    API_NOT_FOUND_ERROR(404001, "接口未找到"),

    TIMEOUT(408001, "请求超时"),

    APCOS_TIMEOUT(408002, "远程请求不成功"),


    EXCEPTION(500000, "系统错误,请联系管理员"), 
    EXCEPTION_FAIL(500001, "操作失败,请稍后在试"),

    JSON_EXCEPTION(880, "json数据解析异常"),
    JSON_NULL_EXCEPTION(881, "json数据为NULL"),
    PARAM_NULL_EXCEPTION(882, "方法参数为NULL"),
    PARAM_ERROR_EXCEPTION(883, "方法参数错误"),
    MODULE_EXCEPTION(500004, "模块调用异常"),
    DEV_CTLRES_NULL(500004, "设备调用返回结果空"),
    FORMAT_EXCEPTION(2, "参数校验失败"),
    UNKOWN_FORMAT_EXCEPTION(3, "未知参数验证错误"),
    UNKOWN_ERROR(4, "未知错误"),
    DATABASE_EXCEPTION(800, "数据库操作异常"),
    DATA_REPETITION(801, "数据重复"),
    DATA_NULLINSERT(802, "字段为空异常"),
    DATA_FIELDTOOLONG(803, "字段内容太长"),
    DATA_UNEXIST(804, "数据不存在"),
    DATA_DEL_FAIL(805, "数据不能删除"),
    UNLOGIN_ERROR(6, "未登录错误"),
    ORG_ERROR(7, "组织机构获取失败"),
    USER_ORG_ERROR(7, "根据用户组织机构获取失败"),
    NO_ACCESS(801, "没有权限进行此操作");

    private Integer code;
    private String message;

    Type(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
