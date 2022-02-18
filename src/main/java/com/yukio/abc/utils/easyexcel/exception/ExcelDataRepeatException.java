package com.yukio.abc.utils.easyexcel.exception;


import com.yukio.abc.utils.easyexcel.base.R;
import com.yukio.abc.utils.easyexcel.base.Type;

import java.util.List;

/**
 * @author yukio
 * @create 2021-02-06 17:38
 */
public class ExcelDataRepeatException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    R result = new R();

    public ExcelDataRepeatException(R result) {
        this.result = result;
    }

    public ExcelDataRepeatException(Type type) {
        super(type.getMessage());
        result.setCode(type.getCode());
        result.setMessage(type.getMessage());
    }

    public ExcelDataRepeatException(Integer code, String message) {
        super(message);
        result.setCode(code);
        result.setMessage(message);
    }

    public ExcelDataRepeatException(Type type, List<Error> messages) {
        super(type.getMessage());
        result.setCode(type.getCode());
        result.setMessage(type.getMessage());
        result.setMessages(messages);
    }

    public ExcelDataRepeatException(Integer code, String message, List<Error> messages) {
        super(message);
        result.setCode(code);
        result.setMessage(message);
        result.setMessages(messages);
    }

    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
    }
}
