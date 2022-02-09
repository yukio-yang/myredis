package com.yukio.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 自媒体图文内容信息表
 * </p>
 *
 * @author yukio
 * @since 2020-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cms_news")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    public News() {
    }

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 自媒体用户ID
     */
    private Integer userId;

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    /**
     * 标题
     */
    private String title;

    /**
     * 图文内容
     */
    private String content;

    /**
     * 文章布局
            0 无图文章
            1 单图文章
            2 多图文章
     */
    private Boolean type;

    /**
     * 图文频道ID
     */
    private Integer channelId;

    private String labels;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")//这个在LocalDateTime不起效果，需在rediscONFIG里面的jackson2JsonRedisSerializer()配置
    private LocalDateTime createdTime;//LocalDateTime  HH:mm:ss

    /**
     * 提交时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") // HH:mm:ss
    private LocalDateTime submitedTime;

    /**
     * 当前状态
            0 草稿
            1 提交（待审核）
            2 审核失败
            8 审核通过（待发布）
            9 已发布
     */
    private Integer status;

    /**
     * 定时发布时间，不定时则为空
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime publishTime;

    /**
     * 拒绝理由
     */
    private String reason;

    /**
     * 发布库文章ID
     */
    private Integer articleId;

    /**
     * //图片用逗号分隔
     */
    private String images;

    public News(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
