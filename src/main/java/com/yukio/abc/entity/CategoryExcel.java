package com.yukio.abc.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yukio.abc.utils.easyexcel.converter.SetConsumePayTypeConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品分类
 * </p>
 *
 * @author yukio
 * @since 2022-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@ExcelIgnoreUnannotated//easy排除未标记的选项(或字段上使用@ExcelIgnore)
@ContentRowHeight(20)//设置行高
@ToString
public class CategoryExcel extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 路径
     */
    @ExcelProperty(value = "路径",index = 0)//,converter = SetConsumeDiscernLogURLConverter.class
    @ColumnWidth(30)
    private String path;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 名称
     */
    @ExcelProperty(value = "目录名称",index = 1)
    @ColumnWidth(30)
    private String name;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 状态：0/下架 1/上架
     */
    @ApiModelProperty(value = "状态(1_上架,0_下架)" )
    @ExcelProperty(value = "状态",index = 2,converter = SetConsumePayTypeConverter.class)
    @ColumnWidth(30)
    private Integer state;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间",index = 3)
    @ColumnWidth(30)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


}
