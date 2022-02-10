package com.yukio.abc.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品分类
 * </p>
 *
 * @author yukio
 * @since 2022-02-08
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryVo extends  BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 路径
     */
    private String path;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 状态：0/下架 1/上架
     */
    private Integer state;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(example = "2022-02-10 10:10:10")
    private LocalDateTime createTime;//

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(example = "2022-02-10 10:10:10")
    private LocalDateTime updateTime;


}
