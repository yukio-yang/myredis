package com.yukio.abc.utils.easyexcel.base;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 *          错误键值对类型
 * @author waxxd
 * @version 1.0
 * @date 2020-06-01
 * @jdkversion since 8
 **/
public class RError {

    private String key;

    private String value;

    public static RError build(String key, String value) {
        return new RError(key, value);
    }

    /**
     *  生成错误list
     * @param fieldErrorList 传入BindingResult.getFieldErrors()的返回值
     * @return RError的list集合
     **/
    public static List<RError> buildFromFieldErrorList(List<FieldError> fieldErrorList){
        List<RError> errorList = new ArrayList<>();
        fieldErrorList.forEach( (fieldError) -> {
            errorList.add(build(fieldError.getField(), fieldError.getDefaultMessage()));
        });
        return errorList;
    }

    public RError(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
