package com.yukio.abc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yukio
 * @since 2022-02-08
 */
@Data//callSuper = false默认仅使用该类中定义的属性且不调用父类的方法 callSuper = true可以排除掉多个类相同属性判断的相等
@EqualsAndHashCode(callSuper = false)//exclude可以排除一些属性  of指定只使用哪些属性
@Accessors(chain = true)
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("NAME")
    private String name;

    private Double balance;


}
